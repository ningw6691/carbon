<template>
  <div class="centermap">
    <div class="maptitle">
      <div class="zuo"></div>
      <span class="titletext">{{ maptitle }}</span>
      <div class="you"></div>
    </div>
    <div class="mapwrap">
      <dv-border-box-13>
        <div class="up">
          <div class="bg_color_black item" v-for="item in titleItem" :key="item.title">
            <p class="rdx-m-l-30 rdx-m-t-10">{{ item.title }}</p>
            <div class="rdx-m-l-30 rdx-m-t-10">
              <dv-digital-flop class="dv-dig-flop" :config="item.number" />
            </div>
          </div>
          <div class="bg_color_black item">
            <p class="rdx-m-l-30 rdx-m-t-10">{{ yearEmissionCfg.title }}</p>
            <div class="rdx-m-l-30 rdx-m-t-10">
              <dv-digital-flop class="dv-dig-flop" :config="yearEmissionCfg.number" />
            </div>
          </div>
          <div class="bg_color_black item">
            <p class="rdx-m-l-30 rdx-m-t-10">{{ monthEmissionCfg.title }}</p>
            <div class="rdx-m-l-30 rdx-m-t-10">
              <dv-digital-flop class="dv-dig-flop" :config="monthEmissionCfg.number" />
            </div>
          </div>
          <div class="bg_color_black item">
            <p class="rdx-m-l-30 rdx-m-t-10">{{ todayEmissionCfg.title }}</p>
            <div class="rdx-m-l-30 rdx-m-t-10">
              <dv-digital-flop class="dv-dig-flop" :config="todayEmissionCfg.number" />
            </div>
          </div>
        </div>
        <div id="center_right1">
          <div class="bg_color_black">
            <div class="d-flex rdx-p-t-2 rdx-p-l-2">
              <div class="d-flex">
                <span>碳排放排行榜</span>
              </div>
            </div>
            <div class="rdx-m-t-10 body_box">
              <dv-scroll-board class="dv_scr_board" :config="config" />
            </div>
          </div>
        </div>
      </dv-border-box-13>
    </div>
  </div>
</template>

