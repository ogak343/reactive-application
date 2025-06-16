package com.example.reactiveapplication.configuration.data;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "config")
public record ApplicationConfigData(
        RabbitConfigData rabbit
) {

    public record RabbitConfigData(
            String url,
            String username,
            String password
    ) {}
}
