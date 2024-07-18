package com.xingchen.core.registry;

import com.xingchen.core.spi.SpiLoader;

/**
 * @author xing'chen
 * @version 1.0
 * @description: 注册中心工厂（用于获取注册中心对象）
 * @date 2024/7/18 14:23
 */
public class RegistryFactory {

    static {
        SpiLoader.load(Registry.class);
    }

    /**
     * 默认注册中心
     */
    private static final Registry DEFAULT_REGISTRY = new EtcdRegistry();

    /**
     * 获取实例
     *
     * @param key
     * @return
     */
    public static Registry getInstance(String key) {
        return SpiLoader.getInstance(Registry.class, key);
    }

}

