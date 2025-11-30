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
    private String customerPhoneNumber;
    private String channelPhoneNumber;

    @CreatedDate
    private LocalDateTime createdAt;

    public Customer(String customerId, String customerName, String channelId, String customerPhoneNumber){
        this.customerId = customerId;
        this.customerName = customerName;
        this.channelId = channelId;
        this.customerPhoneNumber = customerPhoneNumber;
        if (this.channelId.equals("94c43479-3338-4ff6-883a-46ff0fd9edc7")){
            this.channelPhoneNumber = "+923000724227";
        }
        this.createdAt = LocalDateTime.now();
    }
}
