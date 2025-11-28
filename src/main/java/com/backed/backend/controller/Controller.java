package com.backed.backend.controller;

import com.backed.backend.dto.ConversationRequest;
import com.backed.backend.dto.MessageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static java.sql.DriverManager.println;


@RestController
@RequestMapping("api/v1/")
public class Controller {


    @PostMapping("conversation")
    public ResponseEntity<ConversationRequest> botAndCustomerConversation(@RequestBody ConversationRequest conversationRequest) {
        System.out.println("Received conversation request: " + conversationRequest);
        return ResponseEntity.ok(conversationRequest);
    }

    @PostMapping("message")
    public ResponseEntity<MessageRequest> message(@RequestBody MessageRequest messageRequest){
        System.out.println("Received message request: " + messageRequest);
        return ResponseEntity.status(200).build();
    }

}
