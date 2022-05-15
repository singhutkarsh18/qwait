package com.example.qwait.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

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
    private Integer billingTime;
    private Integer waitingTime;
    private Integer peopleCount=0;
    private String frm;
    private String to;
    private String about;

    public Store(String name, Integer counter, String latitude,String longitude, Integer billingTime, Integer waitingTime, Integer peopleCount ,AppUser appUser) {
        this.name = name;
        this.counter = counter;
        this.latitude = latitude;
        this.longitude = longitude;
        this.billingTime = billingTime;
        this.waitingTime = waitingTime;
        this.peopleCount = peopleCount;
        this.appUser = appUser;
    }

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)@JoinColumn(name="user_id",referencedColumnName = "id")
    private AppUser appUser;

    @JsonIgnore
    @OneToMany(mappedBy = "store",cascade = CascadeType.ALL)
    private Set<Customer> customers = new LinkedHashSet<>();

}
