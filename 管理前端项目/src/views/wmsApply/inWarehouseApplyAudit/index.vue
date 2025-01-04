formge<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="90px">
      <el-form-item label="申请单号" prop="serialNo">
        <el-input v-model="queryParams.serialNo" placeholder="请输入申请单" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="关联单据号" prop="bizBillId">
        <el-input v-model="queryParams.bizBillId" placeholder="请输入关联单据号" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="业务类型" prop="bizType">
        <el-select v-model="queryParams.bizType" placeholder="请选择业务类型" clearable filterable size="small">
          <el-option v-for="dict in bizTypeOptions" :key="dict.dictValue" :label="dict.dictLabel"
            :value="dict.dictValue" />
        </el-select>
      </el-form-item>
      <el-form-item label="供应商" prop="supplierId">
        <el-select v-model="queryParams.supplierId" placeholder="请选择供应商" clearable filterable size="small">
          <el-option v-for="dict in supplierIdOptions" :key="dict.dictValue" :label="dict.dictLabel"
            :value="dict.dictValue" />
        </el-select>
      </el-form-item>
      <el-form-item label="合同号" prop="contractId">
        <el-input v-model="queryParams.contractId" placeholder="请输入合同流水号" clearable size="small"
          @keyup.enter.native="handleQuery" />
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
      <el-form-item label="申请日期" prop="applyTime">
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
      <el-form-item label="审核日期" prop="auditTime">
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

    <el-table v-loading="loading" :data="inWarahouseApplyList" @selection-change="handleSelectionChange">
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="申请单号" align="center" prop="serialNo" width="100" />
      <el-table-column label="关联单据号" align="center" prop="bizBillNo" width="100" />
      <el-table-column label="业务类型" align="center" prop="bizType" :formatter="bizTypeFormat" />
      <el-table-column label="供应商" align="center" prop="supplierId" :formatter="supplierIdFormat" />
      <el-table-column label="合同号" align="center" prop="contractNo" />
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
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-no-more-click size="mini" type="text" icon="el-icon-edit"
            @click="handleUpdate(scope.row)">详情</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改入库申请对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="90px" :inline="true">
        <el-form-item label="申请单号" prop="serialNo">
          <el-input v-model="form.serialNo" disabled />
        </el-form-item>
        <el-form-item label="关联单据号" prop="bizBillId">
          <el-input v-model="form.bizBillId" disabled>
            <el-button v-no-more-click slot="append" @click="handleSelectBiz" disabled>选择</el-button>
          </el-input>
        </el-form-item>
        <el-form-item label="业务类型" prop="bizType">
          <el-select v-model="form.bizType" placeholder="请选择业务类型" clearable filterable disabled>
            <el-option v-for="dict in bizTypeOptions" :key="dict.dictValue" :label="dict.dictLabel"
              :value="dict.dictValue"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="供应商" prop="supplierId">
          <el-select v-model="form.supplierId" placeholder="请选择供应商" clearable filterable disabled>
            <el-option v-for="dict in supplierIdOptions" :key="dict.dictValue" :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="合同号" prop="contractId">
          <el-input v-model="form.contractId" disabled />
        </el-form-item>
        <el-form-item label="合同类型" prop="contractType">
          <el-select v-model="form.contractType" placeholder="" disabled>
            <el-option v-for="dict in contractTypeOptions" :key="dict.dictValue" :label="dict.dictLabel"
              :value="dict.dictValue"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="申请类型" prop="applyType">
          <el-select v-model="form.applyType" placeholder="" disabled>
            <el-option v-for="dict in applyTypeOptions" :key="dict.dictValue" :label="dict.dictLabel"
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
          <el-input v-model="form.auditComment" type="textarea" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" disabled />
        </el-form-item>
        <el-divider content-position="center">入库申请明细信息</el-divider>
        <el-table :data="wmsInWarehouseApplyDetailList" :row-class-name="rowWmsInWarehouseApplyDetailIndex"
          ref="wmsInWarehouseApplyDetail">
          <el-table-column label="序号" align="center" prop="index" width="50" />
          <el-table-column label="物料" prop="materialName" width="300">
            <template slot-scope="scope">
              <el-form-item :prop="'wmsInWarehouseApplyDetailList.' + scope.$index + '.materialName'"
                :rules="rules.materialName">
                <el-input v-model="scope.row.materialName" placeholder="请选择物料" disabled>
                  <el-button v-no-more-click slot="append" @click="handleSelectMaterial(scope.$index)"
                    disabled>选择</el-button>
                </el-input>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="仓库" prop="warehouseId" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'wmsInWarehouseApplyDetailList.' + scope.$index + '.warehouseId'"
                :rules="rules.warehouseId">
                <el-select v-model="scope.row.warehouseId" placeholder="请选择仓库"
                  @change="handleWarehouseChange(scope.row.warehouseId, scope.$index)" disabled>
                  <el-option v-for="dict in warehouseIdOptions" :key="dict.dictValue" :label="dict.dictLabel"
                    :value="dict.dictValue"></el-option>
                </el-select>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="库区" prop="whRegionId" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'wmsInWarehouseApplyDetailList.' + scope.$index + '.whRegionId'"
                :rules="rules.whRegionId">
                <el-select v-model="scope.row.whRegionId" placeholder="请选择库区"
                  @change="handleWhRegionChange(scope.row.whRegionId, scope.$index)" disabled>
                  <el-option v-for="dict in scope.row.whRegionOptions" :key="dict.id" :label="dict.name"
                    :value="dict.id" />
                </el-select>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="库位" prop="whLocationId" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'wmsInWarehouseApplyDetailList.' + scope.$index + '.whLocationId'"
                :rules="rules.whLocationId">
                <el-select v-model="scope.row.whLocationId" placeholder="请选择库位" disabled>
                  <el-option v-for="dict in scope.row.whLocationOptions" :key="dict.id" :label="dict.name"
                    :value="dict.id" />
                </el-select>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="到货数量" prop="totalQuantity" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'wmsInWarehouseApplyDetailList.' + scope.$index + '.totalQuantity'"
                :rules="rules.totalQuantity">
                <el-input v-model="scope.row.totalQuantity" placeholder="请输入到货数量" disabled />
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="入库数量" prop="inQuantity" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'wmsInWarehouseApplyDetailList.' + scope.$index + '.inQuantity'"
                :rules="rules.inQuantity">
                <el-input v-model="scope.row.inQuantity" placeholder="请输入入库数量" disabled />
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
              <el-form-item :prop="'wmsInWarehouseApplyDetailList.' + scope.$index + '.batchNo'" :rules="rules.batchNo">
                <el-input v-model="scope.row.batchNo" placeholder="请输入生产批号" disabled />
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="生产日期" prop="productDate" width="240">
            <template slot-scope="scope">
              <el-form-item :prop="'wmsInWarehouseApplyDetailList.' + scope.$index + '.productDate'"
                :rules="rules.productDate">
                <el-date-picker clearable v-model="scope.row.productDate" type="date" value-format="yyyy-MM-dd"
                  placeholder="请选择生产日期" disabled />
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="生产单位" prop="manufacturer" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'wmsInWarehouseApplyDetailList.' + scope.$index + '.manufacturer'"
                :rules="rules.manufacturer">
                <el-input v-model="scope.row.manufacturer" placeholder="请输入生产单位" disabled />
              </el-form-item>
            </template>
          </el-table-column>
        </el-table>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button v-no-more-click v-if="form.applyStatus != '2'" type="primary" @click="submitForm('1')">通
          过</el-button>
        <el-button v-no-more-click v-if="form.applyStatus != '2'" type="warning" @click="submitForm('0')">驳
          回</el-button>
        <el-button v-no-more-click @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listInWarahouseApply4Audit, getInWarahouseApply, auditInWarahouseApply } from "@/api/wmsApply/inWarahouseApply";
