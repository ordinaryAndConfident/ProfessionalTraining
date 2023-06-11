<template>
    <div class="song-list">
        <div class="song-list-header">
            <ul>
                <li v-for="(item,index) in styles" :key="index" :class="{active: item.name == activeName}"
                 @click="handleChangeView(item.name)" ref="liStyle">
                    <h4 v-if="item.name == '热门推荐'" style="color:#C10D0C;">{{item.name}}</h4>
                    <span v-else>
                        {{item.name}}
                    </span>
                </li>
            </ul>
        </div>
        <div>
            <content-list :content-list="data" />
            <div class="pagination">
                <el-pagination @current-change="handleCurrentChange" background layout="total,prev,pager,next" :current-page="currentPage"
                    :page-size="pageSize" :total="allSongList.length"></el-pagination>
            </div>
        </div>
    </div>
</template>

<script>
import ContentList from '@/components/ContentList.vue'
import { getAllSongList, selectSongListByStyle } from '@/api/http'
import {styles} from '@/assets/data/song-style'
export default {
    name: 'song-list',
    components: {
        ContentList
    },
    data() {
        return {
            allSongList: [],    // 歌单数据
            pageSize:5,         // 每页显示10条数据
            currentPage:1,       // 当前页
            styles:[],              // 歌单风格
            activeName:''             // 当前风格名称
        }
    },
    mounted() {
        this.getAll();
        this.styles = styles;
        this.activeName = '热门推荐'
    },
    computed:{
        // 计算表格数据
        data(){
            if(this.allSongList != null){
                return this.allSongList.slice((this.currentPage - 1) * this.pageSize, this.currentPage * this.pageSize);
            }else{
                return this.allSongList = [];
            }
        }
    },
    methods: {
        getAll() {
            getAllSongList().then((res) => {
                if (res.data) {
                    this.allSongList = res.data;
                } else {
                    this.$notify({
                        message: '暂无该歌曲内容',
                        type: 'warning'
                    });
                }
            }).catch((error) => {
                console.log(error);
            });
        },
        getSongList(){
            getAllSongList().then((res) => {
                this.currentPage = 1;
                this.allSongList = res.data;
            }).catch((error) => {
                console.log(error);
            })
        },
        handleChangeView(name,index){ // 获取当前页
           this.activeName = name;
            if(name == '热门推荐'){
                this.getSongList();
            }else{
                this.handleChangeStyle(name);
            }
        },
        handleCurrentChange(val){
            this.currentPage = val;
        },
        handleChangeStyle(style){    // 根据风格显示对应的歌单
            selectSongListByStyle(style).then((res)=>{
                this.currentPage = 1;
                this.allSongList = res.data;
            }).catch((error)=>{
                console.log(error);
            });
        }
    }
}
</script>

<style lang="scss" scoped>
@import '@/assets/css/song-list.scss'
</style>