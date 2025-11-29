package com.backed.backend.entity;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Data
@Document(collection = "customer")
public class Customer {
    @Id
    private String customerId;
    private String customerName;
    private String channelId;

    @CreatedDate
    private LocalDateTime createdAt;
}
