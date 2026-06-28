package com.example.manageCar.DTO;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private String passwordOld;
    private String passwordNew;
    private String confirmPasswordNew;
}
