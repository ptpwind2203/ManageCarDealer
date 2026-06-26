<template>
  <div class="modal-overlay" @click.self="handleCancel">
    <div class="change-password-container">
      <div class="form-header">
        <h2>Thay đổi mật khẩu</h2>
        <p>Vui lòng nhập mật khẩu hiện tại và mật khẩu mới để bảo mật tài khoản.</p>
      </div>

      <form @submit.prevent="handleSubmit" class="password-form">
        <!-- Mật khẩu hiện tại -->
        <div class="form-group">
          <label for="currentPassword">
            Mật khẩu hiện tại <span class="required">*</span>
          </label>

          <div class="input-wrapper">
            <input
              :type="showCurrentPassword ? 'text' : 'password'"
              id="currentPassword"
              v-model="form.passwordOld"
              placeholder="Nhập mật khẩu hiện tại"
              :class="{ 'input-error': errors.passwordOld }"
              @input="clearError('passwordOld')"
            />

            <button
              type="button"
              class="toggle-password"
              @click="showCurrentPassword = !showCurrentPassword"
            >
              <span v-if="showCurrentPassword">👁️‍🗨️</span>
              <span v-else>👁️</span>
            </button>
          </div>

          <p v-if="errors.passwordOld" class="error-text">
            {{ errors.passwordOld }}
          </p>
        </div>

        <!-- Mật khẩu mới -->
        <div class="form-group">
          <label for="newPassword">
            Mật khẩu mới <span class="required">*</span>
          </label>

          <div class="input-wrapper">
            <input
              :type="showNewPassword ? 'text' : 'password'"
              id="newPassword"
              v-model="form.passwordNew"
              placeholder="Nhập mật khẩu mới (tối thiểu 6 ký tự)"
              :class="{ 'input-error': errors.passwordNew }"
              @input="clearError('passwordNew')"
            />

            <button
              type="button"
              class="toggle-password"
              @click="showNewPassword = !showNewPassword"
            >
              <span v-if="showNewPassword">👁️‍🗨️</span>
              <span v-else>👁️</span>
            </button>
          </div>

          <p v-if="errors.passwordNew" class="error-text">
            {{ errors.passwordNew }}
          </p>
        </div>

        <!-- Xác nhận mật khẩu -->
        <div class="form-group">
          <label for="confirmPassword">
            Xác nhận mật khẩu mới <span class="required">*</span>
          </label>

          <div class="input-wrapper">
            <input
              :type="showConfirmPassword ? 'text' : 'password'"
              id="confirmPassword"
              v-model="form.confirmPasswordNew"
              placeholder="Nhập lại mật khẩu mới"
              :class="{ 'input-error': errors.confirmPasswordNew }"
              @input="clearError('confirmPasswordNew')"
            />

            <button
              type="button"
              class="toggle-password"
              @click="showConfirmPassword = !showConfirmPassword"
            >
              <span v-if="showConfirmPassword">👁️‍🗨️</span>
              <span v-else>👁️</span>
            </button>
          </div>

          <p v-if="errors.confirmPasswordNew" class="error-text">
            {{ errors.confirmPasswordNew }}
          </p>
        </div>

        <!-- Thông báo -->
        <div
          v-if="statusMessage"
          :class="['status-message', statusType]"
        >
          {{ statusMessage }}
        </div>

        <!-- Button -->
        <div class="form-actions">
          <button
            type="button"
            class="btn-cancel"
            @click="handleCancel"
            :disabled="isLoading"
          >
            Hủy bỏ
          </button>

          <button
            type="submit"
            class="btn-submit"
            :disabled="isLoading"
          >
            <span
              v-if="isLoading"
              class="spinner"
            ></span>

            {{ isLoading ? "Đang cập nhật..." : "Cập nhật mật khẩu" }}
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref, defineProps, defineEmits } from "vue";
import axios from "axios";

const API = "http://localhost:8080/api/accounts";

const props = defineProps({
  userID: {
    type: Number,
    required: true,
  },
});

const emit = defineEmits(["submit-success", "cancel"]);

const form = reactive({
  passwordOld: "",
  passwordNew: "",
  confirmPasswordNew: "",
});

const errors = reactive({
  passwordOld: "",
  passwordNew: "",
  confirmPasswordNew: "",
});

const showCurrentPassword = ref(false);
const showNewPassword = ref(false);
const showConfirmPassword = ref(false);

const isLoading = ref(false);
const statusMessage = ref("");
const statusType = ref("");

const clearError = (field) => {
  errors[field] = "";
  statusMessage.value = "";
};

