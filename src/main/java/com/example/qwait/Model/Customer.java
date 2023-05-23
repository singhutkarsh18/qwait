package com.example.qwait.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.time.ZonedDateTime;

@AllArgsConstructor@NoArgsConstructor
@Document
@Getter@Setter
public class Customer {
    @Id
    private Long id;
    private String username;
    private String name;
    private Integer counterNo;
    private Integer waitingTime;
    private Integer billingTime;
    private ZonedDateTime entryTime;

    private Store store;

    public Customer(String username, String name, Integer counterNo, ZonedDateTime entryTime, Store store) {
        this.username = username;
        this.name = name;
        this.counterNo = counterNo;
        this.entryTime = entryTime;
        this.store = store;
    }
}