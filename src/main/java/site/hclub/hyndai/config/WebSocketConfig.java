package site.hclub.hyndai.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import site.hclub.hyndai.service.SocketHandler;


/**
 * @author 김은솔
 * @description: 웹소켓을 위한 Configuration 설정
 * ===========================
	   AUTHOR      NOTE
 * ---------------------------
 *
 * ===========================
 */
@Configuration 
@EnableWebSocket 
public class WebSocketConfig implements WebSocketConfigurer {
    private final SocketHandler webSocketHandler;

    public WebSocketConfig(SocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
    }
    
    /**
	 작성자: 김은솔 
	 처리 내용:   WebSocketHandler 요청 매핑 을 구성한다.
	*/
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
                .addHandler(webSocketHandler, "/ws/chat") // 호출 path 지정
                .setAllowedOrigins("*"); // 허용 도메인
    }
}

