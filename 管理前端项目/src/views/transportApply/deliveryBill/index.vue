<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="单据号" prop="serialNo">
        <el-input v-model="queryParams.serialNo" placeholder="请输入单据号" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="调度单号" prop="dispatchBillNo">
        <el-input v-model="queryParams.dispatchBillNo" placeholder="请输入调度单号" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="车牌号" prop="plateNo">
        <el-input v-model="queryParams.plateNo" placeholder="请输入车牌号" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="配送状态" prop="billStatus">
        <el-select v-model="queryParams.billStatus" placeholder="请选择配送状态" clearable filterable size="small">
          <el-option v-for="dict in billStatusOptions" :key="dict.dictValue" :label="dict.dictLabel"
            :value="dict.dictValue" />
        </el-select>
      </el-form-item>
      <el-form-item label="配送时间">
        <el-date-picker v-model="daterangeDeliveryTime" size="small" style="width: 240px" value-format="yyyy-MM-dd"
          type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="到达时间">
        <el-date-picker v-model="daterangeArriveTime" size="small" style="width: 240px" value-format="yyyy-MM-dd"
          type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="客户" prop="customerId">
        <el-select v-model="queryParams.customerId" placeholder="请选择客户" clearable filterable size="small">
          <el-option v-for="dict in customerIdOptions" :key="dict.dictValue" :label="dict.dictLabel"
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
          v-hasPermi="['transportApply:deliveryBill:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-no-more-click type="success" plain icon="el-icon-edit" size="mini" :disabled="single"
          @click="handleUpdate" v-hasPermi="['transportApply:deliveryBill:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-no-more-click type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple"
          @click="handleDelete" v-hasPermi="['transportApply:deliveryBill:remove']">删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="deliveryBillList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="单据号" align="center" prop="serialNo" width="150" />
      <el-table-column label="调度单号" align="center" prop="dispatchBillNo" width="150" />
      <el-table-column label="车牌号" align="center" prop="plateNo" />
      <el-table-column label="司机" align="center" prop="driver" />
      <el-table-column label="司机联系电话" align="center" prop="contactTel" width="150" />
      <el-table-column label="配送状态" align="center" prop="billStatus" :formatter="billStatusFormat" />
      <el-table-column label="配送时间" align="center" prop="deliveryTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.deliveryTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="到达时间" align="center" prop="arriveTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.arriveTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="客户" align="center" prop="customerId" :formatter="customerIdFormat" />
      <el-table-column label="客户联系人" align="center" prop="customerContact" width="150" />
      <el-table-column label="客户联系电话" align="center" prop="customerContactTel" width="150" />
      <el-table-column label="配送地址" align="center" prop="deliveryAddress" width="150" />
      <el-table-column label="发货地址" align="center" prop="shipAddress" width="150" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-no-more-click v-if="scope.row.billStatus == '0'" size="mini" type="text" icon="el-icon-truck"
            @click="handleUpdateStatus(scope.row, '2')" v-hasPermi="['transportApply:deliveryBill:edit']">配送</el-button>
          <el-button v-no-more-click v-if="scope.row.billStatus == '2'" size="mini" type="text" icon="el-icon-box"
            @click="handleUpdateStatus(scope.row, '1')" v-hasPermi="['transportApply:deliveryBill:edit']">收货</el-button>
          <el-button v-no-more-click size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['transportApply:deliveryBill:edit']">修改</el-button>
          <el-button v-no-more-click v-if="scope.row.billStatus == '0'" size="mini" type="text" icon="el-icon-delete"
            @click="handleDelete(scope.row)" v-hasPermi="['transportApply:deliveryBill:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改配送管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px" :inline="true">
        <el-form-item label="单据号" prop="serialNo">
          <el-input v-model="form.serialNo" placeholder="" disabled />
        </el-form-item>
        <el-form-item label="调度单号" prop="dispatchBillNo">
          <el-input v-model="form.dispatchBillNo" placeholder="请选择调度单" disabled>
            <el-button v-no-more-click slot="append" @click="handleSelectDispatch"
              :disabled="form.billStatus == '0' ? false : true">选择</el-button>
          </el-input>
        </el-form-item>
        <el-form-item label="车牌号" prop="plateNo">
          <el-select v-model="form.plateNo" placeholder="请选择车牌号" clearable filterable @change="handleChangePlateNo"
            :disabled="form.billStatus == '0' ? false : true">
            <el-option v-for="dict in truckOptions" :key="dict.plateNo" :label="dict.plateNo"
              :value="dict.plateNo"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="司机" prop="driver">
          <el-input v-model="form.driver" placeholder="" disabled />
        </el-form-item>
        <el-form-item label="司机联系电话" prop="contactTel">
          <el-input v-model="form.contactTel" placeholder="" disabled />
        </el-form-item>
        <el-form-item label="客户" prop="customerId">
          <el-select v-model="form.customerId" placeholder="" clearable filterable disabled>
            <el-option v-for="dict in customerIdOptions" :key="dict.dictValue" :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="客户联系人" prop="customerContact">
          <el-input v-model="form.customerContact" placeholder="" disabled />
        </el-form-item>
        <el-form-item label="客户联系电话" prop="customerContactTel">
          <el-input v-model="form.customerContactTel" placeholder="" disabled />
        </el-form-item>
        <el-form-item label="配送状态" prop="billStatus">
          <el-select v-model="form.billStatus" placeholder="" clearable filterable disabled>
            <el-option v-for="dict in billStatusOptions" :key="dict.dictValue" :label="dict.dictLabel"
              :value="dict.dictValue"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="发货地址" prop="shipAddress">
          <el-input v-model="form.shipAddress" placeholder="请输入发货地址" :disabled="form.billStatus == '0' ? false : true" />
        </el-form-item>
        <el-form-item label="配送时间" prop="deliveryTime">
          <el-date-picker clearable size="small" v-model="form.deliveryTime" type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择配送时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="配送地址" prop="deliveryAddress">
          <el-input v-model="form.deliveryAddress" placeholder="请输入配送地址" :disabled="form.billStatus == '0' ? false : true" />
        </el-form-item>
        <el-form-item label="到达时间" prop="arriveTime">
          <el-date-picker clearable size="small" v-model="form.arriveTime" type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss" placeholder="" disabled>
          </el-date-picker>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入备注"
            :disabled="form.billStatus == '0' ? false : true" />
        </el-form-item>
        <el-divider content-position="center">配送单明细信息</el-divider>
        <el-table :data="wmsDeliveryBillDetailList" :row-class-name="rowWmsDeliveryBillDetailIndex"
          ref="wmsDeliveryBillDetail">
          <el-table-column label="序号" align="center" prop="index" width="50" />
          <el-table-column label="物料" prop="materialName" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'wmsDeliveryBillDetailList.' + scope.$index + '.materialName'" :rules="rules.materialId">
                <el-input v-model="scope.row.materialName" placeholder="" disabled />
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="生产批号" prop="batchNo" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'wmsDeliveryBillDetailList.' + scope.$index + '.batchNo'" :rules="rules.batchNo">
                <el-input v-model="scope.row.batchNo" placeholder="" disabled />
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="仓库" prop="warehouseId" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'wmsDeliveryBillDetailList.' + scope.$index + '.warehouseId'" :rules="rules.warehouseId">
                <el-select v-model="scope.row.warehouseId" placeholder="" disabled>
                  <el-option v-for="dict in warehouseIdOptions" :key="dict.dictValue" :label="dict.dictLabel"
                    :value="dict.dictValue"></el-option>
                </el-select>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="库区" prop="whRegionId" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'wmsDeliveryBillDetailList.' + scope.$index + '.whRegionId'" :rules="rules.whRegionId">
                <el-select v-model="scope.row.whRegionId" placeholder="" disabled>
                  <el-option v-for="dict in scope.row.whRegionOptions" :key="dict.id" :label="dict.name"
                    :value="dict.id" />
                </el-select>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="库位" prop="whLocationId" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'wmsDeliveryBillDetailList.' + scope.$index + '.whLocationId'"
                :rules="rules.whLocationId">
                <el-select v-model="scope.row.whLocationId" placeholder="" disabled>
                  <el-option v-for="dict in scope.row.whLocationOptions" :key="dict.id" :label="dict.name"
                    :value="dict.id" />
                </el-select>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="发货数量" prop="deliveryQuantity" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'wmsDeliveryBillDetailList.' + scope.$index + '.deliveryQuantity'"
                :rules="rules.deliveryQuantity">
                <el-input v-model="scope.row.deliveryQuantity" placeholder="请输入发货数量"
                  :disabled="form.billStatus == '0' ? false : true" />
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="单价" prop="price" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'wmsDeliveryBillDetailList.' + scope.$index + '.price'" :rules="rules.price">
                <el-input v-model="scope.row.price" placeholder="" disabled />
              </el-form-item>
            </template>
          </el-table-column>

          <el-table-column label="总价" prop="totalPrice" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'wmsDeliveryBillDetailList.' + scope.$index + '.totalPrice'" :rules="rules.totalPrice">
                {{ scope.row.price ? scope.row.deliveryQuantity * scope.row.price : '' }}
              </el-form-item>
            </template>
          </el-table-column>

          <el-table-column label="备注" prop="detailRemark" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'wmsDeliveryBillDetailList.' + scope.$index + '.detailRemark'"
                :rules="rules.detailRemark">
                <el-input v-model="scope.row.detailRemark" placeholder="请输入备注"
                  :disabled="form.billStatus == '0' ? false : true" />
              </el-form-item>
            </template>
          </el-table-column>
        </el-table>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button v-no-more-click v-if="form.billStatus == '0'" type="primary" @click="submitForm">确 定</el-button>
        <el-button v-no-more-click @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <select-dispatch-bill :open="dispatchSelectOpen" @onSelected="handleDispatchSelected"
      @onCancel="handleDispatchCancel"></select-dispatch-bill>
  </div>
