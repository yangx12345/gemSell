import Vue from 'vue'
//导入路由
import Router from 'vue-router'
//注册VueRouter
Vue.use(Router);
//导入单页面组件
import Index from '../components/index';
//导入详情页面
import Detail from '../components/detail'

import Cart from '../components/shoppingCart'

import Login from '../components/login'

import Order from '../components/order'

import Pay from '../components/pay'

import paySuccess from '../components/paySuccess'

import OrderList from '../components/orderList'

import OrderDetail from '../components/orderDetail'

import GoodsList from '../components/goodsList'

import changeMessage from '../components/changeMessage'

import userInfo from '../components/userInfo'

import axios from 'axios';
//基础地址
//让ajax携带cookie
// 跨域请求时 是否会携带 凭证(cookie)
axios.defaults.withCredentials = true;
//Vue原型对象存储axios
Vue.prototype.$axios = axios;

//定义路由规则
let routes = [{
    //首页
    path: '/',
    // component: Index,
    redirect: '/index'
  },
  {
    //首页
    path: '/index',
    component: Index
  },
  {
    //商品列表
    path: '/goodslist',
    component: GoodsList
  },
  {
    //详情页
    path: '/detail',
    component: Detail
  },
  {
    //详情页
    path: '/userInfo',
    component: userInfo
  },
  {
    //购物车
    path: '/cart',
    component: Cart
  },
  {
    //登录
    path: '/login/:selected',
    component: Login
  },
  {
    //订单
    path: '/order/:ids',
    component: Order,
    meta: {
      checkLogin: true
    }
  },
  {
    //订单详情
    path: '/pay/:id',
    component: Pay,
    meta: {
      checkLogin: true
    }
  },
  {
    //订单成功
    path: '/paySuccess',
    component: paySuccess,
    meta: {
      checkLogin: true
    }
  },
  {
    //订单列表
    path: '/orderlist',
    component: OrderList,
    meta: {
      checkLogin: true
    }
  },
  {
    //订单列表
    path: '/changeMessage',
    component: changeMessage,
    meta: {
      checkLogin: true
    }
  },
  {
    //订单详情
    path: '/orderDetail/:id',
    component: OrderDetail,
    meta: {
      checkLogin: true
    }
  },
]

//实例化路由对象
let router = new Router({
  routes: routes
})

// 增加 导航守卫(路由守卫),判断是否是登录状态,不是打回到登录
router.beforeEach((to, from, next) => {
  if (to.meta.checkLogin == true) {
    axios.get("site/account/islogin")
      .then(response => {
        if (response.data.code != 'nologin') {
          next();
        } else {
          next('/login');
        }
      })
  } else {
    next();
  }
})
export default new Router({
  routes,
})
