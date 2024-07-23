package com.xingchen.core.server.tcp;

import com.xingchen.core.protocol.ProtocolConstant;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.parsetools.RecordParser;

/**
 * @author xing'chen
 * @version 1.0
 * @description: 装饰者模式（使用 recordParser 对原有的 buffer 处理能力进行增强）
 * @date 2024/7/23 10:42
 */
public class TcpBufferHandlerWrapper implements Handler<Buffer> {




    /**
     * 解析器，用于解决半包、粘包问题
     */
    private final RecordParser recordParser;

    public TcpBufferHandlerWrapper(Handler<Buffer> bufferHandler) {
        recordParser = initRecordParser(bufferHandler);
    }

    @Override
    public void handle(Buffer buffer) {
        recordParser.handle(buffer);
    }
    private RecordParser initRecordParser(Handler<Buffer> bufferHandler){
        //构造parser
        RecordParser parser = RecordParser.newFixed(ProtocolConstant.MESSAGE_HEADER_LENGTH);
        parser.setOutput(new Handler<Buffer>() {
            //初始化
            int size=-1;
            //一次完整的读取（头+体）
            Buffer resultBuffer=Buffer.buffer();
            @Override
            public void handle(Buffer buffer) {
                if(size==-1){
                    //读取消息体成都
                    size=buffer.getInt(13);
                    parser.fixedSizeMode(size);
                    //写入头消息到结果
                    resultBuffer.appendBuffer(buffer);
                }else {
                    //写入体消息到结果
                    resultBuffer.appendBuffer(buffer);
                    //已拼接为完整的buffer，执行处理
                    bufferHandler.handle(resultBuffer);
                    //重置一次
                    parser.fixedSizeMode(ProtocolConstant.MESSAGE_HEADER_LENGTH);
                    size=-1;
                    resultBuffer=Buffer.buffer();
                }
            }
        });
        return parser;
    }
}

