package com.c1eye.im.codec;

import com.c1eye.im.bean.msg.ProtoMsg;
import com.c1eye.im.exception.InvalidFrameException;
import com.c1eye.im.instance.ProtoInstance;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author c1eye
 * time 2022/8/3 14:06
 */
public class SimpleProtobufDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(
            ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        Object msg = decode(channelHandlerContext, byteBuf);
        if(msg != null){
            // 向后传递
            list.add(msg);
        }

    }

    private Object decode(ChannelHandlerContext ctx, ByteBuf in) throws InvalidFrameException,
                                                                        InvalidProtocolBufferException {
        // 标记当前读取位置，方便回滚
        in.markReaderIndex();
        // 判断包头部长度 这里使用的是Head-Content协议
        // 如果不满足协议头部长度就返回空
        if (in.readableBytes() < 8) {
            return null;
        }
        // 进行数据包校验
        short magic = in.readShort();
        if (magic != ProtoInstance.MAGIC_CODE) {
            String error = "客户端口令不对:" + ctx.channel().remoteAddress();
            throw new InvalidFrameException(error);
        }
        short version = in.readShort();
        if (version != ProtoInstance.VERSION_CODE) {
            String error = "协议的版本不对:" + ctx.channel().remoteAddress();
            throw new InvalidFrameException(error);
        }

        // 读取传送过来的消息的长度。
        int length = in.readInt();

        // 长度如果小于0
        if (length < 0) {
            // 非法数据，关闭连接
            ctx.close();
        }
        // 这里处理半包
        if (in.readableBytes() < length) {
            // 重置读取位置
            in.resetReaderIndex();
            return null;
        }

        byte[] arr = null;
        if(in.hasArray()){
            // 如果是堆缓冲区
            //TODO 这里暂时先一样处理
            arr = new byte[length];
            in.readBytes(arr,0,length);
        }else{
            // 直接内存
            arr = new byte[length];
            in.readBytes(arr, 0, length);
        }
        // 将字节转换为POJO
        return ProtoMsg.Message.parseFrom(arr);
    }
}
