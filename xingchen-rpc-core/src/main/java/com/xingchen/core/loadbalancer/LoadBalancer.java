package com.xingchen.core.loadbalancer;

import com.xingchen.core.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;

/**
 * @author xing'chen
 * @version 1.0
 * @description: 负载均衡器（消费端使用）
 * @date 2024/7/23 16:35
 */
public interface LoadBalancer {


    /**
     * 选择服务调用
     * @param requestParams
     * @param serviceMetaInfoList
     * @return
     */
    ServiceMetaInfo select(Map<String,Object> requestParams, List<ServiceMetaInfo> serviceMetaInfoList);

}

