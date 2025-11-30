package com.backed.backend.dto;

import lombok.Data;

@Data
public class ListConversationsResponse {

    private String conversationId;
    private String channelPhoneNumber;
    private String conversationStatus;
    private String customerName;
}
