package com.backed.backend.repository;

import com.backed.backend.entity.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends MongoRepository<Message, Object>{
//    List<Message> findAllByConversationId(String conversationId);
}
