package com.backed.backend.service;

import com.backed.backend.dto.ConversationRequest;
import com.backed.backend.dto.MessageRequest;
import com.backed.backend.entity.Customer;
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

    public int storeBotAndCustomerConversation(ConversationRequest conversationRequest) {
        try{
        storeCustomer(conversationRequest.getCustomerId(),
                conversationRequest.getCustomerName(),
                conversationRequest.getChannelId(),
                conversationRequest.getCustomerPhoneNumber());
        }catch (Exception e){
            return 500;
        }
        return 200;
    }

    public void checkAndListMessage(MessageRequest messageRequest) {

    }

    private void extractCustomerInfo(){

    }

    private void storeCustomer(String customerId, String customerName, String channelId, String customerPhoneNumber){
       Customer customer = new Customer(
       customerId,
       customerName,
       channelId,
       customerPhoneNumber
       );

       Customer savedCustomer = customerRepository.save(customer);
    }
}
