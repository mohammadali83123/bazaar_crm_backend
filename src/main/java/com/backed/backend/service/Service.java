package com.backed.backend.service;

import com.backed.backend.dto.ConversationRequest;
import com.backed.backend.dto.MessageRequest;
import com.backed.backend.repository.ConversationRepository;
import com.backed.backend.repository.CustomerRepository;
import com.backed.backend.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class Service {

    @Autowired
    private CustomerRepository customerRepository;
    private ConversationRepository conversationRepository;
    private MessageRepository messageRepository;

    public void storeBotAndCustomerConversation(ConversationRequest conversationRequest) {

    }

    public void checkAndListMessage(MessageRequest messageRequest) {

    }

    private void extractCustomerInfo(){

    }

    private void storeCustomer(){

    }
}
