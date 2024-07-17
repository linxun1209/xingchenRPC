package com.xingchen.consumer;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.xingche.easy.model.RpcRequest;
import com.xingche.easy.model.RpcResponse;
import com.xingche.easy.serializer.JdkSerializer;
import com.xingche.easy.serializer.Serializer;
import com.xingche.easy.server.HttpServerHandler;
import com.xingchen.common.model.User;
import com.xingchen.common.service.UserService;

import java.io.IOException;

/**
 * @author xing'chen
 * @version 1.0
 * @description: 静态代理
 * @date 2024/7/16 21:10
 */
public class UserServiceProxy implements UserService {


    @Override
    public User getUser(User user) {
        Serializer serializer=new JdkSerializer();

        RpcRequest rpcRequest= RpcRequest.builder()
                .serviceName(UserService.class.getName())
                .methodName("getUSer")
                .parameterTypes(new Class[]{User.class})
                .args(new Object[]{user})
                .build();
        try {
            byte[] bytes = serializer.serialize(rpcRequest);
            byte[] result;
            try (HttpResponse httpRequest = HttpRequest.post("http://localhosrt:8080")
                    .body(bytes)
                    .execute()) {
                result = httpRequest.bodyBytes();
            }
            RpcResponse rpcResponse = serializer.deserialize(result, RpcResponse.class);
            return (User) rpcResponse.getData();
        }catch (IOException e){
                e.printStackTrace();
        }
        return null;
    }


}

