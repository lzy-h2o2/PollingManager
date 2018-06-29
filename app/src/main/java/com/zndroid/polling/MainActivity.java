package com.zndroid.polling;

import android.app.Activity;
import android.os.Bundle;

import com.zndroid.polling.power.PowerEnum;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fun();
    }

    private void fun() {
        PollingManager pollingManager = PollingManager.getInstance();

        pollingManager
                .build(PowerEnum.LOW)
                .with(this)
                .startPolling();
    }
}
