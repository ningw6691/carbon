<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="物料名称" prop="materialName">
        <el-input v-model="queryParams.materialName" placeholder="请输入物料名称" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="检验项目" prop="checkItemName">
        <el-input v-model="queryParams.checkItemName" placeholder="请输入检验项目名称" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="检验类型" prop="checkType">
        <el-select v-model="queryParams.checkType" placeholder="请选择检验类型" clearable filterable size="small">
          <el-option v-for="dict in checkTypeOptions" :key="dict.dictValue" :label="dict.dictLabel"
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
        <el-button v-no-more-click type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['mesCheck:standard:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-no-more-click type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['mesCheck:standard:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-no-more-click type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['mesCheck:standard:remove']">删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="standardList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="物料名称" align="center" prop="materialName" />
      <el-table-column label="型号" align="center" prop="materialModel" />
      <el-table-column label="规格" align="center" prop="materialSpecification" />
      <el-table-column label="单位" align="center" prop="materialUnit" />
      <el-table-column label="检验项目" align="center" prop="checkItemName" />
      <el-table-column label="检验类型" align="center" prop="checkType" :formatter="checkTypeFormat" />
      <el-table-column label="标准值上限" align="center" prop="maxValue" />
      <el-table-column label="标准值下限" align="center" prop="minValue" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-no-more-click size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['mesCheck:standard:edit']">修改</el-button>
          <el-button v-no-more-click size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['mesCheck:standard:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改质检标准对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-form-item label="物料名称" prop="materialName">
          <el-input v-model="form.materialName" disabled>
            <el-button v-no-more-click slot="append" @click="handleSelectMaterial">选择</el-button>
          </el-input>
        </el-form-item>
        <el-form-item label="检验项目名称" prop="checkItemName">
          <el-input v-model="form.checkItemName" placeholder="请输入检验项目名称" />
        </el-form-item>
        <el-form-item label="检验类型" prop="checkType">
          <el-select v-model="form.checkType" placeholder="请选择检验类型" clearable filterable>
            <el-option v-for="dict in checkTypeOptions" :key="dict.dictValue" :label="dict.dictLabel"
              :value="dict.dictValue"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="标准值上限" prop="maxValue">
          <el-input v-model="form.maxValue" placeholder="请输入标准值上限" />
        </el-form-item>
        <el-form-item label="标准值下限" prop="minValue">
          <el-input v-model="form.minValue" placeholder="请输入标准值下限" />
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
    <!--选择物料档案-->
    <MaterialSelect :open="materialSelectOpen" @onCancel="handleMaterialCancel" @onSelected="handleMaterialSelected">
    </MaterialSelect>
  </div>
</template>

<script>
  import {
    listStandard,
    getStandard,
    delStandard,
    addStandard,
    updateStandard,
    exportStandard
  } from "@/api/mesCheck/standard";

  import MaterialSelect from "@/components/Material/MaterialSelect";

  export default {
    name: "Standard",
    components: {
      MaterialSelect,
    },
    data() {
      return {
        materialSelectOpen: false,
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
        // 质检标准表格数据
        standardList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 检验类型：1来料检验2产品检验3生产采样字典
        checkTypeOptions: [],
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          materialId: null,
          materialName: null,
          checkItemName: null,
          checkType: null,
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          materialId: [{
            required: true,
            message: "物料id不能为空",
            trigger: "blur"
          }],
          checkItemName: [{
            required: true,
            message: "检验项目名称不能为空",
            trigger: "blur"
          }, {
            max: 20,
            message: "长度不能超过20个字符",
            trigger: "blur"
          }],
          checkType: [{
            required: true,
            message: "检验类型不能为空",
            trigger: "change"
          }],
          maxValue: [{
            required: true,
            message: "标准值上限不能为空",
            trigger: "blur"
          }],
          minValue: [{
            required: true,
            message: "标准值下限不能为空",
            trigger: "blur"
          }],
        }
      };
    },
    created() {
      this.getList();
      this.getDicts("mes_check_type").then(response => {
        this.checkTypeOptions = response.data;
      });
    },
    methods: {
      //选择物料处理函数
      handleSelectMaterial() {
        this.materialSelectOpen = true;
      },
      handleMaterialCancel() {
        this.materialSelectOpen = false;
      },
      //选择物料回调函数
      handleMaterialSelected(list) {
        if (list && list.length > 0) {
          this.material = list[0];
          this.form.materialId = this.material.id;
          this.form.materialName = this.material.name;
          this.form.materialCode = this.material.code;
          this.form.materialModel = this.material.model;
          this.form.materialSpecification = this.material.specification;
          this.form.materialUnit = this.material.unit;
        }
        this.materialSelectOpen = false;
      },
      /** 查询质检标准列表 */
      getList() {
        this.loading = true;
        listStandard(this.queryParams).then(response => {
          this.standardList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },
      // 检验类型：1来料检验2产品检验3生产采样字典翻译
      checkTypeFormat(row, column) {
        return this.selectDictLabel(this.checkTypeOptions, row.checkType);
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
          checkItemName: null,
          checkType: null,
          maxValue: null,
          minValue: null,
          remark: null,
          materialName: null,
          materialModel: null,
          materialSpecification: null,
          materialUnit: null,
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
        this.title = "添加质检标准";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids
        getStandard(id).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改质检标准";
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.id != null) {
              updateStandard(this.form).then(response => {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addStandard(this.form).then(response => {
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
        this.$confirm('是否确认删除质检标准编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return delStandard(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
      },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams;
        this.$confirm('是否确认导出所有质检标准数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return exportStandard(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
      }
    }
  };

</script>
