package com.example.reactiveapplication.configuration;

import com.example.reactiveapplication.configuration.data.ApplicationConfigData;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.RabbitFlux;
import reactor.rabbitmq.Receiver;
import reactor.rabbitmq.ReceiverOptions;
import reactor.rabbitmq.Sender;
import reactor.rabbitmq.SenderOptions;

@Configuration
@RequiredArgsConstructor
public class RabbitMQConfiguration {

    private final ApplicationConfigData configData;

    @Bean
    public Mono<Connection> rabbitConnectionMono() {
        ApplicationConfigData.RabbitConfigData rabbit = configData.rabbit();
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(rabbit.url());
        factory.setUsername(rabbit.username());
        factory.setPassword(rabbit.password());
        return Mono.fromCallable(factory::newConnection).cache();
    }

    @Bean
    public Sender sender(Mono<Connection> connectionMono) {
        return RabbitFlux.createSender(new SenderOptions().connectionMono(connectionMono));
    }

    @Bean
    public Receiver receiver(Mono<Connection> connectionMono) {
        return RabbitFlux.createReceiver(new ReceiverOptions().connectionMono(connectionMono));
    }
}
