<template>
  <el-container>
    <el-header>
      <Header/>
    </el-header>
    <el-main>
      <app-main/>
    </el-main>
  </el-container>
  <!--    <div v-if="device==='mobile'&&sidebar.opened" class="drawer-bg" @click="handleClickOutside"/>-->
  <!--    <sidebar class="sidebar-container" :style="{ backgroundColor: sideTheme === 'theme-dark' ? variables.menuBg : variables.menuLightBg }" />-->
  <!--    <div :class="{hasTagsView:needTagsView}" class="main-container">-->
  <!--      <div :class="{'fixed-header':fixedHeader}">-->
  <!--        <navbar />-->
  <!--        <tags-view v-if="needTagsView" />-->
  <!--      </div>-->
  <!--      <app-main />-->
  <!--      <right-panel v-if="showSettings">-->
  <!--        <settings />-->
  <!--      </right-panel>-->
  <!--    </div>-->

</template>

<script>
import Header from './components/platform/Header'
import {AppMain} from './components'

export default {
  name: 'Layout',
  components: {
    Header, AppMain
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
