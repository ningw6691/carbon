<template>
  <div class="app-container home">
    <el-row type="flex" justify="center" :gutter="10">
      <el-col :span="24">
        <el-card>
          <div slot="header">
            <span>能耗总览</span>
          </div>
          <div>
            <el-row type="flex" justify="center" :gutter="10">
              <el-col>
                <el-card class="today">
                  <div class="font-title">
                    <span>今日总能耗</span>
                  </div>
                  <div class="font-content">
                    {{energyOverall.todayEmission.toFixed(2)}}KG
                  </div>
                </el-card>
              </el-col>
              <el-col>
                <el-card class="month">
                  <div class="font-title">
                    <span>本月总能耗</span>
                  </div>
                  <div class="font-content">
                    {{energyOverall.thisMonthEmission.toFixed(2)}}KG
                  </div>
                </el-card>
              </el-col>
              <el-col>
                <el-card class="year">
                  <div class="font-title">
                    <span>本年总能耗</span>
                  </div>
                  <div class="font-content">
                    {{energyOverall.thisYearEmission.toFixed(2)}}KG
                  </div>
                </el-card>
              </el-col>
              <el-col>
                <el-card class="year-save">
                  <div class="font-title">
                    <span>本年总减排</span>
                  </div>
                  <div class="font-content">
                    {{energyOverall.thisYearSave.toFixed(2)}}KG
                  </div>
                </el-card>
              </el-col>
            </el-row>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row type="flex" justify="center" :gutter="10" style="margin-top:10px;">
      <el-col :span="12">
        <el-card>
          <div slot="header">
            <span>设备能耗排行</span>
          </div>
          <div>
            <el-table :data="equipmentPowerReportList">
              <el-table-column label="排名" type="index" align="center" width="50" />
              <el-table-column label="设备名称" align="center" prop="equipmentName" />
              <el-table-column label="已产数量" align="center" prop="productQuantity" />
              <el-table-column label="单位耗电量" align="center" prop="powerConsume" />
              <el-table-column label="总耗电量" align="center" prop="totalPowerConsume" />
              <el-table-column label="单位碳排放" align="center" prop="carbonEmission" :formatter="carbonEmissionFormat"/>
              <el-table-column label="总碳排放" align="center" prop="totalCarbonEmission" :formatter="totalCarbonEmissionFormat"/>
            </el-table>
          </div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <div slot="header">
            <span>产品能耗排行</span>
          </div>
          <div>
            <el-table :data="productPowerReportList">
              <el-table-column label="排名" type="index" align="center" width="50" />
              <el-table-column label="产品" align="center" prop="materialName" />
              <el-table-column label="已产数量" align="center" prop="productQuantity" />
              <el-table-column label="单位耗电量" align="center" prop="powerConsume" />
              <el-table-column label="总耗电量" align="center" prop="totalPowerConsume" />
              <el-table-column label="单位碳排放" align="center" prop="carbonEmission" :formatter="carbonEmissionFormat"/>
              <el-table-column label="总碳排放" align="center" prop="totalCarbonEmission" :formatter="totalCarbonEmissionFormat"/>
            </el-table>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row type="flex" justify="center" :gutter="10" style="margin-top:10px;">
      <el-col :span="12">
        <el-card>
          <div slot="header">
            <span>综合能耗</span>
          </div>
          <div>
            <div id="energyOverall" :style="{height: '300px'}"></div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <div slot="header">
            <span>能耗占比</span>
          </div>
          <div>
            <div id="energyPercent" :style="{height: '300px'}"></div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row type="flex" justify="center" :gutter="10" style="margin-top:10px;">
      <el-col :span="12">
        <el-card>
          <div slot="header">
            <span>能耗地图</span>
          </div>
          <div>
            <div id="energyMap" :style="{height: '300px'}"></div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <div slot="header">
            <span>能耗趋势</span>
          </div>
          <div>
            <div id="energyTrend" :style="{height: '300px'}"></div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { productPowerReportByDay, productPowerReportByMonth, productPowerReportByYear, productPowerReportOverall } from "@/api/carbonReport/productPowerReport";
