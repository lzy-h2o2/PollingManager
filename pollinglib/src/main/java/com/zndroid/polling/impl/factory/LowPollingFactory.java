package com.zndroid.polling.impl.factory;

import com.zndroid.polling.core.IPolling;
import com.zndroid.polling.core.IPollingFactory;
import com.zndroid.polling.impl.polling.LowPolling;

/**
 * @author lazy
 * @create 2018/6/28
 * @description
 */
public class LowPollingFactory implements IPollingFactory {
    @Override
    public IPolling createPolling() {
        return new LowPolling();
    }
}
