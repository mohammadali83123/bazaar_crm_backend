package com.backed.backend.repository;

import com.backed.backend.entity.Conversation;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationRepository extends MongoRepository<Conversation, Object>{
}
