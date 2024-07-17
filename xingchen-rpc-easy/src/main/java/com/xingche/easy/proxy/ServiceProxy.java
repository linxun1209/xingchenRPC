package com.xingche.easy.proxy;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.xingche.easy.model.RpcRequest;
import com.xingche.easy.model.RpcResponse;
import com.xingche.easy.serializer.JdkSerializer;
import com.xingche.easy.serializer.Serializer;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author xing'chen
 * @version 1.0
 * @description:  服务代理（JDK 动态代理）
 * @date 2024/7/16 21:26
 */
public class ServiceProxy implements InvocationHandler {


    /**
     * 调用代理
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //指定序列化器
        Serializer serializer=new JdkSerializer();
        //构造请求
        RpcRequest rpcRequest=RpcRequest.builder()
                .serviceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();
        try {

            //序列化
            byte[] bytes = serializer.serialize(rpcRequest);
            //发送请求
            //todo 这里硬编码了（应该是使用注册中心和服务发现机制来解决)
            try (HttpResponse httpResponse = HttpRequest.post("http://localhost:8080")
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

