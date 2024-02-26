package site.hclub.hyndai.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;
import site.hclub.hyndai.service.SocketHandler;

/**
 * @author 김은솔
 * @description: 웹소켓을 위한 Configuration 설정
 * ===========================
	   AUTHOR      NOTE
 * ---------------------------
 *    김은솔         최초생성
 * ===========================
 */
@Configuration 
@EnableWebSocket 
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer, WebSocketConfigurer {
    private final SocketHandler webSocketHandler;

    public WebSocketConfig(SocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").withSockJS();
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