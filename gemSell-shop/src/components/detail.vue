<template>
    <div>
        <div class="section">
            <div class="location">
                <span>当前位置：</span>
                <a href="/index.html">首页</a> &gt;
                <a href="/goods.html">购物商城</a> &gt;
                <a href="/goods/42/1.html">商品详情</a>
            </div>
        </div>
        <div class="section">
            <div class="wrapper clearfix">
                <div class="wrap-box">
                    <div class="left-925">
                        <div class="goods-box clearfix">
                            <div class="pic-box">
                                <ProductZoomer v-if="images.normal_size.length!=0" :base-images="images" :base-zoomer-options="zoomerOptions">
                                </ProductZoomer>
                            </div>
                            <div class="goods-spec">
                                <h1>{{goodsInfo.goodName}}</h1>
                                <p class="subtitle">{{goodsInfo.typeName}}</p>
                                <div class="spec-box">
                                    <dl>
                                        <dt>销售价</dt>
                                        <dd>
                                            <em id="commoditySellPrice" class="price">¥{{goodsInfo.price}}</em>
                                        </dd>
                                    </dl>
                                </div>
                                <div class="spec-box">
                                    <dl>
                                        <dt>购买数量</dt>
                                        <dd>
                                            <div class="stock-box">
                                                <el-input-number v-model="buyCount" :min="1" :max="goodsInfo.totalNumber" label="描述文字" size="small"></el-input-number>
                                            </div>
                                            <span class="stock-txt">
                                                库存
                                                <em id="commodityStockNum">{{goodsInfo.remainNumber}}</em>件
                                            </span>
                                        </dd>
                                    </dl>
                                    <dl>
                                        <dd>
                                            <div id="buyButton" class="btn-buy">
                                                <button @click="addOrder()" class="buy">立即购买</button>
                                                <button @click="addGoods" class="add">加入购物车</button>
                                            </div>
                                        </dd>
                                    </dl>
                                </div>
                            </div>
                        </div>
                        <div id="goodsTabs" class="goods-tab bg-wrap">
                            <Affix>
                                <div id="tabHead" class="tab-head" style="position: static; top: 517px; width: 925px;">
                                    <ul>
                                        <li>
                                            <a href="javascript:;" :class="{selected:isSelected}" @click="isSelected=true">商品介绍</a>
                                        </li>
                                    </ul>
                                </div>
                            </Affix>
                            <div class="tab-content entry" style="padding: 20px" v-show="isSelected" v-html="goodsInfo.introduce">
                            </div>
                        </div>
                    </div>
                    <div class="left-220">
                        <div class="bg-wrap nobg">
                            <div class="sidebar-box">
                                <h4>推荐商品</h4>
                                <ul class="side-img-list">
                                    <li v-for="items in hotList" :key="items.goodId" @click="gotoDetail(items)">
                                        <div class="img-box">
                                            <!-- <a href="#/site/goodsinfo/90" class=""> -->
                                            <router-link :to="'/detail/'+items.goodId">
                                                <img :src="JSON.parse(items.imgAddress)[0].url">
                                            </router-link>
                                            <!-- </a> -->
                                        </div>
                                        <div class="txt-box">
                                            <a href="#/site/goodsinfo/90" class="">{{items.goodName}}</a>
                                        </div>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <BackTop></BackTop>
        <img v-if="imgList.length!=0" class="moveImg" :src="imgList[0].url" alt="">
    </div>
