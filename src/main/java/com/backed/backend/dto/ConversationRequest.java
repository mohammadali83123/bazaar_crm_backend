package com.backed.backend.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ConversationRequest {
    private String channelId;
    private List<Map<String, Object>> conversation;
    private String customerId;
    private String customerName;
    private String customerPhoneNumber;
}
