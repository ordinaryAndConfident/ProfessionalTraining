<template>
    <div class="table">
        <div class="crumbs">
           <i class="el-icon-tickets">歌曲信息</i>
        </div>
        <div class="container" style="margin-bottom: 15px;">
            <div class="handle-box">
                <el-input v-model="search" size="small" placeholder="请输入歌曲名称" class="search-input"
                    prefix-icon="el-icon-search" />
                <el-button type="primary" size="mini" icon="el-icon-plus" @click="dialogVisible = true">添加歌曲</el-button>
                <el-button type="danger" size="mini" icon="el-icon-delete" @click="deleteSelected">批量删除</el-button>
            </div>
        </div>
        <el-table ref="multipleTable" :data="data" tooltip-effect="dark" style="width: 100%" height="500px" fit border
            @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="45">
            </el-table-column>
            <el-table-column label="歌曲图片" width="110" align="center">
                <template slot-scope="scope">
                    <el-upload :action="updateSongPic(scope.row.id)" :on-success="handleSuccess" :show-file-list="false"
                        :before-upload="handleBeforeUpload" :multiple="false">
                        <div class="song-avatar">
                            <img :src="getBaseUrl(scope.row.picture)" :alt="scope.row.picture" style="width:100%">
                        </div>
                    </el-upload>
                </template>
            </el-table-column>
            <el-table-column label="歌名-歌手" width="180" align="center">
                <template slot-scope="scope">
                    {{scope.row.name}}
                    <div class="play" @click="setSongUrl(scope.row.url,scope.row.name)">
                        <div v-if="toggle == scope.row.name && playIcon">
                            <el-tooltip class="item" effect="dark" content="暂停" placement="right">
                                <div class="size-icon">
                                    <i class="el-icon-video-pause"></i>
                                </div>
                            </el-tooltip>
                        </div>
                        <div v-else>
                            <el-tooltip class="item" effect="dark" content="播放" placement="right">
                                <div class="size-icon">
                                    <i class="el-icon-video-play"></i>
                                </div>
                            </el-tooltip>
                        </div>
                    </div>
                </template>
            </el-table-column>
            <el-table-column prop="introduction" label="专辑" width="160" align="center"></el-table-column>
            <el-table-column label="歌词">
                <template slot-scope="scope">
                    <ul style="height:80px;overflow: auto;">
                        <li v-for="(item,index) in handleLyric(scope.row.lyric)" :key="index">
                            {{item}}
                        </li>
                    </ul>
                </template>
            </el-table-column>
            <el-table-column label="更新歌曲" width="115" align="center">
                <template slot-scope="scope">
                    <el-upload :action="updateSongUrl(scope.row.id)" :on-success="handleSuccess" :show-file-list="false"
                        :before-upload="handleBeforeSong" :multiple="false">
                        <el-button size="mini" icon="el-icon-upload2">更新歌曲</el-button>
                    </el-upload>
                </template>
            </el-table-column>
            <el-table-column label="操作" width="185px" align="center">
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
        <el-dialog title="添加歌曲" :visible.sync="dialogVisible" width="600px" center>
            <el-form v-model="songForm" ref="songForm" label-width="80px" id="new">
                <el-form-item prop="name" label="名称" size="mini">
                    <el-input v-model="songForm.name" placeholder="歌曲名称"></el-input>
                </el-form-item>
                <el-form-item prop="introduction" label="专辑" size="mini">
                    <el-input v-model="songForm.introduction" placeholder="专辑"></el-input>
                </el-form-item>
                <el-form-item prop="lyric" label="歌词" size="mini">
                    <el-input v-model="songForm.lyric" placeholder="歌词" type="textarea" :rows="5"></el-input>
                </el-form-item>
                <el-form-item label="歌曲上传" width="110">
                    <template slot-scope="scope">
                    <el-upload class="upload-demo" action="" :on-change="handleChange" accept="audio/*" :auto-upload="false"
                        :file-list="fileList" :on-success="handleSuccess" :before-upload="handleBeforeSong">
                        <el-button size="small" type="primary">点击上传</el-button>
                    </el-upload>
                    </template>
                </el-form-item>
            </el-form>
            <span slot="footer">
                <el-button size="mini" @click="dialogVisible = false">取消</el-button>
                <el-button size="mini" @click="newSong">确定</el-button>
            </span>
        </el-dialog>
        <!--修改歌曲-->
        <el-dialog title="修改歌曲" :visible.sync="dialogEdit" width="600px" center>
            <el-form :model="editForm" ref="songForm" label-width="80px">
                <el-form-item prop="name" label="歌手-歌名" size="mini">
                    <el-input v-model="editForm.name" placeholder="歌手-歌曲"></el-input>
                </el-form-item>
                <el-form-item prop="introduction" label="专辑" size="mini">
                    <el-input v-model="editForm.introduction" placeholder="专辑"></el-input>
                </el-form-item>
                <el-form-item prop="lyric" label="歌词" size="mini">
                    <el-input v-model="editForm.lyric" placeholder="歌词" type="textarea" :rows="5"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer">
                <el-button size="mini" @click="dialogEdit = false">取消</el-button>
                <el-button size="mini" @click="eidtConform">确定</el-button>
            </span>
        </el-dialog>
        <!--删除歌手-->
        <el-dialog title="删除歌曲" :visible.sync="dialogDel" width="300px" center>
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
import { mapGetters } from 'vuex'
import '@/assets/js/iconfont.js'
import { addSong, getSongBySingerId, updateSongInfo, deleteSongById } from '@/api/http'

