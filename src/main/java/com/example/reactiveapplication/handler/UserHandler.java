package com.example.reactiveapplication.handler;

import com.example.reactiveapplication.dto.req.UserCreate;
import com.example.reactiveapplication.dto.resp.UserResp;
import com.example.reactiveapplication.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class UserHandler {

    private final UserService service;

    public UserHandler(UserService service) {
        this.service = service;
    }

    public Mono<ServerResponse> get(ServerRequest serverRequest) {
        return service.get(Long.valueOf(serverRequest.pathVariable("id")))
                .flatMap(res -> ServerResponse.ok().bodyValue(res))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> create(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(UserCreate.class)
                .flatMap(service::create)
                .flatMap(res -> ServerResponse.ok().bodyValue(res));
    }
}
