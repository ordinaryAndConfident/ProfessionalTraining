<template>
    <div>
        <headers></headers>
        <asider></asider>
        <div class="content" :class="{'content-collapse':collapse}">
            <router-view></router-view>
        </div>
    </div>
</template>

<script>
import Headers from './Headers.vue'
import Asider from './Asider.vue'
import bus from '@/assets/js/bus'

export default {
    data(){
        return{
            collapse:false
        }
    },
    components: {
        Headers,
        Asider
    },
    created(){
         // 通过 bus 进行组件间的通信，来折叠侧边栏
        bus.$on('collapse', msg => {
            this.collapse = msg
        })
    }
}
</script>

<style>
</style>