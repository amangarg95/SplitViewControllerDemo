package com.amangarg.splitviewcontrollerdemo;

import android.app.Fragment;
import android.os.Bundle;

abstract class SplitViewAbsFragment extends Fragment {
    private SplitViewController mController;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    public void setController(final SplitViewController controller) {
        mController = controller;
    }

    protected SplitViewController getController() {
        return mController;
    }

    protected abstract int getViewId();
}
