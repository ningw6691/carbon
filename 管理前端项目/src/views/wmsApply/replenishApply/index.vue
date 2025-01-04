<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="单据号" prop="serialNo">
        <el-input v-model="queryParams.serialNo" placeholder="请输入单据号" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable filterable size="small">
          <el-option v-for="dict in statusOptions" :key="dict.dictValue" :label="dict.dictLabel"
            :value="dict.dictValue" />
        </el-select>
      </el-form-item>
      <el-form-item label="申请人" prop="applyUser">
        <el-select v-model="queryParams.applyUser" placeholder="请选择申请人" clearable filterable size="small">
          <el-option v-for="dict in applyUserOptions" :key="dict.dictValue" :label="dict.dictLabel"
            :value="dict.dictValue" />
        </el-select>
      </el-form-item>
      <el-form-item label="申请日期">
        <el-date-picker v-model="daterangeApplyTime" size="small" style="width: 240px" value-format="yyyy-MM-dd"
          type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="申请状态" prop="applyStatus">
        <el-select v-model="queryParams.applyStatus" placeholder="请选择申请状态" clearable filterable size="small">
          <el-option v-for="dict in applyStatusOptions" :key="dict.dictValue" :label="dict.dictLabel"
            :value="dict.dictValue" />
        </el-select>
      </el-form-item>
      <el-form-item label="审核人" prop="auditUser">
        <el-select v-model="queryParams.auditUser" placeholder="请选择审核人" clearable filterable size="small">
          <el-option v-for="dict in auditUserOptions" :key="dict.dictValue" :label="dict.dictLabel"
            :value="dict.dictValue" />
        </el-select>
      </el-form-item>
      <el-form-item label="审核日期">
        <el-date-picker v-model="daterangeAuditTime" size="small" style="width: 240px" value-format="yyyy-MM-dd"
          type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="审核状态" prop="auditStatus">
        <el-select v-model="queryParams.auditStatus" placeholder="请选择审核状态" clearable filterable size="small">
          <el-option v-for="dict in auditStatusOptions" :key="dict.dictValue" :label="dict.dictLabel"
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
          v-hasPermi="['wmsApply:replenishApply:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-no-more-click type="success" plain icon="el-icon-edit" size="mini" :disabled="single"
          @click="handleUpdate" v-hasPermi="['wmsApply:replenishApply:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-no-more-click type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple"
          @click="handleDelete" v-hasPermi="['wmsApply:replenishApply:remove']">删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="replenishApplyList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="单据号" align="center" prop="serialNo" />
      <el-table-column label="状态" align="center" prop="status" :formatter="statusFormat" />
      <el-table-column label="申请人" align="center" prop="applyUser" :formatter="applyUserFormat" />
      <el-table-column label="申请日期" align="center" prop="applyTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.applyTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="申请状态" align="center" prop="applyStatus" :formatter="applyStatusFormat" />
      <el-table-column label="审核人" align="center" prop="auditUser" :formatter="auditUserFormat" />
      <el-table-column label="审核日期" align="center" prop="auditTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.auditTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="审核状态" align="center" prop="auditStatus" :formatter="auditStatusFormat" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-no-more-click
            v-if="scope.row.auditStatus == '1' && scope.row.status == '1' && scope.row.purchaseApplyFlag == '0'"
            size="mini" type="text" icon="el-icon-circle-check" @click="handleGenerate(scope.row)"
            v-hasPermi="['wmsApply:replenishApply:edit']">生成采购申请
          </el-button>
          <el-button v-no-more-click size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['wmsApply:replenishApply:edit']">修改</el-button>
          <el-button v-no-more-click v-if="scope.row.auditStatus != '1' && scope.row.applyStatus != '1'" size="mini"
            type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['wmsApply:replenishApply:remove']">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改补货申请对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px" :inline="true">
        <el-form-item label="单据号" prop="serialNo">
          <el-input v-model="form.serialNo" placeholder="-系统自动生成" disabled />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="form.status" placeholder="" clearable filterable disabled>
            <el-option v-for="dict in statusOptions" :key="dict.dictValue" :label="dict.dictLabel"
              :value="dict.dictValue"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="申请人" prop="applyUser">
          <el-select v-model="form.applyUser" placeholder="" disabled>
            <el-option v-for="dict in applyUserOptions" :key="dict.dictValue" :label="dict.dictLabel"
              :value="dict.dictValue"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="申请日期" prop="applyTime">
          <el-date-picker size="small" v-model="form.applyTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="" disabled>
          </el-date-picker>
        </el-form-item>
        <el-form-item label="申请状态" prop="applyStatus">
          <el-select v-model="form.applyStatus" placeholder="" disabled>
            <el-option v-for="dict in applyStatusOptions" :key="dict.dictValue" :label="dict.dictLabel"
              :value="dict.dictValue"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="审核人" prop="auditUser">
          <el-select v-model="form.auditUser" placeholder="" disabled>
            <el-option v-for="dict in auditUserOptions" :key="dict.dictValue" :label="dict.dictLabel"
              :value="dict.dictValue"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="审核日期" prop="auditTime">
          <el-date-picker size="small" v-model="form.auditTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="" disabled>
          </el-date-picker>
        </el-form-item>
        <el-form-item label="审核状态" prop="auditStatus">
          <el-select v-model="form.auditStatus" placeholder="" disabled>
            <el-option v-for="dict in auditStatusOptions" :key="dict.dictValue" :label="dict.dictLabel"
              :value="dict.dictValue"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="审核意见" prop="auditComment">
          <el-input v-model="form.auditComment" type="textarea" disabled />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"
            :disabled="form.auditStatus != '1' && form.applyStatus != '1' ? false : true" />
        </el-form-item>
        <el-divider content-position="center">补货申请明细信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button v-no-more-click v-if="form.auditStatus != '1' && form.applyStatus != '1'" type="primary"
              icon="el-icon-plus" size="mini" @click="handleAddWmsReplenishApplyDetail">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button v-no-more-click v-if="form.auditStatus != '1' && form.applyStatus != '1'" type="danger"
              icon="el-icon-delete" size="mini" @click="handleDeleteWmsReplenishApplyDetail">删除</el-button>
          </el-col>
        </el-row>
        <el-table :data="wmsReplenishApplyDetailList" :row-class-name="rowWmsReplenishApplyDetailIndex"
          @selection-change="handleWmsReplenishApplyDetailSelectionChange" ref="wmsReplenishApplyDetail">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center" prop="index" width="50" />
          <el-table-column label="物料" prop="materialName" width="300">
            <template slot-scope="scope">
              <el-form-item :prop="'wmsReplenishApplyDetailList.' + scope.$index + '.materialName'"
                :rules="rules.materialName">
                <el-input v-model="scope.row.materialName" placeholder="  " disabled>
                  <el-button v-no-more-click slot="append" @click="handleSelectMaterial(scope.$index)"
                    :disabled="form.auditStatus != '1' && form.applyStatus != '1' ? false : true">选择</el-button>
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
          <el-table-column label="补货数量" prop="quantity" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'wmsReplenishApplyDetailList.' + scope.$index + '.quantity'" :rules="rules.quantity">
                <el-input v-model.number="scope.row.quantity" placeholder="请输入补货数量"
                  :disabled="form.auditStatus != '1' && form.applyStatus != '1' ? false : true" />
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="备注" prop="detailRemark" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'wmsReplenishApplyDetailList.' + scope.$index + '.detailRemark'"
                :rules="rules.detailRemark">
                <el-input v-model="scope.row.detailRemark" placeholder="请输入备注"
                  :disabled="form.auditStatus != '1' && form.applyStatus != '1' ? false : true" />
              </el-form-item>
            </template>
          </el-table-column>
        </el-table>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button v-no-more-click v-if="form.auditStatus != '1' && form.applyStatus != '1'" type="primary"
          @click="saveForm">保
          存</el-button>
        <el-button v-no-more-click v-if="form.auditStatus != '1' && form.applyStatus != '1'" type="warning"
          @click="submitForm">提
          交
        </el-button>
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
  listReplenishApply,
  getReplenishApply,
  delReplenishApply,
  addReplenishApply,
  updateReplenishApply,
  exportReplenishApply,
  submitReplenishApply,
  genPurchaseApply
} from "@/api/wmsApply/replenishApply";
import MaterialSelect from "@/components/Material/MaterialSelect";

