<template>
  <div style="height: 50px;line-height: 50px;border-bottom: 1px solid #ccc;display:flex;background-color:#b3edff">
    <div style="width: 250px;padding-left: 30px;font-weight:bold;color: deepskyblue" @click="$router.push('/admin/user')">艺 术 品 NFT 平 台</div>

    <div style="width: 90%;display:flex;margin-left: 20%">
      <div style="flex: 1" @click="jumpMarket">商店</div>
      <div style="flex: 1" @click="jumpMyArts">我的作品</div>
      <div style="flex: 1" @click="jumpCreate">创作</div>
      <div style="flex: 1" @click="$router.push('/contact')">contact</div>
      <div style="flex: 1">
        <el-dropdown style="margin-top: 15px">
        <span class="el-dropdown-link" style="width: 100px;padding-left: 30px;font-weight:bold;color: blue;font-size: larger">
         {{form.userName }}<i class="el-icon-arrow-down el-icon--right"></i>
        </span>
          <template #dropdown>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @click="$router.push('/personSpace/person')">个人信息</el-dropdown-item>
              <el-dropdown-item @click="$router.push('/login')">退出系统</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
<!--      &lt;!&ndash;      注册框&ndash;&gt;-->
<!--      <el-dialog v-model="logupdialogVisible" title="Tips" width="30%">-->
<!--        <el-form-item label="用户名">-->
<!--          <el-input v-model="form.username" style="width: 80%"></el-input>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="密码">-->
<!--          <el-input v-model="form.username" style="width: 80%"></el-input>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="年龄">-->
<!--          <el-input v-model="form.age" style="width: 80%"></el-input>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="性别">-->
<!--          <el-radio v-model="form.sex" label="男" size="large">男</el-radio>-->
<!--          <el-radio v-model="form.sex" label="女" size="large">女</el-radio>-->
<!--          <el-radio v-model="form.sex" label="未知" size="large">未知</el-radio>-->
<!--        </el-form-item>-->
<!--        <template #footer>-->
<!--          <span class="dialog-footer">-->
<!--            <el-button @click="logupsave">Cancel</el-button>-->
<!--            <el-button type="primary" @click="logupsave">Confirm</el-button>-->
<!--          </span>-->
<!--        </template>-->
<!--      </el-dialog>-->
<!--      &lt;!&ndash;      登录框&ndash;&gt;-->
<!--      <el-dialog v-model="logindialogVisible" title="Tips" width="30%">-->
<!--        <el-form-item label="用户名">-->
<!--          <el-input v-model="form.username" style="width: 80%"></el-input>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="密码">-->
<!--          <el-input v-model="form.username" style="width: 80%"></el-input>-->
<!--        </el-form-item>-->
<!--        <template #footer>-->
<!--          <span class="dialog-footer">-->
<!--            <el-button @click="loginsave">Cancel</el-button>-->
<!--            <el-button type="primary" @click="loginsave">Confirm</el-button>-->
<!--          </span>-->
<!--        </template>-->
<!--      </el-dialog>-->
<!--      二维码-->
<!--      <el-dialog-->
<!--          title="微信扫码支付"-->
<!--          v-model="CodedialogVisible"-->
<!--          width="20%"-->
<!--          center-->
<!--          @close="close">-->
<!--        <img src="../assets/img/code.jpg" style="width:260px;height:260px;display:flex">-->
<!--        <p style="text-align:center;margin:5px"><el-button type="primary" size="mini" @click="refresh">刷新二维码</el-button></p>-->
<!--&lt;!&ndash;        <p style="text-align:center"><b>订单名称:{{dialogData.ordername}}</b></p>&ndash;&gt;-->
<!--&lt;!&ndash;        <p style="text-align:center"><b>订单价格:{{dialogData.orderprice}}元</b></p>&ndash;&gt;-->
<!--&lt;!&ndash;        <p style="text-align:center"><b>数量:{{dialogData.ordernum}}</b></p>&ndash;&gt;-->
<!--        <div slot='footer' class="dialog-footer" style="text-align:center">-->
<!--          <el-button type="primary" @click="confirm">已支付</el-button>-->
<!--          <el-button type="warning" @click="cancel">取消</el-button>-->
<!--        </div>-->
<!--      </el-dialog>-->
<!--    </div>-->
<!--    <div style="width: 100px;padding-left: 30px;font-weight:bold;color: blue">-->
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";
export default {
  created(){
    if(!this.from)
    {
      this.load()
      console.log("load")
    }
  },
  methods:{
    load() {//从数据库加载数据渲染到前端页面
      console.log('header-res')
      request.get("/dataj/user/personSpace").then(res => {
        console.log(res);
        this.form = res.data;
      })
    },
    jumpPersonSpace(){
      //alert('hello world');
      this.$router.push('/personSpace/person')
      },
    jumpHome(){
      this.$router.push('/')
    },
    jumpMarket(){
      this.$router.push('/Market')
    },
    jumpMyArts(){
      this.$router.push('/MyArts')
    },
    jumpCreate(){
      this.$router.push('/Create')
    },
    login(){
      this.logindialogVisible=true;
      this.form={};
    },
  },
  data() {
    return {
      form: {
        userName:"",
      },
      search: '',
      currentPage: 1,
      total: 10,
      logupdialogVisible: false,
      logindialogVisible: false,
      tableData: []
    }
  }
}

</script>

<style scoped>
.example-showcase .el-dropdown-link {
  cursor: pointer;
  color: var(--el-color-primary);
  display: flex;
  align-items: center;
}
</style>