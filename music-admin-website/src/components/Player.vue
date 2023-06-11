<template>
    <div class="song-player">
        <audio 
        id="player" 
        :src="url" 
        controls = "controls" 
        preload="true" 
        @canplay="play"
        @ended="ended"
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
            'songId', 'url', 'isPlay'
        ])
    },
    watch:{
        // 监听播放状态
        isPlay(){
            this.togglePlay();
        }
    },
    methods:{
        play() {  // 获取歌曲地址进行播放
            let player = document.querySelector("#player");
            // 播放
            player.play();
        },
        ended(){ // 播放完成
           this.isPlay = false;
        },
        togglePlay() { // 开始、暂停
            let player = document.querySelector("#player");
            if(this.isPlay){
                player.play();
            }else{
                player.pause();
            }
        }
    }
}
</script>

<style>
.song-player{
    display: none;
}
</style>