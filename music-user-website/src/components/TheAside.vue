<template>
    <transition name="silde-fade">
        <div class="the-aside" v-show="showAside">
           <h3 class="title">播放列表</h3>
           <ul class="menus">
                <li v-for="(song,index) in songsList" :key="index" :class="{'is-play':id == song.id}"
                @click="playSong(song.id, song.url, song.pic, index, song.name, song.lyric)">
                    {{handleSongName(song.name)}}
                </li>
           </ul>
        </div>
    </transition>
</template>

<script>
import { mapGetters } from 'vuex';
import { mixin } from '@/mixins/utils.js'
export default {
    name:'the-aside',
    mixins:[mixin],
    computed:{
        ...mapGetters([
            'showAside', // 是否显示歌单列表
            'songsList', // 当前歌曲列表
            'id'        // 当前播放音乐id
        ])
    },
    mounted(){
        let _this = this;
        // 点击空白处隐藏歌单列表
        document.addEventListener('click', function () {
            _this.$store.commit('setShowAside', false);
        }, true);
    },
    methods:{
        // 处理歌曲名称
        handleSongName(str) {
            let arr = str.split("-");
            return arr[0];
        }
    }
}
</script>

<style lang="scss" scoped>
@import '@/assets/css/the-aside.scss'
</style>