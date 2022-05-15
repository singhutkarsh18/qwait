package com.example.qwait.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class PasswordDto {
    private String username;
    private String password;
    private String name;
    private Long mob;
    private String gender;
    private Boolean role;
}
