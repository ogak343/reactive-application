package com.example.reactiveapplication.controller;

import com.example.reactiveapplication.dto.Notification;
import com.example.reactiveapplication.dto.NotificationReqDto;
import com.example.reactiveapplication.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService service;

    @PostMapping("/post")
    public ResponseEntity<Mono<Notification>> create(@RequestBody NotificationReqDto reqDto) {

        return ResponseEntity.ok(service.create(reqDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mono<Notification>> get(@PathVariable("id") Long id) {

        return ResponseEntity.ok(service.get(id));
    }

    @GetMapping(value = "/list", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<Flux<Notification>> getAll() {

        return ResponseEntity.ok(service.getAll());
    }
}
