package com.c1eye.imclient.client;

import com.c1eye.imclient.converter.LoginMsgConverter;
import com.c1eye.im.bean.User;
import com.c1eye.im.bean.msg.ProtoMsg;
import com.c1eye.imserver.session.ClientSession;
import com.c1eye.util.Logger;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import java.io.IOException;
import java.util.UUID;

public class TestProtobufConverter
{


    @Test
    public void testLoginMsgConverter() throws IOException
    {


        ProtoMsg.Message message =
                new LoginMsgConverter(getUser(), getSession()).build();

        Logger.info("session id:=" + message.getSessionId());
        ProtoMsg.LoginRequest pkg=  message.getLoginRequest();
        Logger.info("id:=" + pkg.getUid());
        Logger.info("content:=" + pkg.getToken());
    }

    private ClientSession getSession() {
        // 创建会话
        ClientSession session=new ClientSession(new EmbeddedChannel());

        session.setConnected(true);
        return session;
    }

    private User getUser() {

        User user = new User();
        user.setUid("1");
        user.setToken(UUID.randomUUID().toString());
        user.setDevId(UUID.randomUUID().toString());
        return  user;

    }

}
