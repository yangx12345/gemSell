<template>
  <div class="container">
    <el-form ref="form" :model="form" :inline="true" label-min-width="80px">
      <el-form-item label="请求人">
        <el-input v-model="form.ownerName" clearable />
      </el-form-item>
      <el-form-item label="鉴品编码">
        <el-input v-model="form.treasureCode" clearable />
      </el-form-item>
      <el-form-item label="鉴定人">
        <el-input v-model="form.authUserName" clearable />
      </el-form-item>
      <el-button type="primary" @click="getList()">查询</el-button>
      <el-button @click="resetData">重置</el-button>
      <el-button type="primary" @click="addAuthenticate">添加</el-button>
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
        prop="authenticateId"
        label="编号"
        min-width="50"
        align="center"
      />
      <el-table-column
        prop="ownerName"
        label="请求人"
        min-width="120"
        align="center"
      />
      <el-table-column
        prop="authUserName"
        label="鉴定人"
        min-width="120"
        align="center"
      />
      <el-table-column
        prop="treasureName"
        label="鉴品名称"
        min-width="120"
        align="center"
      />
      <el-table-column
        prop="treasureCode"
        label="鉴品编码"
        min-width="80"
        align="center"
      />
      <el-table-column
        prop="typeName"
        label="类型名称"
        min-width="80"
        align="center"
      />
      <el-table-column
        prop="texture"
        label="质地"
        min-width="80"
        align="center"
      />
      <el-table-column
        prop="weight"
        label="重量"
        min-width="80"
        align="center"
      />
      <el-table-column
        prop="formCity"
        label="产地"
        min-width="120"
        align="center"
      />
      <el-table-column
        prop="result"
        label="鉴定结果"
        align="center"
        min-width="240"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        prop="createTime"
        label="申请时间"
        min-width="160"
        align="center"
      />
      <el-table-column
        prop="createTime"
        label="鉴定时间"
        min-width="160"
        align="center"
      />
      <el-table-column
        fixed="right"
        label="操作"
        align="center"
        min-width="200"
      >
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="updateClick(scope.row)">编辑</el-button>
          <el-button type="text" size="small" @click="handleClick(scope.row)">删除</el-button>
          <el-button type="text" size="small" @click="viewClick(scope.row)">图片预览</el-button>
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
    <addgood :dialog-form-visible.sync="dialogFormVisible" :authenticate="Authenticate" :options="options" @getList="getList" />
    <el-dialog
      title="商品图片预览"
      :visible.sync="viewVisible"
      width="60%"
      :before-close="viewhandleClose"
    >
      <el-carousel height="450px">
        <el-carousel-item v-for="item in imgOptions" :key="item.id">
          <img :src="item.url">
        </el-carousel-item>
      </el-carousel>

    </el-dialog>
  </div>

</template>

<script>
import { getListByCondition, batchDelete, deleteById } from '@/api/authenticate'
import { getSelectTree } from '@/api/type'
import addgood from './addAuthenticate'
// // import the component
// import Treeselect from '@riophae/vue-treeselect'
// // import the styles
// import '@riophae/vue-treeselect/dist/vue-treeselect.css'
export default {
  components: {
    addgood
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
      Authenticate: {
        authenticateId: null,
        ownerId: null,
        ownerName: '',
        imgAddress: [],
        treasureName: '',
        treasureCode: '',
        typeId: null,
        typeName: '',
        texture: '',
        weight: '',
        formCity: '',
        authUserId: '',
        authUserName: '',
        result: '',
        createTime: '',
        successTime: ''
      },
      options: [],
      viewVisible: false,
      imgOptions: []
    }
  },
  mounted() {
    this.getList()
    getSelectTree().then(resp => {
      this.options = resp.data
    })
  },
  methods: {
    // 角色翻译
    statusFormatter(row) {
      if (row.status === 0) return '未发布'
      if (row.status === 1) return '已发布未售'
      if (row.status === 2) return '已售'
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
        deleteById(row.authenticateId).then(response => {
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
    delPicture(row) {

    },
    viewhandleClose() {
      this.viewVisible = false
      this.imgOptions = []
    },
    addAuthenticate() {
      this.Authenticate = {
        authenticateId: null,
        ownerId: null,
        ownerName: '',
        imgAddress: [],
        treasureName: '',
        treasureCode: '',
        typeId: null,
        typeName: '',
        texture: '',
        weight: '',
        formCity: '',
        authUserId: '',
        authUserName: '',
        result: '',
        createTime: '',
        successTime: ''
      }
      this.dialogFormVisible = true
    },
    updateClick(row) {
      this.Authenticate = { ...row }
      this.dialogFormVisible = true
    },
    // 重置
    resetData() {
      this.form = {}
      this.getList()
    },
    viewClick(row) {
      this.imgOptions = JSON.parse(row.imgAddress)
      this.viewVisible = true
    },
    getList() {
      if (!this.form.departmentId) this.form.departmentId = this.$store.getters.deptId
      getListByCondition(this.form, this.pageIndex, this.pageSize).then(resp => {
        this.tableData = resp.data.list
        this.total = resp.data.total
      })
    },
    handleSelectionChange(val) {
      this.ids = val.map(item => item.authenticateId)
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

