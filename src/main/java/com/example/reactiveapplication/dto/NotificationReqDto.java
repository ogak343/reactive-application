package com.example.reactiveapplication.dto;

import com.example.reactiveapplication.constants.MessageType;
import lombok.Data;

@Data
public class NotificationReqDto {

    private MessageType type;
    private String message;
}
