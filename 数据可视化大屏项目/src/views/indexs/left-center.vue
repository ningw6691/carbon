<template>
  <Echart id="leftCenter" :options="options" class="left_center_inner" v-if="pageflag" ref="charts" />
  <Reacquire v-else @onclick="getData" style="line-height:200px">
    重新获取
  </Reacquire>
</template>

<script>
import { currentGET } from 'api/modules'
export default {
  data() {
    return {
      options: {},
      consumeData: {
        totalPowerConsume: 0,
        totalOfficePowerConsume: 0,
        totalWaterConsume: 0,
        totalOfficeWaterConsume: 0
      },
      pageflag: true,
      timer: null
    };
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
      this.pageflag = true
      currentGET('big1').then(res => {
        if (res.success) {
          this.consumeData = res.data[0];
          this.$nextTick(() => {
            this.init()
            this.switper()
          })
        }else {
          this.pageflag = false
        }
      })
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
      let myChart = this.$refs.charts.chart
      myChart.on('mouseover', params => {
        this.clearData()
      });
      myChart.on('mouseout', params => {
        this.timer = setInterval(looper, this.$store.state.setting.echartsAutoTime);
      });
    },
    //初始化
    init() {
      let total = this.consumeData.totalPowerConsume + this.consumeData.totalOfficePowerConsume + this.consumeData.totalWaterConsume + this.consumeData.totalOfficeWaterConsume;
      let colors = ["#ECA444", "#33A1DB", "#56B557", "#c66863"];
      let piedata = {
        name: "能耗占比",
        type: "pie",
        radius: ["42%", "65%"],
        avoidLabelOverlap: false,
        itemStyle: {
          borderRadius: 4,
          borderColor: "rgba(0,0,0,0)",
          borderWidth: 2,
        },
        color: colors,
        data: [
          {
            value: this.consumeData.totalWaterConsume,
            name: "生产用水量",
            label: {
              shadowColor: colors[3],
            },
          },
          {
            value: this.consumeData.totalPowerConsume,
            name: "生产耗电量",
            label: {
              shadowColor: colors[0],
            },
          },
          {
            value: this.consumeData.totalOfficePowerConsume,
            name: "办公耗电量",
            label: {
              shadowColor: colors[2],
            },
          },
          {
            value: this.consumeData.totalOfficeWaterConsume,
            name: "办公用水量",
            label: {
              shadowColor: colors[1],
            },
          },
        ],
      };
      this.options = {
        title: {
          // zlevel: 0,
          text: ["{value|" + total + "}", "{name|总数}"].join("\n"),
          top: "center",
          left: "center",
          textStyle: {
            rich: {
              value: {
                color: "#ffffff",
                fontSize: 24,
                fontWeight: "bold",
                lineHeight: 20,
              },
              name: {
                color: "#ffffff",
                lineHeight: 20,
              },
            },
          },
        },
        tooltip: {
          trigger: "item",
          backgroundColor: "rgba(0,0,0,.6)",
          borderColor: "rgba(147, 235, 248, .8)",
          textStyle: {
            color: "#FFF",
          },
        },
        legend: {
          show: false,
          top: "5%",
          left: "center",
        },
        series: [
          //展示圆点
          {
            ...piedata,
            tooltip: { show: true },
            label: {
              formatter: "   {b|{b}}   \n   {c|{c}}   {per|{d}%}  ",
              //   position: "outside",
              rich: {
                b: {
                  color: "#fff",
                  fontSize: 12,
                  lineHeight: 26,
                },
                c: {
                  color: "#31ABE3",
                  fontSize: 14,
                },
                per: {
                  color: "#31ABE3",
                  fontSize: 14,
                },
              },
            },
            labelLine: {
              length: 20, // 第一段线 长度
              length2: 36, // 第二段线 长度
              show: true,
              emphasis: {
                show: true,
              },
            },
          },
          {
            ...piedata,
            tooltip: { show: true },
            itemStyle: {},
            label: {
              backgroundColor: "auto", //圆点颜色，auto：映射的系列色
              height: 0,
              width: 0,
              lineHeight: 0,
              borderRadius: 2.5,
              shadowBlur: 8,
              shadowColor: "auto",
              padding: [2.5, -2.5, 2.5, -2.5],
            },
            labelLine: {
              length: 20, // 第一段线 长度
              length2: 36, // 第二段线 长度
              show: false,
            },
          },
        ],
      };
    },
  },
};
</script>
<style lang='scss' scoped>
</style>