const validateForm = () => {
  let valid = true;

  Object.keys(errors).forEach((key) => (errors[key] = ""));

  if (!form.passwordOld) {
    errors.passwordOld = "Vui lòng nhập mật khẩu hiện tại.";
    valid = false;
  }

  if (!form.passwordNew) {
    errors.passwordNew = "Vui lòng nhập mật khẩu mới.";
    valid = false;
  } else if (form.passwordNew.length < 6) {
    errors.passwordNew = "Mật khẩu phải có ít nhất 6 ký tự.";
    valid = false;
  } else if (form.passwordNew === form.passwordOld) {
    errors.passwordNew = "Mật khẩu mới không được trùng mật khẩu cũ.";
    valid = false;
  }

  if (!form.confirmPasswordNew) {
    errors.confirmPasswordNew = "Vui lòng xác nhận mật khẩu.";
    valid = false;
  } else if (form.confirmPasswordNew !== form.passwordNew) {
    errors.confirmPasswordNew = "Mật khẩu xác nhận không khớp.";
    valid = false;
  }

  return valid;
};

const resetForm = () => {
  form.passwordOld = "";
  form.passwordNew = "";
  form.confirmPasswordNew = "";

  Object.keys(errors).forEach((key) => (errors[key] = ""));

  statusMessage.value = "";
};

const handleSubmit = async () => {
  if (!validateForm()) return;

  isLoading.value = true;
  statusMessage.value = "";

  try {

    const response = await axios.put(
      `${API}/${props.userID}/change-password`,
      form
    );

    statusType.value = "success";
    statusMessage.value = response.data;

    setTimeout(() => {
        emit("submit-success");
        resetForm();
        emit("cancel");
    }, 2000);

  } catch (error) {

    statusType.value = "error";

    if (error.response) {
      statusMessage.value = error.response.data;
    } else {
      statusMessage.value = "Có lỗi xảy ra.";
    }

  } finally {
    isLoading.value = false;
  }
};

const handleCancel = () => {
  resetForm();
  emit("cancel");
};
</script>

<style scoped>
/* ================= Overlay ================= */

.modal-overlay {
  position: fixed;
  inset: 0;
  background: rgba(0, 0, 0, 0.45);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 99999;
  backdrop-filter: blur(3px);
  animation: fadeIn .25s ease;
}

/* ================= Container ================= */

.change-password-container {
  width: 90%;
  max-width: 480px;
  background: #fff;
  border-radius: 12px;
  padding: 30px;
  box-shadow: 0 15px 40px rgba(0, 0, 0, .25);
  animation: popup .3s ease;
}

/* ================= Header ================= */

.form-header {
  margin-bottom: 24px;
}

.form-header h2 {
  margin: 0 0 8px;
  color: #1e293b;
}

.form-header p {
  margin: 0;
  color: #64748b;
  font-size: 14px;
}

/* ================= Form ================= */

.password-form {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.form-group {
  display: flex;
  flex-direction: column;
  gap: 6px;
}

label {
  font-weight: 500;
  color: #334155;
}

.required {
  color: red;
}

/* ================= Input ================= */

.input-wrapper {
  position: relative;
}

.input-wrapper input {
  width: 100%;
  padding: 12px 45px 12px 14px;
  border: 1px solid #cbd5e1;
  border-radius: 8px;
  outline: none;
  transition: .2s;
  box-sizing: border-box;
}

.input-wrapper input:focus {
  border-color: #4f46e5;
  box-shadow: 0 0 0 3px rgba(79,70,229,.15);
}

.input-error {
  border-color: #ef4444 !important;
}

.toggle-password {
  position: absolute;
  right: 12px;
  top: 50%;
  transform: translateY(-50%);
  border: none;
  background: transparent;
  cursor: pointer;
}

.error-text {
  color: red;
  font-size: 12px;
}

/* ================= Status ================= */

.status-message {
  padding: 12px;
  border-radius: 8px;
  text-align: center;
}

.success {
  background: #ecfdf5;
  color: #15803d;
}

.error {
  background: #fef2f2;
  color: #b91c1c;
}

/* ================= Buttons ================= */

.form-actions {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}

.btn-cancel,
.btn-submit {
  padding: 11px 20px;
  border-radius: 8px;
  cursor: pointer;
  font-size: 14px;
}

.btn-cancel {
  border: 1px solid #d1d5db;
  background: #f8fafc;
}

.btn-cancel:hover {
  background: #e2e8f0;
}

.btn-submit {
  border: none;
  background: #4f46e5;
  color: white;
}

.btn-submit:hover {
  background: #4338ca;
}

.btn-submit:disabled,
.btn-cancel:disabled {
  opacity: .6;
  cursor: not-allowed;
}

/* ================= Spinner ================= */

.spinner {
  width: 16px;
  height: 16px;
  border: 2px solid rgba(255,255,255,.3);
  border-top-color: #fff;
  border-radius: 50%;
  display: inline-block;
  animation: spin .8s linear infinite;
}

/* ================= Animation ================= */

@keyframes spin {
  to {
    transform: rotate(360deg);
  }
}

@keyframes popup {
  from {
    opacity: 0;
    transform: translateY(-20px) scale(.9);
  }
  to {
    opacity: 1;
    transform: translateY(0) scale(1);
  }
}

@keyframes fadeIn {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}
</style>