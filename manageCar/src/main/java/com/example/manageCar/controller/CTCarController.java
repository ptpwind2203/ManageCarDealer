package com.example.manageCar.controller;


import com.example.manageCar.DTO.carDetailRequest;
import com.example.manageCar.model.CTCar;
import com.example.manageCar.model.Color;
import com.example.manageCar.model.Version;
import com.example.manageCar.service.CTCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/car-details")
@CrossOrigin(origins = "http://localhost:8081")
public class CTCarController {
    @Autowired
    private CTCarService ctCarService;

    @GetMapping("/colors")
    public List<Color> getAllColors()
    {
        return ctCarService.getAllColors();
    }

    @GetMapping("/versions")
    public List<Version> getAllVersions(){
        return ctCarService.getAllVersions();
    }


    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody carDetailRequest request){
        if (request.getVersionID() == null || request.getVersionID() <= 0) {
            throw new RuntimeException("Vui lòng chọn phiên bản");
        }
        if (request.getColorID() == null || request.getColorID() <= 0) {
            throw new RuntimeException("Vui lòng chọn màu sắc");
        }
        ctCarService.add(request);
        return ResponseEntity.ok("Thêm chi tiết xe thành công");
    }
    @PutMapping("/update")
    public ResponseEntity<String> update(
            @RequestBody carDetailRequest request
    ) {

        ctCarService.update(request);

        return ResponseEntity.ok(
                "Cập nhật chi tiết xe thành công"
        );
    }
}
