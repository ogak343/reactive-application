package com.example.reactiveapplication.dto.req;

import java.util.UUID;

public record EventDto(
        UUID requestId,
        String message
) {
}
