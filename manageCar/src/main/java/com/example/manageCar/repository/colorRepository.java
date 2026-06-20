package com.example.manageCar.repository;

import com.example.manageCar.model.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface colorRepository extends JpaRepository<Color, Integer> {

    @Query("""
    SELECT DISTINCT cd.color
    FROM CTCar cd
    WHERE cd.carID = :carId
""")
    List<Color> findColorsByCarId(Integer carId);


}
