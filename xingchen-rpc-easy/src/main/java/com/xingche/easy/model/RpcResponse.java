package com.xingche.easy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xing'chen
 * @version 1.0
 * @description:  RPC 响应
 * @date 2024/7/16 20:45
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RpcResponse {
    /**
     * 响应数据
     */
    private Object data;

    /**
     * 响应数据类型（预留）
     */
    private Class<?> dataType;

    /**
     * 响应信息
     */
    private String message;

    /**
     * 异常信息
     */
    private Exception exception;
}

