<template>
  <div style="height: 50px;line-height: 50px;border-bottom: 1px solid #ccc;display:flex;background-color:#b3edff">

    <div style="width: 300px;padding-left: 30px;font-weight:bold;color: deepskyblue" @click="$router.push('/market')"><h2>后 台 管 理</h2></div>
    <div style="width: 40%;display:flex;margin-left: 30%">
    </div>

    <div style="flex: 1">
      <div style="flex: 1">
        <el-dropdown style="margin-top: 15px">
        <span class="el-dropdown-link" style="width: 100px;padding-left: 30px;font-weight:bold;color: blue;font-size: larger">
         管理者<i class="el-icon-arrow-down el-icon--right"></i>
        </span>
          <template #dropdown>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item  @click="$router.push('/admin/AdminInfo')">个人信息</el-dropdown-item>
              <el-dropdown-item @click="$router.push('/login')">退出系统</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </div>
  </div>
</template>

<script>
import { ArrowDown } from '@element-plus/icons-vue'
export default {
  created() {
    this.load()
  },
  methods:{
    load(){
    //   request.get("/dataj/user/personSpace/person").then(res=>{
    //     console.log('person-res')
    //     console.log(res)
    //     this.form=res.data;
    //   })
    },
  },
  data() {
    return {
      form: {

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