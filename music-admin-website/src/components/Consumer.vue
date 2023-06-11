<template>
    <div class="table">
        <div class="container" style="margin-bottom: 15px;">
            <div class="handle-box">
                <el-input v-model="search" size="small" placeholder="请输入用户账号" class="search-input"
                    prefix-icon="el-icon-search" />
                <el-button type="primary" size="mini" icon="el-icon-plus" @click="dialogVisible = true">添加用户</el-button>
                <el-button type="danger" size="mini" icon="el-icon-delete" @click="deleteSelected">批量删除</el-button>
                <el-button type="success" size="mini" icon="el-icon-refresh-right" @click="handleChangePassword">重置密码</el-button>
            </div>
        </div>
        <el-table ref="multipleTable" :data="data" tooltip-effect="dark" style="width: 100%" border
            @selection-change="handleSelectionChange">
            <el-table-column type="selection">
            </el-table-column>
            <el-table-column label="用户图片" width="110" align="center">
                <template slot-scope="scope">
                    <el-upload :action="updateAvatar(scope.row.id)" :on-success="handleSuccess"
                        :before-upload="handleBeforeUpload">
                        <div class="singer-avatar">
                            <img :src="getBaseUrl(scope.row.avatar)" :alt="scope.row.name" style="width:100%">
                        </div>
                    </el-upload>
                </template>
            </el-table-column>
            <el-table-column prop="nickName" label="昵称" align="center"></el-table-column>
            <el-table-column prop="sex" label="性别" width="50px" align="center">
                <template slot-scope="scope">
                    {{ transSex(scope.row.sex) }}
                </template>
            </el-table-column>
            <el-table-column prop="phone" label="手机号码" align="center"></el-table-column>
            <el-table-column prop="email" label="邮箱" align="center"></el-table-column>
            <el-table-column prop="location" label="地址" align="center"></el-table-column>
            <el-table-column prop="birthday" label="生日" align="center">
                <template slot-scope="scope">
                    {{ transBirth(scope.row.birthday) }}
                </template>
            </el-table-column>
            <el-table-column prop="introduction" label="个性签名">
                <template slot-scope="scope">
                    <p style="height:80px;overflow:auto">{{ scope.row.introduction }}</p>
                </template>
            </el-table-column>
            <el-table-column label="用户收藏" align="center">
                <template slot-scope="scope">
                    <el-button size="mini" @click="getFavoriteDetails(scope.row.id)">详情</el-button>
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
        <el-dialog title="添加用户" :visible.sync="dialogVisible" width="600px" center>
            <el-form :model="consumerForm" ref="consumerForm" label-width="80px" :rules="rules">
                <el-form-item prop="nickName" label="昵称" size="mini">
                    <el-input v-model="consumerForm.nickName" placeholder="昵称"></el-input>
                </el-form-item>
                <el-form-item label="性别">
                    <el-radio-group v-model="consumerForm.sex">
                        <el-radio :label="0">男</el-radio>
                        <el-radio :label="1">女</el-radio>
                        <el-radio :label="3">保密</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item prop="birthday" label="生日" size="mini">
                    <el-date-picker type="date" value-format="yyyy-MM-dd" placeholder="请选择日期"
                        v-model="consumerForm.birthday" style="width:100%"></el-date-picker>
                </el-form-item>
                <el-form-item prop="phone" label="手机号码" size="mini">
                    <el-input v-model="consumerForm.phone" placeholder="手机号码"></el-input>
                </el-form-item>
                <el-form-item prop="email" label="邮箱" size="mini">
                    <el-input v-model="consumerForm.email" placeholder="邮箱"></el-input>
                </el-form-item>
                <el-form-item prop="location" label="地区" size="mini">
                    <el-input v-model="consumerForm.location" placeholder="地区"></el-input>
                </el-form-item>
                <el-form-item prop="introduction" label="个性签名" size="mini">
                    <el-input v-model="consumerForm.introduction" placeholder="个性签名" type="textarea" :rows="5"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer">
                <el-button size="mini" @click="dialogVisible = false">取消</el-button>
                <el-button size="mini" @click="newSinger">确定</el-button>
            </span>
        </el-dialog>
        <!--修改歌手-->
        <el-dialog title="修改用户" :visible.sync="dialogEdit" width="600px" center>
            <el-form :model="editForm" ref="editForm" label-width="80px" :rules="rules">
                <el-form-item prop="nickName" label="昵称" size="mini">
                    <el-input v-model="editForm.nickName" placeholder="昵称"></el-input>
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
                <el-form-item prop="phone" label="手机号码" size="mini">
                    <el-input v-model="editForm.phone" placeholder="手机号码"></el-input>
                </el-form-item>
                <el-form-item prop="email" label="邮箱" size="mini">
                    <el-input v-model="editForm.email" placeholder="邮箱"></el-input>
                </el-form-item>
                <el-form-item prop="location" label="地区" size="mini">
                    <el-input v-model="editForm.location" placeholder="地区"></el-input>
                </el-form-item>
                <el-form-item prop="introduction" label="个性签名" size="mini">
                    <el-input v-model="editForm.introduction" placeholder="个性签名" type="textarea" :rows="5"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer">
                <el-button size="mini" @click="dialogEdit = false">取消</el-button>
                <el-button size="mini" @click="eidtConform">确定</el-button>
            </span>
        </el-dialog>
        <!--删除歌手-->
        <el-dialog title="删除用户" :visible.sync="dialogDel" width="300px" center>
            <div align="center">确定要删除吗?</div>
            <span slot="footer">
                <el-button size="mini" @click="dialogDel = false">取消</el-button>
                <el-button size="mini" @click="handleDel">确定</el-button>
            </span>
        </el-dialog>
        <!-- 重置密码 -->
        <el-dialog title="重置密码" :visible.sync="dialogPassWord" width="400px" center>
            <el-form>
                <el-form-item>
                    <el-input type="password" v-model="password" placeholder="请输入新密码"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer">
                <el-button size="mini" @click="dialogPassWord = false">取消</el-button>
                <el-button size="mini" @click="ConfirmChangePassword">确定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import { addconsumer, getAllConsumer, updateConsumerInfo, deleteConsumerInfo, changePasswordByAccount } from '@/api/http'
