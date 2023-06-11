<template>
    <div>
        <template v-for="item in menuList">
            <!-- 有次级菜单的,则展开子选项-->
            <el-submenu v-if="item.children && item.children.length >= 1" :key="item.index" :index="item.index">
                <template slot="title">
                    <i :class="item.icon"></i>
                    <span slot="title">{{ item.title }}</span>
                </template>
                <!-- 递归,实现无限级展开 -->
                <menu-tree :menuList="item.children"></menu-tree>
            </el-submenu>
            <!-- 没有次级菜单的 -->
            <el-menu-item v-if="!item.children" :key="item.id" :index="item.index">
                <template>
                    <i :class="item.icon"></i>
                    <span slot="title">{{ item.title }}</span>
                </template>
            </el-menu-item>
        </template>
    </div>
</template>

<script>
export default {
    name: 'MenuTree',
    props: {
        menuList: {
            type: Array,
            default() {
                return []
            }
        }
    },
    methods: {

    }
}
</script>

<style>
/*隐藏文字*/ 
  .el-menu--collapse .el-submenu__title span{ 
    display: none; 
  }
  /*隐藏 > */
  .el-menu--collapse .el-submenu__title .el-submenu__icon-arrow{ 
    display: none; 
  }

</style>>