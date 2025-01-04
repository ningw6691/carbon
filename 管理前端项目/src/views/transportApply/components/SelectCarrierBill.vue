<template>
  <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body @open="handleOpen" :close-on-click-modal="false" :show-close="false">
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
      <el-form-item>
        <el-button v-no-more-click type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button v-no-more-click icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button v-no-more-click type="warning" plain icon="el-icon-close" size="mini" @click="handleCancel">取消</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="carrierApplyList">
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="单据号" align="center" prop="serialNo" />
      <el-table-column label="出库单号" align="center" prop="outWarehouseNo" width="150">
      </el-table-column>
      <el-table-column label="发货单号" align="center" prop="saleDeliveryNo" width="150" />
      <el-table-column label="客户" align="center" prop="customerId" :formatter="customerIdFormat" />
      <el-table-column label="收货地址" align="center" prop="shippingAddress" />
      <el-table-column label="联系人" align="center" prop="contact" />
      <el-table-column label="联系电话" align="center" prop="contactTel" />
      <el-table-column label="交货日期" align="center" prop="deliveryDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.deliveryDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="承运人" align="center" prop="carrierUser" :formatter="carrierUserFormat" />
      <el-table-column label="运输距离" align="center" prop="distance">
        <template slot-scope="scope">
          <span>{{scope.row.distance}}公里</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right">
        <template slot-scope="scope">
          <el-button v-no-more-click size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)">详情</el-button>
          <el-button v-no-more-click size="mini" type="text" icon="el-icon-check" @click="handleSelect(scope.row)">选择</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <!-- 添加或修改承运申请对话框 -->
    <el-dialog :title="title" :visible.sync="detailOpen" width="1000px" append-to-body>
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
        <el-table-column label="交货日期" align="center" prop="deliveryDate" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.deliveryDate, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
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
          <el-input v-model="form.distance" placeholder="" disabled>
            <template slot="append">
              公里
            </template>
          </el-input>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="" disabled />
        </el-form-item>
        <el-divider content-position="center">产品承运单明细信息</el-divider>
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
                <el-select v-model="scope.row.whLocationId" placeholder="">
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
    <out-warehouse-detail :open="outBillOpen" :billId="outBillId" @onCancel="handleCancelOutWh"></out-warehouse-detail>
  </el-dialog>
</template>

<script>
import { listCarrierApply, getCarrierApply } from "@/api/transportApply/carrierApply";
import { listWarehouseRegion } from "@/api/warehouse/warehouseRegion";
import { listWarehouseLocation } from "@/api/warehouse/warehouseLocation";
import OutWarehouseDetail from '@/views/wmsApply/components/OutWarehouseDetail.vue';
export default {
  name: "CarrierBillSelect",
  components:
  {
    OutWarehouseDetail
  },
  props: {
    // 是否显示弹出层
    open: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      material: null,
      currentRowIndex: null,
      outBillId: null,
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
      title: "选择承运单",
      // 是否显示弹出层
      detailOpen: false,
      outBillOpen: false,
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
        auditStatus: '1',
        outWarehouseId: null,
        outWarehouseNo: null,
        saleDeliveryId: null,
        saleDeliveryNo: null,
        hasDelivery: '1',
      },
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
    handleSelect(row) {
      this.$emit('onSelected', row);
    },
    handleOpen() {
      this.getList();
    },
    handleCancel() {
      this.$emit('onCancel');
    },
    handleCancelOutWh() {
      this.outBillOpen = false;
    },
    handleOpenOutWhDetail(outWhId) {
      this.outBillId = outWhId;
      this.outBillOpen = true;
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
      listCarrierApply(this.queryParams).then(response => {
        this.carrierApplyList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 客户字典翻译
    customerIdFormat(row, column) {
      return this.selectDictLabel(this.customerIdOptions, row.customerId);
    },
    // 承运人字典翻译
    carrierUserFormat(row, column) {
      return this.selectDictLabel(this.carrierUserOptions, row.carrierUser);
    },
    // 取消按钮
    cancel() {
      this.reset();
      this.detailOpen = false;
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
        saleDeliveryId: null,
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
    /** 修改按钮操作 */
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
        this.title = "查看承运申请";
        console.log('form', this.form)
        this.detailOpen = true;
      });
    },
    /** 产品承运申请明细序号 */
    rowWmsCarrierApplyDetailIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
  }
};
</script>
