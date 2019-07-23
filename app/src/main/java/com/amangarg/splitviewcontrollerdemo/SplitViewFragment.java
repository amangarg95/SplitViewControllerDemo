package com.amangarg.splitviewcontrollerdemo;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SplitViewFragment extends SplitViewController {
    private static final String TAG = SplitViewFragment.class.getSimpleName();

    private final FragmentManager.OnBackStackChangedListener mBackStackListener =
            new FragmentManager.OnBackStackChangedListener() {
                @Override
                public void onBackStackChanged() {
                    if (getFragmentManager().getBackStackEntryCount() == 0) {
                        setDetailViewTitle(getString(R.string.app_name));
                    }
                }
            };


    // ================================================================================
    // Lifecycle
    // ================================================================================

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_splitview, container, false);

        SplitViewMasterFragment masterFragment = (SplitViewMasterFragment) getFragmentManager()
                .findFragmentById(getMasterFragmentContainerId());

        if (masterFragment == null) {
            masterFragment = (SplitViewMasterFragment) Fragment
                    .instantiate(getActivity(), ListFragment.class.getName());

            setMasterFragment(masterFragment);
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        Log.d(TAG, "onStart");

        getFragmentManager().addOnBackStackChangedListener(mBackStackListener);
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d(TAG, "onResume");
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause");

        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d(TAG, "onStop");

        getFragmentManager().removeOnBackStackChangedListener(mBackStackListener);

        super.onStop();
    }


    // ================================================================================
    // Split View Controller
    // ================================================================================

    @Override
    public int getMasterFragmentContainerId() {
        return R.id.masterView;
    }

    @Override
    public int getDetailFragmentContainerId() {
        return R.id.detailView;
    }

    @Override
    public boolean isSplitViewLayout() {
        return getResources().getBoolean(R.bool.is_split_view_layout);
    }

    @Override
    public void setDetailViewTitle(final CharSequence title) {
        final ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle(isSplitViewLayout() ? getString(R.string.app_name) : title);
        }
    }

    @Override
    public void setDetailViewSubtitle(final CharSequence subtitle) {
        final ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        if (actionBar != null) {
            actionBar.setSubtitle(isSplitViewLayout() ? null : subtitle);
        }
    }


    // ================================================================================
    // Split View Navigation Listener
    // ================================================================================


    public boolean usesNavigationDrawer() {
        return true;
    }


    public void setNavigationDrawerEnabled(final boolean enabled) {
        final Activity activity = getActivity();

        if (activity instanceof MainActivity) {
            ((MainActivity) activity).setNavigationDrawerEnabled(enabled);
        }
    }


    public boolean shouldShowActionBarUpIndicator(final int detailItemCount) {
        return !isSplitViewLayout() && detailItemCount > 0;
    }

    public void onDetailItemCountChanged(final int detailItemCount) {
        final AppCompatActivity activity = (AppCompatActivity) getActivity();

        if (activity == null) {
            return;
        }

        final ActionBar actionBar = activity.getSupportActionBar();

        if (actionBar != null) {
            final boolean showUpIndicator = shouldShowActionBarUpIndicator(detailItemCount);
            final boolean usesNavDrawer = usesNavigationDrawer();

            actionBar.setDisplayHomeAsUpEnabled(showUpIndicator || usesNavDrawer);
            actionBar.setHomeButtonEnabled(showUpIndicator || usesNavDrawer);

            setNavigationDrawerEnabled(detailItemCount == 0 || isSplitViewLayout());
        }
    }
}
