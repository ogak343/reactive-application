package com.example.reactiveapplication.config;

import com.example.reactiveapplication.model.ChatMessage;
import com.example.reactiveapplication.model.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class WebSocketDisconnectListenerConfiguration {

    private final SimpMessageSendingOperations messageTemplate;

    @EventListener
    public void handWebSocketDisconnectEvent(SessionDisconnectEvent event) {

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = accessor.getSessionAttributes().get("username").toString();
        if (username!=null) {
            log.info("User disconnected: {}", username);

            ChatMessage chatMessage = ChatMessage.builder().type(MessageType.LEAVE).sender(username).build();

            messageTemplate.convertAndSend("/topic/public", chatMessage);
        }

    }
}
