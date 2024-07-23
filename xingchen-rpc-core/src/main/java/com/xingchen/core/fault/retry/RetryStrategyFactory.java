package com.xingchen.core.fault.retry;


import com.xingchen.core.spi.SpiLoader;

/**
 * @author xing'chen
 * @version 1.0
 * @description: 重试策略工厂（用于获取重试器对象）
 * @date 2024/7/23 20:18
 */
public class RetryStrategyFactory {

    static {
        SpiLoader.load(RetryStrategy.class);
    }

    /**
     * 默认重试器
     */
    private static final RetryStrategy DEFAULT_RETRY_STRATEGY = new NoRetryStrategy();

    /**
     * 获取实例
     *
     * @param key
     * @return
     */
    public static RetryStrategy getInstance(String key) {
        return SpiLoader.getInstance(RetryStrategy.class, key);
    }

}
