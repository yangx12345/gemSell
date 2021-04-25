<template>
  <div class="app-container">
    <el-dialog
      :title="dialogForm.dialogFormTitle"
      :visible.sync="dialogForm.dialogFormVisible"
      :close-on-click-modal="false"
      @before-close="onCancel()"
    >
      <el-form ref="currentUser" :model="currentUser" :inline="true" label-width="120px" :rules="userRules">
        <el-form-item label="登录名" prop="userName">
          <el-input v-model="currentUser.userName" placeholder="请输入" :disabled="type==='update'" />
        </el-form-item>
        <el-form-item v-if="!currentUser.userId" label="登录密码" prop="password">
          <el-input v-model="currentUser.password" placeholder="请输入" show-password />
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="currentUser.name" placeholder="请输入" />
        </el-form-item>
        <el-form-item label="性别">
          <el-radio-group v-model="currentUser.sex" placeholder="请输入">
            <el-radio label="男" value="男" />
            <el-radio label="女" value="女" />
          </el-radio-group>
        </el-form-item>
        <el-form-item label="角色" prop="role">
          <el-radio-group v-model="currentUser.role" placeholder="请输入">
            <el-radio :label="'0'">系统管理员</el-radio>
            <el-radio :label="'1'">鉴定人</el-radio>
            <el-radio :label="'2'">普通用户</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="电话号码" prop="phone">
          <el-input v-model="currentUser.phone" placeholder="请输入" />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="onSubmit">{{ currentUser.userId?'保存':'添加' }}</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import { add, update } from '@/api/userManage.js'
export default {
  props: {
    dialogForm: {
      type: Object,
      default: null
    },
    currentUser: {
      type: Object,
      default: null
    }
  },
  data() {
    return {
      userRules: {
        userName: [{ required: true, trigger: 'blur', message: '请输入登录名' }, { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }],
        password: [{ required: true, trigger: 'blur', message: '请输入密码' }, { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }],
        role: [{ required: true, trigger: 'blur', message: '请选择角色' }],
        phone: [{ pattern: /^1[3|4|5|7|8][0-9]{9}$/, trigger: 'blur', message: '电话号码不合法' }]
      },
      type: ''
    }
  },
  computed: {
    ...mapGetters([
      'userName'
    ])
  },
  methods: {
    onCancel() {
      if (this.$refs['currentUser']) {
        this.$refs['currentUser'].clearValidate()
      }
      this.dialogForm.dialogFormVisible = false
    },
    // 提交
    onSubmit() {
      this.$refs['currentUser'].validate((valid) => {
        if (valid) {
          if (!this.currentUser.userId) {
            this.currentUser.createBy = this.userName
            add(this.currentUser).then(resp => {
              if (resp.code === 1) {
                this.$message({
                  message: '增加成功',
                  type: 'success'
                })
                this.dialogForm.dialogFormVisible = false
                this.$emit('getList')
              }
            })
          } else {
            this.currentUser.updateBy = this.userName
            update(this.currentUser).then((result) => {
              if (result.code === 1) {
                this.$message({
                  message: '更新成功',
                  type: 'success'
                })
                this.dialogForm.dialogFormVisible = false
                this.$emit('getList')
              }
            })
          }
        }
      })
    }
  }
}
</script>

<style scoped>
.line{
  text-align: center;
}
</style>

