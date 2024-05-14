package com.javawhizz.App.config;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class WebSocketSessionManager {
    private final Set<WebSocketSession> webSocketSessions = new HashSet<>();

    public Set<WebSocketSession> getWebSocketSessionsExcept(WebSocketSession webSocketSession){
        return this.webSocketSessions.
                stream().
                filter(x -> !x.getId().equalsIgnoreCase(webSocketSession.getId())).
                collect(Collectors.toSet());
    }

    public void addWebSocketSession(WebSocketSession webSocketSession){
        this.webSocketSessions.add(webSocketSession);
    }

    public void removeWebSocketSession(WebSocketSession webSocketSession){
        var matchingSessions = this.webSocketSessions.
                stream().
                filter(x -> x.getId().equalsIgnoreCase(webSocketSession.getId())).
                collect(Collectors.toSet());

        this.webSocketSessions.removeAll(matchingSessions);
    }
}
