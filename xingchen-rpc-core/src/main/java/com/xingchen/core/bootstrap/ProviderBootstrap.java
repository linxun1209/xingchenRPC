package com.xingchen.core.bootstrap;

import com.xingchen.core.RpcApplication;
import com.xingchen.core.conifg.RegistryConfig;
import com.xingchen.core.conifg.RpcConfig;
import com.xingchen.core.model.ServiceMetaInfo;
import com.xingchen.core.model.ServiceRegisterInfo;
import com.xingchen.core.registry.LocalRegistry;
import com.xingchen.core.registry.Registry;
import com.xingchen.core.registry.RegistryFactory;
import com.xingchen.core.server.tcp.VertxTcpServer;

import java.util.List;

/**
 * @author xing'chen
 * @version 1.0
 * @description: 服务提供者启动类（初始化）
 * @date 2024/7/29 11:28
 */
public class ProviderBootstrap {



    public static void init(List<ServiceRegisterInfo<?>> serviceRegisterInfoList){
        //RPC框架初始化（配置和注册中心）
        RpcApplication.init();
        //全局配置
        final RpcConfig rpcConfig=RpcApplication.getRpcConfig();

        //注册服务
        for (ServiceRegisterInfo<?> serviceRegisterInfo:serviceRegisterInfoList){
            String serviceName = serviceRegisterInfo.getServiceName();
            //本地注册
            LocalRegistry.register(serviceName,serviceRegisterInfo.getImplClass());

            //注册服务到注册中心
            RegistryConfig registryConfig=rpcConfig.getRegistryConfig();
            Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
            ServiceMetaInfo serviceMetaInfo=new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
            serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
            try{
                registry.register(serviceMetaInfo);
            }catch (Exception e){
                throw new RuntimeException(serviceName+"服务注册失败"+e);
            }
        }
        // 启动服务器
        VertxTcpServer vertxTcpServer = new VertxTcpServer();
        vertxTcpServer.doStart(rpcConfig.getServerPort());
    }

}