<script>
import {
  currentGET
} from 'api/modules'
export default {
  data() {
    return {
      maptitle: "数据总览",
      pageflag: true,
      timer: null,
      echartBindClick: false,
      config: {

      },
      titleItem: [{
        title: '今年累计销售额',
        number: {
          number: [0],
          toFixed: 2,
          textAlign: 'left',
          content: '{nt}',
          style: {
            fontSize: 26
          }
        }
      },
      {
        title: '本月累计销售额',
        number: {
          number: [0],
          toFixed: 2,
          textAlign: 'left',
          content: '{nt}',
          style: {
            fontSize: 26
          }
        }
      },
      {
        title: '今日累计销售额',
        number: {
          number: [0],
          toFixed: 2,
          textAlign: 'left',
          content: '{nt}',
          style: {
            fontSize: 26
          }
        }
      },
      ],
      todayEmissionCfg: {},
      monthEmissionCfg: {},
      yearEmissionCfg: {},
    };
  },
  created() {
    this.getData();
  },
  mounted() {

  },
  beforeDestroy() {
    this.clearData()
  },
  methods: {
    //清理图标数据
    clearData() {
      if (this.timer) {
        clearInterval(this.timer)
        this.timer = null
      }
    },
    //获取图标数据
    getData() {
      this.pageflag = true;
      this.getSaleAmount();
      this.getCarbonEmission();
      this.getEquipmentRank();
      this.switper();
    },
    //获取销售额
    getSaleAmount() {
      currentGET('big12').then(res => {
        if (res.success) {
          let list = [{
            title: '今年累计销售额',
            number: {
              number: [res.data.yearTotal],
              toFixed: 2,
              textAlign: 'left',
              content: '{nt}',
              style: {
                fontSize: 26
              }
            }
          },
          {
            title: '本月累计销售额',
            number: {
              number: [res.data.monthTotal],
              toFixed: 2,
              textAlign: 'left',
              content: '{nt}',
              style: {
                fontSize: 26
              }
            }
          },
          {
            title: '今日累计销售额',
            number: {
              number: [res.data.todayTotal],
              toFixed: 2,
              textAlign: 'left',
              content: '{nt}',
              style: {
                fontSize: 26
              }
            }
          },
          ];
          this.titleItem = list;
        }
      })
    },
    //获取碳排放
    getCarbonEmission() {
      currentGET('big8').then(res => {
        let rows = res.rows;
        if (rows && rows.length > 0) {
          this.todayEmissionCfg = {
            title: '今日累计碳排放',
            number: {
              number: [rows[0].totalCarbonEmission],
              toFixed: 2,
              textAlign: 'left',
              content: '{nt}',
              style: {
                fontSize: 26
              }
            }
          };
        }
      });
      //获取本月累计碳排放
      currentGET('big9').then(res => {
        let rows = res.rows;
        if (rows && rows.length > 0) {
          this.monthEmissionCfg = {
            title: '本月累计碳排放',
            number: {
              number: [rows[0].totalCarbonEmission],
              toFixed: 2,
              textAlign: 'left',
              content: '{nt}',
              style: {
                fontSize: 26
              }
            }
          };
        }
      });
      //获取本年累计碳排放
      currentGET('big10').then(res => {
        let rows = res.rows;
        if (rows && rows.length > 0) {
          this.yearEmissionCfg = {
            title: '本年累计碳排放',
            number: {
              number: [rows[0].totalCarbonEmission],
              toFixed: 2,
              textAlign: 'left',
              content: '{nt}',
              style: {
                fontSize: 26
              }
            }
          };
        }
      });
    },
    //获取设备排行
    getEquipmentRank() {
      currentGET('big11', {
        pageNum: 1,
        pageSize: 10,
      }).then(res => {
        this.config = {
          header: ['设备', '生产数量', '碳排放'],
          data: [],
          rowNum: 7, //表格行数
          headerHeight: 35,
          headerBGC: '#0f1325', //表头
          oddRowBGC: '#0f1325', //奇数行
          evenRowBGC: '#171c33', //偶数行
          index: true,
          columnWidth: [150],
          align: ['center']
        };
        let list = res.rows;
        list.forEach(eq => {
          this.config.data.push([
            eq.equipmentName,
            eq.productQuantity,
            eq.totalCarbonEmission.toFixed(2),
          ])
        });
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
    },
  },
};
</script>
<style lang='scss' scoped>
.up {
  width: 100%;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;

  .item {
    border-radius: 6px;
    padding-top: 8px;
    margin-top: 8px;
    width: 32%;
    height: 70px;

    .dv-dig-flop {
      width: 160px;
      height: 30px;
    }
  }
}

.centermap {
  margin-bottom: 30px;

  .maptitle {
    height: 60px;
    display: flex;
    justify-content: center;
    padding-top: 10px;
    box-sizing: border-box;

    .titletext {
      font-size: 28px;
      font-weight: 900;
      letter-spacing: 6px;
      background: linear-gradient(
        92deg,
        #0072ff 0%,
        #00eaff 48.8525390625%,
        #01aaff 100%
      );
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
      margin: 0 10px;
    }

    .zuo,
    .you {
      background-size: 100% 100%;
      width: 29px;
      height: 20px;
      margin-top: 8px;
    }

    .zuo {
      background: url("../../assets/img/xiezuo.png") no-repeat;
    }

    .you {
      background: url("../../assets/img/xieyou.png") no-repeat;
    }
  }

  .mapwrap {
    height: 548px;
    width: 100%;
    // padding: 0 0 10px 0;
    box-sizing: border-box;
    position: relative;
  }
}

$box-height: 410px;
$box-width: 100%;

#center_right1 {
  padding: 16px;
  padding-top: 20px;
  height: $box-height;
  width: $box-width;
  border-radius: 5px;

  .bg_color_black {
    height: $box-height - 30px;
    border-radius: 10px;
  }

  .text {
    color: #c3cbde;
  }

  .body_box {
    border-radius: 10px;
    overflow: hidden;

    .dv_scr_board {
      width: 95%;
      height: 300px;
    }
  }
}
</style>