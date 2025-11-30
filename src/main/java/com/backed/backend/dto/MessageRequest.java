package com.backed.backend.dto;

import lombok.Data;

import java.util.Map;

@Data
public class MessageRequest{
    private Map<String,Object> payload;
}
