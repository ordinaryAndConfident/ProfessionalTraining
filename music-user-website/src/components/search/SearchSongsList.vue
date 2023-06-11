<template>
    <div class="search-song-lists">
        <content-list :content-list="albumDatas"/>
    </div>
</template>

<script>
import ContentList from '@/components/ContentList.vue'
import { selectSongListByTitle } from '@/api/http'
export default {
    name: 'search-songs-list',
    components:{
        ContentList
    },
    data(){
        return{
            albumDatas:[]
        }
    },
    mounted(){
        this.getSearchList();
    },
    methods:{
        getSearchList(){
            if(!this.$route.query.keywords){
                this.$notify({
                    message: '输入的内容不能为空哟!',
                    type: 'warning'
                });
            }else{
                selectSongListByTitle(this.$route.query.keywords).then((res)=>{
                    if(res.data){
                        this.albumDatas = res.data;
                    }else{
                        this.$notify({
                            message: '暂无该歌曲内容',
                            type: 'warning'
                        });
                    }
                }).catch((error)=>{
                    console.log(error);
                });
            }
        }
    }
}
</script>

<style lang="scss" scoped>
@import '@/assets/css/search-song-lists.scss'
</style>