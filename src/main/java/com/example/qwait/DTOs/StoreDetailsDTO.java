package com.example.qwait.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class StoreDetailsDTO {
    private String name;
    private Integer counter;
    private Integer billingTime;
    private String latitude;
    private String longitude;

}
