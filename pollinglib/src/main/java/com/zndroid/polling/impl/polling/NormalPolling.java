package com.zndroid.polling.impl.polling;

import android.content.Context;

import com.zndroid.polling.PollingManager;
import com.zndroid.polling.core.IPolling;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author lazy
 * @create 2018/6/28
 * @description
 * It is recommended to use If
 *      the time-consuming operation execution time
 * is less than
 *      the polling period
 */
public class NormalPolling extends IPolling {

    private Timer mTimer;
    private NormalTimerTask mTimerTask;
    @Override
    public void init(Context context) {
        mTimer = new Timer();
        mTimerTask = new NormalTimerTask();
    }

    /**
     * 默认场景：执行事务时间 <= 轮询周期，任务无延迟执行
     * */
    @Override
    public void startPolling() {
        //如果耗时操作执行时间 <= 轮询周期 这两个方法计时时间没有区别，
        //如果耗时操作执行完时间 > 轮询周期 schedule下次执行时间会从耗时操作真正完成时开始（应用于大部分实际场景，也是本lib使用的方式，否则就会导致执行周期>设置的周期）
        //                              scheduleAtFixedRate下次执行时间不会受耗时操作时间影响，会从事物开始执行的时间点执行
//        mTimer.schedule();
//        mTimer.scheduleAtFixedRate();
        mTimer.schedule(mTimerTask, PollingManager.__TIME_0s, PollingManager.__TIME_3s);//立即执行，执行周期3秒
    }

    @Override
    public void startPolling(long period) {
        if (period < 0)
            throw new IllegalArgumentException("Negative period.");

        mTimer.schedule(mTimerTask, PollingManager.__TIME_0s, period);//立即执行，并指定执行周期
    }

    @Override
    public void startDelay(long delayTime) {
        if (delayTime < 0)
            throw new IllegalArgumentException("Negative delay.");

        mTimer.schedule(mTimerTask, delayTime);
    }

    @Override
    public void startAt(long atTime) {
        //如果指定的时间已过 - 立即执行
        Date date = new Date(atTime);
        mTimer.schedule(mTimerTask, date);
    }

    @Override
    public void endPolling() {
        if (null != mTimer) {
            mTimer.cancel();
            mTimer = null;
        }
        if (null != mTimerTask) {
            mTimerTask.cancel();
            mTimerTask = null;
        }
    }

    private class NormalTimerTask extends TimerTask {

        @Override
        public void run() {
            mPollRunning.run();
        }
    }
}
