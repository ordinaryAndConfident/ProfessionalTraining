<template>
    <div class="my-music">
        <div class="album-slide">
            <div class="album-img">
                <img :src="attachImageUrl(consumer.avatar)" alt="">
            </div>
            <ul class="album-info">
                <li>昵称：{{ consumer.nickName }}</li>
                <li>性别：{{ attachSex(consumer.nickName) }}</li>
                <li>生日：{{ attachTime(consumer.birthday) }}</li>
            </ul>
        </div>
        <div class="album-content">
            <div class="album-title">
                个性签名：{{ consumer.introduction }}
            </div>
            <div class="songs-body">
                <h3 class="title">我的收藏</h3>
                <album-content :listOfSongs="songList">
                </album-content>
            </div>
        </div>
    </div>
</template>

<script>
import {mixin} from '@/mixins/utils' 
import { mapGetters } from 'vuex';
import { selectConsumerById, getConsumerFavorite, getSongById } from '@/api/http'
import AlbumContent from '@/components/AlbumContent'
export default {
    name: 'my-music',
    mixins:[mixin],
    computed:{
        ...mapGetters([
            'songsList',    // 当前播放列表
        ])
    },
    components:{
        AlbumContent
    },
    mounted(){
        this.getInfo();
        this.getMyFavorite();
    },
    data(){
        return{
            songList:[], // 收藏的播放列表(歌曲详情)
            favorites:[], // 用户收藏列表
            consumer:{}
        }
    },
    methods:{
        getInfo(){
            selectConsumerById(this.consumerId).then((res)=>{
                if(res.code == 200){
                    this.consumer = res.data;
                }
            }).catch((error)=>{
                console.log(error);
            });
        },
        getMyFavorite(){
            getConsumerFavorite(this.consumerId).then((res) => {
                if (res.code == 200) {
                    this.favorites = res.data;
                    // 通过歌曲id 查询歌曲信息
                    for (let item of this.favorites) {
                        getSongById(item.songId).then((res) => {
                            if (res.code == 200) {
                                this.songList.push(res.data);
                            }
                        }).catch((error) => {
                            console.log(error);
                        });
                    }
                }
            }).catch((error) => {
                console.log(error);
            });
        }
    }
}
</script>

<style lang="scss" scoped>
.title{
    position: relative;
    margin-left: 47%;
    padding: 10px;
}
@import '@/assets/css/my-music.scss'
</style>