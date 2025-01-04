<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="模型名称" prop="modelName">
        <el-input v-model="queryParams.modelName" placeholder="请输入模型名称" clearable size="small" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button v-no-more-click type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button v-no-more-click icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button v-no-more-click type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['mesModel:productModel:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-no-more-click type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate" v-hasPermi="['mesModel:productModel:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-no-more-click type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete" v-hasPermi="['mesModel:productModel:remove']">删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="productModelList" @selection-change="handleSelectionChange">
      <el-table-column width="55px" >
        <!-- <template slot="header" slot-scope="scope" >
          <input type="checkbox" />
        </template>
        <template slot-scope="scope">
          <input type="checkbox" />
        </template> -->
      </el-table-column>
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="模型名称" align="center" prop="modelName" />
      <el-table-column label="产品" align="center" prop="productName" />
      <el-table-column label="型号" align="center" prop="productModel" />
      <el-table-column label="规格" align="center" prop="productSpecification" />
      <el-table-column label="单位" align="center" prop="productUnit" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-no-more-click size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['mesModel:productModel:edit']">修改</el-button>
          <el-button v-no-more-click size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)" v-hasPermi="['mesModel:productModel:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <!-- 添加或修改产品建模对话框 -->
    <el-dialog v-if="open" :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px" :inline="true">
        <el-form-item label="产品" prop="productName">
          <el-input v-model="form.productName" placeholder="请选择产品" disabled>
            <el-button v-no-more-click slot="append" @click="handleSelectProduct">选择</el-button>
          </el-input>
        </el-form-item>
        <el-form-item label="产品型号" prop="productModel">
          <el-input v-model="form.productModel" placeholder="" disabled/>
        </el-form-item>
        <el-form-item label="产品规格" prop="productSpecification">
          <el-input v-model="form.productSpecification" placeholder="" disabled/>
        </el-form-item>
        <el-form-item label="模型名称" prop="modelName">
          <el-input v-model="form.modelName" placeholder="请输入模型名称" />
        </el-form-item>
        <el-form-item label="备注">
          <editor v-model="form.remark" :min-height="192" />
        </el-form-item>
        <el-divider content-position="center">产品模型明细信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button v-no-more-click type="primary" icon="el-icon-plus" size="mini" @click="handleAddMesProductModelDetail">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button v-no-more-click type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteMesProductModelDetail">删除</el-button>
          </el-col>
        </el-row>
        <el-table :data="mesProductModelDetailList" :row-class-name="rowMesProductModelDetailIndex" @selection-change="handleMesProductModelDetailSelectionChange" ref="mesProductModelDetail">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center" prop="index" width="50" />
          <el-table-column label="物料" prop="materialName" width="250">
            <template slot-scope="scope">
              <el-form-item :prop="'mesProductModelDetailList.'+scope.$index+'.materialName'" :rules="rules.materialName">
                <el-input v-model="scope.row.materialName" placeholder="请选择物料" disabled>
                  <el-button v-no-more-click slot="append" @click="handleSelectMaterial(scope.$index)">选择</el-button>
                </el-input>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="型号" prop="materialModel">
          </el-table-column>
          <el-table-column label="规格" prop="materialSpecification">
          </el-table-column>
          <el-table-column label="单位" prop="materialUnit">
          </el-table-column>
          <el-table-column label="备注" prop="detailRemark" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'mesProductModelDetailList.'+scope.$index+'.detailRemark'" :rules="rules.detailRemark">
                <el-input v-model="scope.row.detailRemark" placeholder="请输入备注" />
              </el-form-item>
            </template>
          </el-table-column>
        </el-table>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button v-no-more-click type="primary" @click="submitForm">确 定</el-button>
        <el-button v-no-more-click @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!--选择产品档案-->
    <MaterialSelect :open="productSelectOpen" @onCancel="handleProductCancel" @onSelected="handleProductSelected"></MaterialSelect>
    <!--选择物料档案-->
    <MaterialSelect :open="materialSelectOpen" @onCancel="handleMaterialCancel" @onSelected="handleMaterialSelected"></MaterialSelect>
  </div>
</template>

<script>
import { listProductModel, getProductModel, delProductModel, addProductModel, updateProductModel, exportProductModel } from "@/api/mesModel/productModel";
import Editor from '@/components/MyEditor';
import MaterialSelect from "@/components/Material/MaterialSelect";
import { getMaterialInfo } from "@/api/material/materialInfo";
export default {
  name: "ProductModel",
  components: {
    Editor,
    MaterialSelect,
  },
  data() {
    return {
      currentRowIndex: null,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedMesProductModelDetail: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 产品建模表格数据
      productModelList: [],
      // 产品模型明细表格数据
      mesProductModelDetailList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      materialSelectOpen: false,
      productSelectOpen: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        modelName: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        productName: [
          { required: true, message: "产品不能为空", trigger: "blur" }
        ],
        modelName: [
          { required: true, message: "模型名称不能为空", trigger: "blur" },
          { max: 20, message: "模型名称最多20个字符", trigger: "blur" }
        ],
        materialName: [
          { required: true, message: "物料不能为空", trigger: "blur" }
        ],
        detailRemark:[
          { max: 100, message: "备注最多100个字符", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  watch: {
    'mesProductModelDetailList': {
      handler(newVal, oldVal) {
        this.form.mesProductModelDetailList = newVal;
      },
      immediate: true,
      deep: true,
    }
  },
  methods: {
    //选择物料处理函数
    handleSelectMaterial(index) {
      this.materialSelectOpen = true;
      this.currentRowIndex = index;
    },
    handleMaterialCancel() {
      this.materialSelectOpen = false;
    },
    //选择物料回调函数
    handleMaterialSelected(list) {
      if (list && list.length > 0) {
        let material = list[0];
        this.mesProductModelDetailList[this.currentRowIndex].materialId = material.id;
        this.mesProductModelDetailList[this.currentRowIndex].materialName = material.name;
        this.mesProductModelDetailList[this.currentRowIndex].materialCode = material.code;
        this.mesProductModelDetailList[this.currentRowIndex].materialModel = material.model;
        this.mesProductModelDetailList[this.currentRowIndex].materialSpecification = material.specification;
        this.mesProductModelDetailList[this.currentRowIndex].materialUnit = material.unit;
      }
      this.materialSelectOpen = false;
      this.$up
    },
    handleSelectProduct() {
      this.productSelectOpen = true;
    },
    handleProductSelected(list) {
      if (list && list.length > 0) {
        let material = list[0];
        this.form.productId = material.id;
        this.form.productName = material.name;
        this.form.productModel = material.model;
        this.form.productSpecification = material.specification;
      }
      this.productSelectOpen = false;
    },
    handleProductCancel() {
      this.productSelectOpen = false;
    },
    /** 查询产品建模列表 */
    getList() {
      this.loading = true;
      listProductModel(this.queryParams).then(response => {
        this.productModelList = response.rows;
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
        productId: null,
        productName: null,
        modelName: null,
        remark: null,
        productSpecification: null,
        productModel: null,
        mesProductModelDetailList: [],
      };
      this.mesProductModelDetailList = [];
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
      this.title = "添加产品建模";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getProductModel(id).then(response => {
        this.form = response.data;
        getMaterialInfo(this.form.productId).then(res => {
          this.form.productName = res.data.name;
        })
        this.mesProductModelDetailList = response.data.mesProductModelDetailList;
        this.open = true;
        this.title = "修改产品建模";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {

          if (this.form.id != null) {
            updateProductModel(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addProductModel(this.form).then(response => {
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
      this.$confirm('是否确认删除产品建模编号为"' + ids + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delProductModel(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    /** 产品模型明细序号 */
    rowMesProductModelDetailIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
    /** 产品模型明细添加按钮操作 */
    handleAddMesProductModelDetail() {
      let obj = {};
      obj.materialId = "";
      obj.materialName = "";
      obj.materialModel = "";
      obj.materialSpecification = "";
      obj.materialUnit = "";
      obj.detailRemark = "";
      this.mesProductModelDetailList.push(obj);
    },
    /** 产品模型明细删除按钮操作 */
    handleDeleteMesProductModelDetail() {
      if (this.checkedMesProductModelDetail.length == 0) {
        this.$alert("请先选择要删除的产品模型明细数据", "提示", { confirmButtonText: "确定", });
      } else {
        this.mesProductModelDetailList.splice(this.checkedMesProductModelDetail[0].index - 1, 1);
      }
    },
    /** 单选框选中数据 */
    handleMesProductModelDetailSelectionChange(selection) {
      if (selection.length > 1) {
        this.$refs.mesProductModelDetail.clearSelection();
        this.$refs.mesProductModelDetail.toggleRowSelection(selection.pop());
      } else {
        this.checkedMesProductModelDetail = selection;
      }
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有产品建模数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return exportProductModel(queryParams);
      }).then(response => {
        this.download(response.msg);
      })
    }
  }
};
</script>
