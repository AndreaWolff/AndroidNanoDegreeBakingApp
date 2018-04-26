package com.andrea.bakingapp.features.instruction.logic;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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

import javax.inject.Inject;

import static com.andrea.bakingapp.features.common.ActivityConstants.RECIPE;
import static com.andrea.bakingapp.features.common.ActivityConstants.STEP;

public class InstructionPresenter {

    private final Context context;

    private InstructionContract.View view;
    private SimpleExoPlayer simpleExoPlayer;
    private Recipe recipe;
    private Step step;

    @Inject
    InstructionPresenter(@NonNull Context context) {
        this.context = context;
    }

    public void connectView(@Nullable InstructionContract.View view, @Nullable Bundle savedInstanceState, @Nullable Bundle extras) {
        this.view = view;

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
            view.showRecipeInstructions(step.getShortDescription(), step.getDescription());

            String videoURL = step.getVideoURL();
            if (!videoURL.isEmpty()) {
                view.showVideo();
                initializePlayer(Uri.parse(videoURL));
                return;
            }

            String thumbnailURL = step.getThumbnailURL();
            if (!thumbnailURL.isEmpty()) {
                view.showVideo();
                initializePlayer(Uri.parse(thumbnailURL));
                return;
            }

            view.hideVideo();
        }
    }

    public void onViewDestroyed() {
        view = null;
        if (simpleExoPlayer != null) {
            releasePlayer();
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

            String userAgent = Util.getUserAgent(context, "bakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(uri,
                    new DefaultDataSourceFactory(context, userAgent),
                    new DefaultExtractorsFactory(), null, null);
            simpleExoPlayer.prepare(mediaSource);
            simpleExoPlayer.setPlayWhenReady(true);
        }
    }

    private void releasePlayer() {
        simpleExoPlayer.stop();
        simpleExoPlayer.release();
        simpleExoPlayer = null;
    }
}
