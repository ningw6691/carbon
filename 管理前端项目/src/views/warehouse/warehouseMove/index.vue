<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="物料" prop="materialName">
        <el-input v-model="queryParams.materialName" placeholder="请输入物料" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="批号" prop="batchNo">
        <el-input v-model="queryParams.batchNo" placeholder="请输入批号" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="源仓库" prop="sourceWarehouseId">
        <el-select v-model="queryParams.sourceWarehouseId" placeholder="请选择源仓库" clearable filterable size="small"
          @change="handleSourceWhChange">
          <el-option v-for="dict in warehouseIdOptions" :key="dict.dictValue" :label="dict.dictLabel"
            :value="parseInt(dict.dictValue)"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="源库区" prop="sourceRegionId">
        <el-select v-model="queryParams.sourceRegionId" placeholder="请选择源库区" clearable filterable size="small"
          @change="handleSourceRegionChange">
          <el-option v-for="dict in sourceRegionOpts" :key="dict.id" :label="dict.name" :value="dict.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="源库位" prop="sourceLocationId">
        <el-select v-model="queryParams.sourceLocationId" placeholder="请选择源库位" clearable filterable size="small">
          <el-option v-for="dict in sourceLocationOpts" :key="dict.id" :label="dict.name" :value="dict.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="目标仓库" prop="targetWarehouseId">
        <el-select v-model="queryParams.targetWarehouseId" placeholder="请选择目标仓库" clearable filterable size="small"
          @change="handleTargetWhChange">
          <el-option v-for="dict in warehouseIdOptions" :key="dict.dictValue" :label="dict.dictLabel"
            :value="parseInt(dict.dictValue)"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="目标库区" prop="targetRegionId">
        <el-select v-model="queryParams.targetRegionId" placeholder="请选择目标库区" clearable filterable size="small"
          @change="handleTargetRegionChange">
          <el-option v-for="dict in targetRegionOpts" :key="dict.id" :label="dict.name" :value="dict.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="目标库位" prop="targetLocationId">
        <el-select v-model="queryParams.targetLocationId" placeholder="请选择目标库位" clearable filterable size="small">
          <el-option v-for="dict in targetLocationOpts" :key="dict.id" :label="dict.name" :value="dict.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="移库时间">
        <el-date-picker v-model="daterangeOperateDate" size="small" style="width: 240px" value-format="yyyy-MM-dd"
          type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="执行人" prop="operateUser">
        <el-select v-model="queryParams.operateUser" placeholder="请选择执行人" clearable filterable size="small">
          <el-option v-for="dict in operateUserOptions" :key="dict.dictValue" :label="dict.dictLabel"
            :value="dict.dictValue" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button v-no-more-click type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button v-no-more-click icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button v-no-more-click type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple"
          @click="handleDelete" v-hasPermi="['warehouse:warehouseMove:remove']">删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="warehouseMoveList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="物料" align="center" prop="materialName" />
      <el-table-column label="源仓库" align="center" prop="sourceWarehouseName" />
      <el-table-column label="源库区" align="center" prop="sourceRegionName" />
      <el-table-column label="源库位" align="center" prop="sourceLocationName" />
      <el-table-column label="批号" align="center" prop="batchNo" />
      <el-table-column label="目标仓库" align="center" prop="targetWarehouseName" />
      <el-table-column label="目标库区" align="center" prop="targetRegionName" />
      <el-table-column label="目标库位" align="center" prop="targetLocationName" />
      <el-table-column label="移出数量" align="center" prop="moveQuantity" />
      <el-table-column label="移库时间" align="center" prop="operateDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.operateDate, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="执行人" align="center" prop="operateUser" :formatter="operateUserFormat" />
      <el-table-column label="移库原因" align="center" prop="reason" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-no-more-click size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['warehouse:warehouseMove:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />
  </div>
</template>

<script>
import { listWarehouseMove, delWarehouseMove, exportWarehouseMove } from "@/api/warehouse/warehouseMove";
import { listWarehouseRegion } from "@/api/warehouse/warehouseRegion";
import { listWarehouseLocation } from "@/api/warehouse/warehouseLocation";
export default {
  name: "WarehouseMove",
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
      // 移库信息表格数据
      warehouseMoveList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 移库时间时间范围
      daterangeOperateDate: [],
      // 执行人字典
      operateUserOptions: [],
      warehouseIdOptions: [],
      sourceRegionOpts: [],
      sourceLocationOpts: [],
      targetRegionOpts: [],
      targetLocationOpts: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        materialName: null,
        sourceWarehouseId: null,
        sourceRegionId: null,
        sourceLocationId: null,
        batchNo: null,
        targetWarehouseId: null,
        targetRegionId: null,
        targetLocationId: null,
        operateDate: null,
        operateUser: null,
        status: null,
        orderByColumn: 'operate_date',
        isAsc: 'desc'
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
    this.getQueryData("query_user").then(res => {
      this.operateUserOptions = res.data;
    });
    this.getQueryData("query_warehouse").then(res => {
      this.warehouseIdOptions = res.data;
    });
  },
  methods: {
    handleSourceWhChange(val) {
      if (!val) {
        this.sourceRegionOpts = [];
        return;
      }
      this.queryParams.sourceRegionId = null;
      this.queryParams.sourceLocationId = null;
      let queryParams = {
        warehouseId: val,
      }
      listWarehouseRegion(queryParams).then(res => {
        this.sourceRegionOpts = res.rows;
      });
    },
    handleSourceRegionChange(val) {
      if (!val) {
        this.sourceLocationOpts = [];
        return;
      }
      this.queryParams.sourceLocationId = null;
      let queryParams = {
        regionId: val,
      }
      listWarehouseLocation(queryParams).then(res => {
        this.sourceLocationOpts = res.rows;
      })
    },
    handleTargetWhChange(val) {
      if (!val) {
        this.targetRegionOpts = [];
        return;
      }
      this.queryParams.targetRegionId = null;
      this.queryParams.targetLocationId = null;
      let queryParams = {
        warehouseId: val,
      }
      listWarehouseRegion(queryParams).then(res => {
        this.targetRegionOpts = res.rows;
      });
    },
    handleTargetRegionChange(val) {
      if (!val) {
        this.targetLocationOpts = [];
        return;
      }
      this.queryParams.targetLocationId = null;
      let queryParams = {
        regionId: val,
      }
      listWarehouseLocation(queryParams).then(res => {
        this.targetLocationOpts = res.rows;
      })
    },
    /** 查询移库信息列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeOperateDate && '' != this.daterangeOperateDate) {
        this.queryParams.params["beginOperateDate"] = this.daterangeOperateDate[0];
        this.queryParams.params["endOperateDate"] = this.daterangeOperateDate[1];
      }
      listWarehouseMove(this.queryParams).then(response => {
        this.warehouseMoveList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 执行人字典翻译
    operateUserFormat(row, column) {
      return this.selectDictLabel(this.operateUserOptions, row.operateUser);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.daterangeOperateDate = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除移库信息编号为"' + ids + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delWarehouseMove(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有移库信息数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return exportWarehouseMove(queryParams);
      }).then(response => {
        this.download(response.msg);
      })
    }
  }
};
</script>
