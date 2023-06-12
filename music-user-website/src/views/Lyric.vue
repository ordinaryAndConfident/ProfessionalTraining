<template>
    <div class="container">
        <div class="box-title">
            <h2 class="lyric-title">{{ name }}</h2>
        </div>
        <div class="lyric-container">
            <ul v-if="lyric.length" ref="lrc">
                <li v-for="(item, index) in lyric" :key="index">
                    {{ item[1] }}
                </li>
            </ul>
            <div v-else class="no-lyric">
                <span>暂时没找到这首歌的歌词哟😥</span>
            </div>
        </div>
    </div>
</template>

<script>
import { mapGetters } from 'vuex';
import { mixin } from '@/mixins/utils'
export default {
    name: 'lyric',
    mixins: [mixin],
    data() {
        return {
            lyrics: [],    // 歌曲歌词
            songName: '',
            doms: { // 获取需要用到的 dom 节点
                ul:'',
                container:''  
            },
            containerHeight: 0, // 容器高度
            liHeight: 0,        // 每个li的高度
            maxOffset: 0        // 最大偏移量
        }
    },
    computed: {
        ...mapGetters([
            'currentTime',  // 当前歌曲播放时间
            'id',           // 当前歌曲id
            'lyric',         // 当前歌曲歌词
            'name',          // 歌曲名称
            'songsList',     // 当前歌曲列表
            'listIndex'      // 当前歌曲在歌单中的位置

        ])
    },
    created() {
        this.lyrics = this.lyric;
    },
    mounted(){
        // 获取dom
        this.doms.ul = document.querySelector('.lyric-container ul');
        this.doms.container = document.querySelector(".lyric-container");
        //容器高度
        this.containerHeight = this.doms.container.clientHeight;
        //每个li的高度
        this.liHeight = this.doms.ul.children[0].clientHeight;
        //最大偏移量
        this.maxOffset = this.doms.ul.clientHeight - this.containerHeight;
    },
    watch: {
        id() {
            this.lyrics = this.parseLyric(this.songsList[this.listIndex].lyric);   // 对应歌曲的歌词
        },
        currentTime() {
            if (this.lyrics.length > 0) {
                for (let i = 0; i < this.lyrics.length; i++) {
                    if (this.currentTime >= this.lyrics[i][0]) {  // 监听歌曲播放时间和歌词对应的时间进行匹配
                        for (let j = 0; j < this.lyrics.length; j++) {
                            this.setOffset();
                        }
                        if (i >= 0) {
                            this.setOffset();
                        }
                    }
                }
            }
        }
    },
    methods: {
        /**
         * 计算出，在当前播放器播放到第几秒的情况下
         * lrcData数组中，应该高亮显示的歌词下标
         * 如果没有任何一句歌词需要显示 则返回 -1
         */
        findActiveIndex() {
            // 播放器当前播放到第几秒的时间
            var currentTime = this.currentTime;
            for (var i = 0; i < this.lyrics.length; i++) {
                // 当前时间小于当前播放的时间
                if (currentTime < this.lyrics[i][0]) {
                    // 返回上一句歌词的下标
                    return i - 1;
                }
            }
            // 找遍了都没找到 说明播放到最后一句歌词了
            return this.lyrics.length - 1;
        },
        /**
         *  设置 ul(.lrc - list) 元素的偏移量
            高亮显示的歌词 决定了 ul 列表的偏移 
         */
        setOffset() {
            var index = this.findActiveIndex();
            var offset = this.liHeight * index + this.liHeight / 2 - this.containerHeight / 2;
            if (offset < 0) {
                offset = 0;
            }
            if (offset > this.maxOffset) {
                offset = this.maxOffset;
            }
            this.doms.ul.style.transform = `translateY(-${offset}px)`;
            // 去掉之前的高亮显示
            var li = this.doms.ul.querySelector('.active');
            if (li) {
                li.classList.remove('active');
            }
            // 设置当前需要高亮显示的li
            li = this.doms.ul.children[index];
            if (li) {
                li.classList.add('active');
            }
        }
    }
}
</script>

<style lang="scss" scoped>
.container {
    height: 525px;
    text-align: center;
    margin: auto;
    margin-top: 25px;
    width: 700px;
    background-color: #fff;
    border-radius: 12px;
    padding: 0 20px 50px 20px;
}

.box-title {
    width: 100%;
    height: 60px;
    line-height: 60px;
    text-align: center;
    border-bottom: 2px solid #bdc3c7;
}

.lyric-container {
    height: 500px;
    text-align: center;
    overflow: hidden;
    margin-top: 5px;
    overflow: hidden;
}

.lyric-container ul {
    transition: all 0.3s ease;
    list-style: none;
}

.lyric-container li {
    height: 38px;
    line-height: 38px;
    font-size: 18px;
    transition: all 0.3s ease;
}

.lyric-container li.active {
    color: #e13d3b;
    transform: scale(1.4);
}

.no-lyric {
    margin: 210px auto;
    text-align: center;
    span {
        font-size: 24px;
        text-align: center;
    }
}
</style>