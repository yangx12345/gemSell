<template>
    <div class="good-container">
        <div class="goods-item" v-for="(item,index) in goodList" :key="index" @click="gotoDetail(item)">
               <el-image
                 style="width: 200px; height: 200px"
                :src="item.imgAddress[0].url"
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
    </div>
</template>

<script>
export default {
     data: function() {
        return {
            goodList: []
        };
    },
    mounted() {
        var data= {
            status: 1,
            typeId: this.$route.query.typeId
        }
        this.$axios
        .post("/gemsell-api/goods/getListByCondition?pageIndex="+1+"&pageSize="+10,data, 1,10)
        .then(response => {
            this.goodList = response.data.data.list;
        });
    },
    methods: {
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