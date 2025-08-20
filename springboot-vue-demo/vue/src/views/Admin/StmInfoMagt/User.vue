<template>
  <div style="display: flex;">
    <div style="padding: 5px">
      <div style="text-align: center;font-size: 50px;border-width: thick;font-family: 华文行楷;margin: 10px">
        <p>用   户   信   息   管   理</p>
      </div>

      <el-card>
        <!--    搜索区域-->
        <div style="margin: 0px">
          <el-input v-model="search" placeholder="请输入关键字" style="width: 80%" clearable/>
          <el-button type="primary" style="margin: 2px;margin-left: 20px" @click="load">查询</el-button>
          <el-button type="primary" @click="add">新增</el-button>
          <!--    功能区域-->
          <!--          <div style="margin: 10px 0">-->
          <!--            <el-button type="primary" @click="add">新增</el-button>-->
          <!--            <el-button type="primary" @click="daorv">导入</el-button>-->
          <!--            <el-button type="primary " @click="getvnt">导出</el-button>-->
          <!--          </div>-->
        </div>
      </el-card>

      <div>
<!--搜索+功能-->
<!--      <el-card>-->
<!--        &lt;!&ndash;    搜索区域&ndash;&gt;-->
<!--        <div style="margin: 10px">-->
<!--          <el-input v-model="search" placeholder="请输入关键字" style="width: 30%" clearable/>-->
<!--          <el-button type="primary" style="margin: 5px" @click="load">查询</el-button>-->
<!--        </div>-->
<!--        &lt;!&ndash;    功能区域&ndash;&gt;-->
<!--        <div style="margin: 10px 0">-->
<!--          <el-button type="primary" @click="add">新增</el-button>-->
<!--          <el-button type="primary" @click="daorv">导入</el-button>-->
<!--          <el-button type="primary " @click="getvnt">导出</el-button>-->
<!--        </div>-->
<!--      </el-card>-->
      </div>
<!--表格+分页-->
      <el-card style="margin-top: 5px">
        <!--    表格区域-->
        <el-scrollbar height="480px" style="border: #3E3E3E">
          <el-table :data="tableData" stripe border style="width: 100%">
          <el-table-column
              prop="userId"
              label="ID"
              width="150" sortable/>

          <el-table-column  prop="userPict"  label="用户头像" width="120">
            <el-avatar  shape="square" :size="30" :src="url"/>
          </el-table-column>

          <el-table-column
              prop="userName"
              label="用户名"
              width="200"/>

          <el-table-column
              prop="userCard"
              label="银行卡号"
              width="200"/>

          <el-table-column
              prop="userMoney"
              label="钱包余额"
              width="200"/>

          <el-table-column
              prop="userAddr"
              label="用户地址"
              width="200"/>

          <!--操作区-->
          <el-table-column fixed="right" label="Operations" width="200">
            <template #default="scope">

              <el-button type="default" size="mini" @click="handleEdit(scope.row)">编辑</el-button>
              <el-popconfirm title="Are you sure to delete this?"  @confirm="handleDelete(scope.row.userId)">
                <template #reference>
                  <el-button type="danger" size="mini">删除</el-button>
                </template>
              </el-popconfirm>

            </template>
          </el-table-column>
        </el-table>
        </el-scrollbar>
        <!--    分页-->
        <el-pagination
            style="text-align: center;margin-left: 30%;margin-top: 5px"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            v-model:currentPage="currentPage"
            :page-size="pageSize"
            :total="total"
            :page-sizes="[5, 10, 20]"
            :small="small"
            :disabled="disabled"
            :background="background"
            layout="total, sizes, prev, pager, next, jumper"
        >
        </el-pagination>
      </el-card>
<!--      对话框-->
    <div style="margin: 10px 0">
      <el-dialog v-model="dialogVisible" title="Tips" width="30%">
        <el-form :model="form" label-width="120px">
          <el-form-item label="用户名">
            <el-input v-model="form.userName" style="width: 80%"></el-input>
          </el-form-item>

          <el-form-item label="密码">
            <el-input v-model="form.userPassword" style="width: 80%"></el-input>
          </el-form-item>

          <el-form-item label="银行卡号">
            <el-input v-model="form.userCard" style="width: 80%"></el-input>
          </el-form-item>

          <el-form-item label="用户地址">
            <el-input v-model="form.userAddr" style="width: 80%"></el-input>
          </el-form-item>
        </el-form>

        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible = false">取消</el-button>
            <el-button type="primary" @click="save">保存</el-button>
          </span>
        </template>

      </el-dialog>

    </div>
