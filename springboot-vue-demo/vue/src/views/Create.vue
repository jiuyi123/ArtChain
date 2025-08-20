<template>
  <div  style="display: flex;background-color: beige;">
    <el-card style="width: 100%; margin: 10px">
      <div style="text-align:center">
        <h1>艺  术  品  创  作</h1>
      </div>
      <div display="flex" style="margin: 10px 0">
          <el-form :model="form" label-width="120px">
            <el-form-item label="作品上传">
              <div style="text-align:left">
<!--                <h1>上传一个作品</h1>-->
                <el-upload
                    class="upload-demo"
                    drag
                    action="/dataj/files/upload" :on-success="filesUploadSucess"
                    multiple>
                  <el-icon class="el-icon--upload"><upload-filled/></el-icon>
                  <div class="el-upload__text">
                    Drop file here or <em>click to upload</em>
                  </div>
                </el-upload>
              </div>
            </el-form-item>
            <el-form-item label="艺术品名称">
              <el-input v-model="form.nftName" style="width: 80%"></el-input>
            </el-form-item>
            <el-form-item label="艺术品价格">
              <el-input v-model="form.nftPrice" style="width: 80%"></el-input>
            </el-form-item>
            <el-form-item label="艺术品类型">
              <el-radio v-model="form.nftClass" label="1">图片</el-radio>
              <el-radio v-model="form.nftClass" label="2">音乐</el-radio>
              <el-radio v-model="form.nftClass" label="3">视频</el-radio>
              <el-radio v-model="form.nftClass" label="4">实体</el-radio>
            </el-form-item>
            <el-form-item label="艺术品描述" >
              <el-input v-model="form.nftDescri" style="width: 80%"  :rows="4" type="textarea"></el-input>
            </el-form-item>
            <el-form-item label="外部链接">
              <el-input v-model="form.nftLoc" style="width: 80%"></el-input>
            </el-form-item>
            <el-form-item label="">
              <el-button size="big" type="primary" style="width:10%" @click="build">创 建</el-button>
            </el-form-item>
          </el-form>
      </div>
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
            <el-button type="primary" style="margin-left: 24%" @click="beginCreat">确认</el-button>
            <el-button type="default" @click="passwordDialogVisible = false">取消</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>

      <!--      存储上传的交易hash，作为确权凭证-->
      <el-dialog
          title="确权凭证"
          v-model="identityDialogVisible"
          width="40%"
          center>
        <el-form>
          <el-form-item>
            <el-input v-model="uploadHash"></el-input>
          </el-form-item>
          <el-form-item>
            <div style="color: red"><h4>为避免作品确权问题，请妥善保管该凭证</h4></div>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" style="margin-left: 40%" @click="identityDialogVisible=false">确认</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>
    </el-card>
  </div>
</template>
<script>
import request from "@/utils/request";
  export default {
    data(){
      return{
        passwordDialogVisible:false,
        identityDialogVisible:false,
        form:{},
        userform:{},
        neworder:{},
        toChain:{},
        nftId:0,
        uploadHash:"",
      }
    },
    created() {
      this.userload();
    },
    methods:{
      userload(){//从数据库加载数据渲染到前端页面
        request.get("/dataj/user/personSpace").then(res=>{
          console.log('person-res')
          console.log(res)
          this.userform=res.data;
          console.log("finish")
        })
      },
      //文件上传成功返回信息
      filesUploadSucess(res) {
        console.log("res");
        // console.log(res.data);
        this.form.nftLoc=res.data.nftLoc;
        this.form.nftHash=res.data.nftHash;
        this.form.nftFingerPrint=res.data.nftFingerPrint;
        this.form.nftUid=res.data.nftUid;
        console.log(this.form);
        // console.log(this.form.nftLoc);
      },
      build(){
        this.passwordDialogVisible = true;
      },
      beginCreat(){
        console.log("beginCreat")
        console.log(this.userform)
        this.form.nftWriterId=this.userform.userId;
        this.form.nftOwnerId=this.userform.userId;
        // console.log(this.form)
        //将nft插入到数据库中
        request.post("/dataj/nft/create",this.form).then(res=>{
          //执行完毕后结果回到res这
          console.log(res);
          //弹窗提示正确
          if(res.code==='0')
          {
            this.$message({
              type:"message",
              message:"系统检测通过，等待第三方审核"
            })
            this.nftId=res.data
            /***********送入第三方************/
            //********************order 新加order************************/
            this.neworder.orderSaleId = this.userform.userId;
            this.neworder.orderBuyerId = this.userform.userId;
            this.neworder.orderPrice = this.form.nftPrice;

            //上传区块链
            this.toChain.vntAccount=this.userform.userAddr;//buyhash
            this.toChain.vntPassword=this.userform.userConfirmPassword+ this.userform.userIdentity;//password
            this.toChain.vntHash= this.form.nftHash;

            //清空form
            this.form={}

            //获取区块链上交易hash
            request.post("/api/user/upload",this.toChain).then(res => {
              console.log("上传区块链")
              console.log(this.toChain);
              console.log(res);
              this.neworder.orderHash = res.vntTransaction;
              this.uploadHash = res.vntTransaction;
              //保存交易hash
              this.identityDialogVisible = true
              // console.log(this.neworder.orderHash);
            })

            //将订单保存到数据库中
            setTimeout(() => {
              this.neworder.orderNftId=this.nftId
              console.log("将订单保存到数据库中")
              console.log(this.neworder)
              request.post("/dataj/orders/insert",this.neworder).then(res => {
                console.log(res);
              });
            }, 3000);

          }
          else{
            //存在相同的作品
            this.$message({
              type:"error",
              message:res.msg,
            })
            this.form={}
            return
          }
        })

        this.passwordDialogVisible=false;
      },



    }
  }
</script>
<script setup>
name: "Create"
</script>
<style scoped>
</style>