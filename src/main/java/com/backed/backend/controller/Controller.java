package com.backed.backend.controller;

import com.backed.backend.dto.ConversationRequest;
import com.backed.backend.dto.ListConversationsResponse;
import com.backed.backend.dto.ListMessagesResponse;
import com.backed.backend.dto.MessageRequest;
import com.backed.backend.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1/")
public class Controller {

    @Autowired
    private Service service;


    @PostMapping("store/conversation")
    public ResponseEntity<ConversationRequest> storeBotAndCustomerConversation(@RequestBody ConversationRequest conversationRequest) {
        return ResponseEntity.status(service.storeBotAndCustomerConversation(conversationRequest)).build();
    }

    @PostMapping("store/message")
    public ResponseEntity<MessageRequest> checkAndStoreMessage(@RequestBody MessageRequest messageRequest){
        service.checkAndStoreMessage(messageRequest);
        return ResponseEntity.status(200).build();
    }

    @PutMapping("conversation/{conversationId}/view/close")
    public ResponseEntity<String> closeConversation(@PathVariable String conversationId){
        service.closeConversation(conversationId);
        return ResponseEntity
                .status(200)
                .body("Conversation closed");
    }

    @GetMapping("conversations/view")
    public ResponseEntity<List<ListConversationsResponse>> getConversations(){
        return ResponseEntity.ok(service.getConversations());
    }

    @GetMapping("conversation/{conversationId}/view")
    public ResponseEntity<List<ListMessagesResponse>> getConversationMessages(@PathVariable String conversationId){
        return ResponseEntity.ok(service.getMessages(conversationId));
    }

}
