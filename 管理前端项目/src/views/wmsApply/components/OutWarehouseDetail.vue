<template>
  <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body @open="handleOpen" :close-on-click-modal="false" :show-close="false">
    <el-form ref="form" :model="form" :rules="rules" label-width="90px" :inline="true">
      <el-form-item label="单据号" prop="serialNo">
        <el-input v-model="form.serialNo" placeholder="" disabled />
      </el-form-item>
      <el-form-item label="关联单据号" prop="bizBillId">
        <el-input v-model="form.bizBillId" placeholder="" disabled>
        </el-input>
      </el-form-item>
      <el-form-item label="业务类型" prop="bizType">
        <el-select v-model="form.bizType" placeholder="" clearable filterable>
          <el-option v-for="dict in bizTypeOptions" :key="dict.dictValue" :label="dict.dictLabel" :value="dict.dictValue"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="客户" prop="customerId">
        <el-select v-model="form.customerId" placeholder="" clearable filterable disabled>
          <el-option v-for="dict in customerIdOptions" :key="dict.dictValue" :label="dict.dictLabel" :value="parseInt(dict.dictValue)"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="合同编号" prop="contractId">
        <el-input v-model="form.contractId" placeholder="" disabled />
      </el-form-item>
      <el-form-item label="合同类型" prop="contractType" disabled>
        <el-select v-model="form.contractType" placeholder="" clearable filterable disabled>
          <el-option v-for="dict in contractTypeOptions" :key="dict.dictValue" :label="dict.dictLabel" :value="dict.dictValue"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="申请人" prop="applyUser">
        <el-select v-model="form.applyUser" placeholder="" clearable filterable disabled>
          <el-option v-for="dict in applyUserOptions" :key="dict.dictValue" :label="dict.dictLabel" :value="dict.dictValue"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="申请日期" prop="applyTime">
        <el-date-picker clearable size="small" v-model="form.applyTime" type="date" value-format="yyyy-MM-dd" placeholder="" disabled>
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
        <el-date-picker clearable size="small" v-model="form.auditTime" type="date" value-format="yyyy-MM-dd" placeholder="" disabled>
        </el-date-picker>
      </el-form-item>
      <el-form-item label="审核状态" prop="auditStatus">
        <el-select v-model="form.auditStatus" placeholder="" clearable filterable disabled>
          <el-option v-for="dict in auditStatusOptions" :key="dict.dictValue" :label="dict.dictLabel" :value="dict.dictValue"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="审核意见" prop="auditComment">
        <el-input v-model="form.auditComment" type="textarea" placeholder="" disabled />
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="form.remark" type="textarea" placeholder="" disabled />
      </el-form-item>
      <el-form-item label="单据状态" prop="billStatus">
        <el-select v-model="form.billStatus" placeholder="" clearable filterable disabled>
          <el-option v-for="dict in billStatusOptions" :key="dict.dictValue" :label="dict.dictLabel" :value="dict.dictValue"></el-option>
        </el-select>
      </el-form-item>
      <el-divider content-position="center">出库申请明细信息</el-divider>
      <el-table :data="wmsOutWarehouseApplyDetailList" :row-class-name="rowWmsOutWarehouseApplyDetailIndex" ref="wmsOutWarehouseApplyDetail">
        <el-table-column label="序号" align="center" prop="index" width="50" />
        <el-table-column label="物料" prop="materialName" width="300">
          <template slot-scope="scope">
            <el-form-item :prop="'wmsOutWarehouseApplyDetailList.'+scope.$index+'.materialName'" :rules="rules.materialName">
              <el-input v-model="scope.row.materialName" placeholder="请选择物料" disabled>
                <el-button v-no-more-click slot="append" @click="handleSelectMaterial(scope.$index)" disabled>选择</el-button>
              </el-input>
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column label="仓库" prop="warehouseId" width="150">
          <template slot-scope="scope">
            <el-form-item :prop="'wmsOutWarehouseApplyDetailList.'+scope.$index+'.warehouseId'">
              <el-select v-model="scope.row.warehouseId" placeholder="" disabled>
                <el-option v-for="dict in warehouseIdOptions" :key="dict.dictValue" :label="dict.dictLabel" :value="dict.dictValue"></el-option>
              </el-select>
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column label="库区" prop="whRegionId" width="150">
          <template slot-scope="scope">
            <el-form-item :prop="'wmsOutWarehouseApplyDetailList.'+scope.$index+'.whRegionId'">
              <el-select v-model="scope.row.whRegionId" placeholder="" disabled>
                <el-option v-for="dict in scope.row.whRegionOptions" :key="dict.id" :label="dict.name" :value="dict.id" />
              </el-select>
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column label="库位" prop="whLocationId" width="150">
          <template slot-scope="scope">
            <el-form-item :prop="'wmsOutWarehouseApplyDetailList.'+scope.$index+'.whLocationId'">
              <el-select v-model="scope.row.whLocationId" placeholder="" disabled>
                <el-option v-for="dict in scope.row.whLocationOptions" :key="dict.id" :label="dict.name" :value="dict.id" />
              </el-select>
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column label="出库数量" prop="outQuantity" width="150">
          <template slot-scope="scope">
            <el-form-item :prop="'wmsOutWarehouseApplyDetailList.'+scope.$index+'.outQuantity'" :rules="rules.outQuantity">
              <el-input v-model.number="scope.row.outQuantity" placeholder="请输入出库数量" disabled />
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
            <el-form-item :prop="'wmsOutWarehouseApplyDetailList.'+scope.$index+'.batchNo'">
              <el-input v-model="scope.row.batchNo" placeholder="" disabled />
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
import { getOutWarehouseApply } from "@/api/wmsApply/outWarehouseApply";
import { listWarehouseRegion } from "@/api/warehouse/warehouseRegion";
import { listWarehouseLocation } from "@/api/warehouse/warehouseLocation";
export default {
  name: "OutWarehouseDetail",
  components: {

  },
  props: {
    // 是否显示弹出层
    open: {
      type: Boolean,
      default: false,
    },
    //单据id
    billId: null,
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedWmsOutWarehouseApplyDetail: [],
      // 出库申请表格数据
      outWarehouseApplyList: [],
      // 出库申请明细表格数据
      wmsOutWarehouseApplyDetailList: [],
      // 弹出层标题
      title: "出库单明细",
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

      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
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
    handleOpen(){
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
      this.reset();
      this.$emit('onCancel')
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
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
        auditComment: null,
        remark: null,
        billStatus: null,
        wmsOutWarehouseApplyDetailList: [],
      };
      this.wmsOutWarehouseApplyDetailList = [];
      this.resetForm("form");
    },
    /** 获取出库单详情 */
    getInfo() {
      getOutWarehouseApply(this.billId).then(response => {
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
        this.title = "修改出库申请";
      });
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
    /** 单选框选中数据 */
    handleWmsOutWarehouseApplyDetailSelectionChange(selection) {
      if (selection.length > 1) {
        this.$refs.wmsOutWarehouseApplyDetail.clearSelection();
        this.$refs.wmsOutWarehouseApplyDetail.toggleRowSelection(selection.pop());
      } else {
        this.checkedWmsOutWarehouseApplyDetail = selection;
      }
    },
  }
}
</script>