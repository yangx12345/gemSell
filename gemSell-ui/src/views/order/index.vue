<template>
  <div class="container">
    <el-form ref="form" :model="form" :inline="true" label-min-width="80px">
      <el-form-item label="商品名称">
        <el-input v-model="form.goodName" clearable />
      </el-form-item>
      <el-form-item label="用户名">
        <el-input v-model="form.userName" clearable />
      </el-form-item>
      <el-form-item label="商品状态">
        <el-select v-model="form.status" placeholder="请选择商品状态" clearable>
          <el-option label="待付款" :value="'0'" />
          <el-option label="已付代发" :value="'1'" />
          <el-option label="已发" :value="'2'" />
          <el-option label="已取消" :value="'3'" />
          <el-option label="完成" :value="'4'" />
          <el-option label="审核中" :value="'5'" />
          <el-option label="已拒绝" value="6" />
        </el-select>
      </el-form-item>
      <el-button type="primary" @click="getList()">查询</el-button>
      <el-button @click="resetData">重置</el-button>
      <el-button type="primary" @click="addorder">添加</el-button>
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
        prop="orderId"
        label="编号"
        min-width="50"
        align="center"
      />
      <el-table-column
        prop="userName"
        label="用户名"
        min-width="100"
        align="center"
      />
      <el-table-column
        prop="goodName"
        label="商品名称"
        min-width="100"
        align="center"
      />
      <el-table-column
        prop="price"
        label="单价"
        align="center"
        min-width="100"
      />
      <el-table-column
        prop="num"
        label="数量"
        min-width="80"
        align="center"
      />
      <el-table-column
        prop="totalPrice"
        label="总价"
        min-width="80"
        align="center"
      />
      <el-table-column
        prop="status"
        label="状态"
        min-width="120"
        :formatter="statusFormatter"
        align="center"
      />
      <el-table-column
        prop="createTime"
        label="创建时间"
        min-width="160"
        align="center"
      />
      <el-table-column
        prop="successTime"
        label="完成时间"
        min-width="160"
        align="center"
      />
      <el-table-column
        prop="getTime"
        label="收货时间"
        min-width="160"
        align="center"
      />
      <el-table-column
        prop="payTime"
        label="支付时间"
        min-width="160"
        align="center"
      />
      <el-table-column
        prop="cancalTime"
        label="取消时间"
        min-width="160"
        align="center"
      />
      <el-table-column
        fixed="right"
        label="操作"
        align="center"
        min-width="120"
      >
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="updateClick(scope.row)">编辑</el-button>
          <el-button type="text" size="small" @click="handleClick(scope.row)">删除</el-button>
          <el-button v-if="scope.row.status === '5'" type="text" size="small" @click="auditClick(scope.row)">审核</el-button>
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
    <addOrder :dialog-form-visible.sync="dialogFormVisible" :order="order" @getList="getList" />
    <el-dialog
      title="审核要取消的订单"
      :visible.sync="auditVisible"
      width="40%"
    >
      <el-form :model="form">
        <el-form-item label="审核订单" label-width="120">
          <el-radio-group v-model="status">
            <el-radio :label="3">同意</el-radio>
            <el-radio :label="6">拒绝</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="auditVisible = false">取 消</el-button>
        <el-button type="primary" @click="audit()">确 定</el-button>
      </span>
    </el-dialog>
  </div>

</template>

<script>
import { getListByCondition, batchDelete, deleteById, audit } from '@/api/order'
import addOrder from './addOrder'
export default {
  components: {
    addOrder
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
      order: {
        orderId: null,
        userId: null,
        userName: '',
        goodId: null,
        goodName: '',
        price: 0,
        status: '0',
        createTime: null,
        successTime: null,
        num: 0,
        totalPrice: 0
      },
      options: [],
      viewVisible: false,
      imgOptions: [],
      auditVisible: false,
      status: 3,
      auditData: {}
    }
  },
  mounted() {
    this.getList()
  },
  methods: {
    auditClick(row) {
      this.auditData = { ...row }
      this.auditVisible = true
    },
    audit() {
      this.auditData.status = this.status
      audit(this.auditData).then((resp) => {
        if (resp.code === 1) {
          this.$message({
            type: 'success',
            message: resp.msg
          })
          this.auditData = {}
          this.status = 3
          this.auditVisible = false
          this.getList()
        }
      })
    },
    // 角色翻译
    statusFormatter(row) {
      if (row.status === '0') return '待付款'
      if (row.status === '1') return '已付代发'
      if (row.status === '2') return '已发'
      if (row.status === '3') return '已取消'
      if (row.status === '4') return '完成'
      if (row.status === '5') return '审核中'
      if (row.status === '6') return '已拒绝'
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
        deleteById(row.orderId).then(response => {
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
    viewhandleClose() {
      this.viewVisible = false
      this.imgOptions = []
    },
    addorder() {
      this.order = {
        orderId: null,
        userId: null,
        userName: '',
        goodId: null,
        goodName: '',
        price: 0,
        status: '0',
        createTime: null,
        successTime: null,
        num: 0,
        totalPrice: 0
      }
      this.dialogFormVisible = true
    },
    updateClick(row) {
      this.order = { ...row }
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
      this.ids = val.map(item => item.orderId)
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

