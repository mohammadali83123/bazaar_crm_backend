package com.backed.backend.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;


@Data
@Document(collection = "customer")
public class Customer {
    @Id
    @Field("_id")
    private String customerId;
    private String customerName;
    private String customerPhoneNumber;
    private String channelPhoneNumber;

    @CreatedDate
    private LocalDateTime createdAt;

    public Customer(String customerId, String customerName, String customerPhoneNumber){
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerPhoneNumber = customerPhoneNumber;
        this.createdAt = LocalDateTime.now();
    }
}
