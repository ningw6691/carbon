<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="单据号" prop="serialNo">
        <el-input v-model="queryParams.serialNo" placeholder="请输入单据号" clearable size="small" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="出库单号" prop="outWarehouseNo">
        <el-input v-model="queryParams.outWarehouseNo" placeholder="请输入出库单号" clearable size="small" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="发货单号" prop="saleDeliveryNo">
        <el-input v-model="queryParams.saleDeliveryNo" placeholder="请输入发货单号" clearable size="small" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="客户" prop="customerId">
        <el-select v-model="queryParams.customerId" placeholder="请选择客户" clearable filterable size="small">
          <el-option v-for="dict in customerIdOptions" :key="dict.dictValue" :label="dict.dictLabel" :value="dict.dictValue" />
        </el-select>
      </el-form-item>
      <el-form-item label="申请人" prop="applyUser">
        <el-select v-model="queryParams.applyUser" placeholder="请选择申请人" clearable filterable size="small">
          <el-option v-for="dict in applyUserOptions" :key="dict.dictValue" :label="dict.dictLabel" :value="dict.dictValue" />
        </el-select>
      </el-form-item>
      <el-form-item label="申请日期">
        <el-date-picker v-model="daterangeApplyTime" size="small" style="width: 240px" value-format="yyyy-MM-dd" type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="申请状态" prop="applyStatus">
        <el-select v-model="queryParams.applyStatus" placeholder="请选择申请状态" clearable filterable size="small">
          <el-option v-for="dict in applyStatusOptions" :key="dict.dictValue" :label="dict.dictLabel" :value="dict.dictValue" />
        </el-select>
      </el-form-item>
      <el-form-item label="审核人" prop="auditUser">
        <el-select v-model="queryParams.auditUser" placeholder="请选择审核人" clearable filterable size="small">
          <el-option v-for="dict in auditUserOptions" :key="dict.dictValue" :label="dict.dictLabel" :value="dict.dictValue" />
        </el-select>
      </el-form-item>
      <el-form-item label="审核日期">
        <el-date-picker v-model="daterangeAuditTime" size="small" style="width: 240px" value-format="yyyy-MM-dd" type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="审核状态" prop="auditStatus">
        <el-select v-model="queryParams.auditStatus" placeholder="请选择审核状态" clearable filterable size="small">
          <el-option v-for="dict in auditStatusOptions" :key="dict.dictValue" :label="dict.dictLabel" :value="dict.dictValue" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button v-no-more-click type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button v-no-more-click icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="carrierApplyList">
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="单据号" align="center" prop="serialNo" />
      <el-table-column label="出库单号" align="center" prop="outWarehouseNo" width="150" />
      <el-table-column label="发货单号" align="center" prop="saleDeliveryNo" width="150" />
      <el-table-column label="客户" align="center" prop="customerId" :formatter="customerIdFormat" />
      <el-table-column label="收货地址" align="center" prop="shippingAddress" />
      <el-table-column label="联系人" align="center" prop="contact" />
      <el-table-column label="联系电话" align="center" prop="contactTel" />
      <el-table-column label="承运人" align="center" prop="carrierUser" :formatter="carrierUserFormat" />
      <el-table-column label="运输距离" align="center" prop="distance" />
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
          <el-button v-no-more-click size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['transportApply:carrierApply:audit']">详情</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <!-- 添加或修改承运申请对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="90px" :inline="true">
        <el-form-item label="单据号" prop="serialNo">
          <el-input v-model="form.serialNo" placeholder="" disabled />
        </el-form-item>
        <el-form-item label="出库单号" prop="outWarehouseNo">
          <el-input v-model="form.outWarehouseNo" placeholder="" disabled>
          </el-input>
        </el-form-item>
        <el-form-item label="发货单号" prop="saleDeliveryNo">
          <el-input v-model="form.saleDeliveryNo" placeholder="" disabled />
        </el-form-item>
        <el-form-item label="客户" prop="customerId">
          <el-select v-model="form.customerId" placeholder="" clearable filterable disabled>
            <el-option v-for="dict in customerIdOptions" :key="dict.dictValue" :label="dict.dictLabel" :value="parseInt(dict.dictValue)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="收货地址" prop="shippingAddress">
          <el-input v-model="form.shippingAddress" placeholder="" disabled />
        </el-form-item>
        <el-form-item label="联系人" prop="contact">
          <el-input v-model="form.contact" placeholder="" disabled />
        </el-form-item>
        <el-form-item label="联系电话" prop="contactTel">
          <el-input v-model="form.contactTel" placeholder="" disabled />
        </el-form-item>
        <el-form-item label="承运人" prop="carrierUser">
          <el-select v-model="form.carrierUser" placeholder="" clearable filterable disabled>
            <el-option v-for="dict in carrierUserOptions" :key="dict.dictValue" :label="dict.dictLabel" :value="dict.dictValue"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="运输距离" prop="distance">
          <el-input v-model="form.distance" placeholder="" disabled />
        </el-form-item>
        <el-form-item label="申请人" prop="applyUser">
          <el-select v-model="form.applyUser" placeholder="请选择申请人" clearable filterable disabled>
            <el-option v-for="dict in applyUserOptions" :key="dict.dictValue" :label="dict.dictLabel" :value="dict.dictValue"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="申请日期" prop="applyTime">
          <el-date-picker clearable size="small" v-model="form.applyTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss" placeholder="" disabled>
          </el-date-picker>
        </el-form-item>
        <el-form-item label="申请状态" prop="applyStatus">
          <el-select v-model="form.applyStatus" placeholder="" clearable filterable disabled>
            <el-option v-for="dict in applyStatusOptions" :key="dict.dictValue" :label="dict.dictLabel" :value="dict.dictValue"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="审核人" prop="auditUser">
          <el-select v-model="form.auditUser" placeholder="" clearable filterable disabled>
            <el-option v-for="dict in auditUserOptions" :key="dict.dictValue" :label="dict.dictLabel" :value="dict.dictValue"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="审核日期" prop="auditTime">
          <el-date-picker clearable size="small" v-model="form.auditTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss" placeholder="" disabled>
          </el-date-picker>
        </el-form-item>
        <el-form-item label="审核状态" prop="auditStatus">
          <el-select v-model="form.auditStatus" placeholder="" clearable filterable disabled>
            <el-option v-for="dict in auditStatusOptions" :key="dict.dictValue" :label="dict.dictLabel" :value="dict.dictValue"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="" disabled />
        </el-form-item>
        <el-form-item label="审核意见" prop="auditComment">
          <el-input v-model="form.auditComment" type="textarea" placeholder="" />
        </el-form-item>
        <el-divider content-position="center">产品承运申请明细信息</el-divider>
        <el-table :data="wmsCarrierApplyDetailList" :row-class-name="rowWmsCarrierApplyDetailIndex" ref="wmsCarrierApplyDetail">
          <el-table-column label="序号" align="center" prop="index" width="50" />
          <el-table-column label="物料" prop="materialName" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'wmsCarrierApplyDetailList.'+scope.$index+'.materialName'" :rules="rules.materialName">
                <el-input v-model="scope.row.materialName" placeholder="" disabled />
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="生产批号" prop="batchNo" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'wmsCarrierApplyDetailList.'+scope.$index+'.batchNo'" :rules="rules.batchNo">
                <el-input v-model="scope.row.batchNo" placeholder="" disabled />
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="仓库" prop="warehouseId" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'wmsCarrierApplyDetailList.'+scope.$index+'.warehouseId'" :rules="rules.warehouseId">
                <el-select v-model="scope.row.warehouseId" placeholder="" disabled>
                  <el-option v-for="dict in warehouseIdOptions" :key="dict.dictValue" :label="dict.dictLabel" :value="dict.dictValue"></el-option>
                </el-select>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="库区" prop="whRegionId" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'wmsCarrierApplyDetailList.'+scope.$index+'.whRegionId'" :rules="rules.whRegionId">
                <el-select v-model="scope.row.whRegionId" placeholder="" disabled>
                  <el-option v-for="dict in scope.row.whRegionOptions" :key="dict.id" :label="dict.name" :value="dict.id" />
                </el-select>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="库位" prop="whLocationId" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'wmsCarrierApplyDetailList.'+scope.$index+'.whLocationId'" :rules="rules.whLocationId">
                <el-select v-model="scope.row.whLocationId" placeholder="" disabled>
                  <el-option v-for="dict in scope.row.whLocationOptions" :key="dict.id" :label="dict.name" :value="dict.id" />
                </el-select>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="发货数量" prop="deliveryQuantity" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'wmsCarrierApplyDetailList.'+scope.$index+'.deliveryQuantity'" :rules="rules.deliveryQuantity">
                <el-input v-model="scope.row.deliveryQuantity" placeholder="" disabled />
              </el-form-item>
            </template>
          </el-table-column>
        </el-table>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button v-no-more-click v-if="form.applyStatus!='2'" type="primary" @click="submitForm('1')">通 过</el-button>
        <el-button v-no-more-click v-if="form.applyStatus!='2'" type="warning" @click="submitForm('0')">驳 回</el-button>
        <el-button v-no-more-click @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listCarrierApply4Audit, getCarrierApply, auditCarrierApply } from "@/api/transportApply/carrierApply";
