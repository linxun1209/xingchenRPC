package com.xingchen.core.fault.tolerant;

import com.xingchen.core.model.RpcResponse;

import java.util.Map;

/**
 * @author xing'chen
 * @version 1.0
 * @description: 容错策略
 * @date 2024/7/17 10:48
 */
public interface TolerantStrategy {

    /**
     * 容错
     *
     * @param context 上下文，用于传递数据
     * @param e       异常
     * @return
     */
    RpcResponse doTolerant(Map<String, Object> context, Exception e);
}