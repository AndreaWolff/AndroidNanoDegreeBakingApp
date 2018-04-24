package com.andrea.bakingapp.util;

import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.OrientationHelper;

import com.andrea.bakingapp.application.BakingApplication;

public class RecyclerViewDividerUtil {

    @NonNull public static DividerItemDecoration createRecyclerViewDivider() {
        return new DividerItemDecoration(BakingApplication.getDagger().getContext(), OrientationHelper.VERTICAL);
    }
}
