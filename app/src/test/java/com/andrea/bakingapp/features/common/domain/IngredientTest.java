package com.andrea.bakingapp.features.common.domain;

import com.andrea.bakingapp.BaseUnitTest;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class IngredientTest extends BaseUnitTest {

    @Test
    public void ingredient_getters() {
        // Setup
        Ingredient ingredient = new Ingredient(9, "CUPS", "Flour");

        // Verify
        assertThat(ingredient.getQuantity(), is(9.0F));
        assertThat(ingredient.getMeasure(), is("CUPS"));
        assertThat(ingredient.getIngredient(), is("Flour"));
    }
}