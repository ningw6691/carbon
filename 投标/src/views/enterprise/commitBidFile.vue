<template>
  <div class="commitBidFile">
    <enterprise-info-panel :data-source="enterpriseData" :limit="fileLimit" :size="fileSize" :type="fileType"
                           text="提交" is-show-upload="true"
                           @handle-update="handleUpdate"/>
  </div>
</template>

<script>
import EnterpriseInfoPanel from '@/layout/components/platform/enterpriseInfoPanel.vue'
import {updateBidding, getBidding} from '@/api/bid/enterpriseBidding'

export default {
  name: "commitBidFile",
  components: {EnterpriseInfoPanel},
  data() {
    return {
      fileType: ['pdf', 'zip', 'rar', 'png', 'jpg', 'jpeg'],
      fileLimit: 10,
      fileSize: 100,
      bidId: null,
      enterpriseData: {}
    }
  },
  created() {
    this.bidId = this.$route.query.id
  },
  mounted() {
    const that = this
    getBidding(this.bidId).then(response => {
      that.enterpriseData = response.data.enterprise
    })
  },
  methods: {
    handleUpdate(data) {
      console.log(data)
      const that = this
      if (data.files == null || data.files.length === 0) {
        return;
      }
      const bidData = that.enterpriseData
      const fileList = []
      data.files.map(x => {
        fileList.push({
          enterpriseBidId: this.bidId,
          fileName: x.originalFileName,
          filePath: x.url
        })
      })

      //this.enterpriseData.filesList = fileList

      const bidEnterpriseBidding = {
        id: this.bidId,
        enterpriseId: data.id,
        filesList: fileList
      }

      console.log()
      updateBidding(bidEnterpriseBidding).then(x => {
        this.$alert('报名成功', '提示', {
          confirmButtonText: '确定',
          type: "success",
          callback: () => {
            that.$router.replace('/enterprise/myBidding')
          }
        });
      })
    }
  }
}
</script>

<style scoped>
.commitBidFile {
  .content {
    margin: 0px 30px 44px 30px;
    background: #FFFFFF;
    border-radius: 10px;
    opacity: 1;
    width: 1250px;
    min-height: 860px;
  //display: flex; //justify-content: flex-start; //align-items: flex-start; //flex-wrap: nowrap; //flex-direction: column; //align-content: center; //line-height: 40px; padding: 20px;

    .label {
      font-family: MicrosoftYaHei, sans-serif;
      font-size: 14px;
      font-weight: normal;
      line-height: 22px;
      text-align: right;
      letter-spacing: 0em;

      color: #333333;
    }

    .text {
      font-family: Source Han Sans CN;
      font-size: 14px;
      font-weight: normal;
      line-height: 22px;
      letter-spacing: 0em;
      /* 次强调正文标题 */
      color: #666666;
    }
  }
}


</style>
