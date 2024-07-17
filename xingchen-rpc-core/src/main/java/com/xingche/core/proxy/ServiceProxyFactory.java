package com.xingche.core.proxy;

import java.lang.reflect.Proxy;

/**
 * @author xing'chen
 * @version 1.0
 * @description: 服务代理工厂（用于创建代理对象）
 * @date 2024/7/16 21:42
 */
public class ServiceProxyFactory {

    public static <T> T getProxy(Class<T> seviceClass){
        return (T) Proxy.newProxyInstance(
                seviceClass.getClassLoader(),
                new Class[]{seviceClass},
                new ServiceProxy());
    }

}

