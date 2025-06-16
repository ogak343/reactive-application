package com.example.reactiveapplication.service;

import com.example.reactiveapplication.dto.req.EventDto;
import com.example.reactiveapplication.dto.resp.ResponseDto;
import reactor.core.publisher.Mono;

public interface NotificationService {
    Mono<ResponseDto> sendEvent(EventDto event);
}