</template>

<script>
import { listDeliveryBill, getDeliveryBill, delDeliveryBill, addDeliveryBill, updateDeliveryBill, exportDeliveryBill, updateDeliveryBillStatus } from "@/api/transportApply/deliveryBill";
import { getDispatchBill } from "@/api/transportApply/dispatchBill";
import { getCarrierApply } from "@/api/transportApply/carrierApply";
import { listWarehouseRegion } from "@/api/warehouse/warehouseRegion";
import { listWarehouseLocation } from "@/api/warehouse/warehouseLocation";
import SelectDispatchBill from '../components/SelectDispatchBill.vue';

export default {
  name: "DeliveryBill",
  components: {
    SelectDispatchBill
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedWmsDeliveryBillDetail: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 配送管理表格数据
      deliveryBillList: [],
      // 配送单明细表格数据
      wmsDeliveryBillDetailList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      dispatchSelectOpen: false,
      // 配送状态字典
      billStatusOptions: [],
      // 配送时间时间范围
      daterangeDeliveryTime: [],
      // 到达时间时间范围
      daterangeArriveTime: [],
      // 客户字典
      customerIdOptions: [],
      // 仓库字典
      warehouseIdOptions: [],
      truckOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        serialNo: null,
        dispatchBillId: null,
        dispatchBillNo: null,
        plateNo: null,
        billStatus: null,
        deliveryTime: null,
        arriveTime: null,
        customerId: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        plateNo: [
          { required: true, message: "车牌号不能为空", trigger: "blur" }
        ],
        deliveryTime: [
          { required: true, message: "配送时间不能为空", trigger: "blur" }
        ],
        deliveryAddress: [
          { required: true, message: "配送地址不能为空", trigger: "blur" },
          { max: 100, message: "配送地址最多100个字符", trigger: "blur" }
        ],
        shipAddress: [
          { max: 100, message: "发货地址最多100个字符", trigger: "blur" }
        ],
        remark: [
          { max: 100, message: "备注最多100个字符", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("delivery_status").then(response => {
      this.billStatusOptions = response.data;
    });
    this.getQueryData("query_customer").then(res => {
      this.customerIdOptions = res.data;
    });
    this.getQueryData("query_warehouse").then(res => {
      this.warehouseIdOptions = res.data.map(item => ({
        dictValue: item.dictValue * 1,
        dictLabel: item.dictLabel,
      }));
    });
  },
  watch: {
    'wmsDeliveryBillDetailList': {
      handler(newVal, oldVal) {
        this.form.wmsDeliveryBillDetailList = newVal;
      },
      immediate: true,
      deep: true,
    }
  },
  methods: {
    //修改单据状态
    handleUpdateStatus(row, status) {
      let params = {
        id: row.id,
        billStatus: status,
      };
      updateDeliveryBillStatus(params).then(res => {
        if (status == '1') {
          this.msgSuccess("收货成功");
        }
        if (status == '2') {
          this.msgSuccess("开始配送");
        }
        this.getList();
      });
    },
    //选择车牌号处理函数
    handleChangePlateNo(val) {
      let obj = this.truckOptions.filter(truck => truck.plateNo == val);
      this.form.driver = obj[0].driver;
      this.form.contactTel = obj[0].contactTel;
    },
    //获取库区选项
    getWhRegionOpts(val, index) {
      let queryParams = {
        warehouseId: val,
      }
      listWarehouseRegion(queryParams).then(res => {
        this.wmsDeliveryBillDetailList[index].whRegionOptions = res.rows;
      });
    },
    //获取库位选项
    getWhLocationOpts(val, index) {
      let queryParams = {
        regionId: val,
      }
      listWarehouseLocation(queryParams).then(res => {
        this.wmsDeliveryBillDetailList[index].whLocationOptions = res.rows;
      })
    },
    handleSelectDispatch() {
      this.dispatchSelectOpen = true;
    },
    handleDispatchSelected(row) {
      this.form.dispatchBillId = row.id;
      this.form.dispatchBillNo = row.serialNo;
      this.form.customerId = row.customerId;
      this.form.customerContact = row.customerContact;
      this.form.customerContactTel = row.customerContactTel;
      this.form.deliveryAddress = row.address;
      this.dispatchSelectOpen = false;
      getDispatchBill(this.form.dispatchBillId).then(response => {
        let dispatchInfo = response.data;
        let dispatchDetail = dispatchInfo.wmsDispatchBillDetailList;
        this.truckOptions = [];
        dispatchDetail.forEach(truck => {
          this.truckOptions.push(truck);
        });
        this.wmsDeliveryBillDetailList = [];
        //获取承运单
        (dispatchInfo.carrierApplyId).then(response => {
          response.data.wmsCarrierApplyDetailList.forEach(item => {
            let obj = {
              whRegionOptions: [],
              whLocationOptions: [],
            };
            let detail = Object.assign(obj, item);
            this.wmsDeliveryBillDetailList.push(detail);
          })
          this.wmsDeliveryBillDetailList.forEach((item, index) => {
            this.getWhRegionOpts(item.warehouseId, index);
            this.getWhLocationOpts(item.whRegionId, index);
          })
        });
      });
    },
    handleDispatchCancel() {
      this.dispatchSelectOpen = false;
    },
    /** 查询配送管理列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeDeliveryTime && '' != this.daterangeDeliveryTime) {
        this.queryParams.params["beginDeliveryTime"] = this.daterangeDeliveryTime[0];
        this.queryParams.params["endDeliveryTime"] = this.daterangeDeliveryTime[1];
      }
      if (null != this.daterangeArriveTime && '' != this.daterangeArriveTime) {
        this.queryParams.params["beginArriveTime"] = this.daterangeArriveTime[0];
        this.queryParams.params["endArriveTime"] = this.daterangeArriveTime[1];
      }
      listDeliveryBill(this.queryParams).then(response => {
        this.deliveryBillList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 配送状态字典翻译
    billStatusFormat(row, column) {
      return this.selectDictLabel(this.billStatusOptions, row.billStatus);
    },
    // 客户字典翻译
    customerIdFormat(row, column) {
      return this.selectDictLabel(this.customerIdOptions, row.customerId);
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
        dispatchBillId: null,
        dispatchBillNo: null,
        plateNo: null,
        driver: null,
        contactTel: null,
        billStatus: '0',
        deliveryTime: null,
        arriveTime: null,
        customerId: null,
        customerContact: null,
        customerContactTel: null,
        deliveryAddress: null,
        shipAddress: null,
        remark: null,
        wmsDeliveryBillDetailList: [],
      };
      this.wmsDeliveryBillDetailList = [];
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.daterangeDeliveryTime = [];
      this.daterangeArriveTime = [];
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
      this.title = "添加配送单";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getDeliveryBill(id).then(response => {
        this.form = response.data;
        this.wmsDeliveryBillDetailList = response.data.wmsDeliveryBillDetailList;
        this.open = true;
        this.title = "修改配送单";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {

          if (this.form.id != null) {
            updateDeliveryBill(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addDeliveryBill(this.form).then(response => {
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
      this.$confirm('是否确认删除配送单编号为"' + ids + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delDeliveryBill(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    /** 配送单明细序号 */
    rowWmsDeliveryBillDetailIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有配送管理数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return exportDeliveryBill(queryParams);
      }).then(response => {
        this.download(response.msg);
      })
    }
  }
};
</script>
