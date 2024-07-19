package com.example.reactiveapplication.repository;

import com.example.reactiveapplication.model.UserModel;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface UserRepository extends R2dbcRepository<UserModel, Long> {
}
