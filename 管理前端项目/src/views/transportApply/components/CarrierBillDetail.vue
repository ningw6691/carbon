<template>
  <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body @open="handleOpen" :close-on-click-modal="false" :show-close="false">
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
        <el-select v-model="form.customerId" placeholder="请选择客户" clearable filterable disabled>
          <el-option v-for="dict in customerIdOptions" :key="dict.dictValue" :label="dict.dictLabel" :value="parseInt(dict.dictValue)"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="交货日期" prop="deliveryDate">
        <el-date-picker clearable size="small" v-model="form.deliveryDate" type="date" value-format="yyyy-MM-dd" placeholder="" disabled>
        </el-date-picker>
      </el-form-item>
      <el-form-item label="收货地址" prop="shippingAddress">
        <el-input v-model="form.shippingAddress" placeholder="请输入收货地址" disabled />
      </el-form-item>
      <el-form-item label="联系人" prop="contact">
        <el-input v-model="form.contact" placeholder="请输入联系人" disabled />
      </el-form-item>
      <el-form-item label="联系电话" prop="contactTel">
        <el-input v-model="form.contactTel" placeholder="请输入联系电话" disabled />
      </el-form-item>
      <el-form-item label="承运人" prop="carrierUser">
        <el-select v-model="form.carrierUser" placeholder="请选择承运人" clearable filterable disabled>
          <el-option v-for="dict in carrierUserOptions" :key="dict.dictValue" :label="dict.dictLabel" :value="dict.dictValue"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="运输距离" prop="distance">
        <el-input v-model="form.distance" placeholder="请输入运输距离" disabled>
          <template slot="append">
            公里
          </template>
        </el-input>
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
        <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" disabled />
      </el-form-item>
      <el-form-item label="审核意见" prop="auditComment">
        <el-input v-model="form.auditComment" type="textarea" placeholder="" disabled />
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
      <el-button v-no-more-click @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>
<script>
import { getCarrierApply } from "@/api/transportApply/carrierApply";
import { listWarehouseRegion } from "@/api/warehouse/warehouseRegion";
import { listWarehouseLocation } from "@/api/warehouse/warehouseLocation";
export default {
  name: "CarrierApplyDetail",
  components:
  {

  },
  props: {
    // 是否显示弹出层
    open: {
      type: Boolean,
      default: false,
    },
    billId: null,
  },
  data() {
    return {
      currentRowIndex: null,
      // 遮罩层
      loading: true,
      // 产品承运申请明细表格数据
      wmsCarrierApplyDetailList: [],
      // 弹出层标题
      title: "",
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
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
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
    handleOpen() {
      this.getInfo();
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
      this.reset();
      this.$emit('onCancel');
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
    /** 产品承运申请明细序号 */
    rowWmsCarrierApplyDetailIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
    /** 获取详情 */
    getInfo() {
      this.reset();
      getCarrierApply(this.billId).then(response => {
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
        this.title = "查看承运单详情";
      });
    },
  }
};
</script>