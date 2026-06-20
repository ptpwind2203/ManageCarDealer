<template>
  <div class="page-wrapper">

    <!-- Header -->
    <div class="header">
      <div class="breadcrumb">
        <router-link to="/cars">Quản lý xe</router-link>
        <span>/</span>
        <router-link :to="`/cars/${carID}`">Chi tiết xe</router-link>
      </div>

      <div class="header-actions">
        <button @click="openAddForm" class="btn btn-add">Thêm chi tiết xe</button>
      </div>
    </div>

    <!-- Title -->
    <h1 class="title"> {{ car.carName }}</h1>

    <!-- Main Card -->
    <div class="main-card">

      <!-- Image -->
      <div class="image-box">
        <img :src="getImage(car.carImage)" alt="vehicle" />
      </div>

      <!-- Info -->
      <div class="info-box">
        <br>
        <div class="description">
            <span>Hãng xe</span>
            <p>{{ car.companyCar }}</p>
        </div>

        <div class="description">
            <span>Động cơ</span>
            <p>{{ car.engineCapacity }}</p>
        </div>
        <div class="description">
            <span>Mô tả</span>
            <p>{{ car.describe }}</p>
        </div>

      </div>
    </div>

    <!-- Table Section -->
    <div class="table-wrapper">

      <h2 class="section-title">Thông tin chi tiết </h2>

      <table>
        <thead>
          <tr>
            <th>STT</th>
            <th>Màu sắc</th>
            <th>Phiên bản</th>
            <th>Số lượng</th>
            <th>Giá bán</th>
            <th>Thao tác</th>
          </tr>
        </thead>

        <tbody>
          <tr v-for="(detail, index) in details" :key="index">
            <td>{{ index + 1 }}</td>  <!-- STT -->
            <td>{{ detail.colorName }}</td> <!-- Màu sắc -->
            <td>{{ detail.versionName }}</td> <!-- Phiên bản -->
            <td>
                <span v-if="detail.quantity === 0" style="color:red; font-weight:700;">
                  Hết hàng
                </span>

                <span v-else>
                  {{ detail.quantity }}
                </span>
            </td> <!-- Số lượng -->
            <td>{{ detail.price?.toLocaleString("vi-VN") }} VNĐ</td> <!-- Giá bán -->
            <td> <!-- Thao tác -->
              <div class="table-actions">
                <button @click="openEditForm(detail)" class="btn btn-edit btn-small">Sửa</button>
                <button class="btn btn-delete btn-small">Xóa</button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>

    </div>
  <CarDetailForm 
      :car="car" 
      :carDetail="selectedDetail" 
      v-if="showForm" 
      @close="showForm = false" 
      @saved="loadCarDetails"/>
  </div>

 
</template>

<script setup>
import { ref, onMounted } from "vue";
import axios from "axios";
import { useRoute } from "vue-router";
import CarDetailForm from "./carDetailForm.vue";

const selectedDetail = ref(null);

const openAddForm = () => {
  selectedDetail.value = null; // Đặt lại chi tiết đã chọn
  showForm.value = true; // Hiển thị form
};

const openEditForm = (detail) => {
  selectedDetail.value = detail; // Đặt chi tiết đã chọn
  showForm.value = true; // Hiển thị form
  console.log("Chi tiết được chọn để sửa:", detail);
};

const showForm = ref(false);

const API = "http://localhost:8080/api/cars";

const route = useRoute();
const carID = route.params.id;

// ==================== CAR ====================

const defaultCar = {
  carID: null,
  carName: "",
  companyCar: "",
  engineCapacity: "",
  describe: "",
  carImage: ""
};

const car = ref({ ...defaultCar });

// ==================== CAR DETAILS ====================

const details = ref([]);

// ==================== IMAGE ====================

const getImage = (name) => {
  try {
    return require(`../assets/${name}.jpg`);
  } catch {
    return require("../assets/logoCarDealer.png");
  }
};

// ==================== API ====================

// Lấy thông tin cơ bản của xe
const getCarById = async () => {
  try {
    const { data } = await axios.get(
      `${API}/${carID}`
    );

    car.value = data;

    console.log("Thông tin xe:", data);
  } catch (err) {
    console.error("Lỗi lấy thông tin xe:", err);
  }
};

// Lấy danh sách chi tiết xe
const getCarDetails = async () => {
  try {
    const { data } = await axios.get(
      `${API}/${carID}/detail`
    );
    console.log(data);
    
    details.value = data;

    console.log("Chi tiết xe:", data);
  } catch (err) {
    console.error("Lỗi lấy chi tiết xe:", err);
  }
};
const loadCarDetails = async () => {
    const { data } = await axios.get(
        `${API}/${carID}/detail`
    );

    details.value = data;
};

