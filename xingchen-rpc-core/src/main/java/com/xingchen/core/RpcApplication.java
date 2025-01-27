package com.xingchen.core;

import com.xingchen.core.conifg.RegistryConfig;
import com.xingchen.core.conifg.RpcConfig;
import com.xingchen.core.constant.RpcConstant;
import com.xingchen.core.proxy.MockServiceProxy;
import com.xingchen.core.registry.Registry;
import com.xingchen.core.registry.RegistryFactory;
import com.xingchen.core.utils.ConfigUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Proxy;

/**
 * @author xing'chen
 * @version 1.0
 * @description: PRC框架应用
 * 相当于holder 存放了项目全局用到的变量，双检锁单例模式实现
 * @date 2024/7/17 10:50
 */
@Slf4j
public class RpcApplication {

    private static volatile RpcConfig rpcConfig;


    /**
     * 框架初始化，支持传入自定义字段
     * @param newRpcConfig
     */
    public static void init(RpcConfig newRpcConfig){
        rpcConfig=newRpcConfig;
        log.info("RPC init, config ={}",newRpcConfig.toString());
        // 注册中心初始化
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
        registry.init(registryConfig);
        log.info("registry init, config = {}", registryConfig);

        //创建并注册Shutdown，Hook，JVM 退出时执行操作
        Runtime.getRuntime().addShutdownHook(new Thread(registry::destroy));
    }


    /**
     * 初始化
     *
     */
    public static void init(){
        RpcConfig newRpcConfig;
        try {
            newRpcConfig= ConfigUtils.loadConfig(RpcConfig.class, RpcConstant.DEFAILT_CONFIG_PREFIX);
        }catch (Exception e){
            //配置加载失败，使用默认值
            newRpcConfig=new RpcConfig();
        }
        init(newRpcConfig);
    }





    /**
     * 获取配置
     * @return
     */
    public static RpcConfig getRpcConfig(){
        if(rpcConfig==null){
            synchronized (RpcApplication.class){
                if(rpcConfig==null){
                    init();
                }
            }
        }
        return rpcConfig;
    }
    /**
     * 根据服务类获取 Mock 代理对象
     *
     * @param serviceClass
     * @param <T>
     */
    public static <T> T getMockProxy(Class<T> serviceClass) {
        return (T) Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class[]{serviceClass},
        new MockServiceProxy());
    }

}

