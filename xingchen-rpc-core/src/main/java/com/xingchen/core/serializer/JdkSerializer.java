package com.xingchen.core.serializer;

import java.io.*;

/**
 * @author xing'chen
 * @version 1.0
 * @description: JDK序列化器
 * @date 2024/7/16 20:34
 */
public class JdkSerializer implements Serializer{

    @Override
    public <T> byte[] serialize(T obj) throws IOException {
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream=new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(obj);
        objectOutputStream.close();
        return outputStream.toByteArray();
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> type) throws IOException {
        ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream=new ObjectInputStream(byteArrayInputStream);
        try{
            return (T)objectInputStream.readObject();
        }catch (ClassNotFoundException e){
            throw new RuntimeException();
        }finally {
            objectInputStream.close();
        }
    }
}

