package com.andrea.bakingapp.features.common.repository;

import android.support.annotation.NonNull;

import com.andrea.bakingapp.features.common.domain.Step;
import com.google.gson.annotations.SerializedName;

class StepDto {

    @SerializedName("id") private int id;
    @SerializedName("shortDescription") private String shortDescription;
    @SerializedName("description") private String description;
    @SerializedName("videoURL") private String videoURL;
    @SerializedName("thumbnailURL") private String thumbnailURL;

    @NonNull Step toStep() {
        return new Step(id,
                        shortDescription,
                        description,
                        videoURL,
                        thumbnailURL);
    }

}
