<template>
    <!-- <div class="sidebar">
        <el-menu class="sidebar-el-menu" :default-active="onRoutes" :collapse="collapse" background-color="#334256"
            text-color="#fff" active-text-color="#20a0ff" router>
            <template v-for="item in items">
                <template>
                    <el-menu-item :index="item.index" :key="item.index">
                        <i :class="item.icon" />
                        <span>{{ item.title }}</span>
                    </el-menu-item>
                </template>
            </template>
        </el-menu>
    </div> -->

    <div class="sidebar">
        <el-menu class="sidebar-el-menu" background-color="#334256" :collapse="collapse" :default-active="onRoutes"
            text-color="#ffffff" active-text-color="#20a0ff" router>
            <menu-tree :menuList="menuList"></menu-tree>
        </el-menu>
    </div>
</template>

<script>
import bus from '@/assets/js/bus';
import MenuTree from './MenuTree.vue';

export default {
    name: "Aside",
    components:{
        MenuTree
    },
    data() {
        return {
            collapse: false,
            menuList: [
                {
                    id: '1',
                    parentid: '0',
                    icon: 'el-icon-s-home',
                    index: '',
                    title: '系统管理',
                    children: [
                        {
                            id: '2',
                            parentid: '1',
                            icon: 'el-icon-odometer',
                            index: '/info',
                            title: '首页',
                        },
                        {
                            id: '3',
                            parentid: '1',
                            icon: 'el-icon-picture',
                            index: '/banner',
                            title: '轮播管理',
                        }
                    ]
                },
                {
                    id: '4',
                    parentid: '0',
                    icon: 'el-icon-s-custom',
                    index: '/consumer',
                    title: '用户管理'
                }, {
                    id: '5',
                    parentid: '0',
                    icon: 'el-icon-user-solid',
                    index: '/singerlist',
                    title: '歌手管理'
                },
                {
                    id: '6',
                    parentid: '0',
                    icon: 'el-icon-headset',
                    index: '/songlist',
                    title: '歌单管理'
                }
            ]
        };
    },
    computed: {
        onRoutes() {
            return this.$route.path
        }
    },
    created() {
        // 通过 bus 进行组件间的通信，来折叠侧边栏
        bus.$on('collapse', msg => {
            this.collapse = msg
        })
    },
    methods: {

    },
};
</script>

<style scoped>
ul,
li {
    list-style: none;
    display: block;
}

.sidebar {
    display: block;
    position: absolute;
    left: 0;
    top: 70px;
    bottom: 0;
    background-color: #334256;
    overflow-y: scroll;
}

.sidebar-el-menu:not(.el-menu--collapse) {
    width: 180px;
}

.sidebar::-webkit-scrollbar {
    width: 0;
}

.sidebar>ul {
    height: 100%;
}
</style>
