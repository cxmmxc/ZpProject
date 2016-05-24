package com.zpproject.view;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

import com.zpproject.util.LogUtil;

/**
 * 作者：Terry.Chen on 2016/5/231525.
 * 邮箱：herewinner@163.com
 * 描述：#TODO
 */
public class HoodleGroupView extends ViewGroup {

    private PointF mDownPoint;

    private RectF mChildeRectF;
    private VelocityTracker velocityTracker;
    private Scroller mScroller;
    private int mMaxFlintVelocity, mMinFlintVelocity;
    private int mChildMeasuredWidth,mChildMeasuredHeight;
    private View chileView;

    public HoodleGroupView(Context context) {
        this(context, null);
    }

    public HoodleGroupView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HoodleGroupView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context);
    }

    private void initData(Context context) {
        mDownPoint = new PointF();
        mChildeRectF = new RectF();
        mScroller = new Scroller(context);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        mMaxFlintVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        mMinFlintVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        LogUtil.e("onLayout");
        chileView = getChildAt(0);
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        mChildMeasuredWidth = chileView.getMeasuredWidth();
        mChildMeasuredHeight = chileView.getMeasuredHeight();
        mChildeRectF.set(measuredWidth / 2 - mChildMeasuredWidth / 2, measuredHeight / 2 - mChildMeasuredHeight / 2, measuredWidth / 2 + mChildMeasuredWidth / 2, measuredHeight / 2 + mChildMeasuredHeight / 2);
        chileView.layout(measuredWidth / 2 - mChildMeasuredWidth / 2, measuredHeight / 2 - mChildMeasuredHeight / 2, measuredWidth / 2 + mChildMeasuredWidth / 2, measuredHeight / 2 + mChildMeasuredHeight / 2);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec,heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        return super.onTouchEvent(event);
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }
        velocityTracker.addMovement(event);
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mDownPoint.x = event.getX();
                mDownPoint.y = event.getY();
                //判断按下的手指是否在图片上面
                if(!isViewUnderPoint(mDownPoint)){
                    return false;
                }
                if(!mScroller.isFinished()){
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float ev_x = event.getX();
                float ev_y = event.getY();
                scrollBy((int) (-ev_x + mDownPoint.x), (int) (-ev_y + mDownPoint.y));
                mChildeRectF.set(mChildeRectF.left + ev_x - mDownPoint.x, mChildeRectF.top + ev_y - mDownPoint.y, mChildeRectF.right + ev_x - mDownPoint.x, mChildeRectF.bottom + ev_y - mDownPoint.y);
                mDownPoint.x = ev_x;
                mDownPoint.y = ev_y;
                break;
            case MotionEvent.ACTION_UP:
                //手指抬起，计算当前速率
                velocityTracker.computeCurrentVelocity(1000, mMaxFlintVelocity);
                int xVelocity = (int) velocityTracker.getXVelocity();
                int yVelocity = (int) velocityTracker.getYVelocity();
                LogUtil.e("xVelo="+xVelocity+",yVelo="+yVelocity);
                int scrollX = getScrollX();
                int scrollY = getScrollY();

                LogUtil.e("scrollX=" + scrollX + ",scrollY=" + scrollY);
                if (Math.abs(xVelocity) > mMinFlintVelocity || Math.abs(yVelocity) > mMinFlintVelocity) {
                    mScroller.fling(scrollX, scrollY, -xVelocity,  -yVelocity, 0, 100000, 0, 100000);
                    awakenScrollBars(mScroller.getDuration());
                    invalidate();
                }
                if (velocityTracker != null) {
                    velocityTracker.clear();
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                LogUtil.e("-----ACTION_CANCEL");
                break;
        }
        return true;
    }

    private boolean isViewUnderPoint(PointF pointF) {
        return mChildeRectF.contains(pointF.x, pointF.y);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        LogUtil.e("computeScroll---"+mScroller.getCurrX()+","+mScroller.getCurrY());
        if (mScroller.computeScrollOffset()) {
            LogUtil.e(mScroller.getCurrX()+"    "+mScroller.getCurrY());
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            mChildeRectF.set(chileView.getLeft(), chileView.getTop(), chileView.getRight(), chileView.getBottom());
            postInvalidate();
        }else {
            LogUtil.e(mScroller.getFinalX() + "-final-" + mScroller.getFinalY());
        }
    }

}
