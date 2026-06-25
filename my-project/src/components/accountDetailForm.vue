<template>
  <div class="modal-overlay">
    <div class="car-detail-container">

      <form class="car-detail-form" @submit.prevent="handleSubmit">

        <!-- HEADER -->
        <div class="form-header">
          <div class="form-header-icon">👤</div>
          <div>
            <p class="form-title">Sửa thông tin tài khoản</p>
            <p class="form-subtitle">Cập nhật thông tin người dùng</p>
          </div>
        </div>

        <!-- USER ID (readonly) -->
        <div class="form-group">
          <label>ID tài khoản</label>
          <p>{{ formData.userID }}</p>
        </div>

        <!-- FULL NAME -->
        <div class="form-group">
          <label>Họ và tên</label>
          <input v-model="formData.fullName" placeholder="Nhập họ và tên" />
        </div>

        <!-- ACCOUNT -->
        <div class="form-group">
          <label>Tài khoản</label>
          <input v-model="formData.account" placeholder="Nhập tài khoản" />
        </div>

        <!-- PHONE + EMAIL -->
        <div class="row-2">

          <div class="form-group">
            <label>Số điện thoại</label>
            <input v-model="formData.numberPhone" placeholder="Nhập số điện thoại" />
          </div>

          <div class="form-group">
            <label>Email</label>
            <input v-model="formData.email" placeholder="Nhập email" />
          </div>

        </div>

        <!-- BIRTH + ADDRESS -->
        <div class="row-2">

          <div class="form-group">
            <label>Ngày sinh</label>
            <input type="date" v-model="formData.birthDate" />
          </div>

          <div class="form-group">
            <label>Địa chỉ</label>
            <input v-model="formData.address" placeholder="Nhập địa chỉ" />
          </div>

        </div>

        <!-- ROLE -->
        <div class="form-group">
          <label>Vai trò</label>

          <div class="select-wrap">
            <select v-model="formData.roleID" required>
              <option value="" disabled>Chọn vai trò</option>

              <option
                v-for="role in defaultRole"
                :key="role.roleID"
                :value="role.roleID"
              >
                {{ role.roleName }}
              </option>
            </select>

            <span class="select-icon">▾</span>
          </div>
        </div>

        <hr class="form-divider" />

        <!-- BUTTON -->
        <div class="button-group">
          <button type="button" class="btn-cancel" @click="emit('close')">
            Hủy
          </button>

          <button type="submit" class="btn-save">
            Lưu thay đổi
          </button>
        </div>

      </form>

    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, defineProps, defineEmits } from "vue";
import axios from "axios";

const API = "http://localhost:8080/api/accounts";

const emit = defineEmits(["close", "saved"]);

const props = defineProps({
  account: {
    type: Object,
    required: true
  }
});

const defaultRole = ref([]);

// FORM DATA
const formData = ref({
  userID: null,
  fullName: "",
  account: "",
  numberPhone: "",
  email: "",
  birthDate: "",
  address: "",
  roleID: null
});

// LOAD ROLE (nếu cần API role riêng)
const getRoles = async () => {
  try {
    const { data } = await axios.get(`${API}/roles`);
    defaultRole.value = data;
  } catch (error) {
    console.error(error);
  }
};

// SUBMIT UPDATE
const handleSubmit = async () => {
  try {
    await axios.put(`${API}/${formData.value.userID}`, formData.value);

    alert("Cập nhật tài khoản thành công");

    emit("saved");
    emit("close");
  } catch (error) {
    console.error(error);
    alert("Có lỗi xảy ra khi cập nhật");
  }
};

// INIT DATA
onMounted(() => {
  getRoles();

  formData.value = {
    userID: props.account.userID,
    fullName: props.account.fullName,
    account: props.account.account,
    numberPhone: props.account.numberPhone,
    email: props.account.email,
    birthDate: props.account.birthDate,
    address: props.account.address,
    roleID: props.account.roleID
  };
});
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  inset: 0;
  background: rgba(0,0,0,0.45);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 9999;
}

.car-detail-container {
  width: 540px;
  max-width: 92%;
  background: #fff;
  border-radius: 16px;
  padding: 28px;
}

/* HEADER */
.form-header {
  display: flex;
  gap: 12px;
  margin-bottom: 24px;
}

.form-header-icon {
  width: 38px;
  height: 38px;
  background: #e6f1fb;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 10px;
}

.form-title {
  font-size: 17px;
  font-weight: 600;
}

.form-subtitle {
  font-size: 13px;
  color: #777;
}

/* FORM */
.form-group {
  display: flex;
  flex-direction: column;
  gap: 6px;
  margin-bottom: 14px;
}

.form-group label {
  font-size: 13px;
  font-weight: 500;
}

.form-group p {
  padding: 9px 12px;
  background: #f5f5f5;
  border-radius: 8px;
}

/* ROW */
.row-2 {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 12px;
}

/* INPUT */
.form-group input {
  padding: 9px 12px;
  border-radius: 8px;
  border: 1px solid #ddd;
}

/* SELECT */
.select-wrap {
  position: relative;
}

.select-wrap select {
  width: 100%;
  padding: 9px 34px 9px 12px;
  border-radius: 8px;
  border: 1px solid #ddd;
  appearance: none;
}

.select-icon {
  position: absolute;
  right: 10px;
  top: 50%;
  transform: translateY(-50%);
}

/* BUTTON */
.button-group {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}

.btn-save {
  background: #185fa5;
  color: #fff;
  padding: 8px 18px;
  border-radius: 8px;
}

.btn-cancel {
  background: #eee;
  padding: 8px 18px;
  border-radius: 8px;
}
</style>