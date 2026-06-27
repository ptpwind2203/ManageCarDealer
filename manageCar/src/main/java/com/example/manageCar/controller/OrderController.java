package com.example.manageCar.controller;

import com.example.manageCar.DTO.OrderRequest;
import com.example.manageCar.model.OrderDetail;
import com.example.manageCar.model.Orders;
import com.example.manageCar.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@CrossOrigin("*")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public List<Orders> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public Orders getOrder(@PathVariable Integer id) {
        return orderService.getOrderById(id);
    }

    @GetMapping("/{id}/details")
    public List<OrderDetail> getOrderDetails(@PathVariable Integer id) {
        return orderService.getOrderDetails(id);
    }

    @PostMapping
    public Orders createOrder(@RequestBody OrderRequest request) {
        return orderService.createOrder(request);
    }

    @PutMapping("/{id}/status")
    public Orders updateStatus(
            @PathVariable Integer id,
            @RequestParam String status
    ) {
        return orderService.updateOrderStatus(id, status);
    }

    @PutMapping("/{id}/cancel")
    public Orders cancelOrder(@PathVariable Integer id) {
        return orderService.cancelOrder(id);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Integer id) {
        orderService.deleteOrder(id);
    }
}
