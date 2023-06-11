<template>
    <div class="song-player">
        <audio 
        ref="player"
        :src="url" 
        controls
        muted="muted"
        preload="true" 
        @canplay="play"
        @ended="ended"
        @timeupdate="timeUpdate"
        >
        </audio>
    </div>
</template>

<script>
import { mapGetters } from 'vuex';
export default {
    name:'audio-player',
    computed:{
        ...mapGetters([
            'songId',  // 歌曲id
            'url',  // 歌曲播放地址
            'isPlay', // 歌曲播放状态
            'songsList', // 当前歌曲列表
            'currentTime', // 当前音乐播放时间
            'changeTime', // 从指定时间开始播放
            'autoNext', // 是否自动播放下一曲
            'volume' // 音量
        ])
    },
    watch:{
        // 监听播放状态
        isPlay(){
            this.togglePlay();
        },
        changeTime() {
           this.$refs.player.currentTime = this.changeTime;
        },
        volume(val) {
            this.$refs.player.volume = val;
        }
    },
    methods:{
        play() {  // 获取歌曲地址进行播放
            let player = this.$refs.player;
            this.$store.commit("setDuration", player.duration);
            // 播放
            player.play();
            this.$store.commit("setIsPlay",true);
        },
        ended(){ // 播放完成
            this.$store.commit("setIsPlay",false);
            this.$store.commit("setCurrentTime",0);
            this.$store.commit("setAutoNext",!this.autoNext);
        },
        togglePlay() { // 开始、暂停
            let player = this.$refs.player;
            if(this.isPlay){
                player.play();
            }else{
                player.pause();
            }
        },
        timeUpdate(){ // 音乐播放时 记录音乐播放位置 放入缓存中
            this.$store.commit('setCurrentTime', this.$refs.player.currentTime);
        }
    }
}
</script>

<style>
.song-player{
    display: none;
}
</style>