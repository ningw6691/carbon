<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="错误类型" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择错误类型" clearable filterable size="small">
          <el-option v-for="dict in typeOptions" :key="dict.dictValue" :label="dict.dictLabel"
            :value="dict.dictValue" />
        </el-select>
      </el-form-item>
      <el-form-item label="错误项" prop="mistakeItem">
        <el-input v-model="queryParams.mistakeItem" placeholder="请输入错误项" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button v-no-more-click type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button v-no-more-click icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button v-no-more-click type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['mesCheck:mistakeProof:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-no-more-click type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['mesCheck:mistakeProof:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-no-more-click type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['mesCheck:mistakeProof:remove']">删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="mistakeProofList" @selection-change="handleSelectionChange">
      <el-table-column width="40px" >
        <!-- <template slot="header" slot-scope="scope" >
          <input type="checkbox" />
        </template>
        <template slot-scope="scope">
          <input type="checkbox" />
        </template> -->
      </el-table-column>
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="错误项" align="center" prop="mistakeItem" />
      <el-table-column label="错误类型" align="center" prop="type" :formatter="typeFormat" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-no-more-click size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['mesCheck:mistakeProof:edit']">修改</el-button>
          <el-button v-no-more-click size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['mesCheck:mistakeProof:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改防错管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="错误项" prop="mistakeItem">
          <el-input v-model="form.mistakeItem" placeholder="请输入错误项" />
        </el-form-item>
        <el-form-item label="错误类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择错误类型" clearable filterable>
            <el-option v-for="dict in typeOptions" :key="dict.dictValue" :label="dict.dictLabel"
              :value="dict.dictValue"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="导致结果" prop="mistakeResult">
          <editor v-model="form.mistakeResult" :min-height="192" />
        </el-form-item>
        <el-form-item label="预防手段" prop="preventMethod">
          <editor1 v-model="form.preventMethod" :min-height="192" :qidx="1"/>
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
  import {
    listMistakeProof,
    getMistakeProof,
    delMistakeProof,
    addMistakeProof,
    updateMistakeProof,
    exportMistakeProof
  } from "@/api/mesCheck/mistakeProof";
  import Editor from '@/components/MyEditor';

  import Editor1 from '@/components/MyEditor';
  export default {
    name: "MistakeProof",
    components: {
      Editor,
      Editor1,
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
        // 防错管理表格数据
        mistakeProofList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 错误类型字典
        typeOptions: [],
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          type: null,
          mistakeItem: null,
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          type: [{
            required: true,
            message: "错误类型不能为空",
            trigger: "change"
          }],
          mistakeItem: [{
            required: true,
            message: "错误项不能为空",
            trigger: "blur"
          },
            { max: 20, message: "长度不能超过20个字符", trigger: "blur" }
          ],
          mistakeResult: [{
            required: true,
            message: "导致结果不能为空",
            trigger: "blur"
          }],
          preventMethod: [{
            required: true,
            message: "预防手段不能为空",
            trigger: "blur"
          }]
        }
      };
    },
    created() {
      this.getList();
      this.getDicts("mes_mistake_type").then(response => {
        this.typeOptions = response.data;
      });
    },
    methods: {
      /** 查询防错管理列表 */
      getList() {
        this.loading = true;
        listMistakeProof(this.queryParams).then(response => {
          this.mistakeProofList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },
      // 错误类型字典翻译
      typeFormat(row, column) {
        return this.selectDictLabel(this.typeOptions, row.type);
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
          type: null,
          mistakeItem: null,
          mistakeResult: null,
          preventMethod: null,
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
        this.title = "添加防错管理";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids
        getMistakeProof(id).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改防错管理";
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.id != null) {
              updateMistakeProof(this.form).then(response => {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addMistakeProof(this.form).then(response => {
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
        this.$confirm('是否确认删除防错管理编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return delMistakeProof(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
      },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams;
        this.$confirm('是否确认导出所有防错管理数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return exportMistakeProof(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
      }
    }
  };

</script>
