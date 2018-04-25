package com.andrea.bakingapp.features.details.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andrea.bakingapp.R;
import com.andrea.bakingapp.base.BaseFragment;
import com.andrea.bakingapp.dagger.component.DaggerDetailsComponent;
import com.andrea.bakingapp.features.common.domain.Ingredient;
import com.andrea.bakingapp.features.common.domain.Step;
import com.andrea.bakingapp.features.details.DetailsContract;
import com.andrea.bakingapp.features.details.logic.DetailsPresenter;

import java.util.List;

import javax.inject.Inject;

import static com.andrea.bakingapp.application.BakingApplication.getDagger;
import static com.andrea.bakingapp.util.DividerUtil.createRecyclerViewDivider;

public class DetailsFragment extends BaseFragment implements DetailsContract.View, StepAdapter.ListItemClickedListener {

    private RecyclerView ingredientsRecyclerView;
    private RecyclerView stepsRecyclerView;

    @Inject
    DetailsPresenter presenter;

    public DetailsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);

        DaggerDetailsComponent.builder()
                              .appComponent(getDagger())
                              .build()
                              .inject(this);

        ingredientsRecyclerView = rootView.findViewById(R.id.ingredientsRecyclerView);
        ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ingredientsRecyclerView.setHasFixedSize(true);
        ingredientsRecyclerView.setNestedScrollingEnabled(false);

        stepsRecyclerView = rootView.findViewById(R.id.stepsRecyclerView);
        stepsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        stepsRecyclerView.setHasFixedSize(true);
        stepsRecyclerView.setNestedScrollingEnabled(false);
        stepsRecyclerView.addItemDecoration(createRecyclerViewDivider());

        presenter.connectView(this, savedInstanceState, getActivity().getIntent().getExtras());

        return rootView;
    }

    // region View methods
    @Override
    public void renderScreenTitle(@NonNull String title) {
        setTitle(title);
    }

    @Override
    public void showIngredients(@NonNull List<Ingredient> ingredients) {
        IngredientAdapter adapter = new IngredientAdapter(ingredients);
        ingredientsRecyclerView.setAdapter(adapter);
    }

    @Override
    public void showSteps(@NonNull List<Step> steps) {
        StepAdapter adapter = new StepAdapter(this, steps);
        stepsRecyclerView.setAdapter(adapter);
    }

    @Override
    public void finishScreen() {
        finishActivity();
    }

    @Override
    public void navigateToRecipeDetails(@NonNull Intent intent) {
        navigateToIntent(intent);
    }
    // endregion

    @Override
    public void onListItemClicked(@NonNull Step step) {
        presenter.onStepSelected(step);
    }
}
