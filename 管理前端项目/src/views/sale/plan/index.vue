<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="计划单号" prop="planNo">
        <el-input v-model="queryParams.planNo" placeholder="请输入计划单号" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="计划标题" prop="title">
        <el-input v-model="queryParams.title" placeholder="请输入计划标题" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="计划类型" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择计划类型" clearable filterable size="small">
          <el-option v-for="dict in typeOptions" :key="dict.dictValue" :label="dict.dictLabel"
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
          v-hasPermi="['sale:plan:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-no-more-click type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['sale:plan:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-no-more-click type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['sale:plan:remove']">删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="planList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="计划单号" align="center" prop="planNo" />
      <el-table-column label="计划标题" align="center" prop="title" />
      <el-table-column label="计划类型" align="center" prop="type" :formatter="typeFormat" />
      <el-table-column label="起始日期" align="center" prop="startDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结束日期" align="center" prop="endDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-no-more-click size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['sale:plan:edit']">修改</el-button>
          <el-button v-no-more-click size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['sale:plan:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改销售计划对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px" :inline="true">
        <el-form-item label="计划单号" prop="planNo">
          <el-input v-model="form.planNo" placeholder="-系统自动生成" disabled />
        </el-form-item>
        <el-form-item label="计划标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入计划标题" />
        </el-form-item>
        <el-form-item label="计划类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择计划类型" clearable filterable>
            <el-option v-for="dict in typeOptions" :key="dict.dictValue" :label="dict.dictLabel"
              :value="dict.dictValue"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="起始日期" prop="startDate">
          <el-date-picker clearable size="small" v-model="form.startDate" type="date" value-format="yyyy-MM-dd"
            placeholder="选择起始日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束日期" prop="endDate">
          <el-date-picker clearable size="small" v-model="form.endDate" type="date" value-format="yyyy-MM-dd"
            placeholder="选择结束日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-divider content-position="center">指标配置信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button v-no-more-click type="primary" icon="el-icon-plus" size="mini" @click="handleAddScmSalePlanDetail">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button v-no-more-click type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteScmSalePlanDetail">删除
            </el-button>
          </el-col>
        </el-row>
        <el-table :data="scmSalePlanDetailList" :row-class-name="rowScmSalePlanDetailIndex"
          @selection-change="handleScmSalePlanDetailSelectionChange" ref="scmSalePlanDetail">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center" prop="index" width="50" />
          <el-table-column label="产品" prop="materialName" width="250">
            <template slot-scope="scope">
              <el-form-item :prop="'scmSalePlanDetailList.'+scope.$index+'.materialName'" :rules="rules.materialName">
                <el-input v-model="scope.row.materialName" placeholder="请选择产品" disabled>
                  <el-button v-no-more-click slot="append" @click="handleSelectMaterial(scope.$index)">选择</el-button>
                </el-input>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="型号" prop="materialModel"></el-table-column>
          <el-table-column label="规格" prop="materialSpecification" width="100"></el-table-column>
          <el-table-column label="单位" prop="materialUnit" width="50"></el-table-column>
          <el-table-column label="单价" prop="materialPrice"></el-table-column>
          <el-table-column label="销售额" prop="saleAmount" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'scmSalePlanDetailList.'+scope.$index+'.saleAmount'" :rules="rules.saleAmount">
                <el-input v-model="scope.row.saleAmount" placeholder="请输入销售额" />
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="备注" prop="detailRemark" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'scmSalePlanDetailList.'+scope.$index+'.detailRemark'" :rules="rules.detailRemark">
                <el-input v-model="scope.row.detailRemark" placeholder="请输入备注" />
              </el-form-item>
            </template>
          </el-table-column>
        </el-table>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button v-no-more-click type="primary" @click="submitForm">确 定</el-button>
        <el-button v-no-more-click @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!--选择物料档案-->
    <MaterialSelect :open="materialSelectOpen" @onCancel="handleMaterialCancel" @onSelected="handleMaterialSelected">
    </MaterialSelect>
  </div>
</template>

<script>
  import {
    listPlan,
    getPlan,
    delPlan,
    addPlan,
    updatePlan,
    exportPlan
  } from "@/api/sale/plan";
  import MaterialSelect from "@/components/Material/MaterialSelect";
  export default {
    name: "SalePlan",
    components: {
      MaterialSelect,
    },
    data() {
      return {
        material: null,
        currentRowIndex: null,
        materialSelectOpen: false,
        // 遮罩层
        loading: true,
        // 选中数组
        ids: [],
        // 子表选中数据
        checkedScmSalePlanDetail: [],
        // 非单个禁用
        single: true,
        // 非多个禁用
        multiple: true,
        // 显示搜索条件
        showSearch: true,
        // 总条数
        total: 0,
        // 销售计划表格数据
        planList: [],
        // 指标配置表格数据
        scmSalePlanDetailList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 计划类型：1年度计划 2季度计划 3月计划字典
        typeOptions: [],
        // 创建人字典
        createByOptions: [],
        // 创建时间时间范围
        daterangeCreateTime: [],
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          planNo: null,
          title: null,
          type: null,
          createBy: null,
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          title: [{
            required: true,
            message: "计划标题不能为空",
            trigger: "blur"
          },{
            max: 20,
            message: "长度不能超过20个字符",
            trigger: "blur"
          }],
          type: [{
            required: true,
            message: "计划类型不能为空",
            trigger: "change"
          }],
          startDate: [{
            required: true,
            message: "起始日期不能为空",
            trigger: "blur"
          }],
          endDate: [{
            required: true,
            message: "结束日期不能为空",
            trigger: "blur"
          }],
          materialName: [{
            required: true,
            message: "产品不能为空",
            trigger: "blur"
          }],
          saleAmount: [{
            required: true,
            message: "销售额为空",
            trigger: "blur"
          }],

        }
      };
    },
    created() {
      this.getList();
      this.getDicts("sale_plan_type").then(response => {
        this.typeOptions = response.data;
      });
      this.getQueryData("query_user").then(res => {
        this.createByOptions = res.data;
      });
    },
    watch: {
      'scmSalePlanDetailList': {
        handler(newVal, oldVal) {
          this.form.scmSalePlanDetailList = newVal;
        },
        immediate: true,
        deep: true,
      }
    },
    methods: {
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
          this.scmSalePlanDetailList[this.currentRowIndex].materialId = this.material.id;
          this.scmSalePlanDetailList[this.currentRowIndex].materialName = this.material.name;
          this.scmSalePlanDetailList[this.currentRowIndex].materialCode = this.material.code;
          this.scmSalePlanDetailList[this.currentRowIndex].materialModel = this.material.model;
          this.scmSalePlanDetailList[this.currentRowIndex].materialSpecification = this.material.specification;
          this.scmSalePlanDetailList[this.currentRowIndex].materialUnit = this.material.unit;
          this.scmSalePlanDetailList[this.currentRowIndex].materialPrice = this.material.price;
        }
        this.materialSelectOpen = false;
      },
      /** 查询销售计划列表 */
      getList() {
        this.loading = true;
        this.queryParams.params = {};
        if (null != this.daterangeCreateTime && '' != this.daterangeCreateTime) {
          this.queryParams.params["beginCreateTime"] = this.daterangeCreateTime[0];
          this.queryParams.params["endCreateTime"] = this.daterangeCreateTime[1];
        }
        listPlan(this.queryParams).then(response => {
          this.planList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },
      // 计划类型：1年度计划 2季度计划 3月计划字典翻译
      typeFormat(row, column) {
        return this.selectDictLabel(this.typeOptions, row.type);
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
          planNo: null,
          title: null,
          type: null,
          startDate: null,
          endDate: null,
          createBy: null,
          createTime: null,
          remark: null,
          scmSalePlanDetailList: [],
        };
        this.scmSalePlanDetailList = [];
        this.resetForm("form");
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams.pageNum = 1;
        this.getList();
      },
      /** 重置按钮操作 */
      resetQuery() {
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
        this.title = "添加销售计划";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids
        getPlan(id).then(response => {
          this.form = response.data;
          this.scmSalePlanDetailList = response.data.scmSalePlanDetailList;
          this.open = true;
          this.title = "修改销售计划";
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (!this.scmSalePlanDetailList || this.scmSalePlanDetailList.length == 0) {
              this.msgError('请添加指标配置信息');
              return false;
            }
            if (this.form.id != null) {
              updatePlan(this.form).then(response => {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addPlan(this.form).then(response => {
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
        this.$confirm('是否确认删除销售计划编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return delPlan(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
      },
      /** 指标配置序号 */
      rowScmSalePlanDetailIndex({
        row,
        rowIndex
      }) {
        row.index = rowIndex + 1;
      },
      /** 指标配置添加按钮操作 */
      handleAddScmSalePlanDetail() {
        let obj = {};
        obj.materialId = "";
        obj.saleAmount = "";
        obj.detailRemark = "";
        obj.materialName = "";
        obj.materialModel = "";
        obj.materialSpecification = "";
        obj.materialUnit = "";
        obj.materialPrice = "";
        this.scmSalePlanDetailList.push(obj);
      },
      /** 指标配置删除按钮操作 */
      handleDeleteScmSalePlanDetail() {
        if (this.checkedScmSalePlanDetail.length == 0) {
          this.$alert("请先选择要删除的指标配置数据", "提示", {
            confirmButtonText: "确定",
          });
        } else {
          this.scmSalePlanDetailList.splice(this.checkedScmSalePlanDetail[0].index - 1, 1);
        }
      },
      /** 单选框选中数据 */
      handleScmSalePlanDetailSelectionChange(selection) {
        if (selection.length > 1) {
          this.$refs.scmSalePlanDetail.clearSelection();
          this.$refs.scmSalePlanDetail.toggleRowSelection(selection.pop());
        } else {
          this.checkedScmSalePlanDetail = selection;
        }
      },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams;
        this.$confirm('是否确认导出所有销售计划数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return exportPlan(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
      }
    }
  };

</script>
