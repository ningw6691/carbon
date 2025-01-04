<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="90px">
      <el-form-item label="供应商名称" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入供应商名称" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="联系人" prop="contact">
        <el-input v-model="queryParams.contact" placeholder="请输入联系人" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <!-- <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable filterable size="small">
          <el-option
            v-for="dict in statusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item> -->
      <!-- <el-form-item label="供应料品" prop="queryMaterialCategoryId">
        <el-select v-model="queryParams.queryMaterialCategoryId" placeholder="请选择供应物料" clearable filterable
          size="small">
          <el-option v-for="dict in materialCategoryOpts" :key="dict.dictValue" :label="dict.dictLabel"
            :value="dict.dictValue" />
        </el-select>
      </el-form-item> -->
      <el-form-item>
        <el-button v-no-more-click type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button v-no-more-click icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button v-no-more-click type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['purchase:supplier:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-no-more-click type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['purchase:supplier:edit']">修改</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="supplierList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" width="60" />
      <el-table-column label="供应商名称" align="center" prop="name" />
      <el-table-column label="公司地址" align="center" prop="addr" />
      <el-table-column label="联系人" align="center" prop="contact" width="80" />
      <el-table-column label="联系电话" align="center" prop="contactTel" width="120" />
      <el-table-column label="申请状态" align="center" prop="applyStatus" :formatter="applyStatusFormat" />
      <el-table-column label="状态" align="center" prop="status" width="80">
        <template #default="scope">
          <el-switch v-model="scope.row.status" active-value="1" inactive-value="0"
            @change="handleStatusChange(scope.row)"></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right" width="80">
        <template slot-scope="scope">
          <el-button v-no-more-click size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['purchase:supplier:edit']">修改</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改供应商对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="供应商名称" prop="name">
              <el-input v-model.trim="form.name" placeholder="请输入供应商名称"
                :disabled="form.auditStatus!='1'&&form.applyStatus!='1'?false:true" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="简称" prop="shortName">
              <el-input v-model.trim="form.shortName" placeholder="请输入简称"
                :disabled="form.auditStatus!='1'&&form.applyStatus!='1'?false:true" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="公司地址" prop="addr">
              <el-input v-model.trim="form.addr" placeholder="请输入地址"
                :disabled="form.auditStatus!='1'&&form.applyStatus!='1'?false:true" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="公司电话" prop="tel">
              <el-input v-model="form.tel" placeholder="请输入公司电话"
                :disabled="form.auditStatus!='1'&&form.applyStatus!='1'?false:true" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="联系人" prop="contact">
              <el-input v-model.trim="form.contact" placeholder="请输入联系人"
                :disabled="form.auditStatus!='1'&&form.applyStatus!='1'?false:true" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系电话" prop="contactTel">
              <el-input v-model="form.contactTel" placeholder="请输入联系电话"
                :disabled="form.auditStatus!='1'&&form.applyStatus!='1'?false:true" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="开户银行" prop="bank">
              <el-input v-model="form.bank" placeholder="请输入开户银行"
                :disabled="form.auditStatus!='1'&&form.applyStatus!='1'?false:true" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="银行账号" prop="account">
              <el-input v-model="form.account" placeholder="请输入银行账号"
                :disabled="form.auditStatus!='1'&&form.applyStatus!='1'?false:true" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="税号" prop="taxNo">
              <el-input v-model="form.taxNo" placeholder="请输入税号"
                :disabled="form.auditStatus!='1'&&form.applyStatus!='1'?false:true" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="供应商分类" prop="type">
              <el-select v-model="form.type" placeholder="请选择供应商分类" clearable filterable
                :disabled="form.auditStatus!='1'&&form.applyStatus!='1'?false:true">
                <el-option v-for="dict in typeOptions" :key="dict.dictValue" :label="dict.dictLabel"
                  :value="dict.dictValue"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="供应商等级" prop="level">
              <el-select v-model="form.level" placeholder="请选择供应商等级" clearable filterable
                :disabled="form.auditStatus!='1'&&form.applyStatus!='1'?false:true">
                <el-option v-for="dict in levelOptions" :key="dict.dictValue" :label="dict.dictLabel"
                  :value="dict.dictValue"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder=""
            :disabled="form.auditStatus!='1'&&form.applyStatus!='1'?false:true" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button v-no-more-click v-if="form.auditStatus!='1'&&form.applyStatus!='1'" type="primary" @click="saveForm">保 存</el-button>
        <el-button v-no-more-click v-if="form.auditStatus!='1'&&form.applyStatus!='1'" type="warning" @click="submitForm">提 交
        </el-button>
        <el-button v-no-more-click v-if="form.auditStatus!='1'&&form.applyStatus!='1'" @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import {
    listSupplier,
    getSupplier,
    delSupplier,
    addSupplier,
    updateSupplier,
    exportSupplier,
    submitSupplier
  } from "@/api/purchase/supplier";

  export default {
    name: "Supplier",
    components: {},
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
        // 供应商表格数据
        supplierList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 供应商分类字典
        typeOptions: [],
        // 供应商等级字典
        levelOptions: [],
        // 审核人字典
        auditUserOptions: [],
        // 审核时间时间范围
        daterangeAuditTime: [],
        // 审核状态字典
        auditStatusOptions: [],
        // 状态字典
        statusOptions: [],
        // 申请状态字典
        applyStatusOptions: [],
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          supplierNo: null,
          name: null,
          shortName: null,
          tel: null,
          bank: null,
          account: null,
          taxNo: null,
          contact: null,
          auditUser: null,
          auditTime: null,
          auditStatus: null,
          status: null,
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          name: [{
            required: true,
            message: "供应商名称不能为空",
            trigger: "blur"
          }, {
            max: 40,
            message: "长度不能超过40个字符",
            trigger: "blur"
          }],
          shortName: [{
            max: 20,
            message: "长度不能超过20个字符",
            trigger: "blur"
          }],
          tel: [{
            max: 20,
            message: "长度不能超过20个字符",
            trigger: "blur"
          }],
          addr: [{
            max: 20,
            message: "长度不能超过20个字符",
            trigger: "blur"
          }],
          contact: [{
            required: true,
            message: "联系人不能为空",
            trigger: "blur"
          }, {
            max: 20,
            message: "长度不能超过20个字符",
            trigger: "blur"
          }],
          contactTel: [{
              required: true,
              message: "联系电话不能为空",
              trigger: "blur"
            },
            {
              max: 20,
              message: "长度不能超过20个字符",
              trigger: "blur"
            }
          ],
          bank: [{
            max: 20,
            message: "长度不能超过20个字符",
            trigger: "blur"
          }],
          account: [{
            max: 20,
            message: "长度不能超过20个字符",
            trigger: "blur"
          }],
          taxNo: [{
            max: 20,
            message: "长度不能超过20个字符",
            trigger: "blur"
          }],
        },
      };
    },
    created() {

      this.getDicts("scm_supplier_type").then(response => {
        this.typeOptions = response.data;
      });
      this.getDicts("scm_supplier_level").then(response => {
        this.levelOptions = response.data;
      });
      this.getQueryData("query_user").then(res => {
        this.auditUserOptions = res.data;
      });
      this.getDicts("apply_status").then(response => {
        this.applyStatusOptions = response.data;
      });
      this.getDicts("audit_status").then(response => {
        this.auditStatusOptions = response.data;
      });
      this.getDicts("valid_status").then(response => {
        this.statusOptions = response.data;
      });
      this.getList();
    },
    methods: {
      // 状态修改
      handleStatusChange(row) {
        let text = row.status === "1" ? "启用" : "停用";
        this.$confirm('确认要"' + text + '""' + row.name + '"用户吗?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return updateSupplier({
            id: row.id,
            status: row.status
          });
        }).then(() => {
          this.msgSuccess(text + "成功");
        }).catch(function () {
          row.status = row.status === "0" ? "1" : "0";
        });
      },

      /** 查询供应商列表 */
      getList() {
        this.loading = true;
        this.queryParams.params = {};
        if (null != this.daterangeAuditTime && '' != this.daterangeAuditTime) {
          this.queryParams.params["beginAuditTime"] = this.daterangeAuditTime[0];
          this.queryParams.params["endAuditTime"] = this.daterangeAuditTime[1];
        }
        listSupplier(this.queryParams).then(response => {
          this.supplierList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },
      // 供应商分类字典翻译
      typeFormat(row, column) {
        return this.selectDictLabel(this.typeOptions, row.type);
      },
      // 供应商等级字典翻译
      levelFormat(row, column) {
        return this.selectDictLabel(this.levelOptions, row.level);
      },
      // 审核人字典翻译
      auditUserFormat(row, column) {
        return this.selectDictLabel(this.auditUserOptions, row.auditUser);
      },
      // 审核状态字典翻译
      auditStatusFormat(row, column) {
        return this.selectDictLabel(this.auditStatusOptions, row.auditStatus);
      },
      // 状态字典翻译
      statusFormat(row, column) {
        return this.selectDictLabel(this.statusOptions, row.status);
      },
      // 申请状态字典翻译
      applyStatusFormat(row, column) {
        return this.selectDictLabel(this.applyStatusOptions, row.applyStatus);
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
          supplierNo: null,
          name: null,
          shortName: null,
          addr: null,
          tel: null,
          bank: null,
          account: null,
          taxNo: null,
          contact: null,
          contactTel: null,
          type: null,
          level: null,
          auditUser: null,
          auditTime: null,
          auditStatus: null,
          auditComment: null,
          status: "1",
          createBy: null,
          createTime: null,
          updateBy: null,
          updateTime: null,
          remark: null,
          materialCategoryIds: []
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
        this.title = "添加供应商";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids
        getSupplier(id).then(response => {
          this.form = response.data;
          // this.form.materialCategoryIds = response.materialCategoryIds;
          this.open = true;
          this.title = "修改供应商";
        });
      },

      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            submitSupplier(this.form).then(response => {
              this.msgSuccess("提交成功");
              this.open = false;
              this.getList();
            });
          }
        });
      },
      /** 保存按钮 */
      saveForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.id != null) {
              updateSupplier(this.form).then(response => {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addSupplier(this.form).then(response => {
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
        this.$confirm('是否确认删除供应商编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return delSupplier(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
      },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams;
        this.$confirm('是否确认导出所有供应商数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return exportSupplier(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
      }
    }
  };

</script>
