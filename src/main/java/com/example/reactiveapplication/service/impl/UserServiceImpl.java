package com.example.reactiveapplication.service.impl;

import com.example.reactiveapplication.dto.req.UserCreate;
import com.example.reactiveapplication.dto.resp.UserResp;
import com.example.reactiveapplication.mapper.UserMapper;
import com.example.reactiveapplication.repository.UserRepository;
import com.example.reactiveapplication.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    @Override
    public Mono<UserResp> create(UserCreate dto) {

        return repository.save(mapper.toModel(dto)).map(mapper::toResp);
    }

    @Override
    public Mono<UserResp> get(Long id) {
        return repository.findById(id).map(mapper::toResp);
    }
}
