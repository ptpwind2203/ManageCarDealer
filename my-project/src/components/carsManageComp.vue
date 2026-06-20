<template>
  <div class="product-management">

    <!-- HEADER -->
    <div class="top-bar">
      <h1>Quản Lý Xe</h1>

      <div class="actions">     <!-- dùng để tạo ra ô tìm kiếm và nút thêm xe -->
        <input v-model="keyword" type="text" placeholder="Tìm kiếm xe..." class="search-input"/> <!-- ô tìm kiếm -->

        <button class="add-btn" @click="openAddForm"> + Thêm Xe</button>  <!-- nút thêm xe -->
      </div>
    </div>

    <!-- FORM điền thông tin xe-->
    <div v-if="showForm" class="form-container">

      <h2>
        {{ isEdit ? "Sửa Xe" : "Thêm Xe" }}
      </h2>

        <div class="form-group">
          <label>Tên xe:</label>
          <input v-model="car.carName" placeholder="Nhập tên xe"/>
        </div>

        <div class="form-group">
          <label>Hãng xe:</label>
          <input v-model="car.companyCar" placeholder="Nhập hãng xe"/>
        </div>

        <div class="form-group">
          <label>Dung tích động cơ:</label>
          <input v-model="car.engineCapacity" placeholder="Nhập dung tích" />
        </div>

        <div class="form-group">
          <label>Mô tả:</label>
          <input v-model="car.describe" placeholder="Nhập mô tả"/>
        </div>

        <div class="form-group">
          <label>Tên ảnh:</label>
          <input v-model="car.carImage" placeholder="VD: Audi_R8"/>
        </div>


        <div class="form-buttons">
          <button class="save-btn" @click="saveCar"> {{ isEdit ? "Cập Nhật" : "Lưu" }} </button>

          <button class="cancel-btn" @click="closeForm"> Hủy </button>
        </div>

    </div>

    <!-- TABLE -->
    <table class="product-table">

      <thead>
        <tr>
          <th>ID</th>
          <th>Hình</th>
          <th>Tên Xe</th>
          <th>Hãng</th>
          <th>Động Cơ</th>
          <th>Mô Tả</th>
          <th>Chức Năng</th>
        </tr>
      </thead>

      <tbody>

        <tr v-for="item in filteredCars" :key="item.carID">

          <td>{{ item.carID }}</td>

          <td><img :src="getImage(item.carImage)" class="car-image"/></td>

          <td>{{ item.carName }}</td>
          <td>{{ item.companyCar }}</td>
          <td>{{ item.engineCapacity }}</td>
          <td>{{ item.describe }}</td>

          <td>
            <div class="action-buttons">
              <router-link class="edit-btn" :to="`/cars/${item.carID}`">chi tiết</router-link>

            
            </div>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import axios from "axios";

// ======================
// STATE
// ======================

const API = "http://localhost:8080/api/cars";

const cars = ref([]);
const keyword = ref("");
const showForm = ref(false);
const isEdit = ref(false);

const defaultCar = {
  carID: null,
  carName: "",
  companyCar: "",
  engineCapacity: "",
  describe: "",
  carImage: ""
};

const car = ref({ ...defaultCar });

// chức năng tìm kiếm xe theo tên hoặc hãng
const filteredCars = computed(() =>
  cars.value.filter(item =>
    item.carName?.toLowerCase().includes(keyword.value.toLowerCase())
      || item.companyCar?.toLowerCase().includes(keyword.value.toLowerCase())
  )
);


// IMAGE 
// vì đặt tên ảnh trong database chứ không phải link nên dùng hàm này để lấy link ảnh
// đặt tên ảnh trùng với tên đường link ảnh
const getImage = (name) => {
  try {
    return require(`../assets/${name}.jpg`);
  } catch {
    return require("../assets/logoCarDealer.png");
  }
};

// API
// nhận dữ liệu từ API của backend và lưu vào trong data. sau đó gán dữ liệu data đó vào cars
const getCars = async () => {
  try {
    const { data } = await axios.get(API);
    cars.value = data;
  } catch (err) {
    console.log(err);
  }
};


//hàm dùng để lưu dữ liệu xe
const saveCar = async () => {
  try {
    await axios.post(API, car.value);
    await getCars();
    closeForm();
  } catch (err) {
    console.log(err);
  }
};


// ======================
// FORM
// ======================

const openAddForm = async () => {
  showForm.value = true;
  isEdit.value = false;
  car.value = { ...defaultCar };

  
};

const closeForm = () => {
  showForm.value = false;
  
};



// ======================
// MOUNTED
// ======================

onMounted(() => {
  getCars();
});
</script>

