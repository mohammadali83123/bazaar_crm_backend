package com.backed.backend.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ListMessagesResponse {
    private String messageId;
    private String contactType;
    private String messageText;
    private LocalDateTime createdAt;
    private String customerPhoneNumber;
    private String conversationStatus;
}
