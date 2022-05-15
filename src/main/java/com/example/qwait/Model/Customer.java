package com.example.qwait.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.ZonedDateTime;

@AllArgsConstructor@NoArgsConstructor
@Entity@Getter@Setter
public class Customer {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String username;
    private String name;
    private String location;
    private Integer counterNo;
    private ZonedDateTime entryTime;
    @ManyToOne@JsonIgnore
    @JoinColumn(name="store_id",referencedColumnName = "id")
    private Store store;

    public Customer(String username, String name, String location, Integer counterNo, ZonedDateTime entryTime, Store store) {
        this.username = username;
        this.name = name;
        this.location = location;
        this.counterNo = counterNo;
        this.entryTime = entryTime;
        this.store = store;
    }
}