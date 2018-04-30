package com.andrea.bakingapp.util;

import com.andrea.bakingapp.BaseUnitTest;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ConversionUtilTest extends BaseUnitTest {

    @Test
    public void convertToInteger() {
        // Setup
        float quantity = 9.0f;

        // Run
        int result = ConversionUtil.convertToInteger(quantity);

        // Verify
        assertThat(result, is(9));
    }

    @Test
    public void convertToMeasurement_returnGRAMS() {
        // Setup
        String grams = "G";

        // Run
        String result = ConversionUtil.convertToMeasurementName(grams);

        // Verify
        assertThat(result, is("GRAMS"));
    }

    @Test
    public void convertToMeasurement_returnKG() {
        // Setup
        String kg = "K";

        // Run
        String result = ConversionUtil.convertToMeasurementName(kg);

        // Verify
        assertThat(result, is("KG"));
    }

    @Test
    public void convertToMeasurementName() {
        // Setup
        String measure = "CUPS";

        // Run
        String result = ConversionUtil.convertToMeasurementName(measure);

        // Verify
        assertThat(result, is("CUPS"));
    }
}