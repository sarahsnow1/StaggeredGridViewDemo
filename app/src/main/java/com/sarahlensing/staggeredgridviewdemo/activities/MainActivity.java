package com.sarahlensing.staggeredgridviewdemo.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.sarahlensing.staggeredgridview.StaggeredGridView;
import com.sarahlensing.staggeredgridviewdemo.R;
import com.sarahlensing.staggeredgridviewdemo.fragments.BasicFragment;
import com.sarahlensing.staggeredgridviewdemo.fragments.TwoDFragment;


public class MainActivity extends ActionBarActivity {

    Fragment mCurrentFragment;

    private void createBasicFragment() {
        createBasicFragment(StaggeredGridView.STAGGERED_GRID_DEFAULT_ORIENTATION);
    }

    private void createBasicFragment(String initOrientation) {
        mCurrentFragment = new BasicFragment(initOrientation);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(null);
        setContentView(R.layout.activity_main);

        createBasicFragment();
        makeCurrentFragmentVisible();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_horizontal:
            case R.id.action_vertical:
                if (ensureBasicFragmentVisible((item.getItemId() == R.id.action_horizontal) ? StaggeredGridView.STAGGERED_GRID_ORIENTATION_HORIZONTAL:StaggeredGridView.STAGGERED_GRID_ORIENTATION_VERTICAL)) {
                    return mCurrentFragment.onOptionsItemSelected(item);
                }
            case R.id.action_both:
                ensure2DFragmentVisible();
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean ensureBasicFragmentVisible(String initOrientation) {
        if ((mCurrentFragment.getClass().isAssignableFrom(BasicFragment.class))) {
            return true;
        }
        createBasicFragment(initOrientation);
        makeCurrentFragmentVisible();
        return false;
    }

    private boolean ensureBasicFragmentVisible() {
        return ensureBasicFragmentVisible(StaggeredGridView.STAGGERED_GRID_DEFAULT_ORIENTATION);
    }

    private boolean ensure2DFragmentVisible() {
        if ((mCurrentFragment instanceof TwoDFragment)) {
            return true;
        }
        create2DScrollFragment();
        makeCurrentFragmentVisible();
        return false;
    }

    private void create2DScrollFragment() {
        mCurrentFragment = new TwoDFragment();
    }

    private void makeCurrentFragmentVisible() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, mCurrentFragment)
                .commit();
    }
}
