<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!--分类数据-->
      <el-col :span="4" :xs="24">
        <div class="head-container">
          <el-button v-no-more-click type="primary" plain size="mini" @click="handleTag">标签模板</el-button>
        </div>
        <div class="head-container">
          <el-tree :data="categoryOptions" :props="defaultProps" :expand-on-click-node="false" ref="tree"
            default-expand-all @node-click="handleNodeClick" />
        </div>
      </el-col>
      <!--物料数据-->
      <el-col :span="20" :xs="24">
        <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="物料编码" prop="code">
            <el-input v-model="queryParams.code" placeholder="请输入物料编码" clearable size="small"
              @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="物料名称" prop="name">
            <el-input v-model="queryParams.name" placeholder="请输入物料名称" clearable size="small"
              @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="型号" prop="model">
            <el-input v-model="queryParams.model" placeholder="请输入型号" clearable size="small"
              @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="规格" prop="specification">
            <el-input v-model="queryParams.specification" placeholder="请输入规格" clearable size="small"
              @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="单位" prop="unit">
            <el-input v-model="queryParams.unit" placeholder="请输入单位" clearable size="small"
              @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="类型" prop="type">
            <el-select v-model="queryParams.type" placeholder="请选择类型" clearable filterable size="small">
              <el-option v-for="dict in typeOptions" :key="dict.dictValue" :label="dict.dictLabel"
                :value="dict.dictValue" />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button v-no-more-click type="primary" icon="el-icon-search" size="mini"
              @click="handleQuery">搜索</el-button>
            <el-button v-no-more-click icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>

        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button v-no-more-click type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
              v-hasPermi="['material:materialInfo:add']">新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button v-no-more-click type="success" plain icon="el-icon-edit" size="mini" :disabled="single"
              @click="handleUpdate" v-hasPermi="['material:materialInfo:edit']">修改</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button v-no-more-click type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple"
              @click="handleDelete" v-hasPermi="['material:materialInfo:remove']">删除</el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="materialInfoList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="编号" align="center" prop="id" />
          <el-table-column label="物料编码" align="center" prop="code" />
          <el-table-column label="物料名称" align="center" prop="name" />
          <el-table-column label="型号" align="center" prop="model" />
          <el-table-column label="规格" align="center" prop="specification" />
          <el-table-column label="单位" align="center" prop="unit" />
          <el-table-column label="单价" align="center" prop="price" />
          <el-table-column label="类型" align="center" prop="type" :formatter="typeFormat" />
          <el-table-column label="备注" align="center" prop="remark" />
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button v-no-more-click size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
                v-hasPermi="['material:materialInfo:edit']">修改</el-button>
              <el-button v-no-more-click size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                v-hasPermi="['material:materialInfo:remove']">删除</el-button>
            </template>
          </el-table-column>
        </el-table>

        <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
          @pagination="getList" />
      </el-col>
    </el-row>

    <!-- 添加或修改物料档案管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="分类" prop="categoryId">
          <treeselect v-model="form.categoryId" :options="categoryOptions" :normalizer="normalizer"
            :default-expand-level="1" :multiple="false" placeholder="请选择分类">
          </treeselect>
        </el-form-item>
        <el-form-item label="物料编码" prop="code">
          <el-input v-model="form.code" placeholder="请输入物料编码" />
        </el-form-item>
        <el-form-item label="物料名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入物料名称" />
        </el-form-item>
        <el-form-item label="型号" prop="model">
          <el-input v-model="form.model" placeholder="请输入型号" />
        </el-form-item>
        <el-form-item label="规格" prop="specification">
          <el-input v-model="form.specification" placeholder="请输入规格" />
        </el-form-item>
        <el-form-item label="单位" prop="unit">
          <el-input v-model="form.unit" placeholder="请输入单位" />
        </el-form-item>
        <el-form-item label="单价" prop="price">
          <el-input v-model="form.price" placeholder="请输入单价" />
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择类型" clearable filterable>
            <el-option v-for="dict in typeOptions" :key="dict.dictValue" :label="dict.dictLabel"
              :value="dict.dictValue"></el-option>
          </el-select>
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
import { listMaterialInfo, getMaterialInfo, delMaterialInfo, addMaterialInfo, updateMaterialInfo, exportMaterialInfo } from "@/api/material/materialInfo";
import { listMaterialCategory } from "@/api/material/materialCategory";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
export default {
  name: "MaterialInfo",
  components: {
    Treeselect
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
      // 物料档案管理表格数据
      materialInfoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 类型字典
      typeOptions: [],
      categoryOptions: [],
      defaultProps: {
        children: "children",
        label: "name"
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        categoryId: null,
        code: null,
        name: null,
        model: null,
        specification: null,
        unit: null,
        type: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        categoryId: [
          { required: true, message: "分类不能为空", trigger: "change" }
        ],
        name: [
          { required: true, message: "物料名称不能为空", trigger: "blur" },
          { min: 1, max: 10, message: "长度在1到10个字符", trigger: "blur" }
        ],
        code: [{ min: 1, max: 10, message: "长度在1到10个字符", trigger: "blur" }],
        model: [{ min: 1, max: 10, message: "长度在1到10个字符", trigger: "blur" }],
        specification: [{ min: 1, max: 20, message: "长度在1到20个字符", trigger: "blur" }],
        unit: [{ min: 1, max: 10, message: "长度在1到10个字符", trigger: "blur" }]
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("material_type").then(response => {
      this.typeOptions = response.data;
    });
    this.getTreeselect();
  },
  methods: {
    handleTag() {
      if (!this.queryParams.categoryId) {
        this.msgError('请选择分类');
        return;
      }
      this.$router.push({
        path: '/wms/materialTag',
        query: {
          categoryId: this.queryParams.categoryId,
        }
      });
    },
    /** 查询下拉树结构 */
    getTreeselect() {
      listMaterialCategory().then(response => {
        this.categoryOptions = [];
        const data = { id: 0, name: '分类', children: [] };
        data.children = this.handleTree(response.data, "id", "parentId");
        this.categoryOptions.push(data);
      });
    },
    normalizer(node) {
      return {
        id: node.id,
        label: node.name,
        children: node.children,
      }
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.categoryId = data.id;
      this.getList();
    },
    /** 查询物料档案管理列表 */
    getList() {
      this.loading = true;
      listMaterialInfo(this.queryParams).then(response => {
        this.materialInfoList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 类型字典翻译
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
        categoryId: null,
        code: null,
        name: null,
        model: null,
        specification: null,
        unit: null,
        price: null,
        type: null,
        remark: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.queryParams.categoryId = null;
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
      this.title = "添加物料档案管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getMaterialInfo(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改物料档案管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateMaterialInfo(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addMaterialInfo(this.form).then(response => {
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
      this.$confirm('是否确认删除物料档案管理编号为"' + ids + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delMaterialInfo(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有物料档案管理数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return exportMaterialInfo(queryParams);
      }).then(response => {
        this.download(response.msg);
      })
    }
  }
};
</script>
