package com.andrea.bakingapp.features.instruction.ui;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.andrea.bakingapp.R;
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

public class InstructionActivity extends AppCompatActivity implements InstructionContract.View {

    @Inject
    InstructionPresenter presenter;

    private SimpleExoPlayerView simpleExoPlayerView;
    private ImageView instructionImageView;
    private TextView instructionLabelTextView;
    private TextView instructionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);

        DaggerInstructionComponent.builder()
                                  .appComponent(getDagger())
                                  .build()
                                  .inject(this);

        simpleExoPlayerView = findViewById(R.id.instructionVideo);
        instructionImageView = findViewById(R.id.instructionNoVideo);
        instructionLabelTextView = findViewById(R.id.instructionLabelTextView);
        instructionTextView = findViewById(R.id.instructionTextView);

        simpleExoPlayerView.setDefaultArtwork(BitmapFactory.decodeResource(getResources(), R.drawable.icon_no_video));

        presenter.connectView(this, savedInstanceState, getIntent().getExtras());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onViewDestroyed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (android.R.id.home == item.getItemId()) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void renderScreenTitle(@NonNull String name) {
        setTitle(name);
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
    public void finishScreen() {
        finish();
    }
}
