package test.cases;


import com.c1eye.im.bean.User;
import com.c1eye.imserver.session.ServerSession;
import com.c1eye.imserver.session.SessionMap;
import com.c1eye.util.JsonUtil;
import com.c1eye.util.Logger;
import io.netty.channel.Channel;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.util.Attribute;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.List;

@Slf4j
public class TestServerSession {


    //测试用例： 会话的双向绑定

    @Test
    public void testSessionBind() throws Exception {
        ServerSession serverSession=new ServerSession(new EmbeddedChannel());
        serverSession.setUser(new User());

        Logger.cfo(JsonUtil.pojoToJson(serverSession.getUser()));
        serverSession.reverseBind();

        Channel channel=serverSession.getChannel();

        Logger.cfo("eg:反向导航");
        Attribute<ServerSession> session = channel.attr(ServerSession.SESSION_KEY);
        Logger.cfo(JsonUtil.pojoToJson(session.get().getUser()));

    }


    //测试用例： 会话的查找

    @Test
    public void testSessionFind() throws Exception {
        User user=new User();
        JsonUtil.pojoToJson(user);
        for (int i = 0; i < 10; i++) {
            ServerSession serverSession=new ServerSession(new EmbeddedChannel());
            serverSession.setUser(user);
            serverSession.reverseBind();
        }
        Logger.cfo("eg:用户查找");
        List<ServerSession> list = SessionMap.getInstance().getSessionsBy(user.getUid());
        Logger.cfo("找到的session数量："+list.size());
        list.forEach(s->Logger.cfo( s.getSessionId()));
    }

}
