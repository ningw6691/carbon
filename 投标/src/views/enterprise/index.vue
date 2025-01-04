<template>
  <div class="home">
    <div class="content">
      <div>用户名称：{{ userName }}</div>
      <div>IP属地：{{ ipAddress }}</div>
      <div>所属机构：{{ affiliationString }}</div>
      <div>手机号码：{{ mobile }}</div>
      <div>当前密码：********</div>
    </div>
  </div>
</template>

<script>
import {getEnterprise} from '@/api/bid/enterprise'
import {mapGetters} from "vuex";

export default {
  name: "enterprise",
  data() {
    return {
      enterpriseData: null,
      userName: "",
      ipAddress: "",
      affiliation: "",
      affiliationString: "",
      mobile: "",
      affiliations: [{
        key: "0",
        value: "企业机构",
      }, {
        key: "1",
        value: "其他机构",
      }]
    }
  },
  mounted() {
    const that = this
    getEnterprise(this.userId).then(response => {
      that.enterpriseData = response.data
      that.userName = that.enterpriseData.userName;
      that.affiliation = that.enterpriseData.affiliation;
      that.ipAddress = that.enterpriseData.ipAddress;
      that.affiliationString = that.affiliations.filter(x => x.key === that.affiliation)[0].value
      that.mobile = that.enterpriseData.mobile;
    })
  },
  computed:{
    ...mapGetters(['userId'])
  }
}
</script>

<style lang="scss" scoped>
.home {
  .content {
    margin: 0px 30px 44px 30px;
    background: #FFFFFF;
    border-radius: 10px;
    opacity: 1;
    width: 1380px;
    min-height: 860px;
    display: flex;
    justify-content: flex-start;
    align-items: flex-start;
    flex-wrap: nowrap;
    flex-direction: column;
    align-content: center;
    line-height: 40px;
    padding: 20px;
  }
}
</style>
