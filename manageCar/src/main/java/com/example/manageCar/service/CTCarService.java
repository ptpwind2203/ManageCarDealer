package com.example.manageCar.service;

import com.example.manageCar.DTO.carDetailRequest;
import com.example.manageCar.exception.DuplicateCTCarException;
import com.example.manageCar.model.CTCar;
import com.example.manageCar.model.CTCarID;
import com.example.manageCar.model.Color;
import com.example.manageCar.model.Version;
import com.example.manageCar.repository.carDetailRepository;
import com.example.manageCar.repository.colorRepository;
import com.example.manageCar.repository.versionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CTCarService {
    @Autowired
    private colorRepository colorRepository;

    @Autowired
    private versionRepository versionRepository;

    @Autowired
    private carDetailRepository carDetailRepository;

    public List<Color> getAllColors() {
        return colorRepository.findAll();
    }

    public List<Version> getAllVersions() {
        return versionRepository.findAll();
    }

    public void add(carDetailRequest request){
        Optional<CTCar> exists =
                carDetailRepository.findByCarIDAndColorIDAndVersionID(
                        request.getCarID(),
                        request.getColorID(),
                        request.getVersionID()
                );

        if(exists.isPresent()){
            throw new DuplicateCTCarException("Chi tiết xe đã tồn tại");
        }
        CTCar ctCar = new CTCar();
        ctCar.setCarID(request.getCarID());
        ctCar.setColorID(request.getColorID());
        ctCar.setVersionID(request.getVersionID());
        ctCar.setPrice(request.getPrice());
        ctCar.setQuantity(request.getQuantity());

        carDetailRepository.save(ctCar);
    }

    public void update(carDetailRequest request) {

        CTCar carDetail =
                carDetailRepository.findByCarIDAndColorIDAndVersionID(
                                request.getCarID(),
                                request.getColorID(),
                                request.getVersionID()
                        ).orElseThrow(() ->
                                new RuntimeException("Không tìm thấy chi tiết xe"));
        carDetail.setQuantity(request.getQuantity()
        );

        carDetail.setPrice(request.getPrice()
        );

        carDetailRepository.save(carDetail);
    }
}
