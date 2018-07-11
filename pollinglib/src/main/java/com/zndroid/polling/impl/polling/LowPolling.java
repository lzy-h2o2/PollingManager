package com.zndroid.polling.impl.polling;

import android.content.Context;
import android.os.Handler;

import com.zndroid.polling.core.IPolling;

/**
 * @author lazy
 * @create 2018/6/28
 * @description <= '1' minute to use it
 */
public class LowPolling extends IPolling {

    private Handler mHandler;

    @Override
    public void init(Context context) {
        if (null == mHandler)
            mHandler = new Handler(context.getMainLooper());
    }

    @Override
    public void startPolling() {
        super.startPolling();
    }

    @Override
    public void startDelay(long delayTime) {
        if (null != mHandler)
            mHandler.postDelayed(mPollRunning, delayTime);
    }

    @Override
    public void startAt(long delayTime) {
        if (null != mHandler)
            mHandler.postAtTime(mPollRunning, delayTime);
    }

    @Override
    public void endPolling() {
        super.endPolling();
    }
}
