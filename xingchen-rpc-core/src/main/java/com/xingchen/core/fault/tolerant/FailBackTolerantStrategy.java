package com.xingchen.core.fault.tolerant;

import com.xingchen.core.model.RpcResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;


/**
 * @author xing'chen
 * @version 1.0
 * @description: 降级到其他服务 - 容错策略
 * @date 2024/7/17 10:48
 */
@Slf4j
public class FailBackTolerantStrategy implements TolerantStrategy {

    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        // todo 可自行扩展，获取降级的服务并调用
        return null;
    }
}
