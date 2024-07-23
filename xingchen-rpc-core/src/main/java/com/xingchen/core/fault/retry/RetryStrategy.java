package com.xingchen.core.fault.retry;

import com.xingchen.core.model.RpcResponse;

import java.util.concurrent.Callable;

/**
 * @author xing'chen
 * @version 1.0
 * @description: 重试策略
 * @date 2024/7/23 20:15
 */
public interface RetryStrategy {
    /**
     * 重试
     *
     * @param callable
     * @return
     * @throws Exception
     */
    RpcResponse doRetry(Callable<RpcResponse> callable) throws Exception;
}

