<template>
  <el-container>
    <!-- 头部 -->
    <el-header class="homeHeader">
      <div class="title">建筑工地工人招聘系统</div>
      <!-- 右侧聊天按钮和用户信息 -->
      <div>
        <!-- <el-button
          icon="el-icon-s-comment"
          type="text"
          style="color: white; margin-right: 35px; font-size: 25px"
        ></el-button> -->
        <el-dropdown @command="commandHandler">
          <span class="el-dropdown-link userinfo">
            {{ user.username }}
          </span>
          <!-- <img class="userFace" :src="user.userFace" alt=" " /> -->
          <el-dropdown-menu slot="dropdown">
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
        <!-- 页面 -->
        <router-view style="margin-top: 20px" />
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import { resetRouter } from "../router";
export default {
  name: "Home",
  data() {
    return {
      user: JSON.parse(window.sessionStorage.getItem("user")),
      routerItem: [],
    };
  },
  mounted() {
    this.initAdmin();
    // 控制菜单
    if (this.user.roles[0].name == "ROLE_admin") {
      this.routerItem = [
        {
          name: "验证公司",
          iconCls: "",
          path: "VerifyCompany",
        },
        {
          name: "公司信息",
          iconCls: "",
          path: "FindAllCompany",
        },
        {
          name: "工地信息",
          iconCls: "",
          path: "FindAllSite",
        },
        {
          name: "工人信息",
          iconCls: "",
          path: "FindAllWorker",
        },
        {
          name: "工种设置",
          iconCls: "",
          path: "WorkTypeList",
        },
        {
          name: "添加系统用户",
          iconCls: "",
          path: "AddAdmin",
        },
        {
          name: "管理公告",
          iconCls: "",
          path: "Announcement",
        },
        {
          name: "管理反馈",
          iconCls: "",
          path: "AdminMessage",
        },
      ];
    } else {
      //客服人员
      this.routerItem = [
        {
          name: "验证公司",
          iconCls: "",
          path: "VerifyCompany",
        },
        {
          name: "管理公告",
          iconCls: "",
          path: "Announcement",
        },
      ];
    }
  },
  methods: {
    //获取系统用户信息
    initAdmin() {
      console.log("获取系统用户信息");
      if (window.sessionStorage.getItem("admin") == null) {
        this.getRequest("/admin/" + this.user.id).then((resp) => {
          if (resp) {
            //放入session中，方便以后用
            console.log("home中获取的admin用户", resp);
            window.sessionStorage.setItem("admin", JSON.stringify(resp));
          }
        });
      }
    },

    //侧边栏的点击事件
    menuClick(index) {
      console.log("当前路由路径", index);
      this.$router.push("/Home/" + index);
    },

    //退出登录
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
            window.sessionStorage.removeItem("admin");
            // 清空菜单
            resetRouter();
            // this.$store.commit("initRouter", []);
            // 跳转到登录页
            this.$router.replace("/AdminLogin");
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "已取消注销",
            });
          });
      }
    },

    // 跳转聊天界面
    goChat() {
      // this.$router.push("/chat");
    },
  },
};
</script>

<style scoped>
.homeHeader {
  background-color: #13576a;
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
</style>