</template>
<script>
import { Loading } from "element-ui";
import $ from 'jquery';
import { getGoodsListByCondition, getById} from '@/api/goods'
import { add } from '@/api/cart'
import { addorder  } from '@/api/order'
export default {
    name: 'detail',
    data: function() {
        return {
            productId: undefined,
            goodsInfo: {},
            hotList: [],
            imgList: [],
            buyCount: 1,
            isSelected: true,
            // 放大镜设置
            zoomerOptions: {
                'zoomFactor': 5, // 放大倍数
                'pane': 'container-round', // container-round 小放大镜
                'hoverDelay': 300,
                'namespace': 'zoomer',
                'move_by_click': true, // true 点击切换
                'scroll_items': 7,
                'choosed_thumb_border_color': "yellowgreen" // 框的颜色
            },
            // 轮播图用的图片 默认的数据格式 不支持
            // 这里的数据 需要在接口调用完毕之后 才能够获取
            images: {
                normal_size: [],
                large_size: [],
                thumbs: []
            },
            pageNum: 1, //页码
            pageSize: 5, //页容量
            comments: [], //评论内容
            totalCount: 0, //评论总条数
            commentInfo: '' //评论内容
        }
    },
    methods: {
        //获取商品信息
        getProductDatil() {
            //获取Id
            this.productId = this.$route.query.id;
            var _this = this
            //获取数据
             getById(this.productId)
                .then(response => {
                    _this.goodsInfo = response.data
                    _this.imgList = JSON.parse(_this.goodsInfo.imgAddress);
                    // 处理 放大镜数据
                    let temArr = [];
                    // 循环处理数据
                    _this.imgList.forEach((v, i) => {
                        temArr.push({
                            id: i,
                            url: v.url
                        })
                    })
                    // 临时数组
                    _this.images.normal_size = temArr;
                    _this.images.large_size = temArr;
                    _this.images.thumbs = temArr;
                     _this.buyCount = 1;
                });
                var data= {
                    status: 1
                }
                getGoodsListByCondition(data,1,5)
                    .then(response => {
                        _this.hotList = response.data.list;
                    });
        },
        // 立即购买
        addOrder(){
            if(this.$store.state.currentUser === undefined || this.$store.state.currentUser === '' ||this.$store.state.currentUser === null){
                this.$message.error('请先登录！')
                return
            }
            var order = {
                goodId: this.goodsInfo.goodId,
                goodName: this.goodsInfo.goodName,
                price: this.goodsInfo.price,
                num: this.buyCount,
                userId: this.$store.state.currentUser.userId,
                userName: this.$store.state.currentUser.userName,
                status: 0,
                totalPrice: this.buyCount * this.goodsInfo.price,
                imgAddress: this.goodsInfo.imgAddress
            }
            addorder(order).then(resp=>{
                if(resp.code === 1){
                    this.$router.push({
                        path: `/order/${resp.data}`
                    })
                }
            })
        },
        // 跳转推荐商品
        gotoDetail(row){
             this.$router.push({
                path:'/detail',
                query: {
                    id: row.goodId
                }
            })
        },
        // 添加购物车
        addGoods(){
            if(this.$store.state.currentUser === undefined || this.$store.state.currentUser === '' ||this.$store.state.currentUser === null){
                this.$message.error('请先登录！')
                return
            }
            //获取加入购物车的位置
            let addOffset = $('.add').offset();
            //获取购物车的位置
            let carTarget = $('.icon-cart').offset();
            $('.moveImg').stop().show().addClass('move').css(addOffset).animate(carTarget,1000,function(){
                $(this).hide().removeClass('move');
            })
            var cart = {
                goodId: this.goodsInfo.goodId,
                goodName: this.goodsInfo.goodName,
                price: this.goodsInfo.price,
                number: this.buyCount,
                userId: this.$store.state.currentUser.userId,
                imgAddress: this.goodsInfo.imgAddress
            }
            add(cart).then(resp=>{
                if(resp.code === 1){
                    this.$store.commit('updateGoodsNum',this.$store.state.cartDate + 1);
                }
            })

        }
},
watch: {
        $route(val, oldVal) {
            // 数组长度为0 直接销毁
            this.images.normal_size = [];
            this.images.large_size = [];
            this.images.thumbs = [];
            // 回调函数中重新复制 再次 生成
            this.getProductDatil();
        }
    },
    created() {
        //加载动画
        const loading = this.$loading({
            lock: true,
            text: "Loading",
            spinner: "el-icon-loading",
            background: "rgba(0, 0, 0, 0.7)"
        });
        this.getProductDatil();
        //关闭加载动画
        let loadingInstance = Loading.service({ text: false });
        this.$nextTick(() => {
            // 以服务的方式调用的 Loading 需要异步关闭
            loadingInstance.close();
        });
    }
}
</script>
<style lang='less'>
.tab-content img {
    width: 100%;
    height: 100%;
    display: block;
}

.pic-box {
    width: 365px;
    .control-box .thumb-list {
        display: flex;
        justify-content: center;
        img {
            width: 80px;
            height: 80px;
            margin: 2px;
        }
    }
    .control {
        //   text-align:center;
        display: flex; // 主轴
        justify-content: center; // 纵轴 副轴 侧轴
        align-items: center;
    }
}
// 移动图片的样式
.moveImg{
    position: absolute;
    width: 50px;
    height: 50px;
    display: none;
}
.moveImg.move{
    transition: transform 1s;
    transform: rotate(360deg) scale(.5);
}
</style>
