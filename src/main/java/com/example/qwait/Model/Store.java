package com.example.qwait.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.*;

@Entity
@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Store {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer counter;
    private String latitude;
    private String longitude;
    private Integer billingTime=0;
    private Integer waitingTime=0;
    private Integer people=0;
    private String frm;
    private String to;
    private String about;
    private Integer user1=0;
    private Integer user2=0;
    private Integer user3=0;

    public Store(String name, Integer counter, String latitude,String longitude, Integer billingTime, Integer waitingTime,Integer people,AppUser appUser) {
        this.name = name;
        this.counter = counter;
        this.latitude = latitude;
        this.longitude = longitude;
        this.billingTime = billingTime;
        this.waitingTime = waitingTime;
        this.appUser = appUser;
    }

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)@JoinColumn(name="user_id",referencedColumnName = "id")
    private AppUser appUser;

    @JsonIgnore
    @OneToMany(mappedBy = "store",cascade = CascadeType.ALL)
    private Set<Customer> customers = new LinkedHashSet<>();

}
