package com.andrea.bakingapp.features.instruction.ui;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.andrea.bakingapp.R;
import com.andrea.bakingapp.base.BaseFragment;
import com.andrea.bakingapp.dagger.component.DaggerInstructionComponent;
import com.andrea.bakingapp.features.instruction.InstructionContract;
import com.andrea.bakingapp.features.instruction.logic.InstructionPresenter;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.andrea.bakingapp.application.BakingApplication.getDagger;
import static com.andrea.bakingapp.features.common.ActivityConstants.STEP;

public class InstructionFragment extends BaseFragment implements InstructionContract.View, ExoPlayer.EventListener {

    @Inject
    InstructionPresenter presenter;

    @BindView(R.id.instructionVideo) SimpleExoPlayerView simpleExoPlayerView;
    @BindView(R.id.instructionNoVideo) ImageView instructionNoImageView;
    @BindView(R.id.instructionLabelTextView) TextView instructionLabelTextView;
    @BindView(R.id.instructionTextView) TextView instructionTextView;
    @BindView(R.id.nextButton) Button nextButton;
    @BindView(R.id.previousButton) Button previousButton;
    private boolean inTabletMode;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_instruction, container, false);

        ButterKnife.bind(this, rootView);

        DaggerInstructionComponent.builder()
                .appComponent(getDagger())
                .build()
                .inject(this);

        simpleExoPlayerView.setDefaultArtwork(BitmapFactory.decodeResource(getResources(), R.drawable.icon_no_video));

        if (getActivity().findViewById(R.id.recipeInstructionsDivider) != null) {
            inTabletMode = true;
        }

        presenter.connectView(this, savedInstanceState, getBundle(getActivity().getIntent().getExtras(), getArguments()), inTabletMode);
        presenter.initializeMediaSession();

        return rootView;
    }

    private Bundle getBundle(Bundle extras, Bundle arguments) {
        Bundle bundle = new Bundle();

        if (extras != null && extras.get(STEP) != null) {
            bundle = extras;
        } else if (arguments != null) {
            bundle = arguments;
        }

        return bundle;
    }

    @OnClick(R.id.nextButton)
    public void onNextSelected() {
        presenter.nextSelected();
    }

    @OnClick(R.id.previousButton)
    public void onPreviousSelected() {
        presenter.previousSelected();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        presenter.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onViewDestroyed();
    }

    // region View methods
    @Override
    public void renderScreenTitle(@NonNull String title) {
        setTitle(title);
    }

    @Override
    public void showRecipeInstructions(@NonNull String label, @NonNull String instruction) {
        instructionLabelTextView.setText(label);
        instructionTextView.setText(instruction);
    }

    @Override
    public void showVideo() {
        simpleExoPlayerView.setVisibility(VISIBLE);
        instructionNoImageView.setVisibility(GONE);
    }

    @Override
    public void hideVideo() {
        simpleExoPlayerView.setVisibility(INVISIBLE);
        instructionNoImageView.setVisibility(VISIBLE);
    }

    @Override
    public void setPlayer(@NonNull SimpleExoPlayer simpleExoPlayer) {
        simpleExoPlayerView.setPlayer(simpleExoPlayer);
    }

    @Override
    public void showNextButton() {
        nextButton.setVisibility(VISIBLE);
    }

    @Override
    public void hideNextButton() {
        nextButton.setVisibility(GONE);
    }

    @Override
    public void showPreviousButton() {
        previousButton.setVisibility(VISIBLE);
    }

    @Override
    public void hidePreviousButton() {
        previousButton.setVisibility(GONE);
    }

    @Override
    public void showPlayerError(@NonNull String errorTitle, @NonNull String errorMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(errorTitle)
                .setMessage(errorMessage)
                .setPositiveButton(android.R.string.ok, (dialogInterface, i) -> {
                    // do nothing
                });
        builder.create();
        builder.show();
    }

    @Override
    public void finishScreen() {
        finishActivity();
    }
    // endregion

    // region ExoPlayer Media Session methods
    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        presenter.onPlayerStateChange(playWhenReady, playbackState);
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {
        presenter.onPlayerError(error);
    }

    @Override
    public void onPositionDiscontinuity() {

    }
    // endregion
}
