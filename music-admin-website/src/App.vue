<template>
  <div id="app">
    <Player/>
    <router-view/>
  </div>
</template>

<script>
import Player from '@/components/Player.vue';
export default {
  name: 'App',
  components:{
    Player
  }
}
</script>

<style>
</style>
