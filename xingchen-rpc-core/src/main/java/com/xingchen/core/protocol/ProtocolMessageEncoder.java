package com.xingchen.core.protocol;

import com.xingchen.core.serializer.Serializer;
import com.xingchen.core.serializer.SerializerFactory;
import io.vertx.core.buffer.Buffer;

import java.io.IOException;

/**
 * @author xing'chen
 * @version 1.0
 * @description: 编码
 * @date 2024/7/22 19:41
 */
public class ProtocolMessageEncoder {


    /**
     * 编码加密
     * @param protocolMessage
     * @return
     * @throws IOException
     */
    public static Buffer encode(ProtocolMessage<?> protocolMessage) throws IOException{
        if(protocolMessage==null||protocolMessage.getHeader()==null){
            return Buffer.buffer();
        }
        ProtocolMessage.Header header=protocolMessage.getHeader();
        //依次向缓冲区写入字节
        Buffer buffer=Buffer.buffer();
        buffer.appendByte(header.getMagic());
        buffer.appendByte(header.getVersion());
        buffer.appendByte(header.getSerializer());
        buffer.appendByte(header.getType());
        buffer.appendByte(header.getStatus());
        buffer.appendLong(header.getRequestId());
        //获取序列化器
        ProtocolMessageSerializerEnum serializerEnum=ProtocolMessageSerializerEnum.getEnumByKey(header.getSerializer());
        if(serializerEnum==null){
            throw new RuntimeException("序列化协议不存在");
        }
        Serializer serializer = SerializerFactory.getInstance(serializerEnum.getValue());
        byte[] bodyBytes = serializer.serialize(protocolMessage.getBody());
        buffer.appendInt(bodyBytes.length);
        buffer.appendBytes(bodyBytes);
        return buffer;
    }
}

