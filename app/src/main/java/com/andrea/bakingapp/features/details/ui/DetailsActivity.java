package com.andrea.bakingapp.features.details.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.andrea.bakingapp.R;
import com.andrea.bakingapp.features.common.domain.Recipe;
import com.andrea.bakingapp.features.common.domain.Step;
import com.andrea.bakingapp.features.instruction.ui.InstructionActivity;
import com.andrea.bakingapp.features.instruction.ui.InstructionFragment;

import static com.andrea.bakingapp.features.common.ActivityConstants.RECIPE;
import static com.andrea.bakingapp.features.common.ActivityConstants.STEP;

public class DetailsActivity extends AppCompatActivity implements DetailsFragment.OnStepClickedListener {

    private boolean tabletMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        if (findViewById(R.id.recipeInstructionsDivider) != null) {
            tabletMode = true;
            return;
        }

        tabletMode = false;
    }

    @Override
    public void onStepClicked(@NonNull Step step, @NonNull Recipe recipe) {
        if (tabletMode) {
            FragmentManager fragmentManager = getSupportFragmentManager();

            InstructionFragment fragment = new InstructionFragment();
            Bundle args = new Bundle();
            args.putParcelable(RECIPE, recipe);
            args.putParcelable(STEP, step);
            fragment.setArguments(args);

            fragmentManager.beginTransaction()
                           .replace(R.id.recipeInstructions, fragment)
                           .commit();

            return;
        }

        Intent intent = new Intent(this, InstructionActivity.class);
        intent.putExtra(RECIPE, recipe);
        intent.putExtra(STEP, step);
        startActivity(intent);
    }

}
