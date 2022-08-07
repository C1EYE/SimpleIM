package com.c1eye.im.codec;

import com.c1eye.im.bean.msg.ProtoMsg;
import com.c1eye.im.instance.ProtoInstance;
import com.c1eye.util.Logger;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author c1eye
 * time 2022/8/3 14:05
 */
public class SimpleProtobufEncoder extends MessageToByteEncoder<ProtoMsg.Message> {


    @Override
    protected void encode(
            ChannelHandlerContext channelHandlerContext, ProtoMsg.Message message, ByteBuf byteBuf) throws Exception {
        encode(message, byteBuf);
    }

    public static void encode(ProtoMsg.Message message, ByteBuf out) {
        out.writeShort(ProtoInstance.MAGIC_CODE);
        out.writeShort(ProtoInstance.VERSION_CODE);

        // 将 ProtoMsg.Message 对象转换为byte
        byte[] bytes = message.toByteArray();

        //TODO 加密消息体
        /*
        ThreeDES des = channel.channel().attr(Constants.ENCRYPT).get();
        byte[] encryptByte = des.encrypt(bytes);
        */

        // 读取消息的长度
        int length = bytes.length;

        Logger.cfo("encoder length=" + length);

        // 先将消息长度写入，也就是消息头
        out.writeInt(length);
        // 消息体中包含我们要发送的数据
        out.writeBytes(bytes);

/*        log.debug("send "
                + "[remote ip:" + ctx.channel().remoteAddress()
                + "][total length:" + length
                + "][bare length:" + msg.getSerializedSize() + "]");*/
    }
}
