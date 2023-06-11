<template>
    <div class="table">
        <div class="container" style="margin-bottom: 15px;">
            <div class="handle-box">
                <el-input v-model="search" size="small" placeholder="请输入歌单名称" class="search-input"
                    prefix-icon="el-icon-search" />
                <el-button type="primary" size="mini" icon="el-icon-plus" @click="dialogVisible = true">添加歌单</el-button>
                <el-button type="danger" size="mini" icon="el-icon-delete" @click="deleteSelected">批量删除</el-button>
            </div>
        </div>
        <el-table ref="multipleTable" :data="data" tooltip-effect="dark" style="width: 100%" height="500px" fit border
            @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="45">
            </el-table-column>
            <el-table-column label="歌单图片" width="110" align="center">
                <template slot-scope="scope">
                    <el-upload :action="updateSongListPic(scope.row.id)" :on-success="handleSuccess" :show-file-list="false"
                        :before-upload="handleBeforeUpload" :multiple="false">
                        <div class="songlit-avatar">
                            <img :src="getBaseUrl(scope.row.pic)" :alt="scope.row.pic" style="width:100%">
                        </div>
                    </el-upload>
                </template>
            </el-table-column>
            <el-table-column prop="title" label="歌单名称" align="center"></el-table-column>
            <el-table-column label="歌单简介" align="center" width="400">
                <template slot-scope="scope">
                    <p style="height:80px;overflow:auto">{{scope.row.introduction}}</p>
                </template>
            </el-table-column>
            <el-table-column prop="style" label="歌单风格" width="190">
                <template slot-scope="scope">
                    <el-tag class="tag" type="success" effect="light"
                     v-for="(style,index) in handleStyle(scope.row.style)"
                     :key="index">{{style}}
                    </el-tag>
                </template>
            </el-table-column>
            <el-table-column label="歌曲管理" width="110" align="center">
                <template slot-scope="scope">
                    <el-button size="mini" @click="songListEdit(scope.row.id)">歌单详情</el-button>
                </template>
            </el-table-column>
            <el-table-column label="评论" align="center">
                <template slot-scope="scope">
                    <el-button size="mini" @click="getComment(scope.row.id)">评论详情</el-button>
                </template>
            </el-table-column>
            <el-table-column label="操作" align="center" width="185">
                <template slot-scope="scope">
                    <el-button size="mini" icon="el-icon-edit" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                    <el-button size="mini" icon="el-icon-delete" type="danger" @click="handleDelete(scope.row.id)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <!--分页-->
        <div class="pageHelper">
            <el-pagination background layout="prev, pager, next" :current-page="currentPage" :page-size="pageSize"
                :total="tableData.length" @current-change="handleCurrentChange">
            </el-pagination>
        </div>
        <!-- 添加歌单 -->
        <el-dialog title="添加歌单" :visible.sync="dialogVisible" width="600px" center>
            <el-form v-model="songListForm" ref="songForm" label-width="80px" id="new">
                <el-form-item prop="title" label="歌单名称" size="mini">
                    <el-input v-model="songListForm.title" placeholder="歌单名称"></el-input>
                </el-form-item>
                <el-form-item prop="style" label="歌单风格" size="mini">
                    <el-input v-model="songListForm.style" placeholder="歌单风格"></el-input>
                </el-form-item>
                <el-form-item prop="introduction" label="歌单简介" size="mini">
                    <el-input v-model="songListForm.introduction" placeholder="歌单简介" type="textarea" :rows="5"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer">
                <el-button size="mini" @click="dialogVisible = false">取消</el-button>
                <el-button size="mini" @click="newSongLit">确定</el-button>
            </span>
        </el-dialog>
        <!--修改歌曲-->
        <el-dialog title="修改歌单" :visible.sync="dialogEdit" width="600px" center>
            <el-form :model="editSongListForm" ref="songForm" label-width="80px">
                <el-form-item prop="title" label="歌单名称" size="mini">
                    <el-input v-model="editSongListForm.title" placeholder="歌单名称"></el-input>
                </el-form-item>
                <el-form-item prop="style" label="歌单风格" size="mini">
                    <el-input v-model="editSongListForm.style" placeholder="歌单风格"></el-input>
                </el-form-item>
                <el-form-item prop="introduction" label="歌单简介" size="mini">
                    <el-input v-model="editSongListForm.introduction" type="textarea" :rows="5" placeholder="歌单简介"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer">
                <el-button size="mini" @click="dialogEdit = false">取消</el-button>
                <el-button size="mini" @click="eidtConform">确定</el-button>
            </span>
        </el-dialog>
        <!--删除歌手-->
        <el-dialog title="删除歌单" :visible.sync="dialogDel" width="300px" center>
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
import { addSongList, getAllSongList, updateSongList, deleteSongList } from '@/api/http'

