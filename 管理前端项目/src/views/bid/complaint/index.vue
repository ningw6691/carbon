<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="投诉标题" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入投诉标题"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="联系人" prop="contact">
        <el-input
          v-model="queryParams.contact"
          placeholder="请输入联系人"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="联系电话" prop="contactTel">
        <el-input
          v-model="queryParams.contactTel"
          placeholder="请输入联系电话"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable filterable
                   size="small"
        >
          <el-option
            v-for="dict in complaintStatus"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          ></el-option>
        </el-select>
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
          v-hasPermi="['bid:complaint:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['bid:complaint:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['bid:complaint:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['bid:complaint:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="complaintList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
<!--      <el-table-column label="主键" align="center" prop="id"/>-->
      <el-table-column label="No." type="index" width="50" />
      <el-table-column label="投诉企业名称" align="center" prop="enterpriseName"/>
      <el-table-column label="投诉标题" align="center" prop="title"/>
      <el-table-column label="联系人" align="center" prop="contact"/>
      <el-table-column label="联系电话" align="center" prop="contactTel"/>
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          {{ getDictValue(scope.row.status) }}
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['bid:complaint:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['bid:complaint:remove']"
          >删除
          </el-button>
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

    <!-- 添加或修改违规投诉对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form"  label-width="80px">
        <el-form-item label="企业名称" prop="enterpriseId">
          <el-input v-model="form.enterpriseName" readonly/>
        </el-form-item>
        <el-form-item label="投诉标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入投诉标题"/>
        </el-form-item>
        <el-form-item label="投诉信息">
          <editor v-model="form.content" :min-height="192" :height="400"/>
        </el-form-item>
        <el-form-item label="联系人" prop="contact">
          <el-input v-model="form.contact" placeholder="请输入联系人"/>
        </el-form-item>
        <el-form-item label="联系电话" prop="contactTel">
          <el-input v-model="form.contactTel" placeholder="请输入联系电话"/>
        </el-form-item>
        <el-form-item label="状态" placeholder="请选择状态">
          <el-select v-model="form.status">
            <el-option
              v-for="dict in complaintStatus"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"/>
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
import {
  listComplaint,
  getComplaint,
  delComplaint,
  addComplaint,
  updateComplaint,
  exportComplaint
} from '@/api/bid/complaint'
import Editor from '@/components/MyEditor'

export default {
  name: 'Complaint',
  components: {
    Editor
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
      // 违规投诉表格数据
      complaintList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        enterpriseId: null,
        title: null,
        content: null,
        contact: null,
        contactTel: null,
        status: null
      },
      // 表单参数
      form: {},
      // 表单校验
      // rules: {
      //   enterpriseId: [
      //     { required: true, message: '企业主键不能为空', trigger: 'blur' }
      //   ]
      // },
      complaintStatus: []
    }
  },
  created() {
    this.getList()

    this.getDicts('complaint_status').then(x => {
      this.complaintStatus = x.data
    })
  },
  methods: {
    getDictValue(key) {
      const dict = this.complaintStatus.filter(x => x.dictValue === key.toString())
      if (dict && dict.length > 0) {
        return dict[0].dictLabel
      }
      return ''
    },
    /** 查询违规投诉列表 */
    getList() {
      this.loading = true
      listComplaint(this.queryParams).then(response => {
        this.complaintList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        enterpriseId: null,
        title: null,
        content: null,
        contact: null,
        contactTel: null,
        status: '0',
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null,
        enterpriseName: null
      }
      this.resetForm('form')
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '添加违规投诉'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getComplaint(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = '修改违规投诉'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateComplaint(this.form).then(response => {
              this.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addComplaint(this.form).then(response => {
              this.msgSuccess('新增成功')
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$confirm('是否确认删除违规投诉编号为"' + ids + '"的数据项?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        return delComplaint(ids)
      }).then(() => {
        this.getList()
        this.msgSuccess('删除成功')
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams
      this.$confirm('是否确认导出所有违规投诉数据项?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        return exportComplaint(queryParams)
      }).then(response => {
        this.download(response.msg)
      })
    }
  }
}
</script>
