<template>
  <div class="container">
    <el-form ref="form" :model="form" :inline="true" label-width="80px">
      <!-- <el-form-item label="分类名称" prop="typeName">
        <el-input v-model="form.typeName" clearable />
      </el-form-item>
      <el-button type="primary" @click="getList()">查询</el-button>
      <el-button @click="resetData()">重置</el-button> -->
      <el-button type="primary" @click="add()">添加</el-button>
    </el-form>

    <el-table
      :data="tableData"
      style="width: 100%;margin: 20px 0;"
      row-key="typeId"
      border
      default-expand-all
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
    >
      <el-table-column
        prop="typeId"
        label="编号"
      />
      <el-table-column
        prop="typeName"
        label="分类名称"
      />
      <el-table-column label="操作" fixed="right">
        <template slot-scope="scope">
          <el-button v-if="scope.row.parentId === 0" type="text" size="small" @click="add(scope.row)">增加</el-button>
          <el-button type="text" size="small" @click="handleUpdate(scope.row)">编辑</el-button>
          <el-button type="text" size="small" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div>
      <el-dialog :title="addOrupdate === 'add' ? '增加分类':'修改分类'" :visible.sync="dialogFormVisible" @close="cancale">
        <el-form ref="addForm" :model="entity" :rules="rules">
          <el-form-item label="分类名称" :label-width="formLabelWidth" prop="typeName">
            <el-input v-model="entity.typeName" autocomplete="off" />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="cancale()">取 消</el-button>
          <el-button type="primary" @click="submit()">确 定</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import { getListByCondition, add, update, deleteById } from '@/api/type'
export default {

  data() {
    return {
      filterText: '',
      tableData: [],
      defaultProps: {
        children: 'children',
        label: 'label'
      },
      form: {
        id: null,
        typeName: ''
      },
      options: [{
        value: 1,
        label: '正常'
      },
      {
        value: 0,
        label: '禁用'
      }],
      entity: {
        typeId: null,
        typeName: '',
        parentId: null
      },
      dialogFormVisible: false,
      formLabelWidth: '100px',
      rules: {
        typeName: [{ required: true, message: '请输入分类名称', trigger: 'blur' }]
      },
      addOrupdate: ''
    }
  },
  created() {
    this.getList()
  },
  methods: {
    resetEntity() {
      this.entity.typeId = null
      this.entity.typeName = ''
    },
    getList() {
      getListByCondition().then(resp => {
        this.tableData = resp.data
      })
    },
    resetData() {
      this.$refs['form'].resetFields()
      this.getList()
    },
    add(row) {
      if (row) {
        const { typeId } = row
        this.entity.parentId = typeId
      } else {
        this.entity.parentId = 0
      }
      this.dialogFormVisible = true
      this.addOrupdate = 'add'
    },
    handleUpdate(row) {
      this.dialogFormVisible = true
      this.addOrupdate = 'update'
      this.entity = { ...row }
    },
    submit() {
      var _this = this
      this.$refs['addForm'].validate((valid) => {
        if (valid) {
          if (_this.addOrupdate === 'add') {
            add(_this.entity).then((result) => {
              if (result.code === 1) {
                _this.$message({
                  message: '增加成功',
                  type: 'success'
                })
                _this.cancale()
                _this.getList()
              }
            })
          } else {
            update(_this.entity).then((result) => {
              if (result.code === 1) {
                _this.$message({
                  message: '修改成功',
                  type: 'success'
                })
                _this.cancale()
                _this.getList()
              }
            })
          }
        } else {
          return false
        }
      })
    },
    cancale() {
      this.addOrupdate = ''
      this.dialogFormVisible = false
      this.$refs['addForm'].clearValidate()
      this.resetEntity()
    },
    handleDelete(row) {
      this.$confirm('您确定要删除该条数据吗?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteById(row.typeId).then(response => {
          const res = response
          if (res.code === 1) {
            this.$message({
              message: '删除成功！',
              type: 'success'
            })
            this.getList()
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
