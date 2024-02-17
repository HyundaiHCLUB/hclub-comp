package site.hclub.hyndai.service;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.extern.log4j.Log4j;
import site.hclub.hyndai.domain.UserMsg;

@Log4j 
@Component
public class SocketHandler extends TextWebSocketHandler implements InitializingBean{
	public static SocketHandler sh=null;
	private static final Logger logger = LoggerFactory.getLogger(SocketHandler.class);
    public Set<WebSocketSession> sessionSet = new HashSet<WebSocketSession>();//접속자 소켓들
    public static Queue<UserMsg> msgList = new LinkedList<>();
    
    public SocketHandler() {
    	sh=this;
        log.info("웹소켓 서버 객체 생성");
    }

    // onopen : 사용자가(브라우저) 웹소켓 서버에 붙게되면 호출
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("afterConnectionEstablished~~~~~~~~~~~~~~~~~~~~~~");
        synchronized(sessionSet){
			sessionSet.add(session);				
		}
        JSONObject obj=new JSONObject();
        obj.put("protocol", "doLogin");
        //정상적으로 접속된 경우 Client에게 패킷 전송
        this.sendMessageToMe(session, obj);
        try {
        } catch (Exception e) {
            //Log.print("afterConnectionEstablished err! "+e, 0, "err");
        }
    }
    
    // 접속이 끊어진 사용자가 발생하면 호출
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("afterConnectionClosed~~~~~~~~~~~~~~~~~~~~~~");
		synchronized(sessionSet){
			this.sessionSet.remove(session);
		}
    }
	
    // CLIENTS 객체에 담긴 세션값들을 가져와서 반복문을 통해 메시지를 발송
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String msg = "" + message.getPayload();
        System.out.println("handleTextMessage~~~~~~~~~~~~~~~~~~~~~~");
		//System.out.println((new Date()).toLocaleString() +" msg:"+msg);
        synchronized(msgList) {
            msgList.add(new UserMsg(session,msg));
        }
    }
    
    //제일 처음 실행되는 함수
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("chatsocket aftersiervice~~~~~~~~~~~~~~~~~~");
		try {
			//초기 설정이 필요한 경우 대비 function
			init();	
			
 		} catch (Exception e) {
			//Log.print("afterPropertiesSet init err! " + e, 0, "err");
		}
		Thread thread = new Thread() {
			@Override
			public void run() {
				while (true) {
					try {
						
						Thread.sleep(10);
						//초기에 메시지가 존재하는지 계속 체크
						int msize = msgList.size();
						if(msize > 0){
							for(int i=0;i<3000;i++)
								cmdProcess();
						}
					} catch (Exception e) {
					}
				}
			}
		};
		thread.start(); 
	}
	
    public void cmdProcess() throws Exception {
		UserMsg um = null;
		
		synchronized (msgList) {
			//메시지가 있을 경우
			if (msgList.size() > 0) {
				//메시지를 꺼낸다
				um = msgList.poll();
			}
		}
		if (um == null)
			return;
		
		WebSocketSession session = um.session;
		String msg = um.msg;

		System.out.println("cmdProcess~~~~~~:"+msg);
		// =================================================} msg
		JSONParser p = new JSONParser();
		JSONObject obj = null;
		
		try {
			//꺼낸 메시지를 JSON Object의 형태로  꺼낸다.
			obj = (JSONObject) p.parse(msg);
		} catch (Exception e) {
			System.out.println("parse Exception:"+e.toString());
			return;
		}

		System.out.println("obj = "+obj);
		
		String protocol = obj.get("protocol").toString();  
		
		switch(protocol){
			case "login":OnLogin(session, obj); return;
			case "createRoom":createRoom(session, obj); return;
			case "sendChat":sendChat(session, obj); return;
		}
	}	
    
    public void sendMessageToMe(WebSocketSession session, JSONObject obj) {
        if(session == null)
            return;
        
        if (session.isOpen() ) {
            try {
                synchronized(session){
                    session.sendMessage(new TextMessage(obj.toString()));
                }
            } catch (Exception e) {
                //Log.print("err sendMessageToMe", 1, "err");
            }
        }
    }
    
    public void sendMessageToMeAllBrowser(JSONObject obj) {
        String idx = ""+obj.get("otherUseridx");
        for (WebSocketSession session : sessionSet) {
            try {
                if (session.isOpen()) {
                    Map<String, Object> m = session.getAttributes();   
                    //세션에 있는 유저중, 내가 찾는 유저에게만 메시지 전송
                    if( m.get("userIdx") != null && (""+m.get("userIdx")).compareTo(idx) ==0 ){
                        session.sendMessage(new TextMessage(obj.toString()));
                    }
                }
            } catch (Exception e) {
                
            }
        }           
    }
    public void init(){
		try {
			
			System.out.println("sysout init");
		} catch (Exception e) {
			//Log.print("init err! "+e, 0, "err");
		}
	}    
    
	//로그인	
	private void OnLogin(WebSocketSession session, JSONObject obj){
        try {
            String userIdx = obj.get("userIdx").toString();
			//String level = obj.get("level").toString();
            System.out.println("로그인성공 userIdx:"+userIdx);
            //웹소켓 세션 -> n번의 회원번호를 가진 회원임을 설정
			Map<String, Object> m = session.getAttributes();
            m.put("userIdx", userIdx);
			//m.put("level", level);//일반회원			
        } catch (Exception e) {
        }
    }
	
	private void createRoom(WebSocketSession session, JSONObject obj){
        try {
        	System.out.println("createRoom");
        	Map<String, Object> m = session.getAttributes();
			String userIdx = ""+m.get("userIdx"); //본인번호 
			
            String otherUseridx = obj.get("otherUseridx").toString();//상대번호	
            //상대방대화내역 디비에서 조회한후 넣기 => list로 넣기 
            
            JSONObject newObj=new JSONObject();
            newObj.put("protocol", "showRoom");
            newObj.put("otherUseridx", otherUseridx);
            //상대방대화내역
            this.sendMessageToMe(session, newObj);			
        } catch (Exception e) {
        }
    }
	
	private void sendChat(WebSocketSession session, JSONObject obj){
        try {
        	System.out.println("sendChat");
        	Map<String, Object> m = session.getAttributes();
			String userIdx = ""+m.get("userIdx"); //본인번호 
			System.out.println("sendChat1 userIdx:"+userIdx);
            String otherUseridx = obj.get("otherUseridx").toString();//상대번호	
            System.out.println("sendChat2 otherUseridx:"+otherUseridx);
            String chatMsg = obj.get("chatMsg").toString();//상대번호	
            System.out.println("sendChat3 chatMsg:"+chatMsg);
            
            //상대방대화내역 디비에 저장
            
            JSONObject newObj=new JSONObject();
            newObj.put("protocol", "sendChat");
            newObj.put("chatMsg", chatMsg);
            newObj.put("fromUser", userIdx);
            newObj.put("toUser", otherUseridx);
            System.out.println("sendChat4");
            
            //상대방대화내역
            
            System.out.println("sendChat 5");
            this.sendMessageToMeAllBrowser(newObj);	//상대방에게 전송
            System.out.println("sendChat 6");
            sendMessageToMe(session, newObj);//나에게 전송
            System.out.println("sendChat 7");
        } catch (Exception e) {
        	System.out.println("e:"+e.getMessage());
        }
    }
}

