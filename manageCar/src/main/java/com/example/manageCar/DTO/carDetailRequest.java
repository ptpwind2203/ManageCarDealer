package com.example.manageCar.DTO;

public class carDetailRequest {
    private Integer carID;
    private Integer colorID;
    private Integer VersionID;
    private int quantity;
    private double price;

    public Integer getCarID() {
        return carID;
    }

    public void setCarID(Integer carID) {
        this.carID = carID;
    }

    public Integer getColorID() {
        return colorID;
    }

    public void setColorID(Integer colorID) {
        this.colorID = colorID;
    }

    public Integer getVersionID() {
        return VersionID;
    }

    public void setVersionID(Integer versionID) {
        this.VersionID = versionID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
