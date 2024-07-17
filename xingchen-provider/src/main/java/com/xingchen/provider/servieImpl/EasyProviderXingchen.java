package com.xingchen.provider.servieImpl;

import com.xingche.easy.registry.LocalRegistry;
import com.xingche.easy.server.HttpServer;
import com.xingche.easy.server.VertxHttpServer;
import com.xingchen.common.service.UserService;

/**
 * @author xing'chen
 * @version 1.0
 * @description: 简易服务提供者实例
 * @date 2024/7/16 20:15
 */
public class EasyProviderXingchen {

    public static void main(String[] args) {

        LocalRegistry.register(UserService.class.getName(),UserServiceImpl.class);
        //启动web服务
        HttpServer httpServer=new VertxHttpServer();
        httpServer.doStart(8080);
    }
}

