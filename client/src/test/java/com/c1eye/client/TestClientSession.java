package com.c1eye.client;


import com.c1eye.im.bean.User;
import com.c1eye.client.session.ClientSession;
import com.c1eye.util.JsonUtil;
import com.c1eye.util.Logger;
import io.netty.channel.Channel;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.util.Attribute;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class TestClientSession {


    //测试用例： 会话的双向绑定

    @Test
    public void testSessionBind() throws Exception {
        ClientSession session=new ClientSession(new EmbeddedChannel());
        session.setUser(new User());

        Logger.cfo(JsonUtil.pojoToJson(session.getUser()));

        Logger.cfo("eg:正向导航");

        Channel channel=session.getChannel();

        Logger.cfo("eg:反向导航");
        Attribute<ClientSession> r_session = channel.attr(ClientSession.SESSION_KEY);

        Logger.cfo(JsonUtil.pojoToJson(r_session.get().getUser()));

    }


}
