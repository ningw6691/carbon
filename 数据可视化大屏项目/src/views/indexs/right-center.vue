<template>
  <div class="right_bottom">
    <dv-capsule-chart :config="config" style="width:100%;height:260px" />
  </div>
</template>

<script>
import { currentGET } from 'api/modules'
export default {
  data() {
    return {
      config: {
        showValue: true,
        unit: "万",
        data: []
      },

    };
  },
  created() {
    this.getData();

  },
  beforeDestroy() {
    this.clearData()
  },
  methods: {
    //清理报表数据
    clearData() {
      if (this.timer) {
        clearInterval(this.timer)
        this.timer = null
      }
    },
    //轮询
    switper() {
      if (this.timer) {
        return
      }
      let looper = (a) => {
        this.getData()
      };
      this.timer = setInterval(looper, this.$store.state.setting.echartsAutoTime);
    },
    //获取报表数据
    getData() {
      this.pageflag = true
      currentGET('big7').then(res => {
        if (!this.timer) {
        }
        if (res.success) {
          this.config = {
            ...this.config,
            data: res.data
          }
          this.switper()
        } else {
          this.pageflag = false
        }
      })
    },
  },
};
</script>
<style lang='scss' scoped>
.list_Wrap {
  height: 100%;
  overflow: hidden;

  ::v-deep  .kong {
    width: auto;
  }
}

.sbtx_swiper_class {
  .img_wrap {
    overflow-x: auto;
  }

}

.right_bottom {
  box-sizing: border-box;
  padding: 0 16px;

  .searchform {
    height: 80px;
    display: flex;
    align-items: center;
    justify-content: center;

    .searchform_item {
      display: flex;
      justify-content: center;
      align-items: center;

      label {
        margin-right: 10px;
        color: rgba(255, 255, 255, 0.8);
      }

      button {
        margin-left: 30px;
      }

      input {}
    }
  }

  .img_wrap {
    display: flex;
    // justify-content: space-around;
    box-sizing: border-box;
    padding: 0 0 20px;
    // overflow-x: auto;

    li {
      width: 105px;
      height: 137px;
      border-radius: 6px;
      overflow: hidden;
      cursor: pointer;
      // background: #84ccc9;
      // border: 1px solid #ffffff;
      overflow: hidden;
      flex-shrink: 0;
      margin: 0 10px;

      img {
        flex-shrink: 0;
      }
    }
  }
}
</style>