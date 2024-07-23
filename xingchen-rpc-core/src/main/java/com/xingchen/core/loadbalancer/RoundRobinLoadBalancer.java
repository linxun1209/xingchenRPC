package com.xingchen.core.loadbalancer;

import com.xingchen.core.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xing'chen
 * @version 1.0
 * @description: 轮询负载均衡器
 * @date 2024/7/23 16:37
 */
public class RoundRobinLoadBalancer implements LoadBalancer {


    /**
     * 当前轮询的下标
     */
    private final AtomicInteger currentIndex = new AtomicInteger(0);


    @Override
    public ServiceMetaInfo select(Map<String, Object> requestParams, List<ServiceMetaInfo> serviceMetaInfoList) {
        if(serviceMetaInfoList.isEmpty()){
            return null;
        }
        //只有一个服务，无需轮询
        int size=serviceMetaInfoList.size();
        if(size==1){
            return serviceMetaInfoList.get(0);
        }
        //取模算法轮询
        int index=currentIndex.getAndIncrement()%size;
        return serviceMetaInfoList.get(index);
    }
}

