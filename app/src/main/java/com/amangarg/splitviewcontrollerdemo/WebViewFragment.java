package com.amangarg.splitviewcontrollerdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class WebViewFragment extends Fragment {
    private static final String TAG = WebViewFragment.class.getSimpleName();

    private static final String GITHUB_URL = "https://github.com/bdbergeron";


    // ================================================================================
    // Lifecycle
    // ================================================================================

    @Override
    public View onCreateView (final LayoutInflater inflater, final ViewGroup container,
                              final Bundle savedInstanceState) {
        final WebView webView =
                (WebView) inflater.inflate(R.layout.fragment_webview, container, false);
        webView.loadUrl(GITHUB_URL);

        return webView;
    }

    @Override
    public void onStart () {
        super.onStart();

        Log.d(TAG, "onStart");
    }

    @Override
    public void onResume () {
        super.onResume();

        Log.d(TAG, "onResume");
    }

    @Override
    public void onPause () {
        Log.d(TAG, "onPause");

        super.onPause();
    }

    @Override
    public void onStop () {
        Log.d(TAG, "onStop");

        super.onStop();
    }
}
