package com.example.reactiveapplication.dto.req;

public record UserCreate(
        String firstname,
        String lastname,
        String username
) {
}
