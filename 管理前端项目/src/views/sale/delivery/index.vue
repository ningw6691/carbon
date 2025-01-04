<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="发货编号" prop="deliveryNo">
        <el-input v-model="queryParams.deliveryNo" placeholder="请输入发货编号" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="合同编号" prop="contractNo">
        <el-input v-model="queryParams.contractNo" placeholder="请输入合同编号" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="客户名称" prop="customerId">
        <el-select v-model="queryParams.customerId" placeholder="请选择客户" clearable filterable size="small">
          <el-option v-for="dict in customerIdOptions" :key="dict.dictValue" :label="dict.dictLabel"
            :value="dict.dictValue" />
        </el-select>
      </el-form-item>
      <el-form-item label="交货日期">
        <el-date-picker v-model="daterangeDeliveryDate" size="small" style="width: 240px" value-format="yyyy-MM-dd"
          type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="出库状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择出库状态" clearable filterable size="small">
          <el-option v-for="dict in statusOptions" :key="dict.dictValue" :label="dict.dictLabel"
            :value="dict.dictValue" />
        </el-select>
      </el-form-item>
      <el-form-item label="创建时间">
        <el-date-picker v-model="daterangeCreateTime" size="small" style="width: 240px" value-format="yyyy-MM-dd"
          type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button v-no-more-click type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button v-no-more-click icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="deliveryList">
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="发货编号" align="center" prop="deliveryNo" />
      <el-table-column label="合同编号" align="center" prop="contractNo" />
      <el-table-column label="客户名称" align="center" prop="customerName" />
      <el-table-column label="送货方式" align="center" prop="deliveryType" :formatter="deliveryTypeFormat" />
      <el-table-column label="交货日期" align="center" prop="deliveryDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.deliveryDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="收货地址" align="center" prop="address" />
      <el-table-column label="联系人" align="center" prop="contact" />
      <el-table-column label="联系电话" align="center" prop="contactTel" />
      <el-table-column label="出库状态" align="center" prop="status" :formatter="statusFormat" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-no-more-click size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['sale:delivery:edit']">修改</el-button>
          <el-button v-no-more-click size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['sale:delivery:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改销售发货单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px" :inline="true">
        <el-form-item label="发货编号" prop="deliveryNo">
          <el-input v-model="form.deliveryNo" placeholder="请输入发货编号" disabled />
        </el-form-item>
        <el-form-item label="合同编号" prop="contractNo">
          <el-input v-model="form.contractNo" placeholder="请输入合同编号" disabled />
        </el-form-item>
        <el-form-item label="客户名称" prop="customerName">
          <el-input v-model="form.customerName" placeholder="请输入客户名称" disabled />
        </el-form-item>
        <el-form-item label="送货方式" prop="deliveryType">
          <el-select v-model="form.deliveryType" placeholder="请选择送货方式" clearable filterable>
            <el-option v-for="dict in deliveryTypeOptions" :key="dict.dictValue" :label="dict.dictLabel"
              :value="dict.dictValue"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="交货日期" prop="deliveryDate">
          <el-date-picker clearable size="small" v-model="form.deliveryDate" type="date" value-format="yyyy-MM-dd"
            placeholder="选择交货日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="收货地址" prop="address">
          <el-input v-model="form.address" placeholder="请输入收货地址" />
        </el-form-item>
        <el-form-item label="联系人" prop="contact">
          <el-input v-model="form.contact" placeholder="请输入联系人" />
        </el-form-item>
        <el-form-item label="联系电话" prop="contactTel">
          <el-input v-model="form.contactTel" placeholder="请输入联系电话" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-divider content-position="center">发货单明细信息</el-divider>
        <!-- <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button v-no-more-click type="primary" icon="el-icon-plus" size="mini" @click="handleAddScmSaleDeliveryDetail">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button v-no-more-click type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteScmSaleDeliveryDetail">删除</el-button>
          </el-col>
        </el-row> -->
        <el-table :data="scmSaleDeliveryDetailList" :row-class-name="rowScmSaleDeliveryDetailIndex"
          ref="scmSaleDeliveryDetail">
          <!-- <el-table-column type="selection" width="50" align="center" /> -->
          <el-table-column label="序号" align="center" prop="index" width="50" />
          <el-table-column label="物料" prop="materialName" width="200"></el-table-column>
          <el-table-column label="型号" prop="materialModel"></el-table-column>
          <el-table-column label="规格" prop="materialSpecification"></el-table-column>
          <el-table-column label="单位" prop="materialUnit"></el-table-column>
          <el-table-column label="发货数量" prop="quantity" width="150"></el-table-column>
          <el-table-column label="备注" prop="detailRemark" width="150"></el-table-column>
        </el-table>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button v-no-more-click type="primary" @click="submitForm">确 定</el-button>
        <el-button v-no-more-click @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listDelivery,
  getDelivery,
  delDelivery,
  addDelivery,
  updateDelivery,
  exportDelivery
} from "@/api/sale/delivery";

