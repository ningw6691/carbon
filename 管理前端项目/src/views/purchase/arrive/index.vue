<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="到货单号" prop="arriveNo">
        <el-input v-model="queryParams.arriveNo" placeholder="请输入到货单号" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="合同编号" prop="contractNo">
        <el-input v-model="queryParams.contractNo" placeholder="请输入合同编号" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="供应商" prop="supplierId">
        <el-select v-model="queryParams.supplierId" placeholder="请选择供应商" clearable filterable size="small">
          <el-option v-for="dict in supplierIdOptions" :key="dict.dictValue" :label="dict.dictLabel"
            :value="dict.dictValue" />
        </el-select>
      </el-form-item>
      <el-form-item label="到货日期">
        <el-date-picker v-model="daterangeArriveDate" size="small" style="width: 240px" value-format="yyyy-MM-dd"
          type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable filterable size="small">
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
      <el-col :span="1.5">
        <el-button v-no-more-click type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['purchase:arrive:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-no-more-click type="success" plain icon="el-icon-edit" size="mini" :disabled="single"
          @click="handleUpdate" v-hasPermi="['purchase:arrive:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-no-more-click type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple"
          @click="handleDelete" v-hasPermi="['purchase:arrive:remove']">删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="arriveList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="到货单号" align="center" prop="arriveNo" />
      <el-table-column label="合同编号" align="center" prop="contractNo" />
      <el-table-column label="供应商名称" align="center" prop="supplierName" />
      <el-table-column label="联系人" align="center" prop="contact" />
      <el-table-column label="联系电话" align="center" prop="contactTel" />
      <el-table-column label="到货日期" align="center" prop="arriveDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.arriveDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status" :formatter="statusFormat" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-no-more-click size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['purchase:arrive:edit']" v-show="scope.row.status == '0'">修改</el-button>
          <el-button v-no-more-click size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['purchase:arrive:remove']" v-show="scope.row.status == '0'">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改采购到货对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px" :inline="true">
        <el-form-item label="到货单号" prop="arriveNo">
          <el-input v-model="form.arriveNo" placeholder="系统自动生成" disabled />
        </el-form-item>
        <el-form-item label="合同编号" prop="contractNo">
          <el-input v-model="form.contractNo" disabled>
            <el-button v-no-more-click slot="append" @click="handleSelectContract">选择</el-button>
          </el-input>
        </el-form-item>
        <el-form-item label="到货日期" prop="arriveDate">
          <el-date-picker clearable size="small" v-model="form.arriveDate" type="date" value-format="yyyy-MM-dd"
            placeholder="选择到货日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-divider content-position="center">到货明细信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <!-- <el-col :span="1.5">
            <el-button v-no-more-click type="primary" icon="el-icon-plus" size="mini" @click="handleAddScmPurchaseArriveDetail">添加
            </el-button>
          </el-col> -->
          <el-col :span="1.5">
            <el-button v-no-more-click type="danger" icon="el-icon-delete" size="mini"
              @click="handleDeleteScmPurchaseArriveDetail">删除
            </el-button>
          </el-col>
        </el-row>
        <el-table :data="scmPurchaseArriveDetailList" :row-class-name="rowScmPurchaseArriveDetailIndex"
          @selection-change="handleScmPurchaseArriveDetailSelectionChange" ref="scmPurchaseArriveDetail">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center" prop="index" width="50" />
          <el-table-column label="物料" prop="materialName" width="200"></el-table-column>
          <el-table-column label="型号" prop="materialModel" width="100"></el-table-column>
          <el-table-column label="规格" prop="materialSpecification"></el-table-column>
          <el-table-column label="单位" prop="materialUnit"></el-table-column>
          <el-table-column label="生产批号" prop="batchNo" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'scmPurchaseArriveDetailList.' + scope.$index + '.batchNo'" :rules="rules.batchNo">
                <el-input v-model="scope.row.batchNo" placeholder="" />
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="单价" prop="price" width="100"></el-table-column>
          <el-table-column label="订货数量" prop="bookQuantity" width="100"></el-table-column>
          <el-table-column label="到货数量" prop="arriveQuantity" width="100">
            <template slot-scope="scope">
              <el-form-item :prop="'scmPurchaseArriveDetailList.' + scope.$index + '.arriveQuantity'"
                :rules="rules.arriveQuantity">
                <el-input v-model.number="scope.row.arriveQuantity" placeholder="" />
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="退货数量" prop="returnQuantity" width="150"></el-table-column>
          <el-table-column label="备注" prop="detailRemark" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'scmPurchaseArriveDetailList.' + scope.$index + '.detailRemark'"
                :rules="rules.detailRemark">
                <el-input v-model="scope.row.detailRemark" placeholder="" />
              </el-form-item>
            </template>
          </el-table-column>
        </el-table>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button v-no-more-click type="primary" @click="submitForm">确 定</el-button>
        <el-button v-no-more-click @click="cancel">取 消</el-button>
      </div>
      <!--选择合同-->
      <ContractSelect :open="contractSelectOpen" @onContractCancel="handleContractCancel"
        @onContractSelected="handleContractSelected"></ContractSelect>
    </el-dialog>
  </div>
</template>

<script>
import {
  listArrive,
  getArrive,
  delArrive,
  addArrive,
  updateArrive,
  exportArrive
} from "@/api/purchase/arrive";
import {
  getContract,
} from "@/api/purchase/contract";
import ContractSelect from "@/components/Purchase/ContractSelect";
export default {
  name: "Arrive",
  components: {
    ContractSelect,
  },
  data() {
    return {
      contractSelectOpen: false,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedScmPurchaseArriveDetail: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 采购到货表格数据
      arriveList: [],
      // 到货明细表格数据
      scmPurchaseArriveDetailList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 到货日期时间范围
      daterangeArriveDate: [],
      // 供应商id字典
      supplierIdOptions: [],
      // 状态字典
      statusOptions: [],
      // 创建人字典
      createByOptions: [],
      // 创建时间时间范围
      daterangeCreateTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        arriveNo: null,
        contractNo: null,
        supplierId: null,
        arriveDate: null,
        status: null,
        createTime: null,
        supplierName: null,
        orderByColumn: 'create_time',
        isAsc: 'desc',
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        contractNo: [{
          required: true,
          message: "请选择合同编号",
          trigger: "blur"
        }],
        arriveDate: [{
          required: true,
          message: "到货日期不能为空",
          trigger: "blur"
        }],
        batchNo: [{
          required: true,
          message: "生产批号不能为空",
          trigger: "blur"
        }],
        arriveQuantity: [{
          required: true,
          message: "到货数量不能为空",
          trigger: "blur"
        },
        {
          type: 'number',
          message: '到货数量必须为数字值'
        },
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("scm_in_warehouse_status").then(response => {
      this.statusOptions = response.data;
    });
    this.getQueryData("query_user").then(res => {
      this.createByOptions = res.data;
    });
    this.getQueryData("query_supplier").then(res => {
      this.supplierIdOptions = res.data;
    });
  },
  watch: {
    'scmPurchaseArriveDetailList': {
      handler(newVal, oldVal) {
        this.form.scmPurchaseArriveDetailList = newVal;
      },
      immediate: true,
      deep: true,
    }
  },
  methods: {
    //选择合同回调函数
    handleContractSelected(list) {
      if (list && list.length > 0) {
        let contract = list[0];
        this.form.contractId = contract.id;
        this.form.contractNo = contract.contractNo;
        this.form.supplierId = contract.supplierId;
        // 根据合同id获取detailList
        getContract(contract.id).then(res => {
          let contractDetailList = res.data.scmPurchaseContractDetailList
          let list = [];
          contractDetailList.forEach(item => {
            list.push({
              materialId: item.materialId,
              materialName: item.materialName,
              materialModel: item.materialModel,
              materialSpecification: item.materialSpecification,
              materialUnit: item.materialUnit,
              price: item.price,
              bookQuantity: item.quantity,
              arriveQuantity: null,
              returnQuantity: 0,
              materialCode: item.materialCode,
              detailRemark: ''
            })
          });
          this.scmPurchaseArriveDetailList = list;
        })
      }
      this.contractSelectOpen = false;
    },
    //选择计划
    handleSelectContract() {
      this.contractSelectOpen = true;
    },
    //取消选择计划
    handleContractCancel() {
      this.contractSelectOpen = false;
    },
    /** 查询采购到货列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeArriveDate && '' != this.daterangeArriveDate) {
        this.queryParams.params["beginArriveDate"] = this.daterangeArriveDate[0];
        this.queryParams.params["endArriveDate"] = this.daterangeArriveDate[1];
      }
      if (null != this.daterangeCreateTime && '' != this.daterangeCreateTime) {
        this.queryParams.params["beginCreateTime"] = this.daterangeCreateTime[0];
        this.queryParams.params["endCreateTime"] = this.daterangeCreateTime[1];
      }
      listArrive(this.queryParams).then(response => {
        this.arriveList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 状态字典翻译
    statusFormat(row, column) {
      return this.selectDictLabel(this.statusOptions, row.status);
    },
    // 创建人字典翻译
    createByFormat(row, column) {
      return this.selectDictLabel(this.createByOptions, row.createBy);
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
        arriveNo: null,
        contractId: null,
        contractNo: null,
        supplierId: null,
        arriveDate: null,
        status: "0",
        remark: null,
        createBy: null,
        createTime: null,
        scmPurchaseArriveDetailList: [],
      };
      this.scmPurchaseArriveDetailList = [];
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.daterangeArriveDate = [];
      this.daterangeCreateTime = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      // this.ids = selection.map(item => item.id)
      let filters = selection.filter(item => item.status == '0');
      this.ids = filters.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加采购到货";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getArrive(id).then(response => {
        this.form = response.data;
        this.scmPurchaseArriveDetailList = response.data.scmPurchaseArriveDetailList;
        this.open = true;
        this.title = "修改采购到货";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (!this.scmPurchaseArriveDetailList || this.scmPurchaseArriveDetailList.length == 0) {
            this.msgError('请选择到货物料');
            return false;
          }
          if (this.form.id != null) {
            updateArrive(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addArrive(this.form).then(response => {
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
      this.$confirm('是否确认删除采购到货编号为"' + ids + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delArrive(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    /** 到货明细序号 */
    rowScmPurchaseArriveDetailIndex({
      row,
      rowIndex
    }) {
      row.index = rowIndex + 1;
    },
    /** 到货明细添加按钮操作 */
    handleAddScmPurchaseArriveDetail() {
      let obj = {};
      obj.materialId = "";
      obj.batchNo = "";
      obj.price = "";
      obj.bookQuantity = "";
      obj.arriveQuantity = "";
      obj.detailRemark = "";
      obj.materialName = "";
      obj.materialModel = "";
      obj.materialSpecification = "";
      obj.materialUnit = "";
      this.scmPurchaseArriveDetailList.push(obj);
    },
    /** 到货明细删除按钮操作 */
    handleDeleteScmPurchaseArriveDetail() {
      if (this.checkedScmPurchaseArriveDetail.length == 0) {
        this.$alert("请先选择要删除的到货明细数据", "提示", {
          confirmButtonText: "确定",
        });
      } else {
        this.scmPurchaseArriveDetailList.splice(this.checkedScmPurchaseArriveDetail[0].index - 1, 1);
      }
    },
    /** 单选框选中数据 */
    handleScmPurchaseArriveDetailSelectionChange(selection) {
      if (selection.length > 1) {
        this.$refs.scmPurchaseArriveDetail.clearSelection();
        this.$refs.scmPurchaseArriveDetail.toggleRowSelection(selection.pop());
      } else {
        this.checkedScmPurchaseArriveDetail = selection;
      }
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有采购到货数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return exportArrive(queryParams);
      }).then(response => {
        this.download(response.msg);
      })
    }
  }
};

</script>
