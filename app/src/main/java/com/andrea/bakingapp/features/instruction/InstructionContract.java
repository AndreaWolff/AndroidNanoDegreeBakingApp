package com.andrea.bakingapp.features.instruction;

import android.support.annotation.NonNull;

public interface InstructionContract {
    interface View {

        void renderScreenTitle(@NonNull String name);

        void showRecipeInstructions(@NonNull String label, @NonNull String instruction);

        void finish();
    }
}
