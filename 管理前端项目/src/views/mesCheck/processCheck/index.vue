<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="流水号" prop="serialNo">
        <el-input v-model="queryParams.serialNo" placeholder="请输入流水号" clearable size="small"
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
      <el-form-item label="物料名称" prop="materialName">
        <el-input v-model="queryParams.materialName" placeholder="请输入物料档案编号" clearable size="small"
          @keyup.enter.native="handleQuery" />
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
          v-hasPermi="['mesCheck:processCheck:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-no-more-click type="success" plain icon="el-icon-edit" size="mini" :disabled="single"
          @click="handleUpdate" v-hasPermi="['mesCheck:processCheck:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-no-more-click type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple"
          @click="handleDelete" v-hasPermi="['mesCheck:processCheck:remove']">删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="processCheckList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="流水号" align="center" prop="serialNo" />
      <el-table-column label="计划编号" align="center" prop="productPlanNo" />
      <!-- <el-table-column label="排产编号" align="center" prop="productScheduleId" />
      <el-table-column label="生产线编号" align="center" prop="productLineId" /> -->
      <el-table-column label="作业名称" align="center" prop="productJobName" />
      <el-table-column label="物料名称" align="center" prop="materialName" />
      <el-table-column label="规格" align="center" prop="materialSpecification" />
      <el-table-column label="型号" align="center" prop="materialModel" />
      <el-table-column label="单位" align="center" prop="materialUnit" />
      <el-table-column label="检验项" align="center" prop="checkItemName" />
      <el-table-column label="生产数量" align="center" prop="productQuantity" />
      <el-table-column label="采样数量" align="center" prop="checkQuantity" />
      <el-table-column label="检验值" align="center" prop="checkValue" />
      <el-table-column label="阈值上限" align="center" prop="maxValue" />
      <el-table-column label="阈值下限" align="center" prop="minValue" />
      <el-table-column label="检验结果" align="center" prop="checkResult" :formatter="checkResultFormat" />
      <el-table-column label="检验人" align="center" prop="checkUser" :formatter="checkUserFormat" />
      <el-table-column label="检验时间" align="center" prop="checkDate" width="180">
        <template slot-scope="scope">
          <span>{{
            parseTime(scope.row.checkDate, "{y}-{m}-{d} {h}:{i}:{s}")
          }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-no-more-click size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['mesCheck:processCheck:edit']">修改</el-button>
          <el-button v-no-more-click size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['mesCheck:processCheck:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改生产采样单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="流水号" prop="serialNo">
          <el-input v-model="form.serialNo" placeholder="-系统自动生成" disabled />
        </el-form-item>
        <el-form-item label="作业名称" prop="productJobName">
          <el-input v-model="form.productJobName" readonly>
            <el-button v-no-more-click slot="append" @click="handleSelectJob">选择</el-button>
          </el-input>
        </el-form-item>
        <el-form-item label="计划编号" prop="productPlanNo">
          <el-input v-model="form.productPlanNo" placeholder="" disabled />
        </el-form-item>
        <el-form-item label="物料名称" prop="materialId">
          <el-select v-model="form.materialId" filterable placeholder="请选择" @change="handleChangeMaterial">
            <el-option v-for="item in materialOpts" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item label="生产数量" prop="productQuantity">
              <el-input v-model="form.productQuantity" placeholder="" disabled />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="采样数量" prop="checkQuantity">
              <el-input v-model.number="form.checkQuantity" placeholder="请输入采样数量" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="检验标准" prop="checkItemName">
          <el-input v-model="form.checkItemName" readonly>
            <el-button v-no-more-click slot="append" @click="handleSelectStandard">选择</el-button>
          </el-input>
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
    <ProductJobSelect :open="jobSelectOpen" :status="'2'" @onSelected="handleJobSelected" @onCancel="handleJobCancel">
    </ProductJobSelect>
    <!--选择检测标准 checkType="1" 来料检查-->
    <StandardSelect :open="standardSelectOpen" :materialId="materialId" checkType="3"
      @onStandardCancel="handleStandardCancel" @onStandardSelected="handleStandardSelected" v-if="materialId">
    </StandardSelect>
  </div>
</template>

<script>
import {
  listProcessCheck,
  getProcessCheck,
  delProcessCheck,
  addProcessCheck,
  updateProcessCheck,
  exportProcessCheck,
} from "@/api/mesCheck/processCheck";
import { getProductJob } from "@/api/mesProduct/productJob";

import { getProductSchedule } from "@/api/mesPlan/productSchedule";

import StandardSelect from "@/components/MesCheck/StandardSelect";
import ProductJobSelect from "@/components/product/ProductJobSelect";
export default {
  name: "ProcessCheck",
  components: {
    StandardSelect,
    ProductJobSelect,
  },
  data() {
    let validatePass = (rule, value, callback) => {
      if (value > this.form.productQuantity) {
        callback(new Error("采样数量不能大于生产数量"));
      } else if (value < 1) {
        callback(new Error("采样数量不能小于1"));
      } else {
        callback();
      }
    };
    return {
      jobDetailList: [],
      materialOpts: [],
      jobSelectOpen: false,
      jobId: null,
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
      // 生产采样单表格数据
      processCheckList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
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
        productPlanId: null,
        productScheduleId: null,
        productLineId: null,
        productJobId: null,
        materialId: null,
        checkStandardId: null,
        productQuantity: null,
        checkQuantity: null,
        checkValue: null,
        maxValue: null,
        minValue: null,
        checkResult: null,
        checkUser: null,
        checkDate: null,
        productJobName: null,
        checkItemName: null,
        materialName: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        productJobName: [
          {
            required: true,
            message: "请选择生产作业",
            trigger: "blur",
          },
        ],
        productPlanNo: [
          {
            required: true,
            message: "生产计划编号不能为空",
            trigger: "blur",
          },
        ],
        productScheduleId: [
          {
            required: true,
            message: "计划排产编号不能为空",
            trigger: "blur",
          },
        ],
        productLineId: [
          {
            required: true,
            message: "生产线编号不能为空",
            trigger: "blur",
          },
        ],
        materialId: [
          {
            required: true,
            message: "物料档案编号不能为空",
            trigger: "blur",
          },
        ],
        checkItemName: [
          {
            required: true,
            message: "请选择检验标准",
            trigger: "blur",
          },
        ],
        productQuantity: [
          {
            required: true,
            message: "生产数量不能为空",
            trigger: "blur",
          },
        ],
        checkQuantity: [
          {
            required: true,
            message: "采样数量不能为空",
            trigger: "blur",
          },
          {
            type: "number",
            message: "抽检数量必须为数字值",
          },
          {
            validator: validatePass,
            trigger: "blur",
          },
        ],
        checkValue: [
          {
            required: true,
            message: "检验值不能为空",
            trigger: "blur",
          },
        ],
        maxValue: [
          {
            required: true,
            message: "标准值上限不能为空",
            trigger: "blur",
          },
        ],
        minValue: [
          {
            required: true,
            message: "标准值下限不能为空",
            trigger: "blur",
          },
        ],
        checkResult: [
          {
            required: true,
            message: "检验结果：0不合格1合格不能为空",
            trigger: "change",
          },
        ],
      },
    };
  },
  created() {
    this.getList();
    this.getDicts("mes_check_result").then((response) => {
      this.checkResultOptions = response.data;
    });
    this.getQueryData("query_user").then((res) => {
      this.checkUserOptions = res.data;
    });
  },
  methods: {
    //选择领料单回调函数
    handleJobSelected(row) {
      console.log(row);
      this.form.productJobId = row.id;
      this.form.productJobName = row.name;
      this.form.productPlanId = row.planId;
      this.form.productPlanNo = row.planNo;
      this.form.productScheduleId = row.scheduleId;
      this.getSchedule(row.scheduleId);
      this.jobId = row.id;
      this.getJobDetailList();
      this.jobSelectOpen = false;
    },
    //选择领料单按钮
    handleSelectJob() {
      this.jobSelectOpen = true;
    },
    //取消选择
    handleJobCancel() {
      this.jobSelectOpen = false;
    },

    getJobDetailList() {
      getProductJob(this.jobId).then((res) => {
        let list = res.data.mesProductJobMaterialList;
        if (list && list.length == 0) {
          this.$message.error("作业子项不能为空，请检查生产作业单！");
        }
        this.materialJobDetailList = res.data.mesProductJobMaterialList;
        this.materialOpts = [];
        list.forEach((element) => {
          this.materialOpts.push({
            value: element.materialId,
            label: element.materialName,
          });
        });
      });
    },

    getSchedule(id) {
      getProductSchedule(id).then((response) => {
        let schedule = response.data;
        this.form.productLineId = schedule.productLineId;
      });
    },

    handleChangeMaterial(materialId) {
      if (materialId) {
        this.materialId = materialId;
        this.form.materialId = materialId;
        for (let item of this.materialJobDetailList) {
          if (item.materialId == materialId) {
            this.form.productQuantity = item.usageQuantity;
            break;
          }
        }
      } else {
        this.$message.error("请先选择领料单");
      }
    },

    //选择检测标准函数
    handleSelectStandard() {
      if (this.materialId) {
        this.standardSelectOpen = true;
      } else {
        this.$message.error("请先选择检验物料");
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
    /** 查询生产采样单列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeCheckDate && "" != this.daterangeCheckDate) {
        this.queryParams.params["beginCheckDate"] = this.daterangeCheckDate[0];
        this.queryParams.params["endCheckDate"] = this.daterangeCheckDate[1];
      }
      listProcessCheck(this.queryParams).then((response) => {
        this.processCheckList = response.rows;
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
        productPlanId: null,
        productPlanNo: null,
        productScheduleId: null,
        productLineId: null,
        productJobId: null,
        materialId: null,
        checkStandardId: null,
        productQuantity: null,
        checkQuantity: null,
        checkValue: null,
        maxValue: null,
        minValue: null,
        checkResult: null,
        remark: null,
        checkUser: null,
        checkDate: null,
        checkItemName: null,
        productJobName: null,
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
      this.daterangeCheckDate = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加生产采样单";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getProcessCheck(id).then((response) => {
        this.form = response.data;
        this.jobId = response.data.productJobId;
        this.getJobDetailList();
        this.open = true;
        this.title = "修改生产采样单";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateProcessCheck(this.form).then((response) => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addProcessCheck(this.form).then((response) => {
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
      this.$confirm(
        '是否确认删除生产采样单编号为"' + ids + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delProcessCheck(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        });
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm("是否确认导出所有生产采样单数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return exportProcessCheck(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        });
    },
  },
};
</script>
