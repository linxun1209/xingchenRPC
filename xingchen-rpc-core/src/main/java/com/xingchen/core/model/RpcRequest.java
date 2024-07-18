package com.xingchen.core.model;


import com.xingchen.core.constant.RpcContant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xing'chen
 * @version 1.0
 * @description: RPC请求
 * @date 2024/7/16 20:41
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RpcRequest implements Serializable {


    /**
     * 服务器名称
     */
    private String serviceName;


    /**
     * 方法名称
     */
    private String methodName;


    /**
     * 服务版本
     */
    private String serviceVersion = RpcContant.DEFAULT_SERVICE_VERSION;


    /**
     * 参数类型列表
     */
    private Class<?>[] parameterTypes;


    /**
     * 参数列表
     */
    private Object[] args;

}

