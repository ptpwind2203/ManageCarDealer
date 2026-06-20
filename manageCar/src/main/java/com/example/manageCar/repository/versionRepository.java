package com.example.manageCar.repository;

import com.example.manageCar.model.Version;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface versionRepository extends JpaRepository<Version,Integer> {
    @Query("""
        SELECT DISTINCT cd.version
        FROM CTCar cd
        WHERE cd.carID = :carId
    """)
    List<Version> findVersionsByCarId(Integer carId);
}
