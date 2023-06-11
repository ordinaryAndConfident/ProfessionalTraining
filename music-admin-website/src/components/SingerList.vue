<template>
    <div class="table">
        <div class="container" style="margin-bottom: 15px;">
            <div class="handle-box">
                <el-input v-model="search" size="small" placeholder="请输入歌手名" class="search-input" prefix-icon="el-icon-search"/>
                <el-button type="primary" size="mini" icon="el-icon-plus" @click="dialogVisible = true">添加歌手</el-button>
                <el-button type="danger" size="mini" icon="el-icon-delete" @click="deleteSelected">批量删除</el-button>
            </div>
        </div>
        <el-table ref="multipleTable" :data="data" tooltip-effect="dark" border
        style="width: 100%" @selection-change="handleSelectionChange">
            <el-table-column type="selection">
            </el-table-column>
            <el-table-column label="歌手图片" width="110p" align="center">
                <template slot-scope="scope">
                    <el-upload :action="updateAvatar(scope.row.id)" :on-success="handleSuccess" :before-upload="handleBeforeUpload">
                        <div class="singer-avatar">
                            <img :src="getBaseUrl(scope.row.avatar)" :alt="scope.row.name" style="width:100%">
                        </div>
                    </el-upload>
                </template>
            </el-table-column>
            <el-table-column prop="name" label="歌手" align="center">
            </el-table-column>
            <el-table-column prop="address" label="性别" width="80" align="center">
                <template slot-scope="scope">
                    {{transSex(scope.row.sex)}}
                </template>
            </el-table-column>
            <el-table-column prop="birthday" label="生日" align="center">
                <template slot-scope="scope">
                    {{transBirth(scope.row.birthday)}}
                </template>
            </el-table-column>
            <el-table-column prop="location" label="地区" align="center"></el-table-column>
            <el-table-column prop="introduction" label="简介" width="400">
                <template slot-scope="scope">
                    <p style="height:80px;overflow:auto">{{scope.row.introduction}}</p>
                </template>
            </el-table-column>
            <el-table-column width="110" align="center" label="歌曲管理">
                <template slot-scope="scope">
                    <el-button size="mini" @click="songList(scope.row.id,scope.row.name)">歌曲管理</el-button>
                </template>
            </el-table-column>
            <el-table-column label="操作" width="185" align="center">
                <template slot-scope="scope">
                    <el-button size="mini" icon="el-icon-edit" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                    <el-button size="mini" icon="el-icon-delete" type="danger" @click="handleDelete(scope.row.id)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <!--分页-->
        <div class="pageHelper">
            <el-pagination 
            background layout="prev, pager, next" 
            :current-page="currentPage" 
            :page-size="pageSize" 
            :total="tableData.length" @current-change="handleCurrentChange">
        </el-pagination>
        </div>
        <el-dialog title="添加歌手" :visible.sync="dialogVisible" width="600px" center>
            <el-form v-model="singerForm" ref="singerForm" label-width="80px">
                <el-form-item prop="name" label="歌手名称" size="mini">
                    <el-input v-model="singerForm.name" placeholder="歌手名称"></el-input>
                </el-form-item>
                <el-form-item label="性别">
                    <el-radio-group v-model="singerForm.sex">
                        <el-radio :label="0">男</el-radio>
                        <el-radio :label="1">女</el-radio>
                        <el-radio :label="3">保密</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item prop="birthday" label="生日" size="mini">
                    <el-date-picker type="date" value-format="yyyy-MM-dd" placeholder="请选择日期" v-model="singerForm.birthday" style="width:100%"></el-date-picker>
                </el-form-item>
                <el-form-item prop="location" label="地区" size="mini">
                    <el-input v-model="singerForm.location" placeholder="地区"></el-input>
                </el-form-item>
                <el-form-item prop="introduction" label="简介" size="mini">
                    <el-input v-model="singerForm.introduction" placeholder="简介" type="textarea" :rows="5"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer">
                <el-button size="mini" @click="dialogVisible = false">取消</el-button>
                <el-button size="mini" @click="newSinger">确定</el-button>
            </span>
        </el-dialog>
        <!--修改歌手-->
        <el-dialog title="修改歌手" :visible.sync="dialogEdit" width="600px" center>
            <el-form :model="editForm" ref="singerForm" label-width="80px">
                <el-form-item prop="name" label="歌手名称" size="mini">
                    <el-input v-model="editForm.name" placeholder="歌手名称"></el-input>
                </el-form-item>
                <el-form-item label="性别">
                    <el-radio-group v-model="editForm.sex">
                        <el-radio :label="0">男</el-radio>
                        <el-radio :label="1">女</el-radio>
                        <el-radio :label="3">保密</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item prop="birthday" label="生日" size="mini">
                    <el-date-picker type="date" value-format="yyyy-MM-dd" placeholder="请选择日期" v-model="editForm.birthday"
                        style="width:100%"></el-date-picker>
                </el-form-item>
                <el-form-item prop="location" label="地区" size="mini">
                    <el-input v-model="editForm.location" placeholder="地区"></el-input>
                </el-form-item>
                <el-form-item prop="introduction" label="简介" size="mini">
                    <el-input v-model="editForm.introduction" placeholder="简介" type="textarea" :rows="5"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer">
                <el-button size="mini" @click="dialogEdit = false">取消</el-button>
                <el-button size="mini" @click="eidtConform">确定</el-button>
            </span>
        </el-dialog>
        <!--删除歌手-->
        <el-dialog title="删除歌手" :visible.sync="dialogDel" width="300px" center>
            <div align="center">确定要删除吗?</div>
            <span slot="footer">
                <el-button size="mini" @click="dialogDel = false">取消</el-button>
                <el-button size="mini" @click="handleDel">确定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import { addSinger, getAll, updateSinger, deleteSinger } from '@/api/http'