<style scoped>
.product-management{
  padding:30px;
  background:#f5f6fa;
  min-height:100vh;
  font-family: Arial, sans-serif;
}

/* ================= TOP BAR ================= */
.top-bar{
  display:flex;
  justify-content:space-between;
  align-items:center;
  margin-bottom:25px;
}

.top-bar h1{
  font-size:24px;
  font-weight:700;
  color:#2c3e50;
}

.actions{
  display:flex;
  gap:10px;
}

/* ================= INPUT ================= */
.search-input,
.form-container input{
  padding:10px;
  border:1px solid #ccc;
  border-radius:8px;
  outline:none;
  transition:0.2s;
}

.search-input{
  width:250px;
}

.search-input:focus,
.form-container input:focus{
  border-color:#3498db;
  box-shadow:0 0 0 3px rgba(52,152,219,0.15);
}

/* ================= BUTTON BASE ================= */
button{
  border:none;
  cursor:pointer;
  color:white;
  border-radius:8px;
  font-weight:600;
  transition:0.2s;
}

/* ================= ACTION BUTTONS ================= */
.add-btn{
  background:#2ecc71;
  padding:10px 18px;
}

.add-btn:hover{
  background:#27ae60;
}

/* ================= FORM ================= */
.form-container{
  background:white;
  padding:20px;
  border-radius:10px;
  margin-bottom:20px;
  display:flex;
  flex-direction:column;
  gap:12px;
  box-shadow:0 2px 10px rgba(0,0,0,0.05);
}

.form-container h2{
  margin-bottom:10px;
  color:#2c3e50;
}

.form-group{
  display:flex;
  align-items:center;
  gap:15px;
}

.form-group label{
  width:150px;
  font-weight:600;
  color:#333;
}

.form-group input{
  flex:1;
}

/* FORM BUTTONS */
.form-buttons{
  display:flex;
  gap:10px;
  margin-top:10px;
}

.form-buttons button{
  padding:10px 14px;
}

.save-btn{
  background:#3498db;
}

.save-btn:hover{
  background:#2980b9;
}

.cancel-btn{
  background:#7f8c8d;
}

.cancel-btn:hover{
  background:#636e72;
}

/* ================= TABLE ================= */
.product-table{
  width:100%;
  border-collapse:collapse;
  background:white;
  border-radius:10px;
  overflow:hidden;
  box-shadow:0 2px 10px rgba(0,0,0,0.05);
}

.product-table th{
  background:#34495e;
  color:white;
  padding:15px;
  text-align:center;
}

.product-table td{
  padding:15px;
  text-align:center;
  border-bottom:1px solid #eee;
}

/* hover row */
.product-table tbody tr:hover{
  background:#f2f6fc;
}

/* ================= IMAGE ================= */
.car-image{
  width:120px;
  height:80px;
  object-fit:cover;
  border-radius:8px;
}

/* ================= ACTION BUTTON (CHỈ CÒN 1) ================= */
.action-buttons{
  display:flex;
  justify-content:center;
}

.edit-btn{
  display:inline-block;
  padding:10px 18px;
  border-radius:10px;
  font-size: 12px;
  font-weight:600;
  text-decoration:none;
  color:white;
  background:linear-gradient(135deg,#f39c12,#e67e22);
  transition:0.25s;

  white-space: nowrap;
}

.edit-btn:hover{
  transform:translateY(-2px);
  opacity:0.9;
}

/* ================= SELECT STYLE (GIỮ NGUYÊN) ================= */
.Select select{
  flex:1;
  padding:12px 14px;
  border:1px solid #dcdde1;
  border-radius:10px;
  background:white;
  font-size:15px;
  color:#2f3640;
  outline:none;
  transition:all .25s ease;
  cursor:pointer;

  appearance:none;
  -webkit-appearance:none;
  -moz-appearance:none;

  background-image:url("data:image/svg+xml;charset=UTF-8,%3Csvg xmlns='http://www.w3.org/2000/svg' width='20' height='20' viewBox='0 0 20 20'%3E%3Cpath fill='%23666' d='M5.5 7.5l4.5 5 4.5-5z'/%3E%3C/svg%3E");
  background-repeat:no-repeat;
  background-position:right 12px center;
  background-size:18px;
}

.Select select:hover{
  border-color:#3498db;
}

.Select select:focus{
  border-color:#3498db;
  box-shadow:0 0 0 4px rgba(52,152,219,0.15);
}

.Select option{
  padding:10px;
}

.Select{
  display:flex;
  align-items:center;
  gap:15px;
}

.Select label{
  width:150px;
  font-weight:600;
  color:#333;
}
</style>