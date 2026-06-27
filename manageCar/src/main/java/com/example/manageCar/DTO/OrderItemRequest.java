package com.example.manageCar.DTO;

import lombok.Data;

@Data
public class OrderItemRequest {
    private Integer carId;
    private Integer colorId;
    private Integer versionId;
    private Integer quantity;
    private String color;
    private String version;
}
