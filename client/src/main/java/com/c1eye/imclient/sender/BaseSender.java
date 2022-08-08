package com.c1eye.imclient.sender;

import com.c1eye.im.bean.User;
import com.c1eye.im.bean.msg.ProtoMsg;
import com.c1eye.imserver.session.ClientSession;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author c1eye
 * time 2022/8/7 21:25
 */
@Data
@Slf4j
public abstract class BaseSender {
    private User user;
    private ClientSession session;

    /**
     * 发送消息
     *
     * @param message
     */
    public void sendMsg(ProtoMsg.Message message) {


        if (null == getSession() || !isConnected()) {
            log.info("连接还没成功");
            return;
        }

        Channel channel = getSession().getChannel();
        ChannelFuture f = channel.writeAndFlush(message);
        f.addListener(future -> {
            // 回调
            if (future.isSuccess()) {
                sendSucced(message);
            } else {
                sendfailed(message);
            }
        });
    }

    public boolean isConnected() {
        if (null == session) {
            log.info("session is null");
            return false;
        }
        return session.isConnected();
    }

    protected void sendSucced(ProtoMsg.Message message) {
        log.info("发送成功");
    }

    protected void sendfailed(ProtoMsg.Message message) {
        log.info("发送失败");
    }
}
