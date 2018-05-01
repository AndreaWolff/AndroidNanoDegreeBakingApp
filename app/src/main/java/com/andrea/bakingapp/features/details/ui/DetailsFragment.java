package com.andrea.bakingapp.features.details.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.andrea.bakingapp.R;
import com.andrea.bakingapp.base.BaseFragment;
import com.andrea.bakingapp.dagger.component.DaggerDetailsComponent;
import com.andrea.bakingapp.features.common.domain.Ingredient;
import com.andrea.bakingapp.features.common.domain.Recipe;
import com.andrea.bakingapp.features.common.domain.Step;
import com.andrea.bakingapp.features.details.DetailsContract;
import com.andrea.bakingapp.features.details.logic.DetailsPresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.andrea.bakingapp.application.BakingApplication.getDagger;
import static com.andrea.bakingapp.util.DividerUtil.createRecyclerViewDivider;

public class DetailsFragment extends BaseFragment implements DetailsContract.View, StepAdapter.ListItemClickedListener {

    private OnStepClickedListener onStepClickedListener;

    public interface OnStepClickedListener {
        void onStepClicked(@NonNull Step step, @NonNull Recipe recipe);
    }

    @BindView(R.id.ingredientsRecyclerView) RecyclerView ingredientsRecyclerView;
    @BindView(R.id.stepsRecyclerView) RecyclerView stepsRecyclerView;

    @Inject
    DetailsPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);

        ButterKnife.bind(this, rootView);

        DaggerDetailsComponent.builder()
                              .appComponent(getDagger())
                              .build()
                              .inject(this);

        ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ingredientsRecyclerView.setHasFixedSize(true);
        ingredientsRecyclerView.setNestedScrollingEnabled(false);

        stepsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        stepsRecyclerView.setHasFixedSize(true);
        stepsRecyclerView.setNestedScrollingEnabled(false);
        stepsRecyclerView.addItemDecoration(createRecyclerViewDivider());

        presenter.connectView(this, savedInstanceState, getActivity().getIntent().getExtras());

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            onStepClickedListener = (OnStepClickedListener) context;
        } catch (ClassCastException e) {
            Toast.makeText(context, context + " must be implemented", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        presenter.onSavedInstanceState(outState);
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
    public void showIngredients(@NonNull List<Ingredient> ingredients) {
        IngredientAdapter adapter = new IngredientAdapter(ingredients);
        ingredientsRecyclerView.setAdapter(adapter);
    }

    @Override
    public void showSteps(@NonNull List<Step> steps, @NonNull Recipe recipe) {
        StepAdapter adapter = new StepAdapter(this, steps, recipe);
        stepsRecyclerView.setAdapter(adapter);
    }

    @Override
    public void finishScreen() {
        finishActivity();
    }
    // endregion

    @Override
    public void onListItemClicked(@NonNull Step step, @NonNull Recipe recipe) {
        onStepClickedListener.onStepClicked(step, recipe);
    }
}
