<!-- 微信二维码 -->
<template>
  jjjj
  <div class=''>
    <el-dialog
        title="微信扫码支付"
        v-model="dialogVisible"
        width="20%"
        center
        @close="close">

      <img src="../assets/img/code.jpg" style="width:260px;height:260px;display:flex">
      <p style="text-align:center;margin:5px"><el-button type="primary" size="mini" @click="refresh">刷新二维码</el-button></p>
      <p style="text-align:center"><b>订单名称:{{dialogData.ordername}}</b></p>
      <p style="text-align:center"><b>订单价格:{{dialogData.orderprice}}元</b></p>
      <p style="text-align:center"><b>数量:{{dialogData.ordernum}}</b></p>
      <div slot='footer' class="dialog-footer" style="text-align:center">
        <el-button type="primary" @click="confirm">已支付</el-button>
        <el-button type="warning" @click="cancel">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script setup>
import QRCode from 'qrcodejs2'
import {ref} from "vue";
  inject: ['reload']
    // 这里存放数据
      const content=ref('')
      const dialogVisible=ref(true)
      const dialogData={
        ordername:"1.jpg",
        orderprice:1200,
        ordernum:1,
      }

  // 方法集合
  function qrcodedraw () { // 生成二维码
      this.qrcode = new QRCode(this.$refs.qrcode, {
        width: 100, // 二维码宽度
        height: 100, // 二维码高度
        text: this.dialogData.codeUrl,
        correctLevel: QRCode.CorrectLevel.H
      })
    }
  function refresh () {
      this.$http.rule.xxxxxxxx({orderId: this.dialogData.item.orderId, paymentMethod: 1}).then(res => {
        if (res.data.code === '200' && res.data.value === 'success') {
          this.$message.success('二维码刷新成功')
          this.content = res.data.data.codeUrl
          document.getElementById('qrcode').innerHTML = ''  //点击一次刷新将之前的二维码内容清空
          this.$nextTick(() => {
            this.qrcodeScan()
          })
        }
      }).catch(() => {})
    }
  function close () {
      this.$message.warning('支付页面关闭!')
      this.dialogData.dialogVisible = false
      this.reload()
    }
  function confirm () {
      this.$http.rule.getMyOrdersList({current: 1, size: 5, orderId: this.dialogData.orderId}).then(res => {
        if (res.data.data.records[0].orderState === '2') {
          this.$message.success('支付成功!')
          this.dialogData.dialogVisible = false
          // this.$router.push({path: '/home/busaccount', query: {showview: 1}})
          this.reload()
        } else if (res.data.data.records[0].orderState === '4') {
          this.$message.error('支付失败!')
          this.dialogData.dialogVisible = false
          // this.$router.push({path: '/home/busaccount', query: {showview: 1}})
          this.reload()
        } else if (res.data.data.records[0].orderState === '1') {
          this.$message.warning('微信未支付!')
          this.dialogData.dialogVisible = false
          // this.$router.push({path: '/home/busaccount', query: {showview: 1}})
          this.reload()
        }
      })
    }
  function cancel () {
      console.log('close')
      console.log( dialogVisible.value)
      dialogVisible.value=false
      console.log( dialogVisible.value)
      this.$message.warning('支付取消!')
      // this.reload()
    }
  // 生命周期 - 挂载完成（可以访问DOM元素）
  function mounted () {
    this.$nextTick(() => {
      console.log('mounted')
      this.qrcodedraw()
    })
  }
</script>

<style scoped>

</style>