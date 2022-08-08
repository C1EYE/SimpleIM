package com.c1eye.imserver.session;

import com.c1eye.im.bean.User;
import com.c1eye.im.bean.msg.ProtoMsg;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * 客户端的session
 *
 * @author c1eye
 * time 2022/5/12 14:47
 */
@Slf4j
@Data
public class ClientSession {
    /**
     * 从netty获取
     */
    public static final AttributeKey<ClientSession> SESSION_KEY = AttributeKey.valueOf("SESSION_KEY");

    private Channel channel;
    private User user;

    private String sessionId;

    private boolean isConnected = false;
    private boolean isLogin = false;

    /**
     * 创建时就绑定session
     *
     * @param channel
     */
    public ClientSession(Channel channel) {
        //正向的绑定
        this.channel = channel;
        this.sessionId = UUID.randomUUID().toString();
        //反向的绑定
        channel.attr(ClientSession.SESSION_KEY).set(this);
    }

    //    登录成功之后,设置sessionId
    public static void loginSuccess(ChannelHandlerContext ctx, ProtoMsg.Message pkg) {
        Channel channel = ctx.channel();
        ClientSession session = channel.attr(ClientSession.SESSION_KEY).get();
        session.setSessionId(pkg.getSessionId());
        session.setLogin(true);
        log.info("登录成功: " + session.getUser().getNickName() + " " + System.nanoTime());
    }

    //获取channel
    public static ClientSession getSession(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        return channel.attr(ClientSession.SESSION_KEY).get();
    }

    public String getRemoteAddress() {
        return channel.remoteAddress().toString();
    }

    //关闭通道
    public void close() {
        isConnected = false;

        ChannelFuture future = channel.close();
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    log.error("连接顺利断开");
                }
            }
        });
    }
}
