package com.xingche.core.serializer;

import java.io.IOException;

/**
 * @author xing'chen
 * @version 1.0
 * @description: 序列化器接口
 * @date 2024/7/16 20:30
 */
public interface Serializer {

    /**
     * 序列化
     * @param obj
     * @return
     * @param <T>
     * @throws IOException
     */
    <T> byte[] serialize(T obj) throws IOException;


    /**
     * 反序列化
     * @param bytes
     * @param type
     * @return
     * @param <T>
     * @throws IOException
     */
    <T> T deserialize(byte[] bytes,Class<T> type) throws IOException;

}

