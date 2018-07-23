package com.zndroid.polling.impl.polling;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.TextUtils;

import com.zndroid.polling.PollingManager;
import com.zndroid.polling.core.IPolling;
import com.zndroid.polling.utils.LogUtil;

/**
 * @author lazy
 * @create 2018/6/28
 * @description <= '1' minute to use it
 */
public class LowPolling extends IPolling {

    private Handler mHandler;
    private HandlerThread mHandlerThread;

    private BackgroundTask mBackgroundTask;

    private boolean isLoopRunning = false;

    private final int MSG_DO_TASK = 0x0001;
    private final int MSG_DO_LOOP = 0x0002;

    private final String __ACTION = "polling_do_background_task_action";

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
                            mHandler.sendEmptyMessageDelayed(MSG_DO_TASK, PollingManager.__TIME_3s);
                            break;
                        case MSG_DO_TASK:
                            LogUtil.i("tttt" + mPollRunning.toString());
                            mPollRunning.run();
                            mHandler.sendEmptyMessage(MSG_DO_LOOP);
                            Intent mIntent = new Intent(context, BackgroundTask.class);
                            mIntent.setAction(__ACTION);
                            context.startService(mIntent);
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
            mHandler.sendEmptyMessage(MSG_DO_LOOP);
        }

    }

    @Override
    public void startDelay(long delayTime) {
        if (null != mHandler)
            mHandler.postDelayed(mPollRunning, delayTime);
    }

    @Override
    public void startAt(long delayTime) {
        if (null != mHandler)
            mHandler.postAtTime(mPollRunning, delayTime);
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
    private class BackgroundTask extends IntentService {

        public BackgroundTask(String name) {
            super(name);
        }

        @Override
        protected void onHandleIntent(Intent intent) {
            LogUtil.i("hhhhhh");

            if (null != intent && !TextUtils.isEmpty(intent.getAction()))
                if (__ACTION.equals(intent.getAction())) {
                    synchronized (this) {
                        mPollRunning.run();
                    }
                }
            mHandler.sendEmptyMessage(MSG_DO_LOOP);
        }
    }
}
