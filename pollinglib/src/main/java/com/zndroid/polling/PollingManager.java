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
    /** default time right away  -> '0' seconds */
    public static final int __0s_TIME = 0;

    /** default time -> '3' seconds */
    public static final int __3s_TIME =                                     1 << 1 |
                                                                                1 << 0;//3ms

    /** default time -> '10' seconds */
    public static final int __10s_TIME = 1 << 9 |
                                                1 << 8 |
                                                    1 << 7 |
                                                        1 << 6 |
                                                            1 << 5 |
                                                                    1 << 3;//1 * 1000ms
    /** default time -> '60' seconds */
    public static final int __60s_TIME =                    (1 << 5 |
                                                                1 << 4 |
                                                                    1 << 3 |
                                                                        1 << 2) * __10s_TIME;//1 * 60 * 1000ms

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
