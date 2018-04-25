package com.andrea.bakingapp.util;

import android.support.annotation.NonNull;

public class ConversionUtil {

    public static int convertToInteger(float quantity) {
        return (int)  quantity;
    }

    @NonNull public static String convertToMeasurementName(@NonNull String measure) {
        if ("G".equalsIgnoreCase(measure)) {
            return "GRAMS";
        }

        if ("K".equalsIgnoreCase(measure)) {
            return "KG";
        }

        return measure;
    }
}
