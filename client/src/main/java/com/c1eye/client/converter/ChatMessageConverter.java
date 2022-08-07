package com.c1eye.client.converter;

import com.c1eye.client.session.ClientSession;
import com.c1eye.im.bean.ChatMsg;
import com.c1eye.im.bean.User;
import com.c1eye.im.bean.msg.ProtoMsg;

/**
 * @author c1eye
 * time 2022/8/6 16:32
 */
public class ChatMessageConverter extends BaseConverter {

    private ChatMsg chatMsg;
    private User user;

    private ChatMessageConverter(ClientSession session) {
        initType(ProtoMsg.HeadType.MESSAGE_REQUEST, session);
    }

    public ProtoMsg.Message build(ChatMsg chatMsg, User user) {
        this.chatMsg = chatMsg;
        this.user = user;
        ProtoMsg.Message.Builder outerBuilder = getOuterBuilder();

        ProtoMsg.MessageRequest.Builder cb =
                ProtoMsg.MessageRequest.newBuilder();
        //填充字段
        this.chatMsg.fillMsg(cb);
        return outerBuilder.setMessageRequest(cb).build();
    }
}
