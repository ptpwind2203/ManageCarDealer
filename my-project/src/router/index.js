import { createRouter, createWebHistory } from 'vue-router'
import HomeComp from '../components/homeComp.vue'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: HomeComp,
    alias: '/home'
  },
  {
    path: '/cars',
    name: 'Cars',
    component: () => import('../components/carsManageComp.vue')
  },
  {
    path: '/cars/:id',
    name: 'CarDetails',
    component: () => import('../components/carDetail.vue'),
    props: true
  },
  {
    path: '/accounts',
    name: 'Accounts',
    component: () => import('../components/accountManageComp.vue')
  },
  {
    path: '/accounts/:id',
    name: 'AccountDetails',
    component: () => import('../components/accountDetail.vue'),
    props: true
  },
  // Ví dụ cấu hình đúng trong router
  {
    path: '/orders',
    name: 'orders',
    component: () => import('../components/OrderManagement.vue') // Đảm bảo đúng đường dẫn file và tên component
  },
  {
    path: "/orders/create",
    name: "CreateOrder",
    component: () => import("../components/CreateOrder.vue")
  },
  {
    path: "/login",
    name: "Login",
    component: () => import("../components/LoginForm.vue")
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

// Route Guard
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem("token");

  if (to.path !== "/login" && !token) {
    next("/login");
  } else {
    next();
  }
});

export default router;