package com.example.reactiveapplication.controller;

import com.example.reactiveapplication.dto.req.UserCreate;
import com.example.reactiveapplication.dto.resp.UserResp;
import com.example.reactiveapplication.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService service;

    @PostMapping
    public ResponseEntity<Mono<UserResp>> create(@RequestBody UserCreate dto) {

        log.info("Creating user: {}", dto);

        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mono<UserResp>> get(@PathVariable Long id) {

        log.info("Retrieving user by id: {}", id);
        return ResponseEntity.ok(service.get(id));
    }
}
