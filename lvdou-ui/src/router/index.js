import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

// 路由表
const routes = [
  {
    path: '/',
    redirect: {
        name: 'login'
    }
},
  {
    path: '/',
    component: () => import('@/views/layout/'),
    children: [
      {
        path: '/login', // 默认子路由
        name: 'login',
        component: () => import('@/views/login/')
      },
      {
        path: '/account', // 默认子路由
        name: 'account',
        component: () => import('@/views/account/')
      },
      {
        path: '/transer',
        name: 'transer',
        component: () => import('@/views/transer/')
      },
      {
        path: '/merchant',
        name: 'merchant',
        component: () => import('@/views/merchant/')
      },
      // 订单组开始
      {
        path: '/order',
        name: 'order',
        component: () => import('@/views/order/')
      },
      // 订单组结束
    ]
  }
]

const router = new VueRouter({
  routes
})

export default router
