package com.gabrielaigner.boundary;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.Session;

/**
 * Websockets Example
 *
 * @author Gabriel Aigner
 */
@ApplicationScoped
@ServerEndpoint("/chat/{name}")
public class ChatSocket {

    Map<String, Session> sessions = new ConcurrentHashMap<>();

    @OnMessage
    public void onMessage(String message, @PathParam("name") String name) {
        broadcast(">> " + name + ": " + message);
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("name") String name) {
        System.out.println("Client '" + name +"' connected.");
        sessions.put(name, session);
    }

    @OnClose
    public void onClose(Session session, @PathParam("name") String name) {
        System.out.println("Client '" + name +"' disconnected.");
        sessions.remove(name);
    }

    @OnError
    public void onError(@PathParam("name") String name, Throwable throwable) {
        sessions.remove(name);
    }

    private void broadcast(String message) {
        sessions.values().forEach(session -> {
            session.getAsyncRemote().sendText(message);
        });
    }
}