<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="工艺名称" prop="processName">
        <el-input v-model="queryParams.processName" placeholder="请输入工艺名称" clearable size="small" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button v-no-more-click type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button v-no-more-click icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button v-no-more-click type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['mesModel:processModel:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-no-more-click type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate" v-hasPermi="['mesModel:processModel:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-no-more-click type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete" v-hasPermi="['mesModel:processModel:remove']">删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="processModelList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="工艺名称" align="center" prop="processName" />
      <el-table-column label="产品" align="center" prop="materialName" />
      <el-table-column label="型号" prop="materialModel">
      </el-table-column>
      <el-table-column label="规格" prop="materialSpecification">
      </el-table-column>
      <el-table-column label="单位" prop="materialUnit">
      </el-table-column>
      <el-table-column label="单位耗电量" align="center" prop="powerConsume" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-no-more-click size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['mesModel:processModel:edit']">修改</el-button>
          <el-button v-no-more-click size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)" v-hasPermi="['mesModel:processModel:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <!-- 添加或修改工艺建模对话框 -->
    <el-dialog v-if="open" :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="产品" prop="materialName">
          <el-input v-model="form.materialName" placeholder="请选择产品" disabled>
            <el-button v-no-more-click slot="append" @click="handleSelectProduct">选择</el-button>
          </el-input>
        </el-form-item>
        <el-form-item label="工艺名称" prop="processName">
          <el-input v-model="form.processName" placeholder="请输入工艺名称" />
        </el-form-item>
        <el-form-item label="单位耗电量" prop="powerConsume">
          <el-input v-model="form.powerConsume" placeholder="请输入耗电量" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="工艺描述">
          <editor v-model="form.description" :min-height="192" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button v-no-more-click type="primary" @click="submitForm">确 定</el-button>
        <el-button v-no-more-click @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!--选择产品档案-->
    <MaterialSelect :open="productSelectOpen" @onCancel="handleProductCancel" @onSelected="handleProductSelected"></MaterialSelect>
  </div>
</template>

<script>
import { listProcessModel, getProcessModel, delProcessModel, addProcessModel, updateProcessModel, exportProcessModel } from "@/api/mesModel/processModel";
import Editor from '@/components/MyEditor';
import MaterialSelect from "@/components/Material/MaterialSelect";
export default {
  name: "ProcessModel",
  components: {
    Editor,
    MaterialSelect,
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
      // 工艺建模表格数据
      processModelList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      productSelectOpen: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        materialId: null,
        processName: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        materialName: [
          { required: true, message: "物料不能为空", trigger: "blur" }
        ],
        processName: [
          { required: true, message: "工艺名称不能为空", trigger: "blur" },
          { max: 20, message: "工艺名称最多20个字符", trigger: "blur" }
        ],
        powerConsume: [
          { required: true, message: "耗电量不能为空", trigger: "blur" },
          { max: 10, message: "单位耗电量最多10个字符", trigger: "blur" }
        ],
        remark:[
          { max: 100, message: "备注最多100个字符", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    handleSelectProduct() {
      this.productSelectOpen = true;
    },
    handleProductSelected(list) {
      if (list && list.length > 0) {
        let material = list[0];
        this.form.materialId = material.id;
        this.form.materialName = material.name;
      }
      this.productSelectOpen = false;
    },
    handleProductCancel() {
      this.productSelectOpen = false;
    },
    /** 查询工艺建模列表 */
    getList() {
      this.loading = true;
      listProcessModel(this.queryParams).then(response => {
        this.processModelList = response.rows;
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
        materialId: null,
        materialName: null,
        processName: null,
        description: null,
        powerConsume: null,
        remark: null,
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
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加工艺建模";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getProcessModel(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改工艺建模";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateProcessModel(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addProcessModel(this.form).then(response => {
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
      this.$confirm('是否确认删除工艺建模编号为"' + ids + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delProcessModel(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有工艺建模数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return exportProcessModel(queryParams);
      }).then(response => {
        this.download(response.msg);
      })
    }
  }
};
</script>
