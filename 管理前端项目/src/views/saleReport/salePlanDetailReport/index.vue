<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="计划单号" prop="planNo">
        <el-input
          v-model="queryParams.planNo"
          placeholder="请输入计划单号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="计划标题" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入计划标题"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="计划类型" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择计划类型" clearable filterable size="small">
          <el-option
            v-for="dict in typeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="产品名称" prop="materialName">
        <el-input
          v-model="queryParams.materialName"
          placeholder="请输入产品名称"
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
          v-hasPermi="['saleReport:salePlanDetailReport:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="salePlanDetailReportList">
      <el-table-column label="计划单号" align="center" prop="planNo" />
      <el-table-column label="计划标题" align="center" prop="title" />
      <el-table-column label="计划类型" align="center" prop="type" :formatter="typeFormat" />
      <el-table-column label="起始日期" align="center" prop="startDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结束日期" align="center" prop="endDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="产品名称" align="center" prop="materialName" />
      <el-table-column label="目标销售额" align="center" prop="targetAmount" />
      <el-table-column label="销售额" align="center" prop="saleAmount" />
      <el-table-column label="状态" align="center" prop="status" >
         <template slot-scope="scope">
          <span v-if="scope.row.status=='1'">已达成</span>
          <span v-if="scope.row.status=='0'">未达成</span>
        </template>
      </el-table-column>
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
import { listSalePlanDetailReport, exportSalePlanDetailReport } from "@/api/saleReport/salePlanDetailReport";

export default {
  name: "SalePlanDetailReport",
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
      // 销售计划达成率报表表格数据
      salePlanDetailReportList: [],
      // 计划类型字典
      typeOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        planNo: null,
        title: null,
        type: null,
        materialName: null
      },
    };
  },
  created() {
    this.getList();
    this.getDicts("sale_plan_type").then(response => {
      this.typeOptions = response.data;
    });
  },
  methods: {
    /** 查询销售计划达成率报表列表 */
    getList() {
      this.loading = true;
      listSalePlanDetailReport(this.queryParams).then(response => {
        this.salePlanDetailReportList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 计划类型字典翻译
    typeFormat(row, column) {
      return this.selectDictLabel(this.typeOptions, row.type);
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
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
      this.$confirm('是否确认导出所有销售计划达成率报表数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportSalePlanDetailReport(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
    }
  }
};
</script>
