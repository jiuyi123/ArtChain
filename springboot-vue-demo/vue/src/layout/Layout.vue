<template>
  <div>
      <!--    头部-->
      <Header/>
    <div  style="display: flex">
      <!--      侧边栏-->
<!--      <Aside/>-->
      <!--      内容区域-->
      <!--    主体-->
      <router-view style="flex: 1"/>
    </div>
  </div>
</template>

<script setup>
import Header from "@/components/Header"
</script>

<style scoped>

</style>