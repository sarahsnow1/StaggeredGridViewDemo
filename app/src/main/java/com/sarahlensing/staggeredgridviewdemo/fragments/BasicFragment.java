package com.sarahlensing.staggeredgridviewdemo.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sarahlensing.staggeredgridview.ItemSize;
import com.sarahlensing.staggeredgridview.StaggeredGridAdapter;
import com.sarahlensing.staggeredgridview.StaggeredGridSectionAdapter;
import com.sarahlensing.staggeredgridview.StaggeredGridView;
import com.sarahlensing.staggeredgridviewdemo.R;
import com.sarahlensing.staggeredgridviewdemo.views.GridItemView;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by sarahlensing on 2/12/14.
 */

public class BasicFragment extends android.support.v4.app.Fragment {

    private static final String TAG = "BasicFragment";

    StaggeredGridView mGridView;
    ArrayList<ItemSize> mItemSizes;
    ArrayList<com.sarahlensing.staggeredgridview.ItemSize> mSectionSizes;

    private final Random random = new Random();

    String mOrientation;

    public BasicFragment() {
        super();
    }

    public BasicFragment(String orientation) {
        super();
        mOrientation = orientation;
    }

    protected int fragmentLayoutID() {
        return R.layout.fragment_basic;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(fragmentLayoutID(), container, false);
        mGridView = (StaggeredGridView)rootView.findViewById(R.id.gridView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        if (mOrientation != null && !mOrientation.equals(mGridView.getGridOrientation())) {
            mGridView.setGridOrientation(mOrientation);
        }
        mGridView.setAdapter(mGridSectionAdapter);
        buildGridSizes();
        startReloadTimer();
        super.onViewCreated(view, savedInstanceState);
    }

    private void setupVertical() {
        buildGridSizes(StaggeredGridView.STAGGERED_GRID_ORIENTATION_VERTICAL);
        mGridView.setGridOrientation(StaggeredGridView.STAGGERED_GRID_ORIENTATION_VERTICAL);
    }

    private void setupHorizontal() {
        buildGridSizes(StaggeredGridView.STAGGERED_GRID_ORIENTATION_HORIZONTAL);
        mGridView.setGridOrientation(StaggeredGridView.STAGGERED_GRID_ORIENTATION_HORIZONTAL);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_horizontal:
                this.setupHorizontal();
                return true;
            case R.id.action_vertical:
                this.setupVertical();
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

        mItemSizes = new ArrayList<ItemSize>();
        mItemSizes.addAll(getItemSizes(orientation));

        mSectionSizes = new ArrayList<ItemSize>();
        mSectionSizes.addAll(getSectionItemSizes(orientation));
    }

    private ArrayList<ItemSize> getItemSizes(String orientation) {
        int sm = 430;
        int med = 635;
        int lg = 860;

        boolean useRnd = true;
        int levelOfRnd = 3;

        ArrayList<ItemSize> itemSizes = new ArrayList<ItemSize>();
        for (int i = 0; i < 200; i++) {

            if (useRnd) {
                int width = (random.nextInt(levelOfRnd)+1) * 200;
                int height = (random.nextInt(levelOfRnd-1)+1) * 200;
                itemSizes.add(new ItemSize(width, height));
            }
            else {
                itemSizes.add(new ItemSize(sm, sm));
                itemSizes.add(new ItemSize(lg, sm));
                itemSizes.add(new ItemSize(sm, sm));
                itemSizes.add(new ItemSize(med, lg));
                itemSizes.add(new ItemSize(med, sm));
                itemSizes.add(new ItemSize(sm, sm));
                itemSizes.add(new ItemSize(sm, sm));
                itemSizes.add(new ItemSize(med, lg));
                itemSizes.add(new ItemSize(lg, lg));
                itemSizes.add(new ItemSize(sm, sm));
                itemSizes.add(new ItemSize(lg, sm));
                itemSizes.add(new ItemSize(sm, sm));
                itemSizes.add(new ItemSize(med, lg));
                itemSizes.add(new ItemSize(med, sm));
                itemSizes.add(new ItemSize(sm, sm));
                itemSizes.add(new ItemSize(sm, sm));
                itemSizes.add(new ItemSize(med, lg));
                itemSizes.add(new ItemSize(lg, lg));
            }
        }
        return itemSizes;
    }

    private ArrayList<ItemSize> getSectionItemSizes(String orientation) {
        int sectionSize = 200;
        int maxHeight = getActivity().getWindowManager().getDefaultDisplay().getHeight()-2*mGridView.getItemMargin();
        int maxWidth = getActivity().getWindowManager().getDefaultDisplay().getWidth()-2*mGridView.getItemMargin();
        ArrayList<ItemSize> sectionSizes = new ArrayList<ItemSize>();
        for (int i = 0; i < 10; i++) {
            if (orientation.equals(StaggeredGridView.STAGGERED_GRID_ORIENTATION_HORIZONTAL)) {
                sectionSizes.add(new ItemSize(sectionSize, maxHeight));
            }
            else {
                sectionSizes.add(new ItemSize(maxWidth, sectionSize));
            }
        }
        return sectionSizes;
    }

    private int nextRandomColor() {
        return Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    private void removeSomeItems() {
        for (int i = 0; i < 20; i++) {
            mItemSizes.remove(0);
        }
        mSectionSizes.remove(0);
    }

    private void appendMoreItems() {
        String orientation = mGridView.getGridOrientation();
        mItemSizes.addAll(getItemSizes(orientation));
        mSectionSizes.addAll(getSectionItemSizes(orientation));
    }

    private void startReloadTimer() {

        Timer timer = new Timer("reloadTimer", true);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                BasicFragment.this.getActivity().runOnUiThread(new Runnable() {
                    public void run() {

                        //for testing how grid handles removed items
                        //removeSomeItems();

                        //for testing how grid handles additional items
                        appendMoreItems();

                        Toast.makeText(BasicFragment.this.getActivity(), "About to reload", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "About to notifyDataSetChanged()");

                        mGridView.reloadGrid();

                        //more efficient than reloadGrid if we are guaranteed that we are appending data
                        //mGridView.reloadGridAppendItems();

                        Log.d(TAG, "notifyDataSetChanged() finished.");
                    }
                });

            }
        };
        timer.schedule(timerTask, 10000);
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
        public View getView(int section, int position, View convertView, ViewGroup parent) {
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
            holder.subtitleView.setText(String.valueOf(itemSize.width) + " x " + String.valueOf(itemSize.height));

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
            holder.titleView.setText("Section:"+String.valueOf(position));
            return convertView;
        }

        @Override
        public int getSectionCount() {
            return mSectionSizes.size();
        }

        @Override
        public int getItemCountForSection(int section) {
            return 20;
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
