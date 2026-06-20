package com.example.manageCar.service;


import com.example.manageCar.DTO.carDetailResponse;
import com.example.manageCar.model.CTCar;
import com.example.manageCar.model.Color;
import com.example.manageCar.model.Version;
import com.example.manageCar.model.cars;
import com.example.manageCar.repository.carDetailRepository;
import com.example.manageCar.repository.carRepository;
import com.example.manageCar.repository.colorRepository;
import com.example.manageCar.repository.versionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class carService {
    @Autowired
    private carRepository carRepository;

    @Autowired
    private carDetailRepository carDetailRepository;

    //xe
    public List<cars> findAll() {
        return carRepository.findAll();
    }

    // ADD
    public cars addCar(cars car) {

        return carRepository.save(car);
    }

    public cars getCarByID(Integer id) {
        return carRepository.findById(id).orElse(null);
    }

    public List<carDetailResponse> getDetailsByCarID(
            Integer carID){

        List<CTCar> details =
                carDetailRepository.findDetailsByCarID(carID);

        return details.stream()
                .map(detail -> {

                    carDetailResponse dto =
                            new carDetailResponse();
                    dto.setCarID(detail.getCarID());
                    dto.setColorID(detail.getColorID());
                    dto.setVersionID(detail.getVersionID());
                    dto.setColorName(
                            detail.getColor()
                                    .getColorName());

                    dto.setVersionName(
                            detail.getVersion()
                                    .getVersionName());

                    dto.setPrice(
                            detail.getPrice());

                    dto.setQuantity(
                            detail.getQuantity());

                    return dto;
                })
                .toList();
    }
}
