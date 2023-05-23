package com.example.qwait.Repository;

import com.example.qwait.Model.AppUser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<AppUser,Long> {
    Optional<AppUser> findByUsername(String username);
}
