<template>
  <div>
    <el-card style="margin: 0px">
      <div style="text-align:center">
        <h1 style="">我  的  作  品</h1>
        <el-radio-group v-model="radio" style="align-content: end">
          <el-radio :label="1">所有作品</el-radio>
          <el-radio :label="2">已上架</el-radio>
          <el-radio :label="3">未上架</el-radio>
        </el-radio-group>
      </div>
    </el-card>
    <el-card>
  <!--  <template>-->
      <el-scrollbar height="580px" style="border: #3E3E3E">
        <div display="inline-block" v-for="list in lists" style="margin: 10px;" >
        <el-card style="width: 100%; margin-left: 10px;display: inline-block;border-color: #6b778c;border-width: 4px">
          <el-row>
          <el-card style="width: 20%;">
            <img :src=list.nftLoc width="250" height="250">
          </el-card>

          <el-card style="width: 60%;margin-left: 10px" >
            <el-form ref="form" :model="lists" label-width="80px">
              <el-form-item label="作品名称">
                <el-input v-model="list.nftName"></el-input>
              </el-form-item>
              <el-form-item label="作品价格">
                <el-input v-model="list.nftPrice"></el-input>
              </el-form-item>
              <el-form-item label="作品描述">
                <el-input v-model="list.nftDescri"></el-input>
              </el-form-item>
              <el-form-item label="作品AID">
                <el-input v-model="list.nftHash"></el-input>
              </el-form-item>


              <el-form-item v-if="list.nftCheck==1">
                <el-button type="primary" v-if=!list.nftSta @click="changemarket(list)">作品上架</el-button>
                <el-button type="primary" v-if=list.nftSta @click="changemarket(list)">作品下架</el-button>
                <el-button type="primary" @click="">修改信息</el-button>
              </el-form-item>
              <el-form-item label="" >
                <div><h2  v-if="list.nftCheck==0">系统查重已通过，第三方审核中，审核通过后可以在此查看</h2></div>
                <div><h3 style="color: #42b983" v-if="list.nftCheck==1">审核通过</h3></div>
                <div><h3 style="color: red" v-if="list.nftCheck==2">审核未通过</h3></div>
              </el-form-item>
            </el-form>
          </el-card>
          </el-row>
        </el-card>

      </div>
      </el-scrollbar>
  <!--  </template>-->
    </el-card>
  <!--  </el-card>-->
    <!--      输入确权密码-->
    <el-dialog
        title="请输入确权密码"
        v-model="passwordDialogVisible"
        width="20%"
        center
        @click="ConfirmPassword">
      <el-form>
        <el-form-item prop="userIdentity">
          <el-input :prefix-icon="Lock" v-model="userform.userConfirmPassword"  placeholder="请输入确权密码" show-password></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" style="margin-left: 24%" @click="beginChain">确认</el-button>
          <el-button type="default" @click="passwordDialogVisible = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

  </div>
</template>
<script>
import request from "@/utils/request";
export default {

  name: "MyArts",
  data(){
    return{
      passwordDialogVisible:false,
      identityDialogVisible:false,
      currentNft:"",
      uploadHash:"",
      form:{},
      lists: [],
      radio:1,
      toChain:{},
      userform:{},
      webform:{
        vntAccount:"0x10ce29289e7fdaf012a83bb7e273ec1788a22d02",
        vntPassword:"nft123",
      },
      nftHash:"",

    }
  },
  created() {
    this.userload();
    setTimeout(() => {
      this.load();
    }, 500);

  },
  methods:{
    userload() {//从数据库加载数据渲染到前端页面
      request.get("/dataj/user/personSpace").then(res => {
        this.userform = res.data;
        this.form.nftOwnerId =res.data.userId;
      })
    },
    load(){//从数据库加载数据渲染到前端页面
      console.log("load")
      console.log("load"+this.form.nftOwnerId)
      request.get("/dataj/nft/myartload", {
        params:{
          userId:this.form.nftOwnerId,
          pageNum:1,
          pageSize: 100,
        }
      }).then(res=>{
        // console.log(res)
        console.log(res.data.records)
        this.lists=res.data.records
      })
    },
    changemarket(list){
      this.passwordDialogVisible=true;

      console.log("--------------changemarket--------------")
      console.log(list)
      this.nftHash= list.nftHash;
      this.currentNft =  list;
      // console.log("---------------将nft更新到数据库中-------------")
      // //将nft更新到数据库中
      // request.put("/dataj/nft/update",list).then(res=>{
      //   console.log(res);
      //   if(res.code==='0')
      //   {
      //     // this.$message({
      //     //   type:"message",
      //     //   message:"更改成功"
      //     // })
      //   }
      //   else{
      //     this.$message({
      //       type:"error",
      //       message:res.msg
      //     })
      //   }
      // })
    },
    beginChain(){
      console.log("---------------toChain-------------")
      this.toChain.vntFrom=this.userform.userAddr;//creat
      this.toChain.vntTo=this.webform.vntAccount;//网站公共账户
      this.toChain.vntPassword=this.userform.userConfirmPassword+this.userform.userIdentity;//password
      this.toChain.tokenId= this.nftHash

      if( this.currentNft.nftSta)
        this.currentNft.nftSta=0
      else
      {
        this.currentNft.nftSta=1
      }

      console.log(this.toChain)
      //获取区块链上交易hash
      request.post("/api/user/approve",this.toChain).then(res => {
        console.log(this.toChain);
        console.log("取区块链上卖方赋权给网站")
        console.log(res);
        this.$message({
          type:"message",
          message:"更改成功."
        })
        this.uploadHash=res.transactionHash;
      })

      console.log("---------------将nft更新到数据库中-------------")
      //将nft更新到数据库中
      request.put("/dataj/nft/update",this.currentNft).then(res=>{
        console.log(res);
        if(res.code==='0')
        {
          this.$message({
            type:"message",
            message:"更改成功"
          })
        }
        else{
          this.$message({
            type:"error",
            message:res.msg
          })
        }
      })
      this.passwordDialogVisible = false
    },
  }
}
</script>

<style scoped>

</style>