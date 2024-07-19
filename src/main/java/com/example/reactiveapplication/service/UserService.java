package com.example.reactiveapplication.service;

import com.example.reactiveapplication.dto.req.UserCreate;
import com.example.reactiveapplication.dto.resp.UserResp;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<UserResp> create(UserCreate dto);

    Mono<UserResp> get(Long id);
}
