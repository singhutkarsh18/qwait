package com.example.qwait.Repository;

import com.example.qwait.Model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CustomerRepo extends MongoRepository<Customer,Long> {

    Optional<Customer> findAllByUsername(String username);
}
