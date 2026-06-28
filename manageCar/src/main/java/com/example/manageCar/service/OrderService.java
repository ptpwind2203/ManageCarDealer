package com.example.manageCar.service;

import com.example.manageCar.DTO.OrderItemRequest;
import com.example.manageCar.DTO.OrderRequest;
import com.example.manageCar.model.*;
import com.example.manageCar.repository.OrderDetailRepository;
import com.example.manageCar.repository.OrderRepository;
import com.example.manageCar.repository.carDetailRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final carDetailRepository carDetailRepo;

    private final Set<String> VALID_STATUS =
            Set.of("PENDING", "CONFIRMED", "PAID", "DELIVERED", "CANCELLED");


    public Orders createOrder(OrderRequest request) {

        validateOrderRequest(request);

        Orders order = new Orders();
        order.setCustomerId(request.getCustomerId());
        order.setCustomerName(request.getCustomerName().trim());
        order.setNumberPhone(request.getNumberPhone().trim());
        order.setEmail(request.getEmail().trim());
        order.setAddress(request.getAddress().trim());
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus("PENDING");
        order.setTotalPrice(BigDecimal.ZERO);
        Orders savedOrder = orderRepository.save(order);

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (OrderItemRequest item : request.getItems()) {
            validateOrderItem(item);
            CTCarID carID = new CTCarID(
                    item.getCarId(),
                    item.getColorId(),
                    item.getVersionId()
            );

            CTCar carDetail = carDetailRepo.findById(carID)
                    .orElseThrow(() ->
                            new RuntimeException("Không tìm thấy xe"));

            if (carDetail.getQuantity() < item.getQuantity()) {
                throw new RuntimeException(
                        "Xe ID " + item.getCarId() + " không đủ số lượng"
                );
            }

            BigDecimal itemTotal = BigDecimal.valueOf(carDetail.getPrice())
                    .multiply(BigDecimal.valueOf(item.getQuantity()));

            carDetail.setQuantity(
                    carDetail.getQuantity() - item.getQuantity()
            );

            carDetailRepo.save(carDetail);

            OrderDetail detail = new OrderDetail();

            detail.setId(new OrderDetailId(
                    savedOrder.getOrderId(),
                    item.getCarId()
            ));

            detail.setQuantityBuy(item.getQuantity());
            detail.setTotalPrice(itemTotal);
            detail.setColor(item.getColor());
            detail.setVersion(item.getVersion());
            detail.setNote("CARDEALER HÂN HẠNH XIN CẢM ƠN BẠN");
            orderDetailRepository.save(detail);
            totalAmount = totalAmount.add(itemTotal);
        }

        savedOrder.setTotalPrice(totalAmount);

        return orderRepository.save(savedOrder);
    }

    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
    }


    public Orders getOrderById(Integer orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException("Không tìm thấy đơn hàng"));
    }


    public List<OrderDetail> getOrderDetails(Integer orderId) {

        getOrderById(orderId);

        return orderDetailRepository.findByIdOrderId(orderId);
    }


    public List<Orders> getOrdersByCustomer(Integer customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    public Orders updateOrderStatus(Integer orderId, String status) {

        Orders order = getOrderById(orderId);

        status = status.toUpperCase().trim();

        if (!VALID_STATUS.contains(status)) {
            throw new RuntimeException("Trạng thái không hợp lệ");
        }

        if ("CANCELLED".equals(order.getStatus())) {
            throw new RuntimeException("Đơn đã hủy không thể cập nhật");
        }

        order.setStatus(status);

        return orderRepository.save(order);
    }


    public Orders cancelOrder(Integer orderId) {

        Orders order = getOrderById(orderId);

        if ("CANCELLED".equals(order.getStatus())) {
            throw new RuntimeException("Đơn hàng đã bị hủy");
        }

        if ("DELIVERED".equals(order.getStatus())) {
            throw new RuntimeException("Không thể hủy đơn đã giao");
        }

        List<OrderDetail> details =
                orderDetailRepository.findByIdOrderId(orderId);

        for (OrderDetail detail : details) {

            CTCar car = carDetailRepo
                    .findByCarID(detail.getId().getCarId())
                    .stream()
                    .filter(x ->
                            x.getColor().getColorName()
                                    .equalsIgnoreCase(detail.getColor())
                                    &&
                                    x.getVersion().getVersionName()
                                            .equalsIgnoreCase(detail.getVersion())
                    )
                    .findFirst()
                    .orElseThrow(() ->
                            new RuntimeException("Không tìm thấy xe để rollback"));
            car.setQuantity(
                    car.getQuantity() + detail.getQuantityBuy()
            );

            carDetailRepo.save(car);
        }
        order.setStatus("CANCELLED");

        return orderRepository.save(order);
    }


    public void deleteOrder(Integer orderId) {

        Orders order = getOrderById(orderId);
        if (!"CANCELLED".equals(order.getStatus())) {
            throw new RuntimeException(
                    "Chỉ được xóa đơn đã hủy"
            );
        }

        List<OrderDetail> details =
                orderDetailRepository.findByIdOrderId(orderId);

        orderDetailRepository.deleteAll(details);

        orderRepository.delete(order);
    }


    private void validateOrderRequest(OrderRequest request) {

        if (request == null) {
            throw new RuntimeException("Dữ liệu đơn hàng không hợp lệ");
        }

        if (request.getCustomerName() == null ||
                request.getCustomerName().isBlank()) {
            throw new RuntimeException("Tên khách hàng không được để trống");
        }

        if (request.getNumberPhone() == null ||
                request.getNumberPhone().isBlank()) {
            throw new RuntimeException("Số điện thoại không được để trống");
        }

        if (request.getAddress() == null ||
                request.getAddress().isBlank()) {
            throw new RuntimeException("Địa chỉ không được để trống");
        }

        if (request.getItems() == null ||
                request.getItems().isEmpty()) {
            throw new RuntimeException(
                    "Đơn hàng phải có ít nhất 1 sản phẩm"
            );
        }
    }

    private void validateOrderItem(OrderItemRequest item) {

        if (item.getQuantity() <= 0) {
            throw new RuntimeException(
                    "Số lượng phải lớn hơn 0"
            );
        }

        if (item.getCarId() == null ||
                item.getColorId() == null ||
                item.getVersionId() == null) {
            throw new RuntimeException(
                    "Thông tin xe không đầy đủ"
            );
        }
    }
}
