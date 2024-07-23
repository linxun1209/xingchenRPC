package com.xingchen.core.fault.retry;

/**
 * @author xing'chen
 * @version 1.0
 * @description: 重试策略键名常量
 * @date 2024/7/23 20:22
 */
public interface RetryStrategyKeys {

    /**
     * 不重试
     */
    String NO = "no";

    /**
     * 固定时间间隔
     */
    String FIXED_INTERVAL = "fixedInterval";

}


