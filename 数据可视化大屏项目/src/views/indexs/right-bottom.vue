<template>
  <div v-if="pageflag" class="right_center_wrap beautify-scroll-def">
    <component :is="components" :data="list" :class-option="defaultOption">
      <ul class="right_center ">
        <li class="right_center_item" v-for="(item, i) in list" :key="i">
          <span class="order_num">{{ i + 1 }}</span>
          <div class="inner_right">
            <div class="page_foot"></div>
            <div class="flex">
              <div class="info">
                <span class="labels ">产品：</span>
                <span class="contents zhuyao"> {{ item.materialName }}</span>
              </div>
              <div class="info">
                <span class="labels">型号：</span>
                <span class="contents "> {{ item.materialModel }}</span>
              </div>
              <div class="info">
                <span class="labels">规格：</span>
                <span class="contents warning"> {{ item.materialSpecification }}</span>
              </div>
              <div class="info">
                <span class="labels">单位：</span>
                <span class="contents warning"> {{ item.materialUnit }}</span>
              </div>
            </div>
            <div class="flex">
              <div class="info">
                <span class="labels">库存：</span>
                <span class="contents ciyao" style="font-size:12px"> {{ item.totalInventory }}</span>
              </div>
              <div class="info time">
                <span class="labels">已生产：</span>
                <span class="contents" style="font-size:12px"> {{ item.productQuantity }}</span>
              </div>
              <div class="info time">
                <span class="labels">待生产：</span>
                <span class="contents" style="font-size:12px"> {{ item.requireQuantity }}</span>
              </div>
              <div class="info time">
                <span class="labels">碳排放：</span>
                <span class="contents" style="font-size:12px"> {{ item.totalCarbonEmission }}</span>
              </div>
            </div>
          </div>
        </li>
      </ul>
    </component>
  </div>
  <Reacquire v-else @onclick="getData" style="line-height:200px" />

</template>

<script>
import { currentGET } from 'api/modules'
import vueSeamlessScroll from 'vue-seamless-scroll'  // vue2引入方式
import Kong from '../../components/kong.vue'
export default {
  components: { vueSeamlessScroll, Kong },

  data() {
    return {
      list: [],
      pageflag: true,
      components: vueSeamlessScroll,
      defaultOption: {
        ...this.$store.state.setting.defaultOption,
        limitMoveNum: 3,
        singleHeight: 250,
        step: 0,
      }
    };
  },
  computed: {
  },
  created() {
    this.getData()
  },

  mounted() { },
  methods: {
    //获取报表数据
    getData() {
      this.pageflag = true
      currentGET('big5', {
        pageNum: 1,
        pageSize: 50,
      }).then(res => {
        if (res.success) {
          this.list = res.rows;
          let timer = setTimeout(() => {
            clearTimeout(timer)
            this.defaultOption.step = this.$store.state.setting.defaultOption.step
          }, this.$store.state.setting.defaultOption.waitTime);
        } else {
          this.pageflag = false
        }
      })
    },

  },
};
</script>
<style lang='scss' scoped>
.right_center {
  width: 100%;
  height: 100%;

  .right_center_item {
    display: flex;
    align-items: center;
    justify-content: center;
    height: auto;
    padding: 10px;
    font-size: 14px;
    color: #fff;

    .order_num {
      margin: 0 20px 0 -20px;
    }

    .inner_right {
      position: relative;
      height: 100%;
      width: 400px;
      flex-shrink: 0;
      line-height: 1.5;

      .page_foot {
        position: absolute;
        height: 2px;
        width: 104%;
        background-image: url("../../assets/img/zuo_xuxian.png");
        bottom: -12px;
        left: -2%;
        background-size: cover;
      }
    }

    .info {
      width: 150px;
      margin-right: 5px;
      display: flex;
      align-items: center;

      .labels {
        flex-shrink: 0;
        font-size: 12px;
        color: rgba(255, 255, 255, 0.6);
      }

      .zhuyao {
        color: $primary-color;
        font-size: 15px;
      }

      .ciyao {
        color: rgba(255, 255, 255, 0.8);
      }

      .warning {
        color: #e6a23c;
        font-size: 15px;
      }
    }
  }
}

.right_center_wrap {
  overflow: hidden;
  width: 100%;
  height: 250px;
}

.overflow_y_auto {
  overflow-y: auto;
}
</style>