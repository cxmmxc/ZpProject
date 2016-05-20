package com.zpproject.util;

import android.util.Log;

/**
 * 作者：Terry.Chen on 2016/5/201113.
 * 邮箱：herewinner@163.com
 * 描述：#TODO
 */
public class LogUtil {
    private static String TAG = "cxm";
    public static void v(String msg){
        Log.v(TAG, msg);
    }

    public static void i(String msg){
        Log.i(TAG, msg);
    }


    public static void w(String msg){
        Log.w(TAG, msg);
    }
    public static void e(String msg){
        Log.e(TAG, msg);
    }
}