export default {
  name: "Delivery",
  components: {},
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedScmSaleDeliveryDetail: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 销售发货单表格数据
      deliveryList: [],
      // 发货单明细表格数据
      scmSaleDeliveryDetailList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 送货方式字典
      deliveryTypeOptions: [],
      // 交货日期时间范围
      daterangeDeliveryDate: [],
      // 客户id字典
      customerIdOptions: [],
      // 创建人字典
      createByOptions: [],
      // 创建时间时间范围
      daterangeCreateTime: [],
      // 出库状态字典
      statusOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        deliveryNo: null,
        contractNo: null,
        customerId: null,
        deliveryType: null,
        deliveryDate: null,
        address: null,
        contact: null,
        contactTel: null,
        status: null,
        createBy: null,
        createTime: null,
        orderByColumn: 'create_time',
        isAsc: 'desc'
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        deliveryType: [{
          required: true,
          message: "送货方式不能为空",
          trigger: "change"
        }],
        deliveryDate: [{
          required: true,
          message: "交货日期不能为空",
          trigger: "blur"
        }],
        address: [{
          required: true,
          message: "收货地址不能为空",
          trigger: "blur"
        }],
        contact: [{
          required: true,
          message: "联系人不能为空",
          trigger: "blur"
        }],
        contactTel: [{
          required: true,
          message: "联系电话不能为空",
          trigger: "blur"
        }],
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("scm_out_warehouse_status").then(response => {
      this.statusOptions = response.data;
    });
    this.getDicts("scm_delivery_type").then(response => {
      this.deliveryTypeOptions = response.data;
    });
    this.getQueryData("query_user").then(res => {
      this.createByOptions = res.data;
    });
    this.getQueryData("query_customer").then(res => {
      this.customerIdOptions = res.data;
    });
  },
  watch: {
    'scmSaleDeliveryDetailList': {
      handler(newVal, oldVal) {
        this.form.scmSaleDeliveryDetailList = newVal;
      },
      immediate: true,
      deep: true,
    }
  },
  methods: {
    /** 查询销售发货单列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeDeliveryDate && '' != this.daterangeDeliveryDate) {
        this.queryParams.params["beginDeliveryDate"] = this.daterangeDeliveryDate[0];
        this.queryParams.params["endDeliveryDate"] = this.daterangeDeliveryDate[1];
      }
      if (null != this.daterangeCreateTime && '' != this.daterangeCreateTime) {
        this.queryParams.params["beginCreateTime"] = this.daterangeCreateTime[0];
        this.queryParams.params["endCreateTime"] = this.daterangeCreateTime[1];
      }
      listDelivery(this.queryParams).then(response => {
        this.deliveryList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 送货方式字典翻译
    deliveryTypeFormat(row, column) {
      return this.selectDictLabel(this.deliveryTypeOptions, row.deliveryType);
    },
    // 创建人字典翻译
    createByFormat(row, column) {
      return this.selectDictLabel(this.createByOptions, row.createBy);
    },
    // 出库状态字典翻译
    statusFormat(row, column) {
      return this.selectDictLabel(this.statusOptions, row.status);
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
        deliveryNo: null,
        contractId: null,
        contractNo: null,
        customerId: null,
        customerName: null,
        deliveryType: null,
        deliveryDate: null,
        address: null,
        contact: null,
        contactTel: null,
        status: null,
        remark: null,
        createBy: null,
        createTime: null,
        scmSaleDeliveryDetailList: [],
      };
      this.scmSaleDeliveryDetailList = [];
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.daterangeDeliveryDate = [];
      this.daterangeCreateTime = [];
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
      this.title = "添加销售发货单";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getDelivery(id).then(response => {
        this.form = response.data;
        this.scmSaleDeliveryDetailList = response.data.scmSaleDeliveryDetailList;
        this.open = true;
        this.title = "修改销售发货单";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateDelivery(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addDelivery(this.form).then(response => {
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
      this.$confirm('是否确认删除销售发货单编号为"' + ids + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delDelivery(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    /** 发货单明细序号 */
    rowScmSaleDeliveryDetailIndex({
      row,
      rowIndex
    }) {
      row.index = rowIndex + 1;
    },
    /** 发货单明细添加按钮操作 */
    handleAddScmSaleDeliveryDetail() {
      let obj = {};
      obj.materialId = "";
      obj.quantity = "";
      obj.detailRemark = "";
      this.scmSaleDeliveryDetailList.push(obj);
    },
    /** 发货单明细删除按钮操作 */
    handleDeleteScmSaleDeliveryDetail() {
      if (this.checkedScmSaleDeliveryDetail.length == 0) {
        this.$alert("请先选择要删除的发货单明细数据", "提示", {
          confirmButtonText: "确定",
        });
      } else {
        this.scmSaleDeliveryDetailList.splice(this.checkedScmSaleDeliveryDetail[0].index - 1, 1);
      }
    },
    /** 单选框选中数据 */
    handleScmSaleDeliveryDetailSelectionChange(selection) {
      if (selection.length > 1) {
        this.$refs.scmSaleDeliveryDetail.clearSelection();
        this.$refs.scmSaleDeliveryDetail.toggleRowSelection(selection.pop());
      } else {
        this.checkedScmSaleDeliveryDetail = selection;
      }
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有销售发货单数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return exportDelivery(queryParams);
      }).then(response => {
        this.download(response.msg);
      })
    }
  }
};

</script>
