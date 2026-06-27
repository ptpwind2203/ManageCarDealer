<template>
  <div class="modal-overlay">
    <div class="detail-container">
      <h2>Chi tiết đơn hàng #{{ order?.orderId }}</h2>

      <div class="customer-info">
        <p><b>Khách hàng:</b> {{ order?.customerName || "Không rõ" }}</p>
        <p><b>SĐT:</b> {{ order?.numberPhone || "Không có" }}</p>
        <p><b>Email:</b> {{ order?.email || "Không có" }}</p>
        <p><b>Địa chỉ:</b> {{ order?.address || "Không có" }}</p>
      </div>

      <table class="detail-table">
        <thead>
          <tr>
            <th>Mã xe</th>
            <th>Màu</th>
            <th>Phiên bản</th>
            <th>Số lượng</th>
            <th>Tổng tiền</th>
          </tr>
        </thead>

        <tbody>
          <tr
            v-for="item in order?.items"
            :key="item?.id?.carId"
          >
            <td>{{ item?.id?.carId }}</td>
            <td>{{ item?.color || "Mặc định" }}</td>
            <td>{{ item?.version || "Tiêu chuẩn" }}</td>
            <td>{{ item?.quantityBuy || 0 }}</td>
            <td>{{ formatMoney(item?.totalPrice) }}</td>
          </tr>
          <tr v-if="!order?.items || order.items.length === 0">
            <td colspan="5" style="padding: 15px; color: #7f8c8d;">
              Không có chi tiết sản phẩm nào.
            </td>
          </tr>
        </tbody>
      </table>

      <div class="btn-group">
        <button @click="$emit('close')">Đóng</button>
      </div>
    </div>
  </div>
</template>

<script setup>
/* global defineProps */

defineProps({
  order: Object
});

const formatMoney = (money) => {
  if (money === null || money === undefined) return "0 VNĐ";
  return Number(money).toLocaleString("vi-VN") + " VNĐ";
};
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  inset: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 999;
}

.detail-container {
  width: 800px;
  background: white;
  padding: 25px;
  border-radius: 12px;
  box-shadow: 0 5px 15px rgba(0,0,0,0.3);
}

.customer-info {
  margin: 20px 0;
  line-height: 1.6;
}

.detail-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 10px;
}

.detail-table th {
  background: #34495e;
  color: white;
  padding: 12px;
}

.detail-table td {
  padding: 12px;
  text-align: center;
  border-bottom: 1px solid #ddd;
}

.btn-group {
  margin-top: 20px;
  text-align: right;
}

button {
  background: #e74c3c;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  font-weight: 600;
}

button:hover {
  opacity: 0.9;
}
</style>