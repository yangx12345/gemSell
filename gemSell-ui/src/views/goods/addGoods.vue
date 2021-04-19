<template>
  <div class="app-container">
    <el-dialog
      :title="currentgood.goodId? '编辑商品':'添加商品'"
      :visible.sync="dialogFormVisible"
      @before-close="onCancel()"
    >
      <el-form ref="currentgood" :model="currentgood" :inline="true" label-width="120px" :rules="userRules">
        <el-form-item label="商品名称" prop="goodName">
          <el-input v-model="currentgood.goodName" placeholder="请输入" :disabled="type==='update'" />
        </el-form-item>
        <el-form-item label="商品分类" prop="typeId">
          <treeselect v-model="currentgood.typeId" :options="options" style="width: 240px" @input="changeType()" />
        </el-form-item>
        <el-form-item label="商品状态" prop="status">
          <el-radio-group v-model="currentgood.status" placeholder="请输入">
            <el-radio :label="0">未发布</el-radio>
            <el-radio :label="1">已发布未售</el-radio>
            <el-radio :label="2">已售</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="进价" prop="purchasePrice">
          <el-input v-model="currentgood.purchasePrice" placeholder="请输入" />
        </el-form-item>
        <el-form-item label="售价" prop="price">
          <el-input v-model="currentgood.price" placeholder="请输入" />
        </el-form-item>
        <el-form-item label="数量" prop="totalNumber">
          <el-input v-model="currentgood.totalNumber" placeholder="请输入" />
        </el-form-item>
        <el-form-item
          label="商品"
        >
          <el-upload
            :on-success="handleSuccess"
            :on-error="handleError"
            :limit="1"
            :with-credentials="true"
            :data="data"
            accept=".jpg,.jpeg,.png"
            class="upload-demo"
            action="http://192.168.24.145:8088/ho-api/op/food/foodImageUpload"
          >
            <el-button
              size="small"
              type="primary"
            >点击上传</el-button>
            <div
              slot="tip"
              class="el-upload__tip"
            >只能上传jpg/png/jpeg文件，且不超过500kb</div>
          </el-upload>
        </el-form-item>
        <el-form-item label="商品介绍" prop="introduce">
          <el-input
            v-model="currentgood.introduce"
            type="textarea"
            :autosize="{ minRows: 2, maxRows: 4}"
            placeholder="请输入"
          />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="onCancel()">取 消</el-button>
        <el-button type="primary" @click="onSubmit">{{ currentgood.goodId?'保存':'添加' }}</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>
import { add, update } from '@/api/goods.js'
import { getById } from '@/api/type.js'
// import the component
import Treeselect from '@riophae/vue-treeselect'
// import the styles
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
export default {
  components: {
    Treeselect
  },
  props: {
    dialogFormVisible: {
      type: Boolean,
      default: false
    },
    currentgood: {
      type: Object,
      default: null
    },
    options: {
      type: Array,
      default: null
    }
  },
  data() {
    return {
      userRules: {
        goodName: [{ required: true, trigger: 'blur', message: '请输入登录名' }, { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }],
        password: [{ required: true, trigger: 'blur', message: '请输入密码' }, { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }],
        purchasePrice: [{ required: true, trigger: 'blur', message: '请输入进价' }],
        price: [{ required: true, trigger: 'blur', message: '请输入售价' }]
      },
      type: '',
      data: {}
    }
  },
  methods: {
    changeType() {
      console.log(this.currentgood.typeId)
      getById(this.currentgood.typeId).then(resp => {
        this.currentgood.typeName = resp.data.typeName
      })
    },
    handleSuccess(response) {
      if (response.code === 1000) {
        this.$message({
          message: '上传成功！',
          type: 'success'
        })
        this.$router.back()
      }
    },
    handleError() {
      this.$message({
        message: '上传失败！',
        type: 'error'
      })
    },
    onCancel() {
      if (this.$refs['currentgood']) {
        this.$refs['currentgood'].clearValidate()
      }
      this.$emit('update:dialogFormVisible', false)
    },
    // 提交
    onSubmit() {
      this.$refs['currentgood'].validate((valid) => {
        if (valid) {
          this.currentgood.remainNumber = this.currentgood.totalNumber
          if (!this.currentgood.goodId) {
            add(this.currentgood).then(resp => {
              if (resp.code === 1) {
                this.$message({
                  message: '增加成功',
                  type: 'success'
                })
                this.$emit('update:dialogFormVisible', false)
                this.$emit('getList')
              }
            })
          } else {
            update(this.currentgood).then((result) => {
              if (result.code === 1) {
                this.$message({
                  message: '更新成功',
                  type: 'success'
                })
                this.$emit('update:dialogFormVisible', false)
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