<!--    <el-button @click="go1">点我跳转</el-button>-->
   </div>
  </div>
</template>

<script>
import request from "@/utils/request";
import Aside from "@/components/Aside";
export default {
  name: 'Home',
  data(){
    return {
      url:"https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg",
      //vnt,
      form: {},
      search:'',
      currentPage:1,
      pageSize:10,
      total:0,
      dialogVisible:false,
      tableData: [],
    }
  },
  created() {
    this.load()
  },
  methods:{
    //加载页面
    load(){//从数据库加载数据渲染到前端页面
        request.get("/dataj/user", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search
        }
      }).then(res=>{
        console.log(res)
        this.tableData=res.data.records
        this.total=res.data.total
      })
    },
    //新增/更新数据
    add(){//新增数据弹窗
      this.dialogVisible=true;
      this.form={};//清空表单
    },
    save(){//将弹窗数据post到数据库
      console.log(this.form.userId);
      if(this.form.userId){
        //更新
        request.put("/dataj/user/update",this.form).then(res=>{
          console.log(res);
          if(res.code==='0')
          {
            this.$message({
              type:"message",
              message:"更新成功"
            })
          }
          else{
            this.$message({
              type:"error",
              message:res.msg
            })
          }
        })
      }
      else{
        request.post("/dataj/user/insert",this.form).then(
            res=>{
              //执行完毕后结果回到res这
              console.log(res);
              //弹窗提示正确
              if(res.code==='0')
              {
                this.$message({
                  type:"message",
                  message:"新增成功"
                })
              }
              else{
                this.$message({
                  type:"error",
                  message:res.msg
                })
              }
            })
      }
      this.dialogVisible=false;
      this.load();
    },
    //删除数据
    handleDelete(userId){
      console.log(userId)
      request.delete("/dataj/user/"+userId).then(res=>{
        if(res.code==='0')
        {
          this.$message({
            type:"message",
            message:"更新成功"
          })
        }
        else{
          this.$message({
            type:"error",
            message:res.msg
          })
        }
      })

      this.load()
    },
    //编辑更新数据
    handleEdit(row){
      //cancel不会改变
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogVisible=true
    },
    //页面容量调整
    handleSizeChange(pageSize){//改变当前每页容纳个数
      this.pageSize=pageSize
      this.load()
    },
    handleCurrentChange(pageNum){ //改变当前页码触发
      this.currentPage=pageNum
      this.load()
    },
    //访问区块链
    getvnt(){
      var vnt;
      var Vnt = require("vnt");
      vnt = new Vnt(new Vnt.providers.HttpProvider("/api"));
      // console.log(Vnt);
      // console.log(vnt);
      if (typeof vnt !== 'undefined') {
        this.vnt= new Vnt(Vnt.currentProvider);
      } else {
        // set the provider you want from VNT.providers
        this.vnt = new Vnt(new Vnt.providers.HttpProvider("/api"));
      }
      //版本
      var version = vnt.version;
      // console.log(version.api); // "0.2.0"
      // console.log(version.node);
      // console.log(version.network);
      // console.log(version.vntchain);
      // console.log(version.whisper);
      // console.log(vnt.isConnected());
      // console.log(vnt.sha3("Some string to be hashed"));
      // console.log(vnt.net.listening);

      console.log(vnt.net.peerCount);
      console.log(vnt.core.defaultAccount);
      console.log(vnt.core.getTransactionCount("0x407d73d8a49eeb85d32cf465507dd71d507100c1"));
      console.log();
      console.log();
      // alert('区块链版本号：'+version.api+'/n'+'node:'+version.node)
      //node
    },
    daorv(){
      console.log("区块链1")
      request.get("/api").then(res=>{
        console.log("区块链2")
        console.log(res);})
    },
  }
}
</script>

