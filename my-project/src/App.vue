<template>
  <div>
    <HeaderCom v-if="showHeader" />

    <main class="content" :class="{ 'full-content': !showHeader }">
      <router-view :key="$route.fullPath" />
    </main>
  </div>
</template>

<script setup>
import HeaderCom from './components/headerComp.vue'
import { computed } from "vue";
import { useRoute } from "vue-router";

const route = useRoute();

const showHeader = computed(() => {
  return route.path !== "/login";
});
</script>

<style>
*{
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

html,
body,
#app{
  width: 100%;
  min-height: 100vh;
  font-family: Arial, Helvetica, sans-serif;
  background: #f5f6fa;
}

.content{
  margin-left: 260px;
  width: calc(100% - 260px);
  min-height: 100vh;
  padding: 25px;
  background: #f5f6fa;
  transition: 0.3s;
}

.full-content{
  margin-left: 0;
  width: 100%;
}

@media (max-width: 768px){
  .content{
    margin-left: 90px;
    width: calc(100% - 90px);
    padding: 15px;
  }

  .full-content{
    margin-left: 0;
    width: 100%;
  }
}
</style>