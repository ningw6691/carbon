<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="作业名称" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入作业名称" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="开始时间">
        <el-date-picker v-model="daterangeStartTime" size="small" style="width: 240px" value-format="yyyy-MM-dd"
          type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="结束时间">
        <el-date-picker v-model="daterangeEndTime" size="small" style="width: 240px" value-format="yyyy-MM-dd"
          type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="作业状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择作业状态" clearable filterable size="small">
          <el-option v-for="dict in statusOptions" :key="dict.dictValue" :label="dict.dictLabel"
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
          v-hasPermi="['mesProduct:productJob:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-no-more-click type="success" plain icon="el-icon-edit" size="mini" :disabled="single"
          @click="handleUpdate" v-hasPermi="['mesProduct:productJob:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-no-more-click type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple"
          @click="handleDelete" v-hasPermi="['mesProduct:productJob:remove']">删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="productJobList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="作业名称" align="center" prop="name" />
      <el-table-column label="生产计划" align="center" prop="planNo" />
      <el-table-column label="计划排产" align="center" prop="scheduleNo" />
      <el-table-column label="产品" align="center" prop="productName" />
      <el-table-column label="型号" prop="materialModel"> </el-table-column>
      <el-table-column label="规格" prop="materialSpecification">
      </el-table-column>
      <el-table-column label="单位" prop="materialUnit"> </el-table-column>
      <el-table-column label="开始时间" align="center" prop="startTime" width="180">
        <template slot-scope="scope">
          <span>{{
            parseTime(scope.row.startTime, "{y}-{m}-{d} {h}:{i}:{s}")
          }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结束时间" align="center" prop="endTime" width="180">
        <template slot-scope="scope">
          <span>{{
            parseTime(scope.row.endTime, "{y}-{m}-{d} {h}:{i}:{s}")
          }}</span>
        </template>
      </el-table-column>
      <el-table-column label="生产数量" align="center" prop="productQuantity" />
      <el-table-column label="作业状态" align="center" prop="status" :formatter="statusFormat" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-no-more-click v-if="scope.row.status == '0'" size="mini" type="text" icon="el-icon-caret-right"
            @click="handleStatus(scope.row, '1')" v-hasPermi="['mesProduct:productJob:edit']">执行</el-button>
          <el-button v-no-more-click v-if="scope.row.status == '1'" size="mini" type="text" icon="el-icon-finished"
            @click="handleStatus(scope.row, '2')" v-hasPermi="['mesProduct:productJob:edit']">完成</el-button>
          <el-button v-no-more-click size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['mesProduct:productJob:edit']">修改</el-button>
          <el-button v-no-more-click v-if="scope.row.status == '0'" size="mini" type="text" icon="el-icon-delete"
            @click="handleDelete(scope.row)" v-hasPermi="['mesProduct:productJob:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改生产作业对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px" :inline="true">
        <el-form-item label="作业名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入作业名称" />
        </el-form-item>
        <el-form-item label="生产计划" prop="planNo">
          <el-input v-model="form.planNo" placeholder="" disabled>
            <el-button v-no-more-click slot="append" @click="handleSelectSchedule" :disabled="form.auditStatus != '1' && form.applyStatus != '1'
              ? false
              : true
              ">选择</el-button>
          </el-input>
        </el-form-item>
        <el-form-item label="产品" prop="productName">
          <el-input v-model="form.productName" placeholder="" disabled />
        </el-form-item>
        <el-form-item label="规格" prop="materialSpecification">
          <el-input v-model="form.materialSpecification" placeholder="" disabled />
        </el-form-item>
        <el-form-item label="型号" prop="materialModel">
          <el-input v-model="form.materialModel" placeholder="" disabled />
        </el-form-item>
        <el-form-item label="单位" prop="materialUnit">
          <el-input v-model="form.materialUnit" placeholder="" disabled />
        </el-form-item>
        <el-form-item label="待产数量" prop="requireQuantity">
          <el-input v-model="form.requireQuantity" placeholder="" disabled />
        </el-form-item>
        <el-form-item label="已产数量" prop="finishQuantity">
          <el-input v-model="form.finishQuantity" placeholder="" disabled />
        </el-form-item>
        <el-form-item label="生产数量" prop="productQuantity">
          <el-input v-model.number="form.productQuantity" placeholder="请输入生产数量" />
        </el-form-item>
        <el-form-item label="排产日期" prop="scheduleDate">
          <el-input v-model="form.scheduleDate" placeholder="" disabled />
        </el-form-item>
        <el-form-item label="开始时间" prop="startTime">
          <el-date-picker clearable size="small" v-model="form.startTime" type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss" placeholder="" disabled>
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="endTime">
          <el-date-picker clearable size="small" v-model="form.endTime" type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss" placeholder="" disabled>
          </el-date-picker>
        </el-form-item>
        <el-form-item label="作业状态" prop="status">
          <el-select v-model="form.status" placeholder="" clearable filterable disabled>
            <el-option v-for="dict in statusOptions" :key="dict.dictValue" :label="dict.dictLabel"
              :value="dict.dictValue"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-divider content-position="center">生产作业物料信息</el-divider>
        <el-table :data="mesProductJobMaterialList" :row-class-name="rowMesProductJobMaterialIndex"
          ref="mesProductJobMaterial">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center" prop="index" width="50" />
          <el-table-column label="物料" prop="materialName" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'mesProductJobMaterialList.' + scope.$index + '.materialName'
                " :rules="rules.materialName">
                <el-input v-model="scope.row.materialName" placeholder="" disabled />
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="型号" prop="materialModel"> </el-table-column>
          <el-table-column label="规格" prop="materialSpecification">
          </el-table-column>
          <el-table-column label="单位" prop="materialUnit"> </el-table-column>
          <el-table-column label="消耗数量" prop="usageQuantity" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'mesProductJobMaterialList.' + scope.$index + '.usageQuantity'
                " :rules="rules.usageQuantity">
                <el-input v-model="scope.row.usageQuantity" placeholder="" disabled />
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="备注" prop="detailRemark" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'mesProductJobMaterialList.' + scope.$index + '.detailRemark'
                " :rules="rules.detailRemark">
                <el-input v-model="scope.row.detailRemark" placeholder="请输入备注" />
              </el-form-item>
            </template>
          </el-table-column>
        </el-table>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button v-no-more-click v-if="form.status == '0' || !form.status" type="primary" @click="submitForm">确
          定</el-button>
        <el-button v-no-more-click @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <product-schedule-select receiveStatus="1" :open="scheduleOpen" @onSelected="handleScheduleSelected"
      @onCancel="handleScheduleCancel"></product-schedule-select>
  </div>
</template>

<script>
import {
  listProductJob,
  getProductJob,
  delProductJob,
  addProductJob,
  updateProductJob,
  exportProductJob,
  updateProductJobStatus,
} from "@/api/mesProduct/productJob";
import ProductScheduleSelect from "@/components/product/ProductScheduleSelect.vue";
import { getMesBom } from "@/api/mesPlan/mesBom";
import { getProductSchedule } from "@/api/mesPlan/productSchedule";
export default {
  name: "ProductJob",
  components: {
    ProductScheduleSelect,
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedMesProductJobMaterial: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 生产作业表格数据
      productJobList: [],
      // 生产作业物料表格数据
      mesProductJobMaterialList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      scheduleOpen: false,
      // 开始时间时间范围
      daterangeStartTime: [],
      // 结束时间时间范围
      daterangeEndTime: [],
      // 作业状态：0未执行1执行中2已完成3异常字典
      statusOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        planId: null,
        scheduleId: null,
        productId: null,
        startTime: null,
        endTime: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          { required: true, message: "作业名称不能为空", trigger: "blur" },
          { max: 20, message: "长度不能超过20个字符", trigger: "blur" },
        ],
        planId: [
          { required: true, message: "生产计划不能为空", trigger: "blur" },
        ],
        materialName: [
          { required: true, message: "物料不能为空", trigger: "blur" },
        ],
        productQuantity: [
          { required: true, message: "生产数量不能为空", trigger: "blur" },
          {
            type: "number",
            min: 1,
            max: 65535,
            message: "生产数量必须为数字值且大小要在1到65535",
          },
        ],
        remark: [{ max: 100, message: "备注最多100个字符", trigger: "blur" }],
      },
    };
  },
  created() {
    this.getList();
    this.getDicts("product_job_status").then((response) => {
      this.statusOptions = response.data;
    });
  },
  watch: {
    mesProductJobMaterialList: {
      handler(newVal, oldVal) {
        this.form.mesProductJobMaterialList = newVal;
      },
      immediate: true,
      deep: true,
    },
    "form.productQuantity": {
      handler(newVal, oldVal) {
        this.calculateUsage();
      },
      immediate: true,
      deep: true,
    },
  },
  methods: {
    handleStatus(row, status) {
      let params = {
        id: row.id,
        status: status,
      };
      updateProductJobStatus(params).then((res) => {
        this.msgSuccess("提交成功");
        this.getList();
      });
    },
    handleSelectSchedule() {
      this.scheduleOpen = true;
    },
    handleScheduleSelected(plan) {
      this.form.planId = plan.planId;
      this.form.planNo = plan.planNo;
      this.form.scheduleId = plan.id;
      this.form.bomId = plan.bomId;
      this.form.productId = plan.materialId;
      this.form.productName = plan.materialName;
      this.form.materialModel = plan.materialModel;
      this.form.materialSpecification = plan.materialSpecification;
      this.form.materialUnit = plan.materialUnit;
      this.form.requireQuantity = plan.requireQuantity;
      this.form.finishQuantity = plan.productQuantity;
      this.form.scheduleDate = plan.productDate;
      this.mesProductJobMaterialList = [];
      getProductSchedule(this.form.scheduleId).then((res) => {
        let list = res.data.mesProductScheduleMaterialList;
        list.forEach((material) => {
          let obj = {};
          obj.materialId = material.materialId;
          obj.materialCode = material.materialCode;
          obj.materialName = material.materialName;
          obj.materialModel = material.materialModel;
          obj.materialSpecification = material.materialSpecification;
          obj.materialUnit = material.materialUnit;
          obj.requireQuantity = material.requireQuantity;
          obj.usageQuantity = "";
          this.mesProductJobMaterialList.push(obj);
          this.calculateUsage();
        });
      });
      this.scheduleOpen = false;
    },
    handleScheduleCancel() {
      this.scheduleOpen = false;
    },
    //计算所需物料数量
    calculateUsage() {
      if (this.form.bomId && this.form.productQuantity) {
        getMesBom(this.form.bomId).then((res) => {
          let list = res.data.mesProductBomDetailList;
          this.mesProductJobMaterialList.forEach((material) => {
            let bom = list.filter(
              (item) => item.materialId == material.materialId
            );
            let requireQuantity = bom[0].usage * this.form.productQuantity;
            material.usageQuantity = requireQuantity;
          });
        });
      }
    },
    /** 查询生产作业列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeStartTime && "" != this.daterangeStartTime) {
        this.queryParams.params["beginStartTime"] = this.daterangeStartTime[0];
        this.queryParams.params["endStartTime"] = this.daterangeStartTime[1];
      }
      if (null != this.daterangeEndTime && "" != this.daterangeEndTime) {
        this.queryParams.params["beginEndTime"] = this.daterangeEndTime[0];
        this.queryParams.params["endEndTime"] = this.daterangeEndTime[1];
      }
      listProductJob(this.queryParams).then((response) => {
        this.productJobList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 作业状态：0未执行1执行中2已完成3异常字典翻译
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
        bomId: null,
        name: null,
        planId: null,
        scheduleId: null,
        scheduleDate: null,
        productId: null,
        startTime: null,
        endTime: null,
        productQuantity: null,
        status: null,
        remark: null,
        mesProductJobMaterialList: [],
      };
      this.mesProductJobMaterialList = [];
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.daterangeStartTime = [];
      this.daterangeEndTime = [];
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
      this.title = "添加生产作业";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getProductJob(id).then((response) => {
        this.form = response.data;
        this.mesProductJobMaterialList =
          response.data.mesProductJobMaterialList;
        getProductSchedule(this.form.scheduleId).then((res) => {
          this.form.bomId = res.data.bomId;
          this.form.scheduleDate = res.data.productDate;
          this.calculateUsage();
        });
        this.open = true;
        this.title = "修改生产作业";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateProductJob(this.form).then((response) => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addProductJob(this.form).then((response) => {
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
        '是否确认删除生产作业编号为"' + ids + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delProductJob(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        });
    },
    /** 生产作业物料序号 */
    rowMesProductJobMaterialIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm("是否确认导出所有生产作业数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return exportProductJob(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        });
    },
  },
};
</script>
