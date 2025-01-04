<template>
  <div class="app-container">
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body @open="handleOpen"
      :close-on-click-modal="false" :show-close="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="90px" :inline="true">
        <el-form-item label="单据号" prop="purchasePlanNo">
          <el-input v-model="form.purchasePlanNo" placeholder="" disabled />
        </el-form-item>
        <el-form-item label="申请人" prop="applyUser">
          <el-select v-model="form.applyUser" placeholder="" clearable filterable disabled>
            <el-option v-for="dict in applyUserOptions" :key="dict.dictValue" :label="dict.dictLabel"
              :value="dict.dictValue"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="申请日期" prop="applyTime">
          <el-date-picker clearable size="small" v-model="form.applyTime" type="date" value-format="yyyy-MM-dd"
            placeholder="" disabled>
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
        <el-form-item label="审核日期" prop="auditTime">
          <el-date-picker clearable size="small" v-model="form.auditTime" type="date" value-format="yyyy-MM-dd"
            placeholder="" disabled>
          </el-date-picker>
        </el-form-item>
        <el-form-item label="审核状态" prop="auditStatus">
          <el-select v-model="form.auditStatus" placeholder="" clearable filterable disabled>
            <el-option v-for="dict in auditStatusOptions" :key="dict.dictValue" :label="dict.dictLabel"
              :value="dict.dictValue"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="审核意见" prop="auditComment">
          <el-input v-model="form.auditComment" placeholder="" disabled />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="" disabled />
        </el-form-item>
        <el-divider content-position="center">采购计划详细信息</el-divider>
        <el-table :data="scmPurchasePlanDetailList" :row-class-name="rowScmPurchasePlanDetailIndex" ref="scmPurchasePlanDetail">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center" prop="index" width="50" />
          <el-table-column label="物料" prop="materialName" width="300">
          </el-table-column>
          <el-table-column label="型号" prop="materialModel"></el-table-column>
          <el-table-column label="规格" prop="materialSpecification"></el-table-column>
          <el-table-column label="单位" prop="materialUnit"></el-table-column>
          <el-table-column label="采购数量" prop="quantity" width="150"></el-table-column>
          <el-table-column label="需求数量" prop="requireQuantity" width="150"></el-table-column>
          <el-table-column label="需求日期" prop="requireDate" width="240"></el-table-column>
          <el-table-column label="备注" prop="detailRemark" width="150"></el-table-column>
        </el-table>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button v-no-more-click @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import {
    getPlan,
  } from "@/api/purchase/plan";

  export default {
    name: "PurchasePlanDetail",
    components: {},
    props: {
      // 是否显示弹出层
      open: {
        type: Boolean,
        default: false,
      },
      planId: null,
    },
    data() {
      return {
        currentRowIndex: null,
        // 遮罩层
        loading: true,
        // 采购计划详细表格数据
        scmPurchasePlanDetailList: [],
        // 弹出层标题
        title: "",
        // 申请人字典
        applyUserOptions: [],
        // 申请时间时间范围
        daterangeApplyTime: [],
        // 申请状态：0未提交1待审核字典
        applyStatusOptions: [],
        // 审核人字典
        auditUserOptions: [],
        // 审核日期时间范围
        daterangeAuditTime: [],
        // 审核状态：0审核未通过1审核通过字典
        auditStatusOptions: [],
        // 表单参数
        form: {},
        // 表单校验
        rules: {}
      };
    },
    created() {
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
      'scmPurchasePlanDetailList': {
        handler(newVal, oldVal) {
          this.form.scmPurchasePlanDetailList = newVal;
        },
        immediate: true,
        deep: true,
      }
    },
    methods: {
      handleOpen() {
        this.getInfo();
      },
      // 申请人字典翻译
      applyUserFormat(row, column) {
        return this.selectDictLabel(this.applyUserOptions, row.applyUser);
      },
      // 申请状态：0未提交1待审核字典翻译
      applyStatusFormat(row, column) {
        return this.selectDictLabel(this.applyStatusOptions, row.applyStatus);
      },
      // 审核人字典翻译
      auditUserFormat(row, column) {
        return this.selectDictLabel(this.auditUserOptions, row.auditUser);
      },
      // 审核状态：0审核未通过1审核通过字典翻译
      auditStatusFormat(row, column) {
        return this.selectDictLabel(this.auditStatusOptions, row.auditStatus);
      },
      // 取消按钮
      cancel() {
        this.reset();
        this.$emit('onPlanDetailCancel');
      },
      // 表单重置
      reset() {
        this.form = {
          id: null,
          purchasePlanNo: null,
          applyUser: null,
          applyTime: null,
          applyStatus: null,
          auditUser: null,
          auditTime: null,
          auditStatus: null,
          auditComment: null,
          remark: null,
          scmPurchasePlanDetailList: [],
        };
        this.scmPurchasePlanDetailList = [];
        this.resetForm("form");
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams.pageNum = 1;
        this.getList();
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.daterangeApplyTime = [];
        this.daterangeAuditTime = [];
        this.resetForm("queryForm");
        this.handleQuery();
      },
      /** 采购计划详细序号 */
      rowScmPurchasePlanDetailIndex({
        row,
        rowIndex
      }) {
        row.index = rowIndex + 1;
      },

      /** 获取详情 */
      getInfo() {
        this.reset();
        getPlan(this.planId).then(response => {
          this.form = response.data;
          this.scmPurchasePlanDetailList = response.data.scmPurchasePlanDetailList;
          this.title = "查看采购计划";
        });
      },
    }
  };

</script>
