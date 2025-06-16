package com.example.reactiveapplication.service.impl;

import com.example.reactiveapplication.dto.req.EventDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Delivery;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.BindingSpecification;
import reactor.rabbitmq.ExchangeSpecification;
import reactor.rabbitmq.OutboundMessage;
import reactor.rabbitmq.QueueSpecification;
import reactor.rabbitmq.Receiver;
import reactor.rabbitmq.Sender;

import java.io.IOException;

@Slf4j
@Component
public class RabbitMQService {
    private final Sender sender;
    private final ObjectMapper objectMapper;
    private final Receiver receiver;

    private Disposable consumerDisposable;

    private static final String NOTIFICATION_ROUTING_KEY = "notification-event";
    private static final String NOTIFICATION_EVENT_QUEUE = "notification-event-queue";
    private static final String NOTIFICATION_EVENT_EXCHANGE = "notification-event-exchange";

    public RabbitMQService(Sender sender, ObjectMapper objectMapper, Receiver receiver) {
        this.sender = sender;
        this.objectMapper = objectMapper;
        this.receiver = receiver;
    }

    public Mono<Void> sendEvent(EventDto event) {
        return Mono.just(event)
                .flatMap(data -> {
                    try {
                        byte[] bytes = objectMapper.writeValueAsBytes(data);
                        OutboundMessage msg = new OutboundMessage(
                                NOTIFICATION_EVENT_EXCHANGE, NOTIFICATION_ROUTING_KEY, bytes);
                        return sender.send(Mono.just(msg))
                                .doOnSuccess(res -> log.info("Sent event to RabbitMQ: {}", event));
                    } catch (JsonProcessingException ex) {
                        log.error("Error parsing JSON", ex);
                        return Mono.error(new RuntimeException("Failed to serialize event", ex));
                    }
                });
    }

    @PostConstruct
    public void setupAndConsume() {
        sender.declareExchange(ExchangeSpecification.exchange(NOTIFICATION_EVENT_EXCHANGE).type("direct").durable(true))
                .then(sender.declareQueue(QueueSpecification.queue(NOTIFICATION_EVENT_QUEUE).durable(true)))
                .then(sender.bind(BindingSpecification.binding()
                        .queue(NOTIFICATION_EVENT_QUEUE)
                        .exchange(NOTIFICATION_EVENT_EXCHANGE)
                        .routingKey(NOTIFICATION_ROUTING_KEY)))
                .doOnSuccess(v -> startConsuming())
                .doOnError(e -> log.error("Failed to setup RabbitMQ topology", e))
                .subscribe();
    }

    private void startConsuming() {
        consumerDisposable = receiver.consumeAutoAck(NOTIFICATION_EVENT_QUEUE)
                .flatMap(this::handleMessage)
                .doOnError(e -> log.error("Error consuming message", e))
                .subscribe();
    }

    private Mono<Void> handleMessage(Delivery delivery) {
        return Mono.fromRunnable(() -> {
            try {
                byte[] body = delivery.getBody();
                EventDto event = objectMapper.readValue(body, EventDto.class);
                log.info("Received event: {}", event);

            } catch (IOException e) {
                log.error("Failed to deserialize EventDto", e);
            }
        });
    }

    @PreDestroy
    public void shutdown() {
        if (consumerDisposable != null && !consumerDisposable.isDisposed()) {
            consumerDisposable.dispose();
        }
    }
}
