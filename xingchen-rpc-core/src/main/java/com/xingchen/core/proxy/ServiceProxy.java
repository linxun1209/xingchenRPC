package com.xingchen.core.proxy;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.xingchen.core.RpcApplication;
import com.xingchen.core.conifg.RpcConfig;
import com.xingchen.core.constant.RpcContant;
import com.xingchen.core.model.RpcRequest;
import com.xingchen.core.model.RpcResponse;
import com.xingchen.core.model.ServiceMetaInfo;
import com.xingchen.core.registry.Registry;
import com.xingchen.core.registry.RegistryFactory;
import com.xingchen.core.serializer.Serializer;
import com.xingchen.core.serializer.SerializerFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author xing'chen
 * @version 1.0
 * @description:  服务代理（JDK 动态代理）
 * @date 2024/7/16 21:26
 */
@Slf4j
public class ServiceProxy implements InvocationHandler {


    /**
     * 调用代理
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //指定序列化器
        final Serializer serializer= SerializerFactory.getInstance(RpcApplication.getRpcConfig().getSerializer());

        String ServiceName = method.getDeclaringClass().getName();
        //构造请求
        RpcRequest rpcRequest=RpcRequest.builder()
                .serviceName(ServiceName)
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();
        try {

            //序列化
            byte[] bytes = serializer.serialize(rpcRequest);
            RpcConfig rpcConfig=RpcApplication.getRpcConfig();
            Registry registry = RegistryFactory.getInstance(rpcConfig.getRegistryConfig().getRegistry());
            ServiceMetaInfo serviceMetaInfo=new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(ServiceName);
            serviceMetaInfo.setServiceVersion(RpcContant.DEFAULT_SERVICE_VERSION);
            List<ServiceMetaInfo> serviceMetaInfoList = registry.serviceDiscovery(serviceMetaInfo.getServiceKey());
            if(CollUtil.isEmpty(serviceMetaInfoList)){
                throw new RuntimeException("暂无服务地址");
            }
            ServiceMetaInfo selecetedServiceMetaInfo = serviceMetaInfoList.get(0);
            //发送请求
            try (HttpResponse httpResponse = HttpRequest.post(selecetedServiceMetaInfo.getServiceAddress())
                    .body(bytes)
                    .execute()) {
                byte[] result = httpResponse.bodyBytes();
                //反序列化
                RpcResponse rpcResponse = serializer.deserialize(result, RpcResponse.class);
                return rpcResponse.getData();
            }
        } catch (IOException e) {
                e.printStackTrace();
        }

        return null;
    }
}

