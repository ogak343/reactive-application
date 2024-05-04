package com.example.reactiveapplication.service;

import com.example.reactiveapplication.dto.Notification;
import com.example.reactiveapplication.dto.NotificationReqDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface NotificationService {
    Mono<Notification> create(NotificationReqDto reqDto);

    Flux<Notification> getAll();

    Mono<Notification> get(Long id);
}
