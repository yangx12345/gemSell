<template>
  <div class="center">
    <div class="el-row">
      <div class="el-col el-col-24">
        <div class="comp">
          <div class="tmpl routeanimate">
            <div class="section">
              <div class="location">
                <span>当前位置：</span>
                <a href="/index.html">首页</a> &gt;
                <a href="#/site/member/center" class="">会员中心</a>&gt;
                <a href="#/site/member/orderlist" class="">我的订单</a>
              </div>
            </div>
            <div class="section clearfix">
              <div class="left-260">
                <div class="bg-wrap">
                  <div class="avatar-box">
                    <h3>{{ $store.state.currentUser.userName }}</h3>
                  </div>
                  <div class="center-nav">
                    <ul>
                      <li>
                        <h2>
                          <i class="iconfont icon-order"></i>
                         <span>订单管理</span>
                          </h2>
                        <div class="list">
                          <p>
                            <router-link to="/orderList">
                              <i class="iconfont icon-arrow-right"></i>交易订单
                            </router-link>
                          </p>
                          <p>
                          <router-link to="/treasure">
                            <i class="iconfont icon-arrow-right"></i>鉴定申请
                          </router-link>
                        </p>
                        </div>
                      </li>
                      <li>
                        <h2>
                            <i class="iconfont icon-user"></i>
                            <span>账户管理</span>
                        </h2>
                        <div class="list">
                          <p>
                            <router-link to="/userInfo">
                            <i class="iconfont icon-arrow-right"></i>账户资料
                            </router-link>
                          </p>
                          <p>
                            <router-link to="/changeMessage">
                            <i class="iconfont icon-arrow-right"></i>修改密码
                            </router-link>
                          </p>
                          <p>
                            <router-link to="/address">
                            <i class="iconfont icon-arrow-right"></i>地址管理
                            </router-link>
                          </p>
                        </div>
                      </li>
                    </ul>
                  </div>
                </div>
              </div>
              <div class="right-auto">
                <div class="bg-wrap" style="min-height: 765px;">
                  <div class="sub-tit">
                    <a href="javascript:void(0)" class="add" @click="back">
                     <i class="iconfont icon-reply"></i>返回</a>
                    <ul>
                      <li class="selected">
                        <a href="javascript:;">查看订单</a>
                      </li>
                    </ul>
                  </div>
                  <div class="order-progress">
                    <ul>
                      <li class="first active">
                        <div class="progress">下单</div>
                        <div class="info">{{goodsList[0].createTime}}</div>
                      </li>
                      <li class="second" :class="goodsList[0].payTime?'active':''">
                        <div class="progress">支付成功</div>
                        <div class="info">{{goodsList[0].payTime}}</div>
                      </li>

                      <li class="third" :class="goodsList[0].getTime?'active':''">
                        <div class="progress">收货</div>
                        <div class="info">{{goodsList[0].getTime}}</div>
                      </li>
                      <li class="last" :class="goodsList[0].successTime?'active':''">
                        <div class="progress">已完成</div>
                        <div class="info">{{goodsList[0].successTime}}</div>
                      </li>
                    </ul>
                  </div>
                  <div class="form-box accept-box form-box1">
                    <dl class="head form-group">
                      <dd>
                        订单号：{{goodsList[0].orderId}}
                        <a v-if="goodsList[0].status === '0'" class="btn-mypay" @click="signExpress">提交订单</a>
                   
                      </dd>
                    </dl>
                    <dl class="form-group">
                      <dt>订单状态：</dt>
                      <dd>
                        {{getStatus(goodsList[0].status)}}
                      </dd>
                    </dl>
                  </div>
                  <div class="table-wrap">
                    <table width="100%" border="0" cellspacing="0" cellpadding="5" class="ftable">
                      <tbody>
                        <tr>
                          <th align="left">商品信息</th>
                          <th width="60%">名称</th>
                          <th width="10%">单价
                          </th>
                          <th width="10%">数量</th>
                          <th width="10%">金额</th>
                        </tr>
                        <tr v-for="(item,index) in goodsList" :key="index">
                          <td width="60">
                            <img :src="JSON.parse(item.imgAddress)[0].url" class="img">
                          </td>
                          <td align="left">
                            <a target="_blank">{{item.goodName}}</a>
                          </td>
                          <td align="center">
                            <s>￥{{item.price}}</s>
                            <p>￥{{item.totalPrice}}</p>
                          </td>
                          <td align="center">{{item.num}}</td>
                          <td align="center">￥{{item.totalPrice}}</td>
                        </tr>
                      </tbody>
                    </table>
                  </div>
                  <div class="form-box accept-box">
                    <dl class="head form-group">
                      <dd>收货信息</dd>
                    </dl>
                    <dl class="form-group">
                      <dt>顾客姓名：</dt>
                      <dd>{{addressData.name}}</dd>
                    </dl>
                    <dl class="form-group">
                      <dt>送货地址：</dt>
                      <dd>{{addressData.province + addressData.city + addressData.area + addressData.detailed}}</dd>
                    </dl>
                    <dl class="form-group">
                      <dt>联系电话：</dt>
                      <dd>{{addressData.phone}}</dd>
                    </dl>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { getById, update } from '@/api/order'
import { getAddressById} from '@/api/address.js'
 export default {
  data() {
    return {
      goodsList: [],
      addressData: {}
    }
  },
  created() {
    this.getOrderInfo();
  },
  methods: {
    // 翻译订单状态
    getStatus(state){
      if(state === '0'){
        return '待付款'
      }
      if(state === '1'){
        return '待发货'
      }
      if(state === '2'){
        return '待收货'
      }
      if(state === '3'){
        return '已取消'
      }
      if(state === '4'){
        return '完成'
      }
      if(state === '5'){
        return '审核时间'
      }
    },
    getOrderInfo() {
     getById(this.$route.params.id)
        .then(response => {
          this.goodsList.push(response.data)
          getAddressById(response.data.addressId).then(resp=>{
            this.addressData = resp.data
          })
        });
    },
    signExpress() {
       this.$confirm('确认支付?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.goodsList[0].status = '1'
          update(this.goodsList[0]).then(resp=>{
            if(resp.code === 1){
              this.$message({
                type: 'success',
                message: '支付成功!'
              });
              this.getOrderInfo();
              this.$router.go(-1)
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });          
        });
    },
    back(){
        this.$router.go(-1);
    }
}
}

</script>
<style>
.btn-mypay {
  float: right;
  padding: 0 10px;
  margin-right: 10px;
  border-radius: 5px;
  background-color: hotpink;
  color: white;
}
.sub-tit{
    padding-top: 0;
}
</style>
