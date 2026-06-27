package com.example.manageCar.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "orderdetail")
@Getter
@Setter
public class OrderDetail {

    @EmbeddedId
    private OrderDetailId id;

    @Column(name = "QuantityBuy")
    private Integer quantityBuy;

    @Column(name = "TotalPrice")
    private BigDecimal totalPrice;

    @Column(name = "Note")
    private String note;

    @Column(name = "Version")
    private String version;

    @Column(name = "Color")
    private String color;
}
