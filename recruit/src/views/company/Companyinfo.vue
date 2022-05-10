<template>
  <div>
    <h1>公司信息</h1>
    <table>
      <tr>
        <td>公司名：</td>
        <td>
          {{ company.name }}
          <el-tooltip
            class="item"
            effect="dark"
            content="只有验证通过才可以发布工地、招聘等信息"
            placement="top"
          >
            <el-tag type="success" v-if="company.state == 2">已验证</el-tag>
            <el-tag type="danger" v-else-if="company.state == 0">未通过</el-tag>
            <el-tag type="warning" v-else>待验证</el-tag>
          </el-tooltip>
        </td>
      </tr>
      <tr>
        <td>联系电话：</td>
        <td>{{ company.phone }}</td>
      </tr>
      <tr>
        <td>联系地址：</td>
        <td>{{ company.address }}</td>
      </tr>
      <tr>
        <td>法人：</td>
        <td>{{ company.person }}</td>
      </tr>
      <tr>
        <td>创建时间：</td>
        <td>{{ company.createTime }}</td>
      </tr>
      <tr>
        <td>公司简述：</td>
        <td>{{ company.remark }}</td>
      </tr>
    </table>
    <!-- 修改信息 -->
    <el-tooltip
      class="item"
      effect="dark"
      content="修改信息后需要重新等待验证！"
      placement="top"
    >
      <el-button style="margin-top: 20px" type="primary" @click="openhandler01"
        >修改信息</el-button
      >
    </el-tooltip>

    <!-- 修改密码 -->
    <el-button type="primary" @click="openhandler02">修改密码</el-button>

    <!-- 修改信息弹窗 -->
    <el-dialog title="修改信息" :visible.sync="dialogVisible01" width="70%">
      <table>
        <tr>
          <td>公司名：</td>
          <td>
            <el-input
              v-model="updatecompany.name"
              size="small"
              style="margin-left: 5px; width: 300px"
            ></el-input>
          </td>
        </tr>
        <tr>
          <td>联系电话：</td>
          <td>
            <el-input
              v-model="updatecompany.phone"
              size="small"
              style="margin-left: 5px; width: 300px"
            ></el-input>
          </td>
        </tr>

        <tr>
          <td>联系地址：</td>
          <td>
            <el-input
              type="textarea"
              v-model="updatecompany.address"
            ></el-input>
          </td>
        </tr>
        <tr>
          <td>法人：</td>
          <td>
            <el-input
              v-model="updatecompany.person"
              size="small"
              style="margin-left: 5px; width: 300px"
            ></el-input>
          </td>
        </tr>

        <tr>
          <td>公司简述：</td>
          <td>
            <el-input type="textarea" v-model="updatecompany.remark"></el-input>
          </td>
        </tr>
        <tr>
          <td>原公司执照：</td>
          <td>
            <el-image
              style="width: 100px; height: 100px"
              :src="updatecompany.license"
              fit="fill"
            ></el-image>
          </td>
          <td>更换公司执照：</td>
          <td>
            <el-upload
              ref="upload01"
              action="/company/uploadImg"
              :limit="1"
              list-type="picture-card"
              :on-success="handlesuccessUpload"
              :on-preview="handlePictureCardPreview"
              :on-remove="handleRemove"
            >
              <!-- :http-request="upLoadFile" -->
              <!-- :auto-upload="false" -->
              <!-- :before-upload="beforeProductUpload" -->

              <i class="el-icon-plus"></i>
            </el-upload>
            <el-dialog :visible.sync="dialogImg">
              <img width="100%" :src="dialogImageUrl" alt="" />
            </el-dialog>
          </td>
        </tr>
      </table>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible01 = false">取 消</el-button>
        <el-tooltip
          class="item"
          effect="dark"
          content="修改信息后需要重新登录！"
          placement="top"
        >
          <el-button type="primary" @click="updateMsg()">修 改</el-button>
        </el-tooltip>
      </span>
    </el-dialog>

    <!-- 修改密码弹窗 -->
    <el-dialog title="提示" :visible.sync="dialogVisible02" width="70%">
      <el-form ref="passwordForm" :model="updatepassword" :rules="rules">
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
        <el-form-item style="display: flex; justify-content: flex-end">
          <template>
            <el-button @click="dialogVisible02 = false">取 消</el-button>
            <el-button type="primary" @click="updatepwd('passwordForm')"
              >修 改</el-button
            >
          </template>
        </el-form-item>
      </el-form>
      <!-- <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible02 = false">取 消</el-button>
        <el-button type="primary" @click="updatepwd()">修 改</el-button>
      </span> -->
    </el-dialog>
  </div>