// ==================== MOUNT ====================

onMounted(async () => {
  await getCarById();
  await getCarDetails();
});
</script>

<style scoped>
:root {
  --primary: #2563eb;
  --primary-hover: #1d4ed8;

  --success: #16a34a;

  --danger: #dc2626;

  --bg: #f8fafc;
  --card: #ffffff;

  --text: #0f172a;
  --text-light: #64748b;

  --border: #e2e8f0;
}

/* ================= RESET ================= */

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

/* ================= PAGE ================= */

.page-wrapper {
  max-width: 1400px;
  margin: auto;
  padding: 30px;
  background: var(--bg);
}

/* ================= HEADER ================= */
.breadcrumb {
  display: flex;
  align-items: center;
  gap: 5px;
}

.breadcrumb a {
  text-decoration: none; /* bỏ gạch dưới */
  color: #000;           /* màu đen */
  font-size: 20px;       /* cỡ chữ 10px */
  transition: color 0.3s ease;
}

.breadcrumb a:hover {
  color: #007bff; /* màu xanh khi hover */
}
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.breadcrumb {
  display: flex;
  gap: 8px;
  color: var(--text-light);
  font-size: 14px;
}

/* ================= TITLE ================= */

.title {
    font-size: 50px;
    font-style: italic;
    font-weight: 700;
    color: black;
    text-align: center;
    text-shadow: 2px 2px 8px rgba(255, 0, 0, 0.9);
    background: linear-gradient(90deg, #007bff, #00c6ff);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    letter-spacing: 0.5px;

}
.title {
  transition: all 0.3s ease;
}

.title:hover {
  transform: translateY(-2px);
  text-shadow: 2px 4px 12px rgba(0, 123, 255, 0.3);
}
.title {
  position: relative;
}

.title::after {
  content: "";
  position: absolute;
  left: 0;
  bottom: -6px;
  width: 100%;
  height: 3px;
  background: #ff0000;
  border-radius: 2px;
}
/* ================= BUTTON ================= */

.header-actions {
  display: flex;
  gap: 10px;
}

.btn {
  border: none;
  cursor: pointer;
  padding: 10px 16px;
  border-radius: 10px;
  font-weight: 600;
  color: white;
  transition: all .25s ease;

  /* mặc định tất cả nút màu xanh lá */
  background: #16a34a;
}

/* hover ALL -> đỏ */
.btn:hover {
  background: #dc2626 !important;
  transform: translateY(-2px);
}

/* small button */
.btn-small {
  padding: 6px 12px;
  font-size: 12px;
  border-radius: 8px;
}

/* ================= CARD ================= */

.main-card {
  display: flex;
  gap: 24px;
  background: var(--card);
  border: 1px solid var(--border);
  border-radius: 16px;
  padding: 20px;
  margin-bottom: 25px;
}

/* image */
.image-box {
  width: 40%;
}

.image-box img {
  width: 100%;
  height: 320px;
  object-fit: contain;
  background: #f1f5f9;
  border-radius: 12px;
}

/* info */
.info-box {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.info-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 15px;
}

.info-item {
  padding: 12px;
  border: 1px solid var(--border);
  border-radius: 10px;
  background: #f8fafc;
}

.info-item label {
  font-size: 12px;
  color: var(--text-light);
}

.info-item span {
  font-weight: 600;
  color: var(--text);
}

/* description */
.description {
  padding: 14px;
  border: 1px solid var(--border);
  border-radius: 10px;
  background: #f8fafc;
}

.description span {
  color: #dc2626; /* màu đỏ */
  font-weight: 600;
}


/* ================= TABLE ================= */

.table-wrapper {
  background: white;
  border: 1px solid var(--border);
  border-radius: 16px;
  padding: 16px;
}

.section-title {
  font-size: 20px;
  margin-bottom: 12px;
  color: var(--text);
}

table {
  width: 100%;
  border-collapse: collapse;
}

thead {
  background: #f8fafc;
}

th, td {
  padding: 14px;
  text-align: center;
  border-bottom: 1px solid #eee;
}

tbody tr:hover {
  background: #eff6ff;
}

/* table actions */
.table-actions {
  display: flex;
  justify-content: center;
  gap: 8px;
}

/* ================= RESPONSIVE ================= */

@media (max-width: 992px) {
  .header {
    flex-direction: column;
    align-items: flex-start;
    gap: 10px;
  }

  .main-card {
    flex-direction: column;
  }

  .image-box {
    width: 100%;
  }

  .info-grid {
    grid-template-columns: 1fr;
  }
}
</style>