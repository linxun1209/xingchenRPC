package com.xingchen.core.serializer;

import com.xingchen.core.spi.SpiLoader;

/**
 * @author xing'chen
 * @version 1.0
 * @description: 序列化器工厂
 * @date 2024/7/17 15:44
 */
public class SerializerFactory {


    static {
        SpiLoader.load(Serializer.class);
    }



    /*

   序列化映射（用于实现单例）

    private static final Map<String,Serializer> KEY_SERIALIZERS_MAP = new HashMap<String,Serializer>(){{
        put(SerializerKeys.JDK,new JdkSerializer());
        put(SerializerKeys.HESSIAN,new HessianSerializer());
        put(SerializerKeys.JSON,new JsonSerializer());
        put(SerializerKeys.HESSIAN,new HessianSerializer());
    }};
     */



    /**
     * 默认序列化器
     */
    private static final Serializer DEFAULT_SERIALIZER = new JdkSerializer();


    /**
     * 获取实例
     * @param key
     * @return
     */
    public static Serializer getInstance(String key){
        return SpiLoader.getInstance(Serializer.class,key);
    }


}

