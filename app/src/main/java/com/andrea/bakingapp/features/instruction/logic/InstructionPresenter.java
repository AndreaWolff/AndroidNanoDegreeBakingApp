package com.andrea.bakingapp.features.instruction.logic;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;

import com.andrea.bakingapp.R;
import com.andrea.bakingapp.features.common.domain.Recipe;
import com.andrea.bakingapp.features.common.domain.Step;
import com.andrea.bakingapp.features.instruction.InstructionContract;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static android.support.v4.media.session.MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS;
import static android.support.v4.media.session.MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS;
import static android.support.v4.media.session.PlaybackStateCompat.STATE_PAUSED;
import static android.support.v4.media.session.PlaybackStateCompat.STATE_PLAYING;
import static com.andrea.bakingapp.features.common.ActivityConstants.CURRENT_POSITION;
import static com.andrea.bakingapp.features.common.ActivityConstants.MEDIA_SESSION_TAG;
import static com.andrea.bakingapp.features.common.ActivityConstants.PLAYBACK_STATE;
import static com.andrea.bakingapp.features.common.ActivityConstants.PLAY_THUMBNAIL;
import static com.andrea.bakingapp.features.common.ActivityConstants.PLAY_VIDEO;
import static com.andrea.bakingapp.features.common.ActivityConstants.PLAY_WHEN_READY;
import static com.andrea.bakingapp.features.common.ActivityConstants.RECIPE;
import static com.andrea.bakingapp.features.common.ActivityConstants.STEP;
import static com.andrea.bakingapp.features.common.ActivityConstants.THUMBNAIL_URL;
import static com.andrea.bakingapp.features.common.ActivityConstants.VIDEO_URL;
import static com.google.android.exoplayer2.ExoPlayer.STATE_READY;

public class InstructionPresenter {

    private final Context context;

    private InstructionContract.View view;
    private SimpleExoPlayer simpleExoPlayer;
    private MediaSessionCompat mediaSession;
    private PlaybackStateCompat.Builder stateBuilder;
    private Recipe recipe;
    private Step step;
    private boolean inTabletMode;
    private boolean playWhenReady;
    private boolean isVideoPlaying;
    private boolean isThumbnailPlaying;
    private int playbackState;
    private long currentPosition;
    private String videoURL;
    private String thumbnailURL;

    @Inject
    InstructionPresenter(@NonNull Context context) {
        this.context = context;
    }

    public void connectView(@Nullable InstructionContract.View view, @Nullable Bundle savedInstanceState, @Nullable Bundle extras, boolean inTabletMode) {
        this.view = view;
        this.inTabletMode = inTabletMode;

        currentPosition = C.TIME_UNSET;
        if (savedInstanceState != null) {
            recipe = savedInstanceState.getParcelable(RECIPE);
            step = savedInstanceState.getParcelable(STEP);
            videoURL = savedInstanceState.getString(VIDEO_URL);
            thumbnailURL = savedInstanceState.getString(THUMBNAIL_URL);
            isVideoPlaying = savedInstanceState.getBoolean(PLAY_VIDEO);
            isThumbnailPlaying = savedInstanceState.getBoolean(PLAY_THUMBNAIL);

            if (videoURL != null && isVideoPlaying) {
                initializePlayer(Uri.parse(videoURL));
            }

            if (thumbnailURL != null && isThumbnailPlaying) {
                initializePlayer(Uri.parse(thumbnailURL));
            }

            // To save the current position of the video I used some help from https://stackoverflow.com/questions/45481775/exoplayer-restore-state-when-resumed
            currentPosition = savedInstanceState.getLong(CURRENT_POSITION, C.TIME_UNSET);
            playWhenReady = savedInstanceState.getBoolean(PLAY_WHEN_READY);
            if (currentPosition != C.TIME_UNSET) {
                simpleExoPlayer.seekTo(currentPosition);
                simpleExoPlayer.setPlayWhenReady(playWhenReady);
            }

            init();
            return;
        }

        if (extras == null) {
            assert view != null;
            view.finishScreen();
            return;
        }

        recipe = extras.getParcelable(RECIPE);
        step = extras.getParcelable(STEP);

        init();
    }

