package com.xingche.core.proxy;

import com.xingche.core.RpcApplication;

import java.lang.reflect.Proxy;

/**
 * @author xing'chen
 * @version 1.0
 * @description: 服务代理工厂（用于创建代理对象）
 * @date 2024/7/16 21:42
 */
public class ServiceProxyFactory {

    public static <T> T getProxy(Class<T> serviceClass){
        if (RpcApplication.getRpcConfig().isMock()) {
            return getMockProxy(serviceClass);
        }

        return (T) Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class[]{serviceClass},
                new ServiceProxy());
    }

    /**
     * 根据服务类获取 Mock 代理对象
     *
     * @param serviceClass
     * @param <T>
     * @return
     */
    public static <T> T getMockProxy(Class<T> serviceClass) {
        return (T) Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class[]{serviceClass},
                new MockServiceProxy());
    }

}

