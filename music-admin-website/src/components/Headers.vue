<template>
    <div class="header">
        <!--折叠按钮-->
        <div class="collapse-btn" @click="collapseChange">
            <i class="el-icon-menu"></i>
        </div>
        <div class="logo">MeetMusic后台管理</div>
        <div class="header-right">
            <div class="btn-fullscreen" @click="handlerFullScreen">
                <el-tooltip :content="isScreen ?'取消全屏':'全屏'" placement="bottom">
                    <i class="el-icon-rank"></i>
                </el-tooltip>
            </div>
            <div class="avatar">
                <img src="@/assets/img/avatar.jpg" alt="">
            </div>
            <el-dropdown class="user-name" trigger="click" @command="hanleCommand" style="display:flex">
                <span class="el-dropdown-link">
                    {{nickName}}
                    <i class="el-icon-caret-bottom"></i>
                </span>
                <el-dropdown-menu slot="dropdown" style="margin-top:-15px;">
                    <el-dropdown-item command="logout">退出登录</el-dropdown-item>
                    <el-dropdown-item command="adminInfo">个人信息</el-dropdown-item>
                    <el-dropdown-item command="modify">修改密码</el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
        </div>
    </div>
</template>

<script>
import bus from '@/assets/js/bus'

export default {
    data(){
        return{
            collapse:false,
            isScreen:false
        }
    },
    computed:{
        nickName(){
            return localStorage.getItem("nickName");
        }
    },
    methods:{
        // 折叠侧边栏
        collapseChange(){
            this.collapse = !this.collapse;
            bus.$emit('collapse', this.collapse);
        },
        // 全屏显示
        handlerFullScreen(){
            if(this.isScreen){
                if(document.exitFullscreen){
                    document.exitFullscreen();
                } else if (document.webkitCancelFullScreen){
                    document.webkitCancelFullScreen()
                } else if (document.mozCancelFullScreen){
                    document.mozCancelFullScreen();
                }else if(document.msExitFullScreen){
                    document.msExitFullScreen();
                }
            }else{
                let element = document.documentElement;

                if(element.requestFullscreen){
                    element.requestFullscreen();
                }else if(element.webkitRequestFullScreen){
                    element.web.webkitRequestFullScreen();
                } else if (element.mozRequestlFullScreen) {
                    element.web.mozRequestlFullScreen();
                } else if (element.msRequestFullScreen){
                    element.msRequestFullScreen();
                }
            }
            this.isScreen = !this.isScreen;
        },
        hanleCommand(command){
            if(command == "logout"){
                localStorage.removeItem("nickName");
                this.$router.push("/");
            } else if (command == 'adminInfo'){
                console.log(command);
            }else{
                console.log(command);
            }
        }
    }
}
</script>

<style scoped>
.header{
    position: relative;
    background-color: #253041;
    box-sizing: border-box;
    width:100%;
    height: 70px;
    font-size: 22px;
    color: #fff;
}

.collapse-btn{
    float: left;
    padding: 0 21px;
    cursor: pointer;
    line-height: 70px;
}

.header .logo{
    position: relative;
    float: left;
    line-height: 70px;
    color: #1989fa;
}

.header-right{
    float: right;
    padding-right: 50px;
    display: flex;
    line-height: 70px;
    align-items: center;
}

.btn-fullscreen{
    margin-right: 5px;
    font-size: 24px;
    transform: rotate(45deg);
}

.avatar{
    margin-left: 20px;

}

.avatar img{
    width: 41px;
    height: 41px;
    border-radius: 50%;
    display: block;
}

.user-name{
    margin-left: 12px;
}

.el-dropdown-link{
    color: #fff;
    cursor: pointer;
    margin-left: 10px;
}
</style>