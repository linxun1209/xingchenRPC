package com.xingchen.core.loadbalancer;

import com.xingchen.core.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author xing'chen
 * @version 1.0
 * @description: 一致性哈希算法轮询器
 * @date 2024/7/23 16:42
 */
public class ConsistentHashLoadBalancer implements LoadBalancer{


    /**
     * 一致性哈希环，存放虚拟节点
     */
    private final TreeMap<Integer,ServiceMetaInfo> virtualNodes=new TreeMap<>();

    /**
     * 虚拟节点数
     */
    private static final int VIRTUAL_NOOE_NUM=100;

    @Override
    public ServiceMetaInfo select(Map<String, Object> requestParams, List<ServiceMetaInfo> serviceMetaInfoList) {
        if(serviceMetaInfoList.isEmpty()){
            return null;
        }
        //构建虚拟节点环
        for(ServiceMetaInfo serviceMetaInfo:serviceMetaInfoList){
            for (int i = 0; i < VIRTUAL_NOOE_NUM; i++) {
                int hash=getHash(serviceMetaInfo.getServiceAddress()+"#"+i);
                virtualNodes.put(hash,serviceMetaInfo);
            }
        }
        //获取调用请求的hash的值
        int hash=getHash(requestParams);
        //选择最接近且大于等于调用请求hash值的虚拟节点
        Map.Entry<Integer,ServiceMetaInfo> entry=virtualNodes.ceilingEntry(hash);
        if(entry==null){
            entry=virtualNodes.firstEntry();
        }
        return entry.getValue();
    }


    /**
     * hash算法，可自行实现
     * @param key
     * @return
     */
    private int getHash(Object key) {
        return key.hashCode();
    }
}

