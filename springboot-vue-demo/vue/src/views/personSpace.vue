<template>
<!--  侧边栏-->
  <personalAside/>
<!--  详细信息-->
  <router-view style="flex: 1"/>
<!--  <div>-->
<!--    我的作品-->
<!--  </div>-->
</template>

<script>
import request from "@/utils/request";
import personalAside from "@/components/personalAside";
export default {
  name: 'personSpace',
  components: {
    personalAside
  },
}

</script>

<style scoped>

</style>