package com.example.manageCar.model;


import jakarta.persistence.*;

@Entity
@Table(name = "cars")
public class cars {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer carID;

    @Column(name = "CarName")
    private String carName;

    @Column(name = "CompanyCar")
    private String companyCar;

    @Column(name = "Description")
    private String describe;

    @Column(name = "EngineCapacity")
    private String engineCapacity;

    @Column(name = "CarImage")
    private String carImage;

    // getter setter chuẩn camelCas
    public cars(){}

    public Integer getCarID() {
        return carID;
    }

    public void setCarID(Integer carID) {
        this.carID = carID;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCompanyCar() {
        return companyCar;
    }

    public void setCompanyCar(String companyCar) {
        this.companyCar = companyCar;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getEngineCapacity() {
        return engineCapacity;
    }

    public void setEngineCapacity(String engineCapacity) {
        this.engineCapacity = engineCapacity;
    }

    public String getCarImage() {
        return carImage;
    }

    public void setCarImage(String carImage) {
        this.carImage = carImage;
    }
}
