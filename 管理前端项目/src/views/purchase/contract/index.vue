<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="合同编号" prop="contractNo">
        <el-input v-model="queryParams.contractNo" placeholder="请输入合同编号" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="申请编号" prop="applyNo">
        <el-input v-model="queryParams.applyNo" placeholder="请输入申请编号" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="供应商" prop="supplierId">
        <el-select v-model="queryParams.supplierId" placeholder="请选择供应商" clearable filterable size="small">
          <el-option v-for="dict in supplierIdOptions" :key="dict.dictValue" :label="dict.dictLabel"
            :value="dict.dictValue" />
        </el-select>
      </el-form-item>
      <el-form-item label="签约日期">
        <el-date-picker v-model="daterangeSignDate" size="small" style="width: 240px" value-format="yyyy-MM-dd"
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
          v-hasPermi="['purchase:contract:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-no-more-click type="success" plain icon="el-icon-edit" size="mini" :disabled="single"
          @click="handleUpdate" v-hasPermi="['purchase:contract:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-no-more-click type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple"
          @click="handleDelete" v-hasPermi="['purchase:contract:remove']">删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="contractList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="合同编号" align="center" prop="contractNo" />
      <el-table-column label="申请编号" align="center" prop="applyNo" />
      <el-table-column label="供应商" align="center" prop="supplierName" />
      <el-table-column label="联系人" align="center" prop="supplierContact" />
      <el-table-column label="联系方式" align="center" prop="supplierContactTel" />
      <el-table-column label="合同金额" align="center" prop="amount" />
      <el-table-column label="送货方式" align="center" prop="deliveryType" :formatter="deliveryTypeFormat" />
      <el-table-column label="签约日期" align="center" prop="signDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.signDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="申请人" align="center" prop="applyUser" :formatter="applyUserFormat" />
      <el-table-column label="申请时间" align="center" prop="applyTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.applyTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="申请状态" align="center" prop="applyStatus" :formatter="applyStatusFormat" />
      <el-table-column label="审核人" align="center" prop="auditUser" :formatter="auditUserFormat" />
      <el-table-column label="审核时间" align="center" prop="auditTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.auditTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="审核状态" align="center" prop="auditStatus" :formatter="auditStatusFormat" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-no-more-click size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['purchase:contract:edit']">修改</el-button>
          <el-button v-no-more-click v-if="scope.row.auditStatus != '1' && scope.row.applyStatus != '1'" size="mini"
            type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['purchase:contract:remove']">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改采购合同对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px" :inline="true">
        <el-form-item label="合同编号" prop="contractNo">
          <el-input v-model="form.contractNo" placeholder="-系统自动生成" disabled />
        </el-form-item>
        <el-form-item label="申请单号" prop="applyNo">
          <el-input v-model="form.applyNo" disabled>
            <el-button v-no-more-click slot="append" @click="handleSelectApply"
              :disabled="form.auditStatus != '1' && form.applyStatus != '1' ? false : true">选择</el-button>
          </el-input>
        </el-form-item>
        <el-form-item label="供应商" prop="supplierId">
          <el-select v-model="form.supplierId" placeholder="请选择供应商" clearable filterable
            :disabled="form.auditStatus != '1' && form.applyStatus != '1' ? false : true">
            <el-option v-for="dict in supplierIdOptions" :key="dict.dictValue" :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="送货方式" prop="deliveryType">
          <el-select v-model="form.deliveryType" placeholder="请选择送货方式" clearable filterable
            :disabled="form.auditStatus != '1' && form.applyStatus != '1' ? false : true">
            <el-option v-for="dict in deliveryTypeOptions" :key="dict.dictValue" :label="dict.dictLabel"
              :value="dict.dictValue"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="签约日期" prop="signDate">
          <el-date-picker clearable size="small" v-model="form.signDate" type="date" value-format="yyyy-MM-dd"
            placeholder="选择签约日期" :disabled="form.auditStatus != '1' && form.applyStatus != '1' ? false : true">
          </el-date-picker>
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
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"
            :disabled="form.auditStatus != '1' && form.applyStatus != '1' ? false : true" />
        </el-form-item>
        <el-divider content-position="center">合同物料明细信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button v-no-more-click v-if="form.auditStatus != '1' && form.applyStatus != '1'" type="primary"
              icon="el-icon-plus" size="mini" @click="handleAddScmPurchaseContractDetail">添加
            </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button v-no-more-click v-if="form.auditStatus != '1' && form.applyStatus != '1'" type="danger"
              icon="el-icon-delete" size="mini" @click="handleDeleteScmPurchaseContractDetail">删除
            </el-button>
          </el-col>
        </el-row>
        <el-table :data="scmPurchaseContractDetailList" :row-class-name="rowScmPurchaseContractDetailIndex"
          @selection-change="handleScmPurchaseContractDetailSelectionChange" ref="scmPurchaseContractDetail">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center" prop="index" width="50" />
          <el-table-column label="物料" prop="materialName" width="300">
            <template slot-scope="scope">
              <el-form-item :prop="'scmPurchaseContractDetailList.' + scope.$index + '.materialName'"
                :rules="rules.materialName">
                <el-input v-model="scope.row.materialName" placeholder="请选择物料" disabled>
                  <el-button v-no-more-click slot="append" @click="handleSelectMaterial(scope.$index)"
                    :disabled="form.auditStatus != '1' && form.applyStatus != '1' ? false : true">选择</el-button>
                </el-input>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="型号" prop="materialModel"></el-table-column>
          <el-table-column label="规格" prop="materialSpecification"></el-table-column>
          <el-table-column label="单位" prop="materialUnit"></el-table-column>
          <el-table-column label="单价" prop="price" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'scmPurchaseContractDetailList.' + scope.$index + '.price'" :rules="rules.price">
                <el-input v-model.number="scope.row.price" placeholder="请输入单价"
                  :disabled="form.auditStatus != '1' && form.applyStatus != '1' ? false : true" />
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="采购数量" prop="quantity" width="140">
            <template slot-scope="scope">
              <el-form-item :prop="'scmPurchaseContractDetailList.' + scope.$index + '.quantity'" :rules="rules.quantity">
                <el-input v-model.number="scope.row.quantity" placeholder="请输入采购数量"
                  :disabled="form.auditStatus != '1' && form.applyStatus != '1' ? false : true" />
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="金额" prop="amount" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'scmPurchaseContractDetailList.' + scope.$index + '.amount'" :rules="rules.amount">
                <span>{{ scope.row.quantity * scope.row.price }}</span>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="需求日期" prop="requireDate" width="240">
            <template slot-scope="scope">
              <el-form-item :prop="'scmPurchaseContractDetailList.' + scope.$index + '.requireDate'"
                :rules="rules.requireDate">
                <el-date-picker clearable v-model="scope.row.requireDate" type="date" value-format="yyyy-MM-dd"
                  placeholder="请选择需求日期" :disabled="form.auditStatus != '1' && form.applyStatus != '1' ? false : true" />
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="备注" prop="detailRemark" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'scmPurchaseContractDetailList.' + scope.$index + '.detailRemark'"
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
        <el-button v-no-more-click v-if="form.auditStatus != '1' && form.applyStatus != '1'" @click="cancel">取 消</el-button>
      </div>
      <!--选择计划-->
      <ApplySelect :open="applySelectOpen" @onCancel="handleApplyCancel" @onSelected="handleApplySelected">
      </ApplySelect>
    </el-dialog>
    <!--选择物料档案-->
    <MaterialSelect :open="materialSelectOpen" @onCancel="handleMaterialCancel" @onSelected="handleMaterialSelected">
    </MaterialSelect>
  </div>
