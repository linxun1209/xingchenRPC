package com.xingchen.provider.servieImpl;

import com.xingche.core.RpcApplication;
import com.xingche.core.registry.LocalRegistry;
import com.xingche.core.server.HttpServer;
import com.xingche.core.server.VertxHttpServer;
import com.xingchen.common.service.UserService;

/**
 * @author xing'chen
 * @version 1.0
 * @description: 简易服务提供者实例
 * @date 2024/7/16 20:15
 */
public class EasyProviderXingchen {

    public static void main(String[] args) {


        //RPC框架初始化
        RpcApplication.init();

        //注册服务
        LocalRegistry.register(UserService.class.getName(),UserServiceImpl.class);
        //启动web服务
        HttpServer httpServer=new VertxHttpServer();
        httpServer.doStart(8080);
    }
}

