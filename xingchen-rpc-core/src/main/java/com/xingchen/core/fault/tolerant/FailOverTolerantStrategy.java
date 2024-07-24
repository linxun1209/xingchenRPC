package com.xingchen.core.fault.tolerant;

import com.xingchen.core.model.RpcResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author xing'chen
 * @version 1.0
 * @description: 转移到其他服务节点 - 容错策略
 * @date 2024/7/17 10:48
 */
@Slf4j
public class FailOverTolerantStrategy implements TolerantStrategy {

    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        // todo 可自行扩展，获取其他服务节点并调用
        return null;
    }
}
