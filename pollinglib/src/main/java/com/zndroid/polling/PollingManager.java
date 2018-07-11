package com.zndroid.polling;

import android.content.Context;

import com.zndroid.polling.core.IFunctions;
import com.zndroid.polling.core.IPollRunning;
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

    //////////////////////////////////////////////
    //                                       9   8   7   6   5   4   3   2   1   0
    /** default time right away  -> '0' seconds */
    public static final long __0s_TIME = 0;

    /** default time  -> '1' seconds */
    public static final long __1s_TIME =1 << 9 |
                                            1 << 8 |
                                                1 << 7 |
                                                    1 << 6 |
                                                        1 << 5 |
                                                                1 << 3;//1 * 1000ms

    /** default time -> '3' seconds */
    public static final long __3s_TIME =                               (1 << 1 |
                                                                             1 << 0)* __1s_TIME;//3 * 1000ms

    /** default time -> '10' seconds */
    public static final long __10s_TIME =                      (1 << 3 |
                                                                        1 << 1) * __1s_TIME;//10 * 1000ms
    /** default time -> '60' seconds */
    public static final long __60s_TIME =              (1 << 5 |
                                                            1 << 4 |
                                                                1 << 3 |
                                                                    1 << 2) * __1s_TIME;//1 * 60 * 1000ms

    //////////////////////////////////////////////

    ///
    private boolean isCalled = false;

    ///
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
            polling.init(context.getApplicationContext());//TODO need test
        return this;
    }

    @Override
    public PollingManager resultAt(IPollRunning mPollRunning) {
        if (null != polling)
            polling.callBack(mPollRunning);
        return this;
    }

    @Override
    public void doPolling() {
        if (null != polling)
            polling.startPolling();
    }

    @Override
    public void endPolling() {
        if (null != polling)
            polling.endPolling();
    }

    @Override
    public void doDelay(long delayTime) {
        if (null != polling)
            polling.startDelay(delayTime);
    }

    @Override
    public void doDelayAt(long delayTime) {
        if (null != polling)
            polling.startAt(delayTime);
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
