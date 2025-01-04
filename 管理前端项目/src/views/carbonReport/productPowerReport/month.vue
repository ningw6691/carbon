<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="月份">
        <el-date-picker v-model="daterangeProductDate" size="small" style="width: 240px" unlink-panels type="monthrange" value-format="yyyy-MM-dd" range-separator="至" start-placeholder="开始月份" end-placeholder="结束月份">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button v-no-more-click type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button v-no-more-click icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="productPowerReportList">
      <el-table-column label="月份" align="center" prop="productMonth" width="180">
      </el-table-column>
      <el-table-column label="生产总耗电量" align="center" prop="totalPowerConsume" />
      <el-table-column label="生产总用水量" align="center" prop="totalWaterConsume" />
      <el-table-column label="生产总节电量" align="center" prop="totalPrdPowerSave" />
      <el-table-column label="办公总用水量" align="center" prop="totalOfficeWaterConsume" />
      <el-table-column label="办公总耗电量" align="center" prop="totalOfficePowerConsume" />
      <el-table-column label="办公总节电量" align="center" prop="totalOfficePowerSave" />
      <el-table-column label="碳排放总量" align="center" prop="totalCarbonEmission" :formatter="carbonEmissionFormater"/>
      <el-table-column label="减少碳排放总量" align="center" prop="totalCarbonSave" :formatter="totalCarbonSaveFormater"/>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

  </div>
</template>

<script>
import { productPowerReportByMonth, exportProductPowerReport } from "@/api/carbonReport/productPowerReport";

export default {
  name: "ProductPowerReport",
  components: {
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // VIEW表格数据
      productPowerReportList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 生产日期时间范围
      daterangeProductDate: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        planId: null,
        bomId: null,
        orderId: null,
        productDate: null,
        materialId: null,
        processId: null,
        productLineId: null,
        processName: null,
        productLineName: null,
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    totalCarbonSaveFormater(row,column){
      return row.totalCarbonSave.toFixed(2);
    },
    carbonEmissionFormater(row,column){
      return row.totalCarbonEmission.toFixed(2);
    },
    /** 查询VIEW列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeProductDate && '' != this.daterangeProductDate) {
        this.queryParams.params["beginProductDate"] = this.daterangeProductDate[0];
        this.queryParams.params["endProductDate"] = this.daterangeProductDate[1];
      }
      productPowerReportByMonth(this.queryParams).then(response => {
        this.productPowerReportList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.daterangeProductDate = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return exportProductPowerReport(queryParams);
      }).then(response => {
        this.download(response.msg);
      })
    }
  }
};
</script>
