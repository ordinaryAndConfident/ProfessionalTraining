<template>
  <div id="app">
    <player/>
    <headers/>
    <router-view/>
    <the-aside/>
    <play-bar/>
    <scroll-top/>
    <the-footer/>
  </div>
</template>

<script>
import Headers from '@/components/Headers.vue'
import ScrollTop from '@/components/ScrollTop.vue'
import TheFooter from '@/components/TheFooter.vue'
import Player from '@/components/Player.vue'
import PlayBar from '@/components/Playbar.vue'
import TheAside from '@/components/TheAside.vue'
export default {
  name: 'App',
  components:{
    Headers,
    Player,
    ScrollTop,
    TheFooter,
    PlayBar,
    TheAside
  }
}
</script>

<style  lang="scss" scoped>
@import './assets/css/app.scss';
</style>
