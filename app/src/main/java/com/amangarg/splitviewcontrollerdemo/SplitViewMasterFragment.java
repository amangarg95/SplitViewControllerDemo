package com.amangarg.splitviewcontrollerdemo;

import android.app.FragmentTransaction;

public abstract class SplitViewMasterFragment extends SplitViewAbsFragment {

    // ================================================================================
    // Presentation
    // ================================================================================

    protected void setDetailFragment(final SplitViewDetailFragment detailFragment) {
        getController().setDetailFragment(detailFragment);
    }

    protected void setDetailFragment(final SplitViewDetailFragment detailFragment, final
    FragmentTransaction transaction) {
        getController().setDetailFragment(detailFragment, transaction);
    }


    // ================================================================================
    // Helpers
    // ================================================================================

    @Override
    protected int getViewId() {
        return getController().getMasterFragmentContainerId();
    }
}