<template>
  <div class="bidding">
    <div class="content">
      <el-table v-loading="loading" style="width: 100%" :data="biddingList" :header-cell-style="{
        background: '#F6FDFD', height: '46px', color: '#000000', border: '0px solid tan', boxShadow: 'inset 0px -1px 0px 0px #EEEEEE'
      }" :row-style="{ height: '50px' }">
        <el-table-column label="序号" type="index" width="244"></el-table-column>
        <el-table-column label="招投标标题" align="left" width="530" prop="bidTitle" />
        <el-table-column label="发布时间" align="left" prop="bidPublishDatetime">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.bidPublishDatetime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="状态" align="left" prop="bidTitle">
          <template slot-scope="scope">
            <span>未报名</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button size="mini" style="color: #13B69F;" type="text" @click="handleTableRow(scope.row)">报名
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
        @pagination="getList" />
    </div>
    <el-dialog title="报名" :visible.sync="dialogVisible" width="300px">
      <span>您确认报名该投标吗？</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submit"
          style="background:linear-gradient(90deg, #22CDB5 8%, #46DFCA 96%);color: #FFFFFF ">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { listBidding } from '@/api/bid/bidding'
import { addBidding } from '@/api/bid/enterpriseBidding'
import { mapGetters } from "vuex";

export default {
  name: "bidding",
  data() {
    return {
      loading: true,
      // 总条数
      total: 0,
      biddingList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 20,
        bidTitle: null,
        bidContent: null,
        bidPublishDatetime: null,
        bidPublisher: null,
        category: null,
        beginDate: null,
        endDate: null,
        status: null
      },
      headerCellClassName: 'headerClass',
      category: "0",
      dialogVisible: false,
      selectRow: null
    }
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      const that = this
      this.loading = true
      this.queryParams.category = "2"
      this.queryParams.enterpriseId = this.userId
      listBidding(this.queryParams).then(response => {
        this.biddingList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    handleTableRow(row) {
      this.dialogVisible = true
      this.selectRow = row
    },
    submit() {
      this.dialogVisible = false
      addBidding({
        enterpriseId: this.userId,
        bidId: this.selectRow.id,
        status: 0
      }).then(response => {
        this.$alert('报名成功', '提示', {
          confirmButtonText: '确定',
          type: "success"
        });
        this.getList();
      })
    }
  },
  computed: {
    ...mapGetters(['userId'])
  }
}
</script>

<style lang="scss" scoped>
.bidding {
  .content {
    margin: 0px 30px 44px 30px;
    background: #FFFFFF;
    border-radius: 10px;
    opacity: 1;
    width: 1250px;
    min-height: 860px;
    padding: 20px;
  }
}

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
</style>
