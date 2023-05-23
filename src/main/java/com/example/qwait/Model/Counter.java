//package com.example.qwait.Model;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.persistence.*;
//@Getter@Setter
//@Entity@AllArgsConstructor@NoArgsConstructor
//public class Counter {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private Long customerId;
//
//    @ManyToOne@JsonIgnore
//    @JoinColumn(name="store_id",referencedColumnName = "id")
//    private Store store;
//}
