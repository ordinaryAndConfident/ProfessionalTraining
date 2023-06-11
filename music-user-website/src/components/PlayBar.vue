<template>
    <div class="play-bar" :class="{ show: !toggle }">
        <div @click="toggle = !toggle" class="item-up" :class="{ turn: !toggle }">
            <svg class="icon">
                <use xlink:href="#icon-jiantou-xia-cuxiantiao"></use>
            </svg>
        </div>
        <div class="kongjian">
            <!-- 上一首 -->
            <div class="item" @click="prev">
                <svg class="icon">
                    <use xlink:href="#icon-ziyuanldpi"></use>
                </svg>
            </div>
            <!-- 播放 -->
            <div class="item" @click="controlMusicState">
                <svg class="icon">
                    <use :xlink:href="playButtonUrl"></use>
                </svg>
            </div>
            <!-- 下一首 -->
            <div class="item" @click="next">
                <svg class="icon">
                    <use xlink:href="#icon-ziyuanldpi1"></use>
                </svg>
            </div>
            <!-- 歌曲图片 -->
            <div class="item-img" @click="toLyric">
                <img :src="picUrl" alt="">
            </div>
            <!-- 播放进度条 -->
            <div class="playing-speed">
                <!-- 开始时间 -->
                <div class="current-time">{{ nowTime }}</div>
                <div class="progress-box">
                    <div class="item-song-title">
                        <div>{{ this.name }}</div>
                        <div>{{ this.artist }}</div>
                    </div>
                    <div ref="progress" class="progress" @mousemove="mousemove">
                        <!--进度条 -->
                        <div class="bg" ref="bg" @click="updateProcess">
                            <div ref="currentProgress" class="cur-progress" :style="{ width: currenLength + '%' }"> </div>
                        </div>
                        <!-- 拖动进度条 -->
                        <div class="idot" ref="idot" :style="{ left: currenLength + '%' }" @mousedown="mousedown"
                            @mouseup="mouseup"></div>
                    </div>
                </div>
                <!-- 播放结束时间 -->
                <div class="left-time">{{ endTime }}</div>
                <!-- 音量 -->
                <div class="item item-volume">
                    <svg class="icon" v-if="volume > 0 && volume <= 100">
                        <use xlink:href="#icon-yinliang1"></use>
                    </svg>
                    <svg class="icon" v-else>
                        <use xlink:href="#icon-yinliangjingyinheix"></use>
                    </svg>
                    <el-slider class="volume" v-model="volume" :vertical="true"></el-slider>
                </div>
                <!--收藏 -->
                <div class="item" @click="collection">
                    <svg class="icon" :class="{ active: isFavorite }">
                        <use xlink:href="#icon-xihuan-shi"></use>
                    </svg>
                </div>
                <!-- 下载 -->
                <div class="item" @click="download">
                    <svg class="icon">
                        <use xlink:href="#icon-xiazai"></use>
                    </svg>
                </div>
                <!-- 当前播放的歌曲列表 -->
                <div class="item" @click="showAside">
                    <svg class="icon">
                        <use xlink:href="#icon-liebiao"></use>
                    </svg>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { mapGetters } from 'vuex';
