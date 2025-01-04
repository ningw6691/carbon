<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="仓库" prop="whId">
        <el-select v-model="queryParams.whId" placeholder="请选择仓库" clearable filterable size="small" @change="handleWarehouseChange">
          <el-option v-for="dict in whIdOptions" :key="dict.dictValue" :label="dict.dictLabel" :value="parseInt(dict.dictValue)" />
        </el-select>
      </el-form-item>
      <el-form-item label="库区" prop="whRegionId">
        <el-select v-model="queryParams.whRegionId" placeholder="请输入库区" clearable filterable size="small" @change="handleWhRegionChange">
          <el-option v-for="dict in whRegionOptions" :key="dict.id" :label="dict.name" :value="dict.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="库位" prop="whLocationId">
        <el-select v-model="queryParams.whLocationId" placeholder="请输入库位" clearable filterable size="small" @change="handleWhLocationChange">
          <el-option v-for="dict in whLocationOptions" :key="dict.id" :label="dict.name" :value="dict.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="物料" prop="materialId">
        <el-input v-model="queryParams.materialId" placeholder="请输入物料" clearable size="small" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="生产批号" prop="batchNo">
        <el-input v-model="queryParams.batchNo" placeholder="请输入生产批号" clearable size="small" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="出库日期">
        <el-date-picker v-model="daterangeInWhDate" size="small" style="width: 240px" value-format="yyyy-MM-dd" type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="物料名称" prop="materialName">
        <el-input v-model="queryParams.materialName" placeholder="请输入物料名称" clearable size="small" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="型号" prop="materialModel">
        <el-input v-model="queryParams.materialModel" placeholder="请输入型号" clearable size="small" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="规格" prop="materialSpecification">
        <el-input v-model="queryParams.materialSpecification" placeholder="请输入规格" clearable size="small" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="单位" prop="materialUnit">
        <el-input v-model="queryParams.materialUnit" placeholder="请输入单位" clearable size="small" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="业务类型" prop="bizType">
        <el-select v-model="queryParams.bizType" placeholder="请选择业务类型" clearable filterable size="small">
          <el-option v-for="dict in bizTypeOptions" :key="dict.dictValue" :label="dict.dictLabel" :value="dict.dictValue" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button v-no-more-click type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button v-no-more-click icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button v-no-more-click type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" v-hasPermi="['wmsReport:outWarehouseDetailReport:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="outWarehouseDetailReportList">
      <el-table-column label="仓库名" align="center" prop="whName" />
      <el-table-column label="库区名称" align="center" prop="whRegionName" />
      <el-table-column label="库位名称" align="center" prop="whLocationName" />
      <el-table-column label="物料名称" align="center" prop="materialName" />
      <el-table-column label="生产批号" align="center" prop="batchNo" />
      <el-table-column label="型号" align="center" prop="materialModel" />
      <el-table-column label="规格" align="center" prop="materialSpecification" />
      <el-table-column label="单位" align="center" prop="materialUnit" />
      <el-table-column v-if="queryParams.bizType" label="业务类型" align="center" prop="bizType" :formatter="bizTypeFormat" />
      <el-table-column label="出库数量" align="center" prop="outQuantity" />
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />
  </div>
</template>

<script>
import { listOutWarehouseDetailReport, exportOutWarehouseDetailReport } from "@/api/wmsReport/outWarehouseDetailReport";
import { listWarehouseRegion } from "@/api/warehouse/warehouseRegion";
import { listWarehouseLocation } from "@/api/warehouse/warehouseLocation";
export default {
  name: "OutWarehouseDetailReport",
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
      // 物料出库明细表格数据
      outWarehouseDetailReportList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 仓库字典
      whIdOptions: [],
      whRegionOptions: [],
      whLocationOptions: [],
      // 业务类型字典
      bizTypeOptions: [],
      // 出库日期时间范围
      daterangeInWhDate: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        materialId: null,
        batchNo: null,
        bizType: null,
        whId: null,
        whRegionId: null,
        whLocationId: null,
        inWhDate: null,
        materialName: null,
        materialModel: null,
        materialSpecification: null,
        materialUnit: null,
      },
    };
  },
  created() {
    this.getList();
    this.getQueryData("query_warehouse").then(res => {
      this.whIdOptions = res.data;
    });
    this.getDicts("event_type").then(response => {
      this.bizTypeOptions = response.data;
    });
  },
  methods: {
    handleWarehouseChange(val) {
      this.queryParams.whRegionId = null;
      this.queryParams.whLocationId = null;
      if (val) {
        this.getWhRegionOpts(val);
      }
    },
    handleWhRegionChange(val) {
      this.queryParams.whLocationId = null;
      if (val) {
        this.getWhLocationOpts(val);
      }
    },
    handleWhLocationChange(val) {

    },
    handleBizTypeChange(val) {
      this.getList();
    },
    //获取库区选项
    getWhRegionOpts(val) {
      let queryParams = {
        warehouseId: val,
      }
      listWarehouseRegion(queryParams).then(res => {
        this.whRegionOptions = res.rows;
      });
    },
    //获取库位选项
    getWhLocationOpts(val) {
      let queryParams = {
        regionId: val,
      }
      listWarehouseLocation(queryParams).then(res => {
        this.whLocationOptions = res.rows;
      })
    },
    /** 查询物料出库明细列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeInWhDate && '' != this.daterangeInWhDate) {
        this.queryParams.params["beginInWhDate"] = this.daterangeInWhDate[0];
        this.queryParams.params["endInWhDate"] = this.daterangeInWhDate[1];
      }
      listOutWarehouseDetailReport(this.queryParams).then(response => {
        this.outWarehouseDetailReportList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 仓库字典翻译
    whIdFormat(row, column) {
      return this.selectDictLabel(this.whIdOptions, row.whId);
    },
    // 业务类型字典翻译
    bizTypeFormat(row, column) {
      return this.selectDictLabel(this.bizTypeOptions, row.bizType);
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.daterangeInWhDate = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有物料出库明细数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return exportOutWarehouseDetailReport(queryParams);
      }).then(response => {
        this.download(response.msg);
      })
    }
  }
};
</script>
