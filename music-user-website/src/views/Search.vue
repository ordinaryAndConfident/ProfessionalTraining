<template>
    <div class="search">
        <nav class="searchList-nav" ref="change">
            <span :class="{isActive:toggle == 'Songs'}" @click="handleChangeView('Songs')">歌曲</span>
            <span :class="{isActive:toggle == 'SongsList'}" @click="handleChangeView('SongsList')">歌单</span>
        </nav>
        <component :is="currentView"/>
    </div>
</template>

<script>
import SearchSongs from '@/components/search/SearchSongs'
import SearchSongsList from '@/components/search/SearchSongsList'
export default {
    name:'search',
    components:{
        SearchSongs,
        SearchSongsList
    },
    data(){
        return{
            toggle:'Songs',
            currentView: 'SearchSongs'
        }
    },
    watch:{
        // 暂时解决搜索不能刷新问题
        $route(to,from) {
            this.$router.go(0)
        }
    },
    methods:{
        handleChangeView(component){
            this.currentView = 'Search' + component;
            this.toggle = component;
        }
    }
}
</script>

<style lang="scss" scoped>
@import '@/assets/css/search.scss'
</style>