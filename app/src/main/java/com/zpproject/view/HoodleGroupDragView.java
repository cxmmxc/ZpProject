package com.zpproject.view;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import com.zpproject.util.LogUtil;

/**
 * 作者：Terry.Chen on 2016/5/241408.
 * 邮箱：herewinner@163.com
 * 描述：通过drag的方式实现弹球的效果
 */
public class HoodleGroupDragView extends ViewGroup {

    private ViewDragHelper mDragHelper;
    private View mChild;
    private VelocityTracker mVelocityTracker;
    private ViewConfiguration viewConfiguration;
    private int mMaxVelocity;

    public HoodleGroupDragView(Context context) {
        this(context, null);
    }

    public HoodleGroupDragView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HoodleGroupDragView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context);
    }

    private void initData(Context context) {
        mDragHelper = ViewDragHelper.create(this, 1.0f, new DragCallBack());
        viewConfiguration = ViewConfiguration.get(context);
        mMaxVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
    }

    class DragCallBack extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return true;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            final int paddingTop = getPaddingTop();
            final int bottomEdge = getHeight() - mChild.getHeight() - paddingTop;
            final int new_top = Math.min(Math.max(top, paddingTop), bottomEdge);
            return new_top;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            int paddingLeft = getPaddingLeft();
            int right_edge = getWidth() - mChild.getWidth() - paddingLeft;
            int new_left = Math.min(Math.max(paddingLeft, left), right_edge);

            return new_left;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
//            super.onViewReleased(releasedChild, xvel, yvel);
            invalidate();
            mDragHelper.flingCapturedView(0, 0, 780,1200);
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mChild = getChildAt(0);
        mChild.layout(0, 0, mChild.getMeasuredWidth(), mChild.getMeasuredHeight());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        if (mVelocityTracker == null) {
//            mVelocityTracker = VelocityTracker.obtain();
//        }
//        mVelocityTracker.addMovement(event);
//        int action = event.getAction();
//        switch (action) {
//            case MotionEvent.ACTION_DOWN:
//                mDragHelper.processTouchEvent(event);
//                break;
//            case MotionEvent.ACTION_UP:
//                mDragHelper.flingCapturedView(0, 0, 1200, 780);
//                break;
//            case MotionEvent.ACTION_CANCEL:
//                mDragHelper.cancel();
//                break;
//        }

        mDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mDragHelper.continueSettling(true)) {
            postInvalidate();
        }
    }
}