import { mixin } from '@/mixins/utils';
import { downloadSong, setFavorite } from '@/api/http'
export default {
    name: "play-bar",
    mixins: [mixin],
    data() {
        return {
            nowTime: '00:00',  // 当前播放进度时间
            endTime: '00:00',  // 当前歌曲总时长
            currenLength: 0,  // 进度条位置
            progressLenth: 0,  // 进度条总长度
            mouseStartX: 0,   //拖拽开始位置
            tag: false,       // 拖拽开始结束标志，开始拖拽时，为true 否则为 false
            volume: 10,       // 系统默认音量
            toggle: true    // 播放器默认不隐藏

        }
    },
    computed: {
        ...mapGetters([
            'isPlay', // 播放状态
            'id',  // 歌曲id
            'url',  // 播放歌曲地址
            'playButtonUrl', // 播放图标
            'picUrl',  // 歌曲图片
            'name',  // 歌曲名称
            'artist',  // 歌手名称
            'duration',  // 音乐时常
            'currentTime', // 当前音乐播放位置
            'songsList',     // 歌曲列表
            'listIndex',     // 当前播放歌曲在列表中的位置
            'autoNext',      // 是否自动播放下一曲 默认是
            'isLogin',       // 是否登录
            'consumerId',           // 用户id
            'isFavorite'    // 当前歌曲是否被收藏
        ])
    },
    watch: {
        // 监控播放状态的图标
        isPlay() {
            if (this.isPlay) {
                this.$store.commit("setPlayButtonUrl", "#icon-zanting");
            } else {
                this.$store.commit("setPlayButtonUrl", "#icon-bofang");
            }
        },
        currentTime() {
            this.nowTime = this.formatTime(this.currentTime);
            this.endTime = this.formatTime(this.duration);
            this.currenLength = (this.currentTime / this.duration) * 100;
        },
        volume() { // 音量发生改变时
            this.$store.commit("setVolume", this.volume / 100);
        },
        autoNext() {
            this.next(); // 下一首
        }
    },
    mounted() {
        this.progressLenth = this.$refs.progress.getBoundingClientRect().width;
        // 音量点击事件
        document.querySelector(".item-volume").addEventListener('click', function (event) {
            document.querySelector(".volume").classList.add("show-volume");
            event.stopPropagation();
        }, false);
        //  隐藏音量滑块
        document.querySelector(".volume").addEventListener('click', function (event) {
            event.stopPropagation();
        }, false);
        // 点击页面其他空白地方 也隐藏音量滑块
        document.addEventListener('click', function () {
            document.querySelector(".volume").classList.remove("show-volume");
        }, false);
    },
    methods: {
        // 控制音乐的播放和暂停
        controlMusicState() {
            if (this.isPlay) {
                this.$store.commit("setIsPlay", false);
            } else {
                this.$store.commit("setIsPlay", true);
            }
        },
        // 解析歌曲时间
        formatTime(time) {
            let format = parseInt(time);
            let res = "";
            let hour = parseInt(format / 3600); // 小时
            let minute = parseInt((format / 60) % 60); // 分钟
            let second = parseInt(format % 60); // 秒
            if (hour > 0) {
                if (hour < 10) {
                    res = '0' + hour + ':';
                } else {
                    res = hour + ':';
                }
            }
            if (minute > 0) {
                if (minute < 10) {
                    res += '0' + minute + ':';
                } else {
                    res += minute + ':';
                }
            } else {
                res += '00:';
            }
            if (second > 0) {
                if (second < 10) {
                    res += '0' + second;
                } else {
                    res += second;
                }
            } else {
                res += '00';
            }
            return res;
        },
        mousemove(event) {
            if (!this.duration) {
                return false;
            }
            if (this.tag) {
                let moveDistance = event.clientX - this.mouseStartX; //进度条移动的距离
                let currenLength = this.$refs.currentProgress.getBoundingClientRect().width;  // 增加进度条的宽度
                let newPercent = ((moveDistance + currenLength) / this.progressLenth) * 100 // 计算进度条长度
                if (newPercent > 100) {
                    newPercent = 100;
                } else if (newPercent < 0) {
                    newPercent = 0;
                }
                this.currenLength = newPercent;
                this.mouseStartX = event.clientX;   // 进度条移动
                this.changeSongTime(newPercent);    // 改变歌曲进度
            }
        },
        mousedown(event) {  // 拖拽开始
            this.mouseStartX = event.clientX;
            this.tag = true;
        },
        mouseup() { // 拖拽过程中时
            this.tag = false;
        },
        changeSongTime(percent) { // 修改歌曲进度
            let newCurrent = (percent * 0.01) * this.duration;
            this.$store.commit("setChangeTime", newCurrent);
        },
        updateProcess(event) { // 点击播放条切换播放进度
            if (!this.tag) {
                let currentLength = this.$refs.bg.offsetLeft; // 进度条左侧坐标
                let newPercent = ((event.clientX - currentLength) / this.progressLenth) * 100;
                if (newPercent > 100) {
                    newPercent = 100;
                } else if (newPercent < 0) {
                    newPercent = 0;
                }
                this.currenLength = newPercent;
                this.changeSongTime(newPercent);
            }
        },
        showAside() {    // 显示播放中的歌曲列表页面
            this.$store.commit("setShowAside", true);
        },
        prev() {     // 上一曲
            if (this.listIndex != -1 && this.songsList.length > 1) { // 当前歌曲只有一首歌或者不存在的歌曲的情况
                if (this.listIndex > 0) { // 不是第一首歌 
                    this.$store.commit("setListIndex", this.listIndex - 1); // 返回上一首
                } else {
                    // 第一首歌
                    this.$store.commit("setListIndex", this.songsList.length - 1); // 返回上一首
                }
                this.toplay(this.songsList[this.listIndex].url); // 播放音乐
            }
        },
        next() {     // 下一曲
            if (this.listIndex != -1 && this.songsList.length > 1) { // 当前歌曲只有一首歌或者不存在的歌曲的情况
                if (this.listIndex < this.songsList.length - 1) { // 不是最后一首歌 
                    this.$store.commit("setListIndex", this.listIndex + 1); // 返回下一首
                } else {
                    // 当前最后一首歌
                    this.$store.commit("setListIndex", 0); // 返回上一首
                }
                this.toplay(this.songsList[this.listIndex].url); // 播放音乐
            }
        },
        // 播放歌曲
        toplay(url) {
            if (url && url != this.url) {
                this.$store.commit("setId", this.songsList[this.listIndex].id)
                this.$store.commit("setUrl", this.$store.state.config.HOST + "/" + url);
                this.$store.commit("setPicUrl", this.$store.state.config.HOST + "/" + this.songsList[this.listIndex].picture);
                this.$store.commit("setName", this.handleSongName(this.songsList[this.listIndex].name));
                this.$store.commit("setArtist", this.handleSingerName(this.songsList[this.listIndex].name));
                this.$store.commit("setLyric", this.parseLyric(this.songsList[this.listIndex].lyric));
                this.$store.commit("setIsFavorite", false); // 收藏状态判断
                this.handleFavorite(this.id);
            }
        },
        toLyric() {
            this.$router.push({ path: `/lyric` });     // 跳转到歌词页面
        },
        download() { // 下载歌曲
            this.$message({
                message: '文件下载中,请耐心等待...',
                type: 'success'
            });
            downloadSong(this.url).then((res) => {
                console.log(res.data);
                let song = res.data;
                let elementLink = document.createElement('a');
                elementLink.download = `${this.artist}-${this.name}.mp3`;
                elementLink.style.display = 'none';
                // 把字符内容转换成 blob格式
                let blob = new Blob([song]);
                elementLink.href = URL.createObjectURL(blob);
                // 把链接地址添加到 document里面
                document.body.appendChild(elementLink);
                // 点击事件
                elementLink.click();
                // 完成后 移除改控件
                //document.body.removeChild(elementLink);
            }).catch((error) => {
                console.log(error);
            });
        },
        collection() {   // 收藏
            if (!this.isFavorite) {
                if (this.isLogin && this.id != null) {
                    let params = {
                        consumerId: this.consumerId,
                        songId: this.id,
                        type: 0
                    }
                    setFavorite(params).then((res) => {
                        if (res.code == 200) {
                            this.$notify({
                                message: res.msg,
                                type: 'success'
                            });
                            this.$store.commit("setIsFavorite", true); // 收藏成功改变爱心颜色
                        } else {
                            this.$notify({
                                message: res.msg,
                                type: 'error'
                            });
                        }
                    })
                } else {
                    this.$notify({
                        message: '请先登录后再进行收藏哟!',
                        type: 'warning'
                    });
                }
            } else {
                this.$notify({
                    message: '您已经收藏该歌曲了哟',
                    type: 'warning'
                });
                return false;
            }
        }
    }
}
</script>

<style lang="scss" scoped>
@import '@/assets/css/play-bar.scss'
</style>