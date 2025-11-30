package com.backed.backend.controller;

import com.backed.backend.dto.ConversationRequest;
import com.backed.backend.dto.MessageRequest;
import com.backed.backend.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1/")
public class Controller {

    @Autowired
    private Service service;


    @PostMapping("conversation")
    public ResponseEntity<ConversationRequest> storeBotAndCustomerConversation(@RequestBody ConversationRequest conversationRequest) {
        return ResponseEntity.status(service.storeBotAndCustomerConversation(conversationRequest)).build();
    }

    @PostMapping("message")
    public ResponseEntity<MessageRequest> checkAndListMessage(@RequestBody MessageRequest messageRequest){
        return ResponseEntity.status(200).build();
    }

}
