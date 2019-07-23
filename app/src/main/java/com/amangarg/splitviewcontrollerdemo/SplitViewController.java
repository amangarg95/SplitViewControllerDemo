package com.amangarg.splitviewcontrollerdemo;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import java.util.ArrayList;

public abstract class SplitViewController extends Fragment implements SplitViewNavigationListener {
    private final FragmentManager.OnBackStackChangedListener mBackStackListener =
            new FragmentManager.OnBackStackChangedListener() {
                @Override
                public void onBackStackChanged() {
                    configureChildFragments();
                }
            };

    private final ArrayList<OnDetailViewChangedListener> mDetailViewChangedListeners =
            new ArrayList<OnDetailViewChangedListener>();

    private SplitViewMasterFragment mMasterFragment;
    private SplitViewDetailFragment mDetailFragment;

    private boolean mNotifyDetailViewListeners = true;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    @Override
    public void onStart() {
        super.onStart();

        configureChildFragments();

        getFragmentManager().addOnBackStackChangedListener(mBackStackListener);
    }

    @Override
    public void onStop() {
        final FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.removeOnBackStackChangedListener(mBackStackListener);

        if (mMasterFragment != null || mDetailFragment != null) {
            final FragmentTransaction transaction = fragmentManager.beginTransaction();

            if (mMasterFragment != null && !mMasterFragment.isDetached()) {
                transaction.detach(mMasterFragment);
            }

            if (mDetailFragment != null && !mDetailFragment.isDetached()) {
                transaction.detach(mDetailFragment);
            }

            transaction.commitAllowingStateLoss();
        }

        super.onStop();
    }

    private void configureChildFragments() {
        if (mMasterFragment == null) {
            throw new IllegalStateException("Master view Fragment could not be found.");
        }

        mMasterFragment.setController(this);

        final FragmentManager fragmentManager = getFragmentManager();

        final SplitViewDetailFragment detailFragment = (SplitViewDetailFragment) fragmentManager
                .findFragmentById(getDetailFragmentContainerId());

        mDetailFragment =
                (detailFragment != null && detailFragment.isRemoving()) ? null : detailFragment;

        if (mDetailFragment != null) {
            mDetailFragment.setController(this);
        }

        final FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (isSplitViewLayout()) {
            transaction.attach(mMasterFragment);

            if (mDetailFragment != null) {
                transaction.attach(mDetailFragment);
            }
        } else if (mDetailFragment != null) {
            transaction.attach(mDetailFragment);
        } else {
            transaction.attach(mMasterFragment);
        }

        transaction.commit();

        notifyDetailViewChangedListeners();
    }


    public abstract int getMasterFragmentContainerId();

    public void setMasterFragment(final SplitViewMasterFragment masterFragment) {
        masterFragment.setController(this);

        final FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(getMasterFragmentContainerId(), masterFragment,
                masterFragment.getClass().getSimpleName());
        transaction.commit();

        mMasterFragment = masterFragment;
    }

    public abstract int getDetailFragmentContainerId();

    public void setDetailFragment(final SplitViewDetailFragment detailFragment) {
        setDetailFragment(detailFragment, getFragmentManager().beginTransaction());
    }

    public void setDetailFragment(final SplitViewDetailFragment detailFragment,
                                  final FragmentTransaction transaction) {
        final FragmentManager fragmentManager = getFragmentManager();

        if (detailFragment == null) {
            fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

            return;
        }

        detailFragment.setController(this);

        if (fragmentManager.getBackStackEntryCount() > 0) {
            mNotifyDetailViewListeners = false;
            fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            mNotifyDetailViewListeners = true;
        }

        transaction.replace(getDetailFragmentContainerId(), detailFragment);
        transaction.addToBackStack(null);

        if (!isSplitViewLayout() && !mMasterFragment.isDetached()) {
            transaction.detach(mMasterFragment);
        }

        transaction.commit();

        mDetailFragment = detailFragment;
    }

    public void setDetailViewTitle(final CharSequence title) {
    }

    public void setDetailViewSubtitle(final CharSequence subtitle) {
    }

    private void notifyDetailViewChangedListeners() {
        if (mNotifyDetailViewListeners) {
            for (final OnDetailViewChangedListener listener : mDetailViewChangedListeners) {
                listener.onDetailViewChanged(mDetailFragment);
            }
        }
    }

    public abstract boolean isSplitViewLayout();

    public void addOnDetailViewChangedListener(final OnDetailViewChangedListener listener) {
        if (!mDetailViewChangedListeners.contains(listener)) {
            mDetailViewChangedListeners.add(listener);
        }
    }

    public void removeOnDetailViewChangedListener(final OnDetailViewChangedListener listener) {
        mDetailViewChangedListeners.remove(listener);
    }

    public interface OnDetailViewChangedListener {
        public void onDetailViewChanged(final SplitViewDetailFragment detailViewFragment);
    }
}