package com.example.reactiveapplication.dto.resp;

public record UserResp(
        Long id,
        String firstname,
        String lastname,
        String username
) {
}
