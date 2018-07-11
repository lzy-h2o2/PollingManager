package com.zndroid.polling.impl.polling;

import android.content.Context;
import android.os.Handler;

import com.zndroid.polling.PollingManager;
import com.zndroid.polling.core.IPolling;

/**
 * @author lazy
 * @create 2018/6/28
 * @description <= '1' minute to use it
 */
public class LowPolling extends IPolling {
    /** default */
    private int delayTime = PollingManager.__10s_TIME;

    private Handler mHandler;

    @Override
    public void init(Context context) {
        if (null == mHandler)
            mHandler = new Handler(context.getMainLooper());
    }

    @Override
    public void startPolling() {

    }

    @Override
    public void endPolling() {

    }
}
