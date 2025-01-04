<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="90px">
      <el-form-item label="单据号" prop="serialNo">
        <el-input v-model="queryParams.serialNo" placeholder="请输入单据号" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="承运单号" prop="carrierNo">
        <el-input v-model="queryParams.carrierNo" placeholder="请输入承运单号" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button v-no-more-click type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button v-no-more-click icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button v-no-more-click type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['transportApply:dispatchBill:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-no-more-click type="success" plain icon="el-icon-edit" size="mini" :disabled="single"
          @click="handleUpdate" v-hasPermi="['transportApply:dispatchBill:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-no-more-click type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple"
          @click="handleDelete" v-hasPermi="['transportApply:dispatchBill:remove']">删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="dispatchBillList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="单据号" align="center" prop="serialNo" width="150" />
      <el-table-column label="承运单号" align="center" prop="carrierNo" width="150">
      </el-table-column>
      <el-table-column label="客户" align="center" prop="customerId" :formatter="customerIdFormat" width="150" />
      <el-table-column label="交货日期" align="center" prop="deliveryDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.deliveryDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="收货地址" align="center" prop="address" />
      <el-table-column label="联系人" align="center" prop="customerContact" />
      <el-table-column label="联系电话" align="center" prop="customerContactTel" />
      <el-table-column label="承运人" align="center" prop="carrierUser" :formatter="carrierUserFormat" />
      <el-table-column label="运输距离" align="center" prop="distance">
        <template slot-scope="scope">
          <span>{{ scope.row.distance }}公里</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-no-more-click size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['transportApply:dispatchBill:edit']">修改</el-button>
          <el-button v-no-more-click size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['transportApply:dispatchBill:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改车辆调度单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px" :inline="true">
        <el-form-item label="单据号" prop="serialNo">
          <el-input v-model="form.serialNo" placeholder="" disabled size="small" />
        </el-form-item>
        <el-form-item label="承运单号" prop="carrierNo">
          <el-input v-model="form.carrierNo" placeholder="" disabled size="small">
            <el-button v-no-more-click slot="append" @click="handleSelectCarrier">选择</el-button>
          </el-input>
        </el-form-item>
        <el-form-item label="出库单号" prop="outWhNo">
          <el-input v-model="form.outWhNo" placeholder="" disabled size="small" />
        </el-form-item>
        <el-form-item label="客户" prop="customerId">
          <el-select v-model="form.customerId" placeholder="" clearable filterable disabled>
            <el-option v-for="dict in customerIdOptions" :key="dict.dictValue" :label="dict.dictLabel"
              :value="dict.dictValue"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="收货地址" prop="address">
          <el-input v-model="form.address" placeholder="" disabled size="small" />
        </el-form-item>
        <el-form-item label="联系人" prop="customerContact">
          <el-input v-model="form.customerContact" placeholder="" disabled size="small" />
        </el-form-item>
        <el-form-item label="联系电话" prop="customerContactTel">
          <el-input v-model="form.customerContactTel" placeholder="" disabled size="small" />
        </el-form-item>
        <el-form-item label="交货日期" prop="deliveryDate">
          <el-date-picker clearable size="small" v-model="form.deliveryDate" type="date" value-format="yyyy-MM-dd"
            placeholder="" disabled>
          </el-date-picker>
        </el-form-item>
        <el-form-item label="承运人" prop="carrierUser">
          <el-select v-model="form.carrierUser" placeholder="" clearable filterable disabled>
            <el-option v-for="dict in carrierUserOptions" :key="dict.dictValue" :label="dict.dictLabel"
              :value="dict.dictValue"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="运输距离" prop="distance">
          <el-input v-model="form.distance" placeholder="" disabled size="small">
            <template slot="append">
              公里
            </template>
          </el-input>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" size="small" />
        </el-form-item>
        <el-divider content-position="center">车辆调度明细</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button v-no-more-click type="primary" icon="el-icon-plus" size="mini"
              @click="handleAddWmsDispatchBillDetail">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button v-no-more-click type="danger" icon="el-icon-delete" size="mini"
              @click="handleDeleteWmsDispatchBillDetail">删除</el-button>
          </el-col>
        </el-row>
        <el-table :data="wmsDispatchBillDetailList" :row-class-name="rowWmsDispatchBillDetailIndex"
          @selection-change="handleWmsDispatchBillDetailSelectionChange" ref="wmsDispatchBillDetail">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center" prop="index" width="50" />
          <el-table-column label="车牌号" prop="plateNo" width="200">
            <template slot-scope="scope">
              <el-form-item :prop="'wmsDispatchBillDetailList.' + scope.$index + '.plateNo'" :rules="rules.plateNo">
                <el-input v-model="scope.row.plateNo" placeholder="请输入车牌号" />
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="驾驶员" prop="driver" width="200">
            <template slot-scope="scope">
              <el-form-item :prop="'wmsDispatchBillDetailList.' + scope.$index + '.driver'" :rules="rules.driver">
                <el-input v-model="scope.row.driver" placeholder="请输入驾驶员" />
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="联系电话" prop="contactTel" width="200">
            <template slot-scope="scope">
              <el-form-item :prop="'wmsDispatchBillDetailList.' + scope.$index + '.contactTel'"
                :rules="rules.contactTel">
                <el-input v-model="scope.row.contactTel" placeholder="请输入联系电话" />
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="载重" prop="truckLoad" width="200">
            <template slot-scope="scope">
              <el-form-item :prop="'wmsDispatchBillDetailList.' + scope.$index + '.truckLoad'" :rules="rules.truckLoad">
                <el-input v-model.number="scope.row.truckLoad" placeholder="请输入载重">
                  <template slot="append">吨</template>
                </el-input>
              </el-form-item>
            </template>
          </el-table-column>
        </el-table>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button v-no-more-click type="primary" @click="submitForm">确 定</el-button>
        <el-button v-no-more-click @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <select-carrier-bill :open="seletCarrierBillOpen" @onCancel="handleCarrierCancel"
      @onSelected="handleCarrierSelect"></select-carrier-bill>
    <carrier-bill-detail :open="carrierBillOpen" :billId="carrierBillId"
      @onCancel="handleCarrierDetailCancel"></carrier-bill-detail>
  </div>
</template>

<script>
import { listDispatchBill, getDispatchBill, delDispatchBill, addDispatchBill, updateDispatchBill, exportDispatchBill } from "@/api/transportApply/dispatchBill";
import SelectCarrierBill from '../components/SelectCarrierBill.vue';
import CarrierBillDetail from '../components/CarrierBillDetail.vue';

export default {
  name: "DispatchBill",
  components: {
    SelectCarrierBill,
    CarrierBillDetail
  },
  data() {
    return {
      carrierBillId: null,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 客户字典
      customerIdOptions: [],
      // 承运人字典
      carrierUserOptions: [],
      // 子表选中数据
      checkedWmsDispatchBillDetail: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 车辆调度单表格数据
      dispatchBillList: [],
      // 车辆调度单明细表格数据
      wmsDispatchBillDetailList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      seletCarrierBillOpen: false,
      carrierBillOpen: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        serialNo: null,
        carrierNo: null,
        outWarehouseId: null,
        orderNo: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        carrierNo: [
          { required: true, message: "承运单号不能为空", trigger: "blur" }
        ],
        plateNo: [
          { required: true, message: "车牌号不能为空", trigger: "blur" }
        ],
        driver: [
          { required: true, message: "驾驶员不能为空", trigger: "blur" }
        ],
        contactTel: [
          { required: true, message: "联系电话不能为空", trigger: "blur" }
        ],
        truckLoad: [
          { type: 'number', min: 1, max: 65535, message: '载重必须为数字值且大小要在1到65535' },
        ],
        remark: [
          { max: 100, message: "备注最多100个字符", trigger: "blur" }
        ],
        plateNo: [
          { max: 20, message: "车牌号最多20个字符", trigger: "blur" }
        ],
        driver: [
          { max: 10, message: "驾驶员最多10个字符", trigger: "blur" }
        ],
        contactTel: [
          { max: 20, message: "联系电话最多20个字符", trigger: "blur" }
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
      this.carrierUserOptions = res.data;
    });
  },
  watch: {
    'wmsDispatchBillDetailList': {
      handler(newVal, oldVal) {
        this.form.wmsDispatchBillDetailList = newVal;
      },
      immediate: true,
      deep: true,
    }
  },
  methods: {
    //点击承运单号处理函数
    handleOpenCarrierBill(id) {
      this.carrierBillId = id;
      this.carrierBillOpen = true;
    },
    handleCarrierDetailCancel() {
      this.carrierBillOpen = false;
    },
    handleCarrierSelect(row) {
      this.form.carrierApplyId = row.id;
      this.form.carrierNo = row.serialNo;
      this.form.outWhNo = row.outWarehouseNo;
      this.form.outWarehouseId = row.outWarehouseId;
      this.form.customerId = row.customerId;
      this.form.address = row.shippingAddress;
      this.form.customerContact = row.contact;
      this.form.customerContactTel = row.contactTel;
      this.form.carrierUser = row.carrierUser;
      this.form.distance = row.distance;
      this.form.deliveryDate = row.deliveryDate;
      this.seletCarrierBillOpen = false;
    },
    handleCarrierCancel() {
      this.seletCarrierBillOpen = false;
    },
    //选择承运单
    handleSelectCarrier() {
      this.seletCarrierBillOpen = true;
    },
    // 客户字典翻译
    customerIdFormat(row, column) {
      return this.selectDictLabel(this.customerIdOptions, row.customerId);
    },
    // 承运人字典翻译
    carrierUserFormat(row, column) {
      return this.selectDictLabel(this.carrierUserOptions, row.carrierUser);
    },
    /** 查询车辆调度单列表 */
    getList() {
      this.loading = true;
      listDispatchBill(this.queryParams).then(response => {
        this.dispatchBillList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
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
        carrierApplyId: null,
        outWhNo: null,
        outWarehouseId: null,
        saleOrderId: null,
        saleOrderNo: null,
        customerId: null,
        address: null,
        customerContact: null,
        customerContactTel: null,
        carrierUser: null,
        distance: null,
        remark: null,
        deliveryDate: null,
        wmsDispatchBillDetailList: [],
      };
      this.wmsDispatchBillDetailList = [];
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
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
      this.title = "添加车辆调度单";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getDispatchBill(id).then(response => {
        this.form = response.data;
        this.wmsDispatchBillDetailList = response.data.wmsDispatchBillDetailList;
        this.open = true;
        this.title = "修改车辆调度单";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {

          if (this.form.id != null) {
            updateDispatchBill(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addDispatchBill(this.form).then(response => {
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
      this.$confirm('是否确认删除车辆调度单编号为"' + ids + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delDispatchBill(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    /** 车辆调度单明细序号 */
    rowWmsDispatchBillDetailIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
    /** 车辆调度单明细添加按钮操作 */
    handleAddWmsDispatchBillDetail() {
      let obj = {};
      obj.plateNo = "";
      obj.driver = "";
      obj.contactTel = "";
      obj.truckLoad = "";
      obj.remark = "";
      this.wmsDispatchBillDetailList.push(obj);
    },
    /** 车辆调度单明细删除按钮操作 */
    handleDeleteWmsDispatchBillDetail() {
      if (this.checkedWmsDispatchBillDetail.length == 0) {
        this.$alert("请先选择要删除的车辆调度单明细数据", "提示", { confirmButtonText: "确定", });
      } else {
        this.wmsDispatchBillDetailList.splice(this.checkedWmsDispatchBillDetail[0].index - 1, 1);
      }
    },
    /** 单选框选中数据 */
    handleWmsDispatchBillDetailSelectionChange(selection) {
      if (selection.length > 1) {
        this.$refs.wmsDispatchBillDetail.clearSelection();
        this.$refs.wmsDispatchBillDetail.toggleRowSelection(selection.pop());
      } else {
        this.checkedWmsDispatchBillDetail = selection;
      }
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有车辆调度单数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return exportDispatchBill(queryParams);
      }).then(response => {
        this.download(response.msg);
      })
    }
  }
};
</script>
