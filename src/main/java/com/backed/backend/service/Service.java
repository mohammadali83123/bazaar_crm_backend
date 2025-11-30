package com.backed.backend.service;

import com.backed.backend.dto.ConversationRequest;
import com.backed.backend.dto.MessageRequest;
import com.backed.backend.entity.Customer;
import com.backed.backend.entity.Message;
import com.backed.backend.repository.ConversationRepository;
import com.backed.backend.repository.CustomerRepository;
import com.backed.backend.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Service
public class Service {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ConversationRepository conversationRepository;
    @Autowired
    private MessageRepository messageRepository;

    public int storeBotAndCustomerConversation(ConversationRequest conversationRequest) {
        try{
             storeCustomer(conversationRequest.getCustomerId(),
                    conversationRequest.getCustomerName(),
                    conversationRequest.getChannelId(),
                    conversationRequest.getCustomerPhoneNumber());

             extractConversation(conversationRequest.getConversation(), conversationRequest.getCustomerId());


             return 200;
        }catch (Exception e) {
            System.out.println("ERROR: " + e);
            return 500;
        }
    }

    public void checkAndListMessage(MessageRequest messageRequest) {

    }

    private void extractConversation(List<Map<String, Object>> conversation, String customerId){
        int size = conversation.size();
        String conversationId = conversation.get(0).get("conversationId").toString();

        for(Map<String, Object> i : conversation){
            String storeContactType = new String();
            Map<String, Object> sender = (Map<String, Object>) i.get("sender");
            String type = sender.get("type").toString();
            if(type.equals("bot")){
                storeContactType = type;
            }else{
                storeContactType = "customer";
            }
            Map<String, Object> body = (Map<String, Object>) i.get("body");
            Map<String, Object> text = (Map<String, Object>) body.get("text");
            String conversationMessage = text.get("text").toString();
            String messageId = i.get("id").toString();
            LocalDateTime createdAt =
                    LocalDateTime.ofInstant(
                            Instant.parse(i.get("updatedAt").toString()),
                            ZoneId.of("Asia/Karachi")
                    );
            Message message = new Message(
                    messageId,
                    conversationId,
                    storeContactType,
                    conversationMessage,
                    createdAt
            );

            Message savedMessage = messageRepository.save(message);
        }
    }

    private int storeCustomer(String customerId, String customerName, String channelId, String customerPhoneNumber){
       try{Customer customer = new Customer(
       customerId,
       customerName,
       channelId,
       customerPhoneNumber
       );

       Customer savedCustomer = customerRepository.save(customer);
       return 200;
       }catch (Exception e){
           return 500;
       }
    }
}
