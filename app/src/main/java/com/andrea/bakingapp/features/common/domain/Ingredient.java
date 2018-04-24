package com.andrea.bakingapp.features.common.domain;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

public class Ingredient implements Parcelable {

    private float quantity;
    private String measure;
    private String ingredient;

    public Ingredient(float quantity,
                      @NonNull String measure,
                      @NonNull String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    public float getQuantity() {
        return this.quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    @NonNull public String getMeasure() {
        return measure;
    }

    public void setMeasure(@NonNull String measure) {
        this.measure = measure;
    }

    @NonNull public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(@NonNull String ingredient) {
        this.ingredient = ingredient;
    }

    protected Ingredient(Parcel in) {
        quantity = in.readFloat();
        measure = in.readString();
        ingredient = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(quantity);
        dest.writeString(measure);
        dest.writeString(ingredient);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
}