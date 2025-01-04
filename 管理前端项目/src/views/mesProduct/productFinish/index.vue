<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="生产计划" prop="planNo">
        <el-input v-model="queryParams.planNo" placeholder="请输入生产计划" clearable size="small" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="生产作业" prop="jobName">
        <el-input v-model="queryParams.jobName" placeholder="请输入生产作业" clearable size="small" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="产品" prop="materialName">
        <el-input v-model="queryParams.materialName" placeholder="请输入产品" clearable size="small" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="完工日期">
        <el-date-picker v-model="daterangeFinishDate" size="small" style="width: 240px" value-format="yyyy-MM-dd" type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="生产批号" prop="batchNo">
        <el-input v-model="queryParams.batchNo" placeholder="请输入生产批号" clearable size="small" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable filterable size="small">
          <el-option v-for="dict in statusOptions" :key="dict.dictValue" :label="dict.dictLabel" :value="dict.dictValue" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button v-no-more-click type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button v-no-more-click icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button v-no-more-click type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete" v-hasPermi="['mesProduct:productFinish:remove']">删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="productFinishList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="单号" align="center" prop="serialNo" />
      <el-table-column label="生产计划" align="center" prop="planNo" />
      <el-table-column label="生产作业" align="center" prop="jobName" />
      <el-table-column label="产品" align="center" prop="materialName" />
      <el-table-column label="型号" prop="materialModel">
      </el-table-column>
      <el-table-column label="规格" prop="materialSpecification">
      </el-table-column>
      <el-table-column label="单位" prop="materialUnit" />
      <el-table-column label="完工日期" align="center" prop="finishDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.finishDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="生产数量" align="center" prop="finishQuantity" />
      <el-table-column label="生产批号" align="center" prop="batchNo" />
      <el-table-column label="状态" align="center" prop="status" :formatter="statusFormat" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-no-more-click v-if="scope.row.status=='0'" size="mini" type="text" icon="el-icon-check" @click="handleUpdateStatus(scope.row,'3')" v-hasPermi="['mesProduct:productFinish:edit']">检验合格</el-button>
          <el-button v-no-more-click v-if="scope.row.status=='0'" size="mini" type="text" icon="el-icon-close" @click="handleUpdateStatus(scope.row,'4')" v-hasPermi="['mesProduct:productFinish:edit']">检验未合格</el-button>
          <el-button v-no-more-click size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['mesProduct:productFinish:edit']">详情</el-button>
          <el-button v-no-more-click size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)" v-hasPermi="['mesProduct:productFinish:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <!-- 添加或修改生产完工单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="单号" prop="serialNo">
          <el-input v-model="form.serialNo" placeholder="" disabled/>
        </el-form-item>
        <el-form-item label="生产计划" prop="planNo">
          <el-input v-model="form.planId" placeholder="请输入生产计划" disabled />
        </el-form-item>
        <el-form-item label="生产作业" prop="jobName">
          <el-input v-model="form.jobName" placeholder="请输入生产作业" disabled />
        </el-form-item>
        <el-form-item label="产品" prop="materialName">
          <el-input v-model="form.materialName" placeholder="请输入产品" disabled />
        </el-form-item>
        <el-form-item label="完工日期" prop="finishDate">
          <el-date-picker clearable size="small" v-model="form.finishDate" type="date" value-format="yyyy-MM-dd" placeholder="选择完工日期" disabled>
          </el-date-picker>
        </el-form-item>
        <el-form-item label="生产数量" prop="finishQuantity">
          <el-input v-model="form.finishQuantity" placeholder="请输入生产数量" disabled />
        </el-form-item>
        <el-form-item label="生产批号" prop="batchNo">
          <el-input v-model="form.batchNo" placeholder="请输入生产批号" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择状态" clearable filterable disabled>
            <el-option v-for="dict in statusOptions" :key="dict.dictValue" :label="dict.dictLabel" :value="dict.dictValue"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button v-no-more-click type="primary" @click="submitForm">确 定</el-button>
        <el-button v-no-more-click @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listProductFinish, getProductFinish, delProductFinish, addProductFinish, updateProductFinish, exportProductFinish, updateProductFinishStatus } from "@/api/mesProduct/productFinish";

export default {
  name: "ProductFinish",
  components: {
  },
  data() {
    return {
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
      // 生产完工单表格数据
      productFinishList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 完工日期时间范围
      daterangeFinishDate: [],
      statusOptions:[],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        planId: null,
        planNo: null,
        scheduleId: null,
        jobId: null,
        jobName: null,
        materialId: null,
        materialName: null,
        finishDate: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        batchNo: [
          { required: true, message: "生产批号不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("product_finish_status").then(response => {
      this.statusOptions = response.data;
    });
  },
  methods: {
    handleUpdateStatus(row, status) {
      let params = {
        id: row.id,
        status: status,
      };
      updateProductFinishStatus(params).then(res => {
        this.msgSuccess('提交成功');
        this.getList();
      })
    },
    /** 查询生产完工单列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeFinishDate && '' != this.daterangeFinishDate) {
        this.queryParams.params["beginFinishDate"] = this.daterangeFinishDate[0];
        this.queryParams.params["endFinishDate"] = this.daterangeFinishDate[1];
      }
      listProductFinish(this.queryParams).then(response => {
        this.productFinishList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 状态字典翻译
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
        serialNo: null,
        planId: null,
        scheduleId: null,
        jobId: null,
        materialId: null,
        finishDate: null,
        finishQuantity: null,
        batchNo: null,
        status: null,
        remark: null,
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
      this.daterangeFinishDate = [];
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
      this.title = "添加生产完工单";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getProductFinish(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改生产完工单";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateProductFinish(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addProductFinish(this.form).then(response => {
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
      this.$confirm('是否确认删除生产完工单编号为"' + ids + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delProductFinish(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有生产完工单数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return exportProductFinish(queryParams);
      }).then(response => {
        this.download(response.msg);
      })
    }
  }
};
</script>
