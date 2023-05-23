package com.example.qwait.Controller;

import com.example.qwait.DTOs.LocationDTO;
import com.example.qwait.DTOs.RegistrationRequest;
import com.example.qwait.DTOs.StoreDetailsDTO;
import com.example.qwait.Services.StoreService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.NoSuchElementException;

@RestController@AllArgsConstructor
@RequestMapping("/api")@CrossOrigin("*")
public class MainController {
    private final StoreService storeService;

    @PostMapping("/addToQueue/{id}")
    public ResponseEntity<?> addToQueue(@PathVariable(name = "id") Long storeId, @RequestBody RegistrationRequest registrationRequest)
    {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(storeService.addCustomer(registrationRequest.getUsername(),storeId));
        }
        catch (IllegalStateException e)
        {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Store or user not present in db");
        }
    }
//    @DeleteMapping("/removeFromQueue/{id}")
//    public ResponseEntity<?> removeFromQueue(@PathVariable("id") Long storeId, Principal principal)
//    {
//        try {
//            return ResponseEntity.status(HttpStatus.OK).body(storeService.removeCustomer(principal.getName(),storeId));
//        }
//        catch (IllegalStateException e1)
//        {
//            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Store or user not present in db");
//        }
//        catch (EntityNotFoundException e2)
//        {
//            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Customer not found in queue");
//        }
//
//    }
    @PostMapping("/addStoreDetails")
    public ResponseEntity<?> addStoreDetails(@RequestBody StoreDetailsDTO storeDetailsDTO,Principal principal)
    {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(storeService.addDetails(storeDetailsDTO,principal.getName()));
        }
        catch (NullPointerException e)
        {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Null value");
        }
    }

    @PostMapping("/getAllStores")
    public ResponseEntity<?> getAllStores(@RequestBody LocationDTO locationDTO)
    {
        try {
            return ResponseEntity.ok(storeService.getStores(locationDTO));
        }
        catch (NullPointerException e)
        {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Missing or null values");
        }
    }
    @GetMapping("/getCustomers/{id}")
    public ResponseEntity<?> getCustomer(@PathVariable("id") Long storeId)
    {
        try {
            return ResponseEntity.ok(storeService.getCustomersInStore(storeId));
        }
        catch (NoSuchElementException e)
        {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getLocalizedMessage());
        }
    }
    @GetMapping("/getStoreDetails/{id}")
    public ResponseEntity<?> getStoreDetails(@PathVariable("id") Long storeId)
    {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(storeService.getStore(storeId));
        }
        catch (IllegalStateException e)
        {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
        }
    }
    @GetMapping("/getStoreQueue/{id}")
    public ResponseEntity<?> getStoreQueue(@PathVariable("id") Long storeId)
    {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(storeService.getQueue(storeId));
        }
        catch (IllegalStateException e)
        {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
        }
    }
//    @GetMapping("/getUserQueues/")
//    public ResponseEntity<?> getUserQueues(@RequestBody RegistrationRequest registrationRequest)
//    {
//        try {
//            return ResponseEntity.status(HttpStatus.OK).body(storeService.getCustomerQueue(registrationRequest.getUsername()));
//        }
//        catch (IllegalStateException e)
//        {
//            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
//        }
//    }
}
