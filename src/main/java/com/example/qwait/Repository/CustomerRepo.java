package com.example.qwait.Repository;

import com.example.qwait.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepo extends JpaRepository<Customer,Long> {

    Optional<Customer> findByUsername(String username);
}
