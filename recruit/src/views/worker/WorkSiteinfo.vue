<template>
  <div id="workSiteInfo">
    <!-- 公司信息 -->
    <h1>
      {{ company.name
      }}<el-divider
        style="margin: 0 20px 0 20px; font-weight: 800"
        direction="vertical"
      ></el-divider>
      {{ company.phone }}
      <el-button type="text" style="color: #ee8b22" @click="opensendHandle()"
        >发消息</el-button
      >
    </h1>

    <!-- 工地信息 -->
    <el-descriptions title="工地信息">
      <el-descriptions-item label="工地名">
        {{ site.name }}
      </el-descriptions-item>
      <el-descriptions-item label="负责人">
        {{ site.userName }}
      </el-descriptions-item>
      <el-descriptions-item label="手机号">
        {{ site.phone }}
      </el-descriptions-item>
      <el-descriptions-item label="开工时间">
        {{ site.startTime }}
      </el-descriptions-item>
      <el-descriptions-item label="预计工期">
        {{ site.expectTime }}
      </el-descriptions-item>
      <el-descriptions-item label="工地地址">
        {{ site.address }}
      </el-descriptions-item>
      <el-descriptions-item label="工地简述">
        <span v-if="site.remark == null">无</span>
        <span v-else>{{ site.remark }}</span>
      </el-descriptions-item>
    </el-descriptions>
    <el-alert title="应聘后，企业方将能够查看到您应聘的信息" type="info">
    </el-alert>
    <!-- 招聘信息 -->
    <div v-if="allRecuritMsg.length == 0">暂无公布的招聘信息</div>
    <!-- 招聘信息 -->
    <el-card
      v-else
      style="margin-top: 10px"
      class="box-card"
      v-for="(item, index) of allRecuritMsg"
      :key="index"
      :index="item.id"
    >
      <div slot="header" class="clearfix">
        <span>
          {{ item.workType.name }}
          <span style="margin-left: 10px">
            <el-tag v-if="item.state == 1">招聘中</el-tag>
            <el-tag v-else type="warning">不招聘</el-tag>
          </span></span
        >
        <el-button
          style="float: right; margin-left: 10px; padding: 3px 0"
          type="text"
          @click="opensendHandle(item)"
          >发消息</el-button
        >

        <el-button
          style="float: right; margin-left: 10px; padding: 3px 0"
          type="text"
          @click="apply(item.id)"
          >应聘</el-button
        >
      </div>
      <div>
        <span>{{ item.salaryType.name }} : {{ item.salary }}</span>

        <span style="margin-left: 30px">招聘人数:{{ item.number }}</span>
      </div>
    </el-card>

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
  name: "WorkSiteinfo",
  data() {
    return {
      worker: JSON.parse(window.sessionStorage.getItem("worker")),
      //公司id
      cid: this.$route.params.cid,
      // 工地id
      sid: this.$route.params.sid,
      company: {},
      site: {},
      allRecuritMsg: [],

      //发送消息规则
      Msgrules: {
        "company.name": [
          { required: true, message: "请输入名字", trigger: "blur" },
        ],
        topic: [{ required: true, message: "请输入主题", trigger: "blur" }],
        content: [{ required: true, message: "请输入内容", trigger: "blur" }],
      },

      //发送消息数据
      sendMsgDate: {
        id: 0,
        sender: 0,
        receiver: 0,
        createTime: null,
        topic: null,
        content: null,
        workerId: 0,
        siteId: 0,
        worktypeId: 0,
        type: 0,
        state: 0,
        worker: { name: null },
        company: { name: null },
      },

      //发送消息时的招聘信息id
      recuritId: 0,

      //发送消息弹窗
      dialogsendMsg: false,
    };
  },
  mounted() {
    // console.log(this.cid, this.sid);
    this.initCompany();
    this.initSite();
    this.initWorker();
    this.$emit("initMessage");
  },
  methods: {
    //获取当前工人用户并将其存入session
    initWorker() {
      if (window.sessionStorage.getItem("worker") == null) {
        this.getRequest("/worker/" + this.user.id).then((resp) => {
          if (resp) {
            window.sessionStorage.setItem("worker", JSON.stringify(resp));
          }
        });
      }
    },

    // 获取公司信息
    initCompany() {
      this.getRequest("/company/" + this.cid).then((resp) => {
        if (resp) {
          this.company = resp;
        }
      });
    },

    // 获取工地加招聘信息
    initSite() {
      this.getRequest("/site/" + this.sid).then((resp) => {
        if (resp) {
          console.log(resp);
          this.site = resp;
          this.allRecuritMsg = resp.recuritMessageList;
        }
      });
    },

    // 应聘
    apply(mid) {
      console.log("应聘的招聘信息的id", mid);
      this.postRequest("/recuritMessage/apply/" + this.worker.id + "/" + mid);
    },

    //打开发送消息弹窗
    opensendHandle(item) {
      this.dialogsendMsg = true;
      this.sendMsgDate.receiver = this.cid;
      this.sendMsgDate.sender = this.worker.id;
      this.sendMsgDate.siteId = this.sid;
      this.sendMsgDate.worker = this.worker;
      this.sendMsgDate.company = this.company;
      this.sendMsgDate.workerId = this.worker.id;
      if (null != item) {
        //此时为携带招聘信息发消息
        this.sendMsgDate.worktypeId = item.wtypeId;
        this.sendMsgDate.type = 1;
      }
      console.log("发送消息的数据", this.sendMsgDate);
    },

    //关闭发送消息弹窗
    closehandler() {
      this.dialogsendMsg = false;
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
                  worttypeId: 0,
                  siteId: 0,
                  type: 0,
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

<style >
</style>