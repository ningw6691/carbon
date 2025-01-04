<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="企业名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入企业名称"
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
          v-hasPermi="['bid:enterprise:add']"
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
          v-hasPermi="['bid:enterprise:edit']"
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
          v-hasPermi="['bid:enterprise:remove']"
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
          v-hasPermi="['bid:enterprise:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="enterpriseList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="No." type="index" width="50"></el-table-column>
      <!--      <el-table-column label="主键" align="center" prop="id" />-->

      <el-table-column label="企业名称" align="center" prop="name"/>
      <el-table-column label="简称" align="center" prop="shortName"/>
      <el-table-column label="所属机构" align="center" prop="affiliation">
        <template slot-scope="scope">
          {{ getDictValueEnter(scope.row.affiliation) }}
        </template>
      </el-table-column>
      <el-table-column label="企业性质" align="center" prop="enterpriseNature">
        <template slot-scope="scope">
          {{ getDictValue(scope.row.enterpriseNature) }}
        </template>
      </el-table-column>
      <el-table-column label="联系人" align="center" prop="contact"/>
      <el-table-column label="统一社会信用代码" align="center" prop="usci"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['bid:enterprise:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['bid:enterprise:remove']"
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

    <!-- 添加或修改投标企业信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户名称" prop="userName">
          <el-input v-model="form.userName" placeholder="请输入用户名称"/>
        </el-form-item>
        <el-form-item label="所属机构" prop="affiliation">
          <!--          <el-input v-model="form.affiliation" placeholder="请输入所属机构"/>-->
          <el-select v-model="form.affiliation" placeholder="请输入所属机构">
            <el-option
              v-for="dict in enterpriseAffiliation"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" placeholder="请输入密码"/>
        </el-form-item>
        <!--        <el-form-item label="盐" prop="salt">-->
        <!--          <el-input v-model="form.salt" placeholder="请输入盐" />-->
        <!--        </el-form-item>-->
        <el-form-item label="手机号码" prop="mobile">
          <el-input v-model="form.mobile" placeholder="请输入手机号码"/>
        </el-form-item>
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入名称"/>
        </el-form-item>
        <el-form-item label="简称" prop="shortName" v-if="form.id !== null">
          <el-input v-model="form.shortName" placeholder="请输入简称"/>
        </el-form-item>
        <el-form-item label="开户行地址" prop="addr" v-if="form.id !== null">
          <el-input v-model="form.addr" placeholder="请输入开户行地址"/>
        </el-form-item>
        <el-form-item label="开户银行" prop="bank" v-if="form.id !== null">
          <el-input v-model="form.bank" placeholder="请输入开户银行"/>
        </el-form-item>
        <el-form-item label="银行账号" prop="account" v-if="form.id !== null">
          <el-input v-model="form.account" placeholder="请输入银行账号"/>
        </el-form-item>
        <el-form-item label="税号" prop="taxNo" v-if="form.id !== null">
          <el-input v-model="form.taxNo" placeholder="请输入税号"/>
        </el-form-item>
        <el-form-item label="联系人" prop="contact" v-if="form.id !== null">
          <el-input v-model="form.contact" placeholder="请输入联系人"/>
        </el-form-item>
        <el-form-item label="联系电话" prop="contactTel" v-if="form.id !== null">
          <el-input v-model="form.contactTel" placeholder="请输入联系电话"/>
        </el-form-item>
        <el-form-item label="统一社会信用代码" prop="usci" v-if="form.id !== null">
          <el-input v-model="form.usci" placeholder="请输入统一社会信用代码"/>
        </el-form-item>
        <el-form-item label="公司注册地址" prop="address" v-if="form.id !== null">
          <el-input v-model="form.address" placeholder="请输入公司注册地址"/>
        </el-form-item>
        <el-form-item label="企业性质" prop="enterpriseNature" v-if="form.id !== null">
<!--          <el-input v-model="form.enterpriseNature" placeholder="请输入企业性质"/>-->
          <el-select v-model="form.enterpriseNature" placeholder="请输入企业性质">
            <el-option
              v-for="dict in enterpriseNature"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="成立日期" prop="doe" v-if="form.id !== null">
          <el-date-picker clearable size="small"
                          v-model="form.doe"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="选择成立日期"
                          v-if="form.id !== null"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="法人/负责人" prop="corpn" v-if="form.id !== null">
          <el-input v-model="form.corpn" placeholder="请输入法人/负责人"/>
        </el-form-item>
        <el-form-item label="法人/负责人身份证" prop="corpnIdcard" v-if="form.id !== null">
          <el-input v-model="form.corpnIdcard" placeholder="请输入法人/负责人身份证"/>
        </el-form-item>
        <el-form-item label="单位联系地址" prop="contactAddress" v-if="form.id !== null">
          <el-input v-model="form.contactAddress" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
        <el-form-item label="经营范围" prop="businessScope" v-if="form.id !== null">
          <el-input v-model="form.businessScope" placeholder="请输入经营范围"/>
        </el-form-item>
        <el-form-item label="公司简介" v-if="form.id !== null">
          <fileUpload v-model="form.profile"/>
        </el-form-item>
        <el-form-item label="营业执照扫描件" v-if="form.id !== null">
          <imageUpload v-model="form.businessLicenseImage"/>
        </el-form-item>
        <el-form-item label="营业执照有效期" prop="businessLicenseGp" v-if="form.id !== null">
          <el-input v-model="form.businessLicenseGp" placeholder="请输入营业执照有效期"/>
        </el-form-item>
        <el-form-item label="法人/负责人身份证扫描件" v-if="form.id !== null">
          <imageUpload v-model="form.corpnIdcardImage"/>
        </el-form-item>
        <el-form-item label="注册电话" prop="registrationPhone" v-if="form.id !== null">
          <el-input v-model="form.registrationPhone" placeholder="请输入注册电话"/>
        </el-form-item>
        <el-form-item label="注册地址" prop="contactAddr" v-if="form.id !== null">
          <el-input v-model="form.contactAddr" placeholder="请输入注册地址"/>
        </el-form-item>
        <el-form-item label="注册资本" prop="registeredCapital" v-if="form.id !== null">
          <el-input v-model="form.registeredCapital" placeholder="请输入注册资本"/>
        </el-form-item>
        <el-form-item label="实缴资本" prop="paidinCapital" v-if="form.id !== null">
          <el-input v-model="form.paidinCapital" placeholder="请输入实缴资本"/>
        </el-form-item>
        <el-form-item label="开户许可证/基本户存款信息证" prop="accountLicence" v-if="form.id !== null">
          <el-input v-model="form.accountLicence" placeholder="请输入开户许可证/基本户存款信息证"/>
        </el-form-item>
        <el-form-item label="备注" prop="remark" v-if="form.id !== null">
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
  listEnterprise,
  getEnterprise,
  delEnterprise,
  addEnterprise,
  updateEnterprise,
  exportEnterprise
} from '@/api/bid/enterprise'
import ImageUpload from '@/components/ImageUpload'
import FileUpload from '@/components/FileUpload'

