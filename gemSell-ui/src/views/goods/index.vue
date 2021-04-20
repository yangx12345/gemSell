<template>
  <div class="container">
    <el-form ref="form" :model="form" :inline="true" label-min-width="80px">
      <el-form-item label="商品名称">
        <el-input v-model="form.goodName" clearable />
      </el-form-item>
      <el-form-item label="商品分类">
        <treeselect v-model="form.typeId" :options="options" style="width: 240px" />
      </el-form-item>
      <el-form-item label="商品状态">
        <el-select v-model="form.status" placeholder="请选择商品状态" clearable>
          <el-option label="未发布" :value="'0'" />
          <el-option label="已发布未售" :value="'1'" />
          <el-option label="已售" :value="'2'" />
        </el-select>
      </el-form-item>
      <el-button type="primary" @click="getList()">查询</el-button>
      <el-button @click="resetData">重置</el-button>
      <el-button type="primary" @click="addgood">添加</el-button>
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
        prop="goodId"
        label="编号"
        min-width="50"
        align="center"
      />
      <el-table-column
        prop="goodName"
        label="商品名称"
        min-width="120"
        align="center"
      />
      <el-table-column
        prop="typeName"
        label="类型名称"
        min-width="120"
        align="center"
      />
      <el-table-column
        prop="introduce"
        label="商品介绍"
        align="center"
        min-width="240"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        prop="totalNumber"
        label="总数量"
        min-width="80"
        align="center"
      />
      <el-table-column
        prop="remainNumber"
        label="剩余数量"
        min-width="80"
        align="center"
      />
      <el-table-column
        prop="purchasePrice"
        label="进价"
        min-width="120"
        align="center"
      />
      <el-table-column
        prop="price"
        label="售价"
        min-width="120"
        align="center"
      />
      <el-table-column
        prop="status"
        label="商品状态"
        min-width="120"
        :formatter="statusFormatter"
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
    <addgood :dialog-form-visible.sync="dialogFormVisible" :currentgood="currentgood" :options="options" @getList="getList" />
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
import { getListByCondition, batchDelete, deleteById } from '@/api/goods'
import { getSelectTree } from '@/api/type'
import addgood from './addGoods'
// import the component
import Treeselect from '@riophae/vue-treeselect'
// import the styles
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
export default {
  components: {
    addgood,
    Treeselect
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
      currentgood: {
        goodName: '',
        goodId: '',
        imgAddress: '12'
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
        deleteById(row.goodId).then(response => {
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
    addgood() {
      this.currentgood = {
        goodName: '',
        typeId: null,
        goodId: '',
        introduce: '',
        imgAddress: JSON.stringify([{ name: 'defaultImg.jpg', url: 'http://localhost:8088/gemsell-api/imgs/defaultImg.jpg' }]),
        price: '',
        status: '',
        purchasePrice: ''
      }
      this.dialogFormVisible = true
    },
    updateClick(row) {
      this.currentgood = { ...row }
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
      this.ids = val.map(item => item.goodId)
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

