<template>
    <div class="content">
        <h1 class="title">
            <slot name="title"></slot>
        </h1>
        <ul>
            <li>
                <div class="song-item">
                    <span class="item-index"></span>
                    <span class="item-title">歌曲名称</span>
                    <span class="item-name">歌手名称</span>
                    <span class="item-intro">专辑</span>
                </div>
            </li>
            <li v-for="(item,index) in listOfSongs" :key="index">
                <div class="song-item" @click="playSong(item.id,item.url,item.picture,index,item.name,item.lyric)">
                    <span class="item-index">{{++index}}</span>
                    <span class="item-title">{{ handleSongName(item.name) }}</span>
                    <span class="item-name">{{ handleSingerName(item.name) }}</span>
                    <span class="item-intro">{{item.introduction}}</span>
                </div>
            </li>
        </ul>
    </div>
</template>

<script>
import { mapGetters } from 'vuex';
import { mixin } from '@/mixins/utils'
export default {
    name: 'album-content',
    mixins:[mixin],
    props: [
        'listOfSongs'
    ]
}
</script>

<style lang="scss" scoped>
@import '@/assets/css/album-content.scss'
</style>

