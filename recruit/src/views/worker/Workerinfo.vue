<template>
  <div>
    <h1>个人信息</h1>
    <table>
      <tr>
        <td>姓名：</td>
        <td>
          {{ worker.name }}
        </td>
      </tr>
      <tr>
        <td>联系电话：</td>
        <td>{{ worker.phone }}</td>
      </tr>
      <tr>
        <td>常驻地址：</td>
        <td>{{ worker.nowAddress }}</td>
      </tr>
      <tr>
        <td>意向地址：</td>
        <td>{{ worker.address }}</td>
      </tr>
      <tr>
        <td>工种：</td>
        <td>
          <el-tag
            style="margin-right: 10px"
            v-for="item of worker.workTypeList"
            :key="item.id"
            :index="item.id"
          >
            {{ item.name }}
          </el-tag>
        </td>
      </tr>
      <tr>
        <td>个人邮箱</td>
        <td>{{ worker.email }}</td>
      </tr>
      <tr>
        <td>学历：</td>
        <td>{{ worker.education }}</td>
      </tr>
      <tr>
        <td>性别：</td>
        <td>{{ worker.sex }}</td>
      </tr>
      <tr>
        <td>出生日期：</td>
        <td>{{ worker.birthday }}</td>
      </tr>
      <tr>
        <td>个人简述：</td>
        <td>{{ worker.remark }}</td>
      </tr>
      <tr>
        <td>是否找应聘：</td>

        <td>
          <el-tooltip
            class="item"
            effect="dark"
            content="处于找工状态，企业可以见个人信息"
            placement="right"
          >
            <el-tag v-if="worker.state == 1"> 找工中 </el-tag>
            <el-tag v-else> 暂不找工 </el-tag>
          </el-tooltip>
        </td>
      </tr>
    </table>

    <!-- 修改信息 -->
    <el-button style="margin-top: 20px" type="primary" @click="openhandler01"
      >修改信息</el-button
    >

    <!-- 修改密码 -->
    <el-button type="primary" @click="openhandler02">修改密码</el-button>

    <!-- 修改信息弹窗 -->
    <el-dialog title="修改信息" :visible.sync="dialogVisible01" width="70%">
      <el-form ref="updateWorker" :model="updateWorker" :rules="rules">
        <!-- 第一行 -->
        <el-row>
          <!-- 姓名 -->
          <el-col :span="8">
            <el-form-item label="姓名:" prop="name">
              <el-input
                v-model="updateWorker.name"
                size="mini"
                style="width: 180px"
                placeholder="请输入姓名"
                prefix-icon="el-icon-edit"
              ></el-input>
            </el-form-item>
          </el-col>
          <!-- 电话号 -->
          <el-col :span="8">
            <el-form-item label="手机号:" prop="phone">
              <el-input
                v-model="updateWorker.phone"
                size="mini"
                style="width: 180px"
                placeholder="请输入手机号"
                prefix-icon="el-icon-edit"
              ></el-input>
            </el-form-item>
          </el-col>
          <!-- 邮箱 -->
          <el-col :span="8">
            <el-form-item label="邮箱:" prop="email">
              <el-input
                v-model="updateWorker.email"
                size="mini"
                style="width: 180px"
                placeholder="请输入邮箱"
                prefix-icon="el-icon-edit"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <!-- 第二行 -->
        <el-row>
          <!-- 常驻地址 -->
          <el-col :span="11">
            <el-form-item label="常驻地址:" prop="nowAddress">
              <el-input
                v-model="updateWorker.nowAddress"
                size="mini"
                style="width: 240px"
                placeholder="请输入常驻地址"
                prefix-icon="el-icon-edit"
              ></el-input>
            </el-form-item>
          </el-col>
          <!-- 意向地址 -->
          <el-col :span="11">
            <el-form-item label="意向地址:" prop="address">
              <el-input
                v-model="updateWorker.address"
                size="mini"
                style="width: 240px"
                placeholder="请输入工作意向地址"
                prefix-icon="el-icon-edit"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <!-- 第三行 -->
        <el-row>
          <!-- 学历 -->
          <el-col :span="8">
            <el-form-item label="学历:" prop="Alleducation">
              <el-select
                v-model="updateWorker.education"
                placeholder="学历"
                size="mini"
                style="width: 150px"
              >
                <el-option
                  v-for="item in Alleducation"
                  :key="item"
                  :label="item"
                  :value="item"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <!-- 工种选择 -->
          <el-col :span="8">
            <el-form-item label="工种:" prop="workerTypes">
              <el-select
                collapse-tags
                v-model="updateWorker.workTypes"
                placeholder="选择添加工种"
                multiple
              >
                <el-option
                  v-for="(r, index) in AllWorkTypes"
                  :key="index"
                  :label="r.name"
                  :value="r.id"
                >
                </el-option>
              </el-select>
              <el-button
                slot="reference"
                type="text"
                icon="el-icon-more"
              ></el-button>
            </el-form-item>
          </el-col>
          <!-- 是否找工 -->
          <el-col :span="8">
            <el-form-item label="是否找工:" prop="state">
              <el-switch
                v-model="updateWorker.state"
                active-color="#13ce66"
                inactive-color="#ff4949"
                :active-value="1"
                :inactive-value="0"
              >
              </el-switch>
            </el-form-item>
          </el-col>
        </el-row>
        <!-- 第四行 -->
        <el-row>
          <!-- 个人简述 -->
          <el-col :span="21">
            <el-form-item label="个人简述:" prop="remark">
              <el-input
                style="width: 300px"
                type="textarea"
                v-model="updateWorker.remark"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 按钮 -->
        <el-row>
          <el-col :span="21">
            <el-form-item style="display: flex; justify-content: flex-end">
              <el-button @click="cancelhandler01">取 消</el-button>
              <el-button
                type="primary"
                @click="updateWorkerHandle('updateWorker')"
                >修 改</el-button
              >
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </el-dialog>

    <!-- 修改密码弹窗 -->
    <el-dialog title="提示" :visible.sync="dialogVisible02" width="50%">
      <el-form :model="updatepassword" :rules="rules">
        <el-form-item label="旧密码:" prop="oldpassword">
          <el-input
            v-model="updatepassword.oldpassword"
            size="mini"
            style="width: 180px"
            placeholder="请输入旧密码"
            prefix-icon="el-icon-edit"
          ></el-input>
        </el-form-item>

        <el-form-item label="新密码:" prop="newpassword">
          <el-input
            v-model="updatepassword.newpassword"
            size="mini"
            style="width: 180px"
            placeholder="请输入新密码"
            prefix-icon="el-icon-edit"
          ></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible02 = false">取 消</el-button>
        <el-button type="primary" @click="updatepwd()">修 改</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { resetRouter } from "../../router";
