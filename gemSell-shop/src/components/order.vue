<template>
    <div>
        <div class="section">
            <div class="location">
                <span>当前位置：</span>
                <a href="/index.html">首页</a> &gt;
                <a href="/cart.html">购物车</a>
            </div>
        </div>

        <div class="section">
            <div class="wrapper">
                <div class="bg-wrap">
                    <!--购物车头部-->
                    <div class="cart-head clearfix">
                        <h2>
                            <i class="iconfont icon-cart"></i>我的购物车</h2>
                        <div class="cart-setp">
                            <ul>
                                <li class="first active">
                                    <div class="progress">
                                        <span>1</span>
                                        放进购物车
                                    </div>
                                </li>
                                <li class="active">
                                    <div class="progress">
                                        <span>2</span>
                                        填写订单信息
                                    </div>
                                </li>
                                <li class="last">
                                    <div class="progress">
                                        <span>3</span>
                                        支付/确认订单
                                    </div>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <!--购物车头部-->
                    <div class="cart-box">
                        <!-- status-icon 让输入框的后面 有提示图标 -->
                        <el-form status-icon :model="addressForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
                            <h2 class="slide-tit">
                                <span>1、收货地址</span>
                            </h2>
                            
                            
                            <div  id="addressForm" name="addressForm" url="">
                              <div v-if="!isNew">
                                <el-table ref="multipleTable" :data="addressList" tooltip-effect="dark" height="255" style="width: 100%" @current-change="clickChange">
                                  <el-table-column label="选择" width="55">
                                      <template slot-scope="scope">
                                          <el-radio  v-model="tableRadio" :label="scope.row"><i></i></el-radio>
                                      </template>
                                  </el-table-column>
                                  <el-table-column prop="name" label="收货人姓名" header-align="center" align="center"></el-table-column>
                                  <el-table-column prop="phone" label="手机号" header-align="center" align="center"></el-table-column>
                                  <el-table-column prop="storeCode" label="详细地址" header-align="center">
                                      <template slot-scope="scope">
                                          {{scope.row.province}}-{{scope.row.city}}-{{scope.row.area}}<br>
                                          {{scope.row.detailed}}
                                      </template>
                                  </el-table-column>
                                </el-table>
                                <el-button type="success" @click="isNew = true">新增地址</el-button>
                             </div>
                                <!-- 上面的一堆 个人信息输入 -->
                                <div v-else class="form-box address-info">
                                    <el-form-item label="收货人姓名" prop="name">
                                        <el-input v-model="addressForm.name" style="width:500px"></el-input>
                                    </el-form-item>
                                    <el-form-item label="所属地区" prop="">
                                        <!-- 使用data中的数据 设置为初始值 -->
                                        <v-distpicker @selected="selected" :province="addressForm.province" :city="addressForm.city" :area="addressForm.area"></v-distpicker>
                                    </el-form-item>
                                    <el-form-item label="详细地址" prop="address">
                                        <el-input v-model="addressForm.detailed" style="width:500px"></el-input>
                                    </el-form-item>
                                    <el-form-item label="手机号" prop="phone">
                                        <el-input v-model="addressForm.phone" style="width:500px"></el-input>
                                    </el-form-item>
                                    <el-button type="success" @click="isNew = false">返回地址列表</el-button>
                                    <el-button type="success" @click="saveAddress">保存</el-button>
                                </div>
                                <h2 class="slide-tit">
                                    <span>2、商品清单</span>
                                </h2>
                                <div class="line15"></div>
                                <table width="100%" border="0" align="center" cellpadding="8" cellspacing="0" class="cart-table">
                                    <tbody>
                                        <tr>
                                            <th colspan="2" align="center">商品图片</th>
                                            <th colspan="2" align="center">商品名称</th>
                                            <th colspan="2" align="center">单价</th>
                                            <th colspan="2" align="center">数量</th>
                                            <th colspan="2" align="center">金额(元)</th>
                                        </tr>
                                        <tr v-for="(item, index) in message" :key="index">
                                            <td colspan="2" align="center">
                                                
                                                <router-link :to="'/detail/'+item.goodId">
                                                <!-- <span>{{ item }} </span> -->
                                                    <img :src="JSON.parse(item.imgAddress)[0].url" class="img">
                                                </router-link>
                                                
                                            </td>
                                            <td colspan="2" align="center">
                                                <router-link :to="'/detail/'+item.goodId">{{item.goodName}} </router-link>
                                            </td>
                                            <td colspan="2" align="center">
                                                <span class="red">
                                                    ￥{{item.price}}
                                                </span>
                                            </td >
                                            <td colspan="2" align="center">{{item.num}}</td>
                                            <td colspan="2" align="center">
                                                <span class="red">
                                                    ￥{{item.totalPrice}}
                                                </span>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                                <div class="line15"></div>
                                <h2 class="slide-tit">
                                    <span>3、结算信息</span>
                                </h2>
                                <div class="buy-foot clearfix">
                                    <div class="right-box">
                                        <p>
                                            商品
                                            <label class="price">{{totalCount}}</label> 件&nbsp;&nbsp;&nbsp;&nbsp; 商品金额：￥
                                            <label id="goodsAmount" class="price">{{total}}</label> 元&nbsp;&nbsp;&nbsp;&nbsp;
                                        </p>
                                        <p class="txt-box">
                                            应付总金额：￥
                                            <label id="totalAmount" class="price">{{total}}</label>
                                        </p>
                                        <p class="btn-box">
                                            <router-link to="/cart">返回购物车</router-link>
                                            <a id="btnSubmit" class="btn submit" @click="sureOrder">支付</a>
                                        </p>
                                    </div>
                                </div>
                            </div>
                        </el-form>
                    </div>
                </div>
            </div>
        </div>

    </div>