export default {
    mixins: [mixin],
    data() {
        return {
            dialogVisible: false,
            dialogEdit: false,
            dialogDel: false,
            songListForm: {
                title: '',
                pic: '',
                style: '',
                introduction: ''
            },
            editSongListForm: {
                id:0,
                title: '',
                pic: '',
                style: '',
                introduction: ''
            },
            tableData: [],
            searchDate: [],
            search: '',
            pageSize: 4,
            currentPage: 1,
            idx: -1,
            selected: [], // 选择了哪些
            fileList: [], // 文件上传列表
        }
    },
    created() {
        this.selectAll();
    },
    computed: {
        // 计算当前搜索结果中的数据
        data() {
            return this.tableData.slice((this.currentPage - 1) * this.pageSize, this.currentPage * this.pageSize);
        }
    },
    watch: {
        search() {
            if (this.search == '') {
                this.tableData = this.searchDate;
            } else {
                this.tableData = [];
                for (let item of this.searchDate) {
                    if (item.title.includes(this.search)) {
                        this.tableData.push(item);
                    }
                }
            }
        }
    },
    methods: {
        selectAll() {
            this.tableData = [];
            getAllSongList().then((res) => {
                if (res.code == 200) {
                    this.tableData = res.data;
                    this.searchDate = res.data;
                    this.currentPage = 1;
                }
            });
        },
        newSongLit() {
            // 文件上传同时发送歌曲信息
            let formdata = new FormData();
            formdata.append("title", this.songListForm.title);
            formdata.append("pic", 'images/songListPic/list.png');
            formdata.append("style", this.songListForm.style);
            formdata.append("introduction", this.songListForm.introduction);
            // // 发送数据
            addSongList(formdata).then((res) => {
                if (res.code == 200) {
                    this.selectAll(); // 重新查询数据
                    this.$message({
                        message: "歌单添加成功啦",
                        type: "success",
                        showClose: true,
                    });
                    this.songListForm = [];
                    this.fileList = [];
                    this.dialogVisible = false;
                } else {
                    this.$message({
                        message: "歌单添加失败",
                        type: "error",
                        showClose: true,
                    });
                }
            }).catch((error) => {
                console.log(error);
            });
        },
        eidtConform() {
            updateSongList(this.editSongListForm).then((res) => {
                if (res.code == 200) {
                    this.selectAll();
                    this.$message({
                        message: "修改成功啦",
                        type: "success",
                        showClose: true,
                    });
                } else {
                    this.$message({
                        message: "修改失败",
                        type: "error",
                        showClose: true,
                    });
                }
            }).catch((error) => {
                console.log(error)
            })
            this.dialogEdit = false
        },
        handleDel() {
            // 删除对应歌单
            deleteSongList(this.idx).then((res) => {
                if (res.code == 200) {
                    this.handlePagination();
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
        },
        //更新歌手头像
        updateSongListPic(id) {
            return `${this.$store.state.HOST}/songList/upload?id=${id}`;
        },
        // 获取当前页
        handleCurrentChange(current) {
            this.currentPage = current;
        },
        handleEdit(index, row) {
            this.dialogEdit = true;
            this.editSongListForm = {
                id:row.id,
                title: row.title,
                style: row.style,
                introduction: row.introduction
            }
        },
        handleChange(file, fileList) {
            this.fileList = fileList.slice(-3);
        },
        handleStyle(style){
            let sty = style.split("、");
            return sty;
        },
        songListEdit(id){
            this.$router.push({ path: `/listsong`, query: { id } });
        },
        getComment(id){ // 获取用户对该歌单的评论列表
            this.$router.push({ path: '/comment-list', query: { id } });
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
    width: 100%;
    margin-bottom: 20px;
}

.songlit-avatar {
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

.tag{
    margin: 0 5px;
}
</style>