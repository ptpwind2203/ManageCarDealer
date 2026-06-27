package com.example.manageCar.DTO;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private Integer customerId;
    private String customerName;
    private String numberPhone;
    private String email;
    private String address;
    private List<OrderItemRequest> items;
}
