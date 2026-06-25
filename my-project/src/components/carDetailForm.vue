<template>
  <div class="modal-overlay">
    <div class="car-detail-container">
      <form class="car-detail-form" @submit.prevent="handleSubmit">

        <div class="form-header">
          <div class="form-header-icon">🚗</div>
          <div>
            <p class="form-title">Thêm chi tiết xe</p>
            <p class="form-subtitle">Điền đầy đủ thông tin bên dưới</p>
          </div>
        </div>

        <div class="form-group">
          <label>Tên xe</label>
          <p>{{ props.car.carName }}</p>
        </div>

        <div class="row-2">
  <!-- Màu sắc -->
  <div class="form-group">
    <label>Màu sắc</label>

    <div class="select-wrap">
      <select v-model="formData.colorID" :disabled="props.carDetail" required>
        <option value="" disabled>Chọn màu sắc</option>

        <option
          v-for="color in defaultColor"
          :key="color.colorID"
          :value="color.colorID"
        >
          {{ color.colorName }}
        </option>
      </select>

      <span class="select-icon">▾</span>
    </div>
  </div>

  <!-- Phiên bản -->
  <div class="form-group">
    <label>Phiên bản</label>

    <div class="select-wrap">
      <select v-model="formData.VersionID" :disabled="props.carDetail" required>
        <option value="" disabled>Chọn phiên bản</option>

        <option
          v-for="version in defaultVersion"
          :key="version.VersionID"
          :value="version.VersionID"
        >
          {{ version.VersionName }}
        </option>
      </select>

      <span class="select-icon">▾</span>
    </div>
  </div>
</div>

        <div class="row-2">
          <div class="form-group">
            <label>Số lượng</label>
            <input type="number" v-model="formData.quantity" placeholder="0" min="0" />
          </div>
          <div class="form-group">
            <label>Giá bán (VNĐ)</label>
            <input type="number" v-model="formData.price" placeholder="0" min="0" />
          </div>
        </div>

        <hr class="form-divider" />

        <div class="button-group">
            <button type="button" class="btn-cancel" @click="emit('close')">Hủy</button>
            <button type="submit" class="btn-save">
            {{ props.carDetail ? 'Lưu thay đổi' : 'Thêm chi tiết' }}
            </button>
        </div>

      </form>
    </div>
  </div>
</template>

<script setup>
import { defineEmits } from 'vue'
import axios from 'axios';
import { ref, onMounted } from 'vue';
import { defineProps } from 'vue';


const API = "http://localhost:8080/api/car-details";
const emit = defineEmits(['close', 'saved']);
const defaultColor = ref([]);
const defaultVersion = ref([]);

const props = defineProps({
    car: {
        type: Object,
        required: true
    },
    carDetail: {
        type: Object,
        default: null
    }
});

//lấy dữ liệu màu ra để hiển thị trong form
const getColors = async () => {
    try {
        const { data } = await axios.get(`${API}/colors`);
        defaultColor.value = data;

        console.log("Màu sắc:", data);
    } catch (error) {
        console.error("Lỗi khi lấy dữ liệu màu:", error);
        defaultColor.value = [];
    }
};

//lấy dữ liệu phiên bản ra để hiển thị trong form
const getVersions = async () => {
    try {
        const { data } = await axios.get(`${API}/versions`);
        defaultVersion.value = data;

        console.log("Phiên bản:", data);
    } catch (error) {
        console.error("Lỗi khi lấy dữ liệu phiên bản:", error);
        defaultVersion.value = [];
    }
};




const formData = ref({
    carID: props.car.carID,
    colorID: "",
    VersionID: "",
    quantity: 0,
    price: 0
});

const handleSubmit = async () => {
    try {
        if (props.carDetail) {
          console.log("Dữ liệu chuẩn bị gửi:");

    console.log(formData.value);
            await axios.put(
                `${API}/update`,
                formData.value
            );
            alert("Cập nhật thành công");
        } else {
          console.log("Dữ liệu chuẩn bị gửi:");

    console.log(formData.value);
            await axios.post(
                `${API}/add`,
                formData.value
            );
            alert("Thêm thành công");
        }
        emit("saved");
        emit("close");
    } catch (error) {
        console.error(error);
        alert(
            error.response.data.message ||
            "Có lỗi xảy ra"
        );
    }
};

