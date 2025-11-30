package com.backed.backend.service;

import com.backed.backend.dto.ConversationRequest;
import com.backed.backend.dto.MessageRequest;
import com.backed.backend.entity.Conversation;
import com.backed.backend.entity.ConversationStatus;
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
import java.util.Optional;

@org.springframework.stereotype.Service
public class Service {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ConversationRepository conversationRepository;
    @Autowired
    private MessageRepository messageRepository;

    public int storeBotAndCustomerConversation(ConversationRequest conversationRequest) {
        try {
            if(customerRepository.findById(conversationRequest.getCustomerId()).isEmpty()){
                storeCustomer(conversationRequest.getCustomerId(),
                        conversationRequest.getCustomerName(),
                        conversationRequest.getCustomerPhoneNumber());
            }
            extractAndStoreConversation(conversationRequest.getConversation(), conversationRequest.getCustomerId(), conversationRequest.getChannelId());
            extractAndStoreConversationMessages(conversationRequest.getConversation());

            return 200;
        } catch (Exception e) {
            System.out.println("ERROR: " + e);
            return 500;
        }
    }

    public void extractAndStoreConversation(List < Map < String, Object >> requestConversation, String customerId, String channelId){
        String conversationId = requestConversation.get(0).get("conversationId").toString();
        Conversation conversation = new Conversation(
                conversationId,
                customerId,
                channelId
        );

        Conversation savedConversation = conversationRepository.save(conversation);
    }

    private void extractAndStoreConversationMessages(List < Map < String, Object >> conversation) {
        int size = conversation.size();

        for (Map < String, Object > i: conversation) {
            String storeContactType = new String();
            Map < String, Object > sender = (Map < String, Object > ) i.get("sender");
            String customerId = sender.get("id").toString();
            String type = sender.get("type").toString();
            if (type.equals("bot")) {
                storeContactType = type;
            } else {
                storeContactType = "customer";
            }
            Map < String, Object > body = (Map < String, Object > ) i.get("body");
            Map < String, Object > text = (Map < String, Object > ) body.get("text");
            String conversationMessage = text.get("text").toString();
            String messageId = i.get("id").toString();
            LocalDateTime createdAt =
                    LocalDateTime.ofInstant(
                            Instant.parse(i.get("updatedAt").toString()),
                            ZoneId.of("Asia/Karachi")
                    );
            Message message = new Message(
                    messageId,
                    customerId,
                    storeContactType,
                    conversationMessage,
                    createdAt
            );

            Message savedMessage = messageRepository.save(message);
        }
    }

    private int storeCustomer(String customerId, String customerName, String customerPhoneNumber) {
        try {
            Customer customer = new Customer(
                    customerId,
                    customerName,
                    customerPhoneNumber
            );

            Customer savedCustomer = customerRepository.save(customer);
            return 200;
        } catch (Exception e) {
            return 500;
        }
    }

    public void closeConversation(String conversationId){
        try {
            Conversation conversation = conversationRepository.findById(conversationId)
                    .orElseThrow(() -> new RuntimeException("Conversation Not Found"));
            System.out.println("Before Conversation: " + conversation);
            conversation.setConversationStatus(ConversationStatus.CLOSED);
            System.out.println("After Conversation: " + conversation);
            conversationRepository.save(conversation);
        } catch (Exception e) {
            System.out.println("ERROR: " + e);
            throw new RuntimeException("Internal Server Error");
        }
    }

    public void checkAndStoreMessage(MessageRequest messageRequest){
        try {
            Map<String,Object> sender = (Map<String,Object>) messageRequest.getPayload().get("sender");
            Map<String,Object> contactInfo = (Map<String,Object>) sender.get("contact");
            Map<String, Object> body = (Map<String, Object>) messageRequest.getPayload().get("body");
            Map<String, Object> text = (Map<String, Object>) body.get("text");
            String customerId = contactInfo.get("id").toString();
            String channelId = messageRequest.getPayload().get("channelId").toString();
            Optional<Conversation> conversation = conversationRepository.findByCustomerIdAndConversationStatusAndChannelId(customerId, ConversationStatus.OPEN, channelId);
            System.out.println("Conversation: " + conversation);
            if(!conversation.isPresent()){
                throw new RuntimeException("Conversation Not Found");
            }

            String messageId = messageRequest.getPayload().get("id").toString();
            String senderType = "customer";
            String messageText = text.get("text").toString();
            LocalDateTime createdAt = LocalDateTime.ofInstant(
                    Instant.parse(messageRequest.getPayload().get("createdAt").toString()),
                    ZoneId.of("Asia/Karachi")
            );

            Message message = new Message(messageId, customerId, senderType, messageText, createdAt);
            Message savedMessage = messageRepository.save(message);


        } catch (Exception e) {
            System.out.println("ERROR: " + e);
            throw new RuntimeException("Internal Server Error");
        }
    }
}