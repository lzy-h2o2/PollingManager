package com.zndroid.polling.impl.polling;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import com.zndroid.polling.PollingManager;
import com.zndroid.polling.core.IPolling;

/**
 * @author lazy
 * @create 2018/6/28
 * @description <= '1' minute to use it and support cancel the pending task
 */
public class LowPolling extends IPolling {

    private Handler mHandler;
    private HandlerThread mHandlerThread;

    private BackgroundTask mBackgroundTask;

    private boolean isLoopRunning = false;

    private final int MSG_DO_TASK = 0x0001;
    private final int MSG_DO_LOOP = 0x0002;

    private long times;

    @Override
    public void init(final Context context) {
        if (null == mHandlerThread) {
            mHandlerThread = new HandlerThread("LowPolling");
            mHandlerThread.start();

            mHandler = new Handler(mHandlerThread.getLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);

                    switch (msg.what) {
                        case MSG_DO_LOOP:
                            isLoopRunning = true;
                            //实际情况我们认为是期望第一次执行不是立即执行，而应该是延迟指定n时间执行，
                            //因为需要轮询的任务往往都是上一任务结束在下一周期再继续执行时间监控，
                            //所以采用了sendEmptyMessageDelayed方法， 该方法同时还支持未执行的任务回退的功能
                            //{@link #removeMessages()}
                            mHandler.sendEmptyMessageDelayed(MSG_DO_TASK, times);
                            break;
                        case MSG_DO_TASK:
                            mBackgroundTask = new BackgroundTask();
                            mBackgroundTask.execute(Integer.MAX_VALUE);
                            break;
                        default:
                            break;
                    }
                }
            };
        }
    }

    @Override
    public void startPolling() {
        if (null != mHandlerThread && null != mHandler && !isLoopRunning) {
            isLoopRunning = true;
            times = PollingManager.__TIME_3s;//默认轮询
            mHandler.sendEmptyMessage(MSG_DO_LOOP);
        }

    }

    @Override
    public void startPolling(long period) {
        if (period < 0)
            throw new IllegalArgumentException("Negative period.");

        if (null != mHandlerThread && null != mHandler && !isLoopRunning) {
            isLoopRunning = true;
            times = period;
            mHandler.sendEmptyMessage(MSG_DO_LOOP);
        }
    }

    @Override
    public void startDelay(long delayTime) {
        if (delayTime < 0)
            throw new IllegalArgumentException("Negative delay.");

        if (null != mHandler)
            mHandler.postDelayed(mPollRunning, delayTime);
    }

    @Override
    public void startAt(long atTime) {
        if (atTime < 0)
            throw new IllegalArgumentException("Negative time.");

        if (null != mHandler)
            mHandler.postAtTime(mPollRunning, atTime);
    }

    @Override
    public void endPolling() {
        if (null != mHandler) {
            if (mHandler.hasMessages(MSG_DO_TASK))
                mHandler.removeMessages(MSG_DO_TASK);
            if (mHandler.hasMessages(MSG_DO_LOOP))
                mHandler.removeMessages(MSG_DO_LOOP);

            mHandler = null;
        }

        if (null != mHandlerThread) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2)
                mHandlerThread.quitSafely();
            else
                mHandlerThread.quit();

            mHandlerThread = null;
        }

        isLoopRunning = false;
    }

    /** 后台执行任务*/
    private class BackgroundTask extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected Integer doInBackground(Integer... integers) {
            if (null != mPollRunning)
                mPollRunning.run();
            return 0;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            if (0 == integer)
                mHandler.sendEmptyMessage(MSG_DO_LOOP);
        }
    }
}
