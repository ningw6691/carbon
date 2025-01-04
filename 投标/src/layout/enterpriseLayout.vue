<template>
  <el-container>
    <el-header>
      <Header/>
    </el-header>
    <el-main>
      <enterprise-main/>
    </el-main>
  </el-container>
</template>

<script>
import Header from './components/platform/Header'
import EnterpriseMain from "@/layout/components/platform/EnterpriseMain.vue";

export default {
  name: 'enterpriseLayout',
  components: {
    Header, EnterpriseMain
  },
  created() {
  }
}
</script>

<style lang="scss" scoped>
@import "~@/assets/styles/mixin.scss";
@import "~@/assets/styles/variables.scss";

.el-container {
  height: 100%;
}

.el-header {
  background-color: #FFFFFF;
  text-align: center;
  padding: 0 0 0 0;
  margin: 0 0 0 0;
}

.el-footer {
  background-color: #B3C0D1;
  color: #333;
  text-align: center;
  line-height: 60px;
}

.el-main {
  background-color: #FAFAFA;
  color: #333;
  text-align: center;
}
</style>
