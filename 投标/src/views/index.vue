<template>
  <div class="app-container home">
    <div style="width: 100%">
      <swiper class="swiper" :options="swiperOption">
        <swiper-slide><img :src="swiperImage1" class="bannerImage"/></swiper-slide>
        <swiper-slide><img :src="swiperImage2" class="bannerImage"/></swiper-slide>
        <swiper-slide><img :src="swiperImage3" class="bannerImage"/></swiper-slide>
      </swiper>
    </div>
    <div style="margin-top: 30px">
      <el-row>
        <el-col :span="12" style="display: flex;justify-content: center;">
          <home-card title="招标预告" :source-data="bidData1" :category="1"/>
        </el-col>
        <el-col :span="12" style="display: flex;justify-content: center;">
          <home-card title="招标公告" :source-data="bidData2" :category="2"/>
        </el-col>
      </el-row>
      <el-row style="margin-top: 30px">
        <el-col :span="12" style="display: flex;justify-content: center;">
          <home-card title="中标公告" :source-data="bidData3" :category="3"/>
        </el-col>
        <el-col :span="12" style="display: flex;justify-content: center;">
          <home-card title="企业采购" :source-data="bidData4" :category="4"/>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import HomeCard from "@/components/Platform/HomeCard.vue";
import {swiper, swiperSlide} from 'vue-awesome-swiper'
// Import Swiper styles
import 'swiper/dist/css/swiper.css'

import {listBidding} from '@/api/bid/bidding'

export default {
  name: "index",
  components: {swiper, swiperSlide, HomeCard},
  data() {
    return {
      swiperImage1: require('@/assets/images/swiper1.png'),
      swiperImage2: require('@/assets/images/swiper2.png'),
      swiperImage3: require('@/assets/images/swiper3.png'),
      swiperOption: {
        slidesPerView: 'auto',
        centeredSlides: true,
        spaceBetween: 30,
        loop: true,
        activeIndex: 2,
      },
      queryParams: {
        pageNum: 1,
        pageSize: 5,
        category: null
      },
      bidData1: null,
      bidData2: null,
      bidData3: null,
      bidData4: null,
    }
  },
  async created() {

  },
  mounted() {
    this.$nextTick(async () => {
      this.bidData1 = await this.getBidData(1);
      this.bidData2 = await this.getBidData(2);
      this.bidData3 = await this.getBidData(3);
      this.bidData4 = await this.getBidData(4);
    })

  },
  methods: {
    async getBidData(category) {
      const that = this;
      const params = that.queryParams;
      params.category = category;
      return listBidding(params);
    }
  }

}
</script>

<style scoped lang="scss">

.swiper {
  height: 240px;
  //width: 5000px;
}

.swiper-wrapper {
  width: 1000px;
}

//
//.swiper-container {
//  width: 5000px;
//
//}

.swiper-slide {
  width: 1000px;
  //margin-left: 30px;
}

.bannerImage {
  width: 1000px;
  height: 240px;
  border-radius: 10px;
  opacity: 1;
  border: 0;
}


</style>

