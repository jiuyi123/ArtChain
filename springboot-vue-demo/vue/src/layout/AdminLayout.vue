<template>
  <div>
    <!--头部-->
    <AdminHeader>
    </AdminHeader>
    <div style="display: flex">
      <!--    侧边栏-->
      <Aside/>
      <!--    主体-->
        <router-view style="display: flex"/>
    </div>

  </div>
</template>

<script>
import Aside from "@/components/Aside";
export default {
  name: "AdminLayout",
  components: {
    Aside
  },
}
</script>

<style scoped>

</style>

<script setup>
import AdminHeader from "@/components/AdminHeader"


</script>