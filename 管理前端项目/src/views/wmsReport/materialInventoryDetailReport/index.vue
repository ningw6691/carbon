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
      <el-form-item label="生产批号" prop="batchNo">
        <el-input v-model="queryParams.batchNo" placeholder="请输入生产批号" clearable size="small" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="生产厂家" prop="manufacturer">
        <el-input v-model="queryParams.manufacturer" placeholder="请输入生产厂家" clearable size="small" @keyup.enter.native="handleQuery" />
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
      <el-form-item>
        <el-button v-no-more-click type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button v-no-more-click icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button v-no-more-click type="warning" plain icon="el-icon-download" size="mini" v-hasPermi="['wmsReport:materialInventoryDetailReport:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="materialInventoryDetailReportList">
      <el-table-column label="仓库名" align="center" prop="whName" />
      <el-table-column v-if="queryParams.whRegionId" label="库区名称" align="center" prop="whRegionName" />
      <el-table-column v-if="queryParams.whLocationId" label="库位名称" align="center" prop="whLocationName" />
      <el-table-column label="物料名称" align="center" prop="materialName" />
      <el-table-column label="型号" align="center" prop="materialModel" />
      <el-table-column label="规格" align="center" prop="materialSpecification" />
      <el-table-column label="单位" align="center" prop="materialUnit" />
      <el-table-column label="生产批号" align="center" prop="batchNo" />
      <el-table-column label="生产厂家" align="center" prop="manufacturer" />
      <el-table-column label="库存量" align="center" prop="inventory" />
      <el-table-column label="锁定库存" align="center" prop="lockInventory" />
      <el-table-column label="库存上限" align="center" prop="maxInventory" />
      <el-table-column label="库存下限" align="center" prop="minInventory" />
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />
  </div>
</template>

<script>
import { listMaterialInventoryDetailReport, exportMaterialInventoryDetailReport } from "@/api/wmsReport/materialInventoryDetailReport";
import { listWarehouseRegion } from "@/api/warehouse/warehouseRegion";
import { listWarehouseLocation } from "@/api/warehouse/warehouseLocation";
export default {
  name: "MaterialInventoryDetailReport",
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
      // 库存明细报表表格数据
      materialInventoryDetailReportList: [],
      // 仓库编号字典
      whIdOptions: [],
      whRegionOptions: [],
      whLocationOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        whId: null,
        whRegionId: null,
        whLocationId: null,
        batchNo: null,
        manufacturer: null,
        materialId: null,
        materialName: null,
        materialModel: null,
        materialSpecification: null,
      },
    };
  },
  created() {
    this.getList();
    this.getQueryData("query_warehouse").then(res => {
      this.whIdOptions = res.data;
    });
  },
  methods: {
    handleWarehouseChange(val) {
      this.queryParams.whRegionId = null;
      this.queryParams.whLocationId = null;
      if (val) {
        this.getWhRegionOpts(val);
        this.getList();
      }
    },
    handleWhRegionChange(val) {
      this.queryParams.whLocationId = null;
      if (val) {
        this.getWhLocationOpts(val);
        this.getList();
      }
    },
    handleWhLocationChange(val) {
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
    /** 查询库存明细报表列表 */
    getList() {
      this.loading = true;
      listMaterialInventoryDetailReport(this.queryParams).then(response => {
        this.materialInventoryDetailReportList = response.rows;
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
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有库存明细报表数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return exportMaterialInventoryDetailReport(queryParams);
      }).then(response => {
        this.download(response.msg);
      })
    }
  }
};
</script>
