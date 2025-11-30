package com.backed.backend.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@Document(collection = "conversation")
public class Conversation {
    @Id
    @Field("_id")
    private String conversationId;
    private String customerId;
    private String channelId;
    private String channelPhoneNumber;
    ConversationStatus conversationStatus;

    @CreatedDate
    LocalDateTime createdAt;

    public Conversation (String conversationId, String customerId, String channelId){
        this.conversationId = conversationId;
        this.customerId = customerId;
        this.createdAt = LocalDateTime.now();
        this.conversationStatus = ConversationStatus.OPEN;
        this.channelId = channelId;
        if (this.channelId.equals("94c43479-3338-4ff6-883a-46ff0fd9edc7")){
            this.channelPhoneNumber = "+923000724227";
        }
    }
}
