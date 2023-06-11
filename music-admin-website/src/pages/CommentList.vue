<template>
    <div class="table">
        <div class="container" style="margin-bottom: 15px;">
            <div class="handle-box">
                <el-input v-model="search" size="small" placeholder="请输入关键字" class="search-input"
                    prefix-icon="el-icon-search" />
                <el-button type="danger" size="mini" @click="deleteSelected">批量删除</el-button>
            </div>
        </div>
        <el-table ref="multipleTable" :data="tableData" tooltip-effect="dark" style="width: 100%" height="500px" fit
            border @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="45"></el-table-column>
            <el-table-column prop="nickName" label="用户昵称" align="center"></el-table-column>
            <el-table-column prop="content" label="评论内容" align="center"></el-table-column>
            <el-table-column prop="up" label="点赞人数" align="center"></el-table-column>
            <el-table-column label="操作" width="150px" align="center">
                <template slot-scope="scope">
                    <el-button size="mini" type="danger" @click="handleDelete(scope.row.id)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <!--删除歌曲-->
        <el-dialog title="删除评论" :visible.sync="dialogDel" width="300px" center>
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
import { selectAllCommentBySongListId, deleteCommentById, selectConsumerById } from '@/api/http'

export default {
    name: 'favorite-list',
    mixins: [mixin],
    data() {
        return {
            dialogDel: false,
            tableData: [],
            searchDate: [],
            search: '',
            idx: -1,
            selected: [], // 选择了哪些
            songListId: ''   // 用户id
        }
    },
    created() {
        this.songListId = this.$route.query.id;
        this.selectAll();
    },
    watch: {
        search() {
            if (this.search == '') {
                this.tableData = this.searchDate;
            } else {
                this.tableData = [];
                for (let item of this.searchDate) {
                    if (item.content.includes(this.search) || item.nickName.includes(this.search)) {
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
            selectAllCommentBySongListId(this.songListId).then((res) => {
                if (res.data == null) {
                    this.$message({
                        message: "暂时还没人有评论该歌单哟",
                        showClose: true,
                    });
                } else {
                    if (res.code == 200) {
                        for (let item of res.data) {
                            this.selectConsumerById(item.consumerId,item);
                        }
                    }
                }
            });
        },
        selectConsumerById(id,item) { // 根据 id 查询歌曲
            selectConsumerById(id).then((res) => {
                let temp = item;
                temp.nickName = res.data.nickName;
                this.tableData.push(temp);
                this.searchDate.push(temp);
            }).catch((error) => {
                console.log(error);
            });
        },
        handleDel() {
            deleteCommentById(this.idx).then((res) => {
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