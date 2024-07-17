package com.xingche.core;

import com.xingche.core.conifg.RpcConfig;
import com.xingche.core.constant.RpcContant;
import com.xingche.core.utils.ConfigUtils;
import lombok.extern.slf4j.Slf4j;

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
    }


    /**
     * 初始化
     *
     */
    public static void init(){
        RpcConfig newRpcConfig;
        try {
            newRpcConfig= ConfigUtils.loadConfig(RpcConfig.class, RpcContant.DEFAILT_CONFIG_PREFIX);
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
}

