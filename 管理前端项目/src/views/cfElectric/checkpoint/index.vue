<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="检测点名称" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入检测点名称" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="检测点地址" prop="address">
        <el-input v-model="queryParams.address" placeholder="请输入检测点地址" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable filterable size="small">
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
          v-hasPermi="['cfElectric:checkpoint:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-no-more-click type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['cfElectric:checkpoint:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-no-more-click type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['cfElectric:checkpoint:remove']">删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="checkpointList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="检测点名称" align="center" prop="name" />
      <el-table-column label="检测点地址" align="center" prop="address" />
      <el-table-column label="实时功率" align="center" prop="rtPower" />
      <el-table-column label="实时电压" align="center" prop="rtVoltage" />
      <el-table-column label="状态" align="center" prop="status" :formatter="statusFormat" />
      <el-table-column label="上报时间" align="center" prop="createTime" width="180"></el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-no-more-click size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['cfElectric:checkpoint:edit']">修改</el-button>
          <el-button v-no-more-click size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['cfElectric:checkpoint:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改能效检测点对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="检测点名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入站点名称" />
        </el-form-item>
        <el-form-item label="检测点地址" prop="address">
          <el-input v-model="form.address" placeholder="请输入站点地址" />
        </el-form-item>
        <el-form-item label="实时功率" prop="rtPower">
          <el-input v-model.number="form.rtPower" placeholder="请输入实时功率" />
        </el-form-item>
        <el-form-item label="实时电压" prop="rtVoltage">
          <el-input v-model.number="form.rtVoltage" placeholder="请输入实时电压" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择状态" clearable filterable>
            <el-option v-for="dict in statusOptions" :key="dict.dictValue" :label="dict.dictLabel"
              :value="dict.dictValue"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入备注" />
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
    listCheckpoint,
    getCheckpoint,
    delCheckpoint,
    addCheckpoint,
    updateCheckpoint,
    exportCheckpoint
  } from "@/api/cfElectric/checkpoint";

  export default {
    name: "Checkpoint",
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
        // 能效检测点表格数据
        checkpointList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 状态字典
        statusOptions: [],
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          name: null,
          address: null,
          rtPower: null,
          rtVoltage: null,
          status: null,
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          name: [{
              required: true,
              message: "检测点名称不能为空",
              trigger: "blur"
            },
            {
              max: 20,
              message: "长度不能超过20个字符",
              trigger: "blur"
            }
          ],
          address: [{
              required: true,
              message: "地址不能为空",
              trigger: "blur"
            },
            {
              max: 20,
              message: "长度不能超过20个字符",
              trigger: "blur"
            }
          ],
          rtPower: [{
            required: true,
            message: "实时功率不能为空",
            trigger: "blur"
          },
          { type: 'number',min: 1, max: 65535, message: '必须为数字值且大小要在1到65535' },
          ],
          rtVoltage: [{
            required: true,
            message: "实时电压不能为空",
            trigger: "blur"
          },
          { type: 'number',min: 1, max: 65535, message: '必须为数字值且大小要在1到65535' },
          ],
          status: [{
            required: true,
            message: "状态不能为空",
            trigger: "change"
          }],
        }
      };
    },
    created() {
      this.getList();
      this.getDicts("overload_status").then(response => {
        this.statusOptions = response.data;
      });
    },
    methods: {
      /** 查询能效检测点列表 */
      getList() {
        this.loading = true;
        listCheckpoint(this.queryParams).then(response => {
          this.checkpointList = response.rows;
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
          name: null,
          address: null,
          rtPower: null,
          rtVoltage: null,
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
        this.title = "添加能效检测点";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids
        getCheckpoint(id).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改能效检测点";
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.id != null) {
              updateCheckpoint(this.form).then(response => {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addCheckpoint(this.form).then(response => {
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
        this.$confirm('是否确认删除能效检测点编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return delCheckpoint(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
      },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams;
        this.$confirm('是否确认导出所有能效检测点数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return exportCheckpoint(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
      }
    }
  };

</script>
