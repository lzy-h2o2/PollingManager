package com.zndroid.polling;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.zndroid.polling.core.IPollRunning;
import com.zndroid.polling.power.PowerEnum;
import com.zndroid.polling.utils.LogUtil;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fun();
    }

    public void back(View v) {
        finish();
    }

    private void fun() {
        PollingManager pollingManager = PollingManager.getInstance();

        pollingManager
                .build(PowerEnum.LOW)
                .with(this)
                .resultAt(new IPollRunning() {
                    @Override
                    public void run() {
                        LogUtil.d("hyhyhyhy");
                    }
                })
                .doDelay(PollingManager.__10s_TIME);
    }
}
