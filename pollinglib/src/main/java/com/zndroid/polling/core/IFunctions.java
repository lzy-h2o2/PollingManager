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
    PollingManager build(PowerEnum power);
    PollingManager with(Context context);
    PollingManager startPolling();
    void endPolling();
}
