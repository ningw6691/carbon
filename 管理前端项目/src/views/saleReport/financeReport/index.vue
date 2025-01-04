<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="合同年份" prop="year">
        <el-date-picker v-model="queryParams.year" type="year" placeholder="选择年" format="yyyy年" value-format="yyyy">
        </el-date-picker>
      </el-form-item>

      <el-form-item>
        <el-button v-no-more-click type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button v-no-more-click icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-tabs v-model="activeName" type="card">
      <el-tab-pane label="季度统计" name="first">
        <el-table v-loading="loading" :data="quarterList">
          <el-table-column label="季度" align="center" prop="quarter" width="160" />
          <el-table-column label="采购合同金额" align="center" prop="purchaseContractTotal" />
          <el-table-column label="采购退款金额" align="center" prop="purchaseReturnTotal" />
          <el-table-column label="销售合同金额" align="center" prop="saleContractTotal" />
          <el-table-column label="销售退款金额" align="center" prop="saleReturnTotal" />
          <el-table-column label="总收入" align="center" prop="incomeTotal" />
          <el-table-column label="总支出" align="center" prop="outcomeTotal" />
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="月份统计" name="second">
        <el-table v-loading="loading" :data="monthList">
          <el-table-column label="月份" align="center" prop="month" width="100" />
          <el-table-column label="采购合同金额" align="center" prop="purchaseContractTotal" />
          <el-table-column label="采购退款金额" align="center" prop="purchaseReturnTotal" />
          <el-table-column label="销售合同金额" align="center" prop="saleContractTotal" />
          <el-table-column label="销售退款金额" align="center" prop="saleReturnTotal" />
          <el-table-column label="总收入" align="center" prop="incomeTotal" />
          <el-table-column label="总支出" align="center" prop="outcomeTotal" />
        </el-table>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
  import {
    financeReportByMonth,
    financeReportByQuarter,
  } from "@/api/saleReport/saleContractReport";

  export default {
    name: "FinanceReport",
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
        // 销售合同报表表格数据
        monthList: [],
        // 销售合同报表表格数据
        quarterList: [],
        // 客户id字典
        customerIdOptions: [],
        // 查询参数
        queryParams: {
          year: null,
          quarter: null,
          month: null,
        },
      };
    },
    created() {
      let date = new Date();
      this.currentYear = date.getFullYear() + '';
      this.queryParams.year = this.currentYear;
      this.getList();
    },
    methods: {
      /** 查询销售合同报表列表 */
      getList() {
        this.loading = true;
        financeReportByMonth(this.queryParams).then(response => {
          this.monthList = response.rows;
        });

        financeReportByQuarter(this.queryParams).then(response => {
          this.quarterList = response.rows;
        });
        this.loading = false;
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.getList();
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.resetForm("queryForm");
        this.queryParams.year = this.currentYear;
        this.handleQuery();
      },
    }
  };

</script>
