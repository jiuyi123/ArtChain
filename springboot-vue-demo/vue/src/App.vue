<template>
  <div>
    <router-view/>
  </div>
</template>
<style>

</style>

<script>
import Header from "@/components/Header";
import Aside from "@/components/Aside";
import {Pear} from "@element-plus/icons-vue";
import PersonSpace from "@/views/personSpace";
import About from "@/views/About";
export default
{
  name: "Layout",
  components:{
    About,
    PersonSpace,
    Pear,
    Header,
    Aside
  }
}
</script>