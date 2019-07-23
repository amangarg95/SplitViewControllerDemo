package com.amangarg.splitviewcontrollerdemo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MoreDetailsFragment extends SplitViewDetailFragment {
    private static final String TAG = MoreDetailsFragment.class.getSimpleName();

    private String mItemName;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bundle args = getArguments();

        if (args != null) {
            mItemName = args.getString(DetailFragment.ARGS_ITEM_NAME);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_moredetails, container, false);

        final TextView itemNameTextView =
                (TextView) view.findViewById(R.id.moreDetails_itemNameTextView);
        itemNameTextView.setText(mItemName);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        Log.d(TAG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d(TAG, "onResume");

        setTitle(mItemName + ": More Information");
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause");

        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d(TAG, "onStop");

        super.onStop();
    }
}