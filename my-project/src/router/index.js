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
  }

]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router