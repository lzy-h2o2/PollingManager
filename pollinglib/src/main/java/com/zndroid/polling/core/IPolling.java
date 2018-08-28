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

    /** 默认轮询*/
    public void startPolling() { throw new UnsupportedOperationException("not implements it"); }
    /** 指定轮询时间*/
    public void startPolling(long period) { throw new UnsupportedOperationException("not implements it"); }
    /** 在指定延迟时间后执行*/
    public void startDelay(long delayTime) { throw new UnsupportedOperationException("not implements it"); }
    /** 在指定时间执行*/
    public void startAt(long delayTime) { throw new UnsupportedOperationException("not implements it");}
    /** 结束轮询释放资源*/
    public void endPolling() { throw new UnsupportedOperationException("not implements it"); }

    public abstract void init(Context context);
}
