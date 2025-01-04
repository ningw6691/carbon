<template>
  <div class="main">
    <el-breadcrumb separator="/" class="breadcrumb">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/enterprise/home' }"> 个人中心</el-breadcrumb-item>
      <el-breadcrumb-item v-if="showLastBreadcrumb">{{ lastBreadcrumbText }}</el-breadcrumb-item>
    </el-breadcrumb>
    <div style="margin-top: 20px">
      <el-container>
        <el-aside width="195px">
          <el-menu background-color="#17C2AA" text-color="#FFFFFF" active-text-color="#17C2AA">
            <app-link to="/enterprise/enterpriseInfo">
              <el-menu-item index="1" @click="onClick('企业信息')">
                <i class="el-icon-menu"></i>
                <span slot="title">企业信息</span>
              </el-menu-item>
            </app-link>
            <app-link to="/enterprise/bidding">
              <el-menu-item index="2" @click="onClick('参与投标')">
                <i class="el-icon-document"></i>
                <span slot="title">参与投标</span>
              </el-menu-item>
            </app-link>
            <app-link to="/enterprise/myBidding">
              <el-menu-item index="3" @click="onClick('我的标的')">
                <i class="el-icon-setting"></i>
                <span slot="title">我的标的</span>
              </el-menu-item>
            </app-link>
          </el-menu>
        </el-aside>
        <el-main>
          <el-scrollbar wrap-class="scrollbar-wrapper">
            <app-main/>
          </el-scrollbar>
        </el-main>
      </el-container>
    </div>

  </div>

</template>

<script>
import {AppMain} from "@/layout/components";
import AppLink from '../Sidebar/Link'

export default {
  name: "EnterpriseMain",
  components: {AppMain, AppLink},
  data() {
    return {
      showLastBreadcrumb: false,
      lastBreadcrumbText: ''
    }
  },
  methods: {
    onClick(f) {
      this.showLastBreadcrumb = true;
      this.lastBreadcrumbText = f
    }
  }
}
</script>

<style lang="scss" scoped>

aside {
  background: #17C2AA;
  box-shadow: 4px 0px 4px 0px rgba(0, 0, 0, 0.08);
  height: 860px;
  width: 195px;
}

.el-main {
  padding: 0;
}

::v-deep .el-menu {
  border-right: 0;

  .el-menu-item {
    color: #FFFFFF !important;

    i {
      color: #FFFFFF !important;
    }
  }

  .el-menu-item.is-active {
    color: #17C2AA !important;
    background-color: #FFFFFF !important;
    border-radius: 6px;

    i {
      color: #17C2AA !important;
    }
  }
}


</style>
