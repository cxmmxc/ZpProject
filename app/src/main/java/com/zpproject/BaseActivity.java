package com.zpproject;

import android.app.Activity;
import android.content.Context;

/**
 * 作者：Terry.Chen on 2016/5/171701.
 * 邮箱：herewinner@163.com
 * 描述：#TODO
 */
public class BaseActivity extends Activity {
    protected Context mContext;
    @Override
    protected void onStart() {
        super.onStart();
        mContext = this;
    }
}
