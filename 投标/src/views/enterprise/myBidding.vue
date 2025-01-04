<template>
  <div class="myBidding">
    <div class="content">
      <el-table v-loading="loading" style="width: 100%" :data="biddingList" :header-cell-style="{
      background:'#F6FDFD',height:'46px',color:'#000000',border: '0px solid tan', boxShadow: 'inset 0px -1px 0px 0px #EEEEEE'}"
                :row-style="{height:'50px'}"
      >
        <el-table-column label="序号" type="index" width="244"></el-table-column>
        <el-table-column label="标的发布名称" align="left" width="530" prop="enterpriseId">
          <template slot-scope="scope">
            <span>{{ scope.row.bidBidding.bidTitle }}</span>
          </template>
        </el-table-column>
        <el-table-column label="结束时间" align="left" prop="enterpriseId">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.bidBidding.endDate, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="状态" align="left" prop="status">
          <template slot-scope="scope">
            <span v-if="scope.row.status === '0' && scope.row.filesList.length === 0"
                  style="color: #E4931B">报名成功</span>
            <span v-if="scope.row.status === '0' && scope.row.filesList.length > 0" style="color: #666666">{{
                getDictValue(scope.row.status)
              }}</span>
            <span v-if="scope.row.status === '1'" style="color: #1B85E8;">{{
                getDictValue(scope.row.status)
              }}</span>
            <span v-if="scope.row.status === '3'" style="color: #17C2AA;">{{ getDictValue(scope.row.status) }}</span>
            <span v-if="scope.row.status === '2'" style="color: #999999;;">{{ getDictValue(scope.row.status) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button size="mini" style="color: #13B69F;" type="text" @click="handleTableRow(scope.row)">
              <template v-if="scope.row.status === '0' && scope.row.filesList.length === 0">
                提交资料
              </template>
              <template v-if="scope.row.status === '0' && scope.row.filesList.length > 0">
                查看
              </template>
              <template v-if="scope.row.status === '1' || scope.row.status === '2'">
                查看
              </template>
              <template v-if="scope.row.status === '3'">
                --
              </template>
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
    </div>
  </div>
</template>

<script>
import {listBidding} from '@/api/bid/enterpriseBidding'
import {mapGetters} from "vuex";

export default {
  name: "myBidding",
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
        enterpriseId: this.userId,
      },
      headerCellClassName: 'headerClass',
      category: "0",
      dialogVisible: false,
      selectRow: null,
      bidStatus: []
    }
  },
  created() {
    const that = this
    this.getList();

    this.getDicts('bid_status').then(response => {
      that.bidStatus = response.data
    })
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
    getDictValue(key) {
      const dict = this.bidStatus.filter(x => x.dictValue === key.toString())
      if (dict && dict.length > 0) {
        return dict[0].dictLabel
      }
      return ''
    },
    handleTableRow(row) {
      if (row.status === '0' && row.filesList.length === 0) {
        this.$router.push(`/enterprise/commitFile?id=${row.id}`)
      } else if (row.status === '0' && row.filesList.length > 0) {
        this.$router.push(`/bid/detail?id=${row.bidBidding.id}`)
      } else if (row.status === '1' || row.status === '3') {
        this.$router.push(`/bid/detail?id=${row.bidBidding.id}`)
      } else {
        return;
      }
    }
  },
  computed: {
    ...mapGetters(['userId'])
  }
}
</script>

<style lang="scss" scoped>
.myBidding {
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
