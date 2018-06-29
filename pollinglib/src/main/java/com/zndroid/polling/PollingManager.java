package com.zndroid.polling;

import android.content.Context;

import com.zndroid.polling.core.IFunctions;
import com.zndroid.polling.core.IPolling;
import com.zndroid.polling.impl.factory.HighPollingFactory;
import com.zndroid.polling.impl.factory.LowPollingFactory;
import com.zndroid.polling.impl.factory.NormalPollingFactory;
import com.zndroid.polling.power.PowerEnum;

/**
 * @author lazy
 * @create 2018/6/28
 * @description
 */
public class PollingManager implements IFunctions{

    private final String TAG = "PollingManager";

    private boolean isCalled = false;

    private IPolling polling;

    @Override
    public PollingManager build(PowerEnum power) {
        switch (power) {
            case HIGH:
                polling = new HighPollingFactory().createPolling();
                break;
            case NORMAL:
                polling = new NormalPollingFactory().createPolling();
                break;
            case LOW:
                polling = new LowPollingFactory().createPolling();
            default:
                break;
        }

        isCalled = true;
        return this;
    }

    @Override
    public PollingManager with(Context context) {
        if (null == context)
            throw new UnsupportedOperationException("context is null, please check it.");

        if (!isCalled)
            throw new UnsupportedOperationException("calling this method after 'build(xxx)' please.");

        if (null != polling)
            polling.init(context.getApplicationContext());//need test
        return this;
    }

    @Override
    public PollingManager startPolling() {
        if (null != polling)
            polling.startPolling();

        return this;
    }

    @Override
    public void endPolling() {
        if (null != polling)
            polling.endPolling();
    }

    ////{
    private PollingManager(){}
    private static class $ {
        private static final PollingManager MANAGER = new PollingManager();
    }

    public static PollingManager getInstance() {
        return $.MANAGER;
    }
    ////}
}
