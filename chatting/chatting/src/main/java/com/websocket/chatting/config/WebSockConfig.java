package com.websocket.chatting.config;

import com.websocket.chatting.config.handler.StompHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**WebSockConfig 클래스는 WebSocket 메시지 브로커를 설정하는 역할을 한다.
 * STOMP 프로토콜을 사용하여 WebSocket 연결을 관리하고, 메시지를 전송하는 경로를 정의하고,
 * STOMP 핸들러를 통해 WebSocket 요청의 보안 및 기타 처리 작업을 관리*/

@RequiredArgsConstructor
@Configuration
@EnableWebSocketMessageBroker
public class WebSockConfig implements WebSocketMessageBrokerConfigurer {

    private final StompHandler stompHandler;
    /** stompHandler는 configureClientInboundChannel 메서드를 통해 클라이언트의 Inbound 채널에서 사용됨*/

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/sub");
        config.setApplicationDestinationPrefixes("/pub");
    }
    // /sub : 서버에서 클라이언트로 메시지를 전달할 때 사용하는 구독 경로
    // /pub : 클라이언트가 서버로 메시지를 발행할 때 사용하는 구독경로

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-stomp").setAllowedOrigins("http://localhost:8080")
                .withSockJS(); // sock.js를 통하여 낮은 버전의 브라우저에서도 websocket이 동작할수 있게 한다.
    }
    // STOMP 엔드포인트를 등록하는 메서드
    /**setAllowedOrigins("localhost:8080") 이 설정은 CORS(Cross-Origin Resource Sharing) 정책을 설정하여,
     * WebSocket 연결을 허용하도록 함. 이는 로컬 환경에서 주로 사용되며, 실제 배포 환경에서는 도메인에 맞게 변경*/

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(stompHandler);
    }
}