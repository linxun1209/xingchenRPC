package com.xingchen.core.server;

import io.vertx.core.Vertx;

/**
 * @author xing'chen
 * @version 1.0
 * @description: Vertx实例
 * @date 2024/7/16 20:04
 */
public class VertxHttpServer implements HttpServer{
    @Override
    public void doStart(int port) {
        //创建vertx实例
        Vertx vertx=Vertx.vertx();
        //创建HTTP实例
        io.vertx.core.http.HttpServer server = vertx.createHttpServer();
        //监听端口并处理请求
        server.requestHandler(new HttpServerHandler());
        /*
        server.requestHandler(port,request->{
            System.out.println("Received request: "+request.method()+"" +request.uri());
            request.response()
                    .putHeader("content-type","text/plain")
                    .end("hello from Vext.x http server!");
        });
        */
        //启动HTTP服务器并监听指定端口
        server.listen(port,result->{
            if(result.succeeded()){
                System.out.println("Server is now listing on port:"+port);
            }else {
                System.err.println("failed to start server:"+port);
            }
        });
    }
}

