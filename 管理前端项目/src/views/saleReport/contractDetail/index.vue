<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="客户名称" prop="customerId">
        <el-select v-model="queryParams.customerId" placeholder="请选择客户" clearable filterable size="small">
          <el-option
            v-for="dict in customerIdOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="签订日期">
        <el-date-picker
          v-model="daterangeSignDate"
          size="small"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="物料名称" prop="materialName">
        <el-input
          v-model="queryParams.materialName"
          placeholder="请输入物料名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button v-no-more-click type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button v-no-more-click icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="contractDetailList">
      <el-table-column label="订单编号" align="center" prop="orderNo" />
      <el-table-column label="合同编号" align="center" prop="contractNo" />
      <el-table-column label="客户名称" align="center" prop="customerName" />
      <el-table-column label="合同金额" align="center" prop="contractAmount" />
      <el-table-column label="签订日期" align="center" prop="signDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.signDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="物料名称" align="center" prop="materialName" />
      <el-table-column label="型号" align="center" prop="materialModel" />
      <el-table-column label="单价" align="center" prop="price" />
      <el-table-column label="订货数量" align="center" prop="quantity" />
      <el-table-column label="订货金额" align="center" prop="amount" />
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
  </div>
</template>

<script>
import { listContractDetail, exportContractDetail } from "@/api/saleReport/contractDetail";

export default {
  name: "ContractDetailReport",
  components: {
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 销售合同明细表格数据
      contractDetailList: [],
      // 客户id字典
      customerIdOptions: [],
      // 签订日期时间范围
      daterangeSignDate: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        contractId: null,
        contractNo: null,
        orderId: null,
        orderNo: null,
        customerId: null,
        signDate: null,
        customerName: null,
        materialId: null,
        materialName: null,
      },
    };
  },
  created() {
    this.getList();
    this.getQueryData("query_customer").then(res => {
      this.customerIdOptions = res.data;
    });
  },
  methods: {
    /** 查询销售合同明细列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeSignDate && '' != this.daterangeSignDate) {
        this.queryParams.params["beginSignDate"] = this.daterangeSignDate[0];
        this.queryParams.params["endSignDate"] = this.daterangeSignDate[1];
      }
      listContractDetail(this.queryParams).then(response => {
        this.contractDetailList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 客户id字典翻译
    customerIdFormat(row, column) {
      return this.selectDictLabel(this.customerIdOptions, row.customerId);
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.daterangeSignDate = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有销售合同明细数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportContractDetail(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
    }
  }
};
</script>
