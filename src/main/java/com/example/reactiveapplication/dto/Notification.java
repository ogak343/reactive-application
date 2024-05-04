package com.example.reactiveapplication.dto;

import com.example.reactiveapplication.constants.MessageType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("notification")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Notification {

    @Id
    private Long id;
    private String message;
    private MessageType type;

    public Notification(NotificationReqDto reqDto) {
        this.message = reqDto.getMessage();
        this.type = reqDto.getType();
    }
}
