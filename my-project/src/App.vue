<template>
  <div>
    <HeaderCom />


    <main class="content">
      <router-view :key="$route.fullPath" />
    </main>

  </div>
</template>

<script setup>
import HeaderCom from './components/headerComp.vue'



</script>

<style>
*{
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

/* FULL HEIGHT */
html,
body,
#app{
  width: 100%;
  min-height: 100vh;

  font-family: Arial, Helvetica, sans-serif;

  background: #f5f6fa;
}

/* LAYOUT */
.app{
  display: flex;
  min-height: 100vh;
}

/* CONTENT */
.content{

  /* chừa chỗ cho sidebar */
  margin-left: 250px;

  width: calc(100% - 250px);

  min-height: 100vh;

  padding: 25px;

  background: #f5f6fa;

  transition: 0.3s;
}

/* RESPONSIVE */
@media (max-width: 768px){

  .content{
    margin-left: 80px;
    width: calc(100% - 80px);
    padding: 15px;
  }

}
</style>