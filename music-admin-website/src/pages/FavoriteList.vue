<template>
    <div class="table">
        <div class="crumbs">
            <i class="el-icon-tickets">用户收藏详情</i>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-input v-model="search" size="small" placeholder="请输入关键字" class="search-input"
                    prefix-icon="el-icon-search" />
                <el-button type="danger" size="mini" icon="el-icon-delete" @click="deleteSelected">批量删除</el-button>
            </div>
        </div>
        <el-table ref="multipleTable" :data="tableData" tooltip-effect="dark" style="width: 100%" height="500px" fit
            border @selection-change="handleSelectionChange">
            <el-table-column type="selection"></el-table-column>
            <el-table-column prop="name" label="歌名-歌手" align="center"></el-table-column>
            <el-table-column label="操作" width="150px" align="center">
                <template slot-scope="scope">
                    <el-button size="mini" icon="el-icon-delete" type="danger" @click="handleDelete(scope.row.id)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <!--删除歌曲-->
        <el-dialog title="删除收藏" :visible.sync="dialogDel" width="300px" center>
            <div align="center">确定要删除吗?</div>
            <span slot="footer">
                <el-button size="mini" @click="dialogDel = false">取消</el-button>
                <el-button size="mini" @click="handleDel">确定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import { mixin } from '@/mixins/index'
import { getSongById, getConsumerFavorite,removeConsumerFavorite } from '@/api/http'

export default {
    name:'favorite-list',
    mixins: [mixin],
    data() {
        return {
            dialogDel: false,
            tableData: [],
            searchDate: [],
            search: '',
            idx: -1,
            selected: [], // 选择了哪些
            consumerId:''   // 用户id
        }
    },
    created() {
        this.consumerId = this.$route.query.id;
        this.selectAll();
    },
    watch: {
        search() {
            if (this.search == '') {
                this.tableData = this.searchDate;
            } else {
                this.tableData = [];
                for (let item of this.searchDate) {
                    if (item.name.includes(this.search)) {
                        this.tableData.push(item);
                    }
                }
            }
        }
    },
    methods: {
        // 查询用户收藏的所有歌曲
        selectAll() {
            this.tableData = [];
            getConsumerFavorite(this.consumerId).then((res) => {
                if (res.data == null){
                    this.$message({
                        message: "该用户暂时还没有收藏任何歌曲哟",
                        showClose: true,
                    });
                }else{
                    if (res.code == 200) {
                        for (let item of res.data) {
                            this.selectSongById(item.songId);
                        }
                    }
                }
            });
        },
        selectSongById(id){ // 根据 id 查询歌曲
            getSongById(id).then((res)=>{
                this.tableData.push(res.data);
                this.searchDate.push(res.data);
            }).catch((error)=>{
                console.log(error);
            });
        },
        handleDel() {
            removeConsumerFavorite(this.consumerId,this.idx).then((res) => {
                if (res.code == 200) {
                    this.selectAll(); // 重新查询数据
                    this.$message({
                        message: "删除成功啦",
                        type: "success",
                        showClose: true,
                    });
                } else {
                    this.$message({
                        message: "删除失败",
                        type: "error",
                        showClose: true,
                    });
                }
                this.dialogDel = false;
            });
        }
    }
}
</script>

<style scoped>
.container {
    padding: 20px;
    background: #fff;
    border: 1px solid #ddd;
    border-radius: 5px;
}

.handle-box {
    margin-bottom: 20px;
}

.song-avatar {
    width: 90%;
    height: 58px;
    border-radius: 6px;
    overflow: hidden;
}

.search-input {
    width: 300px;
    display: inline-block;
    margin-right: 10px;
}

.pageHelper {
    display: flex;
    justify-content: center;
    margin-top: 8px;
}

.crumbs {
    margin-bottom: 20px;
}

.play {
    position: absolute;
    z-index: 100;
    width: 80px;
    height: 80px;
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
    top: 13px;
    right: -20px;
}

.icon {
    width: 1.5em;
    height: 1.5em;
    color: #707070;
    fill: currentColor;
    overflow: hidden;
}

.size-icon {
    font-size: 25px;
    color: #F6416C;
}

.item {
    margin: 4px;
}
</style>