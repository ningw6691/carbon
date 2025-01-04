<template>
  <div style="display: flex" class="header">
    <router-link to="/">
      <img :src="logo" class="logo"/><span class="title">双碳招投标网主界面</span>
    </router-link>
    <el-tabs style="width:367.65px; position: relative; left: 188px" :stretch="true" @tab-click="onClick">
      <el-tab-pane label="平台动态" name="first"/>
      <el-tab-pane label="政策法规" name="second"/>
      <el-tab-pane label="违规投诉" name="third"/>
    </el-tabs>
    <div class="login" @click="onLogin">
      <div>
        <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" fill="none" version="1.1"
             width="40" height="40" viewBox="0 0 40 40">
          <defs>
            <clipPath id="master_svg0_278_5791">
              <rect x="0" y="0" width="40" height="40" rx="0"/>
            </clipPath>
            <linearGradient x1="0.5" y1="0" x2="0.5" y2="1" id="master_svg1_278_5796">
              <stop offset="0%" stop-color="#AAFFEC" stop-opacity="1"/>
              <stop offset="100%" stop-color="#26D8C0" stop-opacity="1"/>
            </linearGradient>
          </defs>
          <g clip-path="url(#master_svg0_278_5791)">
            <g>
              <path
                d="M20,0C8.95455,0,0,8.95455,0,20C0,31.0455,8.95455,40,20,40C31.0455,40,40,31.0459,40,20C40,8.9541,31.0459,0,20,0ZM19.7483,7.45319C23.4468,7.45319,26.4557,10.4175,26.4557,14.062C26.4557,17.7065,23.4468,20.6717,19.7483,20.6717C16.0498,20.6717,13.0413,17.7065,13.0413,14.0629C13.0413,10.4193,16.0503,7.45319,19.7483,7.45319ZM30.9821,30.1421C30.9821,32.5459,27.0573,32.5459,22.5127,32.5459L17.4869,32.5459C12.7561,32.5459,9.01836,32.5459,9.01836,30.1421L9.01836,29.6472C9.01836,25.0503,12.8168,21.3081,17.4869,21.3081L22.5127,21.3081C27.1827,21.3081,30.9821,25.0494,30.9821,29.6472L30.9821,30.1421Z"
                fill="url(#master_svg1_278_5796)" fill-opacity="1"/>
            </g>
            <g>
              <path
                d="M22.512610546875003,21.308025L17.486830546874998,21.308025C12.816760546874999,21.308025,9.018310546875,25.048425,9.018310546875,29.647125L9.018310546875,30.142125C9.018310546875,32.545825,12.756060546875,32.545825,17.486830546874998,32.545825L22.512610546875003,32.545825C27.057310546875,32.545825,30.982010546875,32.545825,30.982010546875,30.142125L30.982010546875,29.647125C30.982010546875,25.049325,27.182710546875,21.308025,22.512610546875003,21.308025ZM19.748210546875,20.671625C23.446710546875,20.671625,26.455710546875,17.707725,26.455710546875,14.062815C26.455710546875,10.417885,23.446710546875,7.453125,19.748210546875,7.453125C16.049750546875,7.453125,13.041250546875,10.419225,13.041250546875,14.062815C13.041250546875,17.706425,16.050200546875,20.671625,19.748210546875,20.671625Z"
                fill="#FFFFFF" fill-opacity="1"/>
            </g>
          </g>
        </svg>
      </div>
      <div class="user" style="width: 100px">{{ realName === ''? '请登录': realName }}</div>
    </div>
  </div>
</template>

<script>
import {mapGetters} from "vuex";

export default {
  name: "Header",
  data() {
    return {
      logo: require("@/assets/logo/logo.png"),
      routers: ['/info/list', '/policylaw/list', '/complaint/list']
    }
  },
  methods: {
    onClick(r) {
      console.log(this.routers[r.index])
      this.$router.push(this.routers[r.index])
    },
    onLogin() {
      this.$router.push('/enterprise/home')
    }
  },
  computed:{
    ...mapGetters(['realName'])
  }
}
</script>

<style lang="scss" scoped>
.header {
  display: flex;
  padding: 15px 0 15px 30px;

  .logo {
    position: relative;
    width: 36px;
    height: 36px;
    opacity: 1;
  }

  .title {
    width: 240px;
    height: 34px;
    opacity: 1;
    margin-left: 10px;
    font-family: MicrosoftYaHei;
    font-size: 24px;
    font-weight: normal;
    letter-spacing: 0em;
    position: relative;
    top: -8px;
    color: #3D3D3D;
  }

  ::v-deep .el-tabs__nav-wrap::after {
    background-color: #FFFFFF;
  }

  ::v-deep .el-tabs__active-bar {
    background: #13B69F;
    color: #13B69F;
    height: 3px !important;
    margin-top: 5px;
  }

  ::v-deep .el-tabs__item.is-active {
    color: #13B69F;
    height: 46px;
  }

  ::v-deep .el-tabs__item {
    font-family: MicrosoftYaHei, sans-serif;
    font-size: 16px;
    font-weight: normal;
    letter-spacing: 0em;
    color: #464646;
  }

  .login {
    position: absolute;
    right: 0;
    width: 200px;
    display: flex;
    justify-content: center;
    align-content: center;
    align-items: center;
    margin-right: 30px;
    cursor: pointer;

    .user {
      margin-left: 14px;
    }
  }
}


</style>
