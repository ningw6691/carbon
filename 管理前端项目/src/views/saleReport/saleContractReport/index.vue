<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="客户" prop="customerId">
        <el-select v-model="queryParams.customerId" placeholder="请选择客户" clearable filterable size="small">
          <el-option v-for="dict in customerIdOptions" :key="dict.dictValue" :label="dict.dictLabel"
            :value="dict.dictValue" />
        </el-select>
      </el-form-item>
      <el-form-item label="合同年份" prop="contractYear">
        <el-date-picker v-model="queryParams.contractYear" type="year" placeholder="选择年" format="yyyy年"
          value-format="yyyy">
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
          <el-table-column label="季度" align="center" prop="contractQuarter" width="160" />
          <el-table-column label="客户名称" align="center" prop="customerName" />
          <el-table-column label="合同金额" align="center" prop="totalAmount" />
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="月份统计" name="second">
        <el-table v-loading="loading" :data="monthList">
          <el-table-column label="月份" align="center" prop="contractMonth" width="160" />
          <el-table-column label="客户名称" align="center" prop="customerName" />
          <el-table-column label="合同金额" align="center" prop="totalAmount" />
        </el-table>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
  import {
    listSaleContractReportByMonth,
    exportSaleContractReportByMonth,
    listSaleContractReportByQuarter,
    exportSaleContractReportByQuarter,
  } from "@/api/saleReport/saleContractReport";

  export default {
    name: "SaleContractReport",
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
          customerId: null,
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
      this.getQueryData("query_customer").then(res => {
        this.customerIdOptions = res.data;
      });
    },
    methods: {
      /** 查询销售合同报表列表 */
      getList() {
        this.loading = true;
        console.log(this.queryParams.contractYear)
        listSaleContractReportByMonth(this.queryParams).then(response => {
          this.monthList = response.rows;
          let customerName = ''
          if (this.queryParams.customerId) {
            for (let dict of this.customerIdOptions) {
              if (dict.dictValue == this.queryParams.customerId + '') {
                customerName = dict.dictLabel;
              }
            }
          }
          if (customerName) {
            for (let item of this.monthList) {
              item.customerName = customerName
            }
          }
          this.loading = false;
        });

        listSaleContractReportByQuarter(this.queryParams).then(response => {
          this.quarterList = response.rows;
          let customerName = ''
          if (this.queryParams.customerId) {
            for (let dict of this.customerIdOptions) {
              if (dict.dictValue == this.queryParams.customerId + '') {
                customerName = dict.dictLabel;
              }
            }
          }
          if (customerName) {
            for (let item of this.quarterList) {
              item.customerName = customerName
            }
          }
        });
      },
      // 客户id字典翻译
      customerIdFormat(row, column) {
        return this.selectDictLabel(this.customerIdOptions, row.customerId);
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.getList();
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.resetForm("queryForm");
        this.queryParams.contractYear = this.currentYear;
        this.handleQuery();
      },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams;
        this.$confirm('是否确认导出所有销售合同报表数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return exportSaleContractReportByMonth(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
      }
    }
  };

</script>
