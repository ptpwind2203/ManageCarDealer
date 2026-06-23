```vue
<template>
  <div class="dashboard">

    <!-- ===== THÔNG TIN NHÂN VIÊN ===== -->
    <div class="profile-section">

      <div class="avatar-card">
        <img
          src="https://via.placeholder.com/120"
          alt="Avatar"
        />
      </div>

      <div class="info-card">
        <h2>Nguyễn Văn A</h2>
        <p>Chức vụ: Quản trị viên</p>
        <p>Email: nguyenvana@gmail.com</p>
        <p>SĐT: 0123456789</p>
      </div>

    </div>

    <!-- ===== THỐNG KÊ ===== -->
    <div class="stats-section">

      <div class="stat-card">
        <h3>Tổng số xe</h3>
        <span>120</span>
      </div>

      <div class="stat-card">
        <h3>Tổng đơn hàng</h3>
        <span>560</span>
      </div>

      <div class="stat-card">
        <h3>Tổng khách hàng</h3>
        <span>320</span>
      </div>

    </div>

    <!-- ===== BẢNG XẾP HẠNG ===== -->
    <div class="ranking-section">

      <!-- Top xe -->
      <div class="ranking-card">
        <h3>Top 5 xe bán chạy</h3>

        <table>
          <thead>
            <tr>
              <th>STT</th>
              <th>Tên xe</th>
              <th>SL</th>
            </tr>
          </thead>

          <tbody>
            <tr v-for="i in 5" :key="i">
              <td>{{ i }}</td>
              <td>Honda Vision</td>
              <td>100</td>
            </tr>
          </tbody>
        </table>
      </div>

      <!-- Top hãng -->
      <div class="ranking-card">
        <h3>Top 5 hãng xe bán chạy</h3>

        <table>
          <thead>
            <tr>
              <th>STT</th>
              <th>Hãng xe</th>
              <th>SL</th>
            </tr>
          </thead>

          <tbody>
            <tr v-for="i in 5" :key="i">
              <td>{{ i }}</td>
              <td>Honda</td>
              <td>100</td>
            </tr>
          </tbody>
        </table>
      </div>

      <!-- Top khách hàng -->
      <div class="ranking-card">
        <h3>Top 5 khách hàng mua nhiều nhất</h3>

        <table>
          <thead>
            <tr>
              <th>STT</th>
              <th>Khách hàng</th>
              <th>SL</th>
            </tr>
          </thead>

          <tbody>
            <tr v-for="i in 5" :key="i">
              <td>{{ i }}</td>
              <td>Nguyễn Văn A</td>
              <td>10</td>
            </tr>
          </tbody>
        </table>
      </div>

    </div>

  </div>
</template>

<script setup>
</script>

<style scoped>

*{
  margin:0;
  padding:0;
  box-sizing:border-box;
}

.dashboard{
  min-height:100vh;
  background:#f5f7fa;
  padding:24px;
  font-family:Inter,sans-serif;
}

/* ==========================
   PROFILE
========================== */

.profile-section{
  display:flex;
  gap:20px;
  margin-bottom:24px;
}

.avatar-card{
  width:120px;
  height:120px;
  background:white;
  border-radius:24px;
  box-shadow:0 2px 10px rgba(0,0,0,.08);
  overflow:hidden;
  flex-shrink:0;
}

.avatar-card img{
  width:100%;
  height:100%;
  object-fit:cover;
}

.info-card{
  flex:1;
  background:white;
  border-radius:24px;
  padding:24px;
  box-shadow:0 2px 10px rgba(0,0,0,.08);
}

.info-card h2{
  margin-bottom:12px;
}

.info-card p{
  margin-bottom:8px;
  color:#555;
}

/* ==========================
   STATS
========================== */

.stats-section{
  display:grid;
  grid-template-columns:repeat(3,1fr);
  gap:20px;
  margin-bottom:24px;
}

.stat-card{
  background:white;
  border-radius:20px;
  padding:24px;
  text-align:center;
  box-shadow:0 2px 10px rgba(0,0,0,.08);
}

.stat-card h3{
  margin-bottom:16px;
  color:#666;
}

.stat-card span{
  font-size:40px;
  font-weight:700;
  color:#1976d2;
}

/* ==========================
   RANKING
========================== */

.ranking-section{
  display:grid;
  grid-template-columns:repeat(3,1fr);
  gap:20px;
}

.ranking-card{
  background:white;
  border-radius:24px;
  padding:20px;
  box-shadow:0 2px 10px rgba(0,0,0,.08);
}

.ranking-card h3{
  margin-bottom:16px;
}

table{
  width:100%;
  border-collapse:collapse;
}

thead{
  background:#f2f4f7;
}

th,
td{
  padding:10px;
  text-align:left;
  border-bottom:1px solid #eee;
}

th{
  font-weight:600;
}

/* ==========================
   RESPONSIVE
========================== */

@media(max-width:992px){

  .stats-section{
    grid-template-columns:1fr;
  }

  .ranking-section{
    grid-template-columns:1fr;
  }

}

@media(max-width:768px){

  .profile-section{
    flex-direction:column;
  }

  .avatar-card{
    width:120px;
    margin:auto;
  }

}
</style>
