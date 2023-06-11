<template>
    <div class="singer">
        <div class="singer-header">
            <ul>
                <li v-for="(item, index) in singerStyle" :key="index" :class="{ active: item.name == activeName }"
                    @click="handleChangeView(item)" ref="liStyle">
                    <h4 v-if="item.name == '推荐歌手'" style="color:#C10D0C;">{{ item.name }}</h4>
                    <span v-else>
                        {{ item.name }}
                    </span>
                </li>
            </ul>
        </div>
        <div>
            <content-list :content-list="data" />
            <div class="pagination">
                <el-pagination @current-change="handleCurrentChange" background layout="total,prev,pager,next"
                    :current-page="currentPage" :page-size="pageSize" :total="allSinger.length"></el-pagination>
            </div>
        </div>
    </div>
</template>

<script>
import ContentList from '@/components/ContentList.vue'
import { getAll, getSingerOfSex } from '@/api/http'
import { singerStyle } from '@/assets/data/singer'
export default {
    name: 'singer-list',
    components: {
        ContentList
    },
    data() {
        return {
            allSinger: [],           // 歌手数据
            pageSize: 5,            // 每页显示10条数据
            currentPage: 1,        // 当前页
            singerStyle: [],      // 歌手性别
            activeName: ''       // 当前风格名称
        }
    },
    mounted() {
        this.getAllSinger();
        this.singerStyle = singerStyle;
        this.activeName = '推荐歌手'
    },
    computed: {
        // 计算表格数据
        data() {
            if (this.allSinger != null) {
                return this.allSinger.slice((this.currentPage - 1) * this.pageSize, this.currentPage * this.pageSize);
            } else {
                return this.allSinger = [];
            }
        }
    },
    methods: {
        getAllSinger() {
            getAll().then((res) => {
                if (res.data) {
                    this.allSinger = res.data;
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
        handleChangeView(item) { // 获取当前页
            this.activeName = item.name;
            if (item.name == '推荐歌手') {
                this.getAllSinger();
            } else {
                this.handleChangeStyle(item.type);
            }
        },
        handleCurrentChange(val) {
            this.currentPage = val;
        },
        handleChangeStyle(sex) {    // 根据性别显示对应的歌手
            getSingerOfSex(sex).then((res) => {
                this.currentPage = 1;
                this.allSinger = res.data;
            }).catch((error) => {
                console.log(error);
            });
        }
    }
}
</script>

<style lang="scss" scoped>
@import '@/assets/css/singer.scss'
</style>