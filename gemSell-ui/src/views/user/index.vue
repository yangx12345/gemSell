<template>
  <div class="container">
    <el-form ref="form" :model="form" :inline="true" label-min-width="80px">
      <el-form-item label="用户姓名">
        <el-input v-model="form.userName" />
      </el-form-item>
      <el-form-item label="用户手机号">
        <el-input v-model="form.phone" />
      </el-form-item>
      <el-form-item label="用户角色">
          <el-select v-model="form.role" placeholder="请选择用户角色">
            <el-option label="系统管理员" :value="'0'"></el-option>
            <el-option label="鉴定人" :value="'1'"></el-option>
            <el-option label="普通用户" :value="'2'"></el-option>
          </el-select>
      </el-form-item>
      <el-button type="primary" @click="getList()">查询</el-button>
      <el-button @click="resetData">重置</el-button>
      <el-button type="primary" @click="addUser">添加</el-button>
      <el-button type="danger" :disabled="ids.length === 0" @click="batchDelete"> 删除</el-button>
    </el-form>
    <el-table
      :data="tableData"
      style="width: 100%"
      @selection-change="handleSelectionChange"
    >
      <el-table-column
        type="selection"
        min-width="55"
        align="center"
      />
      <el-table-column
        prop="userId"
        label="编号"
        min-width="50"
        align="center"
      />
      <el-table-column
        prop="userName"
        label="姓名"
        min-width="120"
        align="center"
      />
      <el-table-column
        prop="sex"
        label="性别"
        min-width="120"
        align="center"
      />
      <el-table-column
        :formatter="roleFormatter"
        prop="role"
        label="角色"
        align="center"
        min-width="120"
      />
      <el-table-column
        prop="phone"
        label="电话号码"
        min-width="120"
        align="center"
      />
      <el-table-column
        label="操作"
        align="center"
        min-width="220"
      >
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="updateClick(scope.row)">编辑</el-button>
          <el-button type="text" size="small" @click="handleClick(scope.row)">删除</el-button>
          <el-button type="text" size="small" @click="openMessage(scope.row)">重置密码</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div>
      <el-pagination
        :current-page="pageIndex"
        :page-size="pageSize"
        :total="total"
        style="float: right;margin-top:10px"
        background
        layout="total, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>
    <addUser :dialogFormVisible.sync="dialogFormVisible" :currentUser="currentUser" @getList="getList"></addUser>
  </div>

</template>

<script>
import { getListByCondition, batchDelete, restPassword, deleteById, update } from '@/api/userManage'
import addUser from './addUser'
export default {
  components: {
    addUser
  },
  data() {
    return {
      tableData: [],
      form: {
      },
      pageIndex: 1,
      pageSize: 10,
      ids: [],
      total: 0,
      dialogFormVisible: false,
      currentUser: {
        userName: '',
        password: '',
        userId: '',
        sex: '',
        role: '',
        phone: ''
      }
    }
  },
  mounted() {
    this.getList()
  },
  methods: {
    // 角色翻译
    roleFormatter(row){
      if(row.role === '0') return '管理员'
      if(row.role === '1') return '鉴定师'
      if(row.role === '2') return '普通用户'
    },
    // 重置密码
    openMessage(row) {
      this.$confirm('此操作将重置员工' + row.userName + '的登录密码, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        restPassword(row.userId).then(resp => {
          if (resp.code === 1) {
            this.$message({
              message: '已将' + row.userName + '密码重置为123456',
              type: 'success'
            })
          } else {
            this.$message({
              message: '重置失败！',
              type: 'error'
            })
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消重置密码'
        })
      })
    },
    handleSizeChange(val) {
      this.pagesize = val
      this.getList()
    },
    handleCurrentChange(val) {
      this.pageIndex = val
      this.getList()
    },
    handleClick(row) {
      this.$confirm('您确定要删除该条数据吗?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteById(row.userId).then(response => {
          const res = response
          if (res.code === 1) {
            this.$message({
              message: '删除成功！',
              type: 'success'
            })
            this.getList()
          } else {
            this.$message({
              message: '删除失败！',
              type: 'error'
            })
          }
        })
      })
    },
    addUser(){
      this.currentUser = {
        userName: '',
        password: '',
        userId: '',
        sex: '',
        role: '',
        phone: ''
      }
      this.dialogFormVisible = true
    },
    updateClick(row) {
      this.currentUser = { ...row }
      this.dialogFormVisible = true
    },
    // 重置
    resetData() {
      this.form = {}
      this.getList()
    },
    getList() {
      if (!this.form.departmentId) this.form.departmentId = this.$store.getters.deptId
      getListByCondition(this.form, this.pageIndex, this.pageSize).then(resp => {
        this.tableData = resp.data.list
        this.total = resp.data.total
      })
    },
    handleSelectionChange(val) {
      this.ids = val.map(item => item.userId)
    },
    batchDelete() {
      this.$confirm('您确定要批量删除已选定的数据项?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        var ids = this.ids.toString()
        batchDelete(ids).then(response => {
          const res = response
          if (res.code === 1) {
            this.$message({
              message: '删除成功！',
              type: 'success'
            })
            this.getList()
          } else {
            this.$message({
              message: '删除失败！',
              type: 'error'
            })
          }
        })
      })
    }
  }
}
</script>

<style scoped>
.container{
  padding: 20px;
}
</style>

