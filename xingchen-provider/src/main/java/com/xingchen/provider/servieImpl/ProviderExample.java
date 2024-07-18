package com.xingchen.provider.servieImpl;

import com.xingchen.common.service.UserService;
import com.xingchen.core.RpcApplication;
import com.xingchen.core.conifg.RegistryConfig;
import com.xingchen.core.conifg.RpcConfig;
import com.xingchen.core.model.ServiceMetaInfo;
import com.xingchen.core.registry.LocalRegistry;
import com.xingchen.core.registry.Registry;
import com.xingchen.core.registry.RegistryFactory;
import com.xingchen.core.server.HttpServer;
import com.xingchen.core.server.VertxHttpServer;

/**
 * @author xing'chen
 * @version 1.0
 * @description:  服务提供者示例
 * @date 2024/7/18 15:25
 */
public class ProviderExample {
    public static void main(String[] args) {
        // RPC 框架初始化
        RpcApplication.init();

        // 注册服务
        String serviceName = UserService.class.getName();
        LocalRegistry.register(serviceName, UserServiceImpl.class);

        // 注册服务到注册中心
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName(serviceName);
        serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
        serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
        try {
            registry.register(serviceMetaInfo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // 启动 web 服务
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
    }
}

