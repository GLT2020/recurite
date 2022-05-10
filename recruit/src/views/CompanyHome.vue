<template>
  <el-container>
    <!-- 头部 -->
    <el-header class="homeHeader">
      <div class="title">建筑工地工人招聘系统</div>
      <!-- 右侧聊天按钮和用户信息 -->
      <div>
        <el-badge :is-dot="notReadFlag" class="item" style="margin-right: 35px">
          <el-button
            icon="el-icon-s-comment"
            type="text"
            class="share-button"
            style="color: white; font-size: 25px"
            @click="goMessage"
          ></el-button>
        </el-badge>
        <el-dropdown @command="commandHandler">
          <span class="el-dropdown-link userinfo">
            {{ user.username }}
          </span>
          <!-- <img class="userFace" :src="user.userFace" alt=" " /> -->
          <el-dropdown-menu slot="dropdown">
            <!-- <el-dropdown-item command="userinfo">个人中心</el-dropdown-item> -->
            <el-dropdown-item command="setting">提交反馈</el-dropdown-item>
            <el-dropdown-item command="logout">注销登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </el-header>
    <!-- 主体 -->
    <el-container>
      <!-- 侧边栏 -->
      <el-aside width="200px">
        <el-menu unique-opened>
          <el-menu-item
            v-for="(item, index) in routerItem"
            :key="index"
            :index="item.path"
            @click="menuClick(item.path)"
          >
            <template slot="title">
              <!-- <i
                :class="item.iconCls"
                style="
                  color: rgb(107, 107, 107);
                  margin-right: 5px;
                  margin-bottom: 3px;
                "
              ></i> -->
              {{ item.name }}</template
            >
          </el-menu-item>
        </el-menu>
      </el-aside>
      <!-- 内容 -->
      <el-main>
        <!-- 面包屑导航 -->

        <div
          class="homeWelcome"
          v-if="this.$router.currentRoute.path == '/CompanyHome'"
        ></div>

        <!-- 页面 -->
        <router-view style="margin-top: 20px" @initMessage="initMessage()" />
      </el-main>
    </el-container>

    <!-- 反馈内容弹窗 -->
    <el-dialog title="提交反馈" :visible.sync="dialogsendMsg" width="50%">
      <el-form
        label-position="left"
        ref="msgDate"
        :model="msgDate"
        :rules="Msgrules"
        width="100%"
      >
        <!-- 第一行 -->
        <el-row>
          <!-- 发信人 -->
          <el-col>
            <el-form-item label="发件人:" prop="sendName">
              <el-input
                disabled
                v-model="msgDate.sendName"
                size="mini"
                style="width: 200px"
                placeholder="请输入发件人"
                prefix-icon="el-icon-edit"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <!-- 第二行 -->
        <el-row>
          <el-form-item label="主题:" prop="topic">
            <el-input
              v-model="msgDate.topic"
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
              <el-input type="textarea" v-model="msgDate.content"></el-input>
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
              <el-button type="primary" @click="sendMsgHandle('msgDate')"
                >发 送</el-button
              >
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </el-dialog>
  </el-container>
</template>

