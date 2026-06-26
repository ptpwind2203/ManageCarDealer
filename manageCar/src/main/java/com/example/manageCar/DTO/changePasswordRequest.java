package com.example.manageCar.DTO;

public class changePasswordRequest {
    private String passwordOld;
    private String passwordNew;
    private String confirmPasswordNew;

    public String getPasswordOld() {
        return passwordOld;
    }

    public void setPasswordOld(String passwordOld) {
        this.passwordOld = passwordOld;
    }

    public String getPasswordNew() {
        return passwordNew;
    }

    public void setPasswordNew(String passwordNew) {
        this.passwordNew = passwordNew;
    }

    public String getConfirmPasswordNew() {
        return confirmPasswordNew;
    }

    public void setConfirmPasswordNew(String confirmPasswordNew) {
        this.confirmPasswordNew = confirmPasswordNew;
    }
}
