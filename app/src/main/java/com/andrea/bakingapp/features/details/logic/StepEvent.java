package com.andrea.bakingapp.features.details.logic;

import android.support.annotation.NonNull;

import com.andrea.bakingapp.features.common.domain.Step;

public class StepEvent {

    private Step step;

    public StepEvent(@NonNull Step step) {
        this.step = step;
    }

    @NonNull public Step getStep() {
        return step;
    }
}
