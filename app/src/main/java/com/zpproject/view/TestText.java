package com.zpproject.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

import com.zpproject.util.LogUtil;

/**
 * 作者：Terry.Chen on 2016/5/231115.
 * 邮箱：herewinner@163.com
 * 描述：#TODO
 */
public class TestText extends TextView {
    public TestText(Context context) {
        this(context, null);
    }

    public TestText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        boolean touchEvent = super.onTouchEvent(event);
        LogUtil.i(touchEvent+"");
        return touchEvent;
//        return super.onTouchEvent(event);
    }

}
