package com.example.qwait.Controller;

import com.example.qwait.DTOs.AddCustomerReq;
import com.example.qwait.Services.StoreService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@RestController@AllArgsConstructor
@RequestMapping("/api/")
public class MainController {
    private final StoreService storeService;

    @PostMapping("/addToQueue")
    public ResponseEntity<?> addToQueue(@RequestBody AddCustomerReq addCustomerReq)
    {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(storeService.addCustomer(addCustomerReq));
        }
        catch (IllegalStateException e)
        {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Store or user not present in db");
        }
    }
    @DeleteMapping("/removeFromQueue")
    public ResponseEntity<?> removeFromQueue(@RequestBody AddCustomerReq addCustomerReq)
    {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(storeService.removeCustomer(addCustomerReq));
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


}
