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
              <a href="#/site/member/treasureList" class="router-link-exact-active ">我的订单</a>
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
                   <a class="add" @click="back"  v-if="!showList">
                     <i class="iconfont icon-reply"></i>返回</a>
                    <a class="add" @click="addaddress">
                     <i class="el-icon-plus"></i>添加</a>
                  <ul>
                    <li class="selected">
                      <a>收货地址</a>
                    </li>
                  </ul>
                </div>
                <div class="table-wrap" v-if="showList">
                  <div class="page-foot">
                    <el-pagination background @size-change="SizeChange" @current-change="CurrentChange" :page-sizes="[5, 8, 10, 15]" :page-size="pageSize" :current-page="pageIndex" layout="total, sizes, prev, pager, next, jumper" :total="totalCount">
                    </el-pagination>
                  </div>
                  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="ftable">
                    <tbody>
                      <tr>
                        <th width="7%" align="left">序号</th>
                        <th width="10%" align="left">省份</th>
                        <th width="10%">城市</th>
                        <th width="10%">地区</th>
                        <th width="10%">收货人</th>
                        <th width="10%">手机号</th>
                        <th width="14%">详细地址</th>
                        <th width="7%">操作</th>
                      </tr>
                      <tr v-for="(item, index) in addressList" :key="item.orderId">
                         <td>{{index+1}}</td>
                        <td>{{item.province}}</td>
                        <!-- 三元表达式 -->
                        <td align="left">{{item.city}}</td>
                        <td align="left">
                          {{ item.area }}
                        </td>
                        <td align="left">{{item.name}}</td>
                        <td align="left">
                          {{item.phone}}
                        </td>
                        <td align="left">
                          {{item.detailed}}
                        </td>
                        <td align="left">
                          <el-button @click="getInfo(item)" type="text">编辑</el-button>
                        </td>
                      </tr>
                    </tbody>
                  </table>
                </div>
                <div v-else class="table-wrap">
                  <el-form :model="address" status-icon :rules="rules" ref="address" label-width="100px" class="demo-address">
                    <el-form-item label="地址" prop="allAddress">
                       <v-distpicker @selected="selected" :province="address.province" :city="address.city" :area="address.area"></v-distpicker>
                    </el-form-item>
                    <el-form-item label="详细地址" prop="detailed">
                        <el-input v-model="address.detailed" ></el-input>
                    </el-form-item>
                    <el-form-item label="收件人" prop="name">
                        <el-input v-model="address.name" ></el-input>
                    </el-form-item>
                    <el-form-item label="手机号" prop="phone">
                        <el-input v-model="address.phone" ></el-input>
                    </el-form-item>
                    <el-form-item>
                      <el-button type="primary" @click="submitForm('address')">提交</el-button> 
                    </el-form-item>
                  </el-form>
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
import VDistpicker from "v-distpicker";
import { update, add, getAddressListByCondition } from '@/api/address'
export default {
  name: 'addressList',
  components: {
    VDistpicker
  },
  data() {
     var validatorPhone = function (rule, value, callback) {
        if (value === '') {
          callback(new Error('手机号不能为空'))
        } else if (!/^1\d{10}$/.test(value)) {
          callback(new Error('手机号格式错误'))
        } else {
          callback()
        }
      }
      var validatorAllAddress = function (rule, value, callback) {
        if (this.allAddress === '') {
          callback(new Error('手机号不能为空'))
        }  else {
          callback()
        }
      }
      return {
        // 鉴品对象
        address: {},
        // 分类数组
        options: [],
        // 是否显示列表
        showList: true,
        // 鉴品列表
        addressList:[],
        pageIndex: 1,
        pageSize: 5,
        totalCount: 0,
        // 完整地址
        allAddress:'',
        rules: {
          name: [
            { required: true, message: '请输入收货人名称', trigger: 'blur' },
            { min: 1, max: 20, message: '长度在 1 到 15 个字符', trigger: 'blur' }
          ],
          allAddress: [
            { validator:validatorAllAddress, trigger: 'change' }
          ],
          detailed: [
            { required: true, message: '请输入详细地址', trigger: 'change' }
          ],
          phone: [
            { required: true, validator: validatorPhone, trigger: 'change' }
          ]
        }
      }
  },
  mounted(){
    this.getaddressList()
  },
  methods: {
     selected(value){
        this.address.province = value.province.value
        this.address.city = value.city.value
        this.address.area = value.area.value
        this.address.postcode = value.area.code
        this.allAddress = this.address.province + this.address.city + this.address.area
    },
    SizeChange(size) {
      this.pageIndex = 1;
      this.pageSize = size;
      this.getaddressList();
    },
    CurrentChange(index) {
      this.pageIndex = index;
      this.getaddressList();
    },
    getaddressList(){
      var data = {
        userId: this.$store.state.currentUser.userId
      }
      getAddressListByCondition(data, this.pageIndex, this.pageSize).then(resp=>{
        this.$set(this,'addressList',resp.data.list)
        this.totalCount = resp.data.total;
      })
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if(this.address.addressId){
            update(this.address).then(resp=>{
              if(resp.code === 1){
                this.$message.success('更新成功')
                this.back()
              }
            })
          }else {
            this.address.userId = this.$store.state.currentUser.userId
            add(this.address).then(resp=>{
              if(resp.code === 1){
                this.$message.success('添加成功')
                this.back()
              }
            })
          }
            
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    // 查看鉴品详情
    getInfo(row){
      this.showList = false,
      this.address = { ...row }
      this.allAddress = this.address.province + this.address.city + this.address.area
    },
    // 鉴品详情返回鉴品列表
    back(){
      this.showList = true
      this.getaddressList()
    },
    // 添加申请
    addaddress(){
      this.showList = false
      this.address = {
        province: '',
        city: '',
        area: '',
        detailed: '',
        postcode: '',
        name: '',
        phone: '',
        userId: ''
      }
    },
  }
}

</script>
<style>
.sub-tit {
  padding: 0;
}

</style>
