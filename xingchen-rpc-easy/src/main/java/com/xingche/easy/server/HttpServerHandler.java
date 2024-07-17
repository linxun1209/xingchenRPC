package com.xingche.easy.server;

import com.xingche.easy.model.RpcRequest;
import com.xingche.easy.model.RpcResponse;
import com.xingche.easy.registry.LocalRegistry;
import com.xingche.easy.serializer.JdkSerializer;
import com.xingche.easy.serializer.Serializer;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @author xing'chen
 * @version 1.0
 * @description: Http请求处理
 * @date 2024/7/16 20:46
 */
public class HttpServerHandler implements Handler<HttpServerRequest>{


    /**
     * 请求
     * @param httpServerRequest
     */
    @Override
    public void handle(HttpServerRequest httpServerRequest) {
        //指定序列化器
        final Serializer serializer=new JdkSerializer();
        //记录日志
        System.out.println("Received request:" +httpServerRequest.method()+" "+httpServerRequest.uri());

        //异步处理 HTTP 请求
        httpServerRequest.bodyHandler(body->{
            byte[] bytes=body.getBytes();
            RpcRequest rpcRequest=null;
            try {
                rpcRequest=serializer.deserialize(bytes,rpcRequest.getClass());

            } catch (IOException e) {
                e.printStackTrace();
            }

            //构造响应结果对象
            RpcResponse rpcResponse=new RpcResponse();
            //如果请求为null，直接返回
            if(rpcResponse==null){
                rpcResponse.setMessage("RPCRequese is null");
                doResponse(httpServerRequest,rpcResponse,serializer);
                return;
            }

            try {
                //获取要调用的服务器实现类，通过反射来调用
                Class<?> implClass = LocalRegistry.get(rpcRequest.getServiceName());
                Method method = implClass.getMethod(rpcRequest.getMethodName(), rpcRequest.getParameterTypes());
                Object invoke = method.invoke(implClass.newInstance(), rpcRequest.getArgs());
                rpcResponse.setData(invoke);
                rpcResponse.setDataType(method.getReturnType());
                rpcResponse.setMessage("ok");
            } catch (Exception e) {
                e.printStackTrace();
                rpcResponse.setMessage(e.getMessage());
                rpcResponse.setException(e);
            }
            doResponse(httpServerRequest,rpcResponse,serializer);
        });

    }


    /**
     * 响应
     * @param request
     * @param rpcResponse
     * @param serializer
     */
    void doResponse(HttpServerRequest request,RpcResponse rpcResponse,Serializer serializer){
        HttpServerResponse httpServerResponse=request.response()
                .putHeader("content-type","application/json");
        try {
            //序列化
            byte[] bytes=serializer.serialize(rpcResponse);
            httpServerResponse.end(Buffer.buffer(bytes));
        }catch (IOException e){
            e.printStackTrace();
            httpServerResponse.end(Buffer.buffer());
        }
    }

}

