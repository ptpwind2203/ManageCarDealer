<template>
  <div class="login-container">
    <div class="grid-bg"></div>
    <div class="glow-top"></div>

    <div class="login-box">
      <div class="logo">
        <div class="logo-mark">
          <svg viewBox="0 0 18 18" fill="none">
            <path d="M2 13L9 5L16 13" stroke="#fff" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"/>
            <path d="M5.5 13L9 8.5L12.5 13" stroke="rgba(255,255,255,0.5)" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"/>
          </svg>
        </div>
        <span class="logo-text">VELOCAR</span>
      </div>

      <p class="tagline">Hệ thống quản lý xe</p>

      <div class="field">
        <label>EMAIL</label>
        <input v-model="email" type="email" placeholder="admin@velocar.vn" />
      </div>

      <div class="field">
        <label>MẬT KHẨU</label>
        <input v-model="password" type="password" placeholder="••••••••" />
      </div>

      <button @click="handleLogin">Đăng nhập</button>

      <hr />
      <p class="footer">Quên mật khẩu? <a href="#">Đặt lại ngay</a></p>
    </div>
  </div>
</template>

<script setup>
import axios from "axios";
import { ref } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();
const email = ref("");
const password = ref("");

const handleLogin = async () => {
  try {
    const response = await axios.post("http://localhost:8080/api/auth/login", {
      email: email.value,
      password: password.value,
    });

    const { token, userID, fullName, account, role, email: userEmail, numberPhone } = response.data;
    localStorage.setItem("token", token);
    localStorage.setItem("userID", userID);
    localStorage.setItem("fullName", fullName);
    localStorage.setItem("account", account);
    localStorage.setItem("role", role);
    localStorage.setItem("email", userEmail);
    localStorage.setItem("numberPhone", numberPhone);

    router.push("/home");
  } catch (error) {
    alert(error.response?.data?.message || "Đăng nhập thất bại");
  }
};
</script>

<style scoped>
.login-container {
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: #0d1117;
  position: relative;
  overflow: hidden;
  font-family: 'Inter', sans-serif;
}

.grid-bg {
  position: absolute;
  inset: 0;
  background-image:
    linear-gradient(rgba(56, 189, 248, 0.04) 1px, transparent 1px),
    linear-gradient(90deg, rgba(56, 189, 248, 0.04) 1px, transparent 1px);
  background-size: 40px 40px;
  pointer-events: none;
}

.glow-top {
  position: absolute;
  top: -80px;
  left: 50%;
  transform: translateX(-50%);
  width: 400px;
  height: 240px;
  background: radial-gradient(ellipse, rgba(56, 139, 253, 0.16) 0%, transparent 70%);
  pointer-events: none;
}

.login-box {
  position: relative;
  z-index: 1;
  width: 360px;
  background: rgba(13, 20, 33, 0.92);
  border: 1px solid rgba(56, 139, 253, 0.22);
  border-radius: 14px;
  padding: 2rem;
  backdrop-filter: blur(8px);
}

.logo {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 4px;
}

.logo-mark {
  width: 32px;
  height: 32px;
  background: linear-gradient(135deg, #388bfd, #1a5fb4);
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.logo-mark svg {
  width: 18px;
  height: 18px;
}

.logo-text {
  font-size: 15px;
  font-weight: 600;
  letter-spacing: 0.12em;
  color: #e6edf3;
}

.tagline {
  font-size: 11px;
  color: #388bfd;
  letter-spacing: 0.08em;
  text-transform: uppercase;
  margin: 0 0 1.5rem;
  font-weight: 500;
}

.field {
  margin-bottom: 1rem;
}

.field label {
  display: block;
  font-size: 10px;
  font-weight: 600;
  letter-spacing: 0.08em;
  color: #7d8590;
  margin-bottom: 6px;
}

.field input {
  width: 100%;
  box-sizing: border-box;
  background: rgba(22, 31, 46, 0.9);
  border: 1px solid rgba(48, 54, 61, 0.9);
  border-radius: 8px;
  padding: 10px 13px;
  color: #e6edf3;
  font-size: 13px;
  font-family: inherit;
  outline: none;
  transition: border-color 0.18s, box-shadow 0.18s;
}

.field input:focus {
  border-color: #388bfd;
  box-shadow: 0 0 0 3px rgba(56, 139, 253, 0.14);
}

.field input::placeholder {
  color: #484f58;
}

button {
  width: 100%;
  padding: 11px;
  margin-top: 0.5rem;
  background: #1f6feb;
  border: none;
  border-radius: 8px;
  color: #fff;
  font-size: 13px;
  font-weight: 600;
  letter-spacing: 0.04em;
  cursor: pointer;
  transition: background 0.16s, transform 0.1s;
  font-family: inherit;
}

button:hover {
  background: #388bfd;
}

button:active {
  transform: scale(0.98);
}

hr {
  border: none;
  border-top: 1px solid rgba(48, 54, 61, 0.7);
  margin: 1.25rem 0 1rem;
}

.footer {
  text-align: center;
  font-size: 11px;
  color: #484f58;
  margin: 0;
}

.footer a {
  color: #388bfd;
  text-decoration: none;
}

.footer a:hover {
  text-decoration: underline;
}
</style>