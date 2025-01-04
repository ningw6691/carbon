<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="承运申请单编号" prop="carrierApplyId">
        <el-input
          v-model="queryParams.carrierApplyId"
          placeholder="请输入承运申请单编号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="物料档案" prop="materialId">
        <el-input
          v-model="queryParams.materialId"
          placeholder="请输入物料档案"
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
      <el-form-item label="仓库" prop="warehouseId">
        <el-select v-model="queryParams.warehouseId" placeholder="请选择仓库" clearable filterable size="small">
          <el-option
            v-for="dict in warehouseIdOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="库区" prop="whRegionId">
        <el-select v-model="queryParams.whRegionId" placeholder="请选择库区" clearable filterable size="small">
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="库位" prop="whLocationId">
        <el-select v-model="queryParams.whLocationId" placeholder="请选择库位" clearable filterable size="small">
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="发货数量" prop="deliveryQuantity">
        <el-input
          v-model="queryParams.deliveryQuantity"
          placeholder="请输入发货数量"
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
          v-hasPermi="['transportApply:carrierApplyDetail:add']"
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
          v-hasPermi="['transportApply:carrierApplyDetail:edit']"
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
          v-hasPermi="['transportApply:carrierApplyDetail:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['transportApply:carrierApplyDetail:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="carrierApplyDetailList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="物料档案" align="center" prop="materialId" />
      <el-table-column label="生产批号" align="center" prop="batchNo" />
      <el-table-column label="仓库" align="center" prop="warehouseId" :formatter="warehouseIdFormat" />
      <el-table-column label="库区" align="center" prop="whRegionId" />
      <el-table-column label="库位" align="center" prop="whLocationId" />
      <el-table-column label="发货数量" align="center" prop="deliveryQuantity" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['transportApply:carrierApplyDetail:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['transportApply:carrierApplyDetail:remove']"
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

    <!-- 添加或修改产品承运申请明细对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="承运申请单编号" prop="carrierApplyId">
          <el-input v-model="form.carrierApplyId" placeholder="请输入承运申请单编号" />
        </el-form-item>
        <el-form-item label="物料档案" prop="materialId">
          <el-input v-model="form.materialId" placeholder="请输入物料档案" />
        </el-form-item>
        <el-form-item label="生产批号" prop="batchNo">
          <el-input v-model="form.batchNo" placeholder="请输入生产批号" />
        </el-form-item>
        <el-form-item label="仓库" prop="warehouseId">
          <el-select v-model="form.warehouseId" placeholder="请选择仓库" clearable filterable>
            <el-option
              v-for="dict in warehouseIdOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="库区" prop="whRegionId">
          <el-select v-model="form.whRegionId" placeholder="请选择库区" clearable filterable>
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
        <el-form-item label="库位" prop="whLocationId">
          <el-select v-model="form.whLocationId" placeholder="请选择库位" clearable filterable>
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
        <el-form-item label="发货数量" prop="deliveryQuantity">
          <el-input v-model="form.deliveryQuantity" placeholder="请输入发货数量" />
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
import { listCarrierApplyDetail, getCarrierApplyDetail, delCarrierApplyDetail, addCarrierApplyDetail, updateCarrierApplyDetail, exportCarrierApplyDetail } from "@/api/transportApply/carrierApplyDetail";

export default {
  name: "CarrierApplyDetail",
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
      // 产品承运申请明细表格数据
      carrierApplyDetailList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 仓库字典
      warehouseIdOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        carrierApplyId: null,
        materialId: null,
        batchNo: null,
        warehouseId: null,
        whRegionId: null,
        whLocationId: null,
        deliveryQuantity: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        materialId: [
          { required: true, message: "物料档案不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getQueryData("query_warehouse").then(res => {
      this.warehouseIdOptions = res.data;
    });
  },
  methods: {
    /** 查询产品承运申请明细列表 */
    getList() {
      this.loading = true;
      listCarrierApplyDetail(this.queryParams).then(response => {
        this.carrierApplyDetailList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 仓库字典翻译
    warehouseIdFormat(row, column) {
      return this.selectDictLabel(this.warehouseIdOptions, row.warehouseId);
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
        carrierApplyId: null,
        materialId: null,
        batchNo: null,
        warehouseId: null,
        whRegionId: null,
        whLocationId: null,
        deliveryQuantity: null,
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
      this.title = "添加产品承运申请明细";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getCarrierApplyDetail(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改产品承运申请明细";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateCarrierApplyDetail(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCarrierApplyDetail(this.form).then(response => {
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
      this.$confirm('是否确认删除产品承运申请明细编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delCarrierApplyDetail(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有产品承运申请明细数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportCarrierApplyDetail(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
    }
  }
};
</script>
