<template>
  <div class="order-management">
    <div class="top-bar">
      <h1>Quản Lý Đơn Hàng</h1>

      <input
        v-model="keyword"
        type="text"
        placeholder="Tìm kiếm theo tên hoặc SĐT..."
        class="search-input"
      />

      <button class="add-order-btn" @click="goToCreateOrder">
        + Thêm đơn
      </button>
    </div>

    <table class="order-table">
      <thead>
        <tr>
          <th>Mã đơn</th>
          <th>Khách hàng</th>
          <th>SĐT</th>
          <th>Email</th>
          <th>Tổng tiền</th>
          <th>Trạng thái</th>
          <th>Ngày tạo</th>
          <th>Chức năng</th>
        </tr>
      </thead>

      <tbody>
        <tr v-for="order in filteredOrders" :key="order.orderId">
          <td>{{ order.orderId }}</td>
          <td>{{ order.customerName || "Không rõ tên" }}</td>
          <td>{{ order.numberPhone || "Không có SĐT" }}</td>
          <td>{{ order.email || "Không có Email" }}</td>
          <td>{{ formatMoney(order.totalPrice) }}</td>

          <td>
            <span :class="getStatusClass(order.status)">
              {{ order.status || "CHƯA XÁC ĐỊNH" }}
            </span>
          </td>

          <td>{{ formatDate(order.createdAt) }}</td>

          <td>
           <div class="action-buttons">
              <button
                class="detail-btn"
                @click="openOrderDetail(order)"
              >
                Chi tiết
              </button>

              <!-- PENDING -> CONFIRMED -->
              <button
                v-if="order.status === 'PENDING'"
                class="confirm-btn"
                @click="updateStatus(order.orderId, 'CONFIRMED')"
              >
                Xác nhận
              </button>

              <!-- CONFIRMED -> PAID -->
              <button
                v-if="order.status === 'CONFIRMED'"
                class="paid-btn"
                @click="updateStatus(order.orderId, 'PAID')"
              >
                Thanh toán
              </button>

              <!-- PAID -> DELIVERED -->
              <button
                v-if="order.status === 'PAID'"
                class="deliver-btn"
                @click="updateStatus(order.orderId, 'DELIVERED')"
              >
                Giao xe
              </button>

              <!-- Hủy nếu chưa giao -->
              <button
                v-if="order.status !== 'DELIVERED' && order.status !== 'CANCELLED'"
                class="cancel-btn"
                @click="cancelOrder(order.orderId)"
              >
                Hủy
              </button>

              <!-- Xóa nếu đã hủy -->
              <button
                v-if="order.status === 'CANCELLED'"
                class="delete-btn"
                @click="deleteOrder(order.orderId)"
              >
                Xóa
              </button>
            </div>
          </td>
        </tr>

        <tr v-if="filteredOrders.length === 0">
          <td colspan="8" style="padding: 30px; color: #7f8c8d;">
            Không tìm thấy đơn hàng nào hoặc danh sách trống.
          </td>
        </tr>
      </tbody>
    </table>

    <OrderDetailForm
      v-if="showDetail"
      :order="selectedOrder"
      @close="showDetail = false"
    />
  </div>
</template>

<script setup>
import axios from "axios";
import { ref, computed, onMounted } from "vue";
import { useRouter } from "vue-router";

import OrderDetailForm from "./OrderDetailForm.vue";

const router = useRouter();

const API = "http://localhost:8080/api/orders";

const orders = ref([]);
const keyword = ref("");
const showDetail = ref(false);
const selectedOrder = ref(null);

// hàm chuyển hướng đến trang tạo đơn hàng
const goToCreateOrder = () => {
  router.push("/orders/create");
};

// Bộ lọc thông minh chống crash giao diện khi trường dữ liệu bị null
const filteredOrders = computed(() => {
  if (!orders.value || !Array.isArray(orders.value)) return [];
  
  return orders.value.filter((order) => {
    if (!order) return false;
    
    const name = order.customerName ? order.customerName.toLowerCase() : "";
    const phone = order.numberPhone ? String(order.numberPhone) : "";
    const searchKey = keyword.value ? keyword.value.toLowerCase().trim() : "";

    return name.includes(searchKey) || phone.includes(searchKey);
  });
});

// Hàm gọi API lấy danh sách đơn hàng
const getOrders = async () => {
  try {
    const response = await axios.get(API);
    console.log("Dữ liệu đơn hàng nhận từ API:", response.data);
    orders.value = Array.isArray(response.data) ? response.data : [];
  } catch (error) {
    console.error("Lỗi khi kết nối API lấy danh sách đơn hàng:", error);
    orders.value = [];
  }
};

