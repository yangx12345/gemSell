<template>
    <div>
        <div class="section">
            <div class="location">
                <span>当前位置：</span>
                <a href="#/" class="router-link-active">首页</a> &gt;
                <a href="#/gemsell-api/goodslist" class="router-link-exact-active router-link-active">购物商城</a>
            </div>
        </div>
        <div class="section">
            <div class="wrapper">
                <div class="wrap-box">
                    <div class="left-220" style="margin: 0px;">
                        <div class="banner-nav">
                            <ul>
                                <li v-for="(item,index) in cateList.slice(0,5)" :key="index">
                                    <h3>
                                        <i class="iconfont icon-arrow-right"></i>
                                        <span>{{item.typeName}}</span>
                                        <!-- <p>
                                            <span v-for="(itemVal,i) in item.subcates" :key="itemVal.id">
                                                手机通讯&nbsp;
                                            </span>
                                        </p> -->
                                    </h3>
                                    <div class="item-box">
                                        <dl>
                                            <dt>
                                                <a @click="gotoList(item.typeId)">{{item.typeName}}</a>
                                            </dt>
                                            <dd>
                                                <a href="/goods/43.html" v-for="(itemVal,i) in item.children" :key="i"><span @click="gotoList(itemVal.typeId)">{{itemVal.typeName}}</span></a>
                                            </dd>
                                        </dl>
                                    </div>
                                </li>
                                <li>
                                    <h3>
                                        <i class="iconfont icon-arrow-right"></i>
                                        <span>查看更多</span>
                                    </h3>
                                    <div class="item-box" >
                                        <dl v-for="(item,index) in cateList.slice(5,cateList.length)" :key="index">
                                            <dt>
                                                <a @click="gotoList(item.typeId)">{{item.typeName}}</a>
                                            </dt>
                                            <dd>
                                                <a href="/goods/43.html" v-for="(itemVal,i) in item.children" :key="i"><span @click="gotoList(item.typeId)">{{itemVal.typeName}}</span></a>
                                            </dd>
                                        </dl>
                                    </div>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <!--幻灯片-->
                    <div class="left-705">
                        <el-carousel trigger="click">
                            <el-carousel-item v-for="(item, index) in sliderList" :key="index">
                                <a href="#">
                                <img :src="JSON.parse(item.imgAddress)[0].url" alt="" class="bannerImg">
                                </a>
                            </el-carousel-item>
                        </el-carousel>
                    </div>
                    <!--/幻灯片-->
                    <div class="left-220">
                        <ul class="side-img-list">
                            <li v-for="(item,index) in topList.slice(0,3)" :key="item.id">
                                <div class="img-box">
                                    <label>{{index+1}}</label>
                                    <img :src="JSON.parse(item.imgAddress)[0].url">
                                </div>
                                <div class="txt-box">
                                    <a href="/goods/show-98.html">{{item.goodName}}</a>
                                    <span>{{item.add_time | filterDate}}</span>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import { Loading } from "element-ui";
export default {
    name: "index",
    data: function() {
        return {
            cateList: [],
            sliderList: [],
            topList: [],
            content: ""
        };
    },

    created() {
        this.$axios
        .post("/gemsell-api/type/getListByCondition")
        .then(response => {
            console.log(response.data.data);
            this.cateList = response.data.data;
            this.content = response.data.data;
        });
        var data= {
            status: 1
        }
        this.$axios
        .post("/gemsell-api/goods/getListByCondition?pageIndex="+1+"&pageSize="+10,data, 1,10)
        .then(response => {
            this.sliderList = response.data.data.list;
            this.topList = response.data.data.list;
        });
    },
    methods: {
        gotoList(typeId){
            this.$router.push({
                path:'/goodslist',
                query: {
                    typeId: typeId
                }
            })
        }
    }
};
</script>
<style scoped>
/* 轮播图样式 */

.bannerImg {
    width: 705px;
    height: 341px;
}

.el-carousel .el-carousel__container {
    height: 341px;
}

body {
    margin: 0;
}
</style>