<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="流水号" prop="serialNo">
        <el-input v-model="queryParams.serialNo" placeholder="请输入流水号" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="领料单号" prop="materialRequisitionNo">
        <el-input v-model="queryParams.materialRequisitionNo" placeholder="请输入领料单号" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="物料名称" prop="materialName">
        <el-input v-model="queryParams.materialName" placeholder="请输入物料物料名称" clearable size="small"
          @keyup.enter.native="handleQuery" />
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
          v-hasPermi="['mesCheck:materialCheck:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-no-more-click type="success" plain icon="el-icon-edit" size="mini" :disabled="single"
          @click="handleUpdate" v-hasPermi="['mesCheck:materialCheck:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-no-more-click type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple"
          @click="handleDelete" v-hasPermi="['mesCheck:materialCheck:remove']">删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="materialCheckList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="流水号" align="center" prop="serialNo" />
      <el-table-column label="领料单号" align="center" prop="materialRequisitionNo" />
      <el-table-column label="物料名称" align="center" prop="materialName" />
      <el-table-column label="规格" align="center" prop="materialSpecification" />
      <el-table-column label="型号" align="center" prop="materialModel" />
      <el-table-column label="单位" align="center" prop="materialUnit" />
      <el-table-column label="需求数量" align="center" prop="requireQuantity" />
      <el-table-column label="已领数量" align="center" prop="receiveQuantity" />
      <el-table-column label="抽检数量" align="center" prop="checkQuantity" />
      <el-table-column label="检验项" align="center" prop="checkItemName" />
      <el-table-column label="标准值上限" align="center" prop="maxValue" />
      <el-table-column label="标准值下限" align="center" prop="minValue" />
      <el-table-column label="检验值" align="center" prop="checkValue" />
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
            v-hasPermi="['mesCheck:materialCheck:edit']">修改</el-button>
          <el-button v-no-more-click size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['mesCheck:materialCheck:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改物料检验单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="流水号" prop="serialNo">
          <el-input v-model="form.serialNo" placeholder="-系统自动生成" disabled />
        </el-form-item>
        <el-form-item label="领料编号" prop="materialRequisitionNo">
          <el-input v-model="form.materialRequisitionNo" readonly>
            <el-button v-no-more-click slot="append" @click="handleSelectRequisition">选择</el-button>
          </el-input>
        </el-form-item>
        <el-form-item label="物料名称" prop="materialId">
          <el-select v-model="form.materialId" filterable placeholder="请选择" @change="handleChangeMaterial">
            <el-option v-for="item in materialOpts" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="需求数量" prop="requireQuantity">
          <el-input v-model="form.requireQuantity" placeholder="" readonly />
        </el-form-item>
        <el-form-item label="已领数量" prop="receiveQuantity">
          <el-input v-model="form.receiveQuantity" placeholder="" readonly />
        </el-form-item>
        <el-form-item label="抽检数量" prop="checkQuantity">
          <el-input v-model.number="form.checkQuantity" placeholder="请输入抽检数量" />
        </el-form-item>
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
    <!--选择已领料未检验的领料单-->
    <RequisitionSelect status="2" :open="requisitionSelectOpen" @onSelected="handleRequisitionSelected"
      @onCancel="handleRequisitionCancel"></RequisitionSelect>
    <!--选择检测标准 checkType="1" 来料检查-->
    <StandardSelect :open="standardSelectOpen" :materialId="materialId" checkType="1"
      @onStandardCancel="handleStandardCancel" @onStandardSelected="handleStandardSelected" v-if="materialId">
    </StandardSelect>
  </div>
</template>

<script>
import {
  listMaterialCheck,
  getMaterialCheck,
  delMaterialCheck,
  addMaterialCheck,
  updateMaterialCheck,
  exportMaterialCheck
} from "@/api/mesCheck/materialCheck";

import {
  getRequisitionApply
} from "@/api/mesProduct/requisitionApply";
import StandardSelect from "@/components/MesCheck/StandardSelect";
import RequisitionSelect from "@/components/product/RequisitionSelect";


export default {
  name: "MaterialCheck",
  components: {
    StandardSelect,
    RequisitionSelect
  },
  data() {
    let validatePass = (rule, value, callback) => {
      if (value > this.form.receiveQuantity) {
        callback(new Error('抽检数量不能大于已领数量'));
      } else if (value < 1) {
        callback(new Error('抽检数量不能小于1'));
      } else {
        callback();
      }
    };
    return {
      materialRequisitionDetailList: [],
      materialOpts: [],
      requisitionSelectOpen: false,
      materialRequisitionId: null,
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
      // 物料检验单表格数据
      materialCheckList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 检验结果字典
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
        materialRequisitionId: null,
        materialRequisitionNo: null,
        materialId: null,
        checkStandardId: null,
        requireQuantity: null,
        receiveQuantity: null,
        checkQuantity: null,
        checkValue: null,
        maxValue: null,
        minValue: null,
        checkResult: null,
        checkUser: null,
        checkDate: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        materialRequisitionId: [{
          required: true,
          message: "领料单编号不能为空",
          trigger: "blur"
        }],
        materialRequisitionNo: [{
          required: true,
          message: "领料单流水不能为空",
          trigger: "blur"
        }],
        materialId: [{
          required: true,
          message: "物料档案编号不能为空",
          trigger: "blur"
        }],
        checkItemName: [{
          required: true,
          message: "请选择检验标准",
          trigger: "blur"
        }],
        requireQuantity: [{
          required: true,
          message: "需求数量不能为空",
          trigger: "blur"
        }],
        receiveQuantity: [{
          required: true,
          message: "已领数量不能为空",
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
        checkResult: [{
          required: true,
          message: "检验结果不能为空",
          trigger: "change"
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
    //选择领料单回调函数
    handleRequisitionSelected(row) {
      this.form.materialRequisitionId = row.id;
      this.form.materialRequisitionNo = row.serialNo;
      this.materialRequisitionId = row.id;
      this.getRequisitionDetailList();
      this.requisitionSelectOpen = false;
    },
    //选择领料单按钮
    handleSelectRequisition() {
      this.requisitionSelectOpen = true;
    },
    //取消选择
    handleRequisitionCancel() {
      this.requisitionSelectOpen = false;
    },

    getRequisitionDetailList() {
      getRequisitionApply(this.materialRequisitionId).then(res => {
        console.log("领料申请详情数据:" + JSON.stringify(res))
        let list = res.data.mesMaterialRequisitionDetailList;
        if (list && list.length == 0) {
          this.$message.error('领料单子项不能为空，请检查领料单！');
        }
        this.materialRequisitionDetailList = res.data.mesMaterialRequisitionDetailList;
        this.materialOpts = [];
        list.forEach(element => {
          this.materialOpts.push({
            value: element.materialId,
            label: element.materialName,
          })
        });
      });
    },

    handleChangeMaterial(materialId) {
      if (materialId) {
        this.materialId = materialId;
        this.form.materialId = materialId;
        for (let item of this.materialRequisitionDetailList) {
          if (item.materialId == materialId) {
            this.form.requireQuantity = item.requireQuantity;
            this.form.receiveQuantity = item.receiveQuantity
            break;
          }
        }
      } else {
        this.$message.error('请先选择领料单');
      }
    },

    //选择检测标准函数
    handleSelectStandard() {
      if (this.materialId) {
        console.log("现在：" + this.materialId)
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
    /** 查询物料检验单列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeCheckDate && '' != this.daterangeCheckDate) {
        this.queryParams.params["beginCheckDate"] = this.daterangeCheckDate[0];
        this.queryParams.params["endCheckDate"] = this.daterangeCheckDate[1];
      }
      listMaterialCheck(this.queryParams).then(response => {
        this.materialCheckList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 检验结果字典翻译
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
        materialRequisitionId: null,
        materialRequisitionNo: null,
        materialId: null,
        checkItemName: null,
        checkStandardId: null,
        requireQuantity: null,
        receiveQuantity: null,
        checkQuantity: null,
        checkValue: null,
        maxValue: null,
        minValue: null,
        checkResult: null,
        remark: null,
        checkUser: null,
        checkDate: null,
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
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加物料检验单";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getMaterialCheck(id).then(response => {
        this.form = response.data;
        this.materialRequisitionId = response.data.materialRequisitionId;
        this.getRequisitionDetailList();
        this.open = true;
        this.title = "修改物料检验单";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateMaterialCheck(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addMaterialCheck(this.form).then(response => {
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
      this.$confirm('是否确认删除物料检验单编号为"' + ids + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delMaterialCheck(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有物料检验单数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return exportMaterialCheck(queryParams);
      }).then(response => {
        this.download(response.msg);
      })
    }
  }
};

</script>
