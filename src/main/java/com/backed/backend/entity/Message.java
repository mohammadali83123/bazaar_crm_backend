package com.backed.backend.entity;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "message")
public class Message {
    @Id
    private String messageId;
    private String customerId;
    private String contactType;
    private String message;
    private LocalDateTime createdAt;

    public Message (String messageId, String customerId, String contactType, String message, LocalDateTime createdAt ){
        this.messageId = messageId;
        this.customerId = customerId;
        this.contactType = contactType;
        this.message = message;
        this.createdAt = createdAt;
    }
}