<template>
  <div class="swiper">
    <el-carousel :interval="3000" arrow="always" indicator-position="outside">
      <el-carousel-item v-for="(item, index) in swiperList" :key="index">
        <img :src="getBaseUrl(item.url)" style="width:100%" />
      </el-carousel-item>
    </el-carousel>
  </div>
</template>

<script>
import { getHomeBanner } from "@/api/http";
import { mixin } from "@/mixins/utils";
export default {
  name: "swiper",
  mixins: [mixin],
  data() {
    return {
      swiperList: []
    };
  },
  created() {
    getHomeBanner().then(res => {
      if (res.code == 200) {
        this.swiperList = res.data;
      }
    });
  }
};
</script>

<style scoped>
.swiper {
  width: 80%;
  margin: auto;
  margin-top: 40px;
}
</style>
