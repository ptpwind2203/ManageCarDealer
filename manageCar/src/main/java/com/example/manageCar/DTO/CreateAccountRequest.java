package com.example.manageCar.DTO;

import lombok.*;

import java.time.LocalDate;

@Data
public class CreateAccountRequest {
    private String account;
    private String fullName;
    private String numberPhone;
    private String email;
    private LocalDate birthDate;
    private String address;
    private Integer roleID;
    private String password;
}
