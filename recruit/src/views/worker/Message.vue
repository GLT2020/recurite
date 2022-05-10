<template>
  <div>
    <h1>信箱</h1>
    <!-- 按钮 -->
    <div style="margin: 10px 0 10px 0">
      <!-- 查看收件箱 -->
      <el-button type="primary" size="small" @click="receiveHandle"
        >收件箱</el-button
      >
      <!-- 已发送 -->
      <el-button type="primary" size="small" @click="sendHandle"
        >已发送</el-button
      >
    </div>
    <!-- 表单 -->
    <el-table v-loading="loading" :data="showDate" stripe style="width: 100%">
      <el-table-column
        v-if="this.showFlag == 0"
        prop="company.name"
        label="发件人"
        width="180"
      >
      </el-table-column>
      <el-table-column v-else prop="company.name" label="收件人" width="180">
      </el-table-column>
      <el-table-column prop="topic" label="主题" width="180"> </el-table-column>
      <el-table-column prop="createTime" label="日期" width="180">
      </el-table-column>
      <el-table-column prop="state" label="状态" width="180">
        <template scope="scope">
          <el-tag v-if="scope.row.state == 0">未查看</el-tag>
          <el-tag v-else type="info">已查看</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="type" label="类别" width="180">
        <template scope="scope">
          <el-tag v-if="scope.row.type == 0 || scope.row.type == 1" type="info"
            >普通</el-tag
          >
          <!-- <el-tag v-else-if="scope.row.type == 1">邀请</el-tag> -->
          <el-tag v-else-if="scope.row.type == 2" type="success">回复</el-tag>
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
            <span class="dialogTop" v-show="this.showFlag == 0">发件人:</span>
            <span class="dialogTop" v-show="this.showFlag == 1">收件人:</span>
            <span>{{ msgDetailsDate.company.name }}</span>
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
          <div
            class="dialogSite"
            v-if="null != msgDetailsDate.siteId && 0 != msgDetailsDate.siteId"
            @click="SiteDetails(msgDetailsDate.receiver, msgDetailsDate.siteId)"
          >
            <div>
              <span>工地名:</span><span>{{ msgDetailsDate.site.name }}</span>
            </div>
            <div>
              <span>负责人:</span
              ><span>{{ msgDetailsDate.site.userName }}</span>
            </div>
            <div
              v-if="
                null != msgDetailsDate.workType && 0 != msgDetailsDate.workType
              "
            >
              <span>工种:</span>
              <span>{{ msgDetailsDate.workType.name }}</span>
            </div>
          </div>
        </div>
        <!-- 回复按钮 -->
        <div
          v-if="this.showFlag == 0"
          style="display: flex; justify-content: flex-end"
        >
          <el-button class="repay" type="primary" @click="openSendHandle"
            >回复</el-button
          >
        </div>
      </div>
    </el-dialog>

    <!-- 发送消息 -->
    <el-dialog title="发送消息" :visible.sync="dialogsendMsg" width="50%">
      <el-form
        label-position="left"
        ref="sendMsgDate"
        :model="sendMsgDate"
        :rules="Msgrules"
        width="100%"
      >
        <!-- 第一行 -->
        <el-row>
          <!-- 收信人 -->
          <el-col>
            <el-form-item label="收信人:" prop="company.name">
              <el-input
                disabled
                v-model="sendMsgDate.company.name"
                size="mini"
                style="width: 200px"
                placeholder="请输入收信人"
                prefix-icon="el-icon-edit"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <!-- 第二行 -->
        <el-row>
          <el-form-item label="主题:" prop="topic">
            <el-input
              v-model="sendMsgDate.topic"
              size="mini"
              style="width: 200px"
              placeholder="请输入主题"
              prefix-icon="el-icon-edit"
            ></el-input>
          </el-form-item>
        </el-row>

        <!-- 第三行 -->
        <el-row>
          <!-- 发送信息内容-->
          <el-col>
            <el-form-item label="消息内容:" prop="content">
              <el-input
                type="textarea"
                v-model="sendMsgDate.content"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <!--第五行按钮 -->
        <el-row>
          <el-col>
            <el-form-item style="display: flex; justify-content: flex-end">
              <el-button style="margin-right: 10px" @click="closehandler()"
                >取 消</el-button
              >
              <el-button type="primary" @click="sendMsgHandle('sendMsgDate')"
                >发 送</el-button
              >
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "Message",
  data() {
    return {
      user: null,

      //接收的信息
      receiveDate: [],
      //发送的信息
      sendDate: [],
      //展示的信息
      showDate: null,
      //当前是查看收件还是发件(0为收件，1为发件)
      showFlag: 0,

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
        company: { name: null },
      },

      //发送消息规则
      Msgrules: {
        "company.name": [
          { required: true, message: "请输入名字", trigger: "blur" },
        ],
        topic: [{ required: true, message: "请输入主题", trigger: "blur" }],
        content: [{ required: true, message: "请输入内容", trigger: "blur" }],
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
      this.showFlag = 0;
      this.loading = true;
      this.getRequest(
        "/companymailbox/worker/" +
          this.currentPage +
          "/" +
          this.size +
          "/" +
          this.user.id
      ).then((resp) => {
        if (resp) {
          // console.log("获取接收到的消息", resp.data);
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

    //查看已发送
    sendHandle(type) {
      if (type == null || type != "page") {
        this.currentPage = 1;
      }
      this.showFlag = 1;
      this.loading = true;
      // console.log("查看已发送的页数", this.currentPage);
      // console.log("查看已发送的页大小", this.size);
      this.getRequest(
        "/workermailbox/getSendedMsg/" +
          this.currentPage +
          "/" +
          this.size +
          "/" +
          this.user.id
      ).then((resp) => {
        if (resp) {
          // console.log("已发送的消息数据", resp);
          this.sendDate = resp.data;
          this.showDate = resp.data;
          this.total = resp.total;
          this.pageFlag = false;
          this.loading = false;
        }
      });
    },

    //查看具体
    openDetailsDialog(item) {
      // console.log("消息具体内容", item);
      this.dialogMsgDetails = true;
      this.msgDetailsDate = item;
      if (this.showFlag == 0 && item.state == 0) {
        //需要将邮件设置为已读
        this.putRequest("/companymailbox/worker/checkMsg", item);
        this.receiveHandle("page");
      }
    },

    //跳转查看工地具体信息
    SiteDetails(cid, sid) {
      this.$router.push("WorkSiteinfo/" + cid + "/" + sid);
      // this.$router.push({ name: "SiteinfoDetail", params: { id: sid } });
    },

    //移除
    deleteHandle(item) {
      console.log("移除时获取的消息", item);
      if (this.showFlag == 0) {
        //收信箱删除
        this.deleteRequest("/companymailbox/deleteReceivedMsg", item).then(
          (resp) => {
            if (resp) {
              this.receiveHandle("page");
            }
          }
        );
      } else {
        //发送箱删除

        this.deleteRequest("/workermailbox/deleteSendedMsg", item).then(
          (resp) => {
            if (resp) {
              this.sendHandle("page");
            }
          }
        );
      }
    },

    //关闭回信弹窗
    closehandler() {
      this.dialogsendMsg = false;
      // this.sendMsgDate = {
      //   worker: { name: null },
      // };
    },

    //打开回信窗口
    openSendHandle() {
      this.sendMsgDate.worker = this.msgDetailsDate.worker;
      this.sendMsgDate.company = this.msgDetailsDate.company;
      this.sendMsgDate.receiver = this.msgDetailsDate.sender;
      this.sendMsgDate.sender = this.msgDetailsDate.receiver;
      this.sendMsgDate.siteId = this.msgDetailsDate.siteId;
      this.sendMsgDate.workType = this.msgDetailsDate.workType;
      this.sendMsgDate.type = 2;
      console.log("回信的内容", this.sendMsgDate);
      this.dialogsendMsg = true;
    },

    //发送消息
    sendMsgHandle(form) {
      this.$refs[form].validate((valid) => {
        if (valid) {
          this.postRequest("/workermailbox/send", this.sendMsgDate).then(
            (resp) => {
              if (resp) {
                this.dialogsendMsg = false;
                this.sendMsgDate = {
                  id: 0,
                  sender: 0,
                  receiver: 0,
                  createTime: null,
                  topic: null,
                  content: null,
                  workerId: 0,
                  siteId: 0,
                  type: 1,
                  state: 0,
                  worker: { name: null },
                  company: { name: null },
                };
              }
            }
          );
        } else {
          return false;
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
