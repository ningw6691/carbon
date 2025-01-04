<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="订单编号" prop="orderNo">
        <el-input v-model="queryParams.orderNo" placeholder="请输入订单编号" clearable size="small"
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
      <el-form-item label="申请人" prop="applyUser">
        <el-select v-model="queryParams.applyUser" placeholder="请选择申请人" clearable filterable size="small">
          <el-option v-for="dict in applyUserOptions" :key="dict.dictValue" :label="dict.dictLabel"
            :value="dict.dictValue" />
        </el-select>
      </el-form-item>
      <el-form-item label="申请时间">
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
      <el-form-item label="审核时间">
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

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button v-no-more-click v-no-more-click type="primary" plain icon="el-icon-plus" size="mini"
          @click="handleAdd" v-hasPermi="['sale:order:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-no-more-click type="success" plain icon="el-icon-edit" size="mini" :disabled="single"
          @click="handleUpdate" v-hasPermi="['sale:order:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-no-more-click type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple"
          @click="handleDelete" v-hasPermi="['sale:order:remove']">删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="orderList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="订单编号" align="center" prop="orderNo" />
      <el-table-column label="客户名称" align="center" prop="customerName" />
      <el-table-column label="联系人" align="center" prop="contact" />
      <el-table-column label="联系方式" align="center" prop="contactTel" />
      <el-table-column label="金额合计" align="center" prop="orderAmount" />
      <el-table-column label="交货日期" align="center" prop="deliveryDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.deliveryDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="申请人" align="center" prop="applyUser" :formatter="applyUserFormat" />
      <el-table-column label="申请时间" align="center" prop="applyTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.applyTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="申请状态" align="center" prop="applyStatus" :formatter="applyStatusFormat" />
      <el-table-column label="审核人" align="center" prop="auditUser" :formatter="auditUserFormat" />
      <el-table-column label="审核时间" align="center" prop="auditTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.auditTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="审核状态" align="center" prop="auditStatus" :formatter="auditStatusFormat" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-no-more-click size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['sale:order:edit']">修改</el-button>
          <el-button v-no-more-click v-if="scope.row.auditStatus != '1' && scope.row.applyStatus != '1'" size="mini"
            type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['sale:order:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改销售订单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px" :inline="true">
        <el-form-item label="订单编号" prop="orderNo">
          <el-input v-model="form.orderNo" placeholder="-系统自动生成" disabled />
        </el-form-item>
        <el-form-item label="客户名称" prop="customerName">
          <el-input v-model="form.customerName" disabled>
            <el-button v-no-more-click slot="append" @click="handleSelectCustomer"
              :disabled="form.auditStatus != '1' && form.applyStatus != '1' ? false : true">选择</el-button>
          </el-input>
        </el-form-item>
        <el-form-item label="交货日期" prop="deliveryDate">
          <el-date-picker clearable size="small" v-model="form.deliveryDate" type="date" value-format="yyyy-MM-dd"
            placeholder="选择交货日期" :disabled="form.auditStatus != '1' && form.applyStatus != '1' ? false : true">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="申请人" prop="applyUser">
          <el-select v-model="form.applyUser" placeholder="" clearable filterable disabled>
            <el-option v-for="dict in applyUserOptions" :key="dict.dictValue" :label="dict.dictLabel"
              :value="dict.dictValue"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="申请时间" prop="applyTime">
          <el-date-picker clearable size="small" v-model="form.applyTime" type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss" placeholder="" disabled>
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
              :value="dict.dictValue"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="审核时间" prop="auditTime">
          <el-date-picker clearable size="small" v-model="form.auditTime" type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss" placeholder="" disabled>
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
          <el-input v-model="form.remark" type="textarea" placeholder=""
            :disabled="form.auditStatus != '1' && form.applyStatus != '1' ? false : true" />
        </el-form-item>
        <el-divider content-position="center">订单明细信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button v-no-more-click v-if="form.auditStatus != '1' && form.applyStatus != '1'" type="primary"
              icon="el-icon-plus" size="mini" @click="handleAddScmSaleOrderDetail">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button v-no-more-click v-if="form.auditStatus != '1' && form.applyStatus != '1'" type="danger"
              icon="el-icon-delete" size="mini" @click="handleDeleteScmSaleOrderDetail">删除</el-button>
          </el-col>
        </el-row>
        <el-table :data="scmSaleOrderDetailList" :row-class-name="rowScmSaleOrderDetailIndex"
          @selection-change="handleScmSaleOrderDetailSelectionChange" ref="scmSaleOrderDetail">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center" prop="index" width="50" />
          <el-table-column label="产品" prop="materialName" width="300">
            <template slot-scope="scope">
              <el-form-item :prop="'scmSaleOrderDetailList.' + scope.$index + '.materialName'" :rules="rules.materialName">
                <el-input v-model="scope.row.materialName" placeholder="请选择物料" disabled>
                  <el-button v-no-more-click slot="append" @click="handleSelectMaterial(scope.$index)"
                    :disabled="form.auditStatus != '1' && form.applyStatus != '1' ? false : true">选择</el-button>
                </el-input>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="型号" prop="materialModel"></el-table-column>
          <el-table-column label="规格" prop="materialSpecification"></el-table-column>
          <el-table-column label="单位" prop="materialUnit"></el-table-column>
          <el-table-column label="单价" prop="price" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'scmSaleOrderDetailList.' + scope.$index + '.price'" :rules="rules.price">
                <el-input v-model="scope.row.price" placeholder="请输入单价"
                  :disabled="form.auditStatus != '1' && form.applyStatus != '1' ? false : true" />
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="订货数量" prop="quantity" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'scmSaleOrderDetailList.' + scope.$index + '.quantity'" :rules="rules.quantity">
                <el-input v-model.number="scope.row.quantity" placeholder="请输入订货数量"
                  :disabled="form.auditStatus != '1' && form.applyStatus != '1' ? false : true" />
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="金额" prop="amount" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'scmSaleOrderDetailList.' + scope.$index + '.amount'" :rules="rules.amount">
                <el-input v-model="scope.row.amount" :readonly="true" />
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="备注" prop="detailRemark" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'scmSaleOrderDetailList.' + scope.$index + '.detailRemark'" :rules="rules.detailRemark">
                <el-input v-model="scope.row.detailRemark" placeholder="请输入备注"
                  :disabled="form.auditStatus != '1' && form.applyStatus != '1' ? false : true" />
              </el-form-item>
            </template>
          </el-table-column>
        </el-table>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button v-no-more-click v-no-more-click v-if="form.auditStatus != '1' && form.applyStatus != '1'" type="primary"
          @click="saveForm">保 存</el-button>
        <el-button v-no-more-click v-no-more-click v-if="form.auditStatus != '1' && form.applyStatus != '1'" type="warning"
          @click="submitForm">提 交
        </el-button>
        <el-button v-no-more-click v-if="form.auditStatus != '1' && form.applyStatus != '1'" @click="cancel">取 消</el-button>
      </div>
      <!--选择客户-->
      <CustomerSelect :open="customerSelectOpen" @onCustomerCancel="handleCustomerCancel"
        @onCustomerSelected="handleCustomerSelected"></CustomerSelect>
    </el-dialog>
    <!--选择物料档案-->
    <MaterialSelect :open="materialSelectOpen" @onCancel="handleMaterialCancel" @onSelected="handleMaterialSelected">
    </MaterialSelect>
  </div>
</template>

<script>
import {
  listOrder,
  getOrder,
  delOrder,
  addOrder,
  updateOrder,
  exportOrder,
  submitOrder
} from "@/api/sale/order";
import MaterialSelect from "@/components/Material/MaterialSelect";
import CustomerSelect from "@/components/Sale/CustomerSelect";
export default {
  name: "Order",
  components: {
    MaterialSelect,
    CustomerSelect
  },
  data() {
    let validateAmount = (rule, value, callback) => {
      if (value > 99999999) {
        callback(new Error('不能超过99999999元'));
      } else {
        callback();
      }
    };
    return {
      customerSelectOpen: false,
      material: null,
      currentRowIndex: null,
      materialSelectOpen: false,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedScmSaleOrderDetail: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 销售订单表格数据
      orderList: [],
      // 订单明细表格数据
      scmSaleOrderDetailList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 交货日期时间范围
      daterangeDeliveryDate: [],
      // 客户id字典
      customerIdOptions: [],
      // 申请人字典
      applyUserOptions: [],
      // 申请时间时间范围
      daterangeApplyTime: [],
      // 申请状态字典
      applyStatusOptions: [],
      // 审核人字典
      auditUserOptions: [],
      // 审核时间时间范围
      daterangeAuditTime: [],
      // 审核状态字典
      auditStatusOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderNo: null,
        customerId: null,
        deliveryDate: null,
        applyUser: null,
        applyTime: null,
        applyStatus: null,
        auditUser: null,
        auditTime: null,
        auditStatus: null,
        customerName: null,
        orderByColumn: 'apply_time',
        isAsc: 'desc'
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        customerName: [{
          required: true,
          message: "客户不能为空",
          trigger: "blur"
        }],
        deliveryDate: [{
          required: true,
          message: "交货日期不能为空",
          trigger: "blur"
        }],
        materialName: [{
          required: true,
          message: "物料不能为空",
          trigger: "blur"
        }],
        price: [{
          required: true,
          message: "单价不能为空",
          trigger: "blur"
        }],
        quantity: [{
          required: true,
          message: "订货数量不能为空",
          trigger: "blur"
        },
        {
          type: 'number',
          message: '订货数量必须为数字'
        },
        {
          type: "number",
          min: 0,
          message: "订货数量不能小于0",
          trigger: "blur"
        },

        ],
        amount: [{
          required: true,
          validator: validateAmount,
          trigger: "change"
        }],
      }
    };
  },
  created() {
    this.getList();
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
    this.getQueryData("query_customer").then(res => {
      this.customerIdOptions = res.data;
    });
  },
  watch: {
    'scmSaleOrderDetailList': {
      handler(newVal, oldVal) {
        this.form.scmSaleOrderDetailList = newVal;
        this.scmSaleOrderDetailList.forEach(row => {
          row.amount = row.price * row.quantity
        })
      },
      immediate: true,
      deep: true,
    }
  },
  methods: {
    //选择计划回调函数
    handleCustomerSelected(list) {
      if (list && list.length > 0) {
        let customer = list[0];
        this.form.customerId = customer.id;
        this.form.customerName = customer.name
      }
      this.customerSelectOpen = false;
    },
    //选择计划
    handleSelectCustomer() {
      this.customerSelectOpen = true;
    },
    //取消选择计划
    handleCustomerCancel() {
      this.customerSelectOpen = false;
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
        this.scmSaleOrderDetailList[this.currentRowIndex].materialId = this.material.id;
        this.scmSaleOrderDetailList[this.currentRowIndex].price = this.material.price;
        this.scmSaleOrderDetailList[this.currentRowIndex].materialName = this.material.name;
        this.scmSaleOrderDetailList[this.currentRowIndex].materialCode = this.material.code;
        this.scmSaleOrderDetailList[this.currentRowIndex].materialModel = this.material.model;
        this.scmSaleOrderDetailList[this.currentRowIndex].materialSpecification = this.material.specification;
        this.scmSaleOrderDetailList[this.currentRowIndex].materialUnit = this.material.unit;
      }
      this.materialSelectOpen = false;
    },
    /** 查询销售订单列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeDeliveryDate && '' != this.daterangeDeliveryDate) {
        this.queryParams.params["beginDeliveryDate"] = this.daterangeDeliveryDate[0];
        this.queryParams.params["endDeliveryDate"] = this.daterangeDeliveryDate[1];
      }
      if (null != this.daterangeApplyTime && '' != this.daterangeApplyTime) {
        this.queryParams.params["beginApplyTime"] = this.daterangeApplyTime[0];
        this.queryParams.params["endApplyTime"] = this.daterangeApplyTime[1];
      }
      if (null != this.daterangeAuditTime && '' != this.daterangeAuditTime) {
        this.queryParams.params["beginAuditTime"] = this.daterangeAuditTime[0];
        this.queryParams.params["endAuditTime"] = this.daterangeAuditTime[1];
      }
      listOrder(this.queryParams).then(response => {
        this.orderList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
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
        orderNo: null,
        customerId: null,
        customerName: null,
        orderAmount: null,
        deliveryDate: null,
        applyUser: null,
        applyTime: null,
        applyStatus: null,
        auditUser: null,
        auditTime: null,
        auditStatus: null,
        auditComment: null,
        remark: null,
        scmSaleOrderDetailList: [],
      };
      this.scmSaleOrderDetailList = [];
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
      this.daterangeApplyTime = [];
      this.daterangeAuditTime = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      let filters = selection.filter(item => item.auditStatus != '1');
      this.ids = filters.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加销售订单";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getOrder(id).then(response => {
        this.form = response.data;
        this.scmSaleOrderDetailList = response.data.scmSaleOrderDetailList;
        this.open = true;
        this.title = "修改销售订单";
      });
    },
    /** 保存按钮 */
    saveForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (!this.scmSaleOrderDetailList || this.scmSaleOrderDetailList.length == 0) {
            this.msgError('请添加订单物料');
            return false;
          }
          if (this.form.id != null) {
            updateOrder(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addOrder(this.form).then(response => {
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
          submitOrder(this.form).then(response => {
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
      this.$confirm('是否确认删除销售订单编号为"' + ids + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delOrder(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    /** 订单明细序号 */
    rowScmSaleOrderDetailIndex({
      row,
      rowIndex
    }) {
      row.index = rowIndex + 1;
    },
    /** 订单明细添加按钮操作 */
    handleAddScmSaleOrderDetail() {
      let obj = {};
      obj.materialId = "";
      obj.price = "";
      obj.quantity = "";
      obj.amount = "";
      obj.detailRemark = "";
      obj.materialName = "";
      obj.materialModel = "";
      obj.materialSpecification = "";
      obj.materialUnit = "";
      this.scmSaleOrderDetailList.push(obj);
    },
    /** 订单明细删除按钮操作 */
    handleDeleteScmSaleOrderDetail() {
      if (this.checkedScmSaleOrderDetail.length == 0) {
        this.$alert("请先选择要删除的订单明细数据", "提示", {
          confirmButtonText: "确定",
        });
      } else {
        this.scmSaleOrderDetailList.splice(this.checkedScmSaleOrderDetail[0].index - 1, 1);
      }
    },
    /** 单选框选中数据 */
    handleScmSaleOrderDetailSelectionChange(selection) {
      if (selection.length > 1) {
        this.$refs.scmSaleOrderDetail.clearSelection();
        this.$refs.scmSaleOrderDetail.toggleRowSelection(selection.pop());
      } else {
        this.checkedScmSaleOrderDetail = selection;
      }
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有销售订单数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return exportOrder(queryParams);
      }).then(response => {
        this.download(response.msg);
      })
    }
  }
};

</script>
