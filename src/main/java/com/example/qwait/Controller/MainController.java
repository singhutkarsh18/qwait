package com.example.qwait.Controller;

import com.example.qwait.DTOs.LocationDTO;
import com.example.qwait.DTOs.StoreDetailsDTO;
import com.example.qwait.Services.StoreService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;

@RestController@AllArgsConstructor
@RequestMapping("/api")@CrossOrigin("*")
public class MainController {
    private final StoreService storeService;

    @PostMapping("/addToQueue/{id}")
    public ResponseEntity<?> addToQueue(@PathVariable("id") Long storeId,Principal principal)
    {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(storeService.addCustomer(principal.getName(),storeId));
        }
        catch (IllegalStateException e)
        {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Store or user not present in db");
        }
    }
    @DeleteMapping("/removeFromQueue/{id}")
    public ResponseEntity<?> removeFromQueue(@PathVariable("id") Long storeId, Principal principal)
    {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(storeService.removeCustomer(principal.getName(),storeId));
        }
        catch (IllegalStateException e1)
        {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Store or user not present in db");
        }
        catch (EntityNotFoundException e2)
        {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Customer not found in queue");
        }

    }
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

    @GetMapping("/getAllStores")
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

}
