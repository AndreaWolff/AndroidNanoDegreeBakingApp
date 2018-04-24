package com.andrea.bakingapp.features.common.domain;

import com.andrea.bakingapp.BaseUnitTest;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class StepTest extends BaseUnitTest {

    @Test
    public void step_getters() {
        // Setup
        Step step = new Step(1, "Recipe Instruction", "These are the recipe instructions.", "", "");

        // Verify
        assertThat(step.getId(), is(1));
        assertThat(step.getShortDescription(), is("Recipe Instruction"));
        assertThat(step.getDescription(), is("These are the recipe instructions."));
        assertThat(step.getVideoURL(), is(""));
        assertThat(step.getThumbnailURL(), is(""));
    }
}