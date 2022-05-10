<template>
  <div>
    <h1>找人模块</h1>
    <div style="display: flex; justify-content: space-between">
      <!-- 搜索工具栏 -->
      <div>
        <el-input
          style="width: 300px; margin-right: 10px"
          prefix-icon="el-icon-search"
          placeholder="请输入工人名字进行搜索"
          clearable
          :disabled="showAdvanceSearch"
          v-model="searchDate.name"
          @keydown.enter="searchHandler('normal')"
        ></el-input>
        <el-button
          type="primary"
          icon="el-icon-search"
          @click="searchHandler('normal')"
          :disabled="showAdvanceSearch"
          >搜索</el-button
        >
        <el-button
          type="primary"
          @click="showAdvanceSearch = !showAdvanceSearch"
        >
          <i
            :class="
              showAdvanceSearch
                ? 'fa fa-angle-double-up'
                : 'fa fa-angle-double-down'
            "
            aria-hidden="true"
          ></i>
          高级搜索</el-button
        >
      </div>
    </div>
    <!-- 高级搜索表单 -->
    <transition name="slide-fade">
      <div
        v-show="showAdvanceSearch"
        style="
          border: 1px solid #409eff;
          border-radius: 5px;
          box-sizing: border-box;
          padding: 5px;
          margin: 10px 0px;
        "
      >
        <!-- 搜索表单 -->
        <el-form ref="searchDate" :model="searchDate" :rules="rules">
          <!-- 第一行 -->
          <el-row>
            <!-- 名字 -->
            <el-col :span="6">
              <el-form-item label="姓名:" prop="name">
                <el-input
                  v-model="searchDate.name"
                  size="mini"
                  style="width: 180px"
                  placeholder="请输入工人姓名"
                  prefix-icon="el-icon-edit"
                ></el-input>
              </el-form-item>
            </el-col>

            <!-- 电话号 -->
            <el-col :span="6">
              <el-form-item label="电话号:" prop="phone">
                <el-input
                  v-model="searchDate.phone"
                  size="mini"
                  style="width: 180px"
                  placeholder="请输入电话号"
                  prefix-icon="el-icon-edit"
                ></el-input>
              </el-form-item>
            </el-col>

            <!-- 邮箱 -->
            <el-col :span="6">
              <el-form-item label="邮箱:" prop="email">
                <el-input
                  v-model="searchDate.email"
                  size="mini"
                  style="width: 180px"
                  placeholder="请输入邮箱"
                  prefix-icon="el-icon-edit"
                ></el-input>
              </el-form-item>
            </el-col>

            <!-- 学历 -->
            <el-col :span="6">
              <el-form-item label="学历:" prop="education">
                <el-select
                  v-model="searchDate.education"
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
          </el-row>
          <!-- 第二行 -->
          <el-row style="margin-top: 20px">
            <!-- 常住地址 -->
            <el-col :span="12">
              <el-form-item label="常住地址:" prop="nowAddress">
                <el-input
                  v-model="searchDate.nowAddress"
                  size="mini"
                  style="width: 280px"
                  placeholder="请输入常住地址"
                  prefix-icon="el-icon-edit"
                ></el-input>
              </el-form-item>
            </el-col>

            <!-- 意向地址 -->
            <el-col :span="12">
              <el-form-item label="意向地址:" prop="address">
                <el-input
                  v-model="searchDate.address"
                  size="mini"
                  style="width: 280px"
                  placeholder="请输入意向地址"
                  prefix-icon="el-icon-edit"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row style="margin-top: 15px">
            <!-- 工种 -->
            <el-col :span="8">
              <el-form-item label="工种:" prop="searchWorkType">
                <el-select
                  collapse-tags
                  v-model="searchWorkType"
                  placeholder="选择工种"
                >
                  <el-option
                    v-for="(r, index) in AllWorkTypes"
                    :key="index"
                    :label="r.name"
                    :value="r.id"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <!-- 性别 -->
            <el-col :span="8">
              <el-form-item label="性别:" prop="sex">
                <el-radio-group v-model="searchDate.sex">
                  <el-radio label="男">男</el-radio>
                  <el-radio label="女">女</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
            <!-- 按钮 -->
            <el-col :span="5">
              <el-button
                size="small"
                icon="el-icon-close"
                @click="handleAdvanceCancel"
                >取消</el-button
              >
              <el-button
                size="small"
                icon="el-icon-search"
                type="primary"
                @click="searchHandler()"
                >搜索</el-button
              >
            </el-col>
          </el-row>
        </el-form>
      </div>
    </transition>

    <!-- 工人显示 -->
    <el-card
      v-show="worker"
      class="box-card"
      v-for="item of worker"
      :key="item.id"
      :index="item.id"
      style="margin-top: 10px"
    >
      <div slot="header" class="clearfix">
        <span
          >姓名：{{ item.name }}
          <el-divider
            style="margin: 0 20px 0 20px; font-weight: 800"
            direction="vertical"
          ></el-divider>

          电话号：{{ item.phone }}</span
        >
        <el-button
          style="float: right; padding: 3px 0"
          type="text"
          @click="opensendMsgDialog(item)"
          >发消息</el-button
        >
      </div>
      <div>
        <!-- 地址 -->
        <div>
          <span style="margin-right: 30px">常住地:{{ item.nowAddress }}</span>
          <span>意向地:{{ item.address }}</span>
        </div>

        <!-- 其他信息 -->
        <div>
          年龄:{{ item.age }}
          <el-divider
            style="margin: 0 20px 0 20px; font-weight: 800"
            direction="vertical"
          ></el-divider>
          性别：{{ item.sex }}
          <el-divider
            style="margin: 0 20px 0 20px; font-weight: 800"
            direction="vertical"
          ></el-divider>
          工种：
          <span v-if="item.workTypeList.length > 0">
            <el-tag
              v-for="list of item.workTypeList"
              :key="list.id"
              :index="list.id"
            >
              {{ list.name }}
            </el-tag>
          </span>
          <span v-else>
            <el-tag> 暂无 </el-tag>
          </span>
        </div>

        <!-- 学历和邮箱 -->
        <div>
          <span v-if="item.education">学历：{{ item.education }}</span>
          <span v-else><el-tag> 暂无信息 </el-tag></span>
          <el-divider
            style="margin: 0 20px 0 20px; font-weight: 800"
            direction="vertical"
          ></el-divider>
          <span v-if="item.email">邮箱：{{ item.email }}</span>
          <span v-else><el-tag> 暂无信息 </el-tag></span>
        </div>
      </div>
    </el-card>
    <!-- 分页控件 -->
    <div style="display: flex; justify-content: center">
      <el-pagination
        background
        layout="sizes, prev, pager, next, ->, total"
        :total="total"
        :hide-on-single-page="pageFlag"
        :page-sizes="[5, 10, 20]"
        :page-size="size"
        @current-change="currentChange"
        @size-change="sizeChange"
      >
      </el-pagination>
    </div>

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
            <el-form-item label="收信人:" prop="worker.name">
              <el-input
                disabled
                v-model="sendMsgDate.worker.name"
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

          <!-- 选择工地 -->
          <el-form-item label="工地:" prop="siteId">
            <el-select
              v-model="sendMsgDate.siteId"
              size="mini"
              style="width: 200px"
              placeholder="请选择工地以邀请工人"
              prefix-icon="el-icon-edit"
            >
              <el-option
                :label="site.name"
                :value="site.id"
                v-for="site of allSites"
                :key="site.id"
                :index="site.id"
              ></el-option>
            </el-select>
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
  name: "FindWorker",
  data() {
    return {
      company: JSON.parse(window.sessionStorage.getItem("company")),
      //搜索数据
      searchDate: {
        name: null,
        phone: null,
        email: null,
        nowAddress: null,
        address: null,
        education: null,
        sex: null,
        state: 1,
        age: 0,
      },
      searchWorkType: null,

      //分页
      total: 0,
      loading: false,
      currentPage: 1,
      size: 5,
      pageFlag: true,

      //发送消息规则
      Msgrules: {
        "worker.name": [
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
        siteId: null,
        type: 1,
        state: 0,
        worker: { name: null },
      },

      //公司属下的工地
      allSites: [],

      //发送消息弹窗
      dialogsendMsg: false,

      //所有工种
      AllWorkTypes: [],

      // 高级搜索显示
      showAdvanceSearch: false,

      //规则
      rules: {
        phone: [
          { min: 11, max: 11, message: "请输入规范的电话号", trigger: "blur" },
        ],
        age: [
          { type: "number", message: "年龄必须为数字值" },
          {
            pattern: /^([1-9]\d?|100)$/,
            message: "请至少输入1",
            trigger: "blur",
          },
        ],
      },

      //学历
      Alleducation: ["本科", "专科", "小学", "初中", "高中", "硕士", "博士"],

      //搜索到的数据
      worker: null,
    };
  },
  mounted() {
    this.initWorkTypes();
    this.initSites();
    this.$emit("initMessage");
  },
  methods: {
    // 高级搜索取消
    handleAdvanceCancel() {
      this.searchDate = {
        name: null,
        phone: null,
        email: null,
        nowAddress: null,
        address: null,
        education: null,
        sex: null,
        state: 1,
        age: 0,
      };
      this.searchWorkType = null;
    },

    //获取所有工种
    initWorkTypes() {
      this.getRequest("/worktype/").then((resp) => {
        if (resp) {
          this.AllWorkTypes = resp;
        }
      });
    },

    // 处理分页搜索
    searchHandler(type) {
      if (type == null || type != "page") {
        this.currentPage = 1;
      }
      let workType = 0;
      if (this.searchWorkType != null) {
        workType = this.searchWorkType;
      }
      this.postRequest(
        "/worker/search/" + this.currentPage + "/" + this.size + "/" + workType,
        this.searchDate
      ).then((resp) => {
        if (resp) {
          console.log(resp);
          this.worker = resp.data;
          this.total = resp.total;
          this.pageFlag = false;
        }
      });
    },

    //当前页改变
    currentChange(currentPage) {
      console.log("currentpage:" + currentPage);
      this.currentPage = currentPage;
      // 修改员工展示数据
      this.searchHandler("page");
    },

    //每页条数改变时
    sizeChange(pageSize) {
      this.size = pageSize;
      // 修改员工展示数据
      this.searchHandler("page");
    },

    //获取公司属下招聘中的工地列表
    initSites() {
      this.getRequest("/company/recuritySites/" + this.company.id).then(
        (resp) => {
          if (resp) {
            this.allSites = resp;
            console.log("公司招聘中的工地列表", resp);
          }
        }
      );
    },

    //关闭发送消息弹窗
    closehandler() {
      this.dialogsendMsg = false;
    },

    //打开发送消息弹窗
    opensendMsgDialog(item) {
      this.dialogsendMsg = true;
      this.sendMsgDate.worker = item;
      this.sendMsgDate.receiver = item.id;
      this.sendMsgDate.sender = this.company.id;
    },

    //发送消息
    sendMsgHandle(form) {
      this.$refs[form].validate((valid) => {
        if (valid) {
          this.postRequest("/companymailbox/send", this.sendMsgDate).then(
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
                  siteId: null,
                  type: 1,
                  state: 0,
                  worker: { name: null },
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
/* 可以设置不同的进入和离开动画 */
/* 设置持续时间和动画函数 */
.slide-fade-enter-active {
  transition: all 0.3s ease;
}
.slide-fade-leave-active {
  transition: all 0.3s cubic-bezier(1, 0.5, 0.8, 1);
}
.slide-fade-enter, .slide-fade-leave-to
/* .slide-fade-leave-active for below version 2.1.8 */ {
  transform: translateX(10px);
  opacity: 0;
}
</style>