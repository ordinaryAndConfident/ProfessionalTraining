<template>
    <div class="song-list-album">
        <div class="album-slide">
            <div class="album-img">
                <img :src="attachImageUrl(songListDetails.pic)" alt="">
            </div>
            <div class="album-info">
                <h3>{{ songListDetails.title}}</h3>
            </div>
        </div>
        <div class="album-content">
            <div class="album-introduction">
                <p>{{ songListDetails.introduction}}</p>
            </div>
            <div class="album-score">
                <div>
                    <h3>歌单评分：{{rankAvg*2}}</h3>
                    <div>
                        <el-rate v-model="rankAvg" :colors="colors" disabled></el-rate>
                    </div>
                </div>
                <div style="margin-left:500px" @click="newRank">
                    <h3>评分：{{rank}}</h3>
                    <el-rate v-model="rank" :colors="colors" allow-half show-text></el-rate>
                </div>
            </div>
            <div class="songs-body" style="margin-top:40px">
                <album-content :listOfSongs="songLists" />
            </div>
            <el-card class="card">
                <comment :playId="songListId" :type="1" />
            </el-card>
        </div>
    </div>
</template>


<script>
import {mixin} from '@/mixins/utils'
import { mapGetters } from 'vuex';
import { getSongListBySongListId, setRank, rankAvg, getUserRankScore, getSongListById } from '@/api/http'
import AlbumContent from '@/components/AlbumContent.vue'
import Comment from '@/components/Comment.vue'
export default {
    name:'song-list-album',
    mixins:[mixin],
    components:{
        AlbumContent,
        Comment
    },
    data(){
        return{
            songLists:[], // 歌曲列表
            songListId:-1,   // 取出传递的歌单id
            rankAvg:0,      // 歌单平均分
            rank:0,          // 用户对歌单评价分数
            colors: ['#99A9BF', '#F7BA2A', '#FF9900'],
            songListDetails:[]
        }
    },
    computed:{
        ...mapGetters([
            'songsList',    // 当前播放列表
            'tempList',      // 当前歌单
            'isLogin',       // 用户登录状态
            'consumerId'           // 登录的用户id
        ])
    },
    mounted(){
        this.songListId = this.$route.params.id;
        this.getSongListById();
        this.getRank();
        this.getScore();
        this.getSongList();
    },
    methods:{
        // 获取当前歌单的歌曲列表
        getSongListById(){
            getSongListBySongListId(this.songListId).then((res)=>{
                if(res.code == 200){
                    this.$store.commit('setSongsList',res.data);
                    this.songLists = res.data;
                }else{
                    this.$notify({
                        message: '该歌单暂时还没收录歌曲哟',
                        type: 'warning'
                    });
                }
            }).catch((error)=>{
                console.log(error);
            })
        },
        getRank(){  // 获取歌单评分
            rankAvg(this.songListId).then((res)=>{
                if(res.code == 200){
                    this.rankAvg = res.data / 2;
                }else{
                    this.$notify({
                        message: res.msg,
                        type: 'error'
                    });
                }
            }).catch((error)=>{
                console.log(error);
            });
        },
        newRank(){  // 用户评分
            if (this.isLogin) {
                let params = {
                    songListId:this.songListId,
                    consumerId: this.consumerId,
                    score:this.rank * 2
                }
                setRank(params).then((res) => {
                    if (res.code == 200) {
                    this.getRank(this.songListId);
                    } else {
                        this.$notify({
                            message: res.msg,
                            type: 'error'
                        });
                    }
                }).catch(() => {
                    this.$notify({
                        message: '你已经评价过了哟，不能重复评价!',
                        type: 'error'
                    });
                });
            }else {
                this.rank = 0;
                this.$notify({
                    message: '请先登录，再进行评分!',
                    type: 'warning'
                });
            }
        },
        getScore(){
            if(this.isLogin){
                getUserRankScore(this.songListId, this.consumerId).then((res) => {
                    if (res.code == 200) {
                        this.rank = res.data / 2; // 显示用户的评分信息
                    } else {
                        this.rank = 0;
                    }
                }).catch((error) => {
                    console.log(error);
                });
            }
        },
        getSongList(){
            getSongListById(this.songListId).then((res) => {
                if (res.code == 200) {
                    this.songListDetails = res.data;
                }
            }).catch((error) => {
                console.log(error);
            });
        }
    }

}
</script>

<style lang="scss" scoped>
.card{
    margin-top: 25px;
    border-radius: 12px;
}
@import '@/assets/css/song-list-album.scss'
</style>