export default {
  name: "ReplenishApply",
  components: {
    MaterialSelect
  },
  data() {
    return {
      material: null,
      currentRowIndex: null,
      materialSelectOpen: false,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedWmsReplenishApplyDetail: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 补货申请表格数据
      replenishApplyList: [],
      // 补货申请明细表格数据
      wmsReplenishApplyDetailList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 状态：1未采购 2采购中 3已采购字典
      statusOptions: [],
      // 申请人字典
      applyUserOptions: [],
      // 申请日期时间范围
      daterangeApplyTime: [],
      // 申请状态字典
      applyStatusOptions: [],
      // 审核人字典
      auditUserOptions: [],
      // 审核日期时间范围
      daterangeAuditTime: [],
      // 审核状态字典
      auditStatusOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        serialNo: null,
        status: null,
        applyUser: null,
        applyTime: null,
        applyStatus: null,
        auditUser: null,
        auditTime: null,
        auditStatus: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        materialName: [{
          required: true,
          message: "物料不能为空",
          trigger: "blur"
        }],
        quantity: [{
          required: true,
          message: "补货数量不能为空",
          trigger: "blur"
        },
        {
          type: 'number',
          message: '补货数量必须为数字值'
        },
        ],
      }

    };
  },
  created() {
    this.getList();
    this.getDicts("wms_replenish_status").then(response => {
      this.statusOptions = response.data;
    });
    this.getQueryData("query_user").then(res => {
      this.applyUserOptions = res.data;
    });
    this.getDicts("apply_status").then(response => {
      this.applyStatusOptions = response.data;
    });
    this.getQueryData("query_user").then(res => {
      this.auditUserOptions = res.data;
    });
    this.getDicts("audit_status").then(response => {
      this.auditStatusOptions = response.data;
    });
  },
  watch: {
    'wmsReplenishApplyDetailList': {
      handler(newVal, oldVal) {
        this.form.wmsReplenishApplyDetailList = newVal;
      },
      immediate: true,
      deep: true,
    }
  },
  methods: {

    //生成采购申请函数
    handleGenerate(row) {
      this.$confirm('是否确认生成采购申请单?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return genPurchaseApply({
          id: row.id
        });
      }).then(() => {
        this.getList();
        this.msgSuccess("生成成功");
      })
    },

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
        this.material = list[0];
        this.wmsReplenishApplyDetailList[this.currentRowIndex].materialId = this.material.id;
        this.wmsReplenishApplyDetailList[this.currentRowIndex].materialName = this.material.name;
        this.wmsReplenishApplyDetailList[this.currentRowIndex].materialCode = this.material.code;
        this.wmsReplenishApplyDetailList[this.currentRowIndex].materialModel = this.material.model;
        this.wmsReplenishApplyDetailList[this.currentRowIndex].materialSpecification = this.material.specification;
        this.wmsReplenishApplyDetailList[this.currentRowIndex].materialUnit = this.material.unit;
      }
      this.materialSelectOpen = false;
    },
    /** 查询补货申请列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeApplyTime && '' != this.daterangeApplyTime) {
        this.queryParams.params["beginApplyTime"] = this.daterangeApplyTime[0];
        this.queryParams.params["endApplyTime"] = this.daterangeApplyTime[1];
      }
      if (null != this.daterangeAuditTime && '' != this.daterangeAuditTime) {
        this.queryParams.params["beginAuditTime"] = this.daterangeAuditTime[0];
        this.queryParams.params["endAuditTime"] = this.daterangeAuditTime[1];
      }
      listReplenishApply(this.queryParams).then(response => {
        this.replenishApplyList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 状态：1未采购 2采购中 3已采购字典翻译
    statusFormat(row, column) {
      return this.selectDictLabel(this.statusOptions, row.status);
    },
    // 申请人字典翻译
    applyUserFormat(row, column) {
      return this.selectDictLabel(this.applyUserOptions, row.applyUser);
    },
    // 申请状态字典翻译
    applyStatusFormat(row, column) {
      return this.selectDictLabel(this.applyStatusOptions, row.applyStatus);
    },
    // 审核人字典翻译
    auditUserFormat(row, column) {
      return this.selectDictLabel(this.auditUserOptions, row.auditUser);
    },
    // 审核状态字典翻译
    auditStatusFormat(row, column) {
      return this.selectDictLabel(this.auditStatusOptions, row.auditStatus);
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
        status: null,
        applyUser: null,
        applyTime: null,
        applyStatus: null,
        auditUser: null,
        auditTime: null,
        auditStatus: null,
        auditComment: null,
        remark: null,
        wmsReplenishApplyDetailList: [],
        materialName: null
      };
      this.wmsReplenishApplyDetailList = [];
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.daterangeApplyTime = [];
      this.daterangeAuditTime = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      let filters = selection.filter(item => item.auditStatus != '1');
      this.ids = filters.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加补货申请";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getReplenishApply(id).then(response => {
        this.form = response.data;
        this.wmsReplenishApplyDetailList = response.data.wmsReplenishApplyDetailList;
        this.open = true;
        this.title = "修改入库申请";
      });
    },
    /** 保存按钮 */
    saveForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateReplenishApply(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addReplenishApply(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },

    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          submitReplenishApply(this.form).then(response => {
            this.msgSuccess("提交成功");
            this.open = false;
            this.getList();
          });
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除补货申请编号为"' + ids + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delReplenishApply(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    /** 补货申请明细序号 */
    rowWmsReplenishApplyDetailIndex({
      row,
      rowIndex
    }) {
      row.index = rowIndex + 1;
    },
    /** 补货申请明细添加按钮操作 */
    handleAddWmsReplenishApplyDetail() {
      let obj = {};
      obj.materialId = "";
      obj.quantity = "";
      obj.warehouseId = "";
      obj.whRegionId = "";
      obj.whLocationId = "";
      obj.detailRemark = "";
      obj.materialName = "";
      obj.materialModel = "";
      obj.materialSpecification = "";
      obj.materialUnit = "";
      obj.whRegionOptions = [];
      obj.whLocationOptions = [];
      this.wmsReplenishApplyDetailList.push(obj);
    },
    /** 补货申请明细删除按钮操作 */
    handleDeleteWmsReplenishApplyDetail() {
      if (this.checkedWmsReplenishApplyDetail.length == 0) {
        this.$alert("请先选择要删除的补货申请明细数据", "提示", {
          confirmButtonText: "确定",
        });
      } else {
        this.wmsReplenishApplyDetailList.splice(this.checkedWmsReplenishApplyDetail[0].index - 1, 1);
      }
    },
    /** 单选框选中数据 */
    handleWmsReplenishApplyDetailSelectionChange(selection) {
      if (selection.length > 1) {
        this.$refs.wmsReplenishApplyDetail.clearSelection();
        this.$refs.wmsReplenishApplyDetail.toggleRowSelection(selection.pop());
      } else {
        this.checkedWmsReplenishApplyDetail = selection;
      }
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有补货申请数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return exportReplenishApply(queryParams);
      }).then(response => {
        this.download(response.msg);
      })
    }
  }
};

</script>
