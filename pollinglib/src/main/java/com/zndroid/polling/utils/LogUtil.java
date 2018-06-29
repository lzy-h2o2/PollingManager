package com.zndroid.polling.utils;

import android.util.Log;

/**
 * @author lazy
 * @create 2018/6/29
 * @description
 */
public class LogUtil {

    private static String TAG = "Polling";
    private static boolean isDebug = true;

    public static void setTAG(String TAG) {
        LogUtil.TAG = TAG;
    }

    public static void setDebug(boolean debug) {
        isDebug = debug;
    }

    public static void i(String msg) {
        if (isDebug)
            Log.i("[" + TAG + "]", msg);
    }

    public static void d(String msg) {
        if (isDebug)
            Log.d("[" + TAG + "]", msg);
    }

    public static void e(String msg) {
        if (isDebug)
            Log.e("[" + TAG + "]", msg);
    }
}
