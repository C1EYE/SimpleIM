package com.c1eye.imclient.handler;

import com.c1eye.im.bean.msg.ProtoMsg;
import com.c1eye.im.instance.ProtoInstance;
import com.c1eye.imserver.session.ClientSession;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@ChannelHandler.Sharable
@Service("LoginResponseHandler")
public class LoginResponseHandler extends ChannelInboundHandlerAdapter {
    @Autowired
    private ChatMsgHandler chatMsgHandler;
//
//    @Autowired
//    private HeartBeatClientHandler heartBeatClientHandler;


    /**
     * 业务逻辑处理
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        // 判断消息实例
        if (!(msg instanceof ProtoMsg.Message)) {
            super.channelRead(ctx, msg);
            return;
        }

        // 判断类型
        ProtoMsg.Message pkg = (ProtoMsg.Message) msg;
        ProtoMsg.HeadType headType = ((ProtoMsg.Message) msg).getType();
        if (!headType.equals(ProtoMsg.HeadType.LOGIN_RESPONSE)) {
            super.channelRead(ctx, msg);
            return;
        }


        //判断返回是否成功
        ProtoMsg.LoginResponse info = pkg.getLoginResponse();

        ProtoInstance.ResultCodeEnum result =
                ProtoInstance.ResultCodeEnum.values()[info.getCode()];

        if (!result.equals(ProtoInstance.ResultCodeEnum.SUCCESS)) {
            //登录失败
            log.info(result.getDesc());
        } else {
            //登录成功
            ClientSession.loginSuccess(ctx, pkg);
            ChannelPipeline p = ctx.pipeline();
            // 只处理一次，成功后移除登录响应处理器
            p.remove(this);
            //在编码器后面，动态插入心跳处理器
//            p.addAfter("encoder", "heartbeat", heartBeatClientHandler);
            p.addAfter("encoder", "chat", chatMsgHandler);

//            heartBeatClientHandler.channelActive(ctx);

        }

    }

}
