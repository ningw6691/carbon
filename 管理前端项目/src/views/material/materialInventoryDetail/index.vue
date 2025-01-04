<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="仓库" prop="whId">
        <el-input
          v-model="queryParams.whId"
          placeholder="请输入仓库"
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
      <el-form-item label="库位" prop="whLocationId">
        <el-input
          v-model="queryParams.whLocationId"
          placeholder="请输入库位"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="物料" prop="whMaterialId">
        <el-input
          v-model="queryParams.whMaterialId"
          placeholder="请输入物料"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="序列号" prop="serialNo">
        <el-input
          v-model="queryParams.serialNo"
          placeholder="请输入序列号"
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
      <el-form-item label="生产厂家" prop="manufacturer">
        <el-input
          v-model="queryParams.manufacturer"
          placeholder="请输入生产厂家"
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
          v-hasPermi="['material:materialInventoryDetail:add']"
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
          v-hasPermi="['material:materialInventoryDetail:edit']"
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
          v-hasPermi="['material:materialInventoryDetail:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['material:materialInventoryDetail:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="materialInventoryDetailList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="仓库" align="center" prop="whId" />
      <el-table-column label="库区" align="center" prop="whRegionId" />
      <el-table-column label="库位" align="center" prop="whLocationId" />
      <el-table-column label="物料" align="center" prop="whMaterialId" />
      <el-table-column label="序列号" align="center" prop="serialNo" />
      <el-table-column label="生产日期" align="center" prop="productDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.productDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="生产厂家" align="center" prop="manufacturer" />
      <el-table-column label="生产批号" align="center" prop="batchNo" />
      <el-table-column label="库存量" align="center" prop="inventory" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['material:materialInventoryDetail:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['material:materialInventoryDetail:remove']"
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

    <!-- 添加或修改仓库物料明细对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="仓库" prop="whId">
          <el-input v-model="form.whId" placeholder="请输入仓库" />
        </el-form-item>
        <el-form-item label="库区" prop="whRegionId">
          <el-input v-model="form.whRegionId" placeholder="请输入库区" />
        </el-form-item>
        <el-form-item label="库位" prop="whLocationId">
          <el-input v-model="form.whLocationId" placeholder="请输入库位" />
        </el-form-item>
        <el-form-item label="物料" prop="whMaterialId">
          <el-input v-model="form.whMaterialId" placeholder="请输入物料" />
        </el-form-item>
        <el-form-item label="序列号" prop="serialNo">
          <el-input v-model="form.serialNo" placeholder="请输入序列号" />
        </el-form-item>
        <el-form-item label="生产日期" prop="productDate">
          <el-date-picker clearable size="small"
            v-model="form.productDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择生产日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="生产厂家" prop="manufacturer">
          <el-input v-model="form.manufacturer" placeholder="请输入生产厂家" />
        </el-form-item>
        <el-form-item label="生产批号" prop="batchNo">
          <el-input v-model="form.batchNo" placeholder="请输入生产批号" />
        </el-form-item>
        <el-form-item label="库存量" prop="inventory">
          <el-input v-model="form.inventory" placeholder="请输入库存量" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注" />
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
import { listMaterialInventoryDetail, getMaterialInventoryDetail, delMaterialInventoryDetail, addMaterialInventoryDetail, updateMaterialInventoryDetail, exportMaterialInventoryDetail } from "@/api/material/materialInventoryDetail";

export default {
  name: "MaterialInventoryDetail",
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
      // 仓库物料明细表格数据
      materialInventoryDetailList: [],
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
        whMaterialId: null,
        serialNo: null,
        productDate: null,
        manufacturer: null,
        batchNo: null,
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
    /** 查询仓库物料明细列表 */
    getList() {
      this.loading = true;
      listMaterialInventoryDetail(this.queryParams).then(response => {
        this.materialInventoryDetailList = response.rows;
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
        whId: null,
        whRegionId: null,
        whLocationId: null,
        whMaterialId: null,
        serialNo: null,
        productDate: null,
        manufacturer: null,
        batchNo: null,
        inventory: null,
        remark: null,
        createTime: null,
        updateTime: null,
        createBy: null,
        updateBy: null
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
      this.title = "添加仓库物料明细";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getMaterialInventoryDetail(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改仓库物料明细";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateMaterialInventoryDetail(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addMaterialInventoryDetail(this.form).then(response => {
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
      this.$confirm('是否确认删除仓库物料明细编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delMaterialInventoryDetail(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有仓库物料明细数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportMaterialInventoryDetail(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
    }
  }
};
</script>
