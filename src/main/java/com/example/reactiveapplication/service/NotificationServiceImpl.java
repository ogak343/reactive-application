package com.example.reactiveapplication.service;

import com.example.reactiveapplication.dto.Notification;
import com.example.reactiveapplication.dto.NotificationReqDto;
import com.example.reactiveapplication.repo.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository repository;

    @Override
    public Mono<Notification> create(NotificationReqDto reqDto) {

        var dto = new Notification(reqDto);
        return repository.save(dto);
    }

    @Override
    public Flux<Notification> getAll() {
        return repository.findAll().delayElements(Duration.ofSeconds(1));
    }

    @Override
    public Mono<Notification> get(Long id) {
        return repository.findById(id);
    }
}
