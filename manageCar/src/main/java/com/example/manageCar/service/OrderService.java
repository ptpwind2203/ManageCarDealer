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

    //  * ========================================
    //  * TẠO ĐƠN HÀNG
    //  * Nghiệp vụ:
    //     * 1. Validate dữ liệu đầu vào
    //     * 2. Tạo order chính
    //     * 3. Duyệt từng sản phẩm trong đơn
    //     * 4. Kiểm tra tồn kho
    //     * 5. Trừ tồn kho
    //     * 6. Lưu chi tiết đơn hàng
    //     * 7. Tính tổng tiền
    //     */
    //  * ========================================
    public Orders createOrder(OrderRequest request) {

        // Kiểm tra dữ liệu request
        validateOrderRequest(request);

        // Khởi tạo đơn hàng mới
        Orders order = new Orders();
        order.setCustomerId(request.getCustomerId());
        order.setCustomerName(request.getCustomerName().trim());
        order.setNumberPhone(request.getNumberPhone().trim());
        order.setEmail(request.getEmail().trim());
        order.setAddress(request.getAddress().trim());
        order.setCreatedAt(LocalDateTime.now());
        // Khi mới tạo mặc định là PENDING
        order.setStatus("PENDING");
        // Tổng tiền ban đầu = 0
        order.setTotalPrice(BigDecimal.ZERO);
        // Lưu order trước để có orderId
        Orders savedOrder = orderRepository.save(order);

        BigDecimal totalAmount = BigDecimal.ZERO;

        // Duyệt từng sản phẩm trong đơn hàng
        for (OrderItemRequest item : request.getItems()) {
            // Validate từng item
            validateOrderItem(item);
            // Tạo composite key để tìm xe đúng phiên bản + màu
            CTCarID carID = new CTCarID(
                    item.getCarId(),
                    item.getColorId(),
                    item.getVersionId()
            );

            // Tìm xe trong kho
            CTCar carDetail = carDetailRepo.findById(carID)
                    .orElseThrow(() ->
                            new RuntimeException("Không tìm thấy xe"));

            // Kiểm tra tồn kho đủ hay không
            if (carDetail.getQuantity() < item.getQuantity()) {
                throw new RuntimeException(
                        "Xe ID " + item.getCarId() + " không đủ số lượng"
                );
            }

            // Tính tổng tiền từng item
            // price(Double) * quantity
            BigDecimal itemTotal = BigDecimal.valueOf(carDetail.getPrice())
                    .multiply(BigDecimal.valueOf(item.getQuantity()));

            // Trừ số lượng tồn kho sau khi đặt
            carDetail.setQuantity(
                    carDetail.getQuantity() - item.getQuantity()
            );

            // Cập nhật lại kho
            carDetailRepo.save(carDetail);

            // Tạo chi tiết đơn hàng
            OrderDetail detail = new OrderDetail();

            // Gán khóa chính
            detail.setId(new OrderDetailId(
                    savedOrder.getOrderId(),
                    item.getCarId()
            ));

            // Lưu số lượng mua
            detail.setQuantityBuy(item.getQuantity());
            // Lưu tổng tiền của item
            detail.setTotalPrice(itemTotal);
            detail.setColor(item.getColor());
            detail.setVersion(item.getVersion());
            detail.setNote("CARDEALER HÂN HẠNH XIN CẢM ƠN BẠN");
            // Lưu chi tiết đơn hàng
            orderDetailRepository.save(detail);
            // Cộng dồn vào tổng đơn hàng
            totalAmount = totalAmount.add(itemTotal);
        }

        savedOrder.setTotalPrice(totalAmount);

        return orderRepository.save(savedOrder);
    }

    // =========================
    // LẤY TOÀN BỘ DANH SÁCH ĐƠN HÀNG
    // =========================
    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
    }

    // =========================
    // LẤY ĐƠN HÀNG THEO ID
    // =========================
    public Orders getOrderById(Integer orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException("Không tìm thấy đơn hàng"));
    }

    // =========================
    // LẤY DANH SÁCH CHI TIẾT ĐƠN HÀNG
    // =========================
    public List<OrderDetail> getOrderDetails(Integer orderId) {

        // Kiểm tra đơn hàng tồn tại
        getOrderById(orderId);

        return orderDetailRepository.findByIdOrderId(orderId);
    }

    // =========================
    // LẤY DANH SÁCH ĐƠN THEO KHÁCH HÀNG
    // =========================
    public List<Orders> getOrdersByCustomer(Integer customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    // =========================
    // CẬP NHẬT TRẠNG THÁI ĐƠN HÀNG
    // Luồng:
    //     * PENDING -> CONFIRMED -> PAID -> DELIVERED
    // =========================
    public Orders updateOrderStatus(Integer orderId, String status) {

        Orders order = getOrderById(orderId);

        status = status.toUpperCase().trim();

        // Kiểm tra trạng thái hợp lệ
        if (!VALID_STATUS.contains(status)) {
            throw new RuntimeException("Trạng thái không hợp lệ");
        }

        // Đơn đã hủy không update được
        if ("CANCELLED".equals(order.getStatus())) {
            throw new RuntimeException("Đơn đã hủy không thể cập nhật");
        }

        order.setStatus(status);

        return orderRepository.save(order);
    }

    // =========================
    // HỦY ĐƠN HÀNG
    //  Nghiệp vụ:
    //     * - rollback lại tồn kho
    //     * - đổi trạng thái thành CANCELLED
    //     */
    // =========================
    public Orders cancelOrder(Integer orderId) {

        Orders order = getOrderById(orderId);

        // Không cho hủy lần 2
        if ("CANCELLED".equals(order.getStatus())) {
            throw new RuntimeException("Đơn hàng đã bị hủy");
        }

        // Đã giao thì không cho hủy
        if ("DELIVERED".equals(order.getStatus())) {
            throw new RuntimeException("Không thể hủy đơn đã giao");
        }

        // Lấy chi tiết đơn
        List<OrderDetail> details =
                orderDetailRepository.findByIdOrderId(orderId);

        // Rollback kho
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
            // Cộng lại tồn kho
            car.setQuantity(
                    car.getQuantity() + detail.getQuantityBuy()
            );

            carDetailRepo.save(car);
        }
        // Chuyển trạng thái thành hủy
        order.setStatus("CANCELLED");

        return orderRepository.save(order);
    }

    // =========================
    //  XÓA ĐƠN HÀNG
    // =========================
    public void deleteOrder(Integer orderId) {

        Orders order = getOrderById(orderId);
        // Chỉ được xóa khi đơn hàng đã hủy
        if (!"CANCELLED".equals(order.getStatus())) {
            throw new RuntimeException(
                    "Chỉ được xóa đơn đã hủy"
            );
        }

        // Xóa chi tiết trước (foreign key)
        List<OrderDetail> details =
                orderDetailRepository.findByIdOrderId(orderId);

        orderDetailRepository.deleteAll(details);

        // Xóa đơn chính
        orderRepository.delete(order);
    }

    // =========================
    // VALIDATION THÔNG TIN ĐƠN HÀNG
    // =========================
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
