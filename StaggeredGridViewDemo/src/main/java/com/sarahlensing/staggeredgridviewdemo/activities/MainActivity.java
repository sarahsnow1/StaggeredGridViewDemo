package com.sarahlensing.staggeredgridviewdemo.activities;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.samsung.staggeredgridview.ItemSize;
import com.samsung.staggeredgridview.StaggeredGridAdapter;
import com.samsung.staggeredgridview.StaggeredGridView;
import com.sarahlensing.staggeredgridviewdemo.R;
import com.sarahlensing.staggeredgridviewdemo.views.GridItemView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        StaggeredGridView mGridView;
        ArrayList<ItemSize> mItemSizes;
        Random random;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            mGridView = (StaggeredGridView)rootView.findViewById(R.id.gridView);
            return rootView;
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            random = new Random();
            buildGridSizes();
            mGridView.setAdapter(mGridAdapter);
            super.onViewCreated(view, savedInstanceState);
        }

        private void buildGridSizes() {
            Random random = new Random();
            mItemSizes = new ArrayList<ItemSize>();
            for (int i = 0; i < 200; i++) {
                int width = (random.nextInt(3)+1) * 200;
                int height = (random.nextInt(3)+1) * 200;
                mItemSizes.add(new ItemSize(width, height));
            }
        }

        private int nextRandomColor() {
            return Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
        }

        StaggeredGridAdapter mGridAdapter = new StaggeredGridAdapter() {
            @Override
            public ItemSize getItemSize(int position) {
                return mItemSizes.get(position);
            }

            @Override
            public int getCount() {
                return mItemSizes.size();
            }

            @Override
            public Object getItem(int position) {
                return position;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder holder;
                if (convertView == null) {
                    LayoutInflater layoutInflator = LayoutInflater.from(getActivity().getBaseContext());
                    convertView = layoutInflator.inflate(R.layout.grid_item_view, null);
                    holder = new ViewHolder();
                    holder.gridItemView = (GridItemView) convertView;
                    holder.titleView = (TextView) convertView.findViewById(R.id.titleView);
                    holder.subtitleView = (TextView) convertView.findViewById(R.id.subtitleView);
                    convertView.setTag(holder);
                }
                else {
                    holder = (ViewHolder) convertView.getTag();
                }
                ItemSize itemSize = mItemSizes.get(position);
                holder.gridItemView.itemSize = itemSize;
                holder.gridItemView.setBackgroundColor(nextRandomColor());
                holder.titleView.setText(String.valueOf(position));
                holder.subtitleView.setText(String.valueOf(itemSize.width) + "x" + String.valueOf(itemSize.height));
                return convertView;
            }
        };

        public class ViewHolder {
            GridItemView gridItemView;
            TextView titleView;
            TextView subtitleView;
        }
    }

}
