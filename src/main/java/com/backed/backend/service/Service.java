package com.backed.backend.service;

import com.backed.backend.dto.ConversationRequest;
import com.backed.backend.dto.ListConversationsResponse;
import com.backed.backend.dto.ListMessagesResponse;
import com.backed.backend.dto.MessageRequest;
import com.backed.backend.entity.Conversation;
import com.backed.backend.entity.ConversationStatus;
import com.backed.backend.entity.Customer;
import com.backed.backend.entity.Message;
import com.backed.backend.repository.ConversationRepository;
import com.backed.backend.repository.CustomerRepository;
import com.backed.backend.repository.MessageRepository;
import org.modelmapper.ModelMapper;
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
            String conversationId = i.get("conversationId").toString();
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
                    conversationId,
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
            conversation.setConversationStatus(ConversationStatus.CLOSED);
            conversationRepository.save(conversation);
        } catch (Exception e) {
            throw new RuntimeException("Internal Server Error");
        }
    }

    public int checkAndStoreMessage(MessageRequest messageRequest){
        try {
            Map<String,Object> sender = (Map<String,Object>) messageRequest.getPayload().get("sender");
            Map<String,Object> contactInfo = (Map<String,Object>) sender.get("contact");
            Map<String, Object> body = (Map<String, Object>) messageRequest.getPayload().get("body");
            Map<String, Object> text = (Map<String, Object>) body.get("text");
            String customerId = contactInfo.get("id").toString();
            String channelId = messageRequest.getPayload().get("channelId").toString();
            Optional<Conversation> conversation = conversationRepository.findByCustomerIdAndConversationStatusAndChannelId(customerId, ConversationStatus.OPEN, channelId);
            if(conversation.isEmpty()){
                return 200;
            }

            String conversationId = conversation.get().getConversationId();
            String messageId = messageRequest.getPayload().get("id").toString();
            String senderType = "customer";
            String messageText = text.get("text").toString();
            LocalDateTime createdAt = LocalDateTime.ofInstant(
                    Instant.parse(messageRequest.getPayload().get("createdAt").toString()),
                    ZoneId.of("Asia/Karachi")
            );

            Message message = new Message(messageId,conversationId , senderType, messageText, createdAt);
            Message savedMessage = messageRepository.save(message);


        } catch (Exception e) {
            throw new RuntimeException("Internal Server Error");
        }
        return 200;
    }

    public List<ListConversationsResponse> getConversations(){
        List<Conversation> conversations = conversationRepository.findAllByConversationStatus(ConversationStatus.OPEN)
                .orElseThrow(()-> new RuntimeException("No Open Conversation Found"));

        Customer customer = customerRepository.findById(conversations.get(0).getCustomerId())
                .orElseThrow(()-> new RuntimeException("Customer Not Found"));

//        Approach 1
//        List<ListConversationsResponse> listConversationsResponse = new ArrayList<>();
//        for (Conversation conversation : conversations) {
//            ListConversationsResponse response  = new ListConversationsResponse();
//            response.setConversationId(conversation.getConversationId());
//            response.setChannelPhoneNumber(conversation.getChannelPhoneNumber());
//            response.setConversationStatus(conversation.getConversationStatus().toString());
//            response.setCustomerName(customer.getCustomerName());
//
//            listConversationsResponse.add(response);
//        }

//        Approach 2
//        List<ListConversationsResponse> listConversationsResponse = new ArrayList<>();
//        for(int i = 0; i<conversations.size();i++){
//           Conversation conversation = conversations.get(i);
//           ListConversationsResponse response = new ListConversationsResponse();
//           response.setConversationId(conversation.getConversationId());
//           response.setChannelPhoneNumber(conversation.getChannelPhoneNumber());
//           response.setConversationStatus(conversation.getConversationStatus().toString());
//           response.setCustomerName(customer.getCustomerName());
//
//           listConversationsResponse.add(response);
//
//        }

//        Approach 3 using java stream
//        List<ListConversationsResponse> listConversationsResponse =
//                conversations.stream()
//                        .map(conversation -> {
//                            ListConversationsResponse response = new ListConversationsResponse();
//                            response.setConversationId(conversation.getConversationId());
//                            response.setChannelPhoneNumber(conversation.getChannelPhoneNumber());
//                            response.setConversationStatus(conversation.getConversationStatus());
//                            response.setCustomerName(customer.getCustomerName());
//                            return response;
//                        })
//                        .toList(); // or .collect(Collectors.toList()) for Java < 16


//        Approach 4 for using builder, annotate @Builder on ListConversationsResponse class
//        List<ListConversationsResponse> listConversationsResponse = conversations.stream()
//                .map(conversation -> ListConversationsResponse.builder()
//                        .conversationId(conversation.getConversationId())
//                        .channelPhoneNumber(conversation.getChannelPhoneNumber())
//                        .conversationStatus(conversation.getConversationStatus().toString())
//                        .customerName(customer.getCustomerName())
//                        .build())
//                .collect(Collectors.toList());

//        Approach 5 by creating parametarized constructor in ListConversationsResponse class and then using java stream to call the constructor and passing conversation iterator

//        Approach 6 by creating Mapper Class and then using java stream and passing conversation iterator (just like approach 5)

//        Aproach 7 using ModelMapper by import its dependency in pom.xml
        ModelMapper modelMapper = new ModelMapper();
        List<ListConversationsResponse> list = conversations.stream()
                .map(c -> {
                    ListConversationsResponse dto = modelMapper.map(c, ListConversationsResponse.class);
                    dto.setCustomerName(customer.getCustomerName());
                    return dto;
                }).toList();

        return list;
    }

    public List<ListMessagesResponse> getMessages(String conversationId){
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(()-> new RuntimeException("Conversation Not Found"));
        Customer customer = customerRepository.findById(conversation.getCustomerId())
                .orElseThrow(()-> new RuntimeException("Customer Not Found"));
        List<Message> messages = messageRepository.findAllByConversationId(conversationId);

        List<ListMessagesResponse> list = messages.stream()
                .map(m -> ListMessagesResponse.builder()
                        .messageId(m.getMessageId())
                        .contactType(m.getContactType())
                        .messageText(m.getMessage())
                        .createdAt(m.getCreatedAt())
                        .customerPhoneNumber(customer.getCustomerPhoneNumber())
                        .conversationStatus(conversation.getConversationStatus().toString())
                        .build())
                .toList();

        return list;
    }
}