import { listEquipmentPowerReport } from "@/api/carbonReport/equipmentPowerReport";
import { getEmissionOverall, getEmissionPercent } from "@/api/carbonReport/monitorChartReport";
export default {
  name: "index",
  data() {
    return {
      energyOverall: {
        todayEmission: 0,
        thisMonthEmission: 0,
        thisYearEmission: 0,
        thisYearSave: 0,
      },
      equipmentPowerReportList: [],
      productPowerReportList: [],
      energyOverallCharOpts: {
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        legend: {},
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'value',
          boundaryGap: [0, 0.01]
        },
        yAxis: {
          type: 'category',
          data: []
        },
        series: [
          {
            name: '生产耗电量',
            type: 'bar',
            data: []
          },
          {
            name: '生产用水量',
            type: 'bar',
            data: []
          },
          {
            name: '办公耗电量',
            type: 'bar',
            data: []
          },
          {
            name: '办公用水量',
            type: 'bar',
            data: []
          },
          {
            name: '节电量',
            type: 'bar',
            data: []
          }
        ]
      },
      energyPercentOpts: {
        title: {
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'vertical',
          left: 'left'
        },
        series: [
          {
            name: '能耗',
            type: 'pie',
            radius: '50%',
            data: [],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      },
      energyMapOpts: {
        title: {
        },
        tooltip: {
        },
        series: [
          {
            name: '总能耗',
            type: 'treemap',
            visibleMin: 300,
            label: {
              show: true,
              formatter: '{b}'
            },
            itemStyle: {
              borderColor: '#fff'
            },
            levels: [
              {
                itemStyle: {
                  borderWidth: 0,
                  gapWidth: 5
                }
              },
              {
                itemStyle: {
                  gapWidth: 1
                }
              },
              {
                colorSaturation: [0.35, 0.5],
                itemStyle: {
                  gapWidth: 1,
                  borderColorSaturation: 0.6
                }
              }
            ],
            data: []
          }
        ]
      },
      energyTrendOpts: {
        tooltip: {
          trigger: 'axis'
        },
        legend: {},
        toolbox: {
          show: true,
          feature: {
            magicType: { type: ['line', 'bar'] },
            restore: {},
          }
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: []
        },
        yAxis: {
          type: 'value',
        },
        series: [
          {
            name: '碳排放量',
            type: 'line',
            data: [],
          },
          {
            name: '碳减排量',
            type: 'line',
            data: [],
          }
        ]
      }
    };
  },
  created() {
    this.getEnergyOverallReport();
    this.getEquipmentEmissionRank();
    this.getProductEmissionRank();
  },
  mounted() {
    this.getCarbonEmissionOverall();
    this.getCarbonEmissionPercent();
  },
  methods: {
    totalCarbonEmissionFormat(row,column){
      return row.totalCarbonEmission.toFixed(2);
    },
    carbonEmissionFormat(row,column){
      return row.carbonEmission.toFixed(2);
    },
    //获取综合能耗数据
    getCarbonEmissionOverall() {
      getEmissionOverall(null).then(res => {
        let data = res.data;
        for (let year in data) {
          let obj = data[year];
          this.energyOverallCharOpts.yAxis.data.push(year);
          this.energyOverallCharOpts.series[0].data.push(obj.totalPowerConsume);
          this.energyOverallCharOpts.series[1].data.push(obj.totalWaterConsume);
          this.energyOverallCharOpts.series[2].data.push(obj.totalOfficePowerConsume);
          this.energyOverallCharOpts.series[3].data.push(obj.totalOfficeWaterConsume);
          this.energyOverallCharOpts.series[4].data.push(obj.totalPrdPowerSave + obj.totalOfficePowerSave);

          this.energyTrendOpts.xAxis.data.push(year);
          this.energyTrendOpts.series[0].data.push(obj.totalCarbonEmission.toFixed(2));
          this.energyTrendOpts.series[1].data.push(obj.totalCarbonSave.toFixed(2));
        }
        this.drawEnergyOverall();
        this.drawEnergyTrend();
      })
    },
    //获取能耗占比
    getCarbonEmissionPercent() {
      getEmissionPercent().then(res => {
        let data = res.data[0];
        this.energyPercentOpts.series[0].data.push({ value: data.totalPowerConsume, name: '生产耗电量' });
        this.energyPercentOpts.series[0].data.push({ value: data.totalOfficePowerConsume, name: '办公耗电量' });
        this.energyPercentOpts.series[0].data.push({ value: data.totalWaterConsume, name: '生产耗水量' });
        this.energyPercentOpts.series[0].data.push({ value: data.totalOfficeWaterConsume, name: '办公耗水量' });
        this.drawEnergyPercent();

        let officeConsume = data.totalOfficePowerConsume + data.totalOfficeWaterConsume;
        let prdConsume = data.totalPowerConsume + data.totalWaterConsume;
        this.energyMapOpts.series[0].data.push({
          "value": officeConsume,
          "name": "办公能耗",
          "children": [
            {
              "value": data.totalOfficePowerConsume,
              "name": "办公耗电量",
            },
            {
              "value": data.totalOfficeWaterConsume,
              "name": "办公用水量",
            },
          ]
        });
        this.energyMapOpts.series[0].data.push({
          "value": prdConsume,
          "name": "生产能耗",
          "children": [
            {
              "value": data.totalPowerConsume,
              "name": "生产耗电量",
            },
            {
              "value": data.totalWaterConsume,
              "name": "生产耗水量",
            },
          ]
        });
        this.drawEnergyMap();
      });
    },
    //获取设备能耗排行
    getEquipmentEmissionRank() {
      listEquipmentPowerReport({
        pageNum: 1,
        pageSize: 10,
      }).then(res => {
        this.equipmentPowerReportList = res.rows;
      })
    },
    //获取产品能耗排行
    getProductEmissionRank() {
      productPowerReportOverall({
        pageNum: 1,
        pageSize: 10,
      }).then(res => {
        this.productPowerReportList = res.rows;
      })
    },
    //获取能耗总览报表数据
    getEnergyOverallReport() {
      productPowerReportByDay(null).then(res => {
        let rows = res.rows;
        if (rows && rows.length > 0) {
          this.energyOverall.todayEmission = rows[0].totalCarbonEmission;
        }
      })
      productPowerReportByMonth(null).then(res => {
        let rows = res.rows;
        if (rows && rows.length > 0) {
          this.energyOverall.thisMonthEmission = rows[0].totalCarbonEmission;
        }
      })
      productPowerReportByYear(null).then(res => {
        let rows = res.rows;
        if (rows && rows.length > 0) {
          this.energyOverall.thisYearEmission = rows[0].totalCarbonEmission;
          this.energyOverall.thisYearSave = rows[0].totalCarbonSave;
        }
      })
    },
    drawEnergyOverall() {
      let myChart = this.$echarts.init(document.getElementById('energyOverall'));
      // 绘制图表
      myChart.setOption(this.energyOverallCharOpts);
      window.addEventListener('resize', myChart.resize);
    },
    drawEnergyPercent() {
      let myChart = this.$echarts.init(document.getElementById('energyPercent'));
      // 绘制图表
      myChart.setOption(this.energyPercentOpts);
      window.addEventListener('resize', myChart.resize);
    },
    drawEnergyMap() {
      let myChart = this.$echarts.init(document.getElementById('energyMap'));
      // 绘制图表
      myChart.setOption(this.energyMapOpts);
      window.addEventListener('resize', myChart.resize);
    },
    drawEnergyTrend() {
      let myChart = this.$echarts.init(document.getElementById('energyTrend'));
      // 绘制图表
      myChart.setOption(this.energyTrendOpts);
      window.addEventListener('resize', myChart.resize);
    },
  },
};
</script>

<style scoped lang="scss">
.home {
  font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;
}
.font-content {
  font-size: 18px;
}
.font-title {
  font-size: 20px;
  padding-bottom: 20px;
}
.today {
  background-color: #60cfeb;
  color: aliceblue;
}
.month {
  background-color: #f7b018;
  color: aliceblue;
}
.year {
  background-color: #fb846c;
  color: aliceblue;
}
.year-save {
  background-color: #28b976;
  color: aliceblue;
}
</style>

