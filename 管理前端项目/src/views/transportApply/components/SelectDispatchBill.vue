<template>
  <el-dialog
    :title="title"
    :visible.sync="open"
    width="1000px"
    append-to-body
    @open="handleOpen"
    :close-on-click-modal="false"
    :show-close="false"
  >
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="90px"
    >
      <el-form-item label="单据号" prop="serialNo">
        <el-input
          v-model="queryParams.serialNo"
          placeholder="请输入单据号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="承运单号" prop="carrierNo">
        <el-input
          v-model="queryParams.carrierNo"
          placeholder="请输入承运单号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button
          v-no-more-click
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >搜索</el-button
        >
        <el-button
          v-no-more-click
          icon="el-icon-refresh"
          size="mini"
          @click="resetQuery"
          >重置</el-button
        >
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          v-no-more-click
          type="warning"
          plain
          icon="el-icon-close"
          size="mini"
          @click="handleCancel"
          >取消</el-button
        >
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="dispatchBillList">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column
        label="单据号"
        align="center"
        prop="serialNo"
        width="150"
      />
      <el-table-column
        label="承运单号"
        align="center"
        prop="carrierNo"
        width="150"
      >
      </el-table-column>
      <el-table-column
        label="客户"
        align="center"
        prop="customerId"
        :formatter="customerIdFormat"
        width="150"
      />
      <el-table-column
        label="交货日期"
        align="center"
        prop="deliveryDate"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.deliveryDate, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="收货地址" align="center" prop="address" />
      <el-table-column label="联系人" align="center" prop="customerContact" />
      <el-table-column
        label="联系电话"
        align="center"
        prop="customerContactTel"
      />
      <el-table-column
        label="承运人"
        align="center"
        prop="carrierUser"
        :formatter="carrierUserFormat"
      />
      <el-table-column label="运输距离" align="center" prop="distance">
        <template slot-scope="scope">
          <span>{{ scope.row.distance }}公里</span>
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
        fixed="right"
      >
        <template slot-scope="scope">
          <el-button
            v-no-more-click
            size="mini"
            type="text"
            icon="el-icon-check"
            @click="handleSelect(scope.row)"
            >选择</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
    <carrier-bill-detail
      :open="carrierBillOpen"
      :billId="carrierBillId"
      @onCancel="handleCarrierDetailCancel"
    ></carrier-bill-detail>
  </el-dialog>
</template>
<script>
import { listDispatchBill } from "@/api/transportApply/dispatchBill";
import CarrierBillDetail from "../components/CarrierBillDetail.vue";
export default {
  name: "SelectDispatchBill",
  components: {
    CarrierBillDetail,
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
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 车辆调度单表格数据
      dispatchBillList: [],
      // 弹出层标题
      title: "选择调度单",
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
    };
  },
  created() {
    this.getQueryData("query_customer").then((res) => {
      this.customerIdOptions = res.data;
    });
    this.getQueryData("query_user").then((res) => {
      this.carrierUserOptions = res.data;
    });
  },
  methods: {
    handleSelect(row) {
      this.$emit("onSelected", row);
    },
    handleOpen() {
      this.getList();
    },
    handleCancel() {
      this.$emit("onCancel");
    },
    //点击承运单号处理函数
    handleOpenCarrierBill(id) {
      this.carrierBillId = id;
      this.carrierBillOpen = true;
    },
    handleCarrierDetailCancel() {
      this.carrierBillOpen = false;
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
      listDispatchBill(this.queryParams).then((response) => {
        this.dispatchBillList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {},
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
  },
};
</script>