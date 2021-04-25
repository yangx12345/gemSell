import Vue from 'vue'
import Router from 'vue-router'
import User from '@/views/user/index'
import Type from '@/views/type/index'
import Goods from '@/views/goods/index'
import Order from '@/views/order/index'
import Authenticate from '@/views/authenticate/index'
import MyAuthenticate from '@/views/myAuthenticate/index'
Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },
  {
    path: '/',
    component: Layout,
    redirect: '/user',
    children: [
      {
        path: 'user',
        name: '用户管理',
        component: User,
        meta: { title: '用户管理', icon: 'el-icon-user-solid' }
      }
    ]
  },
  {
    path: '/type',
    component: Layout,
    redirect: '/type/type',
    children: [
      {
        path: 'type',
        component: Type,
        meta: { title: '分类管理', icon: 'el-icon-set-up' }
      }
    ]
  },
  {
    path: '/goods',
    component: Layout,
    redirect: '/goods/goods',
    children: [
      {
        path: 'goods',
        name: '商品管理',
        component: Goods,
        meta: { title: '商品管理', icon: 'el-icon-s-goods' }
      }
    ]
  },
  {
    path: '/order',
    component: Layout,
    redirect: '/order/order',
    children: [
      {
        path: 'order',
        name: '订单管理',
        component: Order,
        meta: { title: '订单管理', icon: 'el-icon-s-finance' }
      }
    ]
  },
  {
    path: '/authenticate',
    component: Layout,
    redirect: '/authenticate/authenticate',
    children: [
      {
        path: 'authenticate',
        name: '鉴定管理',
        component: Authenticate,
        meta: { title: '鉴定管理', icon: 'el-icon-s-cooperation' }
      }
    ]
  },
  {
    path: '/myAuthenticate',
    component: Layout,
    redirect: '/myAuthenticate/myAuthenticate',
    children: [
      {
        path: 'myAuthenticate',
        name: '鉴定管理',
        component: MyAuthenticate,
        meta: { title: '我的鉴定', icon: 'el-icon-s-cooperation' }
      }
    ]
  },
  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  mode: 'history',
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
