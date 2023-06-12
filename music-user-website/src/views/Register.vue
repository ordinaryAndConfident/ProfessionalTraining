<template>
    <div>
        <login-logo/>
        <div class="signUp">
            <div class="signUp-head">
                <span>用户注册</span>
            </div>
            <el-form :model="registerForm" ref="registerForm" label-width="70px" class="demo-ruleForm" :rules="rules">
                <el-form-item prop="email" label="邮箱">
                    <el-input v-model="registerForm.email" placeholder="邮箱"></el-input>
                </el-form-item>
                <el-form-item prop="password" label="密码">
                    <el-input type="password" v-model="registerForm.password" placeholder="密码" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item prop="code" label="验证码">
                    <el-input v-model="registerForm.code" placeholder="请输入验证码" class="code"></el-input>
                    <div class="login-code" @click="createCode">
                        <img :src="codePath" title="看不清楚，换一张" class="login-code-img"/>
                    </div>
                </el-form-item>
                <el-form-item align="center">
                    <el-button size="small" type="primary" @click="regist('registerForm')" style="width:130px">提交</el-button>
                    <el-button size="small" @click="resetForm('registerForm')" style="width:130px">重置</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
import LoginLogo from '@/components/LoginLogo' 
import { getCode, registerConsumer } from '@/api/http'
import { mixin } from '@/mixins/utils'
export default {
    name:'register',
    mixins:[mixin],
    components:{
        LoginLogo
    },
    data(){
        return{
            registerForm:{
                password:'',
                email:'',
                code:''
            },
            rules:{
                email:[
                    { required: true, message: '请输入邮箱地址', trigger: 'blur' },
                    { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
                ],
                password: [
                    { required: true, message: '请输入密码', trigger: 'blur' },
                    { min: 3, max: 18, message: '长度在 3 到 18 个字符', trigger: 'blur' }
                ]
            },
            codePath: ''
        }
    },
    created(){
        this.createCode();
    },
    mounted() {
        this.changeIndex('注册')
    },
    methods:{
        createCode(){
            getCode().then((res)=>{
                var num = Math.ceil(Math.random() * 10);//生成一个随机数（防止缓存）
                this.codePath = this.$store.state.config.HOST + res.data;
                this.codePath += '?' + num;
            }).catch((error)=>{
                console.log(error);
            })
        },
        regist(formname){
            this.$refs[formname].validate((valid) => {
                if (valid) {
                    registerConsumer(this.registerForm).then((res) => {
                        if(res.code == 200){
                            this.$notify({
                                message: res.msg,
                                type: 'success'
                            });
                            setTimeout(() => {
                                this.changeIndex('登录');
                                this.$router.push({ path: '/login-in' }); // 跳转到首页
                            }, 1500);
                        }else{
                            this.$notify({
                                message: res.msg,
                                type: 'error'
                            });
                        }
                    }).catch((error) => {
                        console.log(error);
                    })
                } else {
                    return false;
                }
            });
        },
        resetForm(formName){
            this.$refs[formName].resetFields();
        }
    }
}
</script>

<style lang="scss" scoped>
.code{
    width: 135px;
}
.login-code{
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