</template>

<script>
import { resetRouter } from "../../router";
export default {
  name: "Companyinfo",
  data() {
    return {
      user: JSON.parse(window.sessionStorage.getItem("user")),
      company: JSON.parse(window.sessionStorage.getItem("company")),
      // company: {
      //   id: null,
      //   name: null,
      //   phone: null,
      //   password: null,
      //   adress: null,
      //   person: null,
      //   createTime: null,
      //   remark: null,
      //   updateTime: null,
      //   userface: null,
      //   state: null,
      // },
      // company: JSON.parse(window.sessionStorage.getItem("company")),
      dialogVisible01: false,
      dialogVisible02: false,
      //修改用的
      updatecompany: {
        id: "",
        name: "",
        phone: "",
        password: "",
        adress: "",
        person: "",
        createTime: "",
        remark: "",
        updateTime: "",
        userface: "",
        state: "",
      },
      updatepassword: {
        oldpassword: null,
        newpassword: null,
      },

      rules: {
        oldpassword: [
          { required: true, message: "请填写旧密码", trigger: "change" },
          {
            min: 3,
            max: 20,
            message: "长度在 3 到 20 个字符",
            trigger: "blur",
          },
        ],
        newpassword: [
          { required: true, message: "请填写新密码", trigger: "change" },
          {
            min: 3,
            max: 20,
            message: "长度在 3 到 20 个字符",
            trigger: "blur",
          },
        ],
      },

      file: null,

      formData: null,

      //图片上传
      dialogImageUrl: "",
      dialogImg: false,
    };
  },
  mounted() {
    // console.log(this.user);
    //获取当前登录的公司信息
    if (this.company == null) this.initCompany();
    this.$emit("initMessage");
  },
  methods: {
    //获取当前登录用户信息
    initCompany() {
      this.company = JSON.parse(window.sessionStorage.getItem("company"));
      // this.company = JSON.parse(window.sessionStorage.getItem("company"));
      // console.log("公司用户信息获取公司用户", this.company.toString.name);
      // if (this.company == null) {
      this.getRequest("/company/" + this.user.id).then((resp) => {
        if (resp) {
          this.company = resp;
          console.log(this.company.toString);
          //放入session中，方便以后用
          window.sessionStorage.removeItem("company");
          window.sessionStorage.setItem("company", this.company);
        }
      });
      // }
    },

    //打开修改信息弹窗
    openhandler01() {
      this.updatecompany = JSON.parse(JSON.stringify(this.company));
      this.dialogVisible01 = true;
    },

    //打卡修改密码弹窗
    openhandler02() {
      this.dialogVisible02 = true;
    },

    //修改信息弹窗
    updateMsg() {
      // console.log(this.updatecompany);
      if (this.file != null) {
        this.updatecompany.license = this.file;
      }
      this.putRequest("/company/", this.updatecompany).then((resp) => {
        if (resp) {
          this.dialogVisible01 = false;
          // this.initCompany();
          this.$refs.upload01.clearFiles();
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

    //修改密码
    updatepwd(form) {
      this.$refs[form].validate((valid) => {
        if (valid) {
          this.putRequest(
            "/company/password/" +
              this.updatepassword.oldpassword +
              "/" +
              this.updatepassword.newpassword,
            this.company
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
        } else {
          return false;
        }
      });
    },

    //图片处理
    handleRemove(file, fileList) {
      //移除图片
      console.log(file, fileList);
    },

    handlePictureCardPreview(file) {
      //图片预览
      this.dialogImageUrl = file.url;
      this.dialogImg = true;
    },

    //上传图片成功返回
    handlesuccessUpload(response, file, fileList) {
      console.log("成功上传的返回值:", response);
      this.file = response;
    },
  },
};
</script>

<style>
</style>