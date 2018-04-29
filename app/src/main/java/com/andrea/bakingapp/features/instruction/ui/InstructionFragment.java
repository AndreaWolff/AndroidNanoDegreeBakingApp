package com.andrea.bakingapp.features.instruction.ui;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import javax.inject.Inject;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.andrea.bakingapp.application.BakingApplication.getDagger;
import static com.andrea.bakingapp.features.common.ActivityConstants.STEP;

public class InstructionFragment extends BaseFragment implements InstructionContract.View {

    @Inject
    InstructionPresenter presenter;

    private SimpleExoPlayerView simpleExoPlayerView;
    private ImageView instructionImageView;
    private TextView instructionLabelTextView;
    private TextView instructionTextView;
    private Button nextButton;
    private Button previousButton;
    private boolean inTabletMode;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_instruction, container, false);

        DaggerInstructionComponent.builder()
                .appComponent(getDagger())
                .build()
                .inject(this);

        simpleExoPlayerView = rootView.findViewById(R.id.instructionVideo);
        instructionImageView = rootView.findViewById(R.id.instructionNoVideo);
        instructionLabelTextView = rootView.findViewById(R.id.instructionLabelTextView);
        instructionTextView = rootView.findViewById(R.id.instructionTextView);
        nextButton = rootView.findViewById(R.id.nextButton);
        previousButton = rootView.findViewById(R.id.previousButton);

        simpleExoPlayerView.setDefaultArtwork(BitmapFactory.decodeResource(getResources(), R.drawable.icon_no_video));

        nextButton.setOnClickListener(v -> presenter.nextSelected());
        previousButton.setOnClickListener(v -> presenter.previousSelected());

        if (getActivity().findViewById(R.id.recipeInstructionsDivider) != null) {
            inTabletMode = true;
        }

        presenter.connectView(this, savedInstanceState, getBundle(getActivity().getIntent().getExtras(), getArguments()), inTabletMode);

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
        instructionImageView.setVisibility(GONE);
    }

    @Override
    public void hideVideo() {
        simpleExoPlayerView.setVisibility(INVISIBLE);
        instructionImageView.setVisibility(VISIBLE);
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
    public void finishScreen() {
        finishActivity();
    }
    // endregion
}
