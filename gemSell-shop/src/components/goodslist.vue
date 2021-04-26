<template>
<div>
    <div>
        <el-form ref="form" :model="form" :inline="true" label-min-width="80px" style="margin: 10px 10% 0">
            <el-form-item label="商品名称">
                <el-input v-model="form.goodName" clearable />
            </el-form-item>
            <el-form-item label="商品分类">
                <treeselect v-model="form.typeId" :options="options" style="width: 240px" />
            </el-form-item>
            <el-button type="primary" @click="getList()">查询</el-button>
        </el-form>
    </div>
    <div class="good-container" style="padding-bottom: 30px">
        <div class="goods-item" v-for="(item,index) in goodList" :key="index" @click="gotoDetail(item)">
               <el-image
                 style="width: 200px; height: 200px"
                :src="JSON.parse(item.imgAddress)[0].url"
                ></el-image>
                <div class="goods-item-content">
                    <strong class="goodPrice">
                        ￥{{item.price}}
                    </strong><br>
                    <div class="goods-title">
                        {{item.goodName}}
                    </div>
                    <el-tooltip class="item" effect="light" :content="item.introduce" placement="bottom">
                        <span>{{item.introduce}}</span>
                    </el-tooltip>
                </div>
        </div>
        <div>
        <el-pagination
            :current-page="pageIndex"
            :page-size="pageSize"
            :total="total"
            style="margin-top:10px;text-align: center"
            background
            layout="total, prev, pager, next, jumper"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
        />
        </div>
    </div>
</div>

</template>

<script>
// import the component
import Treeselect from '@riophae/vue-treeselect'
// import the styles
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import { getTypeSelect} from '@/api/type'
import { getGoodsListByCondition} from '@/api/goods'
export default {
    components: {
        Treeselect
    },
     data: function() {
        return {
            goodList: [],
            form: {},
            pageIndex: 1,
            pageSize: 10,
            total: 0,
            options: []
        };
    },
    mounted() {
        var data= {
            status: 1,
            typeId: this.$route.query.typeId
        }
        this.form.typeId = data.typeId
        getGoodsListByCondition(this.form, this.pageIndex, this.pageSize)
        .then(response => {
            this.goodList = response.data.list;
            this.total = response.data.total
        });
        getTypeSelect()
        .then(response => {
            this.options = response.data;
        })
    },
    methods: {
        handleSizeChange(val) {
            this.pagesize = val
            this.getList()
        },
        handleCurrentChange(val) {
            this.pageIndex = val
            this.getList()
        },
        getList(){
            getGoodsListByCondition(this.form, this.pageIndex, this.pageSize).then(resp => {
            this.goodList = resp.data.list
            this.total = resp.data.total
            })
        },
        gotoDetail(row){
             this.$router.push({
                path:'/detail',
                query: {
                    id: row.goodId
                }
            })
        }
    }
}
</script>

<style scoped>
.good-container {
    margin: 30px 10%;
}
.goods-item {
    margin-right: 20px;
    width: 220px;
    display: inline-block;
    background: #fff;
    border: 1px solid #ccc;
    padding: 38px 9px 10px;
    transition: border-color .1s ease;
}
.goodPrice {
        margin-right: 10px;
        color: #e4393c;
        font-size: 20px;
    }
.goods-title {
    height: 20px;
    line-height: 20px;
    cursor: pointer;
    margin-left: 5px;
    margin-bottom: 8px;
}
.goods-title:hover {
    color: #d70029;
}
.item {
    overflow: hidden;
    text-overflow: ellipsis;
    -o-text-overflow: ellipsis;
    white-space: nowrap;
    width: 195px;
    height: 24px;
    display: block;
    color: #999;
    margin-left: 5px;
}

</style>
