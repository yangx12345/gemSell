<template>
  <div class="el-row">
    <div class="el-col el-col-24">
      <div class="comp">
        <div class="tmpl routeanimate">
          <div class="section">
            <div class="location">
              <span>当前位置：</span>
              <a href="/index.html">首页</a> &gt;
              <a class="">会员中心</a>&gt;
              <a href="#/site/member/orderlist" class="router-link-exact-active ">我的订单</a>
            </div>
          </div>
          <div class="section clearfix">
            <div class="left-260">
              <div class="bg-wrap">
                <div class="avatar-box">
                  <h3>
                      {{ $store.state.currentUser.userName }}
                  </h3>
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
                  <ul>
                    <li class="selected">
                      <a>交易订单</a>
                    </li>
                  </ul>
                </div>
                <div class="table-wrap">
                  <div class="page-foot">
                    <el-pagination background @size-change="SizeChange" @current-change="CurrentChange" :page-sizes="[5, 8, 10, 15]" :page-size="pageSize" :current-page="pageIndex" layout="total, sizes, prev, pager, next, jumper" :total="totalCount">
                    </el-pagination>
                  </div>
                  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="ftable">
                    <tbody>
                      <tr>
                        <th width="5%" align="left">序号</th>
                        <th width="7%" align="left">订单号</th>
                        <th width="10%">姓名</th>
                        <th width="10%">订单金额</th>
                        <th width="7%">下单时间</th>
                        <th width="10%">状态</th>
                        <th width="24%">操作</th>
                      </tr>
                      <tr v-for="(item, index) in orderList" :key="item.orderId">
                         <td>{{index+1}}</td>
                        <td>{{item.orderId}}</td>
                        <!-- 三元表达式 -->
                        <td align="left">{{item.userName==''?'匿名用户':item.userName}}</td>
                        <td align="left">
                          <strong style="color: red;">￥{{item.totalPrice}}</strong>
                          <br> 在线支付
                        </td>
                        <td align="left">{{item.createTime | filterDate('YYYY:MM:DD HH:mm:ss')}}</td>
                        <td align="left">
                          {{getStatus(item.status)}}
                        </td>
                        <td align="left">
                          <router-link :to="'/orderDetail/'+item.orderId">查看订单</router-link>
                          <el-button v-if="item.status != '4'&&item.status != '3'" @click="cancalGoods(item)" type="text">取消订单</el-button>
                          <el-button v-if="item.status = '2'"  @click="getGoods(item)" type="text">点击收货</el-button>
                        </td>
                      </tr>
                    </tbody>
                  </table>
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
import { getListByCondition, update } from '@/api/order'
export default {

  name: 'orderList',
  data() {
    return {
      pageIndex: 1,
      pageSize: 5,
      orderList: [],
      totalCount: 0,
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
        return '审核中'
      }
    },
    getOrderInfo() {
      var data = {
        userId: this.$store.state.currentUser.userId
      }
      getListByCondition(data,this.pageIndex,this.pageSize).then(response => {
        this.$set(this,'orderList',response.data.list)
        this.totalCount = response.data.total;
      })
    },
    SizeChange(size) {
      this.pageIndex = 1;
      this.pageSize = size;
      this.getOrderInfo();
    },
    CurrentChange(index) {
      this.pageIndex = index;
      this.getOrderInfo();
    },
    // 点击收货
    getGoods(row){
      console.log(row)
      this.$confirm('确认收到货了？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          row.status = 4
          row.getTime = this.Format(new Date())
          update(row).then(resp=>{
            if(resp.code === 1){
              this.$message({
                type: 'success',
                message: '收货成功！'
              }); 
              this.getOrderInfo()
            } 
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消确认'
          });          
        });
    },
    // 点击收货
    cancalGoods(row){
      console.log(row)
      this.$confirm('确认取消订单？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          row.status = 5
          row.cancalTime = this.Format(new Date())
          update(row).then(resp=>{
            if(resp.code === 1){
              this.$message({
                type: 'success',
                message: '请求成功，请等候客服审核。'
              }); 
              this.getOrderInfo()
            } 
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消确认'
          });          
        });
    }
  }
}

</script>
<style>
.sub-tit {
  padding: 0;
}

</style>
