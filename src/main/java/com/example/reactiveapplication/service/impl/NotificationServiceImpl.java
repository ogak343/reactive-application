package com.example.reactiveapplication.service.impl;

import com.example.reactiveapplication.dto.req.EventDto;
import com.example.reactiveapplication.dto.resp.ResponseDto;
import com.example.reactiveapplication.service.NotificationService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final RabbitMQService rabbitMQService;

    public NotificationServiceImpl(RabbitMQService rabbitMQService) {
        this.rabbitMQService = rabbitMQService;
    }

    @Override
    public Mono<ResponseDto> sendEvent(EventDto event) {

        //TODO some logic
        return rabbitMQService.sendEvent(event)
                .then(Mono.just(new ResponseDto(200, "Event has been put in Queue!")));
    }
}
