package com.backed.backend.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "message")
public class Message {
    private String messageId;
    private String conversationId;
    private String contactType;
    private String message;
    private LocalDateTime createdAt;

    public Message (String messageId, String conversationId, String contactType, String message, LocalDateTime createdAt ){
        this.messageId = messageId;
        this.conversationId = conversationId;
        this.contactType = contactType;
        this.message = message;
        this.createdAt = createdAt;
    }
}