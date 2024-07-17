package com.xingchen.core.conifg;

import com.xingchen.core.serializer.SerializerKeys;
import lombok.Data;

/**
 * @author xing'chen
 * @version 1.0
 * @description: RPC框架配置
 * @date 2024/7/17 10:34
 */
@Data
public class RpcConfig {


    /**
     * 名称
     */
    private String name="xingchen-RPC";


    /**
     * 版本号
     */
    private String version="1.0";

    /**
     * 服务器主机号
     */
    private String serverHost="localhost";

    /**
     * 服务器端口号
     */
    private int serverPort=8080;

    /**
     * 模拟调用
     */
    private boolean mock=false;


    /**
     * 序列化器
     */
    private String serializer= SerializerKeys.JDK;


}

