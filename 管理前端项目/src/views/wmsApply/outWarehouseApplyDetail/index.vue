<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="申请单编号" prop="applyId">
        <el-input
          v-model="queryParams.applyId"
          placeholder="请输入申请单编号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="物料" prop="materialId">
        <el-input
          v-model="queryParams.materialId"
          placeholder="请输入物料"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="仓库" prop="warehouseId">
        <el-input
          v-model="queryParams.warehouseId"
          placeholder="请输入仓库"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="库位" prop="whLocationId">
        <el-input
          v-model="queryParams.whLocationId"
          placeholder="请输入库位"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="库区" prop="whRegionId">
        <el-input
          v-model="queryParams.whRegionId"
          placeholder="请输入库区"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发货数量" prop="outQuantity">
        <el-input
          v-model="queryParams.outQuantity"
          placeholder="请输入发货数量"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="生产批号" prop="batchNo">
        <el-input
          v-model="queryParams.batchNo"
          placeholder="请输入生产批号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="生产日期" prop="productDate">
        <el-date-picker clearable size="small"
          v-model="queryParams.productDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择生产日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="生产单位" prop="manufacturer">
        <el-input
          v-model="queryParams.manufacturer"
          placeholder="请输入生产单位"
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
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['wmsApply:outWarehouseApplyDetail:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['wmsApply:outWarehouseApplyDetail:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['wmsApply:outWarehouseApplyDetail:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['wmsApply:outWarehouseApplyDetail:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="outWarehouseApplyDetailList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="物料" align="center" prop="materialId" />
      <el-table-column label="仓库" align="center" prop="warehouseId" />
      <el-table-column label="库位" align="center" prop="whLocationId" />
      <el-table-column label="库区" align="center" prop="whRegionId" />
      <el-table-column label="发货数量" align="center" prop="outQuantity" />
      <el-table-column label="生产批号" align="center" prop="batchNo" />
      <el-table-column label="生产日期" align="center" prop="productDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.productDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="生产单位" align="center" prop="manufacturer" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['wmsApply:outWarehouseApplyDetail:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['wmsApply:outWarehouseApplyDetail:remove']"
          >删除</el-button>
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

    <!-- 添加或修改出库申请明细对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="申请单编号" prop="applyId">
          <el-input v-model="form.applyId" placeholder="请输入申请单编号" />
        </el-form-item>
        <el-form-item label="物料" prop="materialId">
          <el-input v-model="form.materialId" placeholder="请输入物料" />
        </el-form-item>
        <el-form-item label="仓库" prop="warehouseId">
          <el-input v-model="form.warehouseId" placeholder="请输入仓库" />
        </el-form-item>
        <el-form-item label="库位" prop="whLocationId">
          <el-input v-model="form.whLocationId" placeholder="请输入库位" />
        </el-form-item>
        <el-form-item label="库区" prop="whRegionId">
          <el-input v-model="form.whRegionId" placeholder="请输入库区" />
        </el-form-item>
        <el-form-item label="发货数量" prop="outQuantity">
          <el-input v-model="form.outQuantity" placeholder="请输入发货数量" />
        </el-form-item>
        <el-form-item label="生产批号" prop="batchNo">
          <el-input v-model="form.batchNo" placeholder="请输入生产批号" />
        </el-form-item>
        <el-form-item label="生产日期" prop="productDate">
          <el-date-picker clearable size="small"
            v-model="form.productDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择生产日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="生产单位" prop="manufacturer">
          <el-input v-model="form.manufacturer" placeholder="请输入生产单位" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button v-no-more-click type="primary" @click="submitForm">确 定</el-button>
        <el-button v-no-more-click @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listOutWarehouseApplyDetail, getOutWarehouseApplyDetail, delOutWarehouseApplyDetail, addOutWarehouseApplyDetail, updateOutWarehouseApplyDetail, exportOutWarehouseApplyDetail } from "@/api/wmsApply/outWarehouseApplyDetail";

export default {
  name: "OutWarehouseApplyDetail",
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
      // 出库申请明细表格数据
      outWarehouseApplyDetailList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        applyId: null,
        materialId: null,
        warehouseId: null,
        whLocationId: null,
        whRegionId: null,
        outQuantity: null,
        batchNo: null,
        productDate: null,
        manufacturer: null
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
  },
  methods: {
    /** 查询出库申请明细列表 */
    getList() {
      this.loading = true;
      listOutWarehouseApplyDetail(this.queryParams).then(response => {
        this.outWarehouseApplyDetailList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        applyId: null,
        materialId: null,
        warehouseId: null,
        whLocationId: null,
        whRegionId: null,
        outQuantity: null,
        serialNo: null,
        batchNo: null,
        productDate: null,
        manufacturer: null,
      };
      this.resetForm("form");
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
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加出库申请明细";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getOutWarehouseApplyDetail(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改出库申请明细";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateOutWarehouseApplyDetail(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addOutWarehouseApplyDetail(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除出库申请明细编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delOutWarehouseApplyDetail(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有出库申请明细数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportOutWarehouseApplyDetail(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
    }
  }
};
</script>
