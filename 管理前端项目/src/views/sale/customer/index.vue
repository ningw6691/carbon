<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="客户名称" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入客户名称" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="联系人" prop="contact">
        <el-input v-model="queryParams.contact" placeholder="请输入联系人" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="客户分类" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择客户分类" clearable filterable size="small">
          <el-option v-for="dict in typeOptions" :key="dict.dictValue" :label="dict.dictLabel"
            :value="dict.dictValue" />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable filterable size="small">
          <el-option v-for="dict in statusOptions" :key="dict.dictValue" :label="dict.dictLabel"
            :value="dict.dictValue" />
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="创建人" prop="createBy">
        <el-select v-model="queryParams.createBy" placeholder="请选择创建人" clearable filterable size="small">
          <el-option
            v-for="dict in createByOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
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
          v-hasPermi="['sale:customer:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-no-more-click type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['sale:customer:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-no-more-click type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['sale:customer:remove']">删除</el-button>
      </el-col>
      <!-- <el-col :span="1.5">
        <el-button v-no-more-click type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['sale:customer:export']">导出</el-button>
      </el-col> -->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="customerList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" width="60" />
      <el-table-column label="客户名称" align="center" prop="name" />
      <el-table-column label="公司地址" align="center" prop="addr" />
      <el-table-column label="联系人" align="center" prop="contact" />
      <el-table-column label="联系电话" align="center" prop="contactTel" />
      <el-table-column label="客户分类" align="center" prop="type" :formatter="typeFormat" />
      <el-table-column label="信用等级" align="center" prop="creditLevel" :formatter="creditLevelFormat" />
      <el-table-column label="状态" align="center" prop="status" width="80">
        <template #default="scope">
          <el-switch v-model="scope.row.status" active-value="1" inactive-value="0"
            @change="handleStatusChange(scope.row)"></el-switch>
        </template>
      </el-table-column>

      <!-- <el-table-column label="创建人" align="center" prop="createBy" :formatter="createByFormat" /> -->
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{m}:{s}') }}</span>
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-no-more-click size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['sale:customer:edit']">修改</el-button>
          <el-button v-no-more-click size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['sale:customer:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改客户档案对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="客户名称" prop="name">
              <el-input v-model="form.name" placeholder="请输入客户名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="简称" prop="shortName">
              <el-input v-model="form.shortName" placeholder="请输入简称" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="公司地址" prop="addr">
              <el-input v-model="form.addr" placeholder="请输入公司地址" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="邮编" prop="postcode">
              <el-input v-model="form.postcode" placeholder="请输入邮编" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="电话" prop="tel">
              <el-input v-model="form.tel" placeholder="请输入电话" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="开户银行" prop="bank">
              <el-input v-model="form.bank" placeholder="请输入开户银行" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="银行账号" prop="account">
              <el-input v-model="form.account" placeholder="请输入银行账号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="税号" prop="taxNo">
              <el-input v-model="form.taxNo" placeholder="请输入税号" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="联系人" prop="contact">
              <el-input v-model="form.contact" placeholder="请输入联系人" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系电话" prop="contactTel">
              <el-input v-model="form.contactTel" placeholder="请输入联系电话" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="客户分类" prop="type">
              <el-select v-model="form.type" placeholder="请选择客户分类" clearable filterable>
                <el-option v-for="dict in typeOptions" :key="dict.dictValue" :label="dict.dictLabel"
                  :value="dict.dictValue"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="信用等级" prop="creditLevel">
              <el-select v-model="form.creditLevel" placeholder="请选择信用等级" clearable filterable>
                <el-option v-for="dict in creditLevelOptions" :key="dict.dictValue" :label="dict.dictLabel"
                  :value="dict.dictValue"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="状态">
              <el-radio-group v-model="form.status">
                <el-radio v-for="dict in statusOptions" :key="dict.dictValue" :label="dict.dictValue">{{dict.dictLabel}}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="备注">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button v-no-more-click type="primary" @click="submitForm">确 定</el-button>
        <el-button v-no-more-click @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import {
    listCustomer,
    getCustomer,
    delCustomer,
    addCustomer,
    updateCustomer,
    exportCustomer
  } from "@/api/sale/customer";

  export default {
    name: "Customer",
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
        // 客户档案表格数据
        customerList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 客户分类字典
        typeOptions: [],
        // 信用等级字典
        creditLevelOptions: [],
        // 状态字典
        statusOptions: [],
        // 创建人字典
        createByOptions: [],
        // 修改人字典
        updateByOptions: [],
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          name: null,
          addr: null,
          tel: null,
          contact: null,
          contactTel: null,
          type: null,
          creditLevel: null,
          status: null,
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          name: [{
            required: true,
            message: "客户名称不能为空",
            trigger: "blur"
          }, {
            max: 20,
            message: "长度不能超过20个字符",
            trigger: "blur"
          }],
          addr: [{
            required: true,
            message: "公司地址不能为空",
            trigger: "blur"
          }, {
            max: 20,
            message: "长度不能超过20个字符",
            trigger: "blur"
          }],
          postcode: [{
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
          }, {
            max: 20,
            message: "长度不能超过20个字符",
            trigger: "blur"
          }],
          tel: [{
            max: 20,
            message: "长度不能超过20个字符",
            trigger: "blur"
          }],
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
          type: [{
            required: true,
            message: "客户分类不能为空",
            trigger: "change"
          }],
          creditLevel: [{
            required: true,
            message: "信用等级不能为空",
            trigger: "change"
          }],
        }
      };
    },
    created() {
      this.getList();
      this.getDicts("scm_customer_type").then(response => {
        this.typeOptions = response.data;
      });
      this.getDicts("scm_customer_level").then(response => {
        this.creditLevelOptions = response.data;
      });
      this.getDicts("valid_status").then(response => {
        this.statusOptions = response.data;
      });
      this.getQueryData("query_user").then(res => {
        this.createByOptions = res.data;
      });
      this.getQueryData("query_user").then(res => {
        this.updateByOptions = res.data;
      });
    },
    methods: {

      // 状态修改
      handleStatusChange(row) {
        let text = row.status === "1" ? "启用" : "停用";
        this.$confirm('确认要"' + text + '""' + row.name + '"客户吗?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return updateCustomer({
            id: row.id,
            status: row.status
          });
        }).then(() => {
          this.msgSuccess(text + "成功");
        }).catch(function () {
          row.status = row.status === "0" ? "1" : "0";
        });
      },
      /** 查询客户档案列表 */
      getList() {
        this.loading = true;
        listCustomer(this.queryParams).then(response => {
          this.customerList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },
      // 客户分类字典翻译
      typeFormat(row, column) {
        return this.selectDictLabel(this.typeOptions, row.type);
      },
      // 信用等级字典翻译
      creditLevelFormat(row, column) {
        return this.selectDictLabel(this.creditLevelOptions, row.creditLevel);
      },
      // 状态字典翻译
      statusFormat(row, column) {
        return this.selectDictLabel(this.statusOptions, row.status);
      },
      // 创建人字典翻译
      createByFormat(row, column) {
        return this.selectDictLabel(this.createByOptions, row.createBy);
      },
      // 修改人字典翻译
      updateByFormat(row, column) {
        return this.selectDictLabel(this.updateByOptions, row.updateBy);
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
          name: null,
          shortName: null,
          addr: null,
          postcode: null,
          tel: null,
          bank: null,
          account: null,
          taxNo: null,
          contact: null,
          contactTel: null,
          type: null,
          creditLevel: null,
          status: '1',
          createBy: null,
          createTime: null,
          updateBy: null,
          updateTime: null,
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
        this.title = "添加客户档案";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids
        getCustomer(id).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改客户档案";
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.id != null) {
              updateCustomer(this.form).then(response => {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addCustomer(this.form).then(response => {
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
        this.$confirm('是否确认删除客户档案编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return delCustomer(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
      },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams;
        this.$confirm('是否确认导出所有客户档案数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return exportCustomer(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
      }
    }
  };

</script>