import { listWarehouseRegion } from "@/api/warehouse/warehouseRegion";
import { listWarehouseLocation } from "@/api/warehouse/warehouseLocation";
export default {
  name: "CarrierApplyAudit",
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
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedWmsCarrierApplyDetail: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 承运申请表格数据
      carrierApplyList: [],
      // 产品承运申请明细表格数据
      wmsCarrierApplyDetailList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
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
      // 仓库字典
      warehouseIdOptions: [],
      // 客户字典
      customerIdOptions: [],
      // 承运人字典
      carrierUserOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        serialNo: null,
        customerId: null,
        applyUser: null,
        applyTime: null,
        applyStatus: null,
        auditUser: null,
        auditTime: null,
        auditStatus: null,
        outWarehouseId: null,
        outWarehouseNo: null,
        saleDiveryId: null,
        saleDeliveryNo: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        auditComment: [{
          validator: checkAuditComment, trigger: 'blur'
        },
        { max: 100, message: "审核意见最多100个字符", trigger: "blur" }
      ],
      }
    };
  },
  created() {
    this.getList();
    this.getQueryData("query_customer").then(res => {
      this.customerIdOptions = res.data;
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
    this.getQueryData("query_user").then(res => {
      this.carrierUserOptions = res.data;
    });
  },
  watch: {
    'wmsCarrierApplyDetailList': {
      handler(newVal, oldVal) {
        this.form.wmsCarrierApplyDetailList = newVal;
      },
      immediate: true,
      deep: true,
    }
  },
  methods: {
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
        this.wmsCarrierApplyDetailList[index].whRegionOptions = res.rows;
      });
    },
    //获取库位选项
    getWhLocationOpts(val, index) {
      let queryParams = {
        regionId: val,
      }
      listWarehouseLocation(queryParams).then(res => {
        this.wmsCarrierApplyDetailList[index].whLocationOptions = res.rows;
      })
    },
    /** 查询承运申请列表 */
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
      listCarrierApply4Audit(this.queryParams).then(response => {
        this.carrierApplyList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 客户字典翻译
    customerIdFormat(row, column) {
      return this.selectDictLabel(this.customerIdOptions, row.customerId);
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
    // 承运人字典翻译
    carrierUserFormat(row, column) {
      return this.selectDictLabel(this.carrierUserOptions, row.carrierUser);
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
        customerId: null,
        applyUser: null,
        applyTime: null,
        applyStatus: null,
        auditUser: null,
        auditTime: null,
        auditStatus: null,
        outWarehouseId: null,
        outWarehouseNo: null,
        saleDiveryId: null,
        saleDeliveryNo: null,
        shippingAddress: null,
        contact: null,
        contactTel: null,
        carrierUser: null,
        distance: null,
        remark: null,
        auditComment: null,
        wmsCarrierApplyDetailList: [],
      };
      this.wmsCarrierApplyDetailList = [];
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
    /** 详情按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getCarrierApply(id).then(response => {
        this.form = response.data;
        response.data.wmsCarrierApplyDetailList.forEach(item => {
          let obj = {
            whRegionOptions: [],
            whLocationOptions: [],
          };
          let detail = Object.assign(obj, item);
          this.wmsCarrierApplyDetailList.push(detail);
        })
        this.wmsCarrierApplyDetailList.forEach((item, index) => {
          this.getWhRegionOpts(item.warehouseId, index);
          this.getWhLocationOpts(item.whRegionId, index);
        })
        this.open = true;
        this.title = "查看承运申请";
      });
    },
    /** 审核按钮 */
    submitForm(status) {
      this.form.auditStatus = status;
      this.$refs["form"].validate(valid => {
        if (valid) {
          auditCarrierApply(this.form).then(response => {
            this.msgSuccess("审核成功");
            this.open = false;
            this.getList();
          });
        }
      });
    },
    /** 产品承运申请明细序号 */
    rowWmsCarrierApplyDetailIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
  }
};
</script>
