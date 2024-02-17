package site.hclub.hyndai.domain;

import org.springframework.web.socket.WebSocketSession;

import lombok.Data;

@Data
public class UserMsg {
    public WebSocketSession session;
	public String msg;

	public UserMsg(WebSocketSession tsession,String msg) 
	{
		session = tsession;
		this.msg=msg;
	}
}
