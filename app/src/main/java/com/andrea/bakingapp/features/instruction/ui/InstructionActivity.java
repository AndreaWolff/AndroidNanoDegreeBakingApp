package com.andrea.bakingapp.features.instruction.ui;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.andrea.bakingapp.R;
import com.andrea.bakingapp.dagger.component.DaggerInstructionComponent;
import com.andrea.bakingapp.features.instruction.InstructionContract;
import com.andrea.bakingapp.features.instruction.logic.InstructionPresenter;

import javax.inject.Inject;

import static com.andrea.bakingapp.application.BakingApplication.getDagger;

public class InstructionActivity extends AppCompatActivity implements InstructionContract.View {

    @Inject
    InstructionPresenter presenter;

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

        instructionLabelTextView = findViewById(R.id.instructionLabelTextView);
        instructionTextView = findViewById(R.id.instructionTextView);

        presenter.connectView(this, savedInstanceState, getIntent().getExtras());
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
}
