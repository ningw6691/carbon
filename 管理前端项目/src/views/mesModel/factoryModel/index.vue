<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="90px">
      <el-form-item label="生产线名称" prop="productLineName">
        <el-input v-model="queryParams.productLineName" placeholder="请输入生产线名称" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button v-no-more-click type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button v-no-more-click icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button v-no-more-click type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['mesModel:factoryModel:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-no-more-click type="success" plain icon="el-icon-edit" size="mini" :disabled="single"
          @click="handleUpdate" v-hasPermi="['mesModel:factoryModel:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-no-more-click type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple"
          @click="handleDelete" v-hasPermi="['mesModel:factoryModel:remove']">删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="factoryModelList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="产品" align="center" prop="materialName" />
      <el-table-column label="生产线名称" align="center" prop="productLineName" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-no-more-click size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['mesModel:factoryModel:edit']">修改</el-button>
          <el-button v-no-more-click size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['mesModel:factoryModel:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改工厂建模对话框 -->
    <el-dialog v-if="open" :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :inline="true" :rules="rules" label-width="100px">
        <el-form-item label="生产线名称" prop="productLineName">
          <el-input v-model="form.productLineName" placeholder="请输入生产线名称" />
        </el-form-item>
        <el-form-item label="产品" prop="materialName">
          <el-input v-model="form.materialName" placeholder="请选择产品" disabled>
            <el-button v-no-more-click slot="append" @click="handleSelectMaterial">选择</el-button>
          </el-input>
        </el-form-item>
        <el-form-item label="产品型号" prop="productModel">
          <el-input v-model="form.productModel" placeholder="" disabled />
        </el-form-item>
        <el-form-item label="产品规格" prop="productSpecification">
          <el-input v-model="form.productSpecification" placeholder="" disabled />
        </el-form-item>
        <el-form-item label="描述">
          <editor v-model="form.remark" :min-height="192" />
        </el-form-item>
        <el-divider content-position="center">工厂建模明细信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button v-no-more-click type="primary" icon="el-icon-plus" size="mini"
              @click="handleAddMesFactoryModelDetail">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button v-no-more-click type="danger" icon="el-icon-delete" size="mini"
              @click="handleDeleteMesFactoryModelDetail">删除</el-button>
          </el-col>
        </el-row>
        <el-table :data="mesFactoryModelDetailList" :row-class-name="rowMesFactoryModelDetailIndex"
          @selection-change="handleMesFactoryModelDetailSelectionChange" ref="mesFactoryModelDetail">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center" prop="index" width="50" />
          <el-table-column label="设备" prop="equipmentId" width="200">
            <template slot-scope="scope">
              <el-form-item :prop="'mesFactoryModelDetailList.' + scope.$index + '.equipmentId'
                " :rules="rules.equipmentId">
                <el-select v-model="scope.row.equipmentId" placeholder="请选择设备" clearable @change="
                  handleEquipChange(scope.row.equipmentId, scope.$index)
                  ">
                  <el-option v-for="dict in equipmentIdOptions" :key="dict.dictValue" :label="dict.dictLabel"
                    :value="parseInt(dict.dictValue)"></el-option>
                </el-select>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="编码" align="center" prop="code" />
          <el-table-column label="型号" align="center" prop="model" />
          <el-table-column label="生产日期" align="center" prop="productDate">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.productDate, "{y}-{m}-{d}") }}</span>
            </template>
          </el-table-column>
          <el-table-column label="生产批号" align="center" prop="batchNo" />
          <el-table-column label="制造商" align="center" prop="manufacturer" />
        </el-table>
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
  listFactoryModel,
  getFactoryModel,
  delFactoryModel,
  addFactoryModel,
  updateFactoryModel,
  exportFactoryModel,
} from "@/api/mesModel/factoryModel";
import Editor from "@/components/MyEditor";
import MaterialSelect from "@/components/Material/MaterialSelect";
import { getEquipmentInfo } from "@/api/baseInfo/equipmentInfo";
export default {
  name: "FactoryModel",
  components: {
    Editor,
    MaterialSelect,
  },
  data() {
    return {
      material: null,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedMesFactoryModelDetail: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 工厂建模表格数据
      factoryModelList: [],
      // 工厂建模明细表格数据
      mesFactoryModelDetailList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      materialSelectOpen: false,
      // 设备字典
      equipmentIdOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        materialId: null,
        productLineName: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        materialName: [
          { required: true, message: "物料不能为空", trigger: "blur" },
        ],
        productLineName: [
          { required: true, message: "生产线名称不能为空", trigger: "blur" },
          { max: 20, message: "生产线名称最多20个字符", trigger: "blur" },
        ],
        equipmentId: [
          { required: true, message: "设备不能为空", trigger: "change" },
        ],
      },
    };
  },
  created() {
    this.getList();
    this.getQueryData("query_equipment").then((res) => {
      this.equipmentIdOptions = res.data;
    });
  },
  watch: {
    mesFactoryModelDetailList: {
      handler(newVal, oldVal) {
        this.form.mesFactoryModelDetailList = newVal;
      },
      immediate: true,
      deep: true,
    },
  },
  methods: {
    handleEquipChange(val, index) {
      if (val) {
        let row = this.mesFactoryModelDetailList[index];
        this.getEquipmentDetail(val, row);
      }
    },
    getEquipmentDetail(eqId, row) {
      if (!eqId) {
        return;
      }
      getEquipmentInfo(eqId).then((res) => {
        let equip = res.data;
        row.code = equip.code;
        row.model = equip.model;
        row.productDate = equip.productDate;
        row.batchNo = equip.batchNo;
        row.manufacturer = equip.manufacturer;
      });
    },
    handleSelectMaterial() {
      this.materialSelectOpen = true;
    },
    handleMaterialSelected(list) {
      if (list && list.length > 0) {
        this.material = list[0];
        this.form.materialId = this.material.id;
        this.form.materialName = this.material.name;
        this.form.productModel = this.material.model;
        this.form.productSpecification = this.material.specification;
      }
      this.materialSelectOpen = false;
    },
    handleMaterialCancel() {
      this.materialSelectOpen = false;
    },
    /** 查询工厂建模列表 */
    getList() {
      this.loading = true;
      listFactoryModel(this.queryParams).then((response) => {
        this.factoryModelList = response.rows;
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
        productSpecification: null,
        productModel: null,
        productLineName: null,
        remark: null,
        mesFactoryModelDetailList: [],
      };
      this.mesFactoryModelDetailList = [];
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
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加工厂建模";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getFactoryModel(id).then((response) => {
        this.form = response.data;
        response.data.mesFactoryModelDetailList.forEach((eq) => {
          let obj = {
            code: "",
            manufacturer: "",
            productDate: "",
            batchNo: "",
            model: "",
          };
          this.mesFactoryModelDetailList.push(Object.assign(obj, eq));
        });
        this.mesFactoryModelDetailList.forEach((eq) => {
          this.getEquipmentDetail(eq.equipmentId, eq);
        });
        this.open = true;
        this.title = "修改工厂建模";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateFactoryModel(this.form).then((response) => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addFactoryModel(this.form).then((response) => {
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
      this.$confirm(
        '是否确认删除工厂建模编号为"' + ids + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delFactoryModel(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        });
    },
    /** 工厂建模明细序号 */
    rowMesFactoryModelDetailIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
    /** 工厂建模明细添加按钮操作 */
    handleAddMesFactoryModelDetail() {
      let obj = {};
      obj.equipmentId = "";
      obj.code = "";
      obj.model = "";
      obj.manufacturer = "";
      obj.productDate = "";
      obj.batchNo = "";
      this.mesFactoryModelDetailList.push(obj);
    },
    /** 工厂建模明细删除按钮操作 */
    handleDeleteMesFactoryModelDetail() {
      if (this.checkedMesFactoryModelDetail.length == 0) {
        this.$alert("请先选择要删除的工厂建模明细数据", "提示", {
          confirmButtonText: "确定",
        });
      } else {
        this.mesFactoryModelDetailList.splice(
          this.checkedMesFactoryModelDetail[0].index - 1,
          1
        );
      }
    },
    /** 单选框选中数据 */
    handleMesFactoryModelDetailSelectionChange(selection) {
      if (selection.length > 1) {
        this.$refs.mesFactoryModelDetail.clearSelection();
        this.$refs.mesFactoryModelDetail.toggleRowSelection(selection.pop());
      } else {
        this.checkedMesFactoryModelDetail = selection;
      }
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm("是否确认导出所有工厂建模数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return exportFactoryModel(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        });
    },
  },
};
</script>
