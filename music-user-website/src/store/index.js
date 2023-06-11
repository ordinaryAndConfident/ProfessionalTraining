import Vue from "vue";
import Vuex from 'vuex'
import config from '@/store/config'
import user from '@/store/user'
import songs from '@/store/songs'

Vue.use(Vuex);

const store = new Vuex.Store({
    modules: {
        config,
        user,
        songs
    }
});

export default store