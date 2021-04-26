<template>
    <div>
        <div class="section">
            <div class="location">
                <span>当前位置：</span>
                <a href="/index.html">首页</a> &gt;
                <a href="/login.html">会员登录</a>
            </div>
        </div>
        <div class="section">
            <div class="wrapper">
                <div class="bg-wrap">
                    <div class="nav-tit">
                        <a :class="selected==='login'?'selected':''" @click="selected = 'login'">账户登录</a>
                        <i>|</i>
                        <a :class="selected==='login'?'':'selected'" @click="selected = 'register'">免费注册</a>
                    </div>
                    <div v-if="selected === 'login'" id="loginform" name="loginform" class="login-box">
                        <el-form :model="loginForm" :rules="rules" ref="loginForm" label-width="100px" class="demo-registerForm">
                            <el-form-item label="用户名" prop="userName">
                                <el-input v-model="loginForm.userName"></el-input>
                            </el-form-item>
                            <el-form-item label="密码" prop="password">
                                <el-input type="password" v-model="loginForm.password"></el-input>
                            </el-form-item>
                            <el-form-item>
                                <el-button type="success" @click="login()">立即登录</el-button>
                            </el-form-item>
                        </el-form>
                    </div>
                    <div v-else id="registerform" name="registerform" class="login-box">
                        <el-form :model="registerForm" :rules="rules" ref="registerForm" label-width="100px" class="demo-registerForm">
                            <el-form-item label="用户名" prop="userName">
                                <el-input v-model="registerForm.userName"></el-input>
                            </el-form-item>
                            <el-form-item label="密码" prop="password">
                                <el-input type="password" v-model="registerForm.password"></el-input>
                            </el-form-item>
                            <el-form-item label="昵称" prop="name">
                                <el-input v-model="registerForm.name"></el-input>
                            </el-form-item>
                            <el-form-item label="手机号" prop="phone">
                                <el-input v-model="registerForm.phone"></el-input>
                            </el-form-item>
                            <el-form-item label="性别" prop="sex">
                                <el-select v-model="registerForm.sex" placeholder="请选择性别">
                                <el-option label="男" value="男"></el-option>
                                <el-option label="女" value="女"></el-option>
                                </el-select>
                            </el-form-item>
                            <el-form-item>
                                <el-button type="success" @click="register('registerForm')">立即注册</el-button>
                            </el-form-item>
                        </el-form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import { register } from '@/api/userManage'
import { getListByCondition } from '@/api/cart'
export default {
    name: 'login',
    data() {
        return {
            username: 'admin',
            password: '123',
            selected: 'login',
            registerForm: {},
            loginForm: {},
            rules: {
                userName: [
                    { required: true, message: '请输入用户名', trigger: 'blur' },
                    { min: 3, max: 13, message: '长度在 3 到 13 个字符', trigger: 'blur' }
                ],
                password: [
                    { required: true, message: '请输入密码', trigger: 'change' }
                ]
            }
        }

    },
    mounted(){
        this.selected = this.$route.params.selected
    },
    methods: {
        login() {
            this.$refs['loginForm'].validate((valid) => {
                if (valid) {
                    // 设置进度条的颜色 尺寸
                    this.$Loading.config({
                        color: "skyblue",
                        failedColor: "hotpink",
                        height: 5
                    });
                    // 显示进度条
                    this.$Loading.start();

                    this.$store.dispatch('login', this.loginForm).then(async () => {
                        this.$Notice.success({
                            title:'提示',
                            desc:'登录成功'
                        })
                        await this.$store.dispatch('getInfo')
                        this.$router.go(-1);
                        var userId = this.$store.state.currentUser.userId
                        var data = {
                            userId: userId
                        }
                        //调用接口
                        getListByCondition(data,1,10).then(response => {
                            this.$store.commit('updateGoodsNum',response.data.total)
                        })
                        //   关闭进度条
                        this.$Loading.finish();

                    }).catch(() => {
                        this.$Loading.error();
                    })
                }
                else {}
            })

        },
        register(formName){
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    this.registerForm.role = 2
                    register(this.registerForm).then(resp=>{
                        if(resp.code === 1){
                                this.$Notice.success({
                                    title:'提示',
                                    desc:'  注册成功'
                                })
                                var loginForm  = {
                                    userName: this.registerForm.userName,
                                    password: this.registerForm.password
                                }
                                this.$store.dispatch('login', loginForm).then(() => {
                                this.$store.dispatch('getInfo')
                                this.$router.go(-1);
                            })
                        }else {
                            this.$Notice.error({
                                title:'提示',
                                desc:resp.msg
                            });
                        }
                    })
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
        }
    }
}
</script>
