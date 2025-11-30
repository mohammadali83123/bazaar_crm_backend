package com.backed.backend.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "conversation")
public class Conversation {
    String conversationId;
    String customerId;
    ConversationStatus conversationStatus;

    @CreatedDate
    LocalDateTime createdAt;

    public Conversation (String conversationId, String customerId){
        this.conversationId = conversationId;
        this.customerId = customerId;
        this.createdAt = LocalDateTime.now();
        this.conversationStatus = ConversationStatus.OPEN;
    }
}
