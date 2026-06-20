package com.example.manageCar.model;


import jakarta.persistence.*;

@Entity
@Table(name = "Versions")
public class Version {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer VersionID;
    private String VersionName;

    public Integer getVersionID() {
        return VersionID;
    }

    public void setVersionID(Integer versionID) {
        this.VersionID = versionID;
    }

    public String getVersionName() {
        return VersionName;
    }

    public void setVersionName(String versionName) {
        this.VersionName = versionName;
    }
}