// Hàm xem chi tiết đơn hàng
const openOrderDetail = async (order) => {
  try {
    const response = await axios.get(`${API}/${order.orderId}/details`);
    selectedOrder.value = {
      ...order,
      items: response.data
    };
    showDetail.value = true;
  } catch (error) {
    console.error("Lỗi khi tải chi tiết đơn hàng:", error);
    alert("Không thể tải chi tiết đơn hàng này.");
  }
};

// Hàm cập nhật trạng thái đơn hàng
const updateStatus = async (orderId, status) => {
  try {
    await axios.put(`${API}/${orderId}/status?status=${status}`);
    alert("Cập nhật trạng thái thành công!");
    await getOrders();
  } catch (error) {
    console.error("Lỗi cập nhật trạng thái:", error);
    alert("Cập nhật trạng thái thất bại.");
  }
};

// Hàm hủy đơn hàng
const cancelOrder = async (orderId) => {
  if (!confirm("Bạn có chắc chắn muốn hủy đơn hàng này không?")) return;
  try {
    await axios.put(`${API}/${orderId}/cancel`);
    alert("Hủy đơn hàng thành công!");
    await getOrders();
  } catch (error) {
    console.error("Lỗi khi hủy đơn hàng:", error);
    alert("Hủy đơn hàng thất bại.");
  }
};

// Hàm xóa đơn hàng
const deleteOrder = async (orderId) => {
  if (!confirm("Bạn có chắc chắn muốn xóa đơn này không?")) return;

  try {
    await axios.delete(`${API}/${orderId}`);
    alert("Xóa đơn thành công!");
    await getOrders();
  } catch (error) {
    console.error("Lỗi xóa đơn:", error);
    alert(error.response?.data || "Xóa thất bại");
  }
};

// Hàm format tiền tệ an toàn
const formatMoney = (money) => {
  if (money === null || money === undefined) return "0 VNĐ";
  return Number(money).toLocaleString("vi-VN") + " VNĐ";
};

// Hàm format ngày tháng an toàn phòng dữ liệu lỗi ISO từ Backend
const formatDate = (dateString) => {
  if (!dateString) return "Chưa cập nhật";
  const date = new Date(dateString);
  return isNaN(date.getTime()) ? "Ngày không hợp lệ" : date.toLocaleString("vi-VN");
};

// Hàm gán class màu sắc dựa trên Trạng thái
const getStatusClass = (status) => {
  if (!status) return "unknown";

  if (status === "PENDING") return "pending";
  if (status === "CONFIRMED") return "confirmed";
  if (status === "PAID") return "paid";
  if (status === "DELIVERED") return "delivered";
  if (status === "CANCELLED") return "cancelled";

  return "unknown";
};

onMounted(() => {
  getOrders();
});
</script>

<style scoped>
.top-actions {
  display: flex;
  gap: 10px;
  align-items: center;
}

.add-order-btn {
  background: #2ecc71;
  color: white;
  padding: 10px 16px;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  font-weight: bold;
}

.add-order-btn:hover {
  opacity: 0.9;
}

.paid {
  color: #9b59b6;
  font-weight: bold;
}

.delivered {
  color: #27ae60;
  font-weight: bold;
}

.paid-btn {
  background: #9b59b6;
}

.deliver-btn {
  background: #16a085;
}

.delete-btn {
  background: #2c3e50;
}

.order-management {
  padding: 30px;
  background: #f5f6fa;
  min-height: 100vh;
}

.top-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.top-bar h1 {
  font-size: 24px;
  font-weight: 700;
  color: #2c3e50;
}

.search-input {
  width: 280px;
  padding: 10px 14px;
  border: 1px solid #ccc;
  border-radius: 8px;
  outline: none;
}

.search-input:focus {
  border-color: #3498db;
}

.order-table {
  width: 100%;
  border-collapse: collapse;
  background: white;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05);
}

.order-table th {
  background: #2c3e50;
  color: white;
  padding: 15px;
  font-weight: 600;
}

.order-table td {
  padding: 15px;
  text-align: center;
  border-bottom: 1px solid #eee;
  color: #333;
}

.order-table tbody tr:hover {
  background-color: #f9f9f9;
}

.action-buttons {
  display: flex;
  gap: 8px;
  justify-content: center;
}

button {
  border: none;
  padding: 8px 14px;
  border-radius: 6px;
  cursor: pointer;
  color: white;
  font-weight: 500;
  transition: opacity 0.2s;
}

button:hover {
  opacity: 0.9;
}

.detail-btn {
  background: #3498db;
}

.confirm-btn {
  background: #2ecc71;
}

.cancel-btn {
  background: #e74c3c;
}

.pending {
  color: #f39c12;
  font-weight: bold;
}

.confirmed {
  color: #2ecc71;
  font-weight: bold;
}

.cancelled {
  color: #e74c3c;
  font-weight: bold;
}

.unknown {
  color: #7f8c8d;
  font-style: italic;
}
</style>