export default {
  name: "Workerinfo",
  data() {
    return {
      user: JSON.parse(window.sessionStorage.getItem("user")),
      worker: JSON.parse(window.sessionStorage.getItem("worker")),
      dialogVisible01: false,
      dialogVisible02: false,

      rules: {
        name: [{ required: true, message: "请输入姓名", trigger: "blur" }],
        phone: [
          { required: true, message: "请输入电话号", trigger: "blur" },
          { min: 11, max: 11, message: "请输入规范的电话号", trigger: "blur" },
        ],
      },

      //更新的数据
      updateWorker: {},
      //更新的密码
      updatepassword: {
        oldpassword: null,
        newpassword: null,
      },

      //学历：
      Alleducation: ["本科", "专科", "小学", "初中", "高中", "硕士", "博士"],

      //工种
      AllWorkTypes: [],

      //所有工种
      workTypes: {},
    };
  },
  mounted() {
    if (this.worker == null) this.initWorker();
    this.initWorkTypes();
    this.$emit("initMessage");
  },
  methods: {
    //获取个人信息
    initWorker() {
      this.getRequest("/worker/" + this.user.id).then((resp) => {
        if (resp) {
          this.worker = resp;
          window.sessionStorage.setItem("worker", JSON.stringify(resp));
        }
      });
    },

    //获取所有工种
    initWorkTypes() {
      this.getRequest("/worktype/").then((resp) => {
        if (resp) {
          this.AllWorkTypes = resp;
        }
      });
    },

    // 修改信息弹窗
    openhandler01() {
      this.dialogVisible01 = true;
      this.updateWorker = JSON.parse(JSON.stringify(this.worker));
    },

    //取消修改信息按钮
    cancelhandler01() {
      this.dialogVisible01 = false;
      this.updateWorker = JSON.parse(JSON.stringify(this.worker));
    },

    //修改密码弹窗
    openhandler02() {
      this.dialogVisible02 = true;
    },

    //修改工人用户信息
    updateWorkerHandle(form) {
      this.$refs[form].validate((valid) => {
        if (valid) {
          this.putRequest("/worker/update", this.updateWorker).then((resp) => {
            if (resp) {
              this.dialogVisible01 = false;
              this.initWorker();
            }
          });
        } else {
          return false;
        }
      });
    },

    //修改密码
    updatepwd() {
      this.putRequest(
        "/worker/password/" +
          this.updatepassword.oldpassword +
          "/" +
          this.updatepassword.newpassword,
        this.worker
      ).then((resp) => {
        if (resp) {
          //回到登录

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
        }
      });
    },
  },
};
</script>

<style>
</style>