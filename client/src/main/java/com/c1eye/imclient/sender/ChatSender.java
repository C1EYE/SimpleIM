package com.c1eye.imclient.sender;

import com.c1eye.im.bean.ChatMsg;
import com.c1eye.im.bean.msg.ProtoMsg;
import com.c1eye.imclient.converter.ChatMessageConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author c1eye
 * time 2022/8/7 21:55
 */
@Slf4j
@Service
public class ChatSender extends BaseSender{

    public void sendChatMsg(String target, String content) {
        log.info("发送消息到: " + target);
        ChatMsg chatMsg = new ChatMsg(getUser());
        chatMsg.setContent(content);
        chatMsg.setMsgType(ChatMsg.MSGTYPE.TEXT);
        chatMsg.setTo(target);
        chatMsg.setMsgId(System.currentTimeMillis());
        ProtoMsg.Message message = ChatMessageConverter.build(chatMsg, getUser(), getSession());

        super.sendMsg(message);
    }

    @Override
    protected void sendSucced(ProtoMsg.Message message) {
        log.info("发送成功:" + message.getMessageRequest().getContent());
    }



    @Override
    protected void sendfailed(ProtoMsg.Message message) {
        log.info("发送失败:" + message.getMessageRequest().getContent());
    }
}