import { listWarehouseRegion } from "@/api/warehouse/warehouseRegion";
import { listWarehouseLocation } from "@/api/warehouse/warehouseLocation";
export default {
  name: "InWarahouseApplyAudit",
  components: {

  },
  data() {
    let checkAuditComment = (rule, value, callback) => {
      if (this.form.auditStatus == '0') {
        if (!value) {
          callback(new Error('请输入审核意见'));
        }
      }
      callback();
    }
    return {
      material: null,
      currentRowIndex: null,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedWmsInWarehouseApplyDetail: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 入库申请表格数据
      inWarahouseApplyList: [],
      // 入库申请明细表格数据
      wmsInWarehouseApplyDetailList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      materialSelectOpen: false,
      // 业务类型字典
      bizTypeOptions: [],
      // 供应商字典
      supplierIdOptions: [],
      // 合同类型字典
      contractTypeOptions: [],
      // 申请类型字典
      applyTypeOptions: [],
      // 申请人字典
      applyUserOptions: [],
      // 申请状态字典
      applyStatusOptions: [],
      // 审核人字典
      auditUserOptions: [],
      // 审核状态字典
      auditStatusOptions: [],
      warehouseIdOptions: [],
      // 申请日期时间范围
      daterangeApplyTime: [],
      // 审核日期时间范围
      daterangeAuditTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        serialNo: null,
        bizBillId: null,
        bizType: null,
        supplierId: null,
        contractId: null,
        contractType: null,
        applyType: '1',
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
        auditComment: [{
          validator: checkAuditComment, trigger: 'blur'
        },
        { max: 100, message: "审核意见最多100个字符", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getQueryData("query_supplier").then(res => {
      this.supplierIdOptions = res.data;
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
    this.getQueryData("query_warehouse").then(res => {
      this.warehouseIdOptions = res.data.map(item => ({
        dictValue: item.dictValue * 1,
        dictLabel: item.dictLabel,
      }));
    });
  },
  watch: {
    'wmsInWarehouseApplyDetailList': {
      handler(newVal, oldVal) {
        this.form.wmsInWarehouseApplyDetailList = newVal;
      },
      immediate: true,
      deep: true,
    }
  },
  methods: {
    //选择关联单据号处理函数
    handleSelectBiz() {

    },
    //选择仓库处理函数
    handleWarehouseChange(val, index) {
      this.wmsInWarehouseApplyDetailList[index].whRegionId = null;
      this.wmsInWarehouseApplyDetailList[index].whLocationId = null;
      if (val) {
        this.getWhRegionOpts(val, index);
      }
    },
    //选择库区处理函数
    handleWhRegionChange(val, index) {
      this.wmsInWarehouseApplyDetailList[index].whLocationId = null;
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
        this.wmsInWarehouseApplyDetailList[index].whRegionOptions = res.rows;
      });
    },
    //获取库位选项
    getWhLocationOpts(val, index) {
      let queryParams = {
        regionId: val,
      }
      listWarehouseLocation(queryParams).then(res => {
        this.wmsInWarehouseApplyDetailList[index].whLocationOptions = res.rows;
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
        this.wmsInWarehouseApplyDetailList[this.currentRowIndex].materialId = this.material.id;
        this.wmsInWarehouseApplyDetailList[this.currentRowIndex].materialName = this.material.name;
        this.wmsInWarehouseApplyDetailList[this.currentRowIndex].materialCode = this.material.code;
        this.wmsInWarehouseApplyDetailList[this.currentRowIndex].materialModel = this.material.model;
        this.wmsInWarehouseApplyDetailList[this.currentRowIndex].materialSpecification = this.material.specification;
        this.wmsInWarehouseApplyDetailList[this.currentRowIndex].materialUnit = this.material.unit;
      }
      this.materialSelectOpen = false;
    },
    /** 查询入库申请列表 */
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
      listInWarahouseApply4Audit(this.queryParams).then(response => {
        this.inWarahouseApplyList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 业务类型字典翻译
    bizTypeFormat(row, column) {
      return this.selectDictLabel(this.bizTypeOptions, row.bizType);
    },
    // 供应商字典翻译
    supplierIdFormat(row, column) {
      return this.selectDictLabel(this.supplierIdOptions, row.supplierId);
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
        bizType: null,
        supplierId: null,
        contractId: null,
        contractType: null,
        applyType: '1',
        applyUser: null,
        applyTime: null,
        applyStatus: null,
        auditUser: null,
        auditTime: null,
        auditStatus: null,
        remark: null,
        auditComment: null,
        wmsInWarehouseApplyDetailList: [],
      };
      this.wmsInWarehouseApplyDetailList = [];
      this.getDicts("event_type").then(response => {
        if (this.form.applyType == '1') {
          this.bizTypeOptions = response.data.filter(item => item.dictValue == '1' || item.dictValue == '2' || item.dictValue == '5');
        }
      });
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
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加入库申请";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getInWarahouseApply(id).then(response => {
        this.form = response.data;
        response.data.wmsInWarehouseApplyDetailList.forEach(item => {
          let obj = {
            whRegionOptions: [],
            whLocationOptions: [],
          };
          let detail = Object.assign(obj, item);
          this.wmsInWarehouseApplyDetailList.push(detail);
        })
        //this.wmsInWarehouseApplyDetailList = response.data.wmsInWarehouseApplyDetailList;
        this.wmsInWarehouseApplyDetailList.forEach((item, index) => {
          this.getWhRegionOpts(item.warehouseId, index);
          this.getWhLocationOpts(item.whRegionId, index);
        })
        this.open = true;
        this.title = "审核入库申请";
      });
    },
    //审核按钮
    submitForm(status) {
      this.form.auditStatus = status;
      this.$refs["form"].validate(valid => {
        if (valid) {
          auditInWarahouseApply(this.form).then(response => {
            this.msgSuccess("审核成功");
            this.open = false;
            this.getList();
          });
        }
      });
    },
    /** 入库申请明细序号 */
    rowWmsInWarehouseApplyDetailIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
  }
};
</script>