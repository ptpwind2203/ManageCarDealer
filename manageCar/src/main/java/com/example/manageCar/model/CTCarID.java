package com.example.manageCar.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
@Embeddable
public class CTCarID implements Serializable {
    private Integer carID;
    private Integer colorID;
    private Integer versionID;

    public CTCarID() {}
    public CTCarID(Integer CarID, Integer ColorID, Integer VersionID) {
        this.carID = CarID;
        this.colorID = ColorID;
        this.versionID = VersionID;
    }

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
        return versionID;
    }

    public void setVersionID(Integer versionID) {
        this.versionID = versionID;
    }
}
