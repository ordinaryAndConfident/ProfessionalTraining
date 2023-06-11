<template>
    <div>
        <el-row :gutter="20" class="mgb20">
            <el-col :span="6">
                <el-card>
                    <div class="grid-content">
                        <div class="grid-content-center">
                            <div class="grid-num">{{consumerCount}}</div>
                            <div>用户总数</div>
                        </div>
                    </div>
                </el-card>
            </el-col>
            <el-col :span="6">
                <el-card>
                    <div class="grid-content">
                        <div class="grid-content-center">
                            <div class="grid-num">{{songsCount}}</div>
                            <div>歌曲总数</div>
                        </div>
                    </div>
                </el-card>
            </el-col>
            <el-col :span="6">
                <el-card>
                    <div class="grid-content">
                        <div class="grid-content-center">
                            <div class="grid-num">{{singerCount}}</div>
                            <div>歌手总数</div>
                        </div>
                    </div>
                </el-card>
            </el-col>
            <el-col :span="6">
                <el-card>
                    <div class="grid-content">
                        <div class="grid-content-center">
                            <div class="grid-num">{{ songListCount }}</div>
                            <div>歌单总数</div>
                        </div>
                    </div>
                </el-card>
            </el-col>
        </el-row>
        <el-row :gutter="20" class="mbg20">
            <el-col :span="12">
                <el-card class="card-container">
                    <div class="chart-title">
                        <h3>用户性别分布</h3>
                    </div>
                    <ve-pie :data="consumerBySex" :theme="options" :settings="chartPieSettings"></ve-pie>
                </el-card>
            </el-col>
            <el-col :span="12">
                <el-card class="card-container">
                    <div class="chart-title">
                        <h3>歌单风格分布</h3>
                    </div>
                    <ve-pie :data="songStyle" :settings="chartPieSettings"></ve-pie>
                </el-card>
            </el-col>
        </el-row>
        <el-row :gutter="20" class="mbg20">
            <el-col :span="12">
                <el-card class="card-container">
                    <div class="chart-title">
                        <h3>歌手性别分布</h3>
                    </div>
                    <ve-pie :data="singerSex" :theme="options1" :settings="chartPieSettings"></ve-pie>
                </el-card>
            </el-col>
            <el-col :span="12">
                <el-card class="card-container">
                    <div class="chart-title">
                        <h3>歌手国籍分布</h3>
                    </div>
                    <ve-pie :data="singerCountry" :settings="chartPieSettings"></ve-pie>
                </el-card>
            </el-col>
        </el-row>
    </div>
</template>

<script>
import { getAllConsumer, selectAllSongs, getAll, getAllSongList } from '@/api/http'
export default {
    data(){
        return{
            consumerCount: 0,
            songsCount: 0,
            singerCount: 0,
            songListCount:0,
            consumers:[],
            songs:[],
            singers:[],
            songList:[],
            consumerBySex:{
                columns:['性别','总数'],
                rows:[
                    {'性别': '男', '总数': 0},
                    {'性别': '女', '总数': 0},
                ]
            },
            options: {
                color: ['#87cefa', '#ffc0cb']
            },
            options1: {
                color: ['#cd84f1', '#ffcccc', '#18dcff']
            },
            chartPieSettings:{
                radius:85,
                offsetY:150,
                roseType:'radius'
            },
            songStyle:{ // 歌单风格
                columns: ['风格', '总数'],
                rows: [
                    { '风格': '华语', '总数': 0 },
                    { '风格': '粤语', '总数': 0 },
                    { '风格': '欧美', '总数': 0 },
                    { '风格': '日韩', '总数': 0 },
                    { '风格': 'BGM', '总数': 0 },
                    { '风格': '轻音乐', '总数': 0 },
                    { '风格': '治愈', '总数': 0 },
                    { '风格': '流行', '总数': 0 },
                    { '风格': '伤感', '总数': 0 },
                ]
            },
            singerSex:{
                columns: ['性别', '总数'],
                rows: [
                    { '性别': '男', '总数': 0 },
                    { '性别': '女', '总数': 0 },
                    { '性别': '保密', '总数': 0 },
                ]
            },
            singerCountry:{
                columns: ['国籍', '总人数'],
                rows: [
                    { '国籍': '美国', '总人数': 0 },
                    { '国籍': '新加坡', '总人数': 0 },
                    { '国籍': '意大利', '总人数': 0 },
                    { '国籍': '中国', '总人数': 0 },
                    { '国籍': '西班牙', '总人数': 0 },
                    { '国籍': '法国', '总人数': 0 },
                ]
            }
        }
    },
    mounted() {
        this.getAllConsumer();
        this.getAllSongs();
        this.getAllSingers();
        this.getAllSongLists();
    },
    methods: {
        getAllConsumer(){
            getAllConsumer().then((res)=>{
                this.consumers = res.data;
                this.consumerCount = res.data.length;
                this.consumerBySex.rows[0]['总数'] = this.handleConsumerSex(res.data,0); // 男生总数
                this.consumerBySex.rows[1]['总数'] = this.handleConsumerSex(res.data,1); // 女生总数
            }).catch((error)=>{
                console.log(error);
            });
        },
        getAllSongs(){
            selectAllSongs().then((res) => {
                this.songs = res.data;
                this.songsCount = res.data.length;
            }).catch((error) => {
                console.log(error);
            });
        },
        getAllSingers() {
            getAll().then((res) => {
                this.singers = res.data;
                this.singerCount = res.data.length;
                for(let item of res.data){
                    this.handleCountry(item.location);
                    this.handleSingerSex(item.sex);
                }
            }).catch((error) => {
                console.log(error);
            });
        },
        getAllSongLists() {
            getAllSongList().then((res) => {
                this.songList = res.data;
                this.songListCount = res.data.length;
                // 设置风格
                for (let i=0;i<res.data.length;i++) {
                    let styles = res.data[i].style.split("、");
                    for(let style of styles){
                        this.handleSongStyle(style);
                    }
                }
            }).catch((error) => {
                console.log(error);
            });
        },
        handleConsumerSex(consumers,sex){ // 根据性别获取用户数量
            let count = 0;
            for(let item of consumers){
                if(item.sex == sex){ // 0：男 1：女
                    count++;
                }
            }
            return count;
        },
        handleSongStyle(style) { // 根据歌单风格获取数量
            for (let item of this.songStyle.rows) {
                if (item['风格'].includes(style)) { // 包含风格名称
                    item['总数']++;
                }
            }
        },
        handleSingerSex(sex){
            switch(sex){
                case 1: sex = '女'; break;
                case 0: sex = '男'; break;
                case 3: sex = '保密';break;
                default:sex = "";
            }
            for (let item of this.singerSex.rows) {
                if (item['性别']== sex && sex != "") { // 0：男 1：女 3:保密
                    item['总数']++;
                }
            }
        },
        handleCountry(location){
            for (let item of this.singerCountry.rows) {
                if (item['国籍'] == location) { // 包含风格名称
                    item['总人数']++;
                }
            }
        }
    }
}
</script>

<style scoped>
.grid-content{
    display: flex;
    align-items: center;
    height: 65px;
}
.grid-content-center{
    flex: 1;
    text-align: center;
    font-size: 14px;
    color: darkgray;
}

.grid-num{
    font-size: 30px;
    font-weight: bold;
}

.card-container{
    width: 100%;
    height: 330px;
    margin-bottom: 20px;
}

.chart-title{
    margin-bottom: 15px;
    position: relative;
    top: -10px;
}
</style>
