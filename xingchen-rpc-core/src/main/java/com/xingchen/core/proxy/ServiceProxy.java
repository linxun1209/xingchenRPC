package com.xingchen.core.proxy;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.xingchen.core.RpcApplication;
import com.xingchen.core.conifg.RpcConfig;
import com.xingchen.core.constant.RpcConstant;
import com.xingchen.core.fault.retry.RetryStrategy;
import com.xingchen.core.fault.retry.RetryStrategyFactory;
import com.xingchen.core.fault.tolerant.TolerantStrategy;
import com.xingchen.core.fault.tolerant.TolerantStrategyFactory;
import com.xingchen.core.loadbalancer.LoadBalancer;
import com.xingchen.core.loadbalancer.LoadBalancerFactory;
import com.xingchen.core.model.RpcRequest;
import com.xingchen.core.model.RpcResponse;
import com.xingchen.core.model.ServiceMetaInfo;
import com.xingchen.core.registry.Registry;
import com.xingchen.core.registry.RegistryFactory;
import com.xingchen.core.serializer.Serializer;
import com.xingchen.core.serializer.SerializerFactory;


import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xingchen.core.server.tcp.VertxTcpClient;
import lombok.extern.slf4j.Slf4j;


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
        //final Serializer serializer= SerializerFactory.getInstance(RpcApplication.getRpcConfig().getSerializer());

        //构造请求
            String ServiceName = method.getDeclaringClass().getName();
            RpcRequest rpcRequest=RpcRequest.builder()
                .serviceName(ServiceName)
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();

            // 从注册中心获取服务提供者请求地址
            //序列化
            RpcConfig rpcConfig = RpcApplication.getRpcConfig();
            Registry registry = RegistryFactory.getInstance(rpcConfig.getRegistryConfig().getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(ServiceName);
            serviceMetaInfo.setServiceVersion(RpcConstant.DEFAULT_SERVICE_VERSION);
            List<ServiceMetaInfo> serviceMetaInfoList = registry.serviceDiscovery(serviceMetaInfo.getServiceKey());
            if (CollUtil.isEmpty(serviceMetaInfoList)) {
                throw new RuntimeException("暂无服务地址");
            }

            //负载均衡
            LoadBalancer loadBalancer= LoadBalancerFactory.getInstance(rpcConfig.getLoadBalancer());
            Map<String,Object> requestParams=new HashMap<>();
            requestParams.put("methodName",rpcRequest.getMethodName());
            ServiceMetaInfo selecetedServiceMetaInfo = loadBalancer.select(requestParams,serviceMetaInfoList);
            //将调用方法名（请求路径）作为负载均衡参数
            // rpc 请求
            // 使用重试机制
            RpcResponse rpcResponse;
            try {
                RetryStrategy retryStrategy= RetryStrategyFactory.getInstance(rpcConfig.getRetryStrategy());
                 rpcResponse= retryStrategy.doRetry(() ->
                        VertxTcpClient.doRequest(rpcRequest, selecetedServiceMetaInfo)
                );
            }catch (Exception e){
                // 容错机制
                TolerantStrategy tolerantStrategy = TolerantStrategyFactory.getInstance(rpcConfig.getTolerantStrategy());
                rpcResponse = tolerantStrategy.doTolerant(null, e);
            }
            /*
           //发送TPC请求
            RpcResponse rpcResponse= VertxTcpClient.doRequest(rpcRequest,selecetedServiceMetaInfo);
             */
            return rpcResponse.getData();

        }

    /**
     * 发送 HTTP 请求
     *
     * @param selectedServiceMetaInfo
     * @param bodyBytes
     * @return
     * @throws IOException
     */
    private static RpcResponse doHttpRequest(ServiceMetaInfo selectedServiceMetaInfo, byte[] bodyBytes) throws IOException {
        final Serializer serializer = SerializerFactory.getInstance(RpcApplication.getRpcConfig().getSerializer());
        // 发送 HTTP 请求
        try (HttpResponse httpResponse = HttpRequest.post(selectedServiceMetaInfo.getServiceAddress())
                .body(bodyBytes)
                .execute()) {
            byte[] result = httpResponse.bodyBytes();
            // 反序列化
            RpcResponse rpcResponse = serializer.deserialize(result, RpcResponse.class);
            return rpcResponse;
        }
    }

}
