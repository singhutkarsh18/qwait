package com.example.qwait.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class AddCustomerReq {
    private String username;
    private Long storeId;
    private String location;
}
