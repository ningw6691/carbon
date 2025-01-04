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
      <el-form-item label="合同编号" prop="contractNo">
        <el-input v-model="queryParams.contractNo" placeholder="请输入合同编号" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="申请编号" prop="applyNo">
        <el-input v-model="queryParams.applyNo" placeholder="请输入申请编号" clearable size="small"
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
          <el-table-column label="申请编号" align="center" prop="applyNo" />
          <el-table-column label="合同编号" align="center" prop="contractNo" />
          <el-table-column label="供应商名称" align="center" prop="supplierName" />
          <el-table-column label="应付金额" align="center" prop="contractAmount" />
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="月份统计" name="second">
        <el-table v-loading="loading" :data="monthList">
          <el-table-column label="月份" align="center" prop="contractMonth" />
          <el-table-column label="申请编号" align="center" prop="applyNo" />
          <el-table-column label="合同编号" align="center" prop="contractNo" />
          <el-table-column label="供应商名称" align="center" prop="supplierName" />
          <el-table-column label="应付金额" align="center" prop="contractAmount" />
        </el-table>
      </el-tab-pane>
    </el-tabs>

  </div>
</template>

<script>
  import {
    monthReport,
    quarterReport
  } from "@/api/purchaseReport/purchaseContractReport";

  export default {
    name: "PurchaseContractReport",
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
        // 采购合同报表表格数据
        monthList: [],
        quarterList: [],
        // 供应商id字典
        supplierIdOptions: [],
        // 查询参数
        queryParams: {
          contractNo: null,
          applyNo: null,
          supplierName: null,
          contractYear: null,
          contractQuarter: null,
          contractMonth: null,
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
      /** 查询采购合同报表列表 */
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
        return this.selectDictLabel(this.supplierIdOptions, row.supplierId);
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.getList();
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.resetForm("queryForm");
        this.handleQuery();
      },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams;
        this.$confirm('是否确认导出所有采购合同报表数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return exportPurchaseContractReport(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
      }
    }
  };

</script>
