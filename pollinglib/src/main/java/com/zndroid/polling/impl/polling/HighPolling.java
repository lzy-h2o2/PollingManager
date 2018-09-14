package com.zndroid.polling.impl.polling;

import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;

import com.zndroid.polling.core.IPolling;

/**
 * @author lazy
 * @create 2018/6/28
 * @description
 */
public class HighPolling extends IPolling {

    private AlarmManager mAlarmManager;

    @Override
    public void init(Context context) {
        mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    @Override
    public void startPolling() {
//        if (null != mAlarmManager)
//            mAlarmManager.re

    }

    @Override
    public void endPolling() {

    }
}
