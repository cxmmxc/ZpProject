package com.zpproject.util;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * 作者：Terry.Chen on 2016/5/171338.
 * 邮箱：herewinner@163.com
 * 描述：Toast提示
 */
public class ToastAlone {
    private static Toast mToast;
    private static Context mApplication;

    public static void init(Context application) {
        mApplication = application;
    }

    public static void show(Context context, String text){
        if (mToast == null) {
            Log.v("cxm", "null");
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        }
        Log.v("cxm", "notnull");
        mToast.setText(text);
        mToast.show();
    }
}