</template>

<script>
import {
  listContract,
  getContract,
  delContract,
  addContract,
  updateContract,
  exportContract,
  submitContract
} from "@/api/purchase/contract";

import {
  getApply,
} from "@/api/purchase/apply";

import MaterialSelect from "@/components/Material/MaterialSelect";
import ApplySelect from "@/components/Purchase/ApplySelect";
export default {
  name: "Contract",
  components: {
    ApplySelect,
    MaterialSelect
  },
  data() {
    return {
      applySelectOpen: false,
      material: null,
      currentRowIndex: null,
      materialSelectOpen: false,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedScmPurchaseContractDetail: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 采购合同表格数据
      contractList: [],
      // 合同物料明细表格数据
      scmPurchaseContractDetailList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 供应商id字典
      supplierIdOptions: [],
      // 送货方式字典
      deliveryTypeOptions: [],
      // 签约日期时间范围
      daterangeSignDate: [],
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
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        contractNo: null,
        applyId: null,
        applyNo: null,
        supplierId: null,
        deliveryType: null,
        signDate: null,
        applyUser: null,
        applyTime: null,
        applyStatus: null,
        auditUser: null,
        auditTime: null,
        auditStatus: null,
        orderByColumn: 'apply_time',
        isAsc: 'desc',
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        applyNo: [{
          required: true,
          message: "请选择申请单号",
          trigger: "blur"
        }],
        supplierId: [{
          required: true,
          message: "请选择供应商",
          trigger: "blur"
        }],
        signDate: [{
          required: true,
          message: "签约日期不能为空",
          trigger: "blur"
        }],
        deliveryType: [{
          required: true,
          message: "请选择送货方式",
          trigger: "blur"
        }],
        materialName: [{
          required: true,
          message: "物料不能为空",
          trigger: "blur"
        }],
        price: [{
          required: true,
          message: "单价不能为空",
          trigger: "blur"
        }],
        quantity: [{
          required: true,
          message: "采购数量不能为空",
          trigger: "blur"
        },
        {
          type: 'number',
          message: '采购数量必须为数字'
        },

        ],
        requireDate: [{
          required: true,
          message: "需求日期不能为空",
          trigger: "blur"
        }],
      }
    };
  },
  created() {
    this.getQueryData("query_supplier").then(res => {
      this.supplierIdOptions = res.data;
    });
    this.getDicts("scm_delivery_type").then(response => {
      this.deliveryTypeOptions = response.data;
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
    this.getList();
  },
  watch: {
    'scmPurchaseContractDetailList': {
      handler(newVal, oldVal) {
        this.form.scmPurchaseContractDetailList = newVal;
      },
      immediate: true,
      deep: true,
    }
  },
  methods: {
    //选择申请回调函数
    handleApplySelected(list) {
      if (list && list.length > 0) {
        let apply = list[0];
        this.form.applyId = apply.id;
        this.form.applyNo = apply.applyNo;
        // 根据申请id获取申请详情
        getApply(apply.id).then(res => {
          let applyDetailList = res.data.scmPurchaseApplyDetailList
          let list = [];
          // 根据申请DetailList封装合同DetailList
          applyDetailList.forEach(item => {
            list.push({
              materialId: item.materialId,
              materialName: item.materialName,
              materialModel: item.materialModel,
              materialSpecification: item.materialSpecification,
              materialUnit: item.materialUnit,
              materialCode: item.materialCode,
              price: item.price,
              quantity: item.quantity,
              amount: item.amount,
              requireDate: item.requireDate,
              detailRemark: ''
            })
          });
          this.scmPurchaseContractDetailList = list;
        })
      }
      this.applySelectOpen = false;
    },
    //选择申请
    handleSelectApply() {
      this.applySelectOpen = true;
    },
    //取消选择申请
    handleApplyCancel() {
      this.applySelectOpen = false;
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
        this.scmPurchaseContractDetailList[this.currentRowIndex].materialId = this.material.id;
        this.scmPurchaseContractDetailList[this.currentRowIndex].materialName = this.material.name;
        this.scmPurchaseContractDetailList[this.currentRowIndex].materialCode = this.material.code;
        this.scmPurchaseContractDetailList[this.currentRowIndex].materialModel = this.material.model;
        this.scmPurchaseContractDetailList[this.currentRowIndex].materialSpecification = this.material.specification;
        this.scmPurchaseContractDetailList[this.currentRowIndex].materialUnit = this.material.unit;
        this.scmPurchaseContractDetailList[this.currentRowIndex].price = this.material.price;
      }
      this.materialSelectOpen = false;
    },
    /** 查询采购合同列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeSignDate && '' != this.daterangeSignDate) {
        this.queryParams.params["beginSignDate"] = this.daterangeSignDate[0];
        this.queryParams.params["endSignDate"] = this.daterangeSignDate[1];
      }
      if (null != this.daterangeApplyTime && '' != this.daterangeApplyTime) {
        this.queryParams.params["beginApplyTime"] = this.daterangeApplyTime[0];
        this.queryParams.params["endApplyTime"] = this.daterangeApplyTime[1];
      }
      if (null != this.daterangeAuditTime && '' != this.daterangeAuditTime) {
        this.queryParams.params["beginAuditTime"] = this.daterangeAuditTime[0];
        this.queryParams.params["endAuditTime"] = this.daterangeAuditTime[1];
      }
      listContract(this.queryParams).then(response => {
        this.contractList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 供应商id字典翻译
    supplierIdFormat(row, column) {
      return this.selectDictLabel(this.supplierIdOptions, row.supplierId);
    },
    // 送货方式字典翻译
    deliveryTypeFormat(row, column) {
      return this.selectDictLabel(this.deliveryTypeOptions, row.deliveryType);
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
        contractNo: null,
        applyId: null,
        applyNo: null,
        supplierId: null,
        amount: null,
        deliveryType: null,
        signDate: null,
        applyUser: null,
        applyTime: null,
        applyStatus: null,
        auditUser: null,
        auditTime: null,
        auditStatus: null,
        auditComment: null,
        createTime: null,
        remark: null,
        scmPurchaseContractDetailList: [],
      };
      this.scmPurchaseContractDetailList = [];
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.daterangeSignDate = [];
      this.daterangeApplyTime = [];
      this.daterangeAuditTime = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      // this.ids = selection.map(item => item.id)
      let filters = selection.filter(item => item.auditStatus != '1');
      this.ids = filters.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加采购合同";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getContract(id).then(response => {
        this.form = response.data;
        this.scmPurchaseContractDetailList = response.data.scmPurchaseContractDetailList;
        this.open = true;
        this.title = "修改采购合同";
      });
    },
    /** 保存按钮 */
    saveForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (!this.scmPurchaseContractDetailList || this.scmPurchaseContractDetailList.length == 0) {
            this.msgError('请添加合同物料');
            return false;
          }
          if (this.form.id != null) {
            updateContract(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addContract(this.form).then(response => {
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
          submitContract(this.form).then(response => {
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
      this.$confirm('是否确认删除采购合同编号为"' + ids + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delContract(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    /** 合同物料明细序号 */
    rowScmPurchaseContractDetailIndex({
      row,
      rowIndex
    }) {
      row.index = rowIndex + 1;
    },
    /** 合同物料明细添加按钮操作 */
    handleAddScmPurchaseContractDetail() {
      let obj = {};
      obj.materialId = "";
      obj.price = "";
      obj.quantity = "";
      obj.amount = "";
      obj.requireDate = "";
      obj.remark = "";
      obj.materialName = "";
      obj.materialModel = "";
      obj.materialSpecification = "";
      obj.materialUnit = "";
      this.scmPurchaseContractDetailList.push(obj);
    },
    /** 合同物料明细删除按钮操作 */
    handleDeleteScmPurchaseContractDetail() {
      if (this.checkedScmPurchaseContractDetail.length == 0) {
        this.$alert("请先选择要删除的合同物料明细数据", "提示", {
          confirmButtonText: "确定",
        });
      } else {
        this.scmPurchaseContractDetailList.splice(this.checkedScmPurchaseContractDetail[0].index - 1, 1);
      }
    },
    /** 单选框选中数据 */
    handleScmPurchaseContractDetailSelectionChange(selection) {
      if (selection.length > 1) {
        this.$refs.scmPurchaseContractDetail.clearSelection();
        this.$refs.scmPurchaseContractDetail.toggleRowSelection(selection.pop());
      } else {
        this.checkedScmPurchaseContractDetail = selection;
      }
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有采购合同数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return exportContract(queryParams);
      }).then(response => {
        this.download(response.msg);
      })
    }
  }
};

</script>
