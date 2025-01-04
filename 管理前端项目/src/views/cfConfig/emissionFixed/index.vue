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
      <el-form-item label="时间类型" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择时间类型" clearable filterable size="small">
          <el-option v-for="dict in typeOptions" :key="dict.dictValue" :label="dict.dictLabel" :value="dict.dictValue" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button v-no-more-click type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button v-no-more-click icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button v-no-more-click type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['cfConfig:emissionFixed:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-no-more-click type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate" v-hasPermi="['cfConfig:emissionFixed:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-no-more-click type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete" v-hasPermi="['cfConfig:emissionFixed:remove']">删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="emissionFixedList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="能耗项" align="center" prop="name" />
      <el-table-column label="项目类型" align="center" prop="category" :formatter="categoryFormat" />
      <el-table-column label="时间类型" align="center" prop="type" :formatter="typeFormat" />
      <el-table-column label="能耗数" align="center" prop="energy" />
      <el-table-column label="预警阈值" align="center" prop="thresholdValue" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-no-more-click size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['cfConfig:emissionFixed:edit']">修改</el-button>
          <el-button v-no-more-click size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)" v-hasPermi="['cfConfig:emissionFixed:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <!-- 添加或修改固定碳排放对话框 -->
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
        <el-form-item label="时间类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择时间类型" clearable filterable>
            <el-option v-for="dict in typeOptions" :key="dict.dictValue" :label="dict.dictLabel" :value="dict.dictValue"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="能耗数" prop="energy">
          <el-input v-model.number="form.energy" placeholder="请输入能耗数" />
        </el-form-item>
        <el-form-item label="预警阈值" prop="thresholdValue">
          <el-input v-model.number="form.thresholdValue" placeholder="请输入预警阈值" />
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
import { listEmissionFixed, getEmissionFixed, delEmissionFixed, addEmissionFixed, updateEmissionFixed, exportEmissionFixed } from "@/api/cfConfig/emissionFixed";

export default {
  name: "EmissionFixed",
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
      // 固定碳排放表格数据
      emissionFixedList: [],
      // 项目类型字典
      categoryOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 时间类型字典
      typeOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        type: null,
        thresholdValue: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          { required: true, message: "能耗项不能为空", trigger: "blur" },
          { max: 20, message: "能耗项最多20个字符", trigger: "blur" }
        ],
        energy: [
          { type: 'number',min: 1, max: 65535, message: '必须为数字值且大小要在1到65535' },
        ],
        thresholdValue: [
          { type: 'number',min: 1, max: 65535, message: '必须为数字值且大小要在1到65535' },
        ],
        remark: [
          { max: 100, message: "备注最多100个字符", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("cf_emission_time_type").then(response => {
      this.typeOptions = response.data;
    });
    this.getDicts("fixed_carbon_emission").then(response => {
      this.categoryOptions = response.data;
    });
  },
  methods: {
    /** 查询固定碳排放列表 */
    getList() {
      this.loading = true;
      listEmissionFixed(this.queryParams).then(response => {
        this.emissionFixedList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 项目类型字典翻译
    categoryFormat(row, column) {
      return this.selectDictLabel(this.categoryOptions, row.category);
    },
    // 时间类型字典翻译
    typeFormat(row, column) {
      return this.selectDictLabel(this.typeOptions, row.type);
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
        type: null,
        thresholdValue: null,
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
      this.title = "添加固定碳排放";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getEmissionFixed(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改固定碳排放";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateEmissionFixed(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addEmissionFixed(this.form).then(response => {
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
      this.$confirm('是否确认删除固定碳排放编号为"' + ids + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delEmissionFixed(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有固定碳排放数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return exportEmissionFixed(queryParams);
      }).then(response => {
        this.download(response.msg);
      })
    }
  }
};
</script>