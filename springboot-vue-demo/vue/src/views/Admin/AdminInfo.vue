<template>
  <div>
    <el-card style="width: 40%; margin: 10px">
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item style="text-align: center" label-width="0">
          <!--          TODO 上传-->
          <el-upload
              class="avatar-uploader"
              action="https://fuss10.elemecdn.com/a/3f/3302e58f9a181d2509f3dc0fa68b0jpeg.jpeg"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
          >
            <!--            action="http://localhost:9090/files/upload"-->
            <img v-if="form.avatar" :src="form.avatar" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="用户名">
          <el-input v-model="form.username"></el-input>
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="form.nickName"></el-input>
        </el-form-item>
        <el-form-item label="年龄">
          <el-input v-model="form.age"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio v-model="form.sex" label="1">男</el-radio>
          <el-radio v-model="form.sex" label="0">女</el-radio>
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="form.phone"></el-input>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="form.email"></el-input>
        </el-form-item>
        <!--        <el-form-item label="密码">-->
        <!--          <el-input v-model="form.password" show-password></el-input>-->
        <!--        </el-form-item>-->
      </el-form>
      <div style="text-align: center">
        <div style="float: left;padding-left: 20%" >
          <el-button type="primary" @click="update">取消</el-button>
        </div>
        <div style="float: left;padding-left: 20%">
          <el-button type="primary" @click="update">保存</el-button>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script>
import request from "@/utils/request";
import Aside from "@/components/Aside";
export default {
  name: 'AdminInfo',
  components: {
    Aside
  },
  data() {
    return {
      //vnt,
      form: {},
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {//从数据库加载数据渲染到前端页面
      request.get("/dataj/user/personSpace/person").then(res => {
        console.log(res)
        this.form=res.data
      })
    },
  }
}
</script>

