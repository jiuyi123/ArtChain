<template>
  <div>
  <el-tabs v-model="activeName" @tab-click="handleClick">
    <el-card style="padding-top: 0px">
      <div style="display: flex;">
        <el-input v-model="input" placeholder="搜索框" style="width: 300px;padding-right: 10px"/>
        <el-button type="primary" :icon="Search" style=""></el-button>
      </div>
    </el-card>
      <el-tab-pane label="图片" name="first">
        <el-card>
          <el-scrollbar height="560px" style="border: #3E3E3E">
            <el-row>
            <div v-for="list in lists" style="display: flex;padding: 6px;padding-top: 0">
                <el-card style="border-color: black;border-width: 2px">
                  <img :src="list.nftLoc" lazy width="190" height="190" @click="Check(list)"/>
                  <div style="text-align: center;margin-bottom: 10px">
                    <h4>{{list.nftName}}</h4>
                    <h4>作品价格:{{list.nftPrice}}元</h4>
                  </div>
                  <div class="flex">
                    <el-button type="primary" :icon="Collection"  style="margin-left: 4%" @click="Check(list)" v-if="1">查看</el-button>
                    <el-button type="primary" :icon="ShoppingCart" @click="Buy(list)" v-if="buyButton">购买</el-button>
                    <el-button type="primary" :icon="ShoppingCart" @click="clickBuy" v-if="hadBuyButton">已购买</el-button>
                    <el-button type="primary" :icon="Collection" @click="clickCheck" v-if="checkButton">查看</el-button>
                  </div>
                </el-card>
      <!--        <Rcode/>-->
            </div>
          </el-row>
          </el-scrollbar>
        </el-card>
      </el-tab-pane>
      <el-tab-pane label="音乐" name="second">
        <el-scrollbar height="560px" style="border: #3E3E3E">
          <el-space direction="vertical" alignment="flex-start">
          <el-button @click="setLoading">Click me to reload</el-button>
          <el-skeleton style="width: 240px" :loading="loading" animated :count="3">
            <template #template>
              <el-skeleton-item variant="image" style="width: 400px; height: 267px" />
              <div style="padding: 14px">
                <el-skeleton-item variant="h3" style="width: 50%" />
                <div
                    style="
                display: flex;
                align-items: center;
                margin-top: 16px;
                height: 16px;
              "
                >
                  <el-skeleton-item variant="text" style="margin-right: 16px" />
                  <el-skeleton-item variant="text" style="width: 30%" />
                </div>
              </div>
            </template>
            <template #default>
              <el-card
                  v-for="item in lists"
                  :key="item.name"
                  :body-style="{ padding: '0px', marginBottom: '1px' }"
              >
                <img :src="item.imgUrl" class="image multi-content" />
                <div style="padding: 14px">
                  <span>{{ item.name }}</span>
                  <div class="bottom card-header">
                    <span class="time">{{ currentDate }}</span>
                    <el-button type="text" class="button">Operation button</el-button>
                  </div>
                </div>
              </el-card>
            </template>
          </el-skeleton>
        </el-space>
        </el-scrollbar>
      </el-tab-pane>
      <el-tab-pane label="视频" name="third">视频</el-tab-pane>
      <el-tab-pane label="实体" name="fourth">实体</el-tab-pane>
  </el-tabs>
<!--    购买菜单-->
    <el-dialog
        title="付款界面"
        v-model="buyDialogVisible"
        width="20%"
        center
        @close="close">
      <el-card style="text-align:left">
        <el-form ref="form" :model="form" >
          <el-form-item label="艺术品名称:">
            <el-message>{{goodsform.nftName}}</el-message>
          </el-form-item>
          <el-form-item label="艺术品价格:">
            <el-message>{{goodsform.nftPrice}}元</el-message>
          </el-form-item>
          <el-form-item label="艺术品AID:">
            <el-message>{{goodsform.nftHash}}</el-message>
          </el-form-item>
        </el-form>
      </el-card>
      <el-button type="warning" @click="cancel" style="margin: 10%;margin-bottom: 0px">取消支付</el-button>
        <el-button type="primary" @click="confirm"  style="margin-top: 10%;margin-bottom: 0px">确认支付</el-button>

    </el-dialog>
