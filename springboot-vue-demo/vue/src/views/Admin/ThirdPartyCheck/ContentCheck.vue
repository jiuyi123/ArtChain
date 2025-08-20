<template>
  <div style="display: flex;">
    <div style="padding: 5px;background-color:#b3ecff">
      <el-card>
        <div style="text-align: center;font-size: 50px;border-width: thick;font-family: 华文行楷;margin: 10px">
          <p>艺   术   品   审   核</p>
        </div>
      </el-card>
      <!--表格+分页-->
      <el-card style="margin-top: 5px">
        <!--    表格区域-->
        <el-scrollbar height="510px" style="border: #3E3E3E">
          <el-table :data="tableData"  stripe border style="width:100%;" >
            <el-table-column   align="center" label="待审核艺术品" width="300%">
              <template #default="scope">
                <el-image
                    style="width: 250px;height: 250px"
                    :src="scope.row.nftLoc"
                    :preview-teleported="true"
                    :preview-src-list="[scope.row.nftLoc]">
                </el-image>
              </template>
            </el-table-column>

            <el-table-column
                align="center"
                prop="nftName"
                label="艺术品名称"
                width="300%"/>

            <el-table-column
                align="center"
                prop="nftDescri"
                label="艺术品描述"
                width="300%"/>

            <el-table-column
                align="center"
                prop="nftPrice"
                label="价值"
                width="80%"/>

            <el-table-column
                align="center"
                prop="nftWriterId"
                label="创作者ID"
                width="100%"/>

<!--            <el-table-column-->
<!--                align="center"-->
<!--                prop="nftFingerPrint"-->
<!--                label="图片指纹"-->
<!--                width="300%"/>-->
            <!--操作区-->
            <el-table-column   align="center" fixed="right" label="Operations" width="200%">
              <template #default="scope">
                <el-button type="primary" size="mini" @click="accept(scope.row)">通过</el-button>
                <el-button type="danger" size="mini" @click="refuse(scope.row)">拒绝</el-button>
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
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";
import Aside from "@/components/Aside";
export default {
  name: 'ContentCheck',
  data(){
    return {
      form: {},//nft
      tableData: [],

      currentPage:1,
      pageSize:10,
      total:0,
    }
  },
  created() {
    this.load()
  },
  methods:{
    //加载页面
    load(){//从数据库加载数据渲染到前端页面
      request.get("/dataj/nft/check", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search
        }
      }).then(res=>{
        this.tableData=res.data.records
        this.total=res.data.total
      })
    },
    refuse(row){
      row.nftCheck=2;
      request.put("/dataj/nft/update",row).then(res=>{
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
      setTimeout(() => {
      this.load()
      }, 1500);
    },
    //accept
    accept(row){
      row.nftCheck=1;
      request.put("/dataj/nft/update",row).then(res=>{
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
      setTimeout(() => {
        this.load()
      }, 1500);
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
  }
}
</script>