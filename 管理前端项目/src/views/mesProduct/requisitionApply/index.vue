<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="领料单号" prop="serialNo">
        <el-input v-model="queryParams.serialNo" placeholder="请输入领料单号" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="领取日期">
        <el-date-picker v-model="daterangeRequisitionDate" size="small" style="width: 240px" value-format="yyyy-MM-dd"
          type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="申请人" prop="applyUser">
        <el-select v-model="queryParams.applyUser" placeholder="请选择申请人" clearable filterable size="small">
          <el-option v-for="dict in applyUserOptions" :key="dict.dictValue" :label="dict.dictLabel"
            :value="dict.dictValue" />
        </el-select>
      </el-form-item>
      <el-form-item label="申请时间">
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
      <el-form-item label="审核时间">
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
          v-hasPermi="['mesProduct:requisitionApply:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-no-more-click type="success" plain icon="el-icon-edit" size="mini" :disabled="single"
          @click="handleUpdate" v-hasPermi="['mesProduct:requisitionApply:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-no-more-click type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple"
          @click="handleDelete" v-hasPermi="['mesProduct:requisitionApply:remove']">删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="requisitionApplyList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="领料单号" align="center" prop="serialNo" />
      <el-table-column label="生产计划" align="center" prop="planNo" />
      <el-table-column label="计划排产" align="center" prop="scheduleNo" />
      <el-table-column label="产品" align="center" prop="materialName" />
      <el-table-column label="型号" prop="materialModel"> </el-table-column>
      <el-table-column label="规格" prop="materialSpecification">
      </el-table-column>
      <el-table-column label="单位" prop="materialUnit"> </el-table-column>
      <el-table-column label="领取日期" align="center" prop="requisitionDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.requisitionDate, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="申请人" align="center" prop="applyUser" :formatter="applyUserFormat" />
      <el-table-column label="申请时间" align="center" prop="applyTime" width="180">
        <template slot-scope="scope">
          <span>{{
            parseTime(scope.row.applyTime, "{y}-{m}-{d} {h}:{i}:{s}")
          }}</span>
        </template>
      </el-table-column>
      <el-table-column label="申请状态" align="center" prop="applyStatus" :formatter="applyStatusFormat" />
      <el-table-column label="审核人" align="center" prop="auditUser" :formatter="auditUserFormat" />
      <el-table-column label="审核时间" align="center" prop="auditTime" width="180">
        <template slot-scope="scope">
          <span>{{
            parseTime(scope.row.auditTime, "{y}-{m}-{d} {h}:{i}:{s}")
          }}</span>
        </template>
      </el-table-column>
      <el-table-column label="审核状态" align="center" prop="auditStatus" :formatter="auditStatusFormat" />
      <el-table-column label="状态" align="center" prop="status" :formatter="statusFormat" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-no-more-click size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['mesProduct:requisitionApply:edit']">修改</el-button>
          <el-button v-no-more-click v-if="scope.row.status == '1'" size="mini" type="text" icon="el-icon-check"
            @click="handleCheck(scope.row.id, '2')" v-hasPermi="['mesProduct:requisitionApply:edit']">检验合格</el-button>
          <el-button v-no-more-click v-if="scope.row.status == '1'" size="mini" type="text" icon="el-icon-close"
            @click="handleCheck(scope.row.id, '3')" v-hasPermi="['mesProduct:requisitionApply:edit']">检验不合格</el-button>
          <el-button v-no-more-click v-if="scope.row.auditStatus != '1' && scope.row.applyStatus != '1'" size="mini"
            type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['mesProduct:requisitionApply:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改领料申请对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px" :inline="true">
        <el-form-item label="领料单号" prop="serialNo">
          <el-input v-model="form.serialNo" placeholder="" disabled />
        </el-form-item>
        <el-form-item label="计划排产" prop="planNo">
          <el-input v-model="form.planNo" placeholder="请选择计划排产" disabled>
            <el-button v-no-more-click slot="append" @click="handleSelectSchedule">选择</el-button>
          </el-input>
        </el-form-item>
        <el-form-item label="产品" prop="materialName">
          <el-input v-model="form.materialName" placeholder="" disabled />
        </el-form-item>
        <el-form-item label="规格" prop="materialSpecification">
          <el-input v-model="form.materialSpecification" placeholder="" disabled />
        </el-form-item>
        <el-form-item label="型号" prop="materialModel">
          <el-input v-model="form.materialModel" placeholder="" disabled />
        </el-form-item>
        <el-form-item label="单位" prop="materialUnit">
          <el-input v-model="form.materialUnit" placeholder="" disabled />
        </el-form-item>
        <el-form-item label="待产数量" prop="requireQuantity">
          <el-input v-model.number="form.requireQuantity" placeholder="" disabled />
        </el-form-item>
        <el-form-item label="生产日期" prop="productDate" disabled>
          <el-date-picker clearable size="small" v-model="form.productDate" type="date" value-format="yyyy-MM-dd"
            placeholder="" disabled>
          </el-date-picker>
        </el-form-item>
        <el-form-item label="领取日期" prop="requisitionDate">
          <el-date-picker clearable size="small" v-model="form.requisitionDate" type="date" value-format="yyyy-MM-dd"
            placeholder="选择领取日期" :disabled="form.auditStatus != '1' && form.applyStatus != '1' ? false : true
              ">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="form.status" placeholder="" clearable filterable disabled>
            <el-option v-for="dict in statusOptions" :key="dict.dictValue" :label="dict.dictLabel"
              :value="dict.dictValue"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" :disabled="form.auditStatus != '1' && form.applyStatus != '1' ? false : true
            " />
        </el-form-item>
        <el-form-item label="申请人" prop="applyUser">
          <el-select v-model="form.applyUser" placeholder="" clearable filterable disabled>
            <el-option v-for="dict in applyUserOptions" :key="dict.dictValue" :label="dict.dictLabel"
              :value="dict.dictValue"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="申请时间" prop="applyTime">
          <el-date-picker clearable size="small" v-model="form.applyTime" type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss" placeholder="" disabled>
          </el-date-picker>
        </el-form-item>
        <el-form-item label="申请状态" prop="applyStatus">
          <el-select v-model="form.applyStatus" placeholder="" clearable filterable disabled>
            <el-option v-for="dict in applyStatusOptions" :key="dict.dictValue" :label="dict.dictLabel"
              :value="dict.dictValue"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="审核人" prop="auditUser">
          <el-select v-model="form.auditUser" placeholder="" clearable filterable disabled>
            <el-option v-for="dict in auditUserOptions" :key="dict.dictValue" :label="dict.dictLabel"
              :value="dict.dictValue"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="审核时间" prop="auditTime">
          <el-date-picker clearable size="small" v-model="form.auditTime" type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss" placeholder="" disabled>
          </el-date-picker>
        </el-form-item>
        <el-form-item label="审核状态" prop="auditStatus">
          <el-select v-model="form.auditStatus" placeholder="" clearable filterable disabled>
            <el-option v-for="dict in auditStatusOptions" :key="dict.dictValue" :label="dict.dictLabel"
              :value="dict.dictValue"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="审核意见" prop="auditComment">
          <el-input v-model="form.auditComment" type="textarea" placeholder="" disabled />
        </el-form-item>
        <el-divider content-position="center">领料单物料明细信息</el-divider>
        <el-table :data="mesMaterialRequisitionDetailList" :row-class-name="rowMesMaterialRequisitionDetailIndex"
          ref="mesMaterialRequisitionDetail">
          <el-table-column label="序号" align="center" prop="index" width="50" />
          <el-table-column label="物料" prop="materialName" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'mesMaterialRequisitionDetailList.' +
                scope.$index +
                '.materialName'
                " :rules="rules.materialId">
                <el-input v-model="scope.row.materialName" placeholder="" disabled />
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="型号" prop="materialModel"> </el-table-column>
          <el-table-column label="规格" prop="materialSpecification">
          </el-table-column>
          <el-table-column label="单位" prop="materialUnit"> </el-table-column>
          <el-table-column label="需求数量" prop="requireQuantity" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'mesMaterialRequisitionDetailList.' +
                scope.$index +
                '.requireQuantity'
                " :rules="rules.requireQuantity">
                <el-input v-model="scope.row.requireQuantity" placeholder="" disabled />
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="已领数量" prop="receiveQuantity" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'mesMaterialRequisitionDetailList.' +
                scope.$index +
                '.receiveQuantity'
                " :rules="rules.receiveQuantity">
                <el-input v-model="scope.row.receiveQuantity" placeholder="" disabled />
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="备注" prop="detailRemark" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'mesMaterialRequisitionDetailList.' +
                scope.$index +
                '.detailRemark'
                " :rules="rules.detailRemark">
                <el-input v-model="scope.row.detailRemark" type="textarea" placeholder="请输入备注" :disabled="form.auditStatus != '1' && form.applyStatus != '1'
                  ? false
                  : true
                  " />
              </el-form-item>
            </template>
          </el-table-column>
        </el-table>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button v-no-more-click v-if="form.auditStatus != '1' && form.applyStatus != '1'" type="primary"
          @click="saveForm">保 存</el-button>
        <el-button v-no-more-click v-if="form.auditStatus != '1' && form.applyStatus != '1'" type="warning"
          @click="submitForm">提 交</el-button>
        <el-button v-no-more-click @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <product-schedule-select status="1" receiveStatus="0" :open="scheduleOpen" @onSelected="handleScheduleSelected"
      @onCancel="handleScheduleCancel"></product-schedule-select>
  </div>
</template>

<script>
import {
  listRequisitionApply,
  getRequisitionApply,
  delRequisitionApply,
  addRequisitionApply,
  updateRequisitionApply,
  exportRequisitionApply,
  submitRequisitionApply,
  setCheckRequisition,
} from "@/api/mesProduct/requisitionApply";
import { getProductSchedule } from "@/api/mesPlan/productSchedule";
import ProductScheduleSelect from "@/components/product/ProductScheduleSelect.vue";
export default {
  name: "RequisitionApply",
  components: {
    ProductScheduleSelect,
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedMesMaterialRequisitionDetail: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 领料申请表格数据
      requisitionApplyList: [],
      // 领料单物料明细表格数据
      mesMaterialRequisitionDetailList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      scheduleOpen: false,
      // 领取日期时间范围
      daterangeRequisitionDate: [],
      // 申请人字典
      applyUserOptions: [],
      // 申请时间时间范围
      daterangeApplyTime: [],
      // 申请状态字典
      applyStatusOptions: [],
      // 审核人字典
      auditUserOptions: [],
      // 审核时间时间范围
      daterangeAuditTime: [],
      // 审核状态字典
      auditStatusOptions: [],
      // 状态字典
      statusOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        serialNo: null,
        requisitionDate: null,
        applyUser: null,
        applyTime: null,
        applyStatus: null,
        auditUser: null,
        auditTime: null,
        auditStatus: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        planNo: [
          { required: true, message: "生产计划不能为空", trigger: "blur" },
        ],
        productId: [
          { required: true, message: "产品不能为空", trigger: "blur" },
        ],
        requisitionDate: [
          { required: true, message: "领料日期不能为空", trigger: "blur" },
        ],
        remark: [{ max: 100, message: "备注最多100个字符", trigger: "blur" }],
      },
    };
  },
  created() {
    this.getList();
    this.getQueryData("query_user").then((res) => {
      this.applyUserOptions = res.data;
    });
    this.getDicts("apply_status").then((response) => {
      this.applyStatusOptions = response.data;
    });
    this.getQueryData("query_user").then((res) => {
      this.auditUserOptions = res.data;
    });
    this.getDicts("audit_status").then((res) => {
      this.auditStatusOptions = res.data;
    });
    this.getDicts("receive_material_status").then((response) => {
      this.statusOptions = response.data;
    });
  },
  watch: {
    mesMaterialRequisitionDetailList: {
      handler(newVal, oldVal) {
        this.form.mesMaterialRequisitionDetailList = newVal;
      },
      immediate: true,
      deep: true,
    },
  },
  methods: {
    handleCheck(id, status) {
      let params = {
        id: id,
        status: status,
      };
      setCheckRequisition(params).then((res) => {
        this.msgSuccess("提交成功");
        this.getList();
      });
    },
    handleSelectSchedule() {
      this.scheduleOpen = true;
    },
    handleScheduleSelected(plan) {
      this.form.planId = plan.planId;
      this.form.planNo = plan.planNo;
      this.form.scheduleId = plan.id;
      this.form.productId = plan.materialId;
      this.form.materialName = plan.materialName;
      this.form.materialModel = plan.materialModel;
      this.form.materialSpecification = plan.materialSpecification;
      this.form.materialUnit = plan.materialUnit;
      this.form.projectId = plan.projectId;
      this.form.requireQuantity = plan.requireQuantity;
      this.form.productDate = plan.productDate;
      this.mesMaterialRequisitionDetailList = [];
      getProductSchedule(this.form.scheduleId).then((res) => {
        let list = res.data.mesProductScheduleMaterialList;
        list.forEach((material) => {
          let obj = {};
          obj.materialId = material.materialId;
          obj.materialCode = material.materialCode;
          obj.materialName = material.materialName;
          obj.materialModel = material.materialModel;
          obj.materialSpecification = material.materialSpecification;
          obj.materialUnit = material.materialUnit;
          obj.requireQuantity = material.requireQuantity;
          this.mesMaterialRequisitionDetailList.push(obj);
        });
      });
      this.scheduleOpen = false;
    },
    handleScheduleCancel() {
      this.scheduleOpen = false;
    },
    /** 查询领料申请列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (
        null != this.daterangeRequisitionDate &&
        "" != this.daterangeRequisitionDate
      ) {
        this.queryParams.params["beginRequisitionDate"] =
          this.daterangeRequisitionDate[0];
        this.queryParams.params["endRequisitionDate"] =
          this.daterangeRequisitionDate[1];
      }
      if (null != this.daterangeApplyTime && "" != this.daterangeApplyTime) {
        this.queryParams.params["beginApplyTime"] = this.daterangeApplyTime[0];
        this.queryParams.params["endApplyTime"] = this.daterangeApplyTime[1];
      }
      if (null != this.daterangeAuditTime && "" != this.daterangeAuditTime) {
        this.queryParams.params["beginAuditTime"] = this.daterangeAuditTime[0];
        this.queryParams.params["endAuditTime"] = this.daterangeAuditTime[1];
      }
      listRequisitionApply(this.queryParams).then((response) => {
        this.requisitionApplyList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
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
    // 状态字典翻译
    statusFormat(row, column) {
      return this.selectDictLabel(this.statusOptions, row.status);
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
        planId: null,
        planNo: null,
        scheduleId: null,
        scheduleNo: null,
        productId: null,
        materialName: null,
        requisitionDate: null,
        applyUser: null,
        applyTime: null,
        applyStatus: null,
        auditUser: null,
        auditTime: null,
        auditStatus: null,
        status: null,
        remark: null,
        auditComment: null,
        mesMaterialRequisitionDetailList: [],
      };
      this.mesMaterialRequisitionDetailList = [];
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.daterangeRequisitionDate = [];
      this.daterangeApplyTime = [];
      this.daterangeAuditTime = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      let filters = selection.filter((item) => item.auditStatus != "1");
      this.ids = filters.map((item) => item.id);
      this.single = filters.length !== 1;
      this.multiple = !filters.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加领料申请";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getRequisitionApply(id).then((response) => {
        this.form = response.data;
        this.mesMaterialRequisitionDetailList =
          response.data.mesMaterialRequisitionDetailList;
        this.open = true;
        this.title = "修改领料申请";
      });
    },
    /** 保存按钮 */
    saveForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateRequisitionApply(this.form).then((response) => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addRequisitionApply(this.form).then((response) => {
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
      this.$refs["form"].validate((valid) => {
        if (valid) {
          submitRequisitionApply(this.form).then((response) => {
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
      this.$confirm(
        '是否确认删除领料申请编号为"' + ids + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delRequisitionApply(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        });
    },
    /** 领料单物料明细序号 */
    rowMesMaterialRequisitionDetailIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm("是否确认导出所有领料申请数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return exportRequisitionApply(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        });
    },
  },
};
</script>
