<template>
    <div class="singer-album">
        <div class="album-slide">
            <div class="singer-img">
                <img :src="attachImageUrl(singer.avatar)" alt="">
            </div>
            <ul class="info">
                <li v-if="singer.sex == 0 || singer.sex == 1">
                    性别：{{attachSex(singer.sex)}}
                </li>
                <li>生日：{{ attachTime(singer.birthday) }}</li>
                <li>地区：{{ singer.location}}</li>
            </ul>
        </div>
        <div class="album-content">
            <div class="intro">
                <h2 class="name">{{singer.name}}</h2>
                <span>{{singer.introduction}}</span>
            </div>
            <div class="content">
                <album-content :listOfSongs="songsList" />
                <el-card class="card">
                    <comment :playId="singerId" :type="0" />
                </el-card>
            </div>
        </div>
    </div>
</template>

<script>
import { mixin } from '@/mixins/utils'
import { mapGetters } from 'vuex';
import { getSongBySingerId } from '@/api/http'
import AlbumContent from '@/components/AlbumContent.vue'
import Comment from '@/components/Comment.vue'
export default {
    name: 'singer-album',
    mixins: [mixin],
    components: {
        AlbumContent,
        Comment
    },
    computed: {
        ...mapGetters([
            'songsList',
            'tempList',      // 当前歌手对象
            'isLogin',       // 用户登录状态
            'consumerId'           // 登录的用户id
        ])
    },
    data(){
        return {
            singerId:'', // 歌手id
            singer:{}   // 当前歌手信息

        }
    },
    created(){
        this.singerId = this.$route.params.id;
        this.singer = this.tempList;
        this.getSongOfSingerId();
    },
    methods:{
        getSongOfSingerId(){
            getSongBySingerId(this.singerId).then((res)=>{
                this.$store.commit("setSongsList",res.data);
            }).catch((error)=>{
                console.log(error);
            });
        }
    }
}
</script>

<style lang="scss" scoped>
.name{
    padding: 15px;
}
.card {
    margin-top: 25px;
    border-radius: 12px;
}
@import '@/assets/css/singer-album.scss'
</style>