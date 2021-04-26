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
                    <a class="add" @click="addTreasure">
                     <i class="el-icon-plus"></i>申请</a>
                  <ul>
                    <li class="selected">
                      <a>鉴定申请</a>
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
                        <th width="5%" align="left">序号</th>
                        <th width="17%" align="left">鉴品编号</th>
                        <th width="10%">鉴品名称</th>
                        <th width="10%">鉴品分类</th>
                        <th width="10%">鉴定结果</th>
                        <th width="10%">鉴品图片</th>
                        <th width="11%">操作</th>
                      </tr>
                      <tr v-for="(item, index) in treasureList" :key="item.orderId">
                         <td>{{index+1}}</td>
                        <td>{{item.treasureCode}}</td>
                        <!-- 三元表达式 -->
                        <td align="left">{{item.treasureName}}</td>
                        <td align="left">
                          {{ item.typeName }}
                        </td>
                        <td align="left">{{item.result?item.result:'待鉴定'}}</td>
                        <td align="left">
                          <el-image
                            style="width: 100px; height: 100px"
                            :src="JSON.parse(item.imgAddress)[0].url"
                            fit="contain"></el-image>
                        </td>
                        <td align="left">
                          <el-button @click="getInfo(item)" type="text">查看详情</el-button>
                        </td>
                      </tr>
                    </tbody>
                  </table>
                </div>
                <div v-else class="table-wrap">
                  <el-form :model="treasure" :rules="rules" status-icon ref="treasure" label-width="100px" class="demo-treasure">
                    <el-form-item label="鉴品名称" prop="treasureName">
                      <el-input v-model="treasure.treasureName" :disabled="treasure.authenticateId? true: false"></el-input>
                    </el-form-item>
                    <el-form-item label="产地" prop="formCity">
                        <el-input v-model="treasure.formCity" :disabled="treasure.authenticateId? true: false"></el-input>
                    </el-form-item>
                    <el-form-item label="类型" prop="typeId">
                        <treeselect v-model="treasure.typeId" :options="options" style="width: 240px" :disabled="treasure.authenticateId? true: false" @input="changeType" />
                    </el-form-item>
                    <el-form-item label="质地" prop="texture">
                        <el-input v-model="treasure.texture" :disabled="treasure.authenticateId? true: false"></el-input>
                    </el-form-item>
                    <el-form-item label="重量" prop="weight">
                        <el-input v-model="treasure.weight" type="number" :disabled="treasure.authenticateId? true: false"></el-input>(克)
                    </el-form-item>
                    <el-form-item v-if="treasure.authenticateId" label="状态" prop="status">
                        {{treasure.status === '1'?'已鉴定':'待鉴定'}}
                    </el-form-item>
                    <el-form-item v-if="treasure.authenticateId" label="鉴定评价" prop="status">
                        {{treasure.remark}}
                    </el-form-item>
                    <el-form-item v-if="treasure.authenticateId" label="鉴定结果" prop="status">
                        {{treasure.result}}
                    </el-form-item>
                    <el-form-item v-if="!treasure.authenticateId" prop="imgAddress">
                      <el-upload
                        :on-success="handleSuccess"
                        :on-error="handleError"
                        :on-remove="handleRemove"
                        :before-upload="handleUpload"
                        :limit="5"
                         multiple
                        :headers="head"
                        :file-list="fileList"
                        accept=".jpg,.jpeg,.png"
                        class="upload-demo"
                        list-type="picture"
                        action="http://localhost:8088/gemsell-api/authenticate/imageUpload"
                      >
                        <el-button
                          size="small"
                          type="primary"
                        >点击上传</el-button>
                        <div slot="tip" class="el-upload__tip">一个鉴品最多五张图片</div>
                        </el-upload>
                    </el-form-item>
                    <el-form-item v-if="!treasure.authenticateId">
                      <el-button type="primary" @click="submitForm('treasure')">提交</el-button> 
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
import { update, add, getListByCondition } from '@/api/authenticate'
// import the component
import Treeselect from '@riophae/vue-treeselect'
// import the styles
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import { getTypeSelect} from '@/api/type'
export default {
  name: 'treasureList',
  components: {
    Treeselect
  },
  data() {
      return {
        // 鉴品对象
        treasure: {},
        // 分类数组
        options: [],
        // 是否显示列表
        showList: true,
        // 鉴品列表
        treasureList:[],
        pageIndex: 1,
        pageSize: 5,
        totalCount: 0,
        fileList: [],
        head: { token: '' },
        rules: {
          treasureName: [
            { required: true, message: '请输入鉴品名称', trigger: 'blur' }
          ],
          imgAddress: [
            { required: true, message: '请输入至少上传一张鉴品图片', trigger: 'blur' }
          ]
        }
      }
  },
  mounted(){
    getTypeSelect()
    .then(response => {
        this.options = response.data;
    })
    this.getTreasureList()
  },
  methods: {
    handleUpload() {
      if (this.fileList.length > 4) {
        this.$message({
          message: '一个商品最多上传五张图片',
          type: 'info'
        })
        return false
      }
      this.head.token = this.$store.state.token
    },
    handleSuccess(response) {
      if (response.code === 1) {
        if (this.treasure.imgAddress.length>0&&this.treasure.imgAddress[0].name === 'defaultImg.jpg') {
          this.treasure.imgAddress = []
          this.treasure.imgAddress.push(response.data)
        } else {
          this.treasure.imgAddress.push(response.data)
        }
        this.$message({
          message: '上传成功！',
          type: 'success'
        })
      }
    },
    handleRemove(file, fileList) {
      if (fileList.length !== 0) {
        var list = []
        fileList.forEach((item) => {
          var o = {
            name: item.name,
            url: item.url
          }
          list.push(o)
        })
        this.treasure.imgAddress = list
      } else {
        this.treasure.imgAddress = [{ name: 'defaultImg.jpg', url: 'http://localhost:8088/gemsell-api/imgs/defaultImg.jpg' }]
      }
    },
    handleError() {
      this.$message({
        message: '上传失败！',
        type: 'error'
      })
    },
    SizeChange(size) {
      this.pageIndex = 1;
      this.pageSize = size;
      this.getTreasureList();
    },
    CurrentChange(index) {
      this.pageIndex = index;
      this.getTreasureList();
    },
    getTreasureList(){
      var data = {
        ownerId: this.$store.state.currentUser.userId
      }
      getListByCondition(data, this.pageIndex, this.pageSize).then(resp=>{
        this.$set(this,'treasureList',resp.data.list)
        this.totalCount = resp.data.total;
      })
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.treasure.ownerId = this.$store.state.currentUser.userId
          this.treasure.ownerName = this.$store.state.currentUser.username
          this.treasure.imgAddress = JSON.stringify(this.treasure.imgAddress)
          var date = new Date()
          // 鉴品编号由年月日分类ID和用户ID组成
          this.treasure.treasureCode = date.getFullYear() + date.getMonth() + date.getDate() + this.treasure.typeId +this.$store.state.currentUser.userId
            add(this.treasure).then(resp=>{
              if(resp.code === 1){
                this.$message.success('申请成功')
                this.back()
              }
            })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    changeType(value,options){
      for(var  i=0; i<this.options.length ; i++){
        if(this.options[i].id === value) this.treasure.typeName = this.options[i].label
        else if(this.options[i].children.length >0) {
          var result = this.changeTypeChild(value, this.options[i].children)
          if(result) this.treasure.typeName = result
        }
      }
    },
    changeTypeChild(value,options){
      for(var  i=0; i<options.length ; i++){
        if(options[i].id === value) return options[i].label
      }
    },
    // 查看鉴品详情
    getInfo(row){
      this.showList = false,
      this.treasure = { ...row }
      this.fileList = JSON.parse(this.treasure.imgAddress)
    },
    // 鉴品详情返回鉴品列表
    back(){
      this.showList = true
      this.getTreasureList()
    },
    // 添加申请
    addTreasure(){
      this.showList = false
      this.treasure = {
        authenticateId: '',
        treasureName: '',
        formCity: '',
        typeId: '',
        texture: '',
        weight: '',
        imgAddress: []
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
