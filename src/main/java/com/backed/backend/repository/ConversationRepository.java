package com.backed.backend.repository;

import com.backed.backend.entity.Conversation;
import com.backed.backend.entity.ConversationStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConversationRepository extends MongoRepository<Conversation, Object> {
    Optional<Conversation> findByCustomerIdAndConversationStatusAndChannelId(String customerId, ConversationStatus conversationStatus, String channelId);
    Optional<List<Conversation>> findAllByConversationStatus(ConversationStatus conversationStatus);
}
