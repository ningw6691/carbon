<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="年份" prop="contractYear">
        <el-date-picker v-model="queryParams.contractYear" type="year" placeholder="选择年" format="yyyy年"
          value-format="yyyy">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="供应商" prop="supplierId">
        <el-select v-model="queryParams.supplierId" placeholder="请选择供应商" clearable filterable size="small">
          <el-option v-for="dict in supplierIdOptions" :key="dict.dictValue" :label="dict.dictLabel"
            :value="dict.dictValue" />
        </el-select>
      </el-form-item>
      <el-form-item label="物料名称" prop="materialName">
        <el-input v-model="queryParams.materialName" placeholder="请输入物料名称" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button v-no-more-click type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button v-no-more-click icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-tabs v-model="activeName" type="card">
      <el-tab-pane label="季度统计" name="first">
        <el-table v-loading="loading" :data="quarterList">
          <el-table-column label="季度" align="center" prop="contractQuarter" />
          <el-table-column label="物料名称" align="center" prop="materialName" />
          <el-table-column label="采购金额" align="center" prop="purchaseAmount" />
          <el-table-column label="退款金额" align="center" prop="returnAmount" />
          <el-table-column label="采购数量" align="center" prop="purchaseNum" />
          <el-table-column label="到货数量" align="center" prop="arriveNum" />
          <el-table-column label="入库数量" align="center" prop="inWarehouseNum" />
          <el-table-column label="退货数量" align="center" prop="returnNum" />
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="月份统计" name="second">
        <el-table v-loading="loading" :data="monthList">
          <el-table-column label="月份" align="center" prop="contractMonth" />
          <el-table-column label="物料名称" align="center" prop="materialName" />
          <el-table-column label="采购金额" align="center" prop="purchaseAmount" />
          <el-table-column label="退款金额" align="center" prop="returnAmount" />
          <el-table-column label="采购数量" align="center" prop="purchaseNum" />
          <el-table-column label="到货数量" align="center" prop="arriveNum" />
          <el-table-column label="入库数量" align="center" prop="inWarehouseNum" />
          <el-table-column label="退货数量" align="center" prop="returnNum" />
        </el-table>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
  import {
    monthReport,
    quarterReport
  } from "@/api/purchaseReport/contractArriveReturnReport";

  export default {
    name: "ContractArriveReturnReport",
    components: {},
    data() {
      return {
        activeName: 'first',
        currentYear: null,
        // 遮罩层
        loading: true,
        // 显示搜索条件
        showSearch: true,
        // 总条数
        total: 0,
        // 表格数据
        monthList: [],
        quarterList: [],
        // 供应商id字典
        supplierIdOptions: [],
        // 查询参数
        queryParams: {
          supplierId: null,
          contractYear: null,
          contractQuarter: null,
          materialId: null,
          materialName: null,
        },
      };
    },
    created() {
      let date = new Date();
      this.currentYear = date.getFullYear() + '';
      this.queryParams.contractYear = this.currentYear;
      this.getList();
      this.getQueryData("query_supplier").then(res => {
        this.supplierIdOptions = res.data;
      });
    },
    methods: {
      /** 查询发货退货明细报表列表 */
      getList() {
        this.loading = true;
        monthReport(this.queryParams).then(response => {
          this.monthList = response.rows;
          this.loading = false;
        });

        quarterReport(this.queryParams).then(response => {
          this.quarterList = response.rows;
          this.loading = false;
        });
      },
      // 供应商id字典翻译
      supplierIdFormat(row, column) {
        return this.selectDictLabel(this.supplierIdOptions, row.customerId);
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams.pageNum = 1;
        this.getList();
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.resetForm("queryForm");
        this.queryParams.contractYear = this.currentYear;
        this.handleQuery();
      },
    }
  };

</script>
