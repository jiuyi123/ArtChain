###细节
1.button的隐藏标签是v-if,禁用是disabled  
2.v-model是对话框出现与否的标签，在setup的const中，需要对.value改变  
3.跨域中浏览器显示local:8080/api其实已经是成功了  
4.后台json数据不显示，可能是jdk版本不同导致的类无法正常调用 
5.request.get(url,config)中config需要输入config?直接输入会报错  
6.清楚按钮clearable  
7.scope传参需要在templa里面加入default="scope"  
8.二次确认需要用@confirm  
9."/dataj/user/"+id中的user+/不能忘了  
10.通过在request.js里面写拦截限制登录访问
11.前端在向后端传参时，属性名要符合驼峰命名法  
12.request前，先引用  import request from "@/utils/request";  
13.Unexpected token x in JSON at position 1 是因为发送的不是json格式  
14.js里面对整型变量取非不能用！，可以用if else  
15.http协议里面res使用后链接会断开，所以res只能写一次  
16.子组件向父组件传值  
model:{
prop:'value',
event:'input'
},  
this.$emit('input', codeList.map(item => item.code).join(''))  
<ValidCode @input="createValidCode"/>  
createValidCode(data){
console.log("data");
},  
17. 带我去·
#el-image
:preview-src-list="[scope.row.nftLoc]">放大预览  
:preview-teleported="true"用来放在预览图被分割

### 路由的用法




### 接口的用法
PutMapping 更新  
POSTMapping 新增  
GetMapping 查询  
DeleteMapping 删除  
方法名要写对
### Script setup 和 Script的区别
1.属性和方法不需要返回，直接用
```
<template>
  <div class="box" style="margin: 0 auto;text-align: center;">
    <h1>{{data}}</h1>
  </div>
</template>
<script setup>
const data = ref('语法糖真香')
</script>
```
其中，data是一个常量对象，不可以改变，但是其属性值可以改变
2. 组件不需要components注册
   不需要从components注册使用，直接import就可以用了
```
<template>
  <div class="box" style="margin: 0 auto;text-align: center;">
    <h1>我是父组件</h1>
    <son></son>
  </div>
</template>
<script setup>
import son from './son.vue'
</script>
```
3. 组件数据传递 props 和 emit 语法改变
   在传统setup中，上面演示到 props需要用 setup(props)接收，而在setup语法糖里，语法不同 props 和 emits 在语法糖中使用 defineProps  和 defineEmits 方法来使用
```
// 父组件
<template>
  <div class="box" style="margin: 0 auto;text-align: center;">
    <h1>我是父组件</h1>
    <son :data="data"></son>
  </div>
</template>
<script setup>
import son from './son.vue'
const data = '传递给子组件'
</script>

// 子组件 这回不需要props接收了
<template>
<div>
  <h2>我是子组件---{{data}}</h2>
</div>
</template>
<script setup>
const props = defineProps ({
  data: {
    type: String
  }
})
console.log(props.data);
</script>
```
