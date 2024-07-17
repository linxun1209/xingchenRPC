package com.xingche.core.registry;

import java.util.ConcurrentModificationException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xing'chen
 * @version 1.0
 * @description: 本地注册中心
 * @date 2024/7/16 20:18
 */
public class LocalRegistry {


    /**
     * 注册信息存储
     */
    private static final Map<String,Class<?>> map=new ConcurrentHashMap<>();


    /**
     * 注册服务
     * @param serviceName
     * @param implClass
     */
    public static void register(String serviceName,Class<?> implClass){
        map.put(serviceName,implClass);
    }


    /**
     * 获取服务
     * @param serviceName
     * @return
     */
    public static Class<?> get(String serviceName){
        return map.get(serviceName);
    }


    /**
     * 删除服务
     * @param serviceName
     */
    public static void remove(String serviceName){
        map.remove(serviceName);
    }
}


