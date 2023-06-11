<template>
    <div>
        <login-logo />
        <div class="signUp">
            <div class="signUp-head">
                <span>用户登录</span>
            </div>
            <el-form :model="loginForm" ref="loginForm" label-width="70px" class="demo-ruleForm" :rules="rules">
                <el-form-item prop="account" label="账号">
                    <el-input v-model="loginForm.account" placeholder="账号"></el-input>
                </el-form-item>
                <el-form-item prop="password" label="密码">
                    <el-input type="password" v-model="loginForm.password" placeholder="密码" autocomplete="off">
                    </el-input>
                </el-form-item>
                <el-form-item align="center">
                    <el-button size="small" type="primary" style="width:130px" @click="handleLogin">登录</el-button>
                    <el-button size="small" style="width:130px" @click="goSignUp">注册</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
import { mapGetters } from 'vuex'
import LoginLogo from '@/components/LoginLogo'
import { loginConsumer } from '@/api/http'
import {mixin} from '@/mixins/utils'
export default {
    name: 'login-in',
    mixins:[mixin],
    components: {
        LoginLogo
    },
    computed:{
        ...mapGetters([
            'id'    // 歌曲id
        ])
    },
    data() {
        return {
            loginForm: {
                password: '',
                account: ''
            },
            rules: {
                email: [
                    { required: true, message: '请输入邮箱地址', trigger: 'blur' },
                    { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
                ],
                password: [
                    { required: true, message: '请输入密码', trigger: 'blur' },
                    { min: 3, max: 18, message: '长度在 3 到 18 个字符', trigger: 'blur' }
                ]
            }
        }
    },
    mounted(){
        this.changeIndex('登录')
    },
    methods: {
        handleLogin() {
            loginConsumer(this.loginForm).then((res)=>{
                if(res.code == 200){
                    this.$notify({
                        message: res.msg,
                        type: 'success'
                    });
                    this.$store.commit("setIsLogin", true);
                    this.$store.commit("setConsumerId", res.data.id);
                    this.$store.commit("setNickName", res.data.nickName);
                    this.$store.commit("setCAvatar", res.data.avatar);
                    this.$store.commit("setSex", res.data.sex);
                    this.handleFavorite(this.id);
                    setTimeout(() => {
                        this.changeIndex('首页');
                        this.$router.push({ path: '/' }); // 跳转到首页
                    }, 1500);
                }else{
                    this.$notify({
                        message: res.msg,
                        type: 'error'
                    });
                }
            }).catch((error)=>{
                console.log(error);
            })
        },
        goSignUp() {
            this.$router.push({path:'/sign-up'});   // 跳转到注册页面
        },
        resetForm(formName) {
            this.$refs[formName].resetFields(); // 重置表单
        }
    }
}
</script>

<style lang="scss" scoped>
.code {
    width: 135px;
}

.login-code {
    width: 135px;
    height: 40px;
    line-height: 40px;
    text-align: center;
    float: right;
}

.login-code-img {
    width: 100%;
    height: 100%;
    cursor: pointer;
}

@import '@/assets/css/sign-up.scss'
</style>