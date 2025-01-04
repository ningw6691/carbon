<template>
  <div v-if="pageflag" class="left_boottom_wrap beautify-scroll-def">
    <component :is="components" :data="list" :class-option="defaultOption">
      <ul class="left_boottom">
        <li class="left_boottom_item" v-for="(item, i) in list" :key="i">
          <span class="order_num dou_dong">{{ i + 1 }}</span>
          <div class="inner_right">
            <div class="page_foot"></div>
            <div class="flex">
              <div class="info">
                <span class="labels">物料：</span>
                <span class="contents zhu_yao dou_dong material_name">
                  {{ item.materialName }}</span>
              </div>
              <div class="info">
                <span class="labels">当前库存：</span>
                <span class="contents " style="font-size: 12px">
                  {{ item.totalInventory }}</span>
              </div>
            </div>

            <span v-if="item.minInventory || item.maxInventory" class="types dou_dong" :class="{
              type_red: item.maxInventory && item.totalInventory > item.maxInventory,
              type_green: item.minInventory && item.totalInventory < item.minInventory,
            }">{{ (item.minInventory && item.totalInventory < item.minInventory) ? "不足" : (item.maxInventory &&
              item.totalInventory > item.maxInventory) ? "溢出" : "正常" }}</span>
            <span v-else class="types dou_dong">正常</span>
            <div class="info address_wrap">
              <span class="labels">仓库：</span>
              <span class="contents ciyao" style="font-size: 12px">
                {{ item.warehouseName }}</span>
            </div>
          </div>
        </li>
      </ul>
    </component>
  </div>

  <Reacquire v-else @onclick="getData" style="line-height: 200px" />
</template>

<script>
import { currentGET } from "api";
import vueSeamlessScroll from "vue-seamless-scroll"; // vue2引入方式
import Kong from "../../components/kong.vue";
export default {
  components: { vueSeamlessScroll, Kong },
  data() {
    return {
      list: [],
      pageflag: true,
      components: vueSeamlessScroll,
      defaultOption: {
        ...this.$store.state.setting.defaultOption,
        singleHeight: 240,
        limitMoveNum: 5,
        step: 100,
      },
    };
  },
  computed: {
  },
  created() {
    this.getData();
  },

  mounted() { },
  methods: {
    //获取报表数据
    getData() {
      this.pageflag = true;
      currentGET("big3", {
        pageNum: 1,
        pageSize: 20,
      }).then((res) => {
        if (res.success) {
          this.list = res.rows;
          let timer = setTimeout(() => {
            clearTimeout(timer);
            this.defaultOption.step =
              this.$store.state.setting.defaultOption.step;
          }, this.$store.state.setting.defaultOption.waitTime);
        } else {
          this.pageflag = false
        }
      });
    },
  },
};
</script>
<style lang='scss' scoped>
.left_boottom_wrap {
  overflow: hidden;
  width: 100%;
  height: 100%;
}

.dou_dong {
  //  vertical-align:middle;
  overflow: hidden;
  -webkit-backface-visibility: hidden;
  -moz-backface-visibility: hidden;
  -ms-backface-visibility: hidden;
  backface-visibility: hidden;
}

.overflow_y_auto {
  overflow-y: auto;
}

.left_boottom {
  width: 100%;
  height: 100%;

  .left_boottom_item {
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 8px;
    font-size: 14px;
    margin: 10px 0;

    .order_num {
      width: 20px;
      margin: 0 16px 0 -20px;
    }

    .info {
      margin-right: 10px;
      display: flex;
      align-items: center;
      color: #fff;

      .labels {
        flex-shrink: 0;
        font-size: 12px;
        color: rgba(255, 255, 255, 0.6);
      }

      .zhu_yao {
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

    .inner_right {
      position: relative;
      height: 100%;
      width: 380px;
      flex-shrink: 0;
      line-height: 1;
      display: flex;
      align-items: center;
      justify-content: space-between;
      flex-wrap: wrap;

      .page_foot {
        position: absolute;
        height: 2px;
        width: 104%;
        background-image: url("../../assets/img/zuo_xuxian.png");
        bottom: -10px;
        left: -2%;
        background-size: cover;
      }

      .address_wrap {
        width: 100%;
        display: flex;
        margin-top: 8px;
      }
    }

    .material_name {
      color: #1890ff;
      font-weight: 900;
      font-size: 15px;
      width: 80px;
      flex-shrink: 0;
    }

    .time {
      font-size: 12px;
      // color: rgba(211, 210, 210,.8);
      color: #fff;
    }

    .address {
      font-size: 12px;
      cursor: pointer;
      // @include text-overflow(1);
    }

    .types {
      width: 30px;
      flex-shrink: 0;
    }

    .type_red {
      color: #fc1a1a;
    }

    .type_green {
      color: #29fc29;
    }
  }
}
</style>