export default {
  name: 'Enterprise',
  components: {
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
      // 投标企业信息表格数据
      enterpriseList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userName: null,
        affiliation: null,
        password: null,
        salt: null,
        mobile: null,
        name: null,
        shortName: null,
        addr: null,
        bank: null,
        account: null,
        taxNo: null,
        contact: null,
        contactTel: null,
        usci: null,
        address: null,
        enterpriseNature: null,
        doe: null,
        corpn: null,
        corpnIdcard: null,
        contactAddress: null,
        businessScope: null,
        profile: null,
        businessLicenseImage: null,
        businessLicenseGp: null,
        corpnIdcardImage: null,
        registrationPhone: null,
        contactAddr: null,
        registeredCapital: null,
        paidinCapital: null,
        accountLicence: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        userName: [
          { required: true, message: '用户名称不能为空', trigger: 'blur' }
        ]
      },
      enterpriseAffiliation: [],
      enterpriseNature: []
    }
  },
  created() {
    this.getList()
    this.getDicts('enterprise_affiliation').then(response => {
      this.enterpriseAffiliation = response.data
    })
    this.getDicts('enterprise_nature').then(response => {
      this.enterpriseNature = response.data
    })
  },
  methods: {
    getDictValue(key) {
      const dict = this.enterpriseNature.filter(x => x.dictValue === key.toString())
      if (dict && dict.length > 0) {
        return dict[0].dictLabel
      }
      return ''
    },
    getDictValueEnter(key) {
      const dict = this.enterpriseAffiliation.filter(x => x.dictValue === key.toString())
      if (dict && dict.length > 0) {
        return dict[0].dictLabel
      }
      return ''
    },
    /** 查询投标企业信息列表 */
    getList() {
      this.loading = true
      listEnterprise(this.queryParams).then(response => {
        this.enterpriseList = response.rows
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
        userName: null,
        affiliation: null,
        password: null,
        salt: null,
        mobile: null,
        name: null,
        shortName: null,
        addr: null,
        bank: null,
        account: null,
        taxNo: null,
        contact: null,
        contactTel: null,
        usci: null,
        address: null,
        enterpriseNature: null,
        doe: null,
        corpn: null,
        corpnIdcard: null,
        contactAddress: null,
        businessScope: null,
        profile: null,
        businessLicenseImage: null,
        businessLicenseGp: null,
        corpnIdcardImage: null,
        registrationPhone: null,
        contactAddr: null,
        registeredCapital: null,
        paidinCapital: null,
        accountLicence: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
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
      this.title = '添加投标企业信息'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getEnterprise(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = '修改投标企业信息'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateEnterprise(this.form).then(response => {
              this.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addEnterprise(this.form).then(response => {
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
      this.$confirm('是否确认删除投标企业信息编号为"' + ids + '"的数据项?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        return delEnterprise(ids)
      }).then(() => {
        this.getList()
        this.msgSuccess('删除成功')
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams
      this.$confirm('是否确认导出所有投标企业信息数据项?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        return exportEnterprise(queryParams)
      }).then(response => {
        this.download(response.msg)
      })
    }
  }
}
</script>
