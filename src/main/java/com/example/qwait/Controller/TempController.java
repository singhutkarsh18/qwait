package com.example.qwait.Controller;

import com.example.qwait.Model.AppUser;
import com.example.qwait.Model.Store;
import com.example.qwait.Repository.CustomerRepo;
import com.example.qwait.Repository.StoreRepository;
import com.example.qwait.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController@AllArgsConstructor
@RequestMapping("/api")
public class TempController {
    private final StoreRepository storeRepository;
    private final CustomerRepo customerRepo;
    private final UserRepository userRepository;

    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@RequestBody AppUser appUser)
    {
        return ResponseEntity.ok(userRepository.save(appUser));
    }
    @PostMapping("/addStore")
    public ResponseEntity<?> addStore(@RequestBody Store store)
    {
        return ResponseEntity.ok(storeRepository.save(store));
    }
}
