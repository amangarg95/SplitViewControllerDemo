package com.amangarg.splitviewcontrollerdemo;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;


public class AboutActivity extends AppCompatActivity {

    // ================================================================================
    // Lifecycle
    // ================================================================================

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_about);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


    // ================================================================================
    // Options Menu
    // ================================================================================

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();

                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }
}