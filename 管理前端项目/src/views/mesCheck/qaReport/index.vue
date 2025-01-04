<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="完工单号" prop="productFinishNo">
        <el-input v-model="queryParams.productFinishNo" placeholder="请输入完工单号" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="计划编号" prop="productPlanNo">
        <el-input v-model="queryParams.productPlanNo" placeholder="请输入生产计划编号" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="作业名称" prop="productJobName">
        <el-input v-model="queryParams.productJobName" placeholder="请输入生产作业名称" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="产品名称" prop="materialName">
        <el-input v-model="queryParams.materialName" placeholder="请输入物料产品名称" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="检验批次" prop="materialBatchNo">
        <el-input v-model="queryParams.materialBatchNo" placeholder="请输入检验批次" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="生产日期">
        <el-date-picker v-model="daterangeProductDate" size="small" style="width: 240px" value-format="yyyy-MM-dd"
          type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="检验项" prop="checkItemName">
        <el-input v-model="queryParams.checkItemName" placeholder="请输入检验项" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button v-no-more-click type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button v-no-more-click icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="productCheckList">
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="检验编号" align="center" prop="serialNo" />
      <el-table-column label="计划编号" align="center" prop="productPlanNo" />
      <el-table-column label="完工单号" align="center" prop="productFinishNo" />
      <el-table-column label="作业名称" align="center" prop="productJobName" />
      <!-- <el-table-column label="计划排产编号" align="center" prop="productScheduleId" /> -->
      <el-table-column label="产线名称" align="center" prop="productLineName" /> 
      <el-table-column label="检验批次" align="center" prop="materialBatchNo" />
      <el-table-column label="产品名称" align="center" prop="materialName" />
      <el-table-column label="生产日期" align="center" prop="productDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.productDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="检验项" align="center" prop="checkItemName" />
      <el-table-column label="生产数量" align="center" prop="productQuantity" />
      <el-table-column label="抽检数量" align="center" prop="checkQuantity" />
      <el-table-column label="检验人" align="center" prop="checkUser" :formatter="checkUserFormat" />
      <el-table-column label="合格数量" align="center" prop="qualifiedRate" />
      <el-table-column label="检验时间" align="center" prop="checkDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.checkDate, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

  </div>
</template>

<script>
  import {
    listProductCheck,
    exportProductCheck
  } from "@/api/mesCheck/qaReport";

  export default {
    name: "QaReport",
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
        // 生产检验单表格数据
        productCheckList: [],
        // 生产日期时间范围
        daterangeProductDate: [],
        // 检验结果：0不合格1合格字典
        checkResultOptions: [],
        // 检验人字典
        checkUserOptions: [],
        // 检验时间时间范围
        daterangeCheckDate: [],
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          serialNo: null,
          productFinishNo: null,
          productPlanId: null,
          productScheduleId: null,
          productLineId: null,
          productJobId: null,
          materialId: null,
          materialBatchNo: null,
          productDate: null,
          productQuantity: null,
          checkStandardId: null,
          checkQuantity: null,
          checkValue: null,
          maxValue: null,
          minValue: null,
          checkResult: null,
          checkUser: null,
          checkDate: null,
          materialName: null,
          checkItemName: null,
          productPlanNo: null,
          materialName: null
        },
      };
    },
    created() {
      this.getList();
      this.getDicts("mes_check_result").then(response => {
        this.checkResultOptions = response.data;
      });
      this.getQueryData("query_user").then(res => {
        this.checkUserOptions = res.data;
      });
    },
    methods: {
      /** 查询生产检验单列表 */
      getList() {
        this.loading = true;
        this.queryParams.params = {};
        if (null != this.daterangeProductDate && '' != this.daterangeProductDate) {
          this.queryParams.params["beginProductDate"] = this.daterangeProductDate[0];
          this.queryParams.params["endProductDate"] = this.daterangeProductDate[1];
        }
        if (null != this.daterangeCheckDate && '' != this.daterangeCheckDate) {
          this.queryParams.params["beginCheckDate"] = this.daterangeCheckDate[0];
          this.queryParams.params["endCheckDate"] = this.daterangeCheckDate[1];
        }
        listProductCheck(this.queryParams).then(response => {
          this.productCheckList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },
      // 检验结果：0不合格1合格字典翻译
      checkResultFormat(row, column) {
        return this.selectDictLabel(this.checkResultOptions, row.checkResult);
      },
      // 检验人字典翻译
      checkUserFormat(row, column) {
        return this.selectDictLabel(this.checkUserOptions, row.checkUser);
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams.pageNum = 1;
        this.getList();
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.daterangeProductDate = [];
        this.daterangeCheckDate = [];
        this.resetForm("queryForm");
        this.handleQuery();
      },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams;
        this.$confirm('是否确认导出所有生产检验单数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return exportProductCheck(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
      }
    }
  };

</script>
