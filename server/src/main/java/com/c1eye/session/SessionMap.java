package com.c1eye.session;

import com.c1eye.im.bean.User;
import com.c1eye.util.Logger;

import javax.swing.text.Style;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 保存session的map
 *
 * @author c1eye
 * time 2022/8/6 21:27
 */
public final class SessionMap {
    private SessionMap() {
    }

    private static final SessionMap INSTANCE = new SessionMap();

    /**
     * sessionId -> session
     */
    private final ConcurrentHashMap<String, ServerSession> map = new ConcurrentHashMap<>();

    public static SessionMap getInstance() {
        return INSTANCE;
    }

    public void addSession(ServerSession s) {
        map.put(s.getSessionId(), s);
    }

    public ServerSession getSession(String sessionId) {
        return map.get(sessionId);
    }

    /**
     * 根据用户id，获取session对象
     */
    public List<ServerSession> getSessionsBy(String userId) {
        //TODO 效率太低
        return map.values()
                  .stream()
                  .filter(s -> s.getUser().getUid().equals(userId))
                  .collect(Collectors.toList());
    }

    public void removeSession(String sessionId) {
        map.remove(sessionId);
    }


}
