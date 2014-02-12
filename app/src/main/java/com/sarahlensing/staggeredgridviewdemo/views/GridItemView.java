package com.sarahlensing.staggeredgridviewdemo.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.sarahlensing.staggeredgridview.ItemSize;

/**
 * Created by sarahlensing on 11/30/13.
 */
public class GridItemView extends RelativeLayout {

    public ItemSize itemSize;

    public GridItemView(Context context) {
        super(context);
    }

    public GridItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GridItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (isInEditMode()) {

        }
        else {
            setMeasuredDimension(itemSize.width, itemSize.height);
        }
    }
}
