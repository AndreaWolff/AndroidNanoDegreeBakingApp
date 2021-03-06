package com.andrea.bakingapp.features.instruction;

import android.support.annotation.NonNull;

import com.google.android.exoplayer2.SimpleExoPlayer;

public interface InstructionContract {

    interface View {

        void renderScreenTitle(@NonNull String title);

        void showRecipeInstructions(@NonNull String label, @NonNull String instruction);

        void showVideo();

        void hideVideo();

        void showThumbnailImage(@NonNull String thumbnailURL);

        void setPlayer(@NonNull SimpleExoPlayer simpleExoPlayer);

        void showNextButton();

        void hideNextButton();

        void showPreviousButton();

        void hidePreviousButton();

        void showPlayerError(@NonNull String errorTitle, @NonNull String errorMessage);

        void finishScreen();

    }

}
