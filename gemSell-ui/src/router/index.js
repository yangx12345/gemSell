import en from "../i18n/lang/en"
import Vue from "vue"
import Router from "vue-router"
import Login from "@/views/login/index"
import Layout from "@/views/layout/layout"
import NotFound from "@/views/page404.vue"

/**
 * 重写路由的push方法
 */
const routerPush = Router.prototype.push
Router.prototype.push = function push (location) {
  return routerPush.call(this, location).catch(error => error)
}
Vue.use(Router)
let routeName = en.routeName
let defaultRouter = [
  { path: "/",
    redirect: "/user",
    hidden: true,
    children: []
  },
  {
    path: "/login",
    component: Login,
    name: "",
    hidden: true,
    children: []
  },
  {
    path: "/404",
    component: NotFound,
    name: "",
    hidden: true,
    children: []
  },
  {
    path: "/user",
    iconCls: "el-icon-user-solid", // 图标样式class
    name: routeName.user,
    component: Layout,
    alone: true,
    children: [
      {
        path: "/user",
        iconCls: "el-icon-user-solid", // 图标样式class
        name: "用户管理",
        component: () => import("@/views/user/index"),
        children: []
      }
    ]
  },
  {
    path: "/type",
    iconCls: "el-icon-set-up", // 图标样式class
    name: routeName.type,
    component: Layout,
    alone: true,
    children: [
      {
        path: "/type",
        iconCls: "el-icon-set-up", // 图标样式class
        name: "分类管理",
        component:  () => import("@/views/type/index"),
        children: []
      }
    ]
  },
  {
    path: "/goods",
    iconCls: "el-icon-s-goods", // 图标样式class
    name: routeName.goods,
    component: Layout,
    alone: true,
    children: [
      {
        path: "/goods",
        iconCls: "el-icon-s-goods", // 图标样式class
        name: "商品管理",
        component:  () => import("@/views/goods"),
        children: []
      }
    ]
  },
  {
    path: "/order",
    iconCls: "el-icon-s-finance", // 图标样式class
    name: routeName.order,
    component: Layout,
    alone: true,
    children: [
      {
        path: "/order",
        iconCls: "el-icon-s-finance", // 图标样式class
        name: "订单管理",
        component:  () => import("@/views/order"),
        children: []
      }
    ]
  },
  {
    path: "/authenticate",
    iconCls: "el-icon-s-cooperation", // 图标样式class
    name: routeName.authenticate,
    component: Layout,
    alone: true,
    children: [
      {
        path: "/authenticate",
        iconCls: "el-icon-s-cooperation", // 图标样式class
        name: "鉴定管理",
        component:  () => import("@/views/authenticate"),
        children: []
      }
    ]
  },
  {
    path: "/404",
    component: NotFound,
    name: "404",
    hidden: true,
    children: []
  }
]

let addRouter = [
  
  { path: "*",
    redirect: "/404",
    hidden: true,
    children: []
  }

]
export default new Router({
  routes: defaultRouter
})
export {defaultRouter, addRouter}
