<template>
<div>
  <el-card style="width: 40%; margin: 10px">
    <el-form ref="form" :model="form" label-width="80px">
      <el-form-item label="钱包余额">
        <el-input v-model="form.userMoney"></el-input>
      </el-form-item>

      <div style="text-align: center">
        <el-button type="primary" @click="">充值</el-button>
        <el-button type="primary" @click="">提现</el-button>
      </div>
    </el-form>
  </el-card>
</div>
</template>

<script>
import request from "@/utils/request";
export default {
  data() {
    return {
      form: {},
      // backgroundsrc:'https://cube.elemecdn.com/6/94/4d3ea53c084bad6931a56d5158a48jpeg.jpeg'
    }
  },
  name: "MyWallet",
  created() {
    if(!this.from)
    {
      this.load()
      console.log("load")
    }

  },
  methods: {
    load() {//从数据库加载数据渲染到前端页面
      console.log('mywallet-res')
      request.get("/dataj/user/personSpace").then(res => {
        console.log(res);
        this.form = res.data;
      })
    }
  }
}
</script>

<style scoped>

</style>