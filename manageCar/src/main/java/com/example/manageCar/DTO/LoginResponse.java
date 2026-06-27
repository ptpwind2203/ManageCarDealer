package com.example.manageCar.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private Integer userID;
    private String fullName;
    private String account;
    private String role;
    private String token;
    private String email;
    private String numberPhone;
}
