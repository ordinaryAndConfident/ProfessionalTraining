<template>
    <div>
        <div class="container">
            <el-card shadow="always" class="card">
                <div slot="header" class="clearfix">
                    <h3>编辑个人信息</h3>
                </div>
                <el-form :model="infoForm" ref="infoForm" label-width="80px" size="normal" :rules="rules">
                    <el-form-item prop="account" label="账号">
                        <el-input v-model="infoForm.account" placeholder="账号" disabled></el-input>
                    </el-form-item>
                    <el-form-item prop="password" label="密码">
                        <el-input v-model="infoForm.password" :type="type" placeholder="请输入新密码 / (为空则表示不修改密码)">
                            <i slot="suffix" class="icon-style" :class="elIcon" autocomplete="auto" @click="flag = !flag" />
                        </el-input>
                    </el-form-item>
                    <el-form-item prop="nickName" label="昵称">
                        <el-input v-model="infoForm.nickName" placeholder="昵称"></el-input>
                    </el-form-item>
                    <el-form-item label="性别">
                        <el-radio-group v-model="infoForm.sex">
                            <el-radio :label="0">男</el-radio>
                            <el-radio :label="1">女</el-radio>
                            <el-radio :label="3">保密</el-radio>
                        </el-radio-group>
                    </el-form-item>
                    <el-form-item prop="phone" label="手机">
                        <el-input v-model="infoForm.phone" placeholder="手机号码"></el-input>
                    </el-form-item>
                    <el-form-item prop="email" label="邮箱">
                        <el-input v-model="infoForm.email" placeholder="邮箱"></el-input>
                    </el-form-item>
                    <el-form-item prop="birthday" label="生日">
                        <el-date-picker :editable="false" type="date" placeholder="选择日期" v-model="infoForm.birthday" 
                        style="width: 100%;" value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
                    </el-form-item>
                    <el-form-item prop="location" label="地区">
                        <el-input v-model="infoForm.location" placeholder="地区"></el-input>
                    </el-form-item>
                    <el-form-item prop="introduction" label="个性签名">
                        <el-input type="textarea" v-model="infoForm.introduction" :rows="3" resize="none"></el-input>
                    </el-form-item>
                    <el-form-item label="用户头像">
                        <el-upload ref="upload" class="avatar-uploader" :action="updateConsumerAvatar()" :show-file-list="false"
                            :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
                            <img v-if="imageUrl" :src="attachImageUrl(imageUrl)" class="avatar">
                            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                        </el-upload>
                        <span style="color:#409EFF">点击修改头像</span>
                    </el-form-item>
                    <el-form-item class="modify">
                        <el-button type="primary" @click="submitForm('infoForm')">立即修改</el-button>
                    </el-form-item>
                </el-form>
            </el-card>
        </div>
    </div>
</template>

<script>
import { mapGetters } from 'vuex'
import {mixin} from '@/mixins/utils'
import { selectConsumerById, updateConsumer } from '@/api/http'
export default {
    name:'person-info',
    mixins:[mixin],
    computed:{
        ...mapGetters([
            'consumerId',   // 用户id
            'isLogin'       // 是否登录
        ]),
        type() {
            return this.flag ? "text" : "password";
        },
        elIcon() {
            return this.flag ? "el-icon-minus" : "el-icon-view";
        }
    },
    data(){
        return{
            infoForm:{
                id:'',
                account:'',
                password:'',
                nickName:'',
                sex:3,
                phone:'',
                email:'',
                birthday:'',
                introduction:'',
                location:''
            },
            rules:{
                email: [
                    { required: true, message: '邮箱不能为空', trigger: 'blur' },
                    { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
                ],
                phone: [
                    { required: true, message: '请输入手机号', trigger: 'blur' },
                    {
                        pattern: /^((0\d{2,3}-\d{7,8})|(1[3584]\d{9}))$/,
                        message: '请输入正确的手机号码',
                    },
                ],
                location: [
                    { required: true, message: '请输入地区', trigger: 'blur' }
                ],
                nickName: [
                    { required: true, message: '请输入昵称', trigger: 'blur' }
                ],
                sex: [
                    { required: true, message: '性别不能为空', trigger: 'blur' }
                ]
            },
            flag:false,
            imageUrl: ''
        }
    },
    mounted(){
        this.getUserInfo();
    },
    methods:{
        getUserInfo(){
            if(this.isLogin){
                selectConsumerById(this.consumerId).then((res) => {
                    this.infoForm = res.data;
                    this.imageUrl = res.data.avatar;
                })
            }
        },
        submitForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    //头像上传成功后，修改用户信息
                    updateConsumer(this.infoForm).then((res) => {
                        if (res.code == 200) {
                            this.$notify({
                                message: res.msg,
                                shadow: true,
                                type: 'success'
                            });
                            this.getUserInfo();
                            this.$store.commit("setNickName", this.infoForm.nickName);
                            this.$store.commit("setSex", this.infoForm.sex);
                        } else {
                            this.$notify({
                                message: res.msg,
                                shadow: true,
                                type: 'error'
                            });
                        }
                    })
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
        },
        handleAvatarSuccess(res, file){
            if (res.code == 200) {
                this.$notify({
                    message: '头像修改成功',
                    shadow: true,
                    type: 'success'
                });
                this.$store.commit("setCAvatar", res.data)
                this.getUserInfo();
            } else {
                this.$notify({
                    message: '头像修改失败',
                    shadow: true,
                    type: 'error'
                });
            }
        },
        beforeAvatarUpload(file) {
            const isJPG = file.type === 'image/jpeg';
            const isLt2M = file.size / 1024 / 1024 < 2;

            if (!isJPG) {
                this.$notify({
                    message: '上传头像图片只能是 JPG 格式!',
                    shadow:true,
                    type: 'error'
                });
            }else {
                if (!isLt2M) {
                    this.$notify({
                        message: '上传头像图片大小不能超过 2MB!',
                        shadow: true,
                        type: 'error'
                    });
                }
            }
            return isJPG && isLt2M;
        },
        updateConsumerAvatar(){ // 修改用户头像
            return `${this.$store.state.config.HOST}/consumer/update/avatar?id=${this.consumerId}`;
        }
    }
}
</script>

<style>
.container{
    padding: 40px 300px;
}
.card{
    border-radius: 8px;
}
.modify{
    text-align: center;
}

 .avatar-uploader .el-upload {
     border: 1px dashed #d9d9d9;
     border-radius: 6px;
     cursor: pointer;
     position: relative;
     overflow: hidden;
 }

 .avatar-uploader .el-upload:hover {
     border-color: #409EFF;
 }

 .avatar-uploader-icon {
     font-size: 28px;
     color: #8c939d;
     width: 178px;
     height: 178px;
     line-height: 178px;
     text-align: center;
 }

 .avatar {
     width: 178px;
     height: 178px;
     display: block;
 }
</style>