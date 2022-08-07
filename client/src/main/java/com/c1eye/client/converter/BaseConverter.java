package com.c1eye.client.converter;

import com.c1eye.client.session.ClientSession;
import com.c1eye.im.bean.msg.ProtoMsg;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * @author c1eye
 * time 2022/8/6 15:04
 */
public abstract class BaseConverter {
    /**
     * 消息类型
     */
    protected ProtoMsg.HeadType type;
    /**
     * session
     */
    private ClientSession session;

    /**
     * 序列id，暂时没用
     */
    private long seqId;

    /**
     * build 方法，由子类实现
     *
     */
//    public abstract ProtoMsg.Message build();

    public void initType(ProtoMsg.HeadType type,ClientSession session){
        this.type = type;
        this.session = session;
    }
    public ProtoMsg.Message.Builder getOuterBuilder() {

        ProtoMsg.Message.Builder mb =
                ProtoMsg.Message.newBuilder()
                                .setType(type)
                                .setSessionId(session.getSessionId())
                                .setSequence(seqId);
        return mb;
    }

}
