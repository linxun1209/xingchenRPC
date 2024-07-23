package com.xingchen.core.protocol;

import com.xingchen.core.model.RpcRequest;
import com.xingchen.core.model.RpcResponse;
import com.xingchen.core.serializer.Serializer;
import com.xingchen.core.serializer.SerializerFactory;
import io.vertx.core.buffer.Buffer;

import java.io.IOException;

import static com.xingchen.core.protocol.ProtocolMessageTypeEnum.*;

/**
 * @author xing'chen
 * @version 1.0
 * @description: 协议消息解码器
 * @date 2024/7/22 19:49
 */
public class ProtocolMessageDecoder {
    public static ProtocolMessage<?> decode(Buffer buffer) throws IOException {
        //分别从指定位置读出 buffer
        ProtocolMessage.Header header=new ProtocolMessage.Header();
        byte magic = buffer.getByte(0);
        //校准魔数
        if(magic!=ProtocolConstant.PROTOCOL_MAGIC){
            throw new RuntimeException("消息magic非法");
        }
        header.setMagic(magic);
        header.setType(buffer.getByte(1));
        header.setSerializer(buffer.getByte(2));
        header.setType(buffer.getByte(3));
        header.setStatus(buffer.getByte(4));
        header.setRequestId(buffer.getLong(5));
        header.setBodyLength(buffer.getInt(13));
        //解决了粘包问题，只读指定长度的数据
        byte[] bodyBytes = buffer.getBytes(17, 17 + header.getBodyLength());
        //解析消息体
        ProtocolMessageSerializerEnum serializerEnum = ProtocolMessageSerializerEnum.getEnumByKey(header.getSerializer());
        if(serializerEnum==null){
            throw new RuntimeException("序列化消息的协议不存在");
        }
        Serializer serializer = SerializerFactory.getInstance(serializerEnum.getValue());

        ProtocolMessageTypeEnum messageSerializerEnum = ProtocolMessageTypeEnum.getEnumByKey(header.getType());
        if(messageSerializerEnum==null){
            throw new IOException("序列化消息的类型不存在");
        }
        switch (messageSerializerEnum){
            case REQUEST:
                RpcRequest request = serializer.deserialize(bodyBytes, RpcRequest.class);
                return new ProtocolMessage<>(header,request);
            case RESPONSE:
                RpcResponse rpcResponse = serializer.deserialize(bodyBytes, RpcResponse.class);
                return new ProtocolMessage<>(header,rpcResponse);
            case HEART_BEAT:
            case OTHERS:
                default:
                throw new RuntimeException("暂不支持该消息类型");
        }

    }

}

