<template>
  <div class="page-wrapper">

    <!-- Header -->
    <div class="header">
      <div class="breadcrumb">
        <router-link to="/accounts">Quản lý tài khoản</router-link>
        <span>/</span>
        <router-link :to="`/accounts/${accountID}`">
          Chi tiết tài khoản
        </router-link>
      </div>

      <div class="header-actions">

          <button @click="openEditForm(account)">
          Sửa
        </button>

        <router-link
          :to="`/accounts/${accountID}/change-password`"
          class="btn"
        >
          Đổi mật khẩu
        </router-link>

      </div>
    </div>

    <h1 class="title">
      {{ account.fullName }}
    </h1>

    <div class="main-card">

      <div class="info-box">

        <div class="row">
            <div class="description large">
            <div class="label">Tài khoản</div>
            <div class="value">{{ account.account }}</div>
          </div>

          <div class="description small">
            <div class="label">ID</div>
            <div class="value">{{ account.userID }}</div>
          </div>

        </div>

        <div class="description">
          <div class="label">Họ và tên</div>
          <div class="value">{{ account.fullName }}</div>
        </div>

        <div class="row">
          <div class="description large">
            <div class="label">Email</div>
            <div class="value">{{ account.email }}</div>
          </div>

          <div class="description small">
            <div class="label">Số điện thoại</div>
            <div class="value">{{ account.numberPhone }}</div>
          </div>

        </div>

        <div class="description">
          <div class="label">Ngày sinh</div>
          <div class="value">{{ account.birthDate }}</div>
        </div>

        <div class="description">
          <div class="label">Địa chỉ</div>
          <div class="value">{{ account.address }}</div>
        </div>

        <div class="description">
          <div class="label">Vai trò</div>
          <div class="value">{{ account.roleName }}</div>
        </div>

      </div>
    </div>

    

  </div>
<accountDetailForm
  v-if="showEditForm"
  :account="selectedAccount"
  @close="closeEditForm"
  @saved="reloadAfterSave"
/>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRoute } from "vue-router";
import axios from "axios";
import accountDetailForm from "@/components/accountDetailForm.vue";


const API = "http://localhost:8080/api/accounts";

const route = useRoute();
const accountID = route.params.id;

const showEditForm = ref(false);
const selectedAccount = ref(null);

const openEditForm = (account) => {
  selectedAccount.value = account;
  showEditForm.value = true;
};
const closeEditForm = () => {
  showEditForm.value = false;
  selectedAccount.value = null;
};
const reloadAfterSave = async () => {
  await loadAccount();   
  closeEditForm();
};

const defaultAccount = {
  userID: null,
  fullName: "",
  account: "",
  birthDate: "",
  email: "",
  password: "",
  confirmPassword: "",
  numberPhone: "",
  address: "",
  roleID: null,
  roleName: ""
};

const account = ref({ ...defaultAccount });


const loadAccount = async () => {
  try {
    const response = await axios.get(`${API}/${accountID}`);

    console.log("Response:", response);
    console.log("Data:", response.data);

    account.value = response.data;
  } catch (error) {
    console.error("Lỗi:", error);
  }
};


onMounted(() => {
  loadAccount();
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
    /* text-shadow: 2px 2px 8px rgba(255, 0, 0, 0.9); */
    letter-spacing: 0.5px;

}
.title {
  transition: all 0.3s ease;
}

.title:hover {
  transform: translateY(-2px);
  /* text-shadow: 2px 4px 12px rgba(0, 123, 255, 0.3); */
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

  background: #16a34a;
}

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

/* ================= ACCOUNT INFO ================= */

.info-box {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 14px;
}

.description {
  display: grid;
  grid-template-columns: 220px 1fr;

  border: 1px solid #e2e8f0;
  border-radius: 12px;
  overflow: hidden;

  background: #fff;
  transition: all 0.25s ease;
}

.description:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
}
.row {
  display: flex;
  gap: 20px;
}
.small {
  flex: 4;
}

.large {
  flex: 6;
}

.label {
  background: #ffffff;
  color: rgb(255, 0, 0);
  font-weight: 700;
  padding: 14px 18px;

  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 18px;
}

.value {
  padding: 14px 18px;

  display: flex;
  align-items: center;

  color: #0f172a;
  font-weight: 500;
  background: #f8fafc;
  font-size: 20px;
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