package com.xingche.core.server;

/**
 * @author xing'chen
 * @version 1.0
 * @description: 服务器端口
 * @date 2024/7/16 20:02
 */
public interface HttpServer {


    /**
     * 启动服务器
     * @param port
     */
    void doStart(int port);
}

