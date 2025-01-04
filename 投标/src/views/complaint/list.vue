<template>
  <div class="list">
    <div style="display: flex; margin-bottom: 30px">
      <el-breadcrumb separator="/" class="breadcrumb">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>投诉列表页</el-breadcrumb-item>
      </el-breadcrumb>
      <div style="position: absolute;right: 0;">
        <el-button class="goButton" @click="goComplaint">
          去投诉
        </el-button>
      </div>
    </div>
    <div class="content">
      <el-table v-loading="loading" style="width: 100%" :data="complaintList" :header-cell-style="{
      background:'#F6FDFD',height:'46px',color:'#000000',border: '0px solid tan', boxShadow: 'inset 0px -1px 0px 0px #EEEEEE'}"
                :row-style="{height:'50px'}"
                @row-click="handleTableRow"
      >
        <el-table-column label="序号" type="index" width="244"></el-table-column>
        <el-table-column label="投诉名称" align="left" width="530" prop="title"/>
        <el-table-column label="创建时间" align="left" width="300" prop="createTime">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="处理状态" align="left" prop="status">
          <template slot-scope="scope">
            <span v-if="scope.row.status=== '0'" style="color: #E4931B;">
            {{ getStatusValue(scope.row.status) }}
            </span>
            <template v-else>
              {{ getStatusValue(scope.row.status) }}
            </template>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button size="mini" style="color: #13B69F;" type="text">查看</el-button>
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
    </div>
    <el-dialog title="投诉" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" label-width="80px" :rules="rules">
        <el-form-item label="投诉标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入投诉标题"/>
        </el-form-item>
        <el-form-item label="投诉信息" prop="content">
          <editor v-model="form.content" :min-height="192" :height="400"/>
        </el-form-item>
        <el-form-item label="联系人" prop="contact">
          <el-input v-model="form.contact" placeholder="请输入联系人"/>
        </el-form-item>
        <el-form-item label="联系电话" prop="contactTel">
          <el-input v-model="form.contactTel" placeholder="请输入联系电话"/>
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
import {listComplaint, addComplaint} from '@/api/bid/complaint'
import Editor from '@/components/MyEditor'
import {complaintRules} from '@/views/enterprise/enterpriseInfoData'
import {mapGetters} from "vuex";

export default {
  name: "list",
  components: {
    Editor
  },
  data() {
    return {
      loading: true,
      // 总条数
      total: 0,
      complaintList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 20,
      },
      headerCellClassName: 'headerClass',
      complaintStatus: [],
      open: false,
      form: {},
      rules: complaintRules
    }
  },
  created() {
    this.getList();
    this.getDicts('complaint_status').then(x => {
      this.complaintStatus = x.data
    })
  },
  methods: {
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
    getList() {
      const that = this
      this.loading = true
      listComplaint(this.queryParams).then(response => {
        this.complaintList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    handleTableRow(row, column, event) {
      this.$router.push(`/complaint/detail?id=${row.id}`)
    },
    getStatusValue(key) {
      const dict = this.complaintStatus.filter(x => x.dictValue === key.toString())
      if (dict && dict.length > 0) {
        return dict[0].dictLabel
      }
      return ''
    },
    goComplaint() {
      this.reset()
      this.open = true
    },
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          this.form.enterpriseId = this.userId
          addComplaint(this.form).then(response => {
            this.msgSuccess('新增成功')
            this.open = false
            this.getList()
          })
        }
      })
    },
    cancel() {
      this.open = false
      this.reset()
    }

  },
  computed:{
    ...mapGetters(['userId'])
  }
}
</script>

<style lang="scss" scoped>
.list {
  .breadcrumb {
    height: 21px;
    font-family: MicrosoftYaHei, sans-serif;
    font-size: 14px;
    font-weight: normal;
    line-height: 21px;
    letter-spacing: 0em;
    margin-left: 30px
  }

  .content {
    margin: 14px 30px 44px 30px;
    background: #FFFFFF;
    border-radius: 10px;
    opacity: 1;
    width: 1380px;
    min-height: 860px;
    padding: 24px 21px 0 21px;
  }
}

.headerClass {

  background: #F6FDFD;

  box-shadow: inset 0px -1px 0px 0px #EEEEEE;
}

::v-deep .pagination-container .el-pagination {
  text-align: center;
  margin-top: 20px;
  display: flex;
  justify-content: center;
  position: relative;
}

::v-deep .el-pager li.active {

  cursor: default;
  font-family: MicrosoftYaHei, sans-serif;
  font-size: 12px;
  font-weight: normal;

  letter-spacing: 0em;
  color: #1DD0B7;
}

.goButton {
  width: 120px;
  height: 36px;
  border-radius: 4px;
  opacity: 1;
  background: linear-gradient(90deg, #22CDB5 8%, #46DFCA 96%);
  font-family: MicrosoftYaHei, sans-serif;
  font-size: 16px;
  font-weight: bold;
  letter-spacing: 0em;
  color: #FFFFFF;
}
</style>
