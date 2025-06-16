package com.example.reactiveapplication.handler;

import com.example.reactiveapplication.dto.req.EventDto;
import com.example.reactiveapplication.service.NotificationService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class NotificationHandler {

    private final NotificationService service;

    public NotificationHandler(NotificationService service) {
        this.service = service;
    }

    public Mono<ServerResponse> sendEvent(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(EventDto.class)
                .flatMap(service::sendEvent)
                .flatMap(res -> ServerResponse.ok().bodyValue(res));
    }
}
