package com.example.manageCar.model;


import jakarta.persistence.*;

@Entity
@Table(name = "cardetail")
@IdClass(CTCarID.class)
public class CTCar {
    @Id
    @Column(name = "CarID")
    private int carID;
    @Id
    @Column(name = "ColorID")
    private int colorID;
    @Id
    @Column(name = "VersionID")
    private int versionID;

    @Column(name = "Price")
    private Double price;

    @Column(name = "Quantity")
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CarID", insertable = false, updatable = false)
    private cars cars;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ColorID", insertable = false, updatable = false)
    private Color color;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VersionID", insertable = false, updatable = false)
    private Version version;

    public int getCarID() {
        return carID;
    }

    public void setCarID(int CarID) {
        this.carID = CarID;
    }

    public int getColorID() {
        return colorID;
    }

    public void setColorID(int ColorID) {
        this.colorID = ColorID;
    }

    public int getVersionID() {
        return versionID;
    }

    public void setVersionID(int versionID) {
        this.versionID = versionID;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public cars getCars() {
        return cars;
    }

    public void setCars(cars cars) {
        this.cars = cars;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }
}