<!--    二维码-->
    <el-dialog
        title="微信扫码支付"
        v-model="codeDialogVisible"
        width="20%"
        center
        @close="close">
      <img src="../assets/img/code.jpg" style="width:260px;height:260px;display:flex">
      <p style="text-align:center;margin:5px"><el-button type="primary" size="mini" @click="refresh">刷新二维码</el-button></p>
      <p style="text-align:center"><b>订单名称:{{dialogData.ordername}}</b></p>
      <p style="text-align:center"><b>订单价格:{{dialogData.orderprice}}元</b></p>
      <p style="text-align:center"><b>数量:{{dialogData.ordernum}}</b></p>
      <p style="text-align:center"><b>TokenId:{{dialogData.tokenId}}</b></p>
      <div slot='footer' class="dialog-footer" style="text-align:center">
<!--        <el-button type="primary" @click="confirm">已支付</el-button>-->
        <el-button type="warning" @click="cancel">取消</el-button>
      </div>
    </el-dialog>
<!--    信息查看-->
    <el-dialog
        title="商品详细信息"
        v-model="goodsDialogVisible"
        width="40%"
        center
        @close="close">
      <el-image :src="goodsform.nftLoc" style="place-content: center"/>
      <el-card style="text-align:left">
        <el-form ref="form" :model="form">
          <el-form-item label="艺术品名称:">
            <el-message>{{goodsform.nftName}}</el-message>
          </el-form-item>
          <el-form-item label="Token Id:">
            <el-message>{{goodsform.nftHash}}</el-message>
          </el-form-item>
          <el-form-item label="艺术品价格:">
            <el-message>{{goodsform.nftPrice}}</el-message>
          </el-form-item>
          <el-form-item label="艺术品作者Id:">
            <el-message>{{goodsform.nftWriterId}}</el-message>
          </el-form-item>
          <el-form-item label="艺术品拥有者Id:">
            <el-message>{{goodsform.nftOwnerId}}</el-message>
          </el-form-item>
          <el-form-item label="艺术品描述:">
            <el-message>{{goodsform.nftDescri}}</el-message>
          </el-form-item>
        </el-form>
      </el-card>

    </el-dialog>
 <!--      存储上传的交易hash，作为确权凭证-->
    <el-dialog
        title="交易凭证"
        v-model="identityDialogVisible"
        width="40%"
        center>
      <el-form>
        <el-form-item>
          <el-input v-model="tradeHash"></el-input>
        </el-form-item>
        <el-form-item>
          <div style="color: red"><h4>为避免交易抵赖问题，请妥善保管该凭证</h4></div>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" style="margin-left: 40%" @click="identityDialogVisible=false">确认</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import request from "@/utils/request";
