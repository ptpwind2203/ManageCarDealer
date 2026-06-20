package com.example.manageCar.repository;

import com.example.manageCar.model.cars;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface carRepository extends JpaRepository<cars, Integer> {


}
