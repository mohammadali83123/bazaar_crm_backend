package com.backed.backend.repository;

import com.backed.backend.entity.Conversation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConversationRepository extends MongoRepository<Conversation, Object> {
    Optional<Conversation> findByConversationId(String conversationId);
}
