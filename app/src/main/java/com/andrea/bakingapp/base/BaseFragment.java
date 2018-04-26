package com.andrea.bakingapp.base;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

public class BaseFragment extends Fragment {

    public BaseFragment() {
    }

    protected void setTitle(@NonNull String title) {
        getActivity().setTitle(title);
    }

    protected void navigateToIntent(@NonNull Intent intent) {
        getActivity().startActivity(intent);
    }

    protected void finishActivity() {
        getActivity().finish();
    }

}
