package com.amangarg.splitviewcontrollerdemo;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class DrawerFragment extends Fragment {
    private static final String TAG = DrawerFragment.class.getSimpleName();

    private static final String STATE_SELECTED_ITEM_POSITION = "selectedItemPosition";

    private final ArrayList<FragmentDrawerItem> mDrawerItems =
            new ArrayList<FragmentDrawerItem>() {{
                add(new FragmentDrawerItem("Home", SplitViewFragment.class, true));
                add(new FragmentDrawerItem("Home", SplitViewFragment.class, false));
                ;
            }};

    private ListView mListView;
    private DrawerListAdapter mListAdapter;
    private DrawerItemSelectionListener mDrawerItemSelectionListener;

    private int mSelectedItemPosition = AdapterView.INVALID_POSITION;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mListAdapter = new DrawerListAdapter(getActivity(), mDrawerItems);

        if (savedInstanceState != null) {
            mSelectedItemPosition = savedInstanceState
                    .getInt(STATE_SELECTED_ITEM_POSITION, AdapterView.INVALID_POSITION);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        mListView = (ListView) inflater.inflate(R.layout.fragment_drawer, container, false);
        mListView.setAdapter(mListAdapter);
        mListView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        mListView.setItemChecked(mSelectedItemPosition, true);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, final View view,
                                    final int position, final long id) {
                mListView.setItemChecked(position, true);

                if (mDrawerItemSelectionListener != null) {
                    mDrawerItemSelectionListener.onDrawerItemSelected(mDrawerItems.get(position));
                }
            }
        });

        return mListView;
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

        if (mSelectedItemPosition == AdapterView.INVALID_POSITION) {
            mListView.performItemClick(mListView, 0, 0);
        }
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(STATE_SELECTED_ITEM_POSITION, mListView.getCheckedItemPosition());
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

    public void setDrawerItemSelectionListener(final DrawerItemSelectionListener listener) {
        mDrawerItemSelectionListener = listener;
    }

    public interface DrawerItemSelectionListener {
        public void onDrawerItemSelected(final FragmentDrawerItem fragmentDrawerItem);
    }

    private class DrawerListAdapter extends ArrayAdapter<FragmentDrawerItem> {
        public DrawerListAdapter(final Context context, final List<FragmentDrawerItem> objects) {
            super(context, android.R.layout.simple_list_item_1, objects);
        }
    }

    public class FragmentDrawerItem {
        private final String mTitle;
        private final Class mFragmentClass;
        private final boolean mDefaultItem;

        public FragmentDrawerItem(final String title, final Class fragmentClass,
                                  final boolean isDefaultItem) {
            mTitle = title;
            mFragmentClass = fragmentClass;
            mDefaultItem = isDefaultItem;
        }

        public String getTitle() {
            return mTitle;
        }

        public Class getFragmentClass() {
            return mFragmentClass;
        }

        public boolean isDefaultItem() {
            return mDefaultItem;
        }
    }
}
