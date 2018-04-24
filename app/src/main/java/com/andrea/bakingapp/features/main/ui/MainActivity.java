package com.andrea.bakingapp.features.main.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.andrea.bakingapp.R;
import com.andrea.bakingapp.dagger.component.DaggerMainComponent;
import com.andrea.bakingapp.features.common.domain.Recipe;
import com.andrea.bakingapp.features.main.MainContract;
import com.andrea.bakingapp.features.main.logic.MainPresenter;

import java.util.List;

import javax.inject.Inject;

import static com.andrea.bakingapp.application.BakingApplication.getDagger;

public class MainActivity extends AppCompatActivity implements MainContract.View, RecipeAdapter.ListItemClickListener {

    private RecyclerView recipeRecyclerView;

    @Inject
    MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DaggerMainComponent.builder()
                           .appComponent(getDagger())
                           .build()
                           .inject(this);

        recipeRecyclerView = findViewById(R.id.recipeRecyclerView);
        recipeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recipeRecyclerView.setHasFixedSize(true);
        recipeRecyclerView.setNestedScrollingEnabled(false);

        presenter.connectView(this, savedInstanceState);
    }

    // region Adapter click listener
    @Override
    public void onListItemClicked(@NonNull Recipe recipe) {
        presenter.onRecipeSelected(recipe);
    }
    // endregion

    // region View methods
    @Override
    public void showRecipeList(@NonNull List<Recipe> recipes) {
        RecipeAdapter adapter = new RecipeAdapter(this, recipes);
        recipeRecyclerView.setAdapter(adapter);
    }

    @Override
    public void showError(@NonNull String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void renderScreenTitle(@NonNull String title) {
        setTitle(title);
    }

    @Override
    public void navigateToRecipeDetails(@NonNull Intent intent) {
        startActivity(intent);
    }
    // endregion
}
