<template>
  <header class="header">

    <!-- LOGO -->
    <div class="logo">

      <router-link to="/" class="logo-link">

        <img
          src="../assets/logoCarDealer.png"
          alt="Logo"
        />

        <span>Car Dealer Manage </span>

      </router-link>

    </div>

    <!-- MENU -->
    <nav class="navbar">

      <ul>

        <li>
          <router-link to="/cars">
            Xe
          </router-link>
        </li>

        <li>
          <router-link to="/accounts">
            Khách hàng
          </router-link>
        </li>

        <li>
          <router-link to="/orders">
            Đơn hàng
          </router-link>
        </li>

        <li>
          <router-link to="/notifications">
            Thông báo
          </router-link>
        </li>

      </ul>

    </nav>

    <!-- USER -->
    <div class="auth">
      <h1 class="user-info">
        <span>Xin chào, Admin</span>
      </h1>
      

    </div>

  </header>
</template>

<script setup>

</script>

<style scoped>

/* RESET */
*{
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

/* SIDEBAR */
.header{
  width: 260px;
  height: 100vh;

  background: #ffffff;

  display: flex;
  flex-direction: column;

  justify-content: space-between;
  align-items: center;

  padding: 30px 20px;

  position: fixed;
  top: 0;
  left: 0;

  box-shadow: 2px 0 15px rgba(0,0,0,0.08);

  z-index: 1000;
}

/* LOGO */
.logo{
  width: 100%;

  display: flex;
  justify-content: center;

  margin-bottom: 40px;
}

/* LOGO LINK */
.logo-link{
  display: flex;
  flex-direction: column;

  align-items: center;

  text-decoration: none;

  gap: 15px;
}

/* LOGO IMAGE */
.logo img{
  width: 100px;
  height: 100px;

  object-fit: contain;

  transition: 0.3s;
}

.logo img:hover{
  transform: scale(1.05);
}

/* LOGO TEXT */
.logo span{
  font-size: 20px;
  font-weight: 700;

  color: #d70018;

  letter-spacing: 1px;
}

/* NAVBAR */
.navbar{
  width: 100%;
  flex: 1;
}

/* MENU LIST */
.navbar ul{
  list-style: none;

  display: flex;
  flex-direction: column;

  gap: 14px;
}

/* MENU ITEM */
.navbar li{
  width: 100%;
}

/* MENU LINK */
.navbar a{
  display: block;

  width: 100%;

  padding: 14px 18px;

  text-decoration: none;

  border-radius: 12px;

  color: #333;

  font-size: 16px;
  font-weight: 600;

  transition: all 0.3s ease;
}

/* HOVER */
.navbar a:hover{
  background: #f5f5f5;

  color: #d70018;

  transform: translateX(5px);
}


/* AUTH */
.auth{
  width: 100%;

  margin-top: 30px;
}

/* BUTTON GROUP */
.auth-buttons{
  display: flex;
  flex-direction: column;

  gap: 12px;
}

/* BUTTON */
.login-btn,
.register-btn{
  width: 100%;

  padding: 13px;

  border: none;
  border-radius: 12px;

  cursor: pointer;

  font-size: 14px;
  font-weight: 600;

  transition: 0.3s;
}

/* LOGIN */
.login-btn{
  background: transparent;

  border: 1px solid #d70018;

  color: #d70018;
}

.login-btn:hover{
  background: #d70018;
  color: white;
}

/* REGISTER */
.register-btn{
  background: #d70018;
  color: white;
}

.register-btn:hover{
  opacity: 0.9;
}

/* USER INFO */
.user-info{
  display: flex;
  align-items: center;

  gap: 12px;

  width: 100%;

  padding: 14px;

  border-radius: 12px;

  background: #f8f8f8;

  cursor: pointer;

  transition: 0.3s;
}

.user-info:hover{
  background: #efefef;
}


/* USERNAME */
.user-info span{
  font-size: 15px;
  font-weight: 600;

  color: #333;
}

/* RESPONSIVE */
@media (max-width: 768px){

  .header{
    width: 90px;

    padding: 20px 10px;
  }

  .logo img{
    width: 55px;
    height: 55px;
  }

  .logo span{
    display: none;
  }

  .navbar a{
    text-align: center;

    padding: 12px 5px;

    font-size: 13px;
  }

  .auth{
    width: 100%;
  }

  .login-btn,
  .register-btn{
    font-size: 11px;

    padding: 10px;
  }

  .user-info{
    justify-content: center;
  }

  .user-info span{
    display: none;
  }

}

</style>