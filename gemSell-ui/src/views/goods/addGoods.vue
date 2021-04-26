<template>
  <div class="app-container">
    <el-dialog
      :title="currentgood.goodId? '编辑商品':'添加商品'"
      width="60%"
      :visible.sync="dialogFormVisible"
      @open="open()"
      @before-close="onCancel()"
    >
      <el-form ref="currentgood" :model="currentgood" label-width="120px" :rules="goodsRules">
        <el-row>
          <el-col :span="12">
            <el-form-item label="商品名称" prop="goodName">
              <el-input v-model="currentgood.goodName" placeholder="请输入" :disabled="type==='update'" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="商品分类" prop="typeId">
              <treeselect v-model="currentgood.typeId" :options="options" style="width: 240px" @input="changeType()" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="商品状态" prop="status">
              <el-radio-group v-model="currentgood.status" placeholder="请输入">
                <el-radio :label="0">未发布</el-radio>
                <el-radio :label="1">已发布未售</el-radio>
                <el-radio :label="2">已售</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="进价" prop="purchasePrice">
              <el-input v-model="currentgood.purchasePrice" type="number" :min="0" placeholder="请输入" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="售价" prop="price">
              <el-input v-model="currentgood.price" type="number" :min="0" placeholder="请输入" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="数量" prop="totalNumber">
              <el-input v-model="currentgood.totalNumber" type="number" :min="0" placeholder="请输入" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item
          label="商品图片"
        >
          <el-upload
            :on-success="handleSuccess"
            :on-error="handleError"
            :on-remove="handleRemove"
            :before-upload="handleUpload"
            :limit="5"
            multiple
            :headers="head"
            :data="data"
            :file-list="fileList"
            accept=".jpg,.jpeg,.png"
            class="upload-demo"
            list-type="picture"
            action="http://localhost:8088/gemsell-api/goods/goodsImageUpload"
          >
            <el-button
              size="small"
              type="primary"
            >点击上传</el-button>
            <div slot="tip" class="el-upload__tip">一个商品最多五张图片</div>
          </el-upload>
        </el-form-item>
        <el-row>
          <el-col :span="24">
            <el-form-item label="商品介绍" prop="introduce">
              <el-input
                v-model="currentgood.introduce"
                style="width:100%"
                type="textarea"
                :autosize="{ minRows:4, maxRows: 6}"
                placeholder="请输入"
              />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="onCancel()">取 消</el-button>
        <el-button type="primary" @click="onSubmit">{{ currentgood.goodId?'保存':'添加' }}</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>
import { mapGetters } from 'vuex'
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
      goodsRules: {
        goodName: [{ required: true, trigger: 'blur', message: '请输入商品名称' }, { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }],
        typeId: [{ required: true, trigger: 'blur', message: '请选择分类' }],
        purchasePrice: [{ required: true, trigger: 'blur', message: '请输入进价' }],
        price: [{ required: true, trigger: 'blur', message: '请输入售价' }]
      },
      type: '',
      data: { goodId: null },
      fileList: [],
      head: { token: '' }
    }
  },
  computed: {
    ...mapGetters([
      'token'
    ])
  },
  methods: {
    handleUpload() {
      if (this.fileList.length > 4) {
        this.$message({
          message: '一个商品最多上传五张图片',
          type: 'info'
        })
        return false
      }
      this.head.token = this.token
    },
    open() {
      this.data.goodId = this.currentgood.goodId
      this.fileList = JSON.parse(this.currentgood.imgAddress)
      this.currentgood.imgAddress = JSON.parse(this.currentgood.imgAddress)
    },
    changeType() {
      if (this.currentgood.typeId) {
        getById(this.currentgood.typeId).then(resp => {
          this.currentgood.typeName = resp.data.typeName
        })
      }
    },
    handleSuccess(response) {
      if (response.code === 1) {
        if (this.currentgood.imgAddress[0].name === 'defaultImg.jpg') {
          this.currentgood.imgAddress = []
          this.currentgood.imgAddress.push(response.data)
        } else {
          this.currentgood.imgAddress.push(response.data)
        }
        this.$message({
          message: '上传成功！',
          type: 'success'
        })
      }
    },
    handleRemove(file, fileList) {
      if (fileList.length !== 0) {
        var list = []
        fileList.forEach((item) => {
          var o = {
            name: item.name,
            url: item.url
          }
          list.push(o)
        })
        this.currentgood.imgAddress = list
      } else {
        this.currentgood.imgAddress = [{ name: 'defaultImg.jpg', url: 'http://localhost:8088/gemsell-api/imgs/defaultImg.jpg' }]
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
          this.currentgood.imgAddress = JSON.stringify(this.currentgood.imgAddress)
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

