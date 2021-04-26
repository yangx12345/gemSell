<template>
    <div id="app">
        <!-- 头部 -->
        <div class="header">
            <!-- 1.0 导航栏头部 -->
            <div class="head-top">
                <div class="section">
                    <div class="left-box">
                        <span>在线宝石商城</span>
                        <a target="_blank" href="#"></a>
                        <a target="_blank" href="#"></a>
                    </div>
                    <div id="menu" class="right-box">
                        <span v-show="$store.state.currentUser.userId === undefined">
                           <router-link to="/login/login">登录</router-link>
                            <strong>|</strong>
                            <router-link to="/login/register">注册</router-link>
                            <strong>|</strong>
                        </span>
                        <span v-show="$store.state.currentUser.userId">
                            <router-link to="/orderlist">我的</router-link>
                            <strong>|</strong>
                            <a @click="layout">退出</a>
                        </span>
                        <router-link to="/cart">
                            <i class="iconfont icon-cart"></i>购物车 (
                            <span id="shoppingCartCount">
                            <span>{{$store.getters.goodsCount}}</span>
                            </span>)
                        </router-link>
                    </div>
                </div>
            </div>
            <!-- 2.0 导航条 -->
            <div class="head-nav">
                <div class="section">
                    <div id="menu2" class="nav-box menuhd">
                        <ul>
                            <li class="index">
                                <router-link to="/index">
                                    <span class="out" style="top: 0px;">首页</span>
                                </router-link>
                            </li>
                            <li class="goods">
                                 <router-link to="/goodslist">
                                    <span class="out" style="top: 0px;">购物商城</span>
                                 </router-link>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <!-- 路由导出试图 -->
        <router-view></router-view>
        <!-- 底部 -->
        <div class="footer">
            <div class="section">
                <div class="foot-nav">
                    <a href="">关于我们</a>
                    <strong>|</strong>
                    <a href="">联系我们</a>
                    <strong>|</strong>
                    <a href="">联系客服</a>
                    <strong>|</strong>
                    <a href="">合作招商</a>
                    <strong>|</strong>
                    <a href="">商家帮助</a>
                    <strong>|</strong>
                    <a href="">营销中心</a>
                    <strong>|</strong>
                    <a href="">隐私政策</a>
                </div>
                <div class="foot-box">
                    <div class="copyright">
                        <p>版权所有 宝石商城 </p>
                        <p>公司地址：xxx 联系电话：xxx</p>
                        <p class="gray">Copyright © 2021 myshop Corporation,All Rights Reserved.</p>
                    </div>
                    <div class="service">
                        <p>周一至周日 9:00-24:00</p>
                        <a href="#">
                            <i class="iconfont icon-phone"></i>在线客服</a>
                    </div>
                </div>
            </div>
        </div>
        <Modal v-model="isShow" @on-ok="userExit">
            <p>部分功能需要登录后才能操作,请确定退出!</p>
        </Modal>
    </div>
</template>
<script>
export default {
    name: "app",
    data: function() {
        return {
            isShow: false
        }
    },
    mounted(){
        if (sessionStorage.getItem("currentUser") ) {
            this.$store.commit('setCurrentUser',JSON.parse(sessionStorage.getItem("currentUser")))
        }
    },
    methods: {
        async layout() {
            await this.$store.dispatch('logout')
            this.$message.success('退出成功！')
            this.$router.push('/')
        },
        userExit() {
            this.$axios.get('/site/account/logout').then(response => {
               if (response.data.status == 0) {
                 this.$router.push('/index');
               }
            })
        }
    }
};
import $ from "jquery";
$(document).ready(function() {
    $("#menu2 li a").wrapInner('<span class="out"></span>');
    $("#menu2 li a").each(function() {
        $('<span class="over">' + $(this).text() + "</span>").appendTo(this);
    });
    $("#menu2 li a").hover(
        function() {
            $(".out", this)
                .stop()
                .animate({ top: "48px" }, 300); // move down - hide
            $(".over", this)
                .stop()
                .animate({ top: "0px" }, 300); // move down - show
        },
        function() {
            $(".out", this)
                .stop()
                .animate({ top: "0px" }, 300); // move up - show
            $(".over", this)
                .stop()
                .animate({ top: "-48px" }, 300); // move up - hide
        }
    );
});
</script>
<style>
@import url("../static/css/style.css");
@import url("../static/site/css/style.css");
#menu2 {
    background-image: none;
}

body {
    background-color: #f0f3ef;
}
</style>
