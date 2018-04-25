package com.andrea.bakingapp.util;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.andrea.bakingapp.R;
import com.bumptech.glide.Glide;

public class GlideUtil {

    public static void displayImage(@NonNull String photo, @NonNull ImageView imageView) {
        Glide.with(imageView.getContext())
                .load(photo)
                .placeholder(R.drawable.icon_photo)
                .into(imageView);
    }
}
