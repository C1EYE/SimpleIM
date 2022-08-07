package com.c1eye.client.converter;

import com.c1eye.client.session.ClientSession;
import com.c1eye.im.bean.User;
import com.c1eye.im.bean.msg.ProtoMsg;

/**
 * @author c1eye
 * time 2022/8/6 15:31
 */
public class LoginMsgConverter extends BaseConverter {

    /**
     * 登录用户
     */
    private final User user;

    public LoginMsgConverter(User user, ClientSession session) {
        initType(ProtoMsg.HeadType.LOGIN_REQUEST, session);
        this.user = user;
    }

    public ProtoMsg.Message build() {
        ProtoMsg.Message.Builder outerBuilder = getOuterBuilder();


        ProtoMsg.LoginRequest.Builder lb =
                ProtoMsg.LoginRequest.newBuilder()
                                     .setDeviceId(user.getDevId())
                                     .setPlatform(user.getPlatform().ordinal())
                                     .setToken(user.getToken())
                                     .setUid(user.getUid());

        return outerBuilder.setLoginRequest(lb).build();
    }
}
