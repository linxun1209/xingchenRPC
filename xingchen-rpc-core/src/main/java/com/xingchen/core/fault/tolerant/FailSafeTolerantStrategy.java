package com.xingchen.core.fault.tolerant;

import com.xingchen.core.model.RpcResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author xing'chen
 * @version 1.0
 * @description: 静默处理异常 - 容错策略
 * @date 2024/7/17 10:48
 */
@Slf4j
public class FailSafeTolerantStrategy implements TolerantStrategy {

    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        log.info("静默处理异常", e);
        return new RpcResponse();
    }
}
