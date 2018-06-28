package com.zndroid.polling;

import android.content.Context;

import com.zndroid.polling.core.IFunctions;
import com.zndroid.polling.power.PowerEnum;

/**
 * @author lazy
 * @create 2018/6/28
 * @description
 */
public class PollingManager implements IFunctions{

    @Override
    public void build(PowerEnum power) {

    }

    @Override
    public void with(Context context) {

    }

    @Override
    public void startPolling() {

    }

    @Override
    public void endPolling() {

    }

    ////{
    private PollingManager(){}
    private static class $ {
        private static final PollingManager MANAGER = new PollingManager();
    }

    public PollingManager getInstance() {
        return $.MANAGER;
    }
    ////}
}
