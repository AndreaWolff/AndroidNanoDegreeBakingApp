package com.andrea.bakingapp.features.instruction.logic;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.andrea.bakingapp.R;
import com.andrea.bakingapp.features.common.domain.Recipe;
import com.andrea.bakingapp.features.common.domain.Step;
import com.andrea.bakingapp.features.instruction.InstructionContract;
import com.google.android.exoplayer2.DefaultLoadControl;
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

import static com.andrea.bakingapp.features.common.ActivityConstants.RECIPE;
import static com.andrea.bakingapp.features.common.ActivityConstants.STEP;

public class InstructionPresenter {

    private final Context context;

    private InstructionContract.View view;
    private SimpleExoPlayer simpleExoPlayer;
    private Recipe recipe;
    private Step step;
    private boolean inTabletMode;

    @Inject
    InstructionPresenter(@NonNull Context context) {
        this.context = context;
    }

    public void connectView(@Nullable InstructionContract.View view, @Nullable Bundle savedInstanceState, @Nullable Bundle extras, boolean inTabletMode) {
        this.view = view;
        this.inTabletMode = inTabletMode;

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

    public void onViewDestroyed() {
        view = null;
        releasePlayer();
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

                if (stepList.get(currentIndex) != null && currentIndex < stepList.size()) {
                    configureInstructionDetails(stepList, currentIndex);

                    if (currentIndex == stepList.size() - 1) {
                        view.hideNextButton();
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

                if (currentIndex == stepList.size() -1) {
                    if (view != null) {
                        view.showNextButton();
                    }
                }

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

    private void configureInstructionDetails(List<Step> stepList, int currentIndex) {
        this.step = stepList.get(currentIndex);
        releasePlayer();
        configureInstructionDescriptions(this.step.getShortDescription(), this.step.getDescription());
        configureInstructionVideo(this.step.getVideoURL(), this.step.getThumbnailURL());
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
                view.showVideo();
                initializePlayer(Uri.parse(videoURL));
                return;
            }

            if (!thumbnailURL.isEmpty()) {
                view.showVideo();
                initializePlayer(Uri.parse(thumbnailURL));
                return;
            }

            view.hideVideo();
        }
    }

    private void configureActionButtons() {
        if (step.getId() > 0) {
            if (view != null) {
                view.showPreviousButton();
            }
        }

        List<Step> steps = recipe.getSteps();
        if (step.getId() == steps.size() -1) {
            if (view != null) {
                view.hideNextButton();
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
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
    }
}
