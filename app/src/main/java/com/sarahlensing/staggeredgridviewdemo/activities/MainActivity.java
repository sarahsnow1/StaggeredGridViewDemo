package com.sarahlensing.staggeredgridviewdemo.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.samsung.staggeredgridview.ItemSize;
import com.samsung.staggeredgridview.StaggeredGridAdapter;
import com.samsung.staggeredgridview.StaggeredGridSectionAdapter;
import com.samsung.staggeredgridview.StaggeredGridView;
import com.sarahlensing.staggeredgridviewdemo.R;
import com.sarahlensing.staggeredgridviewdemo.views.GridItemView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends ActionBarActivity {

    PlaceholderFragment mPlaceholderFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(null);
        setContentView(R.layout.activity_main);

        mPlaceholderFragment = new PlaceholderFragment();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, mPlaceholderFragment)
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
        return mPlaceholderFragment.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        StaggeredGridView mGridView;
        ArrayList<ItemSize> mItemSizes;
        ArrayList<ItemSize> mSectionSizes;
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
//            mGridView.setAdapter(mGridAdapter);
            mGridView.setAdapter(mGridSectionAdapter);
            super.onViewCreated(view, savedInstanceState);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == R.id.action_horizontal) {
                buildGridSizes(StaggeredGridView.STAGGERED_GRID_ORIENTATION_HORIZONTAL);
                mGridView.setGridOrientation(StaggeredGridView.STAGGERED_GRID_ORIENTATION_HORIZONTAL);
                return true;
            }
            else if (id == R.id.action_vertical) {
                buildGridSizes(StaggeredGridView.STAGGERED_GRID_ORIENTATION_VERTICAL);
                mGridView.setGridOrientation(StaggeredGridView.STAGGERED_GRID_ORIENTATION_VERTICAL);
                return true;
            }
            return super.onOptionsItemSelected(item);
        }

        private void buildGridSizes() {
            buildGridSizes(mGridView.getGridOrientation());
        }

        private void buildGridSizes(String orientation) {
            if (mItemSizes != null) {
                mItemSizes.clear();
            }
            if (mSectionSizes != null) {
                mSectionSizes.clear();
            }

            Random random = new Random();
            mItemSizes = new ArrayList<ItemSize>();
            for (int i = 0; i < 200; i++) {
                int width = (random.nextInt(2)+1) * 200;
                int height = (random.nextInt(1)+1) * 200;
                mItemSizes.add(new ItemSize(width, height));
            }

            mSectionSizes = new ArrayList<ItemSize>();
            for (int i = 0; i < 10; i++) {
                if (orientation.equals(StaggeredGridView.STAGGERED_GRID_ORIENTATION_HORIZONTAL)) {
                    mSectionSizes.add(new ItemSize(200, getActivity().getWindowManager().getDefaultDisplay().getHeight() - 250));
                }
                else {
                    mSectionSizes.add(new ItemSize(getActivity().getWindowManager().getDefaultDisplay().getWidth() - 250, 200));
                }
            }
        }

        private int nextRandomColor() {
            return Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
        }

        StaggeredGridSectionAdapter mGridSectionAdapter = new StaggeredGridSectionAdapter() {
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

            @Override
            public ItemSize getSectionSize(int position) {
                return mSectionSizes.get(position);
            }

            @Override
            public long getSectionID(int position) {
                return position;
            }

            @Override
            public View getSectionView(int position, View convertView, ViewGroup parent) {
                SectionViewHolder holder;
                if (convertView == null) {
                    LayoutInflater layoutInflator = LayoutInflater.from(getActivity().getBaseContext());
                    convertView = layoutInflator.inflate(R.layout.grid_section_view, null);
                    holder = new SectionViewHolder();
                    holder.gridItemView = (GridItemView) convertView;
                    holder.titleView = (TextView) convertView.findViewById(R.id.titleView);
                    convertView.setTag(holder);
                }
                else {
                    holder = (SectionViewHolder) convertView.getTag();
                }
                ItemSize itemSize = mSectionSizes.get(position);
                holder.gridItemView.itemSize = itemSize;
                holder.gridItemView.setBackgroundColor(Color.BLUE);
                holder.titleView.setText(String.valueOf(position));
                return convertView;
            }

            @Override
            public int getSectionCount() {
                return mSectionSizes.size();
            }

            @Override
            public int getItemCountForSection(int section) {
                return 10;
            }

            @Override
            public int getItemViewType(int position) {
                return 0;
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }
        };

        public class SectionViewHolder {
            GridItemView gridItemView;
            TextView titleView;
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
