package com.zndroid.polling.core;

import android.content.Context;

import com.zndroid.polling.PollingManager;
import com.zndroid.polling.power.PowerEnum;

/**
 * @author lazy
 * @create 2018/6/28
 * @description
 */
public interface IFunctions {
    //// init work
    PollingManager build(PowerEnum mPowerEnum);
    PollingManager with(Context mContext);
    PollingManager resultAt(IPollRunning pollRunning);

    //// api
    PollingManager startPolling();
    void endPolling();
}
