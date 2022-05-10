<template>
  <div id="adminLogin">
    <el-form
      :model="ruleForm"
      :rules="rules"
      ref="ruleForm"
      class="ruleForm-container"
      id="Login"
      v-loading="loading"
      element-loading-text="正在登录,请稍候..."
      element-loading-spinner="el-icon-loading"
      element-loading-background="rgba(0,0,0,0.85)"
      @keydown.enter="submitForm('ruleForm')"
    >
      <h2>系统登录</h2>

      <el-form-item label="电话号" prop="username">
        <el-input
          type="text"
          autocomplete="false"
          v-model="ruleForm.phone"
          placeholder="请输入电话号"
        ></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input
          v-model="ruleForm.password"
          autocomplete="false"
          show-password
          placeholder="请输入密码"
        ></el-input>
      </el-form-item>
      <el-form-item label="验证码" prop="code">
        <el-input
          type="text"
          v-model="ruleForm.code"
          autocomplete="false"
          placeholder="点击图片更换验证码"
          class="code-input"
        ></el-input>
        <img :src="captchaUrl" @click="updataCaptcha" />
      </el-form-item>
      <el-form-item>
        <el-button
          @click="submitForm('ruleForm')"
          :class="this.ruleForm.state == 2 ? 'login-btn02' : 'login-btn01'"
          >登录</el-button
        >
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  name: "AdminLogin",
  data() {
    return {
      captchaUrl: "/captcha?time=" + new Date(),
      //登录表单
      ruleForm: {
        phone: "",
        password: "",
        code: "",
        checked: false,
        state: 0,
      },
      loading: false,
      rules: {
        phone: [
          { required: true, message: "请输入电话号", trigger: "blur" },
          { min: 11, max: 11, message: "请输入规范的电话号", trigger: "blur" },
        ],
        password: [
          { required: true, message: "请输入密码", trigger: "change" },
          {
            min: 3,
            max: 20,
            message: "长度在 3 到 20 个字符",
            trigger: "blur",
          },
        ],
        code: [{ required: true, message: "请填写验证码", trigger: "change" }],
      },

      workerdialogVisible: false,
      companydialogVisible: false,
    };
  },
  mounted() {},
  methods: {
    updataCaptcha() {
      this.captchaUrl = "/captcha?time=" + new Date();
    },
    //登录
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.loading = true;
          this.postRequest("/login", this.ruleForm).then((resp) => {
            if (resp) {
              console.log("登录获取的信息：", resp);
              //存储用户token
              const tokenStr = resp.obj.tokenHead + resp.obj.token;
              window.sessionStorage.setItem("tokenStr", tokenStr);
              this.loading = false;
              //页面跳转
              let path = this.$route.query.redirect;

              //系统用户跳转
              this.$router.replace(
                path == "/" || path == undefined ? "/Home" : path
              );
            } else {
              this.loading = false;
            }
          });
        } else {
          this.$message.error("请按提示正确输入");
          return false;
        }
      });
    },
  },
};
</script>

<style >
#adminLogin h2 {
  text-align: center;
}
.ruleForm-container {
  position: absolute;
  width: 300px;
  height: 430px;
  top: 50%;
  right: 38%;
  border-radius: 15px;
  padding: 15px 35px 15px 35px;
  margin: -215px 0;
  background-color: #81c0f7;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
}

#Login .code-input {
  width: 180px;
  margin-right: 20px;
}
.remeber {
  margin-right: 30px !important;
}
.login-btn {
  width: 68%;
}

.login-btn01 {
  width: 68%;
  background-color: #4aa6f6;
}

.login-btn02 {
  width: 68%;
  background-color: #f4a047;
}

#Login .el-form-item__content {
  display: flex;
  align-items: center;
}
#Login .el-input__inner {
  height: 35px;
  line-height: 35px;
}
#Login .el-form-item__label {
  display: block;
  text-align: left;
  float: none;
}
#Login .worker {
  position: absolute;
  height: 100px;
  width: 30px;
  left: -8%;
  word-wrap: break-word;
  background-color: #ee8b22;
  color: #f3ffff;
  text-align: center;
  padding-top: 5px;
  border-radius: 10px 0 0 10px;
  cursor: pointer;
}

#Login .company {
  position: absolute;
  height: 100px;
  width: 30px;
  right: -8%;
  word-wrap: break-word;
  text-align: center;
  background-color: #72beff;
  color: #f3ffff;
  padding-top: 5px;
  border-radius: 0 10px 10px 0;
  cursor: pointer;
}

#Login .workerRegister {
  position: absolute;
  height: 100px;
  width: 30px;
  top: 55%;
  left: -8%;
  word-wrap: break-word;
  background-color: #ee8b22;
  color: #f3ffff;
  text-align: center;
  padding-top: 5px;
  border-radius: 10px 0 0 10px;
  cursor: pointer;
}

#Login .companyRegister {
  position: absolute;
  height: 100px;
  width: 30px;
  right: -8%;
  top: 55%;
  word-wrap: break-word;
  text-align: center;
  background-color: #72beff;
  color: #f3ffff;
  padding-top: 5px;
  border-radius: 0 10px 10px 0;
  cursor: pointer;
}

#Login el-col {
  margin-right: 10px;
}
</style>