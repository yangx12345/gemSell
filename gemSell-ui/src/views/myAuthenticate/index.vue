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
      <el-form-item label="鉴定状态">
        <el-select v-model="form.status" placeholder="鉴定状态" clearable>
          <el-option
            label="未鉴定"
            value="0"
          />
          <el-option
            label="已鉴定"
            value="1"
          />
        </el-select>
      </el-form-item>
      <el-button type="primary" @click="getList()">查询</el-button>
      <el-button @click="resetData">重置</el-button>
    </el-form>
    <el-table
      :data="tableData"
      style="width: 100%"
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
        prop="status"
        label="鉴品状态"
        min-width="80"
        align="center"
        :formatter="statusFormatter"
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
        prop="successTime"
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
          <el-button type="text" size="small" @click="updateClick(scope.row)">鉴定</el-button>
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
    <authenticate :dialog-form-visible.sync="dialogFormVisible" :authenticate="Authenticate" :options="options" @getList="getList" />
    <el-dialog
      title="商品图片预览"
      :visible.sync="viewVisible"
      width="60%"
      :before-close="viewhandleClose"
    >
      <div align="center">
        <el-carousel indicator-position="outside" trigger="click" height="50vh">
          <el-carousel-item v-for="item in imgOptions" :key="item.id">
            <img :src="item.url">
          </el-carousel-item>
        </el-carousel>
      </div>
    </el-dialog>
  </div>

</template>

<script>
import { getListByCondition } from '@/api/authenticate'
import { getSelectTree } from '@/api/type'
import authenticate from './authenticate'
// // import the component
// import Treeselect from '@riophae/vue-treeselect'
// // import the styles
// import '@riophae/vue-treeselect/dist/vue-treeselect.css'
export default {
  components: {
    authenticate
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
        successTime: '',
        status: '0'
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
    statusFormatter(row) {
      if (row.status === '0') return '未鉴定'
      if (row.status === '1') return '已鉴定'
    },
    handleSizeChange(val) {
      this.pagesize = val
      this.getList()
    },
    handleCurrentChange(val) {
      this.pageIndex = val
      this.getList()
    },
    viewhandleClose() {
      this.viewVisible = false
      this.imgOptions = []
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
      this.form.authUserId = this.$store.getters.userId
      getListByCondition(this.form, this.pageIndex, this.pageSize).then(resp => {
        this.tableData = resp.data.list
        this.total = resp.data.total
      })
    }
  }
}
</script>

<style lang = "scss" scoped>
.container{
  padding: 20px;
}

.el-carousel__item {
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  .carousel-image {
    max-width: 100%;
    max-height: 100%;
  }
}
</style>