</template>
<script>
// 导入省市联动
import VDistpicker from "v-distpicker";
import { getById, getByIds } from '@/api/order'
import { getListByCondition } from '@/api/cart'
import { add,getAddressListByCondition } from '@/api/address'
export default {
  name: "order",
  // 注册的组件
  components: { VDistpicker },
  data: function() {
    // 自定义验证的规则 触发验证时 会执行这个函数 手机号验证
    var checkMobile = (rule, value, callback) => {
      setTimeout(() => {

        // 定义正则规则
        let exec = /^(0|86|17951)?(13[0-9]|15[012356789]|166|17[3678]|18[0-9]|14[57])[0-9]{8}$/;
        if (exec.test(value) == true) {
          // 正确的时候干的事情
          callback();
        } else {
          callback(new Error("你的手机号不对"));
        }
      }, 500);
    };
    // 邮箱
    var checkEmail = (rule, value, callback) => {
      // 如果value 不为空 格式验证
      // console.log(value);
      if (value.trim().length != 0) {
        // 验证
        // 邮箱的正则
        let exec = /\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/;
        if (exec.test(value.trim()) == true) {
          callback();
        } else {
          callback(new Error("请输入正确邮箱"));
        }
      } else {
        callback();
      }
    };
    // 邮编
    var checkPostCode = (rule, value, callback) => {
      if (value.trim().length != 0) {
        // 验证
        // 邮箱的正则
        let exec = /^[1-9]\d{5}(?!\d)$/;
        if (exec.test(value.trim()) == true) {
          callback();
        } else {
          callback(new Error("请输入正确邮编"));
        }
      } else {
        callback();
      }
    };
    return {
      addressForm: {
        name: "桐先生", // 收货人姓名,
        province: '',
        city: '',
        area: '',
        detailed: "中粮商务公园18楼天台", // 详细地址
        phone: "1008611" // 联系电话
      },
      // 服务器返回的商品数据
      message: [],
      // 商品总价
      total:0,
      // 商品总件数
      totalCount:0,
      // 用户的地址列表
      addressList:[],
      // 当前选择的地址
      tableRadio: null,
      // 是否新增地址
      isNew: false,
      rules: {
        //   需要跟字段相对应
        name: [
          // required 是否必须  message 提示消息 trigger 触发的方式
          { required: true, message: "请输入收货人姓名", trigger: "blur" },
          // min 最短 max 最长 少数民族
          {
            min: 1,
            max: 20,
            message: "长度在 1 到 20 个字符",
            trigger: "change"
          }
        ],
        // 地址的验证规则
        detailed: [
          {
            required: true,
            message: "请输入地址,没有我送给谁呀",
            trigger: "blur"
          }
        ],
        // 手机号验证
        phone: [{ validator: checkMobile, trigger: "change" }],
        // 邮编验证
        postcode: [{ validator: checkPostCode, trigger: "change" }]
      }
    };
  },
  //方法
  methods:{
    // 选择地址
    clickChange(val){
      console.log(val)
      this.tableRadio = val
    },
    // 保存地址
    saveAddress(){
      add(this.addressForm).then(resp=>{
        if(resp.code === 1){
          this.$message.success('添加成功！')
        }
      })
    },
    // 选择地区
    selected(value){
        this.addressForm.province = JSON.stringify(value.province)
        this.addressForm.city = JSON.stringify(value.city)
        this.addressForm.area = JSON.stringify(value.area)
    },
    //提交订单
    sureOrder(){
      this.$confirm('确认支付?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$message({
            type: 'success',
            message: '支付成功!'
          });
          this.$router.push('/pay/'+orderid);
          //删除购物车数据
          this.message.forEach(v=>{
              this.$store.commit('deleteGood',v.orderId);
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });          
        });
    }
  },
  created(){
    //保存id
    getByIds(this.$route.params.ids)
    .then(response=>{
        console.log(response)
        //定义总金额
        let total = 0;
        var totalCount = 0;
        // 定义存储对象
        let cargoodsobj = {};
        //因为后端接口没有提供购买商品的数量,要前端根据具体数值去拼接
        response.data.forEach(v=>{
            //累加总金额
            total += v.totalPrice;
            totalCount += v.num
        });
        this.message = response.data;
        this.total = total;
        this.totalCount = totalCount
    })
    var data = {
      userId: this.$store.state.currentUser.userId
    }
    getAddressListByCondition(data,1,10).then(resp=>{
      this.addressList = resp.data.list
    })
  }
};
</script>
<style scope>
.cart-setp li{
    text-align: center;
}
</style>

