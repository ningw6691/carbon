<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="单据号" prop="serialNo">
        <el-input v-model="queryParams.serialNo" placeholder="请输入单据号" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="物料" prop="materialId">
        <el-input v-model="queryParams.materialId" placeholder="请输入物料" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="仓库" prop="warehouseId">
        <el-select v-model="queryParams.warehouseId" placeholder="请选择仓库" clearable filterable size="small"
          @change="handleWhChange4Query">
          <el-option v-for="dict in warehouseIdOptions" :key="dict.dictValue" :label="dict.dictLabel"
            :value="parseInt(dict.dictValue)"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="库区" prop="whRegionId">
        <el-select v-model="queryParams.whRegionId" placeholder="请选择库区" clearable filterable size="small"
          @change="handleRegionChange4Query">
          <el-option v-for="dict in whRegionOpts4Query" :key="dict.id" :label="dict.name" :value="dict.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="库位" prop="whLocationId">
        <el-select v-model="queryParams.whLocationId" placeholder="请选择库位" clearable filterable size="small">
          <el-option v-for="dict in whLocationOpts4Query" :key="dict.id" :label="dict.name"
            :value="dict.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="批号" prop="batchNo">
        <el-input v-model="queryParams.batchNo" placeholder="请输入批号" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="盘点类型" prop="checkType">
        <el-select v-model="queryParams.checkType" placeholder="请选择盘点类型" clearable filterable size="small">
          <el-option v-for="dict in checkTypeOptions" :key="dict.dictValue" :label="dict.dictLabel"
            :value="dict.dictValue" />
        </el-select>
      </el-form-item>
      <el-form-item label="库存月份" prop="stockMonth">
        <el-date-picker v-model="queryParams.stockMonth" type="month" placeholder="请输入库存月份" clearable size="small"
          @keyup.enter.native="handleQuery">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="盘点日期">
        <el-date-picker v-model="daterangeCheckDate" size="small" style="width: 240px" value-format="yyyy-MM-dd"
          type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="负责人" prop="checkUser">
        <el-select v-model="queryParams.checkUser" placeholder="请选择负责人" clearable filterable size="small">
          <el-option v-for="dict in checkUserOptions" :key="dict.dictValue" :label="dict.dictLabel"
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
          v-hasPermi="['warehouse:warehouseCheck:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-no-more-click type="success" plain icon="el-icon-edit" size="mini" :disabled="single"
          @click="handleUpdate" v-hasPermi="['warehouse:warehouseCheck:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-no-more-click type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple"
          @click="handleDelete" v-hasPermi="['warehouse:warehouseCheck:remove']">删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="warehouseCheckList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="单据号" align="center" prop="serialNo" width="150" />
      <el-table-column label="物料" align="center" prop="materialName" />
      <el-table-column label="仓库" align="center" prop="warehouseName" />
      <el-table-column label="库区" align="center" prop="whRegionName" />
      <el-table-column label="库位" align="center" prop="whLocationName" />
      <el-table-column label="批号" align="center" prop="batchNo" />
      <el-table-column label="盘点类型" align="center" prop="checkType" :formatter="checkTypeFormat" />
      <el-table-column label="库存月份" align="center" prop="stockMonth" />
      <el-table-column label="盘点日期" align="center" prop="checkDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.checkDate, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="负责人" align="center" prop="checkUser" :formatter="checkUserFormat" />
      <el-table-column label="现库存" align="center" prop="inventory" />
      <el-table-column label="实际库存" align="center" prop="actualInventory" />
      <el-table-column label="原因" align="center" prop="reason" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-no-more-click size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['warehouse:warehouseCheck:edit']">修改</el-button>
          <el-button v-no-more-click size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['warehouse:warehouseCheck:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改盘点信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="单据号" prop="serialNo">
          <el-input v-model="form.serialNo" placeholder="" disabled />
        </el-form-item>
        <el-form-item label="物料" prop="materialName">
          <el-input v-model="form.materialName" placeholder="请选择物料" disabled>
            <el-button v-no-more-click slot="append" @click="handleSelectMaterial">选择</el-button>
          </el-input>
        </el-form-item>
        <el-form-item label="仓库" prop="warehouseId">
          <el-select v-model="form.warehouseId" placeholder="" clearable filterable @change="handleWhChange" disabled>
            <el-option v-for="dict in warehouseIdOptions" :key="dict.dictValue" :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="库区" prop="whRegionId">
          <el-select v-model="form.whRegionId" placeholder="" clearable filterable @change="handleRegionChange"
            disabled>
            <el-option v-for="dict in whRegionOpts" :key="dict.id" :label="dict.name" :value="dict.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="库位" prop="whLocationId">
          <el-select v-model="form.whLocationId" placeholder="" clearable filterable disabled>
            <el-option v-for="dict in whLocationOpts" :key="dict.id" :label="dict.name" :value="dict.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="批号" prop="batchNo">
          <el-input v-model="form.batchNo" placeholder="" disabled />
        </el-form-item>
        <el-form-item label="库存月份" prop="stockMonth">
          <el-date-picker v-model="form.stockMonth" value-format="yyyy-MM" type="month" placeholder="请输入库存月份">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="现库存" prop="inventory">
          <el-input v-model.number="form.inventory" placeholder="" disabled />
        </el-form-item>
        <el-form-item label="实际库存" prop="actualInventory">
          <el-input v-model.number="form.actualInventory" placeholder="请输入实际库存" />
        </el-form-item>
        <el-form-item label="盘点日期" prop="checkDate">
          <el-date-picker clearable size="small" v-model="form.checkDate" type="date" value-format="yyyy-MM-dd"
            placeholder="选择盘点日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="负责人" prop="checkUser">
          <el-select v-model="form.checkUser" placeholder="请选择负责人" clearable filterable>
            <el-option v-for="dict in checkUserOptions" :key="dict.dictValue" :label="dict.dictLabel"
              :value="dict.dictValue"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="原因" prop="reason">
          <el-input v-model="form.reason" type="textarea" placeholder="请输入原因" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button v-no-more-click type="primary" @click="submitForm">确 定</el-button>
        <el-button v-no-more-click @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!--选择物料档案-->
    <MaterialDetailInventorySelect :open="materialSelectOpen" @onCancel="handleMaterialCancel"
      @onSelected="handleMaterialSelected"></MaterialDetailInventorySelect>
  </div>
</template>

<script>
import { listWarehouseCheck, getWarehouseCheck, delWarehouseCheck, addWarehouseCheck, updateWarehouseCheck, exportWarehouseCheck } from "@/api/warehouse/warehouseCheck";
import { listWarehouseRegion } from "@/api/warehouse/warehouseRegion";
import { listWarehouseLocation } from "@/api/warehouse/warehouseLocation";
import MaterialDetailInventorySelect from '@/components/Material/MaterialDetailInventorySelect.vue';
export default {
  name: "WarehouseCheck",
  components: {
    MaterialDetailInventorySelect,
  },
  data() {
    return {
      // material:null,
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
      // 盘点信息表格数据
      warehouseCheckList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      materialSelectOpen: false,
      // 盘点类型字典
      checkTypeOptions: [],
      // 盘点日期时间范围
      daterangeCheckDate: [],
      // 负责人字典
      checkUserOptions: [],
      warehouseIdOptions: [],
      whRegionOpts: [],
      whLocationOpts: [],
      whRegionOpts4Query: [],
      whLocationOpts4Query: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        serialNo: null,
        materialId: null,
        warehouseId: null,
        whRegionId: null,
        whLocationId: null,
        batchNo: null,
        checkType: null,
        stockMonth: null,
        checkDate: null,
        checkUser: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        materialName: [
          { required: true, message: "物料不能为空", trigger: "blur" }
        ],
        warehouseId: [
          { required: true, message: "仓库不能为空", trigger: "change" }
        ],
        whRegionId: [
          { required: true, message: "库区不能为空", trigger: "change" }
        ],
        whLocationId: [
          { required: true, message: "库位不能为空", trigger: "change" }
        ],
        batchNo: [
          { required: true, message: "批号不能为空", trigger: "blur" }
        ],
        stockMonth: [
          { required: true, message: "库存月份不能为空", trigger: "blur" }
        ],
        inventory: [
          { required: true, message: "现库存不能为空", trigger: "blur" },
          { type: 'number', min: 1, max: 65535, message: '现库存必须为数字且大小要在1到65535' },
        ],
        actualInventory: [
          { required: true, message: "实际库存不能为空", trigger: "blur" },
          { type: 'number', min: 1, max: 65535, message: '实际库存必须为数字且大小要在1到65535' },
        ],
        checkDate: [
          { required: true, message: "盘点日期不能为空", trigger: "blur" }
        ],
        reason: [
          { max: 100, message: "原因最多100个字符", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("wms_check_type").then(response => {
      this.checkTypeOptions = response.data;
    });
    this.getQueryData("query_user").then(res => {
      this.checkUserOptions = res.data;
    });
    this.getQueryData("query_warehouse").then(res => {
      this.warehouseIdOptions = res.data;
    });
  },
  methods: {
    handleMaterialSelected(material) {
      this.form.materialId = material.materialId;
      this.form.materialName = material.materialName;
      this.form.materialCode = material.materialCode;
      this.form.materialModel = material.materialModel;
      this.form.materialSpecification = material.materialSpecification;
      this.form.materialUnit = material.unit;
      this.form.warehouseId = material.whId;
      this.form.whRegionId = material.whRegionId;
      this.form.whLocationId = material.whLocationId;
      this.form.batchNo = material.batchNo;
      this.form.inventory = material.inventory;
      this.getRegionOpts(material.whId);
      this.getLocationOpts(material.whRegionId);
      this.materialSelectOpen = false;
    },
    handleMaterialCancel() {
      this.materialSelectOpen = false;
    },
    handleSelectMaterial() {
      this.materialSelectOpen = true;
    },
    handleWhChange4Query(val) {
      this.queryParams.whRegionId = null;
      this.queryParams.whLocationId = null;
      this.getRegionOpts(val);
    },
    handleRegionChange4Query(val) {
      this.queryParams.whLocationId = null;
      this.getLocationOpts(val);
    },
    handleWhChange(val) {
      this.form.whRegionId = null;
      this.form.whLocationId = null;
      this.getRegionOpts(val);
    },
    handleRegionChange(val) {
      this.form.whLocationId = null;
      this.getLocationOpts(val);
    },
    getLocationOpts(val) {
      let queryParams = {
        regionId: val,
      }
      listWarehouseLocation(queryParams).then(res => {
        this.whLocationOpts = res.rows;
      })
    },
    getRegionOpts(val) {
      let queryParams = {
        warehouseId: val,
      }
      listWarehouseRegion(queryParams).then(res => {
        this.whRegionOpts = res.rows;
      });
    },
    /** 查询盘点信息列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeCheckDate && '' != this.daterangeCheckDate) {
        this.queryParams.params["beginCheckDate"] = this.daterangeCheckDate[0];
        this.queryParams.params["endCheckDate"] = this.daterangeCheckDate[1];
      }
      listWarehouseCheck(this.queryParams).then(response => {
        this.warehouseCheckList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 盘点类型字典翻译
    checkTypeFormat(row, column) {
      return this.selectDictLabel(this.checkTypeOptions, row.checkType);
    },
    // 负责人字典翻译
    checkUserFormat(row, column) {
      return this.selectDictLabel(this.checkUserOptions, row.checkUser);
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
        serialNo: null,
        materialId: null,
        materialName: null,
        warehouseId: null,
        whRegionId: null,
        whLocationId: null,
        warehouseName: null,
        whRegionName: null,
        whLocationName: null,
        batchNo: null,
        checkType: null,
        stockMonth: null,
        checkDate: null,
        checkUser: null,
        inventory: null,
        actualInventory: null,
        reason: null,
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
      this.daterangeCheckDate = [];
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
      this.title = "添加盘点信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getWarehouseCheck(id).then(response => {
        this.form = response.data;
        this.getRegionOpts(this.form.warehouseId);
        this.getLocationOpts(this.form.whRegionId);
        this.open = true;
        this.title = "修改盘点信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateWarehouseCheck(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addWarehouseCheck(this.form).then(response => {
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
      this.$confirm('是否确认删除盘点信息编号为"' + ids + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delWarehouseCheck(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有盘点信息数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return exportWarehouseCheck(queryParams);
      }).then(response => {
        this.download(response.msg);
      })
    }
  }
};
</script>
