package com.xingchen.core.fault.tolerant;


import com.xingchen.core.model.RpcResponse;

import java.util.Map;


/**
 * @author xing'chen
 * @version 1.0
 * @description: 快速失败 - 容错策略（立刻通知外层调用方）
 * @date 2024/7/17 10:48
 */
public class FailFastTolerantStrategy implements TolerantStrategy {

    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        throw new RuntimeException("服务报错", e);
    }
}
