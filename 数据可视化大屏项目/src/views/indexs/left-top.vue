<template>
  <ul class="user_overview flex" v-if="pageflag">
    <li class="user_overview-item" style="color: #00fdfa">
      <div class="user_overview_nums allnum ">
        <dv-digital-flop :config="powerConsumeCfg" style="width:100%;height:100%;" />
      </div>
      <p>耗电量</p>
    </li>
    <li class="user_overview-item" style="color: #07f7a8">
      <div class="user_overview_nums online">
        <dv-digital-flop :config="waterConsumeCfg" style="width:100%;height:100%;" />
      </div>
      <p>耗水量</p>
    </li>
    <li class="user_overview-item" style="color: #e3b337">
      <div class="user_overview_nums offline">
        <dv-digital-flop :config="carbonEmissionCfg" style="width:100%;height:100%;" />
      </div>
      <p>碳排放量</p>
    </li>
  </ul>
  <Reacquire v-else @onclick="getData" line-height="200px">
    重新获取
  </Reacquire>
</template>

<script>
import { currentGET } from 'api/modules'
let style = {
  fontSize: 24
}
export default {
  data() {
    return {
      options: {},
      pageflag: true,
      timer: null,
      powerConsumeCfg: {
        number: [0],
        content: '{nt}',
        style: {
          ...style,
          // stroke: "#00fdfa",
          fill: "#00fdfa",
        },
      },
      waterConsumeCfg: {
        number: [0],
        content: '{nt}',
        style: {
          ...style,
          // stroke: "#07f7a8",
          fill: "#07f7a8",
        },
      },
      carbonEmissionCfg: {
        number: [0],
        content: '{nt}',
        style: {
          ...style,
          // stroke: "#e3b337",
          fill: "#e3b337",
        },
      },
    };
  },
  filters: {
    numsFilter(msg) {
      return msg || 0;
    },
  },
  created() {
    this.getData()
  },
  mounted() {
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
    //获取报表数据
    getData() {
      this.pageflag = true;
      currentGET("big2").then((res) => {
        if (res.success) {
          let emissionOverview = res.rows[0];
          this.waterConsumeCfg = {
            ...this.waterConsumeCfg,
            number: [emissionOverview.totalWaterConsume + emissionOverview.totalOfficeWaterConsume]
          }
          this.powerConsumeCfg = {
            ...this.powerConsumeCfg,
            number: [emissionOverview.totalPowerConsume + emissionOverview.totalOfficePowerConsume]
          }
          this.carbonEmissionCfg = {
            ...this.carbonEmissionCfg,
            number: [emissionOverview.totalCarbonEmission]
          }
          this.switper()
        } else {
          this.pageflag = false;
        }
      });
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
  },
};
</script>
<style lang='scss' scoped>
.user_overview {
  li {
    flex: 1;

    p {
      text-align: center;
      height: 16px;
      font-size: 16px;
    }

    .user_overview_nums {
      width: 160px;
      height: 160px;
      text-align: center;
      line-height: 100px;
      font-size: 12px;
      margin: 40px auto 30px;
      background-size: cover;
      background-position: center center;
      position: relative;

      &::before {
        content: "";
        position: absolute;
        width: 100%;
        height: 100%;
        top: 0;
        left: 0;
        font-size: 5px;
        background-size: cover;
      }

      &.bgdonghua::before {
        animation: rotating 14s linear infinite;
      }
    }

    .allnum {
      // background-image: url("../../assets/img/left_top_lan.png");
      &::before {
        background-image: url("../../assets/img/left_top_lan.png");
      }
    }

    .online {
      &::before {
        background-image: url("../../assets/img/left_top_lv.png");
      }
    }

    .offline {
      &::before {
        background-image: url("../../assets/img/left_top_huang.png");
      }
    }

    .laramnum {
      &::before {
        background-image: url("../../assets/img/left_top_hong.png");
      }
    }
  }
}
</style>