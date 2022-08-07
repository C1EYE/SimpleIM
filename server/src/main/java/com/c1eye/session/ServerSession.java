package com.c1eye.session;

import com.c1eye.im.bean.User;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import lombok.Data;
import lombok.Getter;

import java.util.UUID;

/**
 * @author c1eye
 * time 2022/8/6 21:22
 */
@Getter
public class ServerSession {

    public static final AttributeKey<String> KEY_USER_ID = AttributeKey.valueOf("key_user_id");

    public static final AttributeKey<ServerSession> SESSION_KEY = AttributeKey.valueOf("SESSION_KEY");

    private final Channel channel;
    private User user;
    private boolean isLogin;

    //session唯一标示
    private final String sessionId;

    public ServerSession(Channel channel) {
        //完成正向绑定
        this.channel = channel;
        this.sessionId = buildNewSessionId();
    }

    //反向导航
    public static ServerSession getSession(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        return channel.attr(ServerSession.SESSION_KEY).get();
    }

    //反向绑定，最终和channel 通道实现双向绑定
    // 顺便加入到会话集合中
    public ServerSession reverseBind() {
        channel.attr(ServerSession.SESSION_KEY).set(this);
        SessionMap.getInstance().addSession(this);
        isLogin = true;
        return this;
    }

    /**
     * 提供的关闭连接方法
     * @param ctx
     */
    public static void closeSession(ChannelHandlerContext ctx) {

    }

    public boolean isValid() {
        return getUser() != null;
    }

    private String buildNewSessionId() {
        return UUID.randomUUID().toString();
    }

    public void setUser(User user) {
        this.user = user;
        user.setSessionId(sessionId);
    }
}
