<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="标题" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入标题"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发布时间" prop="publishDatetime">
        <el-date-picker clearable size="small"
          v-model="queryParams.publishDatetime"
          type="datetime"
          value-format="yyyy-MM-dd HH:mm:ss"
          placeholder="选择发布时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="发布者" prop="publisher">
        <el-input
          v-model="queryParams.publisher"
          placeholder="请输入发布者"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['bid:policylaw:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['bid:policylaw:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['bid:policylaw:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['bid:policylaw:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="policylawList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
<!--      <el-table-column label="主键" align="center" prop="id" />-->
      <el-table-column label="No." type="index" width="50"></el-table-column>
      <el-table-column label="标题" align="center" prop="title" />
      <el-table-column label="发布时间" align="center" prop="publishDatetime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.publishDatetime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="发布者" align="center" prop="publisher" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['bid:policylaw:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['bid:policylaw:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改政策法规对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入标题" />
        </el-form-item>
        <el-form-item label="发布时间" prop="publishDatetime">
          <el-date-picker clearable size="small"
                          v-model="form.publishDatetime"
                          type="datetime"
                          value-format="yyyy-MM-dd HH:mm:ss"
                          placeholder="选择发布时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="发布者" prop="publisher">
          <el-input v-model="form.publisher" placeholder="请输入发布者" />
        </el-form-item>
        <el-form-item label="内容">
          <editor v-model="form.content" :min-height="192" :height="400" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="上传文件">
          <file-upload v-model="fileList" :file-type="fileType" :file-size="100" :limit="10" v-if="showFileList"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listPolicylaw, getPolicylaw, delPolicylaw, addPolicylaw, updatePolicylaw, exportPolicylaw } from "@/api/bid/policylaw";
import Editor from '@/components/MyEditor';
import ImageUpload from '@/components/ImageUpload'
import FileUpload from '@/components/FileUpload'

export default {
  name: "Policylaw",
  components: {
    Editor,
    ImageUpload,
    FileUpload
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
      // 政策法规表格数据
      policylawList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        title: null,
        content: null,
        publishDatetime: null,
        publisher: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        title: [
          { required: true, message: "标题不能为空", trigger: "blur" }
        ],
        content: [
          { required: true, message: "内容不能为空", trigger: "blur" }
        ],
      },
      fileList: [],
      fileType: ['pdf', 'zip', 'rar', 'png', 'jpg', 'jpeg'],
      showFileList: false,
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询政策法规列表 */
    getList() {
      this.loading = true;
      listPolicylaw(this.queryParams).then(response => {
        this.policylawList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
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
        title: null,
        content: null,
        publishDatetime: null,
        publisher: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null,
      };
      this.resetForm("form");
      this.$nextTick(() => {
        this.fileList = []
        this.showFileList = false
        this.$forceUpdate()
        this.showFileList = true
      })
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
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加政策法规";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getPolicylaw(id).then(response => {
        this.form = response.data;
        if (response.data.filesList) {
          this.fileList = []
          response.data.filesList.map(x => {
            this.fileList.push({
              fileName: x.fileName,
              originalFileName: x.fileName,
              url: x.filePath
            })
          })
        }
        this.open = true;
        this.title = "修改政策法规";
      });
    },
    /** 提交按钮 */
    submitForm() {
      if (this.fileList) {
        const file = []
        this.fileList.forEach(item => {
          file.push({
            fileName: item.originalFileName,
            filePath: item.url
          })
        })

        this.form['filesList'] = file
      }
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updatePolicylaw(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPolicylaw(this.form).then(response => {
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
      this.$confirm('是否确认删除政策法规编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delPolicylaw(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有政策法规数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportPolicylaw(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
    }
  }
};
</script>
