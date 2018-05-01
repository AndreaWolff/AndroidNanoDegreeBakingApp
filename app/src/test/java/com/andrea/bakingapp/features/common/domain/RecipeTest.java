package com.andrea.bakingapp.features.common.domain;

import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@SuppressWarnings("unchecked")
public class RecipeTest {

    @Test
    public void recipe_getters() {
        // Setup
        ArrayList ingredients = new ArrayList() {{
            add(new Ingredient(8, "CUPS", "Flour"));
            add(new Ingredient(7, "UNIT", "Eggs"));
        }};

        ArrayList steps = new ArrayList() {{
            add(new Step(1, "Recipe Instructions", "These are the recipe instructions.", "", ""));
        }};

        Recipe recipe = new Recipe(1, "Brownies", ingredients, steps, 8, "");

        // Verify
        assertThat(recipe.getId(), is(1));
        assertThat(recipe.getName(), is("Brownies"));
        assertThat(recipe.getIngredients().size(), is(2));
        assertThat(recipe.getSteps().size(), is(1));
        assertThat(recipe.getServings(), is(8));
        assertThat(recipe.getImage(), is(""));
    }
}