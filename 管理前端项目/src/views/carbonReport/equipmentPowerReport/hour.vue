<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px" :rules="rules">
      <el-form-item label="设备名称" prop="equipmentName">
        <el-input v-model="queryParams.equipmentName" placeholder="请输入设备名称" clearable size="small" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="日期" prop="productDate">
        <el-date-picker v-model="queryParams.productDate" size="small" style="width: 240px" value-format="yyyy-MM-dd" type="date" placeholder="请选择日期"></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button v-no-more-click type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button v-no-more-click icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="equipmentPowerReportList">
      <el-table-column label="设备名称" align="center" prop="equipmentName" />
      <el-table-column label="日期" align="center" prop="productDate">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.productDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="小时" align="center" prop="hour">
        <template slot-scope="scope">
          <span>{{ scope.row.hour }}点</span>
        </template>
      </el-table-column>
      <el-table-column label="耗电量" align="center" prop="totalPowerConsume" />
      <el-table-column label="碳排放" align="center" prop="totalCarbonEmission" />
    </el-table>
  </div>
</template>

<script>
import { listEquipmentPowerReportByHour, exportEquipmentPowerReport } from "@/api/carbonReport/equipmentPowerReport";

export default {
  name: "EquipmentPowerReport",
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
      // VIEW表格数据
      equipmentPowerReportList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 生产日期时间范围
      daterangeProductDate: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        equipmentId: null,
        equipmentName: null,
        planId: null,
        bomId: null,
        orderId: null,
        productDate: null,
        materialId: null,
        processId: null,
        productLineId: null,
        processName: null,
        productLineName: null
      },
      rules: {
        productDate: { required: true, message: "日期不能为空", trigger: "blur" }
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询VIEW列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeProductDate && '' != this.daterangeProductDate) {
        this.queryParams.params["beginProductDate"] = this.daterangeProductDate[0];
        this.queryParams.params["endProductDate"] = this.daterangeProductDate[1];
      }
      listEquipmentPowerReportByHour(this.queryParams).then(response => {
        this.equipmentPowerReportList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },


    /** 搜索按钮操作 */
    handleQuery() {
      this.$refs["queryForm"].validate(valid => {
        if (valid) {
          this.queryParams.pageNum = 1;
          this.getList();
        }
      })
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return exportEquipmentPowerReport(queryParams);
      }).then(response => {
        this.download(response.msg);
      })
    }
  }
};
</script>
