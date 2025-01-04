<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="流水号" prop="serialNo">
        <el-input v-model="queryParams.serialNo" placeholder="请输入流水号" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="完工单号" prop="productFinishNo">
        <el-input v-model="queryParams.productFinishNo" placeholder="请输入完工单号" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="计划编号" prop="productPlanNo">
        <el-input v-model="queryParams.productPlanNo" placeholder="请输入生产计划编号" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="作业名称" prop="productJobName">
        <el-input v-model="queryParams.productJobName" placeholder="请输入生产作业名称" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="产品名称" prop="materialName">
        <el-input v-model="queryParams.materialName" placeholder="请输入物料产品名称" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="检验批次" prop="materialBatchNo">
        <el-input v-model="queryParams.materialBatchNo" placeholder="请输入检验批次" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="生产日期">
        <el-date-picker v-model="daterangeProductDate" size="small" style="width: 240px" value-format="yyyy-MM-dd"
          type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="检验项" prop="checkItemName">
        <el-input v-model="queryParams.checkItemName" placeholder="请输入检验项" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="检验结果" prop="checkResult">
        <el-select v-model="queryParams.checkResult" placeholder="请选择检验结果" clearable filterable size="small">
          <el-option v-for="dict in checkResultOptions" :key="dict.dictValue" :label="dict.dictLabel"
            :value="dict.dictValue" />
        </el-select>
      </el-form-item>
      <el-form-item label="检验人" prop="checkUser">
        <el-select v-model="queryParams.checkUser" placeholder="请选择检验人" clearable filterable size="small">
          <el-option v-for="dict in checkUserOptions" :key="dict.dictValue" :label="dict.dictLabel"
            :value="dict.dictValue" />
        </el-select>
      </el-form-item>
      <el-form-item label="检验时间">
        <el-date-picker v-model="daterangeCheckDate" size="small" style="width: 240px" value-format="yyyy-MM-dd"
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
          v-hasPermi="['mesCheck:productCheck:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-no-more-click type="success" plain icon="el-icon-edit" size="mini" :disabled="single"
          @click="handleUpdate" v-hasPermi="['mesCheck:productCheck:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-no-more-click type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple"
          @click="handleDelete" v-hasPermi="['mesCheck:productCheck:remove']">删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="productCheckList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="流水号" align="center" prop="serialNo" />
      <el-table-column label="计划编号" align="center" prop="productPlanNo" />
      <el-table-column label="完工单号" align="center" prop="productFinishNo" />
      <el-table-column label="作业名称" align="center" prop="productJobName" />
      <!-- <el-table-column label="计划排产编号" align="center" prop="productScheduleId" /> -->
      <el-table-column label="产线名称" align="center" prop="productLineName" />
      <el-table-column label="检验批次" align="center" prop="materialBatchNo" />
      <el-table-column label="产品名称" align="center" prop="materialName" />
      <el-table-column label="生产日期" align="center" prop="productDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.productDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="检验项" align="center" prop="checkItemName" />
      <el-table-column label="生产数量" align="center" prop="productQuantity" />
      <el-table-column label="抽检数量" align="center" prop="checkQuantity" />
      <el-table-column label="检验值" align="center" prop="checkValue" />
      <el-table-column label="阈值上限" align="center" prop="maxValue" />
      <el-table-column label="阈值下限" align="center" prop="minValue" />
      <el-table-column label="检验结果" align="center" prop="checkResult" :formatter="checkResultFormat" />
      <el-table-column label="检验人" align="center" prop="checkUser" :formatter="checkUserFormat" />
      <el-table-column label="检验时间" align="center" prop="checkDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.checkDate, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-no-more-click size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['mesCheck:productCheck:edit']">修改</el-button>
          <el-button v-no-more-click size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['mesCheck:productCheck:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改生产检验单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="流水号" prop="serialNo">
          <el-input v-model="form.serialNo" placeholder="-系统自动生成" disabled />
        </el-form-item>
        <el-form-item label="完工单号" prop="productFinishNo">
          <el-input v-model="form.productFinishNo" readonly>
            <el-button v-no-more-click slot="append" @click="handleSelectFinish">选择</el-button>
          </el-input>
        </el-form-item>
        <el-form-item label="计划编号" prop="productPlanNo">
          <el-input v-model="form.productPlanNo" placeholder="" disabled />
        </el-form-item>
        <el-form-item label="作业名称" prop="productJobName">
          <el-input v-model="form.productJobName" placeholder="" disabled />
        </el-form-item>
        <!-- <el-form-item label="计划排产编号" prop="productScheduleId">
          <el-input v-model="form.productScheduleId" placeholder="" disabled />
        </el-form-item>
        <el-form-item label="生产线编号" prop="productLineId">
          <el-input v-model="form.productLineId" placeholder="" disabled />
        </el-form-item> -->
        <el-form-item label="产品名称" prop="materialName">
          <el-input v-model="form.materialName" placeholder="" disabled />
        </el-form-item>
        <el-form-item label="检验批次" prop="materialBatchNo">
          <el-input v-model="form.materialBatchNo" placeholder="" disabled />
        </el-form-item>
        <el-form-item label="生产日期" prop="productDate">
          <el-input v-model="form.productDate" placeholder="" disabled />
        </el-form-item>
        <el-form-item label="生产数量" prop="productQuantity">
          <el-input v-model="form.productQuantity" placeholder="" disabled />
        </el-form-item>
        <el-form-item label="检验标准" prop="checkItemName">
          <el-input v-model="form.checkItemName" readonly>
            <el-button v-no-more-click slot="append" @click="handleSelectStandard">选择</el-button>
          </el-input>
        </el-form-item>
        <el-form-item label="抽检数量" prop="checkQuantity">
          <el-input v-model.number="form.checkQuantity" placeholder="请输入抽检数量" />
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item label="阈值上限" prop="maxValue">
              <el-input v-model="form.maxValue" placeholder="" disabled />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="阈值下限" prop="minValue">
              <el-input v-model="form.minValue" placeholder="" disabled />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="检验值" prop="checkValue">
              <el-input v-model="form.checkValue" placeholder="请输入检验值" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="检验结果" prop="checkResult">
              <el-select v-model="form.checkResult" placeholder="请选择检验结果" clearable filterable>
                <el-option v-for="dict in checkResultOptions" :key="dict.dictValue" :label="dict.dictLabel"
                  :value="dict.dictValue"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button v-no-more-click type="primary" @click="submitForm">确 定</el-button>
        <el-button v-no-more-click @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!--生产作业-->
    <ProductFinishSelect :open="finishSelectOpen" @onSelected="handleFinishSelected" @onCancel="handleFinishCancel">
    </ProductFinishSelect>
    <!--选择检测标准 checkType="2" 产品检查-->
    <StandardSelect :open="standardSelectOpen" :materialId="materialId" checkType="2"
      @onStandardCancel="handleStandardCancel" @onStandardSelected="handleStandardSelected" v-if="materialId">
    </StandardSelect>
  </div>
</template>

<script>
import {
  listProductCheck,
  getProductCheck,
  delProductCheck,
  addProductCheck,
  updateProductCheck,
  exportProductCheck
} from "@/api/mesCheck/productCheck";

import {
  getProductSchedule
} from "@/api/mesPlan/productSchedule";

import StandardSelect from "@/components/MesCheck/StandardSelect";
import ProductFinishSelect from "@/components/product/ProductFinishSelect";

export default {
  name: "ProductCheck",
  components: {
    ProductFinishSelect,
    StandardSelect
  },
  data() {
    let validatePass = (rule, value, callback) => {
      if (value > this.form.productQuantity) {
        callback(new Error('采样数量不能大于生产数量'));
      } else if (value < 1) {
        callback(new Error('采样数量不能小于1'));
      } else {
        callback();
      }
    };
    return {
      jobDetailList: [],
      materialOpts: [],
      finishSelectOpen: false,
      standardSelectOpen: false,
      standard: null,
      materialId: null,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 生产检验单表格数据
      productCheckList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 生产日期时间范围
      daterangeProductDate: [],
      // 检验结果：0不合格1合格字典
      checkResultOptions: [],
      // 检验人字典
      checkUserOptions: [],
      // 检验时间时间范围
      daterangeCheckDate: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        serialNo: null,
        productFinishNo: null,
        productPlanId: null,
        productScheduleId: null,
        productLineId: null,
        productJobId: null,
        materialId: null,
        materialBatchNo: null,
        productDate: null,
        productQuantity: null,
        checkStandardId: null,
        checkQuantity: null,
        checkValue: null,
        maxValue: null,
        minValue: null,
        checkResult: null,
        checkUser: null,
        checkDate: null,
        materialName: null,
        checkItemName: null,
        productPlanNo: null,
        materialName: null,
        productLineName: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        productFinishNo: [{
          required: true,
          message: "生产完工单号不能为空",
          trigger: "blur"
        }],
        productPlanNo: [{
          required: true,
          message: "生产计划编号不能为空",
          trigger: "blur"
        }],
        productJobName: [{
          required: true,
          message: "生产作业不能为空",
          trigger: "blur"
        }],
        materialName: [{
          required: true,
          message: "检验产品不能为空",
          trigger: "blur"
        }],
        materialBatchNo: [{
          required: true,
          message: "检验批次不能为空",
          trigger: "blur"
        }],

        checkItemName: [{
          required: true,
          message: "请选择检验标准",
          trigger: "blur"
        }],

        checkQuantity: [{
          required: true,
          message: "抽检数量不能为空",
          trigger: "blur"
        },
        {
          type: 'number',
          message: '抽检数量必须为数字值'
        },
        {
          validator: validatePass,
          trigger: 'blur'
        }
        ],
        checkValue: [{
          required: true,
          message: "检验值不能为空",
          trigger: "blur"
        }],
        maxValue: [{
          required: true,
          message: "标准值上限不能为空",
          trigger: "blur"
        }],
        minValue: [{
          required: true,
          message: "标准值下限不能为空",
          trigger: "blur"
        }],
        checkResult: [{
          required: true,
          message: "检验结果不能为空",
          trigger: "change"
        }],
        productDate: [{
          required: true,
          message: "生产日期不能为空",
          trigger: "blur"
        }],
        productQuantity: [{
          required: true,
          message: "生产数量不能为空",
          trigger: "blur"
        }],
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("mes_check_result").then(response => {
      this.checkResultOptions = response.data;
    });
    this.getQueryData("query_user").then(res => {
      this.checkUserOptions = res.data;
    });
  },
  methods: {
    //选择生产完工单回调函数
    handleFinishSelected(row) {
      this.form.productFinishId = row.id;
      this.form.productFinishNo = row.serialNo;
      this.form.productJobId = row.jobId;
      this.form.productJobName = row.jobName;
      this.form.productPlanId = row.planId;
      this.form.productPlanNo = row.planNo;
      this.form.productPlanName = row.planName;
      this.form.productScheduleId = row.scheduleId;
      this.form.materialId = row.materialId;
      this.form.productQuantity = row.finishQuantity;
      this.form.productDate = row.finishDate;
      this.form.materialName = row.materialName;
      this.form.materialBatchNo = row.batchNo;
      this.materialId = row.materialId;
      this.getSchedule(row.scheduleId);
      this.finishSelectOpen = false;
    },
    //选择生产完工单
    handleSelectFinish() {
      this.finishSelectOpen = true;
    },
    //取消选择生产完工单
    handleFinishCancel() {
      this.finishSelectOpen = false;
    },

    getSchedule(id) {
      getProductSchedule(id).then(response => {
        let schedule = response.data;
        this.form.productLineId = schedule.productLineId;
      })
    },

    handleChangeMaterial(materialId) {
      if (materialId) {
        this.materialId = materialId;
      } else {
        this.$message.error('请先选择生产完工单');
      }
    },

    //选择检测标准函数
    handleSelectStandard() {
      if (this.materialId) {
        this.standardSelectOpen = true;
      } else {
        this.$message.error('请先选择检验物料');
      }
    },
    //取消选择检测标准函数
    handleStandardCancel() {
      this.standardSelectOpen = false;
    },
    //选择检测标准回调函数
    handleStandardSelected(list) {
      if (list && list.length > 0) {
        let standard = list[0];
        this.form.maxValue = standard.maxValue;
        this.form.minValue = standard.minValue;
        this.form.checkStandardId = standard.id;
        this.form.checkItemName = standard.checkItemName;
      }
      this.standardSelectOpen = false;
    },
    /** 查询生产检验单列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeProductDate && '' != this.daterangeProductDate) {
        this.queryParams.params["beginProductDate"] = this.daterangeProductDate[0];
        this.queryParams.params["endProductDate"] = this.daterangeProductDate[1];
      }
      if (null != this.daterangeCheckDate && '' != this.daterangeCheckDate) {
        this.queryParams.params["beginCheckDate"] = this.daterangeCheckDate[0];
        this.queryParams.params["endCheckDate"] = this.daterangeCheckDate[1];
      }
      listProductCheck(this.queryParams).then(response => {
        this.productCheckList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 检验结果：0不合格1合格字典翻译
    checkResultFormat(row, column) {
      return this.selectDictLabel(this.checkResultOptions, row.checkResult);
    },
    // 检验人字典翻译
    checkUserFormat(row, column) {
      return this.selectDictLabel(this.checkUserOptions, row.checkUser);
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
        productFinishId: null,
        productFinishNo: null,
        productPlanId: null,
        productScheduleId: null,
        productLineId: null,
        productJobId: null,
        materialId: null,
        materialBatchNo: null,
        productDate: null,
        productQuantity: null,
        checkStandardId: null,
        checkQuantity: null,
        checkValue: null,
        maxValue: null,
        minValue: null,
        checkResult: null,
        remark: null,
        checkUser: null,
        checkDate: null,
        productJobName: null,
        productPlanNo: null,
        materialName: null,
        checkItemName: null,
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.daterangeProductDate = [];
      this.daterangeCheckDate = [];
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
      this.title = "添加产品检验单";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getProductCheck(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改产品检验单";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateProductCheck(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addProductCheck(this.form).then(response => {
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
      this.$confirm('是否确认删除生产检验单编号为"' + ids + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delProductCheck(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有生产检验单数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return exportProductCheck(queryParams);
      }).then(response => {
        this.download(response.msg);
      })
    }
  }
};

</script>
