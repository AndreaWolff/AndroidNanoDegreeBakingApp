package com.andrea.bakingapp.features.details.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.andrea.bakingapp.R;
import com.andrea.bakingapp.dagger.component.DaggerDetailsComponent;
import com.andrea.bakingapp.features.common.domain.Ingredient;
import com.andrea.bakingapp.features.common.domain.Step;
import com.andrea.bakingapp.features.details.DetailsContract;
import com.andrea.bakingapp.features.details.logic.DetailsPresenter;

import java.util.List;

import javax.inject.Inject;

import static com.andrea.bakingapp.application.BakingApplication.getDagger;
import static com.andrea.bakingapp.util.RecyclerViewDividerUtil.createRecyclerViewDivider;

public class DetailsActivity extends AppCompatActivity implements DetailsContract.View, StepAdapter.ListItemClickedListener {

    private RecyclerView ingredientsRecyclerView;
    private RecyclerView stepsRecyclerView;

    @Inject
    DetailsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        DaggerDetailsComponent.builder()
                .appComponent(getDagger())
                .build()
                .inject(this);

        ingredientsRecyclerView = findViewById(R.id.ingredientsRecyclerView);
        ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ingredientsRecyclerView.setHasFixedSize(true);
        ingredientsRecyclerView.setNestedScrollingEnabled(false);

        stepsRecyclerView = findViewById(R.id.stepsRecyclerView);
        stepsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        stepsRecyclerView.setHasFixedSize(true);
        stepsRecyclerView.setNestedScrollingEnabled(false);
        stepsRecyclerView.addItemDecoration(createRecyclerViewDivider());

        presenter.connectView(this, savedInstanceState, getIntent().getExtras());
    }

    @Override
    public void onListItemClicked(@NonNull Step step) {
        presenter.onStepSelected(step);
    }

    // region View methods
    @Override
    public void renderScreenTitle(@NonNull String name) {
        setTitle(name);
    }

    @Override
    public void showIngredients(@NonNull List<Ingredient> ingredients) {
        IngredientsAdapter adapter = new IngredientsAdapter(ingredients);
        ingredientsRecyclerView.setAdapter(adapter);
    }

    @Override
    public void showSteps(@NonNull List<Step> steps) {
        StepAdapter adapter = new StepAdapter(this, steps);
        stepsRecyclerView.setAdapter(adapter);
    }

    @Override
    public void finishScreen() {
        finish();
    }

    @Override
    public void navigateToRecipeDetails(@NonNull Intent intent) {
        startActivity(intent);
    }
    // endregion
}
