<template>
  <div>
    <vue-particles
        class="login-bg"
        color='#39AFFD'
        :particleOpacity="0.7"
        :particlesNumber="80"
        shapeType="circle"
        :particleSize="6"
        linesColor="#8DD1FE"
        :linesWidth="1"
        :lineLinked="true"
        :lineOpacity="0.4"
        :linesDistance="150"
        :moveSpeed="3"
        :hoverEffect="true"
        hoverMode="grab"
        :clickEffect="true"
        clickMode="push"
    >
    </vue-particles>
    <div class="login-box">
      <div class="login-box-title">
        新用户注册
      </div>
      <div class="login-box-from">
        <el-form ref="form" :model="form" size="normal" :rules="rules">

          <el-form-item prop="userName" >
            <el-input :prefix-icon="User" v-model="form.userName" placeholder="请输入用户名"></el-input>
          </el-form-item>

          <el-form-item prop="userPassword" >
            <el-input :prefix-icon="Lock" v-model="form.userPassword" placeholder="请输入网站账户密码" show-password></el-input>
          </el-form-item>

          <el-form-item prop="confirm">
            <el-input :prefix-icon="Lock" v-model="form.confirm"  placeholder="请再次输入密码确认" show-password></el-input>
          </el-form-item>

          <el-form-item prop="userIdentity">
            <el-input :prefix-icon="CreditCard" v-model="form.userIdentity"  placeholder="请输入身份证号"></el-input>
          </el-form-item>

          <el-form-item prop="userConfirmPassword">
            <el-input :prefix-icon="Lock" v-model="form.userConfirmPassword"  placeholder="请输入确权密码" show-password></el-input>
          </el-form-item>

          <el-form-item prop="userEmail" >
            <el-input :prefix-icon="ElemeFilled" v-model="form.userEmail" placeholder="请输入个人邮箱"></el-input>
          </el-form-item>

          <el-form-item style="display: flex">
            <el-input v-model="emailCode" style="width: 50%"  placeholder="请输入验证码"></el-input>
            <el-button v-if="sendHide" type="primary" @click="getCode" style="margin-left: 10%">获取验证码</el-button>
            <el-button v-if="!sendHide" type="primary" style="margin-left: 10%">已成功发送</el-button>
          </el-form-item>

          <el-form-item >
            <el-button style="width: 100%;" type="primary" @click="register">注 册</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>
<script>
  import request from "@/utils/request";
  import axios from "axios";
  export default {
    name:"Register",
    data(){
      return{
        form:{},
        emailCode:"",
        sendHide:true,
        rules:{
          userName:[
            { required: true, message: '请输入用户名', trigger: 'blur' },
            { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' },
            // { validator: letterRule, trigger: 'blur' }
          ],
          userPassword:[
            { required: true, message: '请输入密码', trigger: 'blur'},
            { min: 6, max: 16, message: '长度在 6 到 16 个字符,由数字和字母组成', trigger: 'blur' },
            // { validator: letterRule, trigger: 'blur' }

          ],
          confirm:[
            { required:true,message:'请确认密码',trigger:'blur'},
            // { validator: letterRule, trigger: 'blur' }
          ],
          userEmail:[
            { required:true,message:'请输入邮箱',trigger:'blur'},
            // { validator: letterRule, trigger: 'blur' }
          ],
          userIdentity:[
            { required:true,message:'请输入身份证号',trigger:'blur'},
            // { validator: letterRule, trigger: 'blur' }
          ]
        },
      }
    },
    methods:{
      register(){
        this.$refs['form'].validate((valid)=>{
          if(valid){
            //验证码校验
            if(this.emailCode!="6306")
            {
              this.$message({
                type:"error",
                message:'验证码输入错误'})
              return
            }
            //密码输入不一致
            if(this.form.userPassword!==this.form.confirm)
            {
              this.$message({
                type:"error",
                message:'两次密码输入不一致'})
              return
            }
            //从区块链申请账户
            console.log("从区块链申请账户")
            console.log(this.form.userConfirmPassword+this.form.userIdentity)
            request.post("/api/user/newaccounts",{ "vntPassword":this.form.userConfirmPassword+this.form.userIdentity}).then(res=>{
              console.log(res)
              this.form.userAddr=res.vntAccount
            })
            //将信息插入到数据库
            setTimeout(() => {
              console.log(this.form);
              request.post("/dataj/user/register",this.form).then(res=>{
                if(res.code==='0')
                {
                  this.$message({
                    type:"message",
                    message:"注册成功"
                  })
                  console.log("注册成功")
                  this.$router.push("/login")
                }
                else{
                  this.$message({
                    type:"error",
                    message:res.msg
                  })
                }
              })
            }, 2000);
          }
        })
      },
      getCode() {
        this.sendHide = false;

        this.$message({
          message: "已发送验证码",
          type: "success"
        });
        //   axios.get("url", {
        //         params: {
        //           email: this.form.email
        //         }
        //       }).then(() => {
        //         this.$message({
        //           message: "已发送验证码",
        //           type: "success"
        //         });
        //       })
        //       .catch(() => {
        //         this.$message({
        //           message: "请求超时，请检查网络连接",
        //           type: "error"
        //         });
        //       });
        // },
      },
      submit() {
        const data = {
          email: this.email,
          code: this.code
        };
        axios.post("url", data).then(res => {
              if ((res.data.code = '')) {
                this.$message({
                  message: "验证码错误",
                  type: "error"
                });
                return;
              } else {
                this.$router.push({ path: "/Login" });
                this.$message({
                  message: "验证通过,注册成功",
                  type: "success"
                });
              }
            })
            .catch(() => {
              this.$message({
                message: "请求超时，请检查网络连接",
                type: "error"
              });
            });
      },
    }
  }
</script>
<script setup>
import {Lock,User ,ElemeFilled,CreditCard} from '@element-plus/icons-vue'
</script>
<style scoped>
.login-bg{
  width: 100%;
  height: 100%;
  background:  #e5f2ff;
}
.login-box{
  width: 350px;
  /* height: 287px; */
  background: hsla(0,0%,100%,.2);
  background-color: #b3d8ff;
  border-radius: 5px;
  /*box-shadow: 0 0 2px #f7f7f7;*/
  border: 1px solid #f7f7f7;
  position: absolute;
  left: 50%;
  top: 50%;
  margin-left: -175px;
  margin-top: -250px;
}
.login-box-title{
  line-height: 50px;
  font-size: 36px;
  color: deepskyblue;
  text-align: center;
  border-bottom: 1px solid #ffffff;

}
.login-box-from{
  width: 100%;
  height: auto;
  padding: 30px;
  box-sizing: border-box;
}
</style>