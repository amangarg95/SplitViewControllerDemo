package com.amangarg.splitviewcontrollerdemo;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class DetailFragment extends SplitViewDetailFragment {
    private static final String TAG = DetailFragment.class.getSimpleName();

    public static final String ARGS_ITEM_NAME = "itemName";

    private String mItemName;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bundle args = getArguments();

        if (args != null) {
            mItemName = args.getString(ARGS_ITEM_NAME);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_details, container, false);

        final TextView itemNameTextView =
                (TextView) view.findViewById(R.id.detailView_itemNameTextView);
        itemNameTextView.setText(mItemName);

        final Button moreDetailsButton =
                (Button) view.findViewById(R.id.detailView_moreDetailsButton);
        moreDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final Bundle args = new Bundle();
                args.putString(ARGS_ITEM_NAME, mItemName);

                final MoreDetailsFragment moreDetailsFragment = (MoreDetailsFragment) Fragment
                        .instantiate(getActivity(), MoreDetailsFragment.class.getName(), args);

                pushDetailFragment(moreDetailsFragment);
            }
        });

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

        setTitle(mItemName);
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