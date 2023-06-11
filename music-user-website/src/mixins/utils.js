import { mapGetters } from 'vuex';
import { selectSongLikeName, getConsumerFavorite } from '@/api/http';
export const mixin = {
    computed: {
        ...mapGetters([
            'isLogin',  // 登录状态
            'consumerId'        // 用户id
        ])
    },
    methods: {
        // 获取图片地址
        attachImageUrl(src) {
            return src ? this.$store.state.config.HOST + "/" + src : require('@/assets/img/user.png');
        },
         // 根据相对地址获取绝对地址
        getBaseUrl(url) {
            return `${this.$store.state.config.HOST}/${url}`
        },
        // 根据歌曲名称模糊查询歌曲
        getSong() {
            selectSongLikeName(this.$route.query.keywords).then((res) => {
                if (res.code == 200) {
                    this.$store.commit('setSongsList', res.data); // 把歌曲缓存到 session中
                    this.$message({
                        message: '找到了' + res.data.length + '首歌曲',
                        showClose: true,
                        type: 'success'
                    });
                } else {
                    this.$store.commit('setSongsList', []); // 把歌曲缓存到 session中
                    this.$message({
                        message: '暂时没有找到符合条件的歌曲',
                        showClose: true,
                        type: 'error'
                    });
                }
            }).catch((error) => {
                console.log(error);
            })
        },
        // 获取歌曲名称
        handleSongName(str) {
            let arr = str.split("-");
            return arr[0];
        },
        // 获取歌手名称
        handleSingerName(str) {
            let arr = str.split("-");
            return arr[1];
        },
        // 播放歌曲
        playSong(id, url, pic, index, name, lyric) {
            this.$store.commit("setId", id)
            this.$store.commit("setUrl", this.$store.state.config.HOST + "/" + url);
            this.$store.commit("setPicUrl", this.$store.state.config.HOST + "/" + pic);
            this.$store.commit("setListIndex", index);
            this.$store.commit("setName", this.handleSongName(name));
            this.$store.commit("setArtist", this.handleSingerName(name));
            this.$store.commit("setLyric", this.parseLyric(lyric));
            this.$store.commit("setIsFavorite", false); // 收藏状态判断
            this.handleFavorite(id);
        },
        // 解析歌词
        parseLyric(lyric) {
            let lines = lyric.split("\n");
            let pattern = /\[\d{2}:\d{2}.(\d{3}|\d{2})\]/g; // 歌词时间匹配规则
            let res = [];
            if (!(/\[.+\]/.test(lyric))) { // 如果歌词不满足匹配格式 直接返回原歌词
                return [[0, lyric]];
            }
            while (!pattern.test(lines[0])) {
                lines = lines.slice(1); // 去掉前面格式不正确的歌词行
            }
            // 形成新的歌词数组，格式：[歌词时间,歌词]
            for (let item of lines) {
                let time = item.match(pattern) // 保持歌曲时间段
                if (time != null) {
                    let val = item.replace(pattern, ''); // 去掉歌词前面的时间
                    for (let iterator of time) {
                        let t = iterator.slice(1, -1).split(":"); // 取出时间进行换算
                        if (val != '') {
                            res.push([parseInt(t[0], 10) * 60 + parseFloat(t[1]), val]); // 按十进制转换时间
                        }
                    }
                }
            }
            res.sort(function (a, b) { // 对格式化好的[时间]进行排序操作
                return a[0] - b[0];
            });
            return res;
        },
        changeIndex(index) { // 改变 activeName 状态
            this.$store.commit("setActiveName", index);
        },
        attachSex(sex) { // 解析性别
            if (sex == 0) {
                return '男'
            } else if (sex == 1) {
                return '女'
            } else {
                return '保密'
            }
        },
        attachTime(time) {
            if (time != null) {
                return time.substring(0, 10);
            } else {
                return "";
            }
        },
        handleFavorite(songId) {
            if (this.isLogin) {
                getConsumerFavorite(this.consumerId).then((res) => {
                    if (res.code == 200) {
                        for (let item of res.data) {
                            if (item.songId == songId) {
                                this.$store.commit("setIsFavorite", true); // 收藏状态判断
                                break;
                            }
                        }
                    }
                }).catch((error) => {
                    console.log(error);
                })
            }
        }
    },
}