package com.zpproject.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.zpproject.R;

/**
 * 作者：Terry.Chen on 2016/5/231525.
 * 邮箱：herewinner@163.com
 * 描述：#TODO
 */
public class HoodleView extends ImageView {
    public HoodleView(Context context) {
        this(context, null);
    }

    public HoodleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HoodleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData();
    }

    private void initData() {
//        setImageResource(R.mipmap.ic_launcher);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        setMeasuredDimension(resolveSize(measuredWidth, widthMeasureSpec), resolveSize(measuredHeight, heightMeasureSpec));
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        setImageBitmap(bitmap);
    }
}
