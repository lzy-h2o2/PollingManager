package com.zndroid.polling.core;

import android.content.Context;

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

    public abstract void init(Context context);
    public abstract void startPolling();
    public abstract void endPolling();
}
