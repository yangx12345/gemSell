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
                      <a>账户资料</a>
                    </li>
                  </ul>
                </div>
                <div class="table-wrap">
                  <el-form :model="currentUser" :rules="rules" status-icon ref="currentUser" label-width="100px" class="demo-currentUser">
                    <el-form-item label="用户名" prop="userName">
                      <el-input v-model="currentUser.userName" :disabled="true"></el-input>
                    </el-form-item>
                    <el-form-item label="昵称" prop="name">
                        <el-input v-model="currentUser.name"></el-input>
                    </el-form-item>
                    <el-form-item label="手机号" prop="phone">
                        <el-input v-model="currentUser.phone"></el-input>
                    </el-form-item>
                    <el-form-item label="性别" prop="sex">
                        <el-select v-model="currentUser.sex" placeholder="请选择性别">
                        <el-option label="男" value="男"></el-option>
                        <el-option label="女" value="女"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item>
                      <el-button type="primary" @click="submitForm('currentUser')">提交</el-button> 
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
import { update } from '@/api/userManage'

export default {
  name: 'orderList',
  data() {
      var checkMobile = (rule, value, callback) => {
        setTimeout(() => {

          // 定义正则规则
          let exec = /^(0|86|17951)?(13[0-9]|15[012356789]|166|17[3678]|18[0-9]|14[57])[0-9]{8}$/;
          if (exec.test(value) == true) {
            // 正确的时候干的事情
            callback();
          } else {
            callback(new Error("手机号格式错误"));
          }
        }, 500);
      };
      return {
        currentUser: {},
        rules: {
            userName: [
                { required: true, message: '请输入用户名', trigger: 'blur' },
                { min: 3, max: 13, message: '长度在 3 到 13 个字符', trigger: 'blur' }
            ],
            password: [
                { required: true, message: '请输入密码', trigger: 'change' }
            ],
            // 手机号验证
            phone: [{ validator: checkMobile, trigger: "change" }]
        }
      }
  },
  mounted(){
    this.currentUser = this.$store.state.currentUser
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          update(this.currentUser).then(resp=>{
            if(resp.code === 1){
              this.$message.success('修改成功')
            }
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
}

</script>
<style>
.sub-tit {
  padding: 0;
}

</style>
