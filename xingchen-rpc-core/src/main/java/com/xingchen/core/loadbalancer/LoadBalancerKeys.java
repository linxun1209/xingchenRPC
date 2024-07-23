package com.xingchen.core.loadbalancer;

/**
 * @author xing'chen
 * @version 1.0
 * @description: 负载均衡器键名常量
 * @date 2024/7/23 16:35
 */
public interface LoadBalancerKeys {

    /**
     * 轮询
     */
    String ROUND_ROBIN = "roundRobin";

    String RANDOM = "random";

    String CONSISTENT_HASH = "consistentHash";

}
