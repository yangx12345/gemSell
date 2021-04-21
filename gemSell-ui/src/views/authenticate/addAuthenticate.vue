<template>
  <div class="app-container">
    <el-dialog
      :title="authenticate.authenticateId? '编辑鉴定':'添加商品'"
      width="60%"
      :visible.sync="dialogFormVisible"
      @open="open()"
      @before-close="onCancel()"
    >
      <el-form ref="authenticate" :model="authenticate" label-width="120px" :rules="authenticateRules">
        <el-row>
          <el-col :span="12">
            <el-form-item label="用户" prop="ownerId">
              <el-select v-model="authenticate.ownerId" placeholder="请选择用户" @change="userChange">
                <el-option
                  v-for="item in userOptions"
                  :key="item.userId"
                  :label="item.userName"
                  :value="item.userId"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="鉴定人" prop="authUserId">
              <el-select v-model="authenticate.authUserId" placeholder="请选择鉴定人" @change="authChange">
                <el-option
                  v-for="item in authOptions"
                  :key="item.userId"
                  :label="item.userName"
                  :value="item.userId"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="鉴品名称" prop="treasureName">
              <el-input v-model="authenticate.treasureName" placeholder="请输入鉴品名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="鉴品编码" prop="treasureCode">
              <el-input v-model="authenticate.treasureCode" placeholder="请输入鉴品编码" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="鉴品分类" prop="typeId">
              <treeselect v-model="authenticate.typeId" :options="options" style="width: 240px" @input="changeType()" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="质地" prop="texture">
              <el-input v-model="authenticate.texture" placeholder="请输入" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="重量" prop="weight">
              <el-input v-model="authenticate.weight" type="number" :min="0" :step="0.1" placeholder="请输入" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="产地" prop="formCity">
              <el-input v-model="authenticate.formCity" placeholder="请输入" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item
          prop="imgAddress"
          label="鉴品图片"
        >
          <el-upload
            :on-success="handleSuccess"
            :on-error="handleError"
            :on-remove="handleRemove"
            :before-upload="handleUpload"
            :limit="5"
            multiple
            :headers="head"
            :file-list="fileList"
            accept=".jpg,.jpeg,.png"
            class="upload-demo"
            list-type="picture"
            action="http://localhost:8088/gemsell-api/authenticate/imageUpload"
          >
            <el-button
              size="small"
              type="primary"
            >点击上传</el-button>
            <div slot="tip" class="el-upload__tip">一个鉴品最多五张图片</div>
          </el-upload>
        </el-form-item>
        <el-row>
          <el-col :span="24">
            <el-form-item label="鉴定结果" prop="result">
              <el-input
                v-model="authenticate.result"
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
        <el-button type="primary" @click="onSubmit">{{ authenticate.authenticateId?'保存':'添加' }}</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import { add, update } from '@/api/authenticate'
import { getById } from '@/api/type.js'
import { getUserList } from '@/api/userManage'
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
    authenticate: {
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
      authenticateRules: {
        ownerId: [{ required: true, trigger: 'blur', message: '请选择用户' }],
        treasureName: [{ required: true, trigger: 'blur', message: '请输入鉴品名称' }],
        treasureCode: [{ required: true, trigger: 'blur', message: '请输入鉴品编码' }],
        imgAddress: [{ required: true, trigger: 'blur', message: '请上传鉴品图片' }],
        typeId: [{ required: true, trigger: 'blur', message: '请选择分类' }],
        texture: [{ required: true, trigger: 'blur', message: '请输入质地' }],
        formCity: [{ required: true, trigger: 'blur', message: '请输入产地' }],
        weight: [{ required: true, trigger: 'blur', message: '请输入重量' }]
      },
      type: '',
      data: { authenticateId: null },
      fileList: [],
      head: { token: '' },
      userOptions: [],
      authOptions: []
    }
  },
  computed: {
    ...mapGetters([
      'token'
    ])
  },
  mounted() {
    getUserList('2').then(resp => {
      if (resp.code === 1) {
        this.userOptions = resp.data
      }
    })
    getUserList('1').then(resp => {
      if (resp.code === 1) {
        this.authOptions = resp.data
      }
    })
  },
  methods: {
    changeType() {
      if (this.authenticate.typeId) {
        getById(this.authenticate.typeId).then(resp => {
          this.authenticate.typeName = resp.data.typeName
        })
      }
    },
    userChange(value) {
      this.userOptions.forEach((item) => {
        if (item.userId === value) {
          this.authenticate.ownerName = item.userName
        }
      })
    },
    authChange(value) {
      this.authOptions.forEach((item) => {
        if (item.userId === value) {
          this.authenticate.authUserName = item.userName
        }
      })
    },
    handleUpload() {
      if (this.fileList.length > 4 || this.authenticate.imgAddress.length > 4) {
        this.$message({
          message: '一个商品最多上传五张图片',
          type: 'info'
        })
        return false
      }
      this.head.token = this.token
    },
    open() {
      if (this.authenticate.authenticateId) {
        this.fileList = JSON.parse(this.authenticate.imgAddress)
        this.authenticate.imgAddress = JSON.parse(this.authenticate.imgAddress)
      }
    },
    handleSuccess(response) {
      if (response.code === 1) {
        this.authenticate.imgAddress.push(response.data)
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
        this.authenticate.imgAddress = list
      } else {
        this.authenticate.imgAddress = []
      }
    },
    handleError() {
      this.$message({
        message: '上传失败！',
        type: 'error'
      })
    },
    onCancel() {
      if (this.$refs['authenticate']) {
        this.$refs['authenticate'].clearValidate()
      }
      this.$emit('update:dialogFormVisible', false)
      this.fileList = []
    },
    // 提交
    onSubmit() {
      this.$refs['authenticate'].validate((valid) => {
        if (valid) {
          this.authenticate.imgAddress = JSON.stringify(this.authenticate.imgAddress)
          if (!this.authenticate.authenticateId) {
            add(this.authenticate).then(resp => {
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
            update(this.authenticate).then((result) => {
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