export default {

  name: "Market",
  data() {
    return {
      // form: [],
      lists: [],
      vntTransactionhash:"",
      identityDialogVisible:false,
      goodsDialogVisible:false,
      buyDialogVisible:false,
      goodsform:{},
      currentUser:{},
      neworder:{},
      toChain:{},
      webform:{
        vntAccount:"0x10ce29289e7fdaf012a83bb7e273ec1788a22d02",
        vntPassword:"nft123",
      },
    }
  },
  created() {
      this.load();
  },
  methods: {
    load() {//从数据库加载数据渲染到前端页面
      console.log("load")
      //获取商品信息
      request.get("/dataj/nft/marketload", {
        params: {
          pageNum: 1,
          pageSize: 100,
        }
      }).then(res => {
        // console.log(res)
        console.log(res.data.records)
        this.lists = res.data.records
      })

      //获取用户信息
      request.get("/dataj/user/personSpace").then(res => {
        console.log(res);
        this.currentUser = res.data;
      })
    },
    Check(list){
      console.log("check")
      console.log(list)
      this.goodsDialogVisible = true;
      this.goodsform=list
    },
    Buy(list){
      this.goodsform=list
      console.log("buy")
      // console.log(list)
      if(this.currentUser.userId!=this.goodsform.nftOwnerId)
      {
        this.buyDialogVisible = true;
      }
      else{
        this.$message({
          type:"message",
          message:"这是你自己的作品，你可以在“我的作品”里下架该商品",
        })
      }
    },
    confirm () {
      console.log("confirm")
      if(this.currentUser.userMoney<this.goodsform.nftPrice){
        // this.currentUser.userMoney<this.goodsform.nftPrice
        this.$message({
          type:"message",
          message:"余额不足",
        })
      }
      else{
        /********************user 钱包转账************************/
        request.get("/dataj/user/trans",{
          params: {
            sellId: this.goodsform.nftOwnerId,
            buyId: this.currentUser.userId,
            price:this.goodsform.nftPrice,
          }
          }).then(res => {
          console.log("trans-nft")
          console.log(res);
        })

        /********************order 新加order************************/
        this.neworder.orderNftId = this.goodsform.nftId;
        this.neworder.orderSaleId = this.goodsform.nftOwnerId;
        this.neworder.orderBuyerId = this.currentUser.userId;
        this.neworder.orderPrice = this.goodsform.nftPrice;

        this.toChain.vntFrom = this.webform.vntAccount;
        this.toChain.vntPassword = this.webform.vntPassword;
        this.toChain.vntTo=this.currentUser.userAddr;//buyhash
        this.toChain.tokenId= this.goodsform.nftHash;
        // 获取区块链上交易hash

        request.post("/api/user/transfer",this.toChain).then(res => {
          console.log("区块链上交易hash")
          console.log(this.toChain)
          this.neworder.orderHash = res.vntTranscation;
          this.tradeHash = res.vntTranscation;
          // console.log(this.neworder.orderHash);
        })
        // 将订单保存到数据库中

        setTimeout(() => {
          request.post("/dataj/orders/insert",this.neworder).then(res => {
            console.log("将订单保存到数据库中")
            console.log(this.neworder)
            this.identityDialogVisible = true;
          });
        }, 3000);
        /********************nft 更改owner,下架***********************/
        this.goodsform.nftOwnerId=this.currentUser.userId,
        this.goodsform.nftSta=0
        request.post("/dataj/nft/trans",this.goodsform).then(res => {
          console.log("trans-nft")
          console.log(res);
        })
      }

      this.buyDialogVisible=false;
      this.load();
      this.$message({
        type:"message",
        message:"购买成功，你可以在“我的作品”里查看该商品",
      })
      // buyButton.value=false;
      // hadBuyButton.value=true;
      // checkButton.value=true;
    },
    cancel () {
      this.buyDialogVisible=false
      this.$message({
        type:"message",
        message:"支付取消"
      })
    },
  }
}
</script>

<script setup>
import { ref } from 'vue'
import { ElMessageBox } from 'element-plus'
import Wxlogin from 'vue-wxlogin'
import Rcode from "@/components/Rcode";
import { ShoppingCart,Search,Collection} from '@element-plus/icons-vue'
const dialogData={
  ordername:"1.jpg",
  orderprice:1200,
  ordernum:1,
  tokenId:"63467129359630601073164529958648"
}
const codeDialogVisible = ref(false)
// const orderDialogVisible=ref(false)
const input = ref('')
const activeName = ref('first')
const buyButton=ref(true)
const hadBuyButton=ref(false)
const checkButton=ref(false)

// const handleClick = (tab: string, event: Event) => {
//   console.log(tab, event)
// }
const urls = [
  'https://fuss10.elemecdn.com/a/3f/3302e58f9a181d2509f3dc0fa68b0jpeg.jpeg',
]
const orderSrc = 'https://fuss10.elemecdn.com/a/3f/3302e58f9a181d2509f3dc0fa68b0jpeg.jpeg'
function clickCheck(){
  orderDialogVisible.value=true;
  // var vnt;
  // var Vnt = require("vnt");
  // vnt = new Vnt(new Vnt.providers.HttpProvider("/api"));
  // console.log(Vnt);
  // console.log(vnt);
  // if (typeof vnt !== 'undefined') {
  //   this.vnt= new Vnt(Vnt.currentProvider);
  // } else {
  //   // set the provider you want from VNT.providers
  //   this.vnt = new Vnt(new Vnt.providers.HttpProvider("/api"));
  // }
  // //版本
  // var version = vnt.version;
  // console.log(version.api); // "0.2.0"
  // alert(version.api);
}
function clickPhoto(){
  console.log('hhhh');
  console.log();
  console.log('gggg');
}
function clickBuy(){
  console.log('buy');
  codeDialogVisible.value=true;
}
</script>

<style>
.demo-tabs > .el-tabs__content {
  padding: 32px;
  color: #6b778c;
  font-size: 32px;
  font-weight: 600;
}
.marketlabel{
  font-size: 100px;
}
</style>