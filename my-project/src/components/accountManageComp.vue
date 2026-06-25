<template>
  <div class="product-management">

    <!-- HEADER -->
    <div class="top-bar">
      <h1>Quản Lý Tài Khoản</h1>

      <div class="actions">
        <input
          v-model="keyword"
          type="text"
          placeholder="Tìm kiếm tài khoản..."
          class="search-input"
        />

        <button class="add-btn" @click="openAddForm">
          + Thêm Tài Khoản
        </button>
      </div>
    </div>

    <!-- FORM THÊM -->
    <div v-if="showForm" class="form-container">

      <h2>Thêm Tài Khoản</h2>

      <div class="form-group">
        <label>Họ và tên:</label>
        <input v-model="account.fullName" placeholder="Nhập họ và tên"/>
      </div>

      <div class="form-group">
        <label>Tài khoản:</label>
        <input v-model="account.account" placeholder="Nhập tài khoản"/>
      </div>

      <div class="form-group">
        <label>Mật khẩu:</label>
        <input
          v-model="account.password"
          type="password"
          placeholder="Nhập mật khẩu"
        />
      </div>

      <div class="form-group">
        <label>Số điện thoại:</label>
        <input v-model="account.numberPhone" placeholder="Nhập số điện thoại"/>
      </div>

      <div class="form-group">
        <label>Email:</label>
        <input v-model="account.email" placeholder="Nhập email"/>
      </div>

      <div class="form-group">
        <label>Ngày sinh:</label>
        <input v-model="account.birthDate" type="date"/>
      </div>

      <div class="form-group">
        <label>Địa chỉ:</label>
        <input v-model="account.address" placeholder="Nhập địa chỉ"/>
      </div>

      <div class="form-group">
        <label>Vai trò:</label>
        <select v-model="account.roleID">
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
        <button class="save-btn" @click="saveAccount">
          Lưu
        </button>

        <button class="cancel-btn" @click="closeForm">
          Hủy
        </button>
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
        <tr v-for="infor in filteredAccounts" :key="infor.userID">

          <td>{{ infor.userID }}</td>
          <td>{{ infor.fullName }}</td>
          <td>{{ infor.account }}</td>
          <td>{{ infor.birthDate }}</td>
          <td>{{ infor.numberPhone }}</td>
          <td>{{ infor.email }}</td>
          <td>{{ infor.roleName }}</td>

          <td>
            <router-link
              class="detail-btn"
              :to="`/accounts/${infor.userID}`"
            >
              Chi Tiết
            </router-link>
          </td>

        </tr>
      </tbody>

    </table>

  </div>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import axios from "axios";

const API = "http://localhost:8080/api/accounts";

const accounts = ref([]);
const keyword = ref("");
const showForm = ref(false);
const defaultRole = ref([]);

const account = ref({
  userID: null,
  fullName: "",
  account: "",
  password: "",
  birthDate: "",
  email: "",
  numberPhone: "",
  address: "",
  roleID: null
});

// GET roles
const getRoles = async () => {
  try {
    const { data } = await axios.get(`${API}/roles`);
    defaultRole.value = data;
  } catch (error) {
    console.error(error);
  }
};

// GET accounts
const getAccounts = async () => {
  try {
    const { data } = await axios.get(API);
    accounts.value = data;
  } catch (error) {
    console.error(error);
  }
};

// SEARCH
const filteredAccounts = computed(() =>
  accounts.value.filter(infor =>
    infor.fullName?.toLowerCase().includes(keyword.value.toLowerCase()) ||
    infor.account?.toLowerCase().includes(keyword.value.toLowerCase()) ||
    infor.roleName?.toLowerCase().includes(keyword.value.toLowerCase()) ||
    infor.email?.toLowerCase().includes(keyword.value.toLowerCase()) ||
    infor.numberPhone?.toLowerCase().includes(keyword.value.toLowerCase())
  )
);

// ADD
const saveAccount = async () => {
  try {
    await axios.post(API, account.value);

    alert("Thêm tài khoản thành công");

    await getAccounts();

    closeForm();
  } catch (error) {
    console.error(error);
  }
};

const openAddForm = () => {
  showForm.value = true;

  account.value = {
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

  window.scrollTo({ top: 0, behavior: "smooth" });
};

const closeForm = () => {
  showForm.value = false;
  account.value = {};
};

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

.top-bar{
  display:flex;
  justify-content:space-between;
  align-items:center;
  margin-bottom:25px;
}

.actions{
  display:flex;
  gap:10px;
}

.search-input{
  width:250px;
  padding:10px;
  border:1px solid #ccc;
  border-radius:8px;
}

.add-btn{
  background:#2ecc71;
  padding:10px 18px;
  color:white;
  border:none;
  border-radius:8px;
  cursor:pointer;
}

.form-container{
  background:white;
  padding:20px;
  border-radius:10px;
  margin-bottom:20px;
}

.form-group{
  display:flex;
  gap:15px;
  margin-bottom:10px;
}

.form-group label{
  width:150px;
  font-weight:600;
}

.form-group input,
.form-group select{
  flex:1;
  padding:10px;
}

.form-buttons{
  display:flex;
  gap:10px;
}

.save-btn{
  background:#3498db;
  color:white;
  padding:10px 14px;
}

.cancel-btn{
  background:#7f8c8d;
  color:white;
  padding:10px 14px;
}

.product-table{
  width:100%;
  border-collapse:collapse;
  background:white;
}

.product-table th{
  background:#34495e;
  color:white;
  padding:12px;
}

.product-table td{
  padding:12px;
  text-align:center;
  border-bottom:1px solid #eee;
}

.detail-btn{
  background:#2ecc71;
  color:white;
  padding:8px 12px;
  border-radius:6px;
  text-decoration:none;
}
</style>