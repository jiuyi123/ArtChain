<template>
  <el-menu
      default-active="1-1"
      class="el-menu-vertical-demo"
      :collapse=false
      @open="handleOpen"
      @close="handleClose"
      background-color="lightblue"
      >
    <!--    系统后台信息管理-->
      <el-sub-menu index="1">
          <template #title >
            <el-icon><TrendCharts/></el-icon>
            <h3>后台信息管理</h3>
          </template>
          <el-menu-item index="1-1" @click="$router.push('/admin/user')">
            <el-icon><User/></el-icon>
            <template #title ><h4>用户管理</h4></template>
          </el-menu-item>

          <el-menu-item index="1-2" @click="$router.push('/admin/ArtsCheck')">
            <el-icon><collection/></el-icon>
            <template #title ><h4>艺术品管理</h4></template>
          </el-menu-item>

          <el-menu-item index="1-3" @click="$router.push('/Admin/orderManage')">
            <el-icon><document /></el-icon>
            <template #title><h4>订单管理</h4></template>
          </el-menu-item>
        </el-sub-menu>
    <!--    第三方审核-->
      <el-sub-menu index="2">
        <template #title >
          <el-icon><Stamp/></el-icon>
          <h3>第三方审核</h3>
        </template>
      <el-menu-item index="2-1" @click="$router.push('/check/ContentCheck')">
        <el-icon><CircleCheck/></el-icon>
        <template #title ><h4>内容审核</h4></template>
      </el-menu-item>

      <el-menu-item index="2-2" @click="$router.push('/check/SimilarityCheck')">
        <el-icon><DocumentCopy/></el-icon>
        <template #title ><h4>相似度审核</h4></template>
      </el-menu-item>
    </el-sub-menu>
    <!--    管理员信息-->
      <el-sub-menu index="3">
        <template #title >
          <el-icon><Avatar/></el-icon>
          <h4>管理员信息</h4>
        </template>

      <el-menu-item index="3-1" @click="$router.push('/admin/AdminInfo')">
        <template #title >
          <el-icon><User/></el-icon>
          <h3>个人信息</h3>
        </template>
      </el-menu-item>

      <el-menu-item index="3-2" @click="$router.push('/admin/user')">
        <el-icon><Warning/></el-icon>
        <template #title ><h4>待处理事件</h4></template>
      </el-menu-item>

      <el-menu-item index="3-3" @click="$router.push('/Admin/orderManage')">
        <el-icon><Setting /></el-icon>
        <template #title><h4>系统设置</h4></template>
      </el-menu-item>
    </el-sub-menu>
  </el-menu>
</template>

<script setup>
import { ref } from 'vue'
import {
  Avatar, Document, Collection, Setting, Warning, User, Stamp, TrendCharts,CircleCheck,DocumentCopy
} from '@element-plus/icons-vue'
const isCollapse = ref(true)
</script>

<style scoped>
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  min-height: 0px;
}
</style>