package com.example.qwait.Repository;

import com.example.qwait.Model.Store;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StoreRepository extends MongoRepository<Store,Long> {
}
