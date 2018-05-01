package com.andrea.bakingapp.features.common.repository;

import com.andrea.bakingapp.BaseUnitTest;

import org.junit.Test;

import java.lang.reflect.Method;

import retrofit2.http.GET;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class RecipeDaoTest extends BaseUnitTest {

    @Test
    public void dao_getRecipes_verifyAnnotations() throws Exception {
        // Setup
        Method method = RecipeDao.class.getDeclaredMethod("getRecipe");

        // Verify
        assertThat(method.isAnnotationPresent(GET.class), is(true));
        assertThat(method.getAnnotation(GET.class).value(), is("baking.json"));
    }

}