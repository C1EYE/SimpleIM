package com.c1eye.imclient.converter;

import com.c1eye.im.bean.User;
import com.c1eye.im.bean.msg.ProtoMsg;
import com.c1eye.imserver.session.ClientSession;

/**
 * 心跳消息 Converter
 */
public class HeartBeatMsgConverter extends BaseConverter {
    private final User user;

    public HeartBeatMsgConverter(User user, ClientSession session) {
        super(ProtoMsg.HeadType.HEART_BEAT, session);
        this.user = user;
    }

    public ProtoMsg.Message build() {

        ProtoMsg.Message.Builder outerBuilder = getOuterBuilder();

        ProtoMsg.MessageHeartBeat.Builder inner =
                ProtoMsg.MessageHeartBeat.newBuilder()
                                         .setSeq(0)
                                         .setJson("{\"from\":\"client\"}")
                                         .setUid(user.getUid());
        return outerBuilder.setHeartBeat(inner).build();
    }

}

