<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="能耗项" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入能耗项" clearable size="small" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="项目类型" prop="category">
        <el-select v-model="queryParams.category" placeholder="请选择项目类型" clearable filterable size="small">
          <el-option v-for="dict in categoryOptions" :key="dict.dictValue" :label="dict.dictLabel" :value="dict.dictValue" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button v-no-more-click type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button v-no-more-click icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button v-no-more-click type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['cfConfig:emissionChange:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-no-more-click type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate" v-hasPermi="['cfConfig:emissionChange:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-no-more-click type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete" v-hasPermi="['cfConfig:emissionChange:remove']">删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="emissionChangeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="能耗项" align="center" prop="name" />
      <el-table-column label="项目类型" align="center" prop="category" :formatter="categoryFormat" />
      <el-table-column label="日节约水" align="center" prop="daySaveWater" />
      <el-table-column label="日节约电" align="center" prop="daySaveElectric" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-no-more-click size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['cfConfig:emissionChange:edit']">修改</el-button>
          <el-button v-no-more-click size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)" v-hasPermi="['cfConfig:emissionChange:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <!-- 添加或修改变化碳排放对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="能耗项" prop="name">
          <el-input v-model="form.name" placeholder="请输入能耗项" />
        </el-form-item>
        <el-form-item label="项目类型" prop="category">
          <el-select v-model="form.category" placeholder="请选择项目类型" clearable filterable>
            <el-option v-for="dict in categoryOptions" :key="dict.dictValue" :label="dict.dictLabel" :value="dict.dictValue"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="日节约水" prop="daySaveWater">
          <el-input v-model="form.daySaveWater" placeholder="请输入日节约水" type="number" />
        </el-form-item>
        <el-form-item label="日节约电" prop="daySaveElectric">
          <el-input v-model="form.daySaveElectric" placeholder="请输入日节约电" type="number" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
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
import { listEmissionChange, getEmissionChange, delEmissionChange, addEmissionChange, updateEmissionChange, exportEmissionChange } from "@/api/cfConfig/emissionChange";

export default {
  name: "EmissionChange",
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
      // 变化碳排放表格数据
      emissionChangeList: [],
      // 项目类型字典
      categoryOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        category: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          { required: true, message: "能耗项不能为空", trigger: "blur" },
          { max: 20, message: "能耗项最多20个字符", trigger: "blur" }
        ],
        daySaveWater: [
          { required: true, message: "日节约水不能为空", trigger: "blur" },
          { max: 10, message: "日节约水最多10个字符", trigger: "blur" }
        ],
        daySaveElectric: [
          { required: true, message: "日节约电不能为空", trigger: "blur" },
          { max: 10, message: "日节约电最多10个字符", trigger: "blur" }
        ],
        remark:[
          { max: 100, message: "备注最多100个字符", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("change_carbon_emission").then(response => {
      this.categoryOptions = response.data;
    });
  },
  methods: {
    /** 查询变化碳排放列表 */
    getList() {
      this.loading = true;
      listEmissionChange(this.queryParams).then(response => {
        this.emissionChangeList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 项目类型字典翻译
    categoryFormat(row, column) {
      return this.selectDictLabel(this.categoryOptions, row.category);
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
        name: null,
        daySaveWater: null,
        daySaveElectric: null,
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
      this.title = "添加变化碳排放";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getEmissionChange(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改变化碳排放";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateEmissionChange(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addEmissionChange(this.form).then(response => {
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
      this.$confirm('是否确认删除变化碳排放编号为"' + ids + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delEmissionChange(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有变化碳排放数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return exportEmissionChange(queryParams);
      }).then(response => {
        this.download(response.msg);
      })
    }
  }
};
</script>
