<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="项目" prop="projectId">
        <el-select v-model="queryParams.projectId" placeholder="请选择项目" clearable filterable size="small">
          <el-option v-for="dict in projectIdOptions" :key="dict.dictValue" :label="dict.dictLabel"
            :value="dict.dictValue" />
        </el-select>
      </el-form-item>
      <el-form-item label="计划号" prop="serialNo">
        <el-input v-model="queryParams.serialNo" placeholder="请输入计划号" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="名称" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入名称" clearable size="small"
          @keyup.enter.native="handleQuery" />
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
        <el-button v-no-more-click type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['mesPlan:productPlan:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-no-more-click type="success" plain icon="el-icon-edit" size="mini" :disabled="single"
          @click="handleUpdate" v-hasPermi="['mesPlan:productPlan:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-no-more-click type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple"
          @click="handleDelete" v-hasPermi="['mesPlan:productPlan:remove']">删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="productPlanList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="计划号" align="center" prop="serialNo" />
      <el-table-column label="项目" align="center" prop="projectId" :formatter="projectIdFormat" />
      <el-table-column label="名称" align="center" prop="name" />
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
            v-hasPermi="['mesPlan:productPlan:edit']">修改</el-button>
          <el-button v-no-more-click v-if="scope.row.auditStatus != '1' && scope.row.applyStatus != '1'" size="mini"
            type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['mesPlan:productPlan:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改生产计划对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px" :inline="true">
        <el-form-item label="计划号" prop="serialNo">
          <el-input v-model="form.serialNo" placeholder="" disabled />
        </el-form-item>
        <el-form-item label="订单号" prop="orderNo">
          <el-input v-model="form.orderNo" placeholder="请选择订单" disabled>
            <el-button v-no-more-click slot="append" @click="handleSelectOrder"
              :disabled="form.auditStatus != '1' && form.applyStatus != '1' ? false : true">选择</el-button>
          </el-input>
        </el-form-item>
        <el-form-item label="项目" prop="projectId">
          <el-select v-model="form.projectId" placeholder="请选择项目" clearable filterable
            :disabled="form.auditStatus != '1' && form.applyStatus != '1' ? false : true">
            <el-option v-for="dict in projectIdOptions" :key="dict.dictValue" :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入名称"
            :disabled="form.auditStatus != '1' && form.applyStatus != '1' ? false : true" />
        </el-form-item>
        <el-form-item label="交货日期" prop="deliveryDate">
          <el-date-picker clearable size="small" v-model="form.deliveryDate" type="date" value-format="yyyy-MM-dd"
            placeholder="" disabled>
          </el-date-picker>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"
            :disabled="form.auditStatus != '1' && form.applyStatus != '1' ? false : true" />
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
        <el-divider content-position="center">生产计划明细信息</el-divider>
        <el-table :data="mesProductPlanDetailList" :row-class-name="rowMesProductPlanDetailIndex"
          ref="mesProductPlanDetail">
          <el-table-column label="序号" align="center" prop="index" width="50" />
          <el-table-column label="产品" prop="materialName" width="150">
          </el-table-column>
          <el-table-column label="型号" prop="materialModel">
          </el-table-column>
          <el-table-column label="规格" prop="materialSpecification">
          </el-table-column>
          <el-table-column label="单位" prop="materialUnit">
          </el-table-column>
          <el-table-column label="生产数量" prop="quantity" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'mesProductPlanDetailList.' + scope.$index + '.quantity'" :rules="rules.quantity">
                <el-input v-model.number="scope.row.quantity" placeholder="请输入生产数量"
                  :disabled="form.auditStatus != '1' && form.applyStatus != '1' ? false : true" />
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="生产日期" prop="productDate" width="240">
            <template slot-scope="scope">
              <el-form-item :prop="'mesProductPlanDetailList.' + scope.$index + '.productDate'"
                :rules="rules.productDate">
                <el-date-picker clearable v-model="scope.row.productDate" type="date" value-format="yyyy-MM-dd"
                  placeholder="请选择生产日期" :disabled="form.auditStatus != '1' && form.applyStatus != '1' ? false : true" />
              </el-form-item>
            </template>
          </el-table-column>
        </el-table>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button v-no-more-click v-if="form.auditStatus != '1' && form.applyStatus != '1'" type="primary"
          @click="saveForm">保
          存</el-button>
        <el-button v-no-more-click v-if="form.auditStatus != '1' && form.applyStatus != '1'" type="warning"
          @click="submitForm">提
          交</el-button>
        <el-button v-no-more-click @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!--选择销售订单-->
    <order-select :open="orderSelectOpen" @onOrderCancel="handleOrderCancel"
      @onOrderSelected="handleOrderSelected"></order-select>
  </div>
</template>

<script>
import { listProductPlan, getProductPlan, delProductPlan, addProductPlan, updateProductPlan, exportProductPlan, submitProductPlan } from "@/api/mesPlan/productPlan";
import OrderSelect from '@/components/Sale/OrderSelect.vue';
import { getOrder } from "@/api/sale/order";
export default {
  name: "ProductPlan",
  components: {
    OrderSelect
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedMesProductPlanDetail: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 生产计划表格数据
      productPlanList: [],
      // 生产计划明细表格数据
      mesProductPlanDetailList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      orderSelectOpen: false,
      // 项目字典
      projectIdOptions: [],
      // 交货日期时间范围
      daterangeDeliveryDate: [],
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
        orderId: null,
        serialNo: null,
        projectId: null,
        name: null,
        deliveryDate: null,
        applyUser: null,
        applyTime: null,
        applyStatus: null,
        auditUser: null,
        auditTime: null,
        auditStatus: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        orderNo: [
          { required: true, message: "订单不能为空", trigger: "blur" }
        ],
        projectId: [
          { required: true, message: "项目不能为空", trigger: "blur" }
        ],
        name: [
          { required: true, message: "名称不能为空", trigger: "blur" },
          { max: 20, message: "名称最多20个字符", trigger: "blur" }
        ],
        deliveryDate: [
          { required: true, message: "交货日期不能为空", trigger: "blur" }
        ],
        materialName: [
          { required: true, message: "物料不能为空", trigger: "blur" }
        ],
        quantity: [
          { required: true, message: "生产数量不能为空", trigger: "blur" },
          { type: 'number', min: 1, max: 65535, message: '生产数量必须为数字值且大小要在1到65535' },
        ],
        productDate: [
          { required: true, message: "生产日期不能为空", trigger: "blur" }
        ],
        remark: [
          { max: 100, message: "备注最多100个字符", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getQueryData("query_project").then(res => {
      this.projectIdOptions = res.data;
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
  },
  watch: {
    'mesProductPlanDetailList': {
      handler(newVal, oldVal) {
        this.form.mesProductPlanDetailList = newVal;
      },
      immediate: true,
      deep: true,
    }
  },
  methods: {
    handleOrderSelected(list) {
      let bill = list[0];
      this.form.orderId = bill.id;
      this.form.orderNo = bill.orderNo;
      this.form.customerName = bill.customerName;
      this.form.deliveryDate = bill.deliveryDate;
      getOrder(this.form.orderId).then(res => {
        this.mesProductPlanDetailList = [];
        let list = res.data.scmSaleOrderDetailList;
        list.forEach(material => {
          let obj = {};
          obj.materialId = material.materialId;
          obj.materialCode = material.materialCode;
          obj.materialName = material.materialName;
          obj.materialModel = material.materialModel;
          obj.materialSpecification = material.materialSpecification;
          obj.materialUnit = material.materialUnit;
          obj.quantity = material.quantity;
          this.mesProductPlanDetailList.push(obj);
        });
      });
      this.orderSelectOpen = false;
    },
    handleOrderCancel() {
      this.orderSelectOpen = false;
    },
    handleSelectOrder() {
      this.orderSelectOpen = true;
    },
    /** 查询生产计划列表 */
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
      listProductPlan(this.queryParams).then(response => {
        this.productPlanList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 项目字典翻译
    projectIdFormat(row, column) {
      return this.selectDictLabel(this.projectIdOptions, row.projectId);
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
        orderId: null,
        orderNo: null,
        projectId: null,
        name: null,
        deliveryDate: null,
        applyUser: null,
        applyTime: null,
        applyStatus: null,
        auditUser: null,
        auditTime: null,
        auditStatus: null,
        auditComment: null,
        remark: null,
        mesProductPlanDetailList: [],
      };
      this.mesProductPlanDetailList = [];
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
      this.single = filters.length !== 1
      this.multiple = !filters.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加生产计划";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getProductPlan(id).then(response => {
        this.form = response.data;
        this.mesProductPlanDetailList = response.data.mesProductPlanDetailList;
        this.open = true;
        this.title = "修改生产计划";
      });
    },
    /** 保存按钮 */
    saveForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateProductPlan(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addProductPlan(this.form).then(response => {
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
          submitProductPlan(this.form).then(response => {
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
      this.$confirm('是否确认删除生产计划编号为"' + ids + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delProductPlan(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    /** 生产计划明细序号 */
    rowMesProductPlanDetailIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有生产计划数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return exportProductPlan(queryParams);
      }).then(response => {
        this.download(response.msg);
      })
    }
  }
};
</script>
