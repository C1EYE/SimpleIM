package com.c1eye.imclient.sender;

import com.c1eye.im.bean.msg.ProtoMsg;
import com.c1eye.imclient.converter.LoginMsgConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author c1eye
 * time 2022/8/7 22:29
 */
@Service
@Slf4j
public class LoginSender extends BaseSender {

    public void sendLoginMsg() {
        if (!isConnected()) {
            log.info("还没有建立连接!");
            return;
        }

        log.info("构造登录消息");

        ProtoMsg.Message message = LoginMsgConverter.build(getUser(), getSession());
        log.info("发送登录消息: " + getUser());
        sendMsg(message);
    }
}