import { mixin } from '@/mixins/index'

export default {
    mixins: [mixin],
    data(){
        return{
            dialogVisible:false,
            dialogEdit: false, 
            dialogDel:false,
            singerForm:{
                name:'',
                sex:'',
                birthday:'',
                avatar:'images/singerPic/avatar.gif',
                location:'',
                introduction:''
            },
            editForm:{
                id:'',
                name:'',
                sex:'',
                location:'',
                introduction:'',
                birthday:''
            },
            tableData:[],
            searchDate:[],
            search:'',
            pageSize:4,
            currentPage:1,
            idx:-1,
            selected:[] // 选择了哪些
        }
    },
    created() {
        this.selectAll();
    },
    computed:{
        // 计算当前搜索结果中的数据
        data(){
            return this.tableData.slice((this.currentPage - 1) * this.pageSize , this.currentPage * this.pageSize);
        }
    },
    watch:{
        search(){
            if(this.search == ''){
                this.tableData = this.searchDate;
            }else{
                this.tableData = [];
                for(let item of this.searchDate){
                    if(item.name.includes(this.search)){
                        this.tableData.push(item);
                    }
                }
            }
        }
    },
    methods:{
        selectAll(){
            this.tableData = [];
            getAll().then((res)=>{
                if (res.code == 200) {
                    this.tableData = res.data;
                    this.searchDate = res.data;
                }
            });
        },
        newSinger(){
            addSinger(this.singerForm).then((res)=>{
                if(res.code == 200){
                    this.selectAll();
                    this.$message({
                        message: "添加歌手成功啦",
                        type: "success",
                        showClose: true,
                    });
                }else{
                    this.$message({
                        message: "添加歌手失败",
                        type: "error",
                        showClose: true,
                    });
                }
                this.singerForm = [];
                this.singerForm.avatar = 'images/singerPic/avatar.jpg'
            }).catch((error)=>{
                console.log(error)
            })
            this.dialogVisible = false
        },
        //更新歌手头像
        updateAvatar(id){
            return `${this.$store.state.HOST}/singer/upload?id=${id}`;
        },
        // 获取当前页
        handleCurrentChange(current){
            this.currentPage = current;
        },
        handleEdit(index, row){
            this.dialogEdit = true;
            this.editForm = {
                id: row.id,
                name: row.name,
                sex: row.sex,
                location: row.location,
                introduction: row.introduction,
                birthday: row.birthday
            }
        },
        handleDel(){
            deleteSinger(this.idx).then((res)=>{
                if(res.code == 200){
                    this.handlePagination();
                    this.selectAll(); // 重新查询数据
                    this.$message({
                        message: "删除成功啦",
                        type: "success",
                        showClose: true,
                    });
                }else{
                    this.$message({
                        message: "删除失败",
                        type: "error",
                        showClose: true,
                    });
                }
                this.dialogDel = false;
            });
        },
        eidtConform(){
            updateSinger(this.editForm).then((res)=>{
                if(res.code == 200){
                    this.selectAll();
                    this.$message({
                        message: "修改成功啦",
                        type: "success",
                        showClose: true,
                    });
                }else{
                    this.$message({
                        message: "修改失败",
                        type: "error",
                        showClose: true,
                    });
                }
            }).catch((error)=>{
                console.log(error)
            })
            this.dialogEdit = false
        },
        songList(id,name){ // 歌曲管理
            this.$router.push({path:`/song`,query:{id,name}});
        }
    }
}
</script>

<style scoped>
.container{
    padding: 20px;
    background: #fff;
    border: 1px solid #ddd;
    border-radius: 5px;
}

.handle-box{
    width: 100%;
    margin-bottom: 20px;
}

.singer-avatar{
    width: 100%;
    height: 65px;
    border-radius: 6px;
    margin-bottom: 5px;
    overflow: hidden;
}

.search-input{
    width: 300px;
    display: inline-block;
    margin-right: 10px;
}

.pageHelper{
    display: flex;
    justify-content: center;
    margin-top: 8px;
}
</style>