<script>
import { resetRouter } from "../router";
export default {
  name: "CompanyHome",
  data() {
    return {
      user: JSON.parse(window.sessionStorage.getItem("user")),
      routerItem: [
        {
          name: "公司信息",
          iconCls: "",
          path: "Companyinfo",
        },
      ],
      notReadcount: null,
      notReadFlag: false,
      //定时器
      timer: null,

      //反馈弹窗
      dialogsendMsg: false,
      //反馈数据
      msgDate: {
        id: 0,
        sender: null,
        sendType: null,
        sendName: null,
        topic: null,
        content: null,
      },

      //发送消息规则
      Msgrules: {
        sendName: [
          { required: true, message: "请输入发件人", trigger: "blur" },
        ],
        topic: [{ required: true, message: "请输入主题", trigger: "blur" }],
        content: [{ required: true, message: "请输入内容", trigger: "blur" }],
      },
    };
  },

  mounted() {
    this.initCompany();
    console.log("user:", this.user);
    if (this.user.roles[0].name == "ROLE_company") {
      this.routerItem = [
        {
          name: "首页",
          iconCls: "",
          path: "HomePage",
        },
        {
          name: "公司信息",
          iconCls: "",
          path: "Companyinfo",
        },
        {
          name: "工地列表",
          iconCls: "",
          path: "AllSiteinfo",
        },
        {
          name: "寻找工人",
          iconCls: "",
          path: "FindWorker",
        },
      ];
    } else {
      //未通过验证的公司
      this.routerItem = [
        {
          name: "首页",
          iconCls: "",
          path: "HomePage",
        },
        {
          name: "公司信息",
          iconCls: "",
          path: "Companyinfo",
        },
        {
          name: "工地列表",
          iconCls: "",
          path: "AllSiteinfo",
        },
      ];
    }

    this.initMessage();
    // var _this = this;
    // this.timer = setInterval(() => {
    //   _this.initMessage();
    // }, 30000);
    //获取未读消息列表
    // setInterval(this.initMessage(), 1000);
  },

  methods: {
    //侧边栏的点击事件（搭配router模式才有用）
    menuClick(index) {
      console.log("当前路由路径", index);
      this.$router.push("/CompanyHome/" + index);
    },

    // 退出登录或反馈
    commandHandler(command) {
      if (command == "logout") {
        this.$confirm("即将退出登录, 是否继续?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
          .then(() => {
            this.getRequest("/logout");
            // 清除用户信息
            window.sessionStorage.removeItem("tokenStr");
            window.sessionStorage.removeItem("user");
            window.sessionStorage.removeItem("company");
            // 清空菜单
            resetRouter();
            // this.$store.commit("initRouter", []);
            // 跳转到登录页
            this.$router.replace("/");
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "已取消注销",
            });
          });
      } else if (command == "setting") {
        //提交反馈
        this.msgDate.sender = this.user.id;
        this.msgDate.sendName = this.user.username;
        this.msgDate.sendType = 1;
        this.dialogsendMsg = true;
      }
    },

    //获取当前登录用户并将其存入session
    initCompany() {
      if (window.sessionStorage.getItem("company") == null) {
        this.getRequest("/company/" + this.user.id).then((resp) => {
          if (resp) {
            // this.company = resp;
            //放入session中，方便以后用
            // console.log("home中获取的company用户", resp);
            window.sessionStorage.setItem("company", JSON.stringify(resp));
          }
        });
      }
    },

    //获取未读消息
    initMessage() {
      console.log("获取未读消息被执行");
      this.getRequest("/workermailbox/notReadCount/" + this.user.id).then(
        (resp) => {
          if (resp) {
            this.notReadcount = resp;
            this.notReadFlag = true;
          }
        }
      );
    },

    // 跳转聊天界面
    goMessage() {
      this.notReadFlag = false;
      this.notReadcount == null;
      this.$router.push("/CompanyHome/Message");
    },

    //提交反馈关闭
    closehandler() {
      this.dialogsendMsg = false;
      this.msgDate = {
        id: 0,
        sender: null,
        sendType: null,
        sendName: null,
        topic: null,
        content: null,
      };
    },

    // 提交反馈
    sendMsgHandle(form) {
      this.$refs[form].validate((valid) => {
        if (valid) {
          this.postRequest("/adminmailbox/send", this.msgDate).then((resp) => {
            if (resp) {
              this.dialogsendMsg = false;
              this.msgDate = {
                id: 0,
                sender: null,
                sendType: null,
                sendName: null,
                topic: null,
                content: null,
              };
            }
          });
        } else {
          return false;
        }
      });
    },
  },
  // beforeDestroy() {
  //   clearInterval(this.timer);
  // },
};
</script>

<style scoped>
.homeHeader {
  background-color: #409eff;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 15px;
  box-sizing: border-box;
}
.homeHeader .title {
  font-size: 30px;
  color: whitesmoke;
  text-shadow: 1px 1px 1px whitesmoke;
}
.userinfo {
  font-size: 20px;
  color: whitesmoke;
  /* vertical-align: top; */
  text-align: center;
  cursor: pointer;
}

.homeWelcome {
  text-align: center;
  font-size: 30px;
  font-weight: 600;
  font-family: 华文楷体;
  color: #13576a;
  text-shadow: 2px 4px 0px antiquewhite;
  padding-top: 50px;
}

/* 头像 */
.userFace {
  width: 40px;
  height: 40px;
  border-radius: 25%;
  margin-bottom: 5px;
  vertical-align: middle;
}
.item {
  margin-top: 10px;
  margin-right: 40px;
}
</style>
