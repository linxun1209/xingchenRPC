package com.xingchen.core.fault.tolerant;


import com.xingchen.core.spi.SpiLoader;

/**
 * @author xing'chen
 * @version 1.0
 * @description: 容错策略工厂（工厂模式，用于获取容错策略对象）
 * @date 2024/7/17 10:48
 */
public class TolerantStrategyFactory {

    static {
        SpiLoader.load(TolerantStrategy.class);
    }

    /**
     * 默认容错策略
     */
    private static final TolerantStrategy DEFAULT_RETRY_STRATEGY = new FailFastTolerantStrategy();

    /**
     * 获取实例
     *
     * @param key
     * @return
     */
    public static TolerantStrategy getInstance(String key) {
        return SpiLoader.getInstance(TolerantStrategy.class, key);
    }

}
