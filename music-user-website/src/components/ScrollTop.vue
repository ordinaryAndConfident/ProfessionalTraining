<template>
    <!-- <div class="scroll-top" @click="scrollTop">
         <div class="box-in"></div>
    </div> -->
   
    <el-backtop :bottom='100'>
        <div>
           <i class='el-icon-top'></i>
        </div>
    </el-backtop>
</template>

<script>
export default {
    name:'scroll-top',
    methods:{
        scrollTop(){
            document.documentElement.scrollTop = document.body.scrollTop = 0; // 返回页面最顶端
        }
    }
}
</script>

<style lang="scss" scoped>
@import '@/assets/css/scroll-top.scss'
</style>