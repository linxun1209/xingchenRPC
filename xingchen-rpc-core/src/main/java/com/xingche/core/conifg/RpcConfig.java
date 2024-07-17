package com.xingche.core.conifg;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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


}

