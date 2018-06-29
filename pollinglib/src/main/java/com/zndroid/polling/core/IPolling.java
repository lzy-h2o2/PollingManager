package com.zndroid.polling.core;

import android.content.Context;

/**
 * @author lazy
 * @create 2018/6/28
 * @description
 */
public interface IPolling {
    void init(Context context);
    void startPolling();
    void endPolling();
}
