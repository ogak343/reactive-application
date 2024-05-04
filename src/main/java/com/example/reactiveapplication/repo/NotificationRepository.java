package com.example.reactiveapplication.repo;

import com.example.reactiveapplication.dto.Notification;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface NotificationRepository extends ReactiveCrudRepository<Notification, Long> {
}
