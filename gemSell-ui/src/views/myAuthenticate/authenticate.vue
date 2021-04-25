<template>
  <div class="app-container">
    <el-dialog
      title="鉴定商品"
      width="60%"
      :visible.sync="dialogFormVisible"
      @open="open()"
      @before-close="onCancel()"
    >
      <el-form ref="authenticate" :model="authenticate" label-width="120px" :rules="authenticateRules">
        <el-row>
          <el-col :span="12">
            <el-form-item label="鉴品分类" prop="typeId">
              <treeselect v-model="authenticate.typeId" :options="options" style="width: 240px" disabled />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="质地" prop="texture">
              <el-input v-model="authenticate.texture" placeholder="请输入" disabled />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="重量" prop="weight">
              <el-input v-model="authenticate.weight" disabled type="number" :min="0" :step="0.1" placeholder="请输入" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="产地" prop="formCity">
              <el-input v-model="authenticate.formCity" disabled placeholder="请输入" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item
          prop="imgAddress"
          label="鉴品图片"
        >
          <el-upload
            disabled
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
import { update } from '@/api/authenticate'
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
        result: [{ required: true, trigger: 'blur', message: '请输入鉴定结果' }]
      },
      type: '',
      data: { authenticateId: null },
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
    open() {
      if (this.authenticate.authenticateId) {
        this.fileList = JSON.parse(this.authenticate.imgAddress)
        this.authenticate.imgAddress = JSON.parse(this.authenticate.imgAddress)
      }
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
          this.authenticate.status = '1'
          this.authenticate.successTime = this.Format(new Date())
          update(this.authenticate).then((result) => {
            if (result.code === 1) {
              this.$message({
                message: '鉴定成功',
                type: 'success'
              })
              this.$emit('update:dialogFormVisible', false)
              this.$emit('getList')
            }
          })
        }
      })
    },
    Format(date) {
      let month = '' + (date.getMonth() + 1)
      let day = '' + date.getDate()
      const year = date.getFullYear()
      let minute = ('0' + date.getMinutes()).slice(-2)
      let hour = ('0' + date.getHours()).slice(-2)
      const seconds = ('0' + date.getSeconds()).slice(-2)

      if (minute.length < 2) minute = '0' + minute
      if (hour.length < 2) hour = '0' + hour
      if (month.length < 2) month = '0' + month
      if (day.length < 2) day = '0' + day

      return [year, month, day].join('-') + ' ' + [hour, minute, seconds].join(':')
    }
  }
}
</script>

<style scoped>
.line{
  text-align: center;
}
</style>

