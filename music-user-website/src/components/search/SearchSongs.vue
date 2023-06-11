<template>
    <div class="search-songs">
        <album-content :listOfSongs="songsList" />
    </div>
</template>

<script>
import { mapGetters } from 'vuex'
import {mixin} from '@/mixins/utils'
import AlbumContent from '@/components/AlbumContent.vue'
export default {
    name:'search-songs',
    components:{
        AlbumContent
    },
    mixins: [mixin],
    computed:{
        ...mapGetters([
            'songsList'
        ])
    },
    mounted(){
        this.getSong();
    }
}
</script>

<style lang="scss" scoped>
@import '@/assets/css/search-songs.scss'
</style>