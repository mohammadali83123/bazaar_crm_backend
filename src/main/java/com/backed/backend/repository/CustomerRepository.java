package com.backed.backend.repository;

import com.backed.backend.entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, Object> {
    List<Customer> findByCustomerId(String customerId);
}
