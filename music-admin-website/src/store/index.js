import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex)


const store = new Vuex.Store({
    state: {
        HOST: 'http://127.0.0.1:9527',
        //HOST: 'http://106.55.62.2:9527',
        isPlay: false, // 默认不播放
        url: '', // 播放的歌曲地址
        songId: '' // 歌曲id
    },
    getters: {
        isPlay: state => state.isPlay,
        url: state => state.url,
        songId: state => state.songId
    },
    mutations: {
        setIsPlay: (state, isPlay) => { state.isPlay = isPlay },
        setUrl: (state, url) => { state.url = url },
        setSongId: (state, songId) => { state.songId = songId }
    }
})

export default store