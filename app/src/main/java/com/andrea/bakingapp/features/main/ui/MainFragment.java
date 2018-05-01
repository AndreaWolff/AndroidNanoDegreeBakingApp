package com.andrea.bakingapp.features.main.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.andrea.bakingapp.R;
import com.andrea.bakingapp.base.BaseFragment;
import com.andrea.bakingapp.dagger.component.DaggerMainComponent;
import com.andrea.bakingapp.databinding.FragmentMainBinding;
import com.andrea.bakingapp.features.common.domain.Recipe;
import com.andrea.bakingapp.features.main.MainContract;
import com.andrea.bakingapp.features.main.logic.MainPresenter;

import java.util.List;

import javax.inject.Inject;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.andrea.bakingapp.application.BakingApplication.getDagger;

public class MainFragment extends BaseFragment implements MainContract.View, RecipeAdapter.ListItemClickListener {

    private FragmentMainBinding binding;

    @Inject
    MainPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        binding = DataBindingUtil.setContentView(getActivity(), R.layout.fragment_main);

        DaggerMainComponent.builder()
                           .appComponent(getDagger())
                           .build()
                           .inject(this);

        binding.recipeRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), this.getResources().getInteger(R.integer.grid_column)));
        binding.recipeRecyclerView.setHasFixedSize(true);
        binding.recipeRecyclerView.setNestedScrollingEnabled(false);

        presenter.connectView(this);

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onViewDestroyed();
    }

    // region View methods
    @Override
    public void showRecipeList(@NonNull List<Recipe> recipes) {
        RecipeAdapter adapter = new RecipeAdapter(this, recipes);
        binding.recipeRecyclerView.setAdapter(adapter);
    }

    @Override
    public void showError(@NonNull String errorTitle, @NonNull String errorMessage) {
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
    public void renderScreenTitle(@NonNull String title) {
        setTitle(title);
    }

    @Override
    public void navigateToRecipeDetails(@NonNull Intent intent) {
        navigateToIntent(intent);
    }

    @Override
    public void showLoadingIndicator() {
        binding.recipeLoadingIndicator.setVisibility(VISIBLE);
    }

    @Override
    public void hideLoadingIndicator() {
        binding.recipeLoadingIndicator.setVisibility(GONE);
    }
    // endregion

    @Override
    public void onListItemClicked(@NonNull Recipe recipe) {
        presenter.onRecipeSelected(recipe);
    }
}
