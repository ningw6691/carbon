<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="90px">
      <el-form-item label="单据号" prop="serialNo">
        <el-input v-model="queryParams.serialNo" placeholder="请输入单据号" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="业务类型" prop="bizType">
        <el-select v-model="queryParams.bizType" placeholder="请选择业务类型" clearable filterable size="small">
          <el-option v-for="dict in bizTypeOptions" :key="dict.dictValue" :label="dict.dictLabel"
            :value="dict.dictValue" />
        </el-select>
      </el-form-item>
      <el-form-item label="客户" prop="customerId">
        <el-select v-model="queryParams.customerId" placeholder="请选择客户" clearable filterable size="small">
          <el-option v-for="dict in customerIdOptions" :key="dict.dictValue" :label="dict.dictLabel"
            :value="dict.dictValue" />
        </el-select>
      </el-form-item>
      <el-form-item label="合同类型" prop="contractType">
        <el-select v-model="queryParams.contractType" placeholder="请选择合同类型" clearable filterable size="small">
          <el-option v-for="dict in contractTypeOptions" :key="dict.dictValue" :label="dict.dictLabel"
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
      <el-form-item label="单据状态" prop="billStatus">
        <el-select v-model="queryParams.billStatus" placeholder="请选择单据状态" clearable filterable size="small">
          <el-option v-for="dict in billStatusOptions" :key="dict.dictValue" :label="dict.dictLabel"
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
          v-hasPermi="['wmsApply:outWarehouseApply:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-no-more-click type="success" plain icon="el-icon-edit" size="mini" :disabled="single"
          @click="handleUpdate" v-hasPermi="['wmsApply:outWarehouseApply:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-no-more-click type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple"
          @click="handleDelete" v-hasPermi="['wmsApply:outWarehouseApply:remove']">删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="outWarehouseApplyList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="单据号" align="center" prop="serialNo" />
      <el-table-column label="关联单据号" align="center" prop="bizBillNo" width="150" />
      <el-table-column label="业务类型" align="center" prop="bizType" :formatter="bizTypeFormat" />
      <el-table-column label="客户" align="center" prop="customerId" :formatter="customerIdFormat" />
      <el-table-column label="合同编号" align="center" prop="contractNo" />
      <el-table-column label="合同类型" align="center" prop="contractType" :formatter="contractTypeFormat" />
      <el-table-column label="申请类型" align="center" prop="applyType" :formatter="applyTypeFormat" />
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
      <el-table-column label="单据状态" align="center" prop="billStatus" :formatter="billStatusFormat" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-no-more-click v-if="scope.row.billStatus == '2'" size="mini" type="text"
            icon="el-icon-caret-right" @click="handleDelivery(scope.row)"
            v-hasPermi="['wmsApply:outWarehouseApply:edit']">发货</el-button>
          <el-button v-no-more-click v-if="scope.row.auditStatus == '1' && scope.row.billStatus == '0'" size="mini"
            type="text" icon="el-icon-caret-right" @click="handleOutWarehouse(scope.row)"
            v-hasPermi="['wmsApply:outWarehouseApply:edit']">分拣</el-button>
          <el-button v-no-more-click v-if="scope.row.billStatus == '1'" size="mini" type="text"
            icon="el-icon-caret-right" @click="handleUpdate(scope.row)"
            v-hasPermi="['wmsApply:outWarehouseApply:edit']">盘点</el-button>
          <el-button v-no-more-click size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['wmsApply:outWarehouseApply:edit']">修改</el-button>
          <el-button v-no-more-click v-if="scope.row.auditStatus != '1' && scope.row.applyStatus != '1'" size="mini"
            type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['wmsApply:outWarehouseApply:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改出库申请对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="95px" :inline="true">
        <el-form-item label="单据号" prop="serialNo">
          <el-input v-model="form.serialNo" placeholder="" disabled />
        </el-form-item>
        <el-form-item label="关联单据号" prop="bizBillNo">
          <el-input v-model="form.bizBillNo" placeholder="请输入关联单据号" disabled>
            <el-button v-no-more-click slot="append" @click="handleSelectBiz"
              :disabled="form.auditStatus != '1' && form.applyStatus != '1' ? false : true">选择</el-button>
          </el-input>
        </el-form-item>
        <el-form-item label="业务类型" prop="bizType">
          <el-select v-model="form.bizType" placeholder="请选择业务类型" clearable filterable
            :disabled="form.auditStatus != '1' && form.applyStatus != '1' ? false : true">
            <el-option v-for="dict in bizTypeOptions" :key="dict.dictValue" :label="dict.dictLabel"
              :value="dict.dictValue"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item v-if="form.bizType == '4'" label="客户" prop="customerId">
          <el-select v-model="form.customerId" placeholder="请选择客户" clearable filterable
            :disabled="form.auditStatus != '1' && form.applyStatus != '1' ? false : true">
            <el-option v-for="dict in customerIdOptions" :key="dict.dictValue" :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item v-if="form.bizType == '4'" label="合同编号" prop="contractNo">
          <el-input v-model="form.contractNo" placeholder="" disabled />
        </el-form-item>
        <el-form-item v-if="form.bizType == '4'" label="合同类型" prop="contractType" disabled>
          <el-select v-model="form.contractType" placeholder="" clearable filterable disabled>
            <el-option v-for="dict in contractTypeOptions" :key="dict.dictValue" :label="dict.dictLabel"
              :value="dict.dictValue"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="申请人" prop="applyUser">
          <el-select v-model="form.applyUser" placeholder="" clearable filterable disabled>
            <el-option v-for="dict in applyUserOptions" :key="dict.dictValue" :label="dict.dictLabel"
              :value="dict.dictValue * 1"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="申请日期" prop="applyTime">
          <el-date-picker clearable size="small" v-model="form.applyTime" type="date" value-format="yyyy-MM-dd"
            placeholder="" disabled>
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
              :value="dict.dictValue * 1"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="审核日期" prop="auditTime">
          <el-date-picker clearable size="small" v-model="form.auditTime" type="date" value-format="yyyy-MM-dd"
            placeholder="" disabled>
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
        <el-form-item label="单据状态" prop="billStatus">
          <el-select v-model="form.billStatus" placeholder="" clearable filterable disabled>
            <el-option v-for="dict in billStatusOptions" :key="dict.dictValue" :label="dict.dictLabel"
              :value="dict.dictValue"></el-option>
          </el-select>
        </el-form-item>
        <el-divider v-if="form.bizBillNo" content-position="center">关联单据明细</el-divider>
        <el-table v-if="form.bizBillNo" :data="bizbillDetailList" ref="bizBillDetail">
          <el-table-column label="物料" prop="materialName" width="200" />
          <el-table-column label="型号" prop="materialModel">
          </el-table-column>
          <el-table-column label="规格" prop="materialSpecification">
          </el-table-column>
          <el-table-column label="单位" prop="materialUnit">
          </el-table-column>
          <el-table-column label="发货数量" prop="deliveryQuantity" />
        </el-table>
        <el-divider content-position="center">出库申请明细</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button v-no-more-click v-if="form.auditStatus != '1' && form.applyStatus != '1'" type="primary"
              icon="el-icon-plus" size="mini" @click="handleAddWmsOutWarehouseApplyDetail">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button v-no-more-click v-if="form.auditStatus != '1' && form.applyStatus != '1'" type="danger"
              icon="el-icon-delete" size="mini" @click="handleDeleteWmsOutWarehouseApplyDetail">删除</el-button>
          </el-col>
        </el-row>
        <el-table :data="wmsOutWarehouseApplyDetailList" :row-class-name="rowWmsOutWarehouseApplyDetailIndex"
          @selection-change="handleWmsOutWarehouseApplyDetailSelectionChange" ref="wmsOutWarehouseApplyDetail">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center" prop="index" width="50" />
          <el-table-column label="物料" prop="materialName" width="300">
            <template slot-scope="scope">
              <el-form-item :prop="'wmsOutWarehouseApplyDetailList.' + scope.$index + '.materialName'"
                :rules="rules.materialName">
                <el-input v-model="scope.row.materialName" placeholder="请选择物料" disabled>
                  <el-button v-no-more-click slot="append" @click="handleSelectMaterial(scope.$index)"
                    :disabled="form.auditStatus != '1' && form.applyStatus != '1' ? false : true">选择</el-button>
                </el-input>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="仓库" prop="warehouseId" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'wmsOutWarehouseApplyDetailList.' + scope.$index + '.warehouseId'">
                <el-select v-model="scope.row.warehouseId" placeholder="" disabled>
                  <el-option v-for="dict in warehouseIdOptions" :key="dict.dictValue" :label="dict.dictLabel"
                    :value="dict.dictValue"></el-option>
                </el-select>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="库区" prop="whRegionId" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'wmsOutWarehouseApplyDetailList.' + scope.$index + '.whRegionId'">
                <el-select v-model="scope.row.whRegionId" placeholder="" disabled>
                  <el-option v-for="dict in scope.row.whRegionOptions" :key="dict.id" :label="dict.name"
                    :value="dict.id" />
                </el-select>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="库位" prop="whLocationId" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'wmsOutWarehouseApplyDetailList.' + scope.$index + '.whLocationId'">
                <el-select v-model="scope.row.whLocationId" placeholder="" disabled>
                  <el-option v-for="dict in scope.row.whLocationOptions" :key="dict.id" :label="dict.name"
                    :value="dict.id" />
                </el-select>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="出库数量" prop="outQuantity" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'wmsOutWarehouseApplyDetailList.' + scope.$index + '.outQuantity'"
                :rules="rules.outQuantity">
                <el-input v-model.number="scope.row.outQuantity" placeholder=""
                  :disabled="form.auditStatus != '1' && form.applyStatus != '1' ? false : true" />
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="型号" prop="materialModel">
          </el-table-column>
          <el-table-column label="规格" prop="materialSpecification">
          </el-table-column>
          <el-table-column label="单位" prop="materialUnit">
          </el-table-column>
          <el-table-column label="生产批号" prop="batchNo" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'wmsOutWarehouseApplyDetailList.' + scope.$index + '.batchNo'">
                <el-input v-model="scope.row.batchNo" placeholder="" disabled />
              </el-form-item>
            </template>
          </el-table-column>
        </el-table>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button v-no-more-click v-if="form.billStatus == '1'" type="primary" @click="checkConfirm">盘点确认</el-button>
        <el-button v-no-more-click v-if="form.auditStatus != '1' && form.applyStatus != '1'" type="primary"
          @click="saveForm">保
          存</el-button>
        <el-button v-no-more-click v-if="form.auditStatus != '1' && form.applyStatus != '1'" type="warning"
          @click="submitForm">提
          交</el-button>
        <el-button v-no-more-click @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <MaterialInventorySelect :open="materialSelectOpen" @onCancel="handleMaterialCancel"
      @onSelected="handleMaterialSelected"></MaterialInventorySelect>
    <!--选择销售发货单-->
    <DeliverySelect :open="deliverySelectOpen" @onDeliveryCancel="handleDeliveryCancel"
      @onDeliverySelected="handleDeliverySelected"></DeliverySelect>
    <!--选择未领料的领料单-->
    <RequisitionSelect status="0" :open="requisitionSelectOpen" @onSelected="handleRequisitionSelected"
      @onCancel="handleRequisitionCancel"></RequisitionSelect>
  </div>
</template>

<script>
import { listOutWarehouseApply, getOutWarehouseApply, delOutWarehouseApply, addOutWarehouseApply, updateOutWarehouseApply, exportOutWarehouseApply, submitOutWarehouseApply, outWarehouse, checkOutInventory, delivery } from "@/api/wmsApply/outWarehouseApply";
import { listWarehouseRegion } from "@/api/warehouse/warehouseRegion";
import { listWarehouseLocation } from "@/api/warehouse/warehouseLocation";
import MaterialInventorySelect from "@/components/Material/MaterialInventorySelect.vue";
import DeliverySelect from "@/components/Sale/DeliverySelect.vue";
import { getDelivery } from "@/api/sale/delivery";
import RequisitionSelect from "@/components/product/RequisitionSelect.vue";
import { getRequisitionApply } from "@/api/mesProduct/requisitionApply";
export default {
  name: "OutWarehouseApply",
  components: {
    MaterialInventorySelect,
    DeliverySelect,
    RequisitionSelect,
  },
  data() {
    return {
      material: null,
      currentRowIndex: null,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedWmsOutWarehouseApplyDetail: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 出库申请表格数据
      outWarehouseApplyList: [],
      // 出库申请明细表格数据
      wmsOutWarehouseApplyDetailList: [],
      bizbillDetailList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      materialSelectOpen: false,
      deliverySelectOpen: false,
      requisitionSelectOpen: false,
      // 业务类型字典
      bizTypeOptions: [],
      // 客户字典
      customerIdOptions: [],
      // 合同类型字典
      contractTypeOptions: [],
      // 申请类型字典
      applyTypeOptions: [],
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
      // 单据状态字典
      billStatusOptions: [],
      warehouseIdOptions: [],
      // 查询参数
      queryParams: {
        orderByColumn: 'apply_time',
        isAsc: 'desc',
        pageNum: 1,
        pageSize: 10,
        serialNo: null,
        bizBillId: null,
        bizType: null,
        customerId: null,
        contractId: null,
        contractType: null,
        applyType: '2',
        applyUser: null,
        applyTime: null,
        applyStatus: null,
        auditUser: null,
        auditTime: null,
        auditStatus: null,
        billStatus: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        bizBillNo: [
          { required: true, message: "关联单据号不能为空", trigger: "blur" }
        ],
        bizType: [
          { required: true, message: "业务类型不能为空", trigger: "blur" }
        ],
        materialName: [
          { required: true, message: "物料不能为空", trigger: "blur" }
        ],
        outQuantity: [
          { required: true, message: "出库数量不能为空", trigger: "blur" },
          { type: 'number', min: 1, max: 65535, message: '出库数量必须为数字值且大小要在1到65535' },
        ],
        remark: [
          { max: 100, message: "备注最多100个字符", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("event_type").then(response => {
      this.bizTypeOptions = response.data.filter(item => item.dictValue == '3' || item.dictValue == '4');;
    });
    this.getQueryData("query_customer").then(response => {
      this.customerIdOptions = response.data;
    });
    this.getDicts("contract_type").then(response => {
      this.contractTypeOptions = response.data;
    });
    this.getDicts("wms_apply_type").then(response => {
      this.applyTypeOptions = response.data;
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
    this.getDicts("out_warehouse_status").then(response => {
      this.billStatusOptions = response.data;
    });
    this.getQueryData("query_warehouse").then(res => {
      this.warehouseIdOptions = res.data.map(item => ({
        dictValue: item.dictValue * 1,
        dictLabel: item.dictLabel,
      }));
    });
  },
  watch: {
    'wmsOutWarehouseApplyDetailList': {
      handler(newVal, oldVal) {
        this.form.wmsOutWarehouseApplyDetailList = newVal;
      },
      immediate: true,
      deep: true,
    }
  },
  methods: {
    //选择领料单回调函数
    handleRequisitionSelected(row) {
      this.form.bizBillId = row.id;
      this.form.bizBillNo = row.serialNo;
      this.getRequisitionDetailList();
      this.requisitionSelectOpen = false;
    },
    //选择领料单按钮
    handleSelectRequisition() {
      this.requisitionSelectOpen = true;
    },
    //取消选择
    handleRequisitionCancel() {
      this.requisitionSelectOpen = false;
    },
    //选择销售发货单回调函数
    handleDeliverySelected(list) {
      if (list && list.length > 0) {
        let bill = list[0];
        this.form.bizBillId = bill.id;
        this.form.bizBillNo = bill.deliveryNo;
        this.form.contractId = bill.contractId;
        this.form.contractNo = bill.contractNo;
        this.form.customerId = bill.customerId;
        this.form.contractType = '2';
        // 根据发货单id获取detailList
        this.getSaleDeliveryDetailList();
      }
      this.deliverySelectOpen = false;
    },
    //选择
    handleSelectDelivery() {
      this.deliverySelectOpen = true;
    },
    //取消选择
    handleDeliveryCancel() {
      this.deliverySelectOpen = false;
    },
    //发货处理函数
    handleDelivery(row) {
      delivery({ id: row.id }).then(res => {
        this.msgSuccess("发货成功");
        this.getList();
      })
    },
    getRequisitionDetailList() {
      getRequisitionApply(this.form.bizBillId).then(res => {
        let list = res.data.mesMaterialRequisitionDetailList;
        this.bizbillDetailList = list.map(item => {
          return {
            materialId: item.materialId,
            materialName: item.materialName,
            materialModel: item.materialModel,
            materialSpecification: item.materialSpecification,
            materialUnit: item.materialUnit,
            warehouseId: null,
            whLocationId: null,
            whRegionId: null,
            whRegionOptions: [],
            whLocationOptions: [],
            deliveryQuantity: item.requireQuantity,
            materialCode: item.materialCode,
          }
        })
      });
    },
    getSaleDeliveryDetailList() {
      getDelivery(this.form.bizBillId).then(res => {
        let detailList = res.data.scmSaleDeliveryDetailList
        let list = [];
        detailList.forEach(item => {
          let deliveryQuantity = item.quantity ? item.quantity : 0;
          list.push({
            materialId: item.materialId,
            batchNo: item.batchNo,
            materialName: item.materialName,
            materialModel: item.materialModel,
            materialSpecification: item.materialSpecification,
            materialUnit: item.materialUnit,
            warehouseId: null,
            whLocationId: null,
            whRegionId: null,
            whRegionOptions: [],
            whLocationOptions: [],
            deliveryQuantity: deliveryQuantity,
            materialCode: item.materialCode,
          })
        });
        this.bizbillDetailList = list;
      })
    },
    //分拣处理函数
    handleOutWarehouse(row) {
      outWarehouse({ id: row.id }).then(res => {
        this.msgSuccess("分拣成功");
        this.getList();
      })
    },
    //选择关联单据号处理函数
    handleSelectBiz() {
      if (!this.form.bizType) {
        this.msgError('请选择业务类型');
      }
      //领料出库
      if (this.form.bizType == '3') {
        this.handleSelectRequisition();
      }
      //销售出库
      if (this.form.bizType == '4') {
        this.handleSelectDelivery();
      }
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
        this.wmsOutWarehouseApplyDetailList[this.currentRowIndex].warehouseId = this.material.warehouseId;
        this.wmsOutWarehouseApplyDetailList[this.currentRowIndex].whRegionId = this.material.whRegionId;
        this.wmsOutWarehouseApplyDetailList[this.currentRowIndex].whLocationId = this.material.whLocationId;
        this.wmsOutWarehouseApplyDetailList[this.currentRowIndex].materialId = this.material.materialId;
        this.wmsOutWarehouseApplyDetailList[this.currentRowIndex].materialName = this.material.materialName;
        this.wmsOutWarehouseApplyDetailList[this.currentRowIndex].materialCode = this.material.materialCode;
        this.wmsOutWarehouseApplyDetailList[this.currentRowIndex].materialModel = this.material.materialModel;
        this.wmsOutWarehouseApplyDetailList[this.currentRowIndex].materialSpecification = this.material.materialSpecification;
        this.wmsOutWarehouseApplyDetailList[this.currentRowIndex].materialUnit = this.material.materialUnit;
        this.wmsOutWarehouseApplyDetailList[this.currentRowIndex].batchNo = this.material.batchNo;
        this.handleWarehouseChange(this.material.warehouseId, this.currentRowIndex);
        this.handleWhRegionChange(this.material.whRegionId, this.currentRowIndex);
      }
      this.materialSelectOpen = false;
    },
    //选择仓库处理函数
    handleWarehouseChange(val, index) {
      if (val) {
        this.getWhRegionOpts(val, index);
      }
    },
    //选择库区处理函数
    handleWhRegionChange(val, index) {
      if (val) {
        this.getWhLocationOpts(val, index);
      }
    },
    //获取库区选项
    getWhRegionOpts(val, index) {
      let queryParams = {
        warehouseId: val,
      }
      listWarehouseRegion(queryParams).then(res => {
        this.wmsOutWarehouseApplyDetailList[index].whRegionOptions = res.rows;
      });
    },
    //获取库位选项
    getWhLocationOpts(val, index) {
      let queryParams = {
        regionId: val,
      }
      listWarehouseLocation(queryParams).then(res => {
        this.wmsOutWarehouseApplyDetailList[index].whLocationOptions = res.rows;
      })
    },
    /** 查询出库申请列表 */
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
      listOutWarehouseApply(this.queryParams).then(response => {
        this.outWarehouseApplyList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 业务类型字典翻译
    bizTypeFormat(row, column) {
      return this.selectDictLabel(this.bizTypeOptions, row.bizType);
    },
    // 客户字典翻译
    customerIdFormat(row, column) {
      return this.selectDictLabel(this.customerIdOptions, row.customerId);
    },
    // 合同类型字典翻译
    contractTypeFormat(row, column) {
      return this.selectDictLabel(this.contractTypeOptions, row.contractType);
    },
    // 申请类型字典翻译
    applyTypeFormat(row, column) {
      return this.selectDictLabel(this.applyTypeOptions, row.applyType);
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
    // 单据状态字典翻译
    billStatusFormat(row, column) {
      return this.selectDictLabel(this.billStatusOptions, row.billStatus);
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
        bizBillId: null,
        bizBillNo: null,
        bizType: null,
        customerId: null,
        contractId: null,
        contractNo: null,
        contractType: null,
        applyType: '2',
        applyUser: null,
        applyTime: null,
        applyStatus: null,
        auditUser: null,
        auditTime: null,
        auditStatus: null,
        auditComment: null,
        remark: null,
        billStatus: null,
        wmsOutWarehouseApplyDetailList: [],
      };
      this.wmsOutWarehouseApplyDetailList = [];
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
      this.single = filters.length !== 1
      this.multiple = !filters.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加出库申请";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getOutWarehouseApply(id).then(response => {
        this.form = response.data;
        response.data.wmsOutWarehouseApplyDetailList.forEach(item => {
          let obj = {
            whRegionOptions: [],
            whLocationOptions: [],
          };
          let detail = Object.assign(obj, item);
          this.wmsOutWarehouseApplyDetailList.push(detail);
        })
        this.wmsOutWarehouseApplyDetailList.forEach((item, index) => {
          this.getWhRegionOpts(item.warehouseId, index);
          this.getWhLocationOpts(item.whRegionId, index);
        })
        //领料出库
        if (this.form.bizType == '3') {
          this.getRequisitionDetailList();
        }
        //销售出库
        if (this.form.bizType == '4') {
          this.getSaleDeliveryDetailList();
        }
        this.open = true;
        this.title = "修改出库申请";
      });
    },
    //盘点确认按钮处理函数
    checkConfirm() {
      checkOutInventory({ id: this.form.id }).then(res => {
        this.msgSuccess("出库盘点成功");
        this.open = false;
        this.getList();
      })
    },
    validateOutQuantity() {
      let bizMtList = this.bizbillDetailList.map(detail => detail.materialId);
      let applyMtList = this.wmsOutWarehouseApplyDetailList.map(detail => detail.materialId);
      //检查关联业务单的物料是否都已申请出库
      let absent = bizMtList.concat(bizMtList).filter(id => !bizMtList.includes(id) || !applyMtList.includes(id));
      if (absent && absent.length > 0) {
        this.msgError('申请明细物料与业务单据物料不符');
        return false;
      }
      let outSum = {};
      //检查出库数量是否合法
      this.wmsOutWarehouseApplyDetailList.forEach(detail => {
        let materialId = detail.materialId;
        let outQuantity = detail.outQuantity;
        if (outSum[materialId]) {
          outSum[materialId] = outSum[materialId] + outQuantity;
        } else {
          outSum[materialId] = outQuantity;
        }
      });
      for (let detail of this.bizbillDetailList) {
        let materialId = detail.materialId;
        let outQuantity = detail.deliveryQuantity;
        if (outSum[materialId] < outQuantity) {
          this.msgError('物料：' + detail.materialName + '出库数量不足');
          return false;
        }
        if (outSum[materialId] > outQuantity) {
          this.msgError('物料：' + detail.materialName + '出库数量超出单据数量');
          return false;
        }
      }
      return true;
    },
    /** 保存按钮 */
    saveForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (!this.validateOutQuantity()) {
            return;
          }
          if (this.form.id != null) {
            updateOutWarehouseApply(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addOutWarehouseApply(this.form).then(response => {
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
          if (!this.validateOutQuantity()) {
            return;
          }
          submitOutWarehouseApply(this.form).then(response => {
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
      this.$confirm('是否确认删除出库申请编号为"' + ids + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delOutWarehouseApply(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    /** 出库申请明细序号 */
    rowWmsOutWarehouseApplyDetailIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
    /** 出库申请明细添加按钮操作 */
    handleAddWmsOutWarehouseApplyDetail() {
      let obj = {};
      obj.materialId = "";
      obj.materialName = "";
      obj.warehouseId = "";
      obj.whLocationId = "";
      obj.whRegionId = "";
      obj.outQuantity = "";
      obj.batchNo = "";
      obj.productDate = "";
      obj.manufacturer = "";
      obj.materialModel = "";
      obj.materialSpecification = "";
      obj.materialUnit = "";
      obj.whRegionOptions = [];
      obj.whLocationOptions = [];
      this.wmsOutWarehouseApplyDetailList.push(obj);
    },
    /** 出库申请明细删除按钮操作 */
    handleDeleteWmsOutWarehouseApplyDetail() {
      if (this.checkedWmsOutWarehouseApplyDetail.length == 0) {
        this.$alert("请先选择要删除的出库申请明细数据", "提示", { confirmButtonText: "确定", });
      } else {
        this.wmsOutWarehouseApplyDetailList.splice(this.checkedWmsOutWarehouseApplyDetail[0].index - 1, 1);
      }
    },
    /** 单选框选中数据 */
    handleWmsOutWarehouseApplyDetailSelectionChange(selection) {
      if (selection.length > 1) {
        this.$refs.wmsOutWarehouseApplyDetail.clearSelection();
        this.$refs.wmsOutWarehouseApplyDetail.toggleRowSelection(selection.pop());
      } else {
        this.checkedWmsOutWarehouseApplyDetail = selection;
      }
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有出库申请数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return exportOutWarehouseApply(queryParams);
      }).then(response => {
        this.download(response.msg);
      })
    }
  }
};
</script>