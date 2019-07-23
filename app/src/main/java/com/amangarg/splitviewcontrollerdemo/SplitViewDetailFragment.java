package com.amangarg.splitviewcontrollerdemo;

import android.app.FragmentTransaction;

public abstract class SplitViewDetailFragment extends SplitViewAbsFragment {

    // ================================================================================
    // Presentation
    // ================================================================================

    protected void pushDetailFragment (final SplitViewDetailFragment detailFragment) {
        pushDetailFragment(detailFragment, getFragmentManager().beginTransaction());
    }

    protected void pushDetailFragment (final SplitViewDetailFragment detailFragment,
                                       final FragmentTransaction transaction) {
        detailFragment.setController(getController());

        transaction.replace(getId(), detailFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    // ================================================================================
    // Helpers
    // ================================================================================

    protected void setTitle (final CharSequence title) {
        getController().setDetailViewTitle(title);
    }

    protected void setSubtitle (final CharSequence subtitle) {
        getController().setDetailViewSubtitle(subtitle);
    }

    @Override
    protected int getViewId () {
        return getController().getDetailFragmentContainerId();
    }
}