onMounted(() => {
     getColors();
     getVersions();
     if (props.carDetail) {
        formData.value.carID = props.car.carID;
        formData.value.colorID = props.carDetail.colorID;
        formData.value.VersionID = props.carDetail.versionID;
        formData.value.quantity = props.carDetail.quantity;
        formData.value.price = props.carDetail.price;

        console.log("FormData edit:", formData.value);
    }
});
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  inset: 0;
  background: rgba(0, 0, 0, 0.45);
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
  border: 0.5px solid rgba(0, 0, 0, 0.1);
  padding: 28px 28px 20px;
}

/* Header */
.form-header {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 24px;
}

.form-header-icon {
  width: 38px;
  height: 38px;
  border-radius: 10px;
  background: #e6f1fb;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 20px;
  color: #185fa5;
  flex-shrink: 0;
}

.form-title {
  font-size: 17px;
  font-weight: 600;
  color: #111;
  margin: 0;
}

.form-subtitle {
  font-size: 13px;
  color: #777;
  margin: 0;
}

/* Form layout */
.car-detail-form {
  display: flex;
  flex-direction: column;
  gap: 0;
}

.row-2 {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 12px;
}

/* Groups */
.form-group {
  display: flex;
  flex-direction: column;
  gap: 5px;
  margin-bottom: 14px;
}

.form-group label {
  font-size: 13px;
  font-weight: 500;
  color: #555;
}

/* Static text (tên xe) */
.form-group p {
  font-size: 15px;
  font-weight: 500;
  color: #111;
  padding: 9px 12px;
  background: #f5f5f5;
  border-radius: 8px;
  border: 0.5px solid rgba(0, 0, 0, 0.1);
  margin: 0;
}

/* Select */
.select-wrap {
  position: relative;
}

.select-wrap select {
  width: 100%;
  font-size: 14px;
  color: #111;
  padding: 9px 34px 9px 12px;
  border-radius: 8px;
  border: 0.5px solid rgba(0, 0, 0, 0.2);
  background: #fff;
  outline: none;
  appearance: none;
  transition: border-color 0.15s, box-shadow 0.15s;
  cursor: pointer;
}

.select-wrap select:focus {
  border-color: #185fa5;
  box-shadow: 0 0 0 3px rgba(24, 95, 165, 0.12);
}

.select-icon {
  position: absolute;
  right: 10px;
  top: 50%;
  transform: translateY(-50%);
  color: #888;
  pointer-events: none;
  font-size: 15px;
}

/* Input */
.form-group input {
  font-size: 14px;
  color: #111;
  padding: 9px 12px;
  border-radius: 8px;
  border: 0.5px solid rgba(0, 0, 0, 0.2);
  background: #fff;
  outline: none;
  transition: border-color 0.15s, box-shadow 0.15s;
}

.form-group input:focus {
  border-color: #185fa5;
  box-shadow: 0 0 0 3px rgba(24, 95, 165, 0.12);
}

/* Divider */
.form-divider {
  border: none;
  border-top: 0.5px solid rgba(0, 0, 0, 0.08);
  margin: 8px 0 16px;
}

/* Buttons */
.button-group {
  display: flex;
  justify-content: flex-end;
  gap: 8px;
}

.btn-save,
.btn-cancel {
  font-size: 14px;
  font-weight: 500;
  padding: 8px 20px;
  border-radius: 8px;
  border: 0.5px solid rgba(0, 0, 0, 0.15);
  cursor: pointer;
  transition: background 0.15s, transform 0.1s;
}

.btn-save {
  background: #185fa5;
  color: #fff;
  border-color: #185fa5;
}

.btn-save:hover { background: #0c447c; border-color: #0c447c; }
.btn-save:active { transform: scale(0.98); }

.btn-cancel {
  background: transparent;
  color: #333;
}

.btn-cancel:hover { background: #f0f0f0; }
.btn-cancel:active { transform: scale(0.98); }
</style>