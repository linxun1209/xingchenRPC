package com.xingchen.core.registry;

import com.xingchen.core.conifg.RegistryConfig;
import com.xingchen.core.model.ServiceMetaInfo;

import java.util.List;

/**
 * @author xing'chen
 * @version 1.0
 * @description: 注册中心
 * @date 2024/7/18 10:00
 */
public interface Registry {

    /**
     * 初始化
     *
     * @param registryConfig
     */
    void init(RegistryConfig registryConfig);

    /**
     * 注册服务（服务端）
     *
     * @param serviceMetaInfo
     */
    void register(ServiceMetaInfo serviceMetaInfo) throws Exception;

    /**
     * 注销服务（服务端）
     *
     * @param serviceMetaInfo
     */
    void unRegister(ServiceMetaInfo serviceMetaInfo);

    /**
     * 服务发现（获取某服务的所有节点，消费端）
     *
     * @param serviceKey 服务键名
     * @return
     */
    List<ServiceMetaInfo>   serviceDiscovery(String serviceKey);

    /**
     * 服务销毁
     */
    void destroy();
}

