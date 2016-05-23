package com.zpproject.view;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.zpproject.util.LogUtil;

/**
 * 作者：Terry.Chen on 2016/5/190948.
 * 邮箱：herewinner@163.com
 * 描述：#TODO
 */
public class HYLayout extends LinearLayout {

    ViewDragHelper mViewDrager;

    //让3个view有u不同的功能
    private View mDragView, mAutoBackView, mEdgeTrackerView;

    private Point mAutoPoint;

    public HYLayout(Context context) {
        this(context, null);
    }

    public HYLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData();
    }

    private void initData() {
        mViewDrager = ViewDragHelper.create(this, 1.0f, new CallBack());
        mAutoPoint = new Point();
        mViewDrager.setEdgeTrackingEnabled(ViewDragHelper.EDGE_ALL);
    }


    class CallBack extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == mAutoBackView || child == mDragView;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            LogUtil.i("left= " + left);
            if (left < 0) {
                return 0;
            }
            return left;
//            return super.clampViewPositionHorizontal(child, left, dx);
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            LogUtil.i("top= " + top);
            if (top < 0) {
                return 0;
            }
            return top;
//            return super.clampViewPositionVertical(child, top, dy);
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            if (mAutoBackView == releasedChild) {
                mViewDrager.settleCapturedViewAt(mAutoPoint.x, mAutoPoint.y);
                invalidate();
            }
        }

        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            super.onEdgeDragStarted(edgeFlags, pointerId);
            mViewDrager.captureChildView(mEdgeTrackerView, pointerId);
        }

        @Override
        public int getViewVerticalDragRange(View child) {
//            return super.getViewVerticalDragRange(child);
            return getHeight();
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
//            return super.getViewHorizontalDragRange(child);
            return getWidth();
        }


    }

    @Override
    public void computeScroll() {
//        super.computeScroll();
        if (mViewDrager.continueSettling(true)) {
            invalidate();
        }
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        LogUtil.v("left=" + mAutoBackView.getLeft() + ",top=" + mAutoBackView.getTop() + "---x=" + mAutoBackView.getX() + ",y=" + mAutoBackView.getY());
        mAutoPoint.set(mAutoBackView.getLeft(), mAutoBackView.getTop());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtil.v("onTouchEvent");
        mViewDrager.processTouchEvent(event);
        return true;
//        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean b = super.dispatchTouchEvent(ev);
        LogUtil.v("dispatchTouchEvent="+b);
        return b;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        LogUtil.v("onInterceptTouchEvent");
//        return mViewDrager.shouldInterceptTouchEvent(ev);
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mDragView = getChildAt(0);
        mAutoBackView = getChildAt(1);
        mEdgeTrackerView = getChildAt(2);
    }
}
