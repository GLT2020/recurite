<template>
  <div>
    <h1>反馈</h1>
    <!-- 按钮 -->
    <!-- <div style="margin: 10px 0 10px 0">
      
      <el-button type="primary" size="small" @click="receiveHandle"
        >收件箱</el-button
      >
      
    </div> -->
    <!-- 表单 -->
    <el-table v-loading="loading" :data="showDate" stripe style="width: 100%">
      <el-table-column prop="sendName" label="反馈人" width="180">
      </el-table-column>

      <el-table-column prop="topic" label="主题" width="180"> </el-table-column>
      <el-table-column sortable prop="createTime" label="日期" width="180">
      </el-table-column>
      <el-table-column prop="state" label="状态" width="180">
        <template scope="scope">
          <el-tag v-if="scope.row.state == 0">未查看</el-tag>
          <el-tag v-else type="info">已查看</el-tag>
        </template>
      </el-table-column>

      <el-table-column fixed="right" label="操作" width="120">
        <template slot-scope="scope">
          <el-button
            @click="openDetailsDialog(scope.row)"
            type="text"
            size="small"
          >
            查看具体
          </el-button>
          <el-button @click="deleteHandle(scope.row)" type="text" size="small">
            移除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页控件 -->
    <div style="display: flex; justify-content: center">
      <el-pagination
        background
        layout="sizes, prev, pager, next, ->, total"
        :total="total"
        :hide-on-single-page="pageFlag"
        :page-sizes="[10, 20]"
        :page-size="size"
        @current-change="currentChange"
        @size-change="sizeChange"
      >
      </el-pagination>
    </div>

    <!-- 具体内容弹窗 -->
    <el-dialog title="具体内容" :visible.sync="dialogMsgDetails" width="50%">
      <div style="width: 90%">
        <!-- 收件人 -->
        <div>
          <label>
            <span class="dialogTop">发件人:</span>
            <span>{{ msgDetailsDate.sendName }}</span>
          </label>
        </div>
        <!-- 时间 -->
        <div>
          <label>
            <span class="dialogTop">时 间:</span>
            <span>{{ msgDetailsDate.createTime }}</span>
          </label>
        </div>
        <!-- 主题 -->
        <div>
          <label>
            <span class="dialogTop">主 题:</span>
            <span>{{ msgDetailsDate.topic }}</span>
          </label>
        </div>
        <!-- 内容 -->
        <div>
          <label class="dialogTop"><span>内 容:</span></label>
        </div>
        <!-- 具体内容 -->
        <div class="Msgcontent">
          <div>
            {{ msgDetailsDate.content }}
          </div>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "AdminMessage",
  data() {
    return {
      user: null,

      //接收的信息
      receiveDate: [],
      //发送的信息
      sendDate: [],
      //展示的信息
      showDate: null,

      // 接收窗口加载
      loading: false,

      //信息具体内容
      msgDetailsDate: {
        worker: { name: null },
        site: { name: null, userName: null },
        workType: { name: null },
        company: { name: null },
      },

      //发送消息
      sendMsgDate: {
        worker: { name: null },
      },

      //回信弹窗
      dialogsendMsg: false,

      //分页
      total: 0,
      loading: false,
      currentPage: 1,
      size: 10,
      pageFlag: true,

      //具体信息弹窗
      dialogMsgDetails: false,
    };
  },
  mounted() {
    this.user = JSON.parse(window.sessionStorage.getItem("user"));

    this.receiveHandle();
  },

  methods: {
    //查看收件箱
    receiveHandle(type) {
      if (type == null || type != "page") {
        this.currentPage = 1;
      }

      this.loading = true;
      this.getRequest(
        "/adminmailbox/" + this.currentPage + "/" + this.size
      ).then((resp) => {
        if (resp) {
          console.log("获取接收到的消息", resp.data);
          this.showDate = resp.data;
          this.total = resp.total;
          this.pageFlag = false;
          this.loading = false;
        }
      });
    },

    //当前页改变
    currentChange(currentPage) {
      // console.log("currentpage:" + currentPage);
      this.currentPage = currentPage;
      if (this.showFlag == 0) {
        // 修改接收的展示数据
        this.receiveHandle("page");
      } else {
        this.sendHandle("page");
      }
    },

    //每页条数改变时
    sizeChange(pageSize) {
      this.size = pageSize;
      // 修改接收的展示数据
      if (this.showFlag == 0) {
        this.receiveHandle("page");
      } else {
        this.sendHandle("page");
      }
    },

    //查看具体
    openDetailsDialog(item) {
      console.log("消息具体内容", item);
      this.dialogMsgDetails = true;
      this.msgDetailsDate = item;
      if (item.state == 0) {
        //需要将邮件设置为已读
        this.putRequest("/adminmailbox/checkMsg", item);
        this.receiveHandle("page");
      }
    },

    //移除
    deleteHandle(item) {
      //收信箱删除
      this.deleteRequest("/adminmailbox/" + item.id).then((resp) => {
        if (resp) {
          this.receiveHandle("page");
        }
      });
    },
  },
};
</script>


<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}

/* 展示表单的文字 */
.dialogTop {
  width: 50px;

  display: inline-block;
  text-align: justify;
  text-align-last: justify;
}

/* 展示表单具体内容 */
.Msgcontent {
  display: flex;
  flex-direction: column;
  width: 100%;
  /* height: 200px; */
  border: 1px solid #bcbabaaa;
  padding: 20px;
  margin-top: 10px;
}

/* 展示表单具体内容里的工地 */
.Msgcontent .dialogSite {
  width: 220px;
  text-align: center;
  margin-top: 20px;
  padding: 20px;
  border: 1px solid #bcbabaaa;
  border-radius: 20px;
  background-color: #dcd9d9aa;
  cursor: pointer;
  align-self: center;
}
/* 具体内容里的回复按钮 */
.repay {
  margin-top: 20px;
}
</style>
