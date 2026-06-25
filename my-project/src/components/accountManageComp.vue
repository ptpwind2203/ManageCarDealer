<template>
  <div class="product-management">

    <!-- HEADER -->
    <div class="top-bar">
      <h1>Quản Lý Tài Khoản</h1>

      <div class="actions">     <!-- ô tìm kiếm và nút thêm tài khoản -->
        <input v-model="keyword" type="text" placeholder="Tìm kiếm tài khoản..." class="search-input"/> <!-- ô tìm kiếm -->

        <button v-if="accountStore.isAdmin" class="add-btn" @click="openAddForm"> + Thêm Tài Khoản</button>  <!-- nút thêm tài khoản -->
      </div>
    </div>

    <!-- FORM điền thông tin tài khoản-->
    <div v-if="showForm" class="form-container">

      <h2>
        {{ isEdit ? "Sửa Thông Tin Cơ Bản Tài Khoản" : "Thêm Tài Khoản" }}
      </h2>

        <div class="form-group">
          <label>Tên tài khoản:</label>
          <input v-model="account.fullName" placeholder="Nhập tên tài khoản"/> 
        </div>

        <div class="form-group">
          <label>Tài khoản:</label>
          <input v-model="account.account" placeholder="Nhập tài khoản"/>
        </div>
        <div class="form-group">
          <label>Vai trò:</label>
          <select v-model="account.roleID"  required>
        <option value="" disabled>Chọn vai trò</option>

        <option
          v-for="role in defaultRole"
          :key="role.roleID"
          :value="role.roleID"
        >
          {{ role.roleName }}
        </option>
      </select>
        </div>
    
        <div class="form-buttons">
          <button class="save-btn" @click="saveAccount"> {{ isEdit ? "Cập Nhật" : "Lưu" }} </button>

          <button class="cancel-btn" @click="closeForm"> Hủy </button>
        </div>

    </div>
    <!-- TABLE -->
    <table class="product-table">

      <thead>
        <tr>
          <th>ID</th>
          <th>Họ và Tên</th>
          <th>Tên tài khoản</th>
          <th>Ngày sinh</th>
          <th>Số điện thoại</th>
          <th>Email</th>
          <th>Vai trò</th>
          <th>Chức Năng</th>
        </tr>
      </thead>

      <tbody>

        <tr v-for="infor in filteredAccounts " :key="infor.userID">

          <td>{{ infor.userID }}</td>
          <td>{{ infor.fullName }}</td>
          <td>{{ infor.account }}</td>
          <td>{{ infor.birthDate }}</td>
          <td>{{ infor.numberPhone }}</td>
          <td>{{ infor.email }}</td>
          <td>{{ infor.roleName }}</td>

          <td>
            <div class="action-buttons">
              <button v-if="accountStore.isAdmin" class="edit-btn" @click="openEditForm(infor)">Sửa</button>
              <router-link class="detail-btn" :to="`/accouonts/${infor.userID}`">Chi Tiết</router-link>
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
import { useAccountStore } from '@/stores/Account'

const accountStore = useAccountStore()
// ======================
// STATE
// ======================

const API = "http://localhost:8080/api/accounts";

const accounts = ref([]);
const keyword = ref("");
const showForm = ref(false);
const isEdit = ref(false);
const defaultRole = ref([]);

const defaultAccount = {
  userID: null,
  fullName: "",
  account: "",
  password: "",
  birthDate: "",
  email: "",
  numberPhone: "",
  address: "",
  roleID: null
};

const account = ref({ ...defaultAccount });

const getRoles = async () => {
    try {
        const { data } = await axios.get(`${API}/roles`);
        defaultRole.value = data;

        console.log("Vai trò:", data);
    } catch (error) {
        console.error("Lỗi khi lấy dữ liệu vai trò:", error);
        defaultRole.value = [];
    }
};
const getAccounts = async () => {
  try {
    const { data } = await axios.get(API);
    accounts.value = data;
  } catch (err) {
    console.log(err);
  }
};
// chức năng tìm kiếm xe theo tên hoặc hãng
const filteredAccounts = computed(() =>
  accounts.value.filter(infor =>
    infor.fullName?.toLowerCase().includes(keyword.value.toLowerCase())
      || infor.account?.toLowerCase().includes(keyword.value.toLowerCase())
      || infor.roleName?.toLowerCase().includes(keyword.value.toLowerCase())
      || infor.email?.toLowerCase().includes(keyword.value.toLowerCase())
      || infor.numberPhone?.toLowerCase().includes(keyword.value.toLowerCase())
  )
);




// API
// nhận dữ liệu từ API của backend và lưu vào trong data. sau đó gán dữ liệu data đó vào accounts
// const getAccounts = async () => {
//   try {
//     const { data } = await axios.get(API);
//     account.value = data;
//   } catch (err) {
//     console.log(err);
//   }
// };




// ======================
// FORM
// ======================
const saveAccount = async () => {
  try {
    if (isEdit.value) {
      await axios.put(
        `${API}/${account.value.userID}`,
        account.value
      );
    } else {
      await axios.post(
        API,
        account.value
      );
    }

    await getAccounts();
    closeForm();

  } catch (error) {
    console.error(error);
  }
};
const openAddForm = async () => {
  showForm.value = true;
  isEdit.value = false;
  account.value = { ...defaultAccount };
};
const openEditForm = (infor) => {
  showForm.value = true;
  isEdit.value = true;

  account.value = {
    userID: infor.userID,
    fullName: infor.fullName,
    account: infor.account,
    password: infor.password,
    birthDate: infor.birthDate,
    email: infor.email,
    numberPhone: infor.numberPhone,
    address: infor.address,
    roleID: infor.roleID
  };

  window.scrollTo({
    top: 0,
    behavior: "smooth"
  });
};
const closeForm = () => {
  showForm.value = false;
  account.value = { ...defaultAccount };
};



// ======================
// MOUNTED
// ======================

onMounted(() => {
  getAccounts();
  getRoles();
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
  width:200px;
  font-weight:600;
  color: red;
  font-size: 20px;
}

.form-group input{
  flex:1;
  font-size:20px;
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

/* ================= ACTION BUTTON  ================= */
.action-buttons{
  display:flex;
  justify-content:center;
  gap:8px;
}

.edit-btn{
  display:inline-block;
  padding:10px 18px;
  border-radius:10px;
  font-size: 12px;
  font-weight:600;
  text-decoration:none;
  color:white;
  background:#2ecc71;
  transition:0.25s;
  white-space: nowrap;
}

.edit-btn:hover{
  transform:translateY(-2px);
  background:#c90b0b;
  opacity:0.9;
}
.detail-btn{
  display:inline-block;
  padding:10px 18px;
  border-radius:10px;
  font-size: 12px;
  font-weight:600;
  text-decoration:none;
  color:white;
  background:#2ecc71;
  transition:0.25s;
  white-space: nowrap;
}

.detail-btn:hover{
  transform:translateY(-2px);
  background:#c90b0b;
  opacity:0.9;
}
/* ================= SELECT STYLE================= */
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