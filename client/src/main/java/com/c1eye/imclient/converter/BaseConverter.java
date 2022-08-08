package com.c1eye.imclient.converter;

import com.c1eye.im.bean.msg.ProtoMsg;
import com.c1eye.imserver.session.ClientSession;

/**
 * @author c1eye
 * time 2022/8/6 15:04
 */
public class BaseConverter {
    // TODO 继承结构重新设计
    protected ProtoMsg.HeadType type;
    private final ClientSession session;

    public BaseConverter(ProtoMsg.HeadType type, ClientSession session) {
        this.type = type;
        this.session = session;
    }

    /**
     * 构建消息 基础部分
     */
    public ProtoMsg.Message buildOuter() {
        return getOuterBuilder().buildPartial();
    }

    public ProtoMsg.Message.Builder getOuterBuilder() {
        return ProtoMsg.Message.newBuilder()
                       .setType(type)
                       .setSessionId(session.getSessionId());
    }



}
