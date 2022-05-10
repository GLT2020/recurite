<template>
  <div>
    <el-form
      :model="ruleForm"
      :rules="rules"
      ref="ruleForm"
      :class="
        this.ruleForm.state == 2
          ? 'ruleForm-container02'
          : 'ruleForm-container01'
      "
      id="Login"
      v-loading="loading"
      element-loading-text="正在登录,请稍候..."
      element-loading-spinner="el-icon-loading"
      element-loading-background="rgba(0,0,0,0.85)"
      @keydown.enter="submitForm('ruleForm')"
    >
      <h2>登录</h2>
      <div @click="worker" class="worker">工人登录</div>
      <div @click="company" class="company">企业登录</div>
      <div @click="workerRegister" class="workerRegister">工人注册</div>
      <div @click="companyRegister" class="companyRegister">企业注册</div>
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
        <el-button
          @click="NotPasswordDialog('ruleForm')"
          type="text"
          style="color: #fff"
          >忘记密码？</el-button
        >
      </el-form-item>
    </el-form>

    <!-- 工人注册弹窗 -->
    <el-dialog title="工人注册" :visible.sync="workerdialogVisible" width="75%">
      <el-form ref="empForm" :model="addWorker" :rules="workerrules">
        <!-- 第一行 -->
        <el-row>
          <!-- 姓名 -->
          <el-col :span="8">
            <el-form-item label="姓名:" prop="name">
              <el-input
                v-model="addWorker.name"
                size="mini"
                style="width: 180px"
                placeholder="请输入名字"
                prefix-icon="el-icon-edit"
              ></el-input>
            </el-form-item>
          </el-col>
          <!-- 电话号 -->
          <el-col :span="8">
            <el-form-item label="电话号:" prop="phone">
              <el-input
                v-model="addWorker.phone"
                size="mini"
                style="width: 180px"
                placeholder="请输入电话号"
                prefix-icon="el-icon-edit"
              ></el-input>
            </el-form-item>
          </el-col>
          <!-- 密码 -->
          <el-col :span="8">
            <el-form-item label="密码:" prop="password">
              <el-input
                v-model="addWorker.password"
                size="mini"
                style="width: 180px"
                placeholder="请输入密码"
                prefix-icon="el-icon-edit"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <!-- 第二行 -->
        <el-row>
          <!-- 工作意向地址 -->
          <el-col :span="12">
            <el-form-item label="工作意向地址:" prop="address">
              <el-input
                v-model="addWorker.address"
                size="mini"
                style="width: 320px"
                placeholder="请输入工作意向地址"
                prefix-icon="el-icon-edit"
              ></el-input>
            </el-form-item>
          </el-col>
          <!-- 常驻地址 -->
          <el-col :span="12">
            <el-form-item label="常住地址:" prop="nowAddress">
              <el-input
                v-model="addWorker.nowAddress"
                size="mini"
                style="width: 320px"
                placeholder="请输入常住地址"
                prefix-icon="el-icon-edit"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <!-- 第三行 -->
        <el-row>
          <!-- 性别 -->
          <el-col :span="8">
            <el-form-item label="性别:" prop="sex">
              <el-radio-group v-model="addWorker.sex">
                <el-radio label="男">男</el-radio>
                <el-radio label="女">女</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <!-- 邮箱 -->
          <el-col :span="8">
            <el-form-item label="邮箱:" prop="email">
              <el-input
                v-model="addWorker.email"
                size="mini"
                style="width: 180px"
                placeholder="请输入邮箱"
                prefix-icon="el-icon-edit"
              ></el-input>
            </el-form-item>
          </el-col>

          <!-- 出生日期 -->
          <el-col :span="8">
            <el-form-item label="出生日期:" prop="birthday">
              <el-date-picker
                v-model="addWorker.birthday"
                type="date"
                size="mini"
                style="width: 150px"
                value-format="yyyy-MM-dd"
                placeholder="出生日期"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <!-- 第四行 -->
        <el-row>
          <!-- 学历 -->
          <el-col :span="12">
            <el-form-item label="学历:" prop="Alleducation">
              <el-select
                v-model="addWorker.education"
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
          <!-- 工种 -->
          <el-col :span="12">
            <el-form-item label="工种:" prop="workerTypes">
              <!-- 工种选择 -->
              <el-select
                size="mini"
                collapse-tags
                v-model="addWorker.workTypes"
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

              <!-- <el-select
                v-model="addWorker.workTypes"
                placeholder="工种"
                size="mini"
                style="width: 150px"
              >
                <el-option
                  v-for="item in AllWorkTypes"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                >
                </el-option>
              </el-select> -->
            </el-form-item>
          </el-col>
        </el-row>
        <!-- 第五行 -->
        <el-row>
          <!-- 验证码 -->
          <el-col :span="10">
            <el-form-item label="验证码:" prop="code">
              <el-input
                v-model="addWorker.code"
                size="mini"
                style="width: 180px"
                placeholder="请输入验证码"
                prefix-icon="el-icon-edit"
              ></el-input>
            </el-form-item>
          </el-col>
          <!-- 验证码图片 -->
          <el-col :span="6">
            <img :src="captchaUrl" @click="updataCaptcha" />
          </el-col>
        </el-row>
        <!-- 第六行 -->
        <el-row>
          <!-- 个人简述 -->
          <el-col :span="22">
            <el-form-item label="个人简述:" prop="remark">
              <el-input type="textarea" v-model="addWorker.remark"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <!-- 按钮 -->
        <el-row>
          <el-col :span="21">
            <el-form-item style="display: flex; justify-content: flex-end">
              <el-button @click="closeDialog">取 消</el-button>
              <el-button type="primary" @click="doaddWorker('empForm')"
                >注 册</el-button
              >
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <!-- <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="doaddWorker">确 定</el-button>
      </span> -->
    </el-dialog>

    <!-- 企业注册弹窗 -->
    <el-dialog
      title="企业注册"
      :visible.sync="companydialogVisible"
      width="92%"
    >
      <div>
        <el-form ref="comForm" :model="addCompany" :rules="companyrules">
          <!-- 第一行 -->
          <el-row>
            <!-- 姓名 -->
            <el-col :span="6">
              <el-form-item label="公司名:" prop="name">
                <el-input
                  v-model="addCompany.name"
                  size="mini"
                  style="width: 180px"
                  placeholder="请输入公司名字"
                  prefix-icon="el-icon-edit"
                ></el-input>
              </el-form-item>
            </el-col>
            <!-- 邮箱 -->
            <el-col :span="7">
              <el-form-item label="邮箱:" prop="email">
                <el-input
                  v-model="addCompany.email"
                  size="mini"
                  style="width: 180px"
                  placeholder="请输入邮箱"
                  prefix-icon="el-icon-edit"
                ></el-input>
              </el-form-item>
            </el-col>
            <!-- 联系地址 -->
            <el-col :span="11">
              <el-form-item label="联系地址:" prop="address">
                <el-input
                  v-model="addCompany.address"
                  size="mini"
                  style="width: 320px"
                  placeholder="请输入联系地址"
                  prefix-icon="el-icon-edit"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <!-- 第二行 -->
          <el-row>
            <!-- 法人 -->
            <el-col :span="6">
              <el-form-item label="法人:" prop="person">
                <el-input
                  v-model="addCompany.person"
                  size="mini"
                  style="width: 150px"
                  placeholder="请输入法人姓名"
                  prefix-icon="el-icon-edit"
                ></el-input>
              </el-form-item>
            </el-col>
            <!-- 电话号 -->
            <el-col :span="6">
              <el-form-item label="电话号:" prop="phone">
                <el-input
                  v-model="addCompany.phone"
                  size="mini"
                  style="width: 180px"
                  placeholder="请输入电话号"
                  prefix-icon="el-icon-edit"
                ></el-input>
              </el-form-item>
            </el-col>
            <!-- 密码 -->
            <el-col :span="5">
              <el-form-item label="密码:" prop="password">
                <el-input
                  v-model="addCompany.password"
                  size="mini"
                  style="width: 150px"
                  placeholder="请输入密码"
                  prefix-icon="el-icon-edit"
                ></el-input>
              </el-form-item>
            </el-col>

            <!-- 创建日期 -->
            <el-col :span="6">
              <el-form-item label="创建日期:" prop="createTime">
                <el-date-picker
                  v-model="addCompany.createTime"
                  type="date"
                  size="mini"
                  style="width: 150px"
                  value-format="yyyy-MM-dd"
                  placeholder="创建日期"
                >
                </el-date-picker>
              </el-form-item>
            </el-col>
          </el-row>
          <!-- 第二行 -->
          <el-row>
            <!-- 验证码 -->
            <el-col :span="7">
              <el-form-item label="验证码:" prop="code">
                <el-input
                  v-model="addCompany.code"
                  size="mini"
                  style="width: 180px"
                  placeholder="请输入验证码"
                  prefix-icon="el-icon-edit"
                ></el-input>
              </el-form-item>
            </el-col>
            <!-- 验证码图片 -->
            <el-col :span="6">
              <img :src="captchaUrl" @click="updataCaptcha" />
            </el-col>
          </el-row>
          <!-- 第四行 -->
          <el-row>
            <!-- 公司简述 -->
            <el-col :span="21">
              <el-form-item label="公司简述:" prop="remark">
                <el-input
                  type="textarea"
                  v-model="addCompany.remark"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <!-- 上传营业执照 -->
          <el-row>
            <el-alert
              style="margin-bottom: 5px; width: 300px"
              title="单次限制上传一张"
              type="info"
            >
            </el-alert>
            <el-form-item label="公司执照：">
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
            </el-form-item>
          </el-row>
          <!-- 按钮 -->
          <el-row>
            <el-col :span="21">
              <el-form-item style="display: flex; justify-content: flex-end">
                <el-button @click="closeDialog">取 消</el-button>
                <el-button type="primary" @click="doaddCompany('comForm')"
                  >注 册</el-button
                >
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
      <!-- <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="doaddCompany('comForm')"
          >注 册</el-button
        >
      </span> -->
    </el-dialog>

    <!-- 工人忘记密码弹窗 -->
    <el-dialog
      title="工人忘记密码"
      :visible.sync="notPassworddialogVisible01"
      width="92%"
    >
      <div>
        <el-form
          ref="notPasswordForm"
          :model="notPasswordDate"
          :rules="notPasswordrules"
        >
          <!-- 第一行 -->
          <el-row>
            <!-- 手机号 -->
            <el-col :span="10">
              <el-form-item label="手机号:" prop="phone">
                <el-input
                  v-model="notPasswordDate.phone"
                  size="mini"
                  style="width: 150px"
                  placeholder="请输入手机号"
                  prefix-icon="el-icon-edit"
                ></el-input>
              </el-form-item>
            </el-col>
            <!-- 常住地址 -->
            <el-col :span="10">
              <el-form-item label="常住地址:" prop="nowAddress">
                <el-input
                  v-model="notPasswordDate.nowAddress"
                  size="mini"
                  style="width: 250px"
                  placeholder="请输入常住地址"
                  prefix-icon="el-icon-edit"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <!-- 第二行 -->
          <el-row>
            <!-- 出生日期 -->
            <el-col :span="10">
              <el-form-item label="出生日期:" prop="birthday">
                <el-date-picker
                  v-model="notPasswordDate.birthday"
                  type="date"
                  size="mini"
                  style="width: 150px"
                  value-format="yyyy-MM-dd"
                  placeholder="出生日期"
                >
                </el-date-picker>
              </el-form-item>
            </el-col>
            <!-- 新密码 -->
            <el-col :span="10">
              <el-form-item label="新密码:" prop="password">
                <el-input
                  v-model="notPasswordDate.password"
                  size="mini"
                  style="width: 150px"
                  placeholder="请输入新密码"
                  prefix-icon="el-icon-edit"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <!-- 验证码 -->
            <el-col :span="6">
              <el-form-item label="验证码:" prop="code">
                <el-input
                  v-model="notPasswordDate.code"
                  size="mini"
                  style="width: 150px"
                  placeholder="请输入验证码"
                  prefix-icon="el-icon-edit"
                ></el-input>
              </el-form-item>
            </el-col>
            <!-- 验证码图片 -->
            <img :src="captchaUrl" @click="updataCaptcha" />
          </el-row>
          <!-- 按钮 -->
          <el-row>
            <el-col :span="21">
              <el-form-item style="display: flex; justify-content: flex-end">
                <el-button @click="closeDialog">取 消</el-button>
                <el-button
                  type="primary"
                  @click="donotPassword('notPasswordForm')"
                  >更换密码</el-button
                >
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
    </el-dialog>

    <!-- 企业忘记密码弹窗 -->
    <el-dialog
      title="企业忘记密码"
      :visible.sync="notPassworddialogVisible02"
      width="70%"
    >
      <div>
        <el-form
          ref="ComnotPasswordForm"
          :model="ComnotPasswordDate"
          :rules="ComnotPasswordrules"
        >
          <!-- 第一行 -->
          <el-row>
            <!-- 邮箱 -->
            <el-col :span="12">
              <el-form-item label="邮  箱:" prop="email">
                <el-input
                  v-model="ComnotPasswordDate.email"
                  size="mini"
                  style="width: 190px"
                  placeholder="请输入邮箱"
                  prefix-icon="el-icon-edit"
                ></el-input>
              </el-form-item>
            </el-col>
            <!-- 手机号 -->
            <el-col :span="12">
              <el-form-item label="手机号:" prop="phone">
                <el-input
                  v-model="ComnotPasswordDate.phone"
                  size="mini"
                  style="width: 190px"
                  placeholder="请输入手机号"
                  prefix-icon="el-icon-edit"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <!-- 第二行 -->
          <el-row>
            <!-- 新密码 -->
            <el-col :span="12">
              <el-form-item label="新密码:" prop="password">
                <el-input
                  v-model="ComnotPasswordDate.password"
                  size="mini"
                  style="width: 180px"
                  placeholder="请输入新密码"
                  prefix-icon="el-icon-edit"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <!-- 验证码 -->
            <el-col :span="12">
              <el-form-item label="验证码:" prop="code">
                <el-input
                  v-model="ComnotPasswordDate.code"
                  size="mini"
                  style="width: 180px"
                  placeholder="请输入邮箱验证码"
                  prefix-icon="el-icon-edit"
                ></el-input>
              </el-form-item>
              <el-col :span="6">
                <el-form-item label="">
                  <el-button
                    v-if="timer == null"
                    :disabled="!mailDisable"
                    type="primary"
                    @click="sendEmailHandle('ComnotPasswordForm')"
                  >
                    获取邮件
                  </el-button>
                  <el-button v-else type="primary" :disabled="mailDisable">
                    {{ count }} 秒后重试
                  </el-button>
                </el-form-item>
              </el-col>
            </el-col>
          </el-row>
          <!-- 按钮 -->
          <el-row>
            <el-col :span="21">
              <el-form-item style="display: flex; justify-content: flex-end">
                <el-button @click="closeDialog">取 消</el-button>
                <el-button
                  type="primary"
                  @click="ComdonotPassword('ComnotPasswordForm')"
                  >更换密码</el-button
                >
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "Login",
  data() {
    return {
      captchaUrl: "/captcha?time=" + new Date(),
      //登录表单
      ruleForm: {
        phone: "",
        password: "",
        code: "",
        checked: false,
        state: 2, //默认为工人
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

      file: null,

      formData: null,

      //工人忘记数据
      notPasswordDate: {},

      //企业忘记密码
      ComnotPasswordDate: {},

      //邮件按钮
      mailDisable: true,
      //计时器
      timer: null,
      //倒数秒速
      count: 60,

      //工人注册规则
      workerrules: {
        name: [{ required: true, message: "请输入姓名", trigger: "blur" }],
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
        birthday: [
          { required: true, message: "请输入出生日期", trigger: "blur" },
        ],
        sex: [{ required: true, message: "请选择性别", trigger: "blur" }],
        address: [{ required: true, message: "请输入地址", trigger: "blur" }],
        nowAddress: [
          { required: true, message: "请输入地址", trigger: "blur" },
        ],
        code: [{ required: true, message: "请填写验证码", trigger: "change" }],
      },

      //公司注册规则
      companyrules: {
        name: [{ required: true, message: "请输入公司名", trigger: "blur" }],
        phone: [
          { required: true, message: "请输入电话号", trigger: "blur" },
          { min: 3, max: 11, message: "请输入规范的电话号", trigger: "blur" },
        ],
        email: [
          { required: true, message: "请输入邮箱", trigger: "blur" },
          {
            pattern:
              /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/,
            message: "请输入规范的邮箱",
            trigger: "blur",
          },
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
        createTime: [
          { required: true, message: "请选择创建日期", trigger: "blur" },
        ],
        person: [
          { required: true, message: "请输入法入姓名", trigger: "blur" },
        ],
        address: [{ required: true, message: "请输入地址", trigger: "blur" }],
        code: [{ required: true, message: "请填写验证码", trigger: "change" }],
      },

      //忘记密码规则
      notPasswordrules: {
        phone: [
          { required: true, message: "请输入电话号", trigger: "blur" },
          { min: 3, max: 11, message: "请输入规范的电话号", trigger: "blur" },
        ],
        password: [
          { required: true, message: "请输入新密码", trigger: "change" },
          {
            min: 3,
            max: 20,
            message: "长度在 3 到 20 个字符",
            trigger: "blur",
          },
        ],
        //工人用
        birthday: [
          { required: true, message: "请输入出生日期", trigger: "blur" },
        ],
        nowAddress: [
          { required: true, message: "请输入地址", trigger: "blur" },
        ],
        //公司用
        createTime: [
          { required: true, message: "请选择创建日期", trigger: "blur" },
        ],
        address: [{ required: true, message: "请输入地址", trigger: "blur" }],
        code: [{ required: true, message: "请填写验证码", trigger: "change" }],
      },

      //企业忘记密码规则
      ComnotPasswordrules: {
        phone: [
          { required: true, message: "请输入电话号", trigger: "blur" },
          { min: 3, max: 11, message: "请输入规范的电话号", trigger: "blur" },
        ],
        email: [
          { required: true, message: "请输入邮箱", trigger: "blur" },
          {
            pattern:
              /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/,
            message: "请输入规范的邮箱",
            trigger: "blur",
          },
        ],
        password: [
          { required: true, message: "请输入新密码", trigger: "change" },
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
      notPassworddialogVisible01: false,
      notPassworddialogVisible02: false,

      //学历：
      Alleducation: ["本科", "专科", "小学", "初中", "高中", "硕士", "博士"],

      //工种
      AllWorkTypes: {},

      //工人注册
      addWorker: {
        name: "",
        password: "",
        phone: "",
        nowAddress: null,
        address: null,
        email: null,
        education: null,
        birthday: null,
        sex: null,
        remark: "",
        state: 0,
        code: null,
        workTypes: [],
      },
      //公司注册
      addCompany: {
        name: "",
        password: "",
        phone: "",
        address: "",
        license: "",
        person: "",
        license: null,
        create_time: "",
        update_time: null,
        remark: "",
        state: 1,
        code: null,
      },

      //所有工种
      workTypes: {},

      //图片上传
      dialogImageUrl: "",
      dialogImg: false,
    };
  },
  mounted() {
    this.initWorkTypes();
  },
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
              // console.log(window.sessionStorage.getItem("tokenStr"));
              this.loading = false;
              //页面跳转
              // this.$router.replace("/home");
              //获取输入的路径
              let path = this.$route.query.redirect;
              if (this.ruleForm.state == 1) {
                //企业用户跳转
                this.$router.replace(
                  path == "/" || path == undefined ? "/CompanyHome" : path
                );
              } else {
                //工人用户跳转
                this.$router.replace(
                  path == "/" || path == undefined ? "/WorkerHome" : path
                );
              }
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

    //更换为企业用户登录
    company() {
      this.ruleForm.state = 1;
    },

    //更换为工人用户登录
    worker() {
      this.ruleForm.state = 2;
    },

    //获取所有工种
    initWorkTypes() {
      this.getRequest("/worktype/").then((resp) => {
        if (resp) {
          this.AllWorkTypes = resp;
          console.log("工种表：", resp);
        }
      });
    },

    //工人注册触发
    workerRegister() {
      this.workerdialogVisible = true;
    },
    //企业注册触发
    companyRegister() {
      this.companydialogVisible = true;
    },

    //工人注册
    doaddWorker(empForm) {
      this.$refs[empForm].validate((valid) => {
        if (valid) {
          this.postRequest("/worker/register", this.addWorker).then((resp) => {
            if (resp) {
              this.loading = false;
              this.addWorker = {};
              this.workerdialogVisible = false;
              this.updataCaptcha();
              //页面跳转
              // this.$router.replace("/home");
              //获取输入的路径
              // let path = this.$route.query.redirect;
              // this.$router.replace(
              //   path == "/" || path == undefined ? "/home" : path
              // );
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
    //公司注册
    doaddCompany(comForm) {
      let _this = this;
      this.$refs.upload01.submit();
      this.$refs[comForm].validate((valid) => {
        if (valid) {
          this.postRequest("/company/register", this.addCompany).then(
            (resp) => {
              if (resp) {
                this.loading = false;
                this.addCompany = {};
                this.companydialogVisible = false;
                this.updataCaptcha();
                this.$refs.upload01.clearFiles();
                //页面跳转
                // this.$router.replace("/home");
                //获取输入的路径
                // let path = this.$route.query.redirect;
                // this.$router.replace(
                //   path == "/" || path == undefined ? "/home" : path
                // );
              } else {
                this.loading = false;
              }
            }
          );
          // _this.formData = new FormData();
          // _this.formData.append("name", this.addCompany.name);
          // _this.formData.append("password", this.addCompany.password);
          // _this.formData.append("phone", this.addCompany.phone);
          // _this.formData.append("address", this.addCompany.address);
          // _this.formData.append("license", this.addCompany.license);
          // _this.formData.append("person", this.addCompany.person);
          // _this.formData.append("createTime", this.addCompany.createTime);
          // _this.formData.append("updateTime", this.addCompany.updateTime);
          // _this.formData.append("remark", this.addCompany.remark);
          // _this.formData.append("state", this.addCompany.state);
          // _this
          //   .postFileRequest("/company/register", this.addCompany, this.file)
          //   .then((resp) => {
          //     if (resp) {
          //       this.loading = false;
          //       this.addCompany = {};
          //       this.companydialogVisible = false;
          //       this.updataCaptcha();
          //       this.$refs.upload01.clearFiles();
          //     } else {
          //       this.loading = false;
          //     }
          //   });
        } else {
          this.$message.error("请按提示正确输入");
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
      this.addCompany.license = response;
    },

    // upLoadFile(file) {
    //   console.log("自定义方法上传的图片：", file);
    //   // this.addCompany.append("file", file.file);
    //   // this.addCompany.license = file.file;
    //   // this.postRequest("/company/uploadImg", file).then((resp) => {
    //   //   if (resp) {
    //   // console.log("返回的图片路径", resp);
    //   // this.license = resp;
    //   //   }
    //   // });
    //   axios({
    //     url: "/company/uploadImg",
    //     method: "post",
    //     data: file,
    //     headers: { "Content-Type": "multipart/form-data" },
    //   }).then((resp) => {
    //     if (resp) {
    //       console.log("返回的图片路径", resp);
    //       this.license = resp;
    //     }
    //   });
    //   // this.file = file.file;
    // },

    beforeProductUpload(file) {
      var _this = this;
      // this.addCompany.license = file;
      console.log("上传的图片", file);
      this.file = file.file;
      // return new Promise(function (resolve, reject) {
      //   var reader = new FileReader();
      //   // reader.readAsDataURL(file); // 这里是最关键的一步，转换成base64
      //   reader.onload = function (event) {
      //     _this.addCompany.license = event.target.result; //定义参数获取图片路径
      //     console.log("上传的图片：", _this.addCompany.license);
      //   };
      // });
    },

    //关闭所有弹窗
    closeDialog() {
      this.workerdialogVisible = false;
      this.companydialogVisible = false;
      this.notPassworddialogVisible01 = false;
      this.notPassworddialogVisible02 = false;
      this.updataCaptcha();
    },

    //忘记密码弹窗
    NotPasswordDialog(comFrom) {
      if (this.ruleForm.state == 1) {
        this.notPassworddialogVisible02 = true;
      } else this.notPassworddialogVisible01 = true;
    },

    //工人忘记密码
    donotPassword(From) {
      this.$refs[From].validate((valid) => {
        if (valid) {
          let url = "/worker/notPassword";
          // if (this.ruleForm.state == 1) {
          //   //为公司忘记密码
          //   url = "/company/notPassword";
          // } else {
          //   url = "/worker/notPassword";
          // }
          this.postRequest(url, this.notPasswordDate).then((resp) => {
            if (resp) {
              this.loading = false;
              this.closeDialog();
              this.updataCaptcha();
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

    //发送邮箱验证
    sendEmailHandle(From) {
      let isError = 0;
      this.$refs[From].validateField(["email", "phone"], (error) => {
        if (error) {
          isError = 1;
          this.$message.error("请按提示正确输入");
          return false;
        }
      });
      //开启定时器
      if (isError == 0) {
        if (!this.timer) {
          this.count = 60;
          this.mailDisable = false;
          this.timer = setInterval(() => {
            if (this.count > 0 && this.count <= 60) {
              this.count--;
            } else {
              this.mailDisable = true;
              clearInterval(this.timer);
              this.timer = null;
            }
          }, 1000);
        }

        let url = "/company/sendEmail";
        this.postRequest(url, this.ComnotPasswordDate).then((resp) => {
          if (resp) {
            // this.updataCaptcha();
          }
        });
      }
    },

    //企业忘记密码
    ComdonotPassword(From) {
      this.$refs[From].validate((valid) => {
        if (valid) {
          let url = "/company/notPassword";

          this.postRequest(url, this.ComnotPasswordDate).then((resp) => {
            if (resp) {
              this.loading = false;
              this.closeDialog();
              this.updataCaptcha();
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
#Login h2 {
  text-align: center;
}
.ruleForm-container01 {
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
.ruleForm-container02 {
  position: absolute;
  width: 300px;
  height: 430px;
  top: 50%;
  right: 38%;
  border-radius: 15px;
  padding: 15px 35px 15px 35px;
  margin: -215px 0;
  background-color: #f5c99a;
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