export default {
    mixins: [mixin],
    data() {
        return {
            singerId:-1,
            singerName:'',
            dialogVisible: false,
            dialogEdit: false,
            dialogDel: false,
            songForm: {
                name: '',
                singerName:'',
                singerId: '',
                lyric: '',
                introduction: ''
            },
            editForm: {
                id:'',
                name: '',
                introduction: '',
                lyric:''
            },
            tableData: [],
            searchDate: [],
            search: '',
            pageSize: 4,
            currentPage: 1,
            idx: -1,
            selected: [], // 选择了哪些
            fileList:[], // 文件上传列表
            toggle:'', // 播放歌曲名称
            playIcon:false
        }
    },
    created() {
        this.singerId = this.$route.query.id;
        this.singerName = this.$route.query.name;
        this.selectAll();
    },
    destroyed() {
        this.$store.commit('setIsPlay', false); // 离开页面关闭歌曲
    },
    computed: {
        // 计算当前搜索结果中的数据
        data() {
            return this.tableData.slice((this.currentPage - 1) * this.pageSize, this.currentPage * this.pageSize);
        },
         ...mapGetters([
           'isPlay'
        ])
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
        selectAll() {
            this.tableData = [];
            getSongBySingerId(this.singerId).then((res) => {
                if (res.code == 200) {
                    this.tableData = res.data;
                    this.searchDate = res.data;
                    this.currentPage = 1;
                }
            });
        },
        newSong() {
            // 文件上传同时发送歌曲信息
            let formdata = new FormData();
            if(!this.songForm.lyric){
                this.songForm.lyric = "[00:00:00] 暂无歌词";
            }
            this.songForm.singerName = this.singerName;
            formdata.append("file",this.fileList[0].raw);
            formdata.append("name",this.songForm.name +"-"+this.songForm.singerName);
            formdata.append("singerId",this.singerId);
            formdata.append("lyric", this.songForm.lyric);
            formdata.append("introduction", this.songForm.introduction);
            // 发送数据
            addSong(formdata).then((res)=>{
                if (res.code == 200) {
                    this.selectAll(); // 重新查询数据
                    this.$message({
                        message: "歌曲添加成功啦",
                        type: "success",
                        showClose: true,
                    });
                } else {
                    this.$message({
                        message: "歌曲添加失败",
                        type: "error",
                        showClose: true,
                    });
                }
            }).catch((error)=>{
                console.log(error);
            });
            this.songForm = [];
            this.fileList = [];
            this.dialogVisible = false;
        },
        eidtConform() {
            updateSongInfo(this.editForm).then((res) => {
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
            deleteSongById(this.idx).then((res) => {
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
        updateSongPic(id) {
            return `${this.$store.state.HOST}/song/upload?id=${id}`;
        },
        // 获取当前页
        handleCurrentChange(current) {
            this.currentPage = current;
        },
        handleEdit(index, row) {
            this.dialogEdit = true;
            this.editForm = {
                id: row.id,
                name: row.name,
                introduction: row.introduction,
                lyric: row.lyric
            }
        },
        handleChange(file, fileList) {
            this.fileList = fileList.slice(-3);
        },
        handleLyric(lyric){
            let lines = lyric.split("\n");
            let rules = /\[\d{2}:\d{2}.(\d{3}|d{2})\]/g;
            let res = [];
            for(let item of lines){
                let val = item.replace(rules,'');
                res.push(val);
            }
            return res;
        },
        updateSongUrl(id){
            return `${this.$store.state.HOST}/song/updateSongUrl?id=${id}`;
        },
        setSongUrl(url,name){ // 切歌
            this.toggle = name;
            this.playIcon = !this.playIcon;
            this.$store.commit('setUrl', this.$store.state.HOST +"/"+ url);
            if(this.isPlay){
                this.$store.commit('setIsPlay', false);
            }else{
                this.$store.commit('setIsPlay', true);
            }
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

.play{
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

.icon{
    width: 1.5em;
    height: 1.5em;
    color: #707070;
    fill: currentColor;
    overflow: hidden;
}

.size-icon{
    font-size: 25px;
    color: #F6416C;
}

.item {
    margin: 4px;
}
</style>