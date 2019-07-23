package com.amangarg.splitviewcontrollerdemo;

import android.app.Fragment;
import android.os.Bundle;

abstract class SplitViewAbsFragment extends Fragment {
    private SplitViewController mController;


    // ================================================================================
    // Fragment Lifecycle
    // ================================================================================

    @Override
    public void onCreate (final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }


    // ================================================================================
    // Split View Controller
    // ================================================================================

    public void setController (final SplitViewController controller) {
        mController = controller;
    }

    protected SplitViewController getController () {
        return mController;
    }


    // ================================================================================
    // Helpers
    // ================================================================================

    protected abstract int getViewId ();
}
