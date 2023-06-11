<template>
    <div>
        <div class="content-list">
            <ul class="section-content">
                <li class="content-item" v-for="(item,index) in contentList" :key="index">
                    <!-- 歌手 -->
                    <div class="kuo" v-if="item.avatar" @click="goAlbum(item,item.name)">
                        <img class="item-img" :src="attachImageUrl(item.avatar)" alt="item.name">
                    </div>
                    <!-- 歌单 -->
                    <div class="kuo" v-else   @click="goAlbum(item,item.name)">
                        <img class="item-img" :src="attachImageUrl(item.pic)" alt="item.title">
                    </div>
                    <p class="item-name" v-if="item.name">{{item.name}}</p>
                    <p class="item-name" v-else>{{item.title}}</p>
                </li>
            </ul>
        </div>
    </div>
</template>

<script>
import { mixin } from '@/mixins/utils.js';
export default {
    name:'content-list',
    mixins: [mixin],
    props:['contentList'],
    data() {
        return {
            
        }
    },
    methods:{
        goAlbum(item,type){
            this.$store.commit("setTempList",item);
            if(type){ // 歌手
                this.$router.push({ path:`singer-album/${item.id}`});
            }else{  // 歌单
                this.$router.push({ path: `song-list-album/${item.id}`});
            }
        }
    }
}
</script>

<style lang="scss" scoped>
@import '@/assets/css/content-list.scss'
</style>