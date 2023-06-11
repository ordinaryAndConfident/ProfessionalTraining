<template>
    <div class="the-header">
        <div class="header-logo" @click="goHome">
            <svg class="icon">
                <use xlink:href="#icon-music"></use>
            </svg>
            <span>遇见音乐</span>
        </div>
        <ul class="navbar">
            <li :class="{active:item.name == activeName}" v-for="item in navInfo" :key="item.path" @click="goPage(item.path,item.name)">
                {{item.name}}
            </li>
            <li>
                <div class="header-search">
                    <div class="search-container">
                        <el-input placeholder="搜索歌曲" height="200" size="small" v-model="keywords" class="input" @keyup.enter.native="goSearch()">
                            <el-button slot="append" size="small" icon="el-icon-search" @click="goSearch()"></el-button>
                        </el-input>
                    </div>
                </div>
            </li>
            <li :class="{active:item.name == activeName}" v-for="item in loginInfo" :key="item.path"
                @click="goPage(item.path,item.name)" v-show="!isLogin">
                {{item.name}}
            </li>
        </ul>
        <div class="header-right" v-show="isLogin">
            <div id="user">
                <img :src="attachImageUrl(avatar)"/>
            </div>
            <el-dropdown class="user-name" trigger="click" @command="hanleCommand" style="display:flex">
                <span class="el-dropdown-link">
                    <span class="nickName">{{nickName}}</span>
                    <i class="el-icon-caret-bottom"></i>
                </span>
                <el-dropdown-menu slot="dropdown" style="margin-top:-15px;">
                    <el-dropdown-item command="personInfo">个人信息</el-dropdown-item>
                    <el-dropdown-item command="logout">退出登录</el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
        </div>
    </div>
</template>

<script>
import { mapGetters } from 'vuex';
import { navInfo, loginInfo } from '@/assets/data/header'
import {mixin} from '@/mixins/utils'
export default {
    name: 'headers',
    mixins:[mixin],
    data() {
        return {
            navInfo: [],
            loginInfo:[],
            keywords:""
        }
    },
    computed:{
        ...mapGetters([
            'activeName',
            'isLogin',
            'avatar',
            'nickName'
        ])
    },
    created() {
        this.navInfo = navInfo;
        this.loginInfo = loginInfo;
    },
    methods: {
        goHome() {
            this.$store.commit('setActiveName', '首页');
            this.$router.push({ path: "/" });
        },
        goPage(path,name){
            if(!this.isLogin && path == '/my-music'){
                this.$notify({
                    message: '请先登录哟!',
                    type: 'warning'
                });
            }else{
                this.$store.commit('setActiveName', name);
                this.$router.push({ path: path });
            }
        },
        goSearch(){
            if(!this.keywords){
                this.$message({
                    message: '歌曲名称不能为空哟',
                    showClose: true,
                    type: 'warning'
                });
                return false;
            }
            this.$router.push({path:'/search',query:{keywords:this.keywords}});
        },
        hanleCommand(command){
            if (command == "logout") {
                window.sessionStorage.setItem("nickName", JSON.stringify(""));
                this.$store.commit("setIsLogin",false);
                this.$store.commit("setIsFavorite",false);
                this.$router.push({path:'/'});
                this.$router.go(0);
            } else if (command == 'personInfo') {
                this.$router.push({ path: '/person-info' });
            } else{
                console.log(command);
            }
        }
    }
}
</script>

<style lang="scss" scoped>
.search-container{
    height: 65px;
    line-height: 65px;
    text-align: center;
}

.el-input {
    width: 260px;
}

.nickName{
    color: #67757f;
    font-size: 15px;
    font-weight: 600px;
}
@import '@/assets/css/the-header.scss'
</style>