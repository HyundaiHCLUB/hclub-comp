//package site.hclub.hyndai.service;
//import java.io.IOException;
//import java.util.Collections;
//import java.util.HashSet;
//import java.util.Set;
//import javax.websocket.server.ServerEndpoint;
//import javax.websocket.OnClose;
//import javax.websocket.OnError;
//import javax.websocket.OnMessage;
//import javax.websocket.OnOpen;
//import javax.websocket.Session;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Controller;
//import lombok.extern.log4j.Log4j;
//import javax.websocket.RemoteEndpoint.Basic;
//
//@Log4j 
//@Controller
//@ServerEndpoint(value="/socket")
//public class WebSocketChat {
//	private static Set<Session> clients = Collections.synchronizedSet(new HashSet<>());
//    private static final Logger logger = LoggerFactory.getLogger(WebSocketChat.class);
//    
//    public WebSocketChat() {
//        log.info("웹소켓 서버 객체 생성");
//    }
// 
//    @OnOpen
//    public void onOpen(Session session) {
//        logger.info("Open session id:"+session.getId());
//        try {
//            final Basic basic=session.getBasicRemote();
//            basic.sendText("Connection Established");
//        }catch (Exception e) {
//            log.info(e.getMessage());
//        }
//        clients.add(session);
//    }
//    
//    private void sendAllSessionToMessage(Session self,String message) {
//        try {
//            for(Session session : WebSocketChat.clients) {
//                if(!self.getId().equals(session.getId())) {
//                    session.getBasicRemote().sendText(message.split(",")[1]+" : "+message);
//                }
//            }
//        }catch (Exception e) {
//        	log.info(e.getMessage());
//        }
//    }
//    @OnMessage
//    public void onMessage(String message,Session session) {
//        logger.info("Message From "+message.split(",")[1] + ": "+message.split(",")[0]);
//        try {
//            final Basic basic=session.getBasicRemote();
//            basic.sendText("to : "+message);
//        }catch (Exception e) {
//        	 log.info(e.getMessage());
//        }
//        sendAllSessionToMessage(session, message);
//    }
//    @OnError
//    public void onError(Throwable e,Session session) {
//        
//    }
//    @OnClose
//    public void onClose(Session session) {
//        logger.info("Session "+session.getId()+" has ended");
//    }
//    private void broadcast(String message) {
//        for (Session session : clients) {
//            try {
//                session.getBasicRemote().sendText(message);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}
//
