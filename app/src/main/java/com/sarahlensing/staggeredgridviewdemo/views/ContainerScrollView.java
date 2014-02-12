package com.sarahlensing.staggeredgridviewdemo.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by sarahlensing on 2/12/14.
 */
public class ContainerScrollView extends ScrollView {

    private float mTouchX;
    private float mTouchY;

    public ContainerScrollView(Context context) {
        super(context);
    }

    public ContainerScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ContainerScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchX = event.getX();
                mTouchY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                return movingVertically(event);
        }
        return super.onInterceptTouchEvent(event);
    }

    private boolean movingVertically(MotionEvent event) {
        float diffX = Math.abs(mTouchX - event.getX());
        float diffY = Math.abs(mTouchY - event.getY());
        return diffY > diffX;
    }
}
