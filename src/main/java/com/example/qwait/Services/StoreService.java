package com.example.qwait.Services;

import com.example.qwait.DTOs.AddCustomerReq;
import com.example.qwait.Model.AppUser;
import com.example.qwait.Model.Customer;
import com.example.qwait.Model.Store;
import com.example.qwait.Repository.CustomerRepo;
import com.example.qwait.Repository.StoreRepository;
import com.example.qwait.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service@AllArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final CustomerRepo customerRepo;
    private final UserRepository userRepository;

    public String addCustomer(AddCustomerReq addCustomerReq)
    {
        AppUser appUser = userRepository.findByUsername(addCustomerReq.getUsername()).orElseThrow(IllegalStateException::new);
        Store store = storeRepository.findById(addCustomerReq.getStoreId()).orElseThrow(IllegalStateException::new);
        Integer peopleCount = store.getPeopleCount();
        Integer newCounter= (peopleCount%store.getCounter())==0?store.getCounter():peopleCount%store.getCounter();
        ZonedDateTime entryTime= ZonedDateTime.now(ZoneId.of("Asia/Kolkata"));
        Customer customer = new Customer(addCustomerReq.getUsername(),appUser.getName(),addCustomerReq.getLocation(),newCounter,entryTime,store);

    }
}
