package com.xingchen.consumer;

import com.xingche.core.conifg.RpcConfig;
import com.xingche.core.utils.ConfigUtils;

/**
 * @author xing'chen
 * @version 1.0
 * @description: 简易服务消费者示例
 * @date 2024/7/17 11:24
 */
public class ConsumerXingchen {
    public static void main(String[] args) {
        RpcConfig rpc= ConfigUtils.loadConfig(RpcConfig.class,"rpc");
        System.out.println(rpc);
    }

}

