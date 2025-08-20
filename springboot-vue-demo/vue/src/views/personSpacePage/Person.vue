<template>
<!--  <div>个人信息</div>-->
<div>
  <div>
    <img src="../../assets/img/myimg/background.png" style="width: 100%;"/>
    <div style="position:absolute; z-index:2; left:50%; top:10%;color:#6b778c;font-size: 40px;">个 人 主 页</div>
<!--    <div style="position:absolute; z-index:2; left:78%; top:50%;">-->
<!--      <el-button type="default">关注 15</el-button>-->
<!--      <el-button type="default">粉丝 10</el-button>-->
<!--      <el-button type="default">作品 0</el-button>-->
<!--    </div>-->
  </div>
  <div>
    <el-card style="width: 90%; margin: 10px">
      <el-form ref="form" :model="form" label-width="100px">
        <el-form-item label="用户名">
          <el-input v-model="form.userName"></el-input>
        </el-form-item>

        <el-form-item label="用户ID">
          <el-input v-model="form.userId"></el-input>
        </el-form-item>

        <el-form-item label="身份证号">
          <el-input v-model="form.userIdentity"></el-input>
        </el-form-item>

        <el-form-item label="区块链账户" label-width="100px">
          <el-input v-model="form.userAddr"></el-input>
        </el-form-item>

<!--        <div style="text-align: center">-->
<!--          <el-button type="primary" @click="update">修改信息</el-button>-->
<!--        </div>-->
      </el-form>
    </el-card>
  </div>
</div>
</template>

<script>
  import request from "@/utils/request";

  export default {
    name: "Person",
    data() {
      return {
        form: {},
        backgroundsrc:'https://cube.elemecdn.com/6/94/4d3ea53c084bad6931a56d5158a48jpeg.jpeg'
      }
    },
    created() {
      this.load()
      console.log("load")
    },
    methods: {
      load(){//从数据库加载数据渲染到前端页面
        request.get("/dataj/user/personSpace").then(res=>{
          console.log('person-res')
          console.log(res)
          this.form=res.data;
        })
      },

      handleAvatarSuccess(res) {
        this.form.avatar = res.data
        this.$message.success("上传成功")
        // this.update()
      },
      update() {
        // TODO 接口没写
        request.put("/dataj/user", this.form).then(res => {
          console.log(res)
          if (res.code === '0') {
            this.$message({
              type: "success",
              message: "更新成功"
            })
            sessionStorage.setItem("user", JSON.stringify(this.form))
            // TODO   触发Layout更新用户信息
            this.$emit("userInfo")
          } else {
            this.$message({
              type: "error",
              message: res.msg
            })
          }
        })
      }
    }
  }
</script>
<script  setup>

</script>
<style>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 120px;
  height: 120px;
  line-height: 120px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
