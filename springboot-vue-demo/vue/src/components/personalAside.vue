<template>
  <el-menu
      default-active="1"
      class="el-menu-vertical-demo"
      :collapse=false
      @open="handleOpen"
      @close="handleClose"
      background-color=""
      >
    <el-menu-item index="1" @click="$router.push('/personSpace/person')">
      <el-icon><IconMenu /></el-icon>
      <template #title >个人信息</template>
    </el-menu-item>

    <el-menu-item index="2" @click="$router.push('/personSpace/myWallet')">
      <el-icon><wallet/></el-icon>
      <template #title >我的钱包</template>
    </el-menu-item>

    <el-menu-item index="3" @click="$router.push('/personSpace/myOrder')">
      <el-icon><document /></el-icon>
      <template #title>我的订单</template>
    </el-menu-item>

    <el-menu-item index="4" @click="$router.push('/personSpace/cart')">
      <el-icon><shoppingCart /></el-icon>
      <template #title>购物车</template>
    </el-menu-item>

    <el-menu-item index="5" @click="$router.push('/personSpace/favorite')">
      <el-icon><collection /></el-icon>
      <template #title>收藏</template>
    </el-menu-item>

    <el-menu-item index="6" @click="$router.push('/personSpace/setting')">
      <el-icon><setting /></el-icon>
      <template #title>设置</template>
    </el-menu-item>
  </el-menu>
<!--  </div>-->
<!--</div>-->
</template>


<script setup>
import { ref } from 'vue'
import {
  Location,
  Document,
  Menu as IconMenu,
  Setting,
  Wallet,
  ShoppingCart,
  Collection
} from '@element-plus/icons-vue'
const isCollapse = ref(true)
</script>

<style scoped>
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  min-height: 0px;
}
</style>