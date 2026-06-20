package com.example.manageCar.controller;


import com.example.manageCar.DTO.carDetailResponse;
import com.example.manageCar.model.CTCar;
import com.example.manageCar.model.cars;
import com.example.manageCar.service.carService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
@CrossOrigin(origins = "http://localhost:8081")
public class carsController {

    @Autowired
    private carService carService;

    @GetMapping
    public List<cars> getAllCars() {
        return carService.findAll();
    }


    @GetMapping("/{id}")
    public cars getCarById(@PathVariable Integer id) {
        return carService.getCarByID(id);
    }
    @GetMapping("/{id}/detail")
    public List<carDetailResponse> getCarDetailById(@PathVariable Integer id) {
        return carService.getDetailsByCarID(id);
    }



    //them
    @PostMapping
    public cars addCar(@RequestBody cars car) {
        return carService.addCar(car);
    }


}
