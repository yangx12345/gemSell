<template>
  <div class="container">
    <el-form ref="form" :model="form" :inline="true" label-width="80px">
      <el-form-item label="用户姓名">
        <el-input v-model="form.username" />
      </el-form-item>
      <el-form-item label="用户角色">
        <el-input v-model="form.cart" />
      </el-form-item>
      <el-button type="primary" @click="getList()">查询</el-button>
      <el-button @click="resetData">重置</el-button>
      <el-button type="danger" :disabled="ids.length === 0" @click="batchDelete"> 删除</el-button>
    </el-form>
    <el-table
      :data="tableData"
      style="width: 100%"
      @selection-change="handleSelectionChange"
    >
      <el-table-column
        type="selection"
        width="55"
        align="center"
      />
      <el-table-column
        prop="id"
        label="编号"
        width="50"
        align="center"
      />
      <el-table-column
        prop="name"
        label="姓名"
        width="120"
        align="center"
      />
      <el-table-column
        prop="username"
        label="登录名"
        width="120"
        align="center"
      />
      <el-table-column
        prop="gender"
        label="性别"
        width="120"
        align="center"
      />
      <el-table-column
        prop="birthday"
        label="出生日期"
        width="120"
        align="center"
      />
      <el-table-column
        :formatter="roleFormatter"
        prop="role"
        label="角色"
        align="center"
        width="120"
      />
      <el-table-column
        prop="idCard"
        label="身份证号"
        width="180"
        align="center"
      />
      <el-table-column
        prop="phone"
        label="电话号码"
        width="120"
        align="center"
      />
      <el-table-column
        prop="address"
        label="联系地址"
        width="120"
        align="center"
        show-overflow-tooltip
      />
      <el-table-column
        prop="beginDate"
        label="入职日期"
        align="center"
        width="120"
      />
      <el-table-column
        prop="workState"
        label="在职状态"
        align="center"
        width="120"
      />
      <el-table-column
        prop="workID"
        label="工号"
        align="center"
        width="120"
      />
      <el-table-column
        fixed="right"
        label="操作"
        align="center"
        width="220"
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
  </div>

</template>

<script>
import { getListByCondition, batchDelete, restPassword, deleteById, update } from '@/api/employee'
export default {
  data() {
    return {
      tableData: [],
      form: {
      },
      pageIndex: 1,
      pageSize: 10,
      ids: [],
      total: 0,
      roleStatus: [
        {
          value: 0,
          label: '管理员'
        },
        {
          value: 1,
          label: '鉴定师'
        },
        {
          value: 2,
          label: '普通用户'
        }
      ],
      dialogFormVisible: false,
      currentUser: {}
    }
  },
  mounted() {
    this.getList()
  },
  methods: {
    // 角色翻译
    roleFormatter(row){
      this.roleStatus.forEach(item=>{
        if(item.value === row.cart) return item.label
      })
    },
    // 重置密码
    openMessage(row) {
      this.$confirm('此操作将重置员工' + row.name + '的登录密码, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        restPassword(row.id).then(resp => {
          if (resp.code === 1) {
            this.$message({
              message: '已将' + row.name + '密码重置为123456',
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
        deleteById(row.id).then(response => {
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
    updateClick(row) {
      this.$router.push({
        path: '/user/add',
        query: {
          id: row.id
        }
      })
    },
    // 重置
    resetData() {
      this.$refs['form'].resetFields()
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
      this.ids = val.map(item => item.id)
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

