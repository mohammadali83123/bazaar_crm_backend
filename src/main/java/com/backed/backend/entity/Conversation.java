package com.backed.backend.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "conversation")
public class Conversation {
}
