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
    //                                        15         14        13        12        11        10        9        8        7        6        5        4        3        2        1
    /** default time right away  -> '0' seconds */
    public static final long __TIME_0s = 0;

    /** default time  -> '1' seconds */
    public static final long __TIME_1s =                                                              1 << 9 | 1 << 8 | 1 << 7 | 1 << 6 | 1 << 5          | 1 << 3;//1 * 1000ms

    /** default time -> '3' seconds */
    public static final long __TIME_3s =                                          1 << 11           | 1 << 9 | 1 << 8 | 1 << 7          | 1 << 5 | 1 << 4 | 1 << 3;//3 * 1000ms

    /** default time -> '10' seconds */
    public static final long __TIME_10s =                     1 << 13                     | 1 << 10 | 1 << 9 | 1 << 8                            | 1 << 4 ;//10 * 1000ms

    /** default time -> '60' seconds */
    public static final long __60s_TIME = 1 << 15 | 1 << 14 | 1 << 13           | 1 << 11           | 1 << 9                   | 1 << 6 | 1 << 5;//1 * 60 * 1000ms

    //////////////////////////////////////////////

    ///
    private boolean isCalled = false;

    ///
    private IPolling polling;

    /**
     * build polling for different implementations. (multiple calls this when you execute different polling)<br>
     * <code>HIGH - AlarmManager</code> <br>
     * <code>NORMAL - Timer & TimerTask</code> <br>
     * <code>LOW - Handler</code>
     * @param power PowerEnum
     * @return PollingManager
     * */
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

    /**
     * store the parent context
     * @param context Context
     * @return PollingManager
     * */
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

    /**
     * you can do something on this callback, this callback on the child thread .
     * @param mPollRunning IPollRunning
     * @return PollingManager
     * */
    @Override
    public PollingManager resultAt(IPollRunning mPollRunning) {
        if (null != polling)
            polling.callBack(mPollRunning);
        return this;
    }

    /**
     * the default polling with 3 sec. {@link #endPolling()} end the polling
     * */
    @Override
    public void doPolling() {
        if (null != polling)
            polling.startPolling();
    }

    /**
     * polling with <code>period</code> sec. {@link #endPolling()} end the polling.
     * by the way, if you use <i>'LowPolling'</i> will remove any pending posts of messages with code 'what' that are in the
     * message queue when called <code>endPolling()</code>.
     * */
    @Override
    public void doPolling(long period) {
        if (null != polling && period > 0)
            polling.startPolling(period);
    }

    /**
     * end the polling & release
     * */
    @Override
    public void endPolling() {
        if (null != polling)
            polling.endPolling();
    }

    /**
     * schedules the specified task for execution after the specified delay.
     * @param delayTime
     * */
    @Override
    public void doDelay(long delayTime) {
        if (null != polling && delayTime > 0)
            polling.startDelay(delayTime);
    }

    /**
     * schedules the specified task for execution at the specified time.
     * If the time is in the past, the task is scheduled for immediate execution when you use <i>'NormalPolling'</i> .
     * */
    @Override
    public void doDelayAt(long atTime) {
        if (null != polling && atTime > 0)
            polling.startAt(atTime);
    }

    ////{
    private PollingManager(){}
    private static class $ {
        private static final PollingManager MANAGER = new PollingManager();
    }

    /**
     * get instance only one.
     * @return PollingManager
     * */
    public static PollingManager getInstance() {
        return $.MANAGER;
    }
    ////}
}
