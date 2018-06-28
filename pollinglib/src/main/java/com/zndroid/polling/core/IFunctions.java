package com.zndroid.polling.core;

import android.content.Context;

import com.zndroid.polling.power.PowerEnum;

/**
 * @author lazy
 * @create 2018/6/28
 * @description
 */
public interface IFunctions {
    void build(PowerEnum power);
    void with(Context context);
    void startPolling();
    void endPolling();
}