    private void init() {
        if (view != null) {
            view.renderScreenTitle(recipe.getName());
        }

        configureInstructionDescriptions(step.getShortDescription(), step.getDescription());
        configureInstructionVideo(step.getVideoURL(), step.getThumbnailURL());

        if (inTabletMode) {
            if (view != null) {
                view.hideNextButton();
                view.hidePreviousButton();
            }
            return;
        }

        configureActionButtons();
    }

    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(RECIPE, recipe);
        outState.putParcelable(STEP, step);
        outState.putBoolean(PLAY_VIDEO, isVideoPlaying);
        outState.putBoolean(PLAY_THUMBNAIL, isThumbnailPlaying);
        outState.putString(VIDEO_URL, videoURL);
        outState.putString(THUMBNAIL_URL, thumbnailURL);
        outState.putBoolean(PLAY_WHEN_READY, playWhenReady);
        outState.putInt(PLAYBACK_STATE, playbackState);
        outState.putLong(CURRENT_POSITION, currentPosition);
    }

    public void onPause() {
        // As per the last reviewer comments, I added these checks in but they caused buggy behaviour on configuration change and restoring the current position.
        // After removing these checks the restoration of the current position worked again. Even though the releasePlayer() is being called in onPause()
        // and onStop(), after a lot of testing this is only triggered once. Thanks for the suggestion!
//        if (SDK_INT <= 23) {
            releasePlayer();
//        }
    }

    public void onStop() {
//        if (SDK_INT > 23) {
            releasePlayer();
//        }
    }

    public void onViewDestroyed() {
        view = null;

        if (mediaSession != null) {
            mediaSession.setActive(false);
        }
    }

    public void nextSelected() {
        List<Step> stepList = new ArrayList<>(recipe.getSteps());
        int currentIndex;

        if (stepList.isEmpty()) {
            return;
        }

        for (Step step : stepList) {
            if (this.step.getId() == step.getId()) {
                currentIndex = step.getId();
                currentIndex += 1;
                configurePreviousButton(stepList, currentIndex);

                if (stepList.get(currentIndex) != null && currentIndex < stepList.size()) {
                    configureInstructionDetails(stepList, currentIndex);

                    if (currentIndex == stepList.size() - 1) {
                        if (view != null) {
                            view.hideNextButton();
                        }
                    }

                    break;
                }
            }
        }
    }

    public void previousSelected() {
        List<Step> stepList = new ArrayList<>(recipe.getSteps());
        int currentIndex;

        if (stepList.isEmpty()) {
            return;
        }

        for (Step step : stepList) {
            if (this.step.getId() == step.getId()) {
                currentIndex = step.getId();
                configureNextButton(stepList, currentIndex);
                currentIndex -= 1;

                if (stepList.get(currentIndex) != null && currentIndex < stepList.size()) {
                    configureInstructionDetails(stepList, currentIndex);

                    if (currentIndex == stepList.get(0).getId()) {
                        if (view != null) {
                            view.hidePreviousButton();
                        }
                    }

                    break;
                }
            }
        }
    }

    public void initializeMediaSession() {
        mediaSession = new MediaSessionCompat(context, MEDIA_SESSION_TAG);
        mediaSession.setFlags(FLAG_HANDLES_MEDIA_BUTTONS | FLAG_HANDLES_TRANSPORT_CONTROLS);
        mediaSession.setMediaButtonReceiver(null);
        stateBuilder = new PlaybackStateCompat.Builder().setActions(PlaybackStateCompat.ACTION_PLAY |
                                                                    PlaybackStateCompat.ACTION_PAUSE |
                                                                    PlaybackStateCompat.ACTION_PLAY_PAUSE);
        mediaSession.setPlaybackState(stateBuilder.build());
        mediaSession.setCallback(new ExoPlayerMediaSessionCallback());
        mediaSession.setActive(true);
    }

    public void onPlayerStateChange(boolean playWhenReady, int playbackState) {
        this.playWhenReady = playWhenReady;
        this.playbackState = playbackState;

        if ((playbackState == STATE_READY) && playWhenReady) {
            stateBuilder.setState(STATE_PLAYING, simpleExoPlayer.getCurrentPosition(), 1f);
        } else if ((playbackState == STATE_READY)) {
            stateBuilder.setState(STATE_PAUSED, simpleExoPlayer.getCurrentPosition(), 1f);
        }

        mediaSession.setPlaybackState(stateBuilder.build());
    }

    public void onPlayerError(ExoPlaybackException error) {
        if (view != null) {

            if (error.getMessage() == null) {
                view.showPlayerError(context.getString(R.string.error_title), context.getString(R.string.error_no_message));
                return;
            }

            view.showPlayerError(context.getString(R.string.error_title), error.getMessage());
        }
    }

    private void configureInstructionDetails(List<Step> stepList, int currentIndex) {
        step = stepList.get(currentIndex);
        releasePlayer();
        configureInstructionDescriptions(step.getShortDescription(), step.getDescription());
        configureInstructionVideo(step.getVideoURL(), step.getThumbnailURL());
    }

    private void configureInstructionDescriptions(String shortDescription, String description) {
        if (shortDescription.equals(description)) {
            description = context.getString(R.string.instruction_first_step, recipe.getName());
        }

        if (view != null) {
            view.showRecipeInstructions(shortDescription, description);
        }
    }

    private void configureInstructionVideo(String videoURL, String thumbnailURL) {

        if (view != null) {
            if (!videoURL.isEmpty()) {
                this.videoURL = videoURL;
                isVideoPlaying = true;
                isThumbnailPlaying = false;
                view.showVideo();
                initializePlayer(Uri.parse(videoURL));
                return;
            }

            if (!thumbnailURL.isEmpty()) {
                // This checks to see if the thumbnail url is an .mp4, if so it should be displayed via the ExoPlayer.
                if (thumbnailURL.contains(".mp4")) {
                    this.thumbnailURL = thumbnailURL;
                    isVideoPlaying = false;
                    isThumbnailPlaying = true;
                    view.showVideo();
                    initializePlayer(Uri.parse(thumbnailURL));
                    return;
                }

                // This will show any non-movie thumbnail using Glide.
                view.showThumbnailImage(thumbnailURL);
                return;
            }

            view.hideVideo();
            releasePlayer();
        }
    }

    private void configureActionButtons() {
        if (step.getId() > 0) {
            if (view != null) {
                view.showPreviousButton();
            }
        }

        List<Step> steps = recipe.getSteps();
        if (step.getId() == steps.size() - 1) {
            if (view != null) {
                view.hideNextButton();
            }
        }
    }

    private void configureNextButton(List<Step> stepList, int currentIndex) {
        if (currentIndex == stepList.size() - 1) {
            if (view != null) {
                view.showNextButton();
            }
        }
    }

    private void configurePreviousButton(List<Step> stepList, int currentIndex) {
        int stepID = stepList.get(currentIndex).getId();
        if (stepID >= 1) {
            if (view != null) {
                view.showPreviousButton();
            }
        }

        if (stepID == 0) {
            if (view != null) {
                view.hidePreviousButton();
            }
        }
    }

    private void initializePlayer(Uri uri) {
        if (simpleExoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector, loadControl);

            if (view != null) {
                view.setPlayer(simpleExoPlayer);
            }

            String userAgent = Util.getUserAgent(context, context.getString(R.string.instruction_app_name));
            MediaSource mediaSource = new ExtractorMediaSource(uri,
                    new DefaultDataSourceFactory(context, userAgent),
                    new DefaultExtractorsFactory(), null, null);
            simpleExoPlayer.prepare(mediaSource);
            simpleExoPlayer.setPlayWhenReady(true);
        }
    }

    private void releasePlayer() {
        if (simpleExoPlayer != null) {
            currentPosition = simpleExoPlayer.getCurrentPosition();
            playWhenReady = simpleExoPlayer.getPlayWhenReady();
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
    }

    private class ExoPlayerMediaSessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            simpleExoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            simpleExoPlayer.setPlayWhenReady(false);
        }
    }
}