import { mixin } from '@/mixins/index'

export default {
    mixins: [mixin],
    data() {
        return {
            dialogVisible: false,
            dialogEdit: false,
            dialogDel: false,
            dialogPassWord:false,
            consumerForm: {
                nickName:'',
                sex: 3,
                birthday: '',
                avatar: '',
                location: '',
                introduction: '',
                phone:'',
                email:''
            },
            editForm: {
                id:'',
                nickName: '',
                sex: '',
                birthday: '',
                location: '',
                introduction: '',
                phone: '',
                email: ''
            },
            tableData: [],
            searchDate: [],
            search: '',
            pageSize: 4,
            currentPage: 1,
            idx: -1,
            selected: [], // 选择了哪些
            password:'',
            rules: {
                email: [
                    { required: true, message: '邮箱不能为空', trigger: 'blur' },
                    { type:'email',message: '请输入正确的邮箱地址',trigger:['blur','change'] }
                ],
                phone: [
                    { required: true, message: '请输入手机号', trigger: 'blur' },
                    {
                        pattern: /^((0\d{2,3}-\d{7,8})|(1[3584]\d{9}))$/,
                        message: '请输入正确的手机号码',
                    },
                ],
                birthday: [
                    { required: true, message: '请输入生日', trigger: 'blur' }
                ],
                location: [
                    { required: true, message: '请输入地区', trigger: 'blur' }
                ],
                nickName: [
                    { required: true, message: '请输入昵称', trigger: 'blur' }
                ]
            }
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
                    if (item.account.includes(this.search)) {
                        this.tableData.push(item);
                    }
                }
            }
        }
    },
    methods: {
        selectAll() {
            this.tableData = [];
            getAllConsumer().then((res) => {
                if (res.code == 200) {
                    this.tableData = res.data;
                    this.searchDate = res.data;
                }
            });
        },
        newSinger() {
            this.$refs['consumerForm'].validate(valid =>{
                if(valid){
                    addconsumer(this.consumerForm).then((res) => {
                        if (res.code == 200) {
                            this.selectAll();
                            this.$message({
                                message: "添加歌手成功啦",
                                type: "success",
                                showClose: true,
                            });
                        } else {
                            this.$message({
                                message: "添加歌手失败",
                                type: "error",
                                showClose: true,
                            });
                        }
                    }).catch((error) => {
                        console.log(error)
                    });
                    this.consumerForm = [];
                    this.dialogVisible = false
                }
            });
        },
        //更新歌手头像
        updateAvatar(id) {
            return `${this.$store.state.HOST}/consumer/update/avatar?id=${id}`;
        },
        // 获取当前页
        handleCurrentChange(current) {
            this.currentPage = current;
        },
        handleEdit(index, row) {
            this.dialogEdit = true;
            this.editForm = {
                id: row.id,
                nickName: row.nickName,
                sex: row.sex,
                birthday: row.birthday,
                location: row.location,
                introduction: row.introduction,
                phone: row.phone,
                email: row.email
            }
        },
        handleDel() {
            deleteConsumerInfo(this.idx).then((res) => {
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
        eidtConform() {
            this.$refs['editForm'].validate(valid => {
                if(valid){
                    updateConsumerInfo(this.editForm).then((res) => {
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
                }
            });
        },
        handleChangePassword(){
            // 如果没选择 让用户先选择
            if(this.selected.length == 0){
                this.$message({
                    message: "请先选择要重置的用户",
                    type: "error",
                    showClose: true,
                });
                return false;
            }
            this.dialogPassWord = true; // 选择用户后,打开修改密码弹窗
            return true;
        },
        ConfirmChangePassword(){
            let len = this.selected.length;
            if(len >= 1){
                for(let i=0;i<len;i++){
                    let params = {
                        account:this.selected[i].account,
                        password:this.password
                    }
                    changePasswordByAccount(params).then((res)=>{
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
                    }).catch((error)=>{
                        console.log(error);
                    })
                }
            }
            this.password = '';
            this.dialogPassWord = false;
        },
        songList(id, name) { // 歌曲管理
            this.$router.push({ path: `/song`, query: { id, name } });
        },
        getFavoriteDetails(id){ // 用户收藏详情
            this.$router.push({path:'/favorite-list',query:{id}});
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

.singer-avatar {
    width: 100%;
    height: 65px;
    border-radius: 6px;
    margin-bottom: 5px;
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
</style>