package com.example.manageCar.DTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AccountRequest {
    private String account;
    private String fullName;
    private String numberPhone;
    private String email;
    private LocalDate birthDate;
    private String address;
    private Integer roleID;
}
