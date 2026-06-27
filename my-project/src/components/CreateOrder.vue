<template>
  <div class="create-order">
    <h1>Tạo Đơn Hàng</h1>

    <!-- Thông tin khách -->
    <div class="form-section">
      <input v-model="order.customerId" type="number" placeholder="Mã khách hàng" />
      <input v-model="order.customerName" type="text" placeholder="Tên khách hàng" />
      <input v-model="order.numberPhone" type="text" placeholder="Số điện thoại" />
      <input v-model="order.email" type="email" placeholder="Email" />
      <input v-model="order.address" type="text" placeholder="Địa chỉ" />
    </div>

    <!-- Danh sách sản phẩm -->
    <div class="item-section">
      <h2>Danh sách xe</h2>

      <div
        class="item-row"
        v-for="(item, index) in order.items"
        :key="index"
      >
        <input v-model="item.carId" type="number" placeholder="Car ID" />
        <input v-model="item.colorId" type="number" placeholder="Color ID" />
        <input v-model="item.versionId" type="number" placeholder="Version ID" />
        <input v-model="item.quantity" type="number" placeholder="Số lượng" />

        <input v-model="item.color" type="text" placeholder="Tên màu" />
        <input v-model="item.version" type="text" placeholder="Phiên bản" />

        <button class="remove-btn" @click="removeItem(index)">
          Xóa
        </button>
      </div>

      <button class="add-btn" @click="addItem">
        + Thêm xe
      </button>
    </div>

    <!-- Submit -->
    <div class="submit-section">
      <button class="submit-btn" @click="createOrder">
        Tạo đơn hàng
      </button>
    </div>
  </div>
</template>

<script setup>
import axios from "axios";
import { reactive } from "vue";

const API = "http://localhost:8080/api/orders";

const order = reactive({
  customerId: "",
  customerName: "",
  numberPhone: "",
  email: "",
  address: "",
  items: [
    {
      carId: "",
      colorId: "",
      versionId: "",
      quantity: 1,
      color: "",
      version: ""
    }
  ]
});

// thêm xe
const addItem = () => {
  order.items.push({
    carId: "",
    colorId: "",
    versionId: "",
    quantity: 1,
    color: "",
    version: ""
  });
};

// xóa xe
const removeItem = (index) => {
  order.items.splice(index, 1);
};

// validate frontend
const validateForm = () => {
  if (!order.customerName.trim()) {
    alert("Tên khách hàng không được để trống");
    return false;
  }

  if (!order.numberPhone.trim()) {
    alert("Số điện thoại không được để trống");
    return false;
  }

  if (!order.address.trim()) {
    alert("Địa chỉ không được để trống");
    return false;
  }

  if (order.items.length === 0) {
    alert("Phải có ít nhất 1 xe");
    return false;
  }

  return true;
};

// tạo đơn
const createOrder = async () => {
  if (!validateForm()) return;

  try {
    const payload = {
      customerId: Number(order.customerId),
      customerName: order.customerName,
      numberPhone: order.numberPhone,
      email: order.email,
      address: order.address,
      items: order.items.map(item => ({
        carId: Number(item.carId),
        colorId: Number(item.colorId),
        versionId: Number(item.versionId),
        quantity: Number(item.quantity),
        color: item.color,
        version: item.version
      }))
    };

    console.log("Payload gửi backend:", payload);

    await axios.post(API, payload);

    alert("Tạo đơn hàng thành công!");

    // reset form
    order.customerId = "";
    order.customerName = "";
    order.numberPhone = "";
    order.email = "";
    order.address = "";
    order.items = [
      {
        carId: "",
        colorId: "",
        versionId: "",
        quantity: 1,
        color: "",
        version: ""
      }
    ];
  } catch (error) {
    console.error(error);
    alert(error.response?.data || "Tạo đơn hàng thất bại");
  }
};
</script>

<style scoped>
.create-order {
  padding: 30px;
  background: #f5f6fa;
  min-height: 100vh;
}

h1 {
  margin-bottom: 20px;
}

.form-section,
.item-section {
  background: white;
  padding: 20px;
  border-radius: 10px;
  margin-bottom: 20px;
}

input {
  width: 100%;
  padding: 12px;
  margin-bottom: 12px;
  border: 1px solid #ccc;
  border-radius: 8px;
}

.item-row {
  border: 1px solid #eee;
  padding: 15px;
  margin-bottom: 15px;
  border-radius: 8px;
}

button {
  border: none;
  padding: 10px 18px;
  border-radius: 8px;
  cursor: pointer;
  color: white;
}

.add-btn {
  background: #3498db;
}

.remove-btn {
  background: #e74c3c;
}

.submit-btn {
  background: #2ecc71;
  font-size: 16px;
}

.submit-section {
  text-align: right;
}
</style>