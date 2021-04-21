<template>
  <div class="app-container">
    <el-dialog
      :title="order.orderId? '编辑商品':'添加商品'"
      width="60%"
      :visible.sync="dialogFormVisible"
      @open="open()"
      @before-close="onCancel()"
    >
      <el-form ref="order" :model="order" label-width="120px" :rules="orderRules">
        <el-row>
          <el-col :span="12">
            <el-form-item label="用户" prop="userId">
              <el-select v-model="order.userId" placeholder="请选择用户" @change="userChange">
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
            <el-form-item label="商品" prop="goodId">
              <el-select v-model="order.goodId" placeholder="请选择商品" @change="goodChange">
                <el-option
                  v-for="item in goodsOptions"
                  :key="item.goodId"
                  :label="item.goodName"
                  :value="item.goodId"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="单价" prop="price">
              <el-input v-model="order.price" type="number" :min="0" readonly />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="数量" prop="num">
              <el-input v-model="order.num" type="number" :min="0" @change="BlurNumber" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="总价" prop="totalPrice">
              <el-input v-model="order.totalPrice" type="number" readonly="" :min="0" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="订单状态" prop="status">
              <el-select v-model="order.status" placeholder="请选择商品">
                <el-option label="待付款" value="0" />
                <el-option label="已付代发" value="1" />
                <el-option label="已发" value="2" />
                <el-option label="取消" value="3" />
                <el-option label="完成" value="4" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="onCancel()">取 消</el-button>
        <el-button type="primary" @click="onSubmit">{{ order.orderId?'保存':'添加' }}</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>
import { add, update } from '@/api/order'
import { getUserList } from '@/api/userManage'
import { getGoodsList } from '@/api/goods'
export default {
  props: {
    dialogFormVisible: {
      type: Boolean,
      default: false
    },
    order: {
      type: Object,
      default: null
    }
  },
  data() {
    return {
      orderRules: {
        userId: [{ required: true, trigger: 'blur', message: '请选择用户' }],
        goodId: [{ required: true, trigger: 'blur', message: '请选择商品' }],
        num: [{ required: true, trigger: 'blur', message: '请输入数量' }],
        totalPrice: [{ required: true, trigger: 'blur', message: '请输入总价' }],
        status: [{ required: true, trigger: 'blur', message: '请选择状态' }],
        price: [{ required: true, trigger: 'blur', message: '请输入售价' }]
      },
      userOptions: [],
      goodsOptions: [],
      topNumber: 0
    }
  },
  mounted() {
    getUserList().then(resp => {
      if (resp.code === 1) {
        this.userOptions = resp.data
      }
    })
    getGoodsList().then(resp => {
      if (resp.code === 1) {
        this.goodsOptions = resp.data
      }
    })
  },
  methods: {
    open() {
      this.goodsOptions.forEach((item) => {
        if (item.goodId === this.order.goodId) {
          this.topNumber = item.remainNumber
        }
      })
    },
    BlurNumber() {
      if (this.order.num > this.topNumber) {
        this.$message(
          { message: '数量不能大于商品剩余数量',
            type: 'warning' }
        )
        return false
      }
      this.order.totalPrice = this.order.num * this.order.price
    },
    userChange(value) {
      console.log(value)
      this.userOptions.forEach((item) => {
        if (item.userId === value) {
          this.order.userName = item.userName
        }
      })
    },
    goodChange(value) {
      this.goodsOptions.forEach((item) => {
        if (item.goodId === value) {
          this.order.goodName = item.goodName
          this.order.price = item.price
          this.topNumber = item.remainNumber
          this.order.num = item.remainNumber
          this.order.totalPrice = this.order.num * this.order.price
        }
      })
    },
    onCancel() {
      this.$refs['order'].clearValidate()
      this.$emit('update:dialogFormVisible', false)
    },
    // 提交
    onSubmit() {
      this.$refs['order'].validate((valid) => {
        if (valid) {
          this.order.remainNumber = this.order.totalNumber
          if (!this.order.orderId) {
            add(this.order).then(resp => {
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
            update(this.order).then((result) => {
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

