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
    private String location;
    private Integer billingTime;
    private Integer waitingTime;
    private Integer peopleCount=0;
    private Integer lastCounter=0;

    @JsonIgnore
    @OneToMany(mappedBy = "store",cascade = CascadeType.ALL)
    private Set<Customer> customers = new LinkedHashSet<>();

}
