<template>
    <div class="loginbody">
        <div class="logindata">
            <div class="logintext">
                <h3>MeetMusic后台管理</h3>
            </div>
            <div class="formdata"   v-loading="loading"
         element-loading-text="拼命加载中" 
         element-loading-spinner="el-icon-loading" 
         element-loading-background="rgba(0, 0, 0, 0.2)">
                <el-form ref="form" :model="form" :rules="rules">
                    <el-form-item prop="account">
                        <el-input prefix-icon="el-icon-user" type="email" v-model="form.account" clearable placeholder="请输入账号"></el-input>
                    </el-form-item>
                    <el-form-item prop="password">
                        <el-input prefix-icon="el-icon-lock" v-model="form.password" clearable placeholder="请输入密码" show-password></el-input>
                    </el-form-item>
                </el-form>
            </div>
            <div class="tool">
                <div>
                    <el-checkbox v-model="checked" @change="remenber">记住密码</el-checkbox>
                </div>
                <div>
                    <span class="shou" @click="forgetpas">忘记密码？</span>
                </div>
            </div>
            <div class="but">
                <el-button type="primary" @click.native.prevent="login('form')" style="width:100%">登录</el-button>
            </div>
        </div>
    </div>
</template>

<script>
import { getLogin } from '@/api/http'
import { re } from 'semver';
export default {
    name: "login",
    data() {
        return {
            form: {
                account: "",
                password: "",
            },
            checked: false,
            rules: {
                account: [
                    { required: true, message: "请输入用户名", trigger: "blur" },
                    { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
                ],
                password: [
                    { required: true, message: "请输入密码", trigger: "blur" },
                    { min:6,max: 18, message: "不能小于6个字符，大于18个字符", trigger: "blur" },
                ],
            },
            loading:false
        };
    },
    mounted() {
        if (localStorage.getItem("user")) {
            this.form = JSON.parse(localStorage.getItem("user"))
            this.checked = true
        }
    },
    methods: {
        login(form) {
            this.$refs[form].validate((valid) => {
                if (valid) {
                    this.loading = true // 打开加载层
                    setTimeout(() => {
                        getLogin(this.form).then((res) => {
                            if (res.code === 200) {
                                this.loading = false; // 登录成功关闭加载层
                                localStorage.setItem("nickName",res.data.nickName)
                                this.$message({
                                    message: "登录成功啦",
                                    type: "success",
                                    showClose: true,
                                });
                                this.$router.push('/info'); // 跳转到首页
                            } else {
                                this.loading = false; // 登录失败关闭加载层
                                this.$message({
                                    message: "账户名或密码错误",
                                    type: "error",
                                    showClose: true,
                                });
                            }
                        }, 1500);
                    }, 1500);
                } else {
                    return false;
                }
            });
        },
        remenber(data) {
            this.checked = data
            if (this.checked) {
                localStorage.setItem("user", JSON.stringify(this.form))
            } else {
                localStorage.removeItem("user")
            }
        },
        forgetpas() {
            this.$message({
                type: "info",
                message: "功能尚未开发额😥",
                showClose: true
            })
        }
    },
};
</script>

<style scoped>
.loginbody {
    width: 100%;
    height: 100%;
    min-width: 1000px;
    background-image: url("../assets/img/bg.jpg");
    background-size: 100% 100%;
    background-position: center center;
    overflow: auto;
    background-repeat: no-repeat;
    position: fixed;
    line-height: 100%;
    padding-top: 220px;
}

.logintext {
    margin-bottom: 20px;
    line-height: 50px;
    text-align: center;
    font-size: 30px;
    font-weight: bolder;
    color: white;
    text-shadow: 2px 2px 4px #000000;
}

.logindata {
    width: 400px;
    height: 300px;
    transform: translate(-50%);
    margin-left: 50%;
}

.tool {
    display: flex;
    justify-content: space-between;
    color: #606266;
}

.but {
    margin-top: 10px;
    text-align: center;
}

.shou {
    cursor: pointer;
    color: #606266;
}
</style>

