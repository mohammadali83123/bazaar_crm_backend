package com.backed.backend.dto;

import lombok.Data;

import java.util.Map;

@Data
public class MessageRequest{
    private String service;
    private String event;
    private Map<String,Object> payload;
}
