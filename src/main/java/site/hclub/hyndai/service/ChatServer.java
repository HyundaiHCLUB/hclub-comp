package site.hclub.hyndai.service;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@ServerEndpoint("/chat")
public class ChatServer {

    private static Set<Session> clients = Collections.synchronizedSet(new HashSet<>());

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        session.getUserProperties().put("username", username);
        clients.add(session);
        broadcast(username + " joined the chat");
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        String username = (String) session.getUserProperties().get("username");
        broadcast(username + ": " + message);
    }

    @OnClose
    public void onClose(Session session) {
        String username = (String) session.getUserProperties().get("username");
        clients.remove(session);
        broadcast(username + " left the chat");
    }

    @OnError
    public void onError(Throwable error) {
        error.printStackTrace();
    }

    private void broadcast(String message) {
        for (Session session : clients) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
