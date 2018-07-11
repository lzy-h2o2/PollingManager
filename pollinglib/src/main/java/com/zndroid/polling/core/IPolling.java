package com.zndroid.polling.core;

import android.content.Context;

import java.lang.reflect.UndeclaredThrowableException;

/**
 * @author lazy
 * @create 2018/6/28
 * @description
 */
public abstract class IPolling {
    protected IPollRunning mPollRunning;

    public void callBack(IPollRunning mPollRunning) {
        this.mPollRunning = mPollRunning;
    }
    public void startPolling() { throw new UnsupportedOperationException("not implements it"); }
    public void startDelay(long delayTime) { throw new UnsupportedOperationException("not implements it"); }
    public void startAt(long delayTime) { throw new UnsupportedOperationException("not implements it");}
    public void endPolling() { throw new UnsupportedOperationException("not implements it"); }

    public abstract void init(Context context);
}
