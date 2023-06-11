<template>
    <div class="home">
        <swiper/>
        <div class="section" v-for="(item,index) in songList" :key="index">
            <div class="section-title">
                {{item.name}}
            </div>
            <content-list :contentList="item.list"/>
        </div>
    </div>
</template>

<script>
import Swiper from '@/components/Swiper.vue'
import ContentList from '@/components/ContentList.vue'
import { getAll, getAllSongList } from '@/api/http'
export default {
    name: 'home',
    components:{
        Swiper,
        ContentList
    },
    data() {
        return {
            songList:[
                {name:"歌单",list:[]},
                {name:"歌手",list:[]},
            ]
        }
    },
    created(){
        this.getSongList();
    },
    methods:{
        // 获取前10条歌单信息
        getSongList(){
            getAllSongList().then((res)=>{
                this.songList[0].list = res.data.slice(0,10);
            }).catch((error)=>{
                console.log(error);
            });
            //获取前10名歌手信息
            getAll().then((res) => {
                this.songList[1].list= res.data.slice(0, 10);
            }).catch((error) => {
                console.log(error);
            });
        }
    }
}
</script>

<style lang="scss" scoped>
@import '@/assets/css/home.scss'
</style>
