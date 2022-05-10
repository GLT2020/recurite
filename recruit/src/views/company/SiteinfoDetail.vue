<template>
  <div>
    <h1>工地具体信息</h1>
    <!-- 工地信息 -->
    <el-descriptions title="">
      <el-descriptions-item label="工地名">
        {{ siteDetails.name }}
      </el-descriptions-item>
      <el-descriptions-item label="负责人">
        {{ siteDetails.userName }}
      </el-descriptions-item>
      <el-descriptions-item label="手机号">
        {{ siteDetails.phone }}
      </el-descriptions-item>
      <el-descriptions-item label="开工时间">
        {{ siteDetails.startTime }}
      </el-descriptions-item>
      <el-descriptions-item label="预计工期">
        {{ siteDetails.expectTime }}
      </el-descriptions-item>
      <el-descriptions-item label="工地地址">
        {{ siteDetails.address }}
      </el-descriptions-item>
      <el-descriptions-item label="工地简述">
        <span v-if="siteDetails.remark == null">无</span>
        <span v-else>{{ siteDetails.remark }}</span>
      </el-descriptions-item>
      <el-descriptions-item label="工地状态">
        <template v-if="siteDetails.siteState == 1">
          <el-tag v-show="siteDetails.recuritState == 1">招聘中</el-tag>
          <el-tooltip
            class="item"
            effect="dark"
            content="工地处于暂不招聘时，所有招聘信息都为暂不招聘"
            placement="top"
          >
            <el-tag type="warning" v-show="siteDetails.recuritState == 0"
              >暂不招聘</el-tag
            >
          </el-tooltip>
        </template>
        <template v-else>
          <el-tag type="info">已完工</el-tag>
        </template>
      </el-descriptions-item>
    </el-descriptions>

    <!-- 按钮 -->
    <div style="margin: 10px 0 10px 0">
      <!-- 添加招聘信息 -->
      <el-button
        type="primary"
        @click="openhandler01"
        v-show="siteDetails.siteState != 0 && siteDetails.recuritState != 0"
        >添加招聘信息</el-button
      >
      <!-- 查看招聘中的信息 -->
      <!-- <el-button type="primary" @click="showingMsg">查看招聘信息</el-button> -->
      <!-- 查看暂不招聘信息 -->
      <!-- <el-button type="primary" @click="shownotMsg">查看暂不招聘信息</el-button> -->
    </div>

    <!-- 展示招聘信息卡片 -->
    <el-card
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
          @click="openworkerMsgHandler(item)"
          >查看应聘</el-button
        >

        <el-button
          v-show="siteDetails.siteState != 0"
          style="float: right; padding: 3px 0"
          type="text"
          @click="openUpdateHandle(item)"
          >修改招聘</el-button
        >
        <el-button
          style="float: right; padding: 3px 0"
          type="text"
          @click="deleteRecruit(item)"
          >删除招聘</el-button
        >
      </div>
      <div>
        <span>{{ item.salaryType.name }} : {{ item.salary }}</span>

        <span style="margin-left: 30px">招聘人数:{{ item.number }}</span>
      </div>
    </el-card>

    <!-- 添加招聘弹窗 -->
    <el-dialog title="添加招聘信息" :visible.sync="dialogVisible01" width="50%">
      <el-form
        ref="addrecuritMessage"
        :model="addrecuritMessage"
        :rules="rules"
      >
        <!-- 第一行 -->
        <el-row>
          <!-- 工种名 -->
          <el-col :span="12">
            <el-form-item label="招聘工种:" prop="wtypeId">
              <el-select
                v-model="addrecuritMessage.wtypeId"
                placeholder="工种"
                size="mini"
                style="width: 150px"
              >
                <el-option
                  v-for="item in allWorkType"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <!-- 招聘人数 -->
          <el-col :span="12">
            <el-form-item label="人数:" prop="number">
              <el-input
                type="number"
                v-model.number="addrecuritMessage.number"
                size="mini"
                style="width: 180px"
                placeholder="请输入招聘人数"
                prefix-icon="el-icon-edit"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <!-- 第二行 -->
        <el-row>
          <!-- 薪资种类 -->
          <el-col :span="12">
            <el-form-item label="薪资种类:" prop="stypeId">
              <el-select
                v-model="addrecuritMessage.stypeId"
                placeholder="请选择薪资种类"
                size="mini"
                style="width: 150px"
              >
                <el-option
                  v-for="item in allSalaryType"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <!-- 薪资 -->
          <el-col :span="12">
            <el-form-item label="薪资:" prop="salary">
              <el-input
                v-model="addrecuritMessage.salary"
                size="mini"
                style="width: 180px"
                placeholder="请输入薪资"
                prefix-icon="el-icon-edit"
              >
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <!-- 第三行 -->
        <el-row>
          <el-col :span="12">
            <!-- 是否找人 -->
            <el-form-item label="是否招聘">
              <el-switch
                v-model="addrecuritMessage.state"
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
          <!-- 招聘信息简述 -->
          <el-col :span="21">
            <el-form-item label="招聘信息简述:" prop="remark">
              <el-input
                type="textarea"
                v-model="addrecuritMessage.remark"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <!--第五行按钮 -->
        <el-row>
          <el-col :span="21">
            <el-form-item style="display: flex; justify-content: flex-end">
              <el-button
                style="margin-right: 10px"
                @click="closehandler('addrecuritMessage')"
                >取 消</el-button
              >
              <el-button
                type="primary"
                @click="insertrecuritMessage('addrecuritMessage')"
                >添 加</el-button
              >
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </el-dialog>

    <!-- 工人应聘信息 -->
    <el-dialog title="应聘工人" :visible.sync="dialogworkerMsg" width="90%">
      <el-table border :data="allworkMsg" style="height: 100px" height="300px">
        <el-table-column
          property="name"
          label="姓名"
          width="100"
        ></el-table-column>
        <el-table-column
          property="phone"
          label="电话号"
          width="130"
        ></el-table-column>
        <el-table-column
          property="appTime"
          label="投递时间"
          width="100"
        ></el-table-column>
        <el-table-column
          property="email"
          label="邮箱"
          width="160"
        ></el-table-column>
        <el-table-column
          property="remark"
          label="个人简述"
          width="180"
        ></el-table-column>
        <el-table-column
          property="education"
          label="学历"
          width="100"
        ></el-table-column>
        <el-table-column label="工种" width="100" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-tag
              v-for="item in scope.row.workTypeList"
              :key="item.id"
              :index="item.id"
              >{{ item.name }}</el-tag
            >
          </template>
        </el-table-column>
        <!-- <el-table-column property="address" label="意向地址"></el-table-column> -->
        <el-table-column fixed="right" label="操作" width="100">
          <template slot-scope="scope">
            <el-button
              @click="opensendMsgDialog(scope.row)"
              type="text"
              size="small"
              >发送消息</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>

    <!-- 修改招聘弹窗 -->
    <el-dialog title="修改招聘信息" :visible.sync="dialogVisible02" width="50%">
      <el-form
        ref="updaterecuritMessage"
        :model="updaterecuritMessage"
        :rules="rules"
      >
        <!-- 第一行 -->
        <el-row>
          <!-- 工种名 -->
          <el-col :span="12">
            <el-form-item label="招聘工种:" prop="wtypeId">
              <el-select
                v-model="updaterecuritMessage.wtypeId"
                placeholder="工种"
                size="mini"
                style="width: 150px"
              >
                <el-option
                  v-for="item in allWorkType"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <!-- 招聘人数 -->
          <el-col :span="12">
            <el-form-item label="人数:" prop="number">
              <el-input
                type="number"
                v-model.number="updaterecuritMessage.number"
                size="mini"
                style="width: 180px"
                placeholder="请输入招聘人数"
                prefix-icon="el-icon-edit"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <!-- 第二行 -->
        <el-row>
          <!-- 薪资种类 -->
          <el-col :span="12">
            <el-form-item label="薪资种类:" prop="stypeId">
              <el-select
                v-model="updaterecuritMessage.stypeId"
                placeholder="请选择薪资种类"
                size="mini"
                style="width: 150px"
              >
                <el-option
                  v-for="item in allSalaryType"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <!-- 薪资 -->
          <el-col :span="12">
            <el-form-item label="薪资:" prop="salary">
              <el-input
                v-model="updaterecuritMessage.salary"
                size="mini"
                style="width: 180px"
                placeholder="请输入薪资"
                prefix-icon="el-icon-edit"
              >
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <!-- 第三行 -->
        <el-row v-show="siteDetails.recuritState == 1">
          <!-- 是否找人 -->
          <el-col :span="12">
            <el-form-item label="是否招聘">
              <el-switch
                v-model="updaterecuritMessage.state"
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
          <!-- 招聘信息简述 -->
          <el-col :span="21">
            <el-form-item label="招聘信息简述:" prop="remark">
              <el-input
                type="textarea"
                v-model="updaterecuritMessage.remark"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <!--第五行按钮 -->
        <el-row>
          <el-col :span="21">
            <el-form-item style="display: flex; justify-content: flex-end">
              <el-button style="margin-right: 10px" @click="closehandler()"
                >取 消</el-button
              >
              <el-button
                type="primary"
                @click="updatehandle('updaterecuritMessage')"
                >修 改</el-button
              >
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
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
  name: "SiteinfoDetail",
  data() {
    return {
      company: JSON.parse(window.sessionStorage.getItem("company")),
      //工地id
      id: this.$route.params.id,
      siteDetails: {},
      //添加招聘信息
      addrecuritMessage: {
        number: 0,
        salary: null,
        stypeId: null,
        wtypeId: null,
        createTime: null,
        reamrk: null,
        state: 1,
      },
      //修改招聘消息
      updaterecuritMessage: {
        id: 0,
        number: 0,
        salary: null,
        stypeId: null,
        wtypeId: null,
        createTime: null,
        reamrk: null,
        state: 1,
      },

      //是否招聘
      RecruitState: 0,

      rules: {
        wtypeId: [{ required: true, message: "请选择工种名", trigger: "blur" }],
        number: [
          { required: true, message: "请输入招聘人数", trigger: "blur" },
          { type: "number", message: "招聘人数必须为数字值" },
          {
            pattern: /^([1-9]\d?|100)$/,
            message: "请至少输入1人",
            trigger: "blur",
          },
        ],
        stypeId: [
          { required: true, message: "请选择薪资种类", trigger: "blur" },
        ],
        salary: [{ required: true, message: "请输入薪水", trigger: "blur" }],
      },

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
        siteId: 0,
        recuritId: 0,
        type: 1,
        state: 0,
        worker: { name: null },
      },

      //发送消息时的工种id
      worktypeId: 0,

      //弹窗
      dialogVisible01: false,
      //修改招聘弹窗
      dialogVisible02: false,

      //工资种类
      allWorkType: [],
      //薪资种类
      allSalaryType: [],

      //所有的招聘信息
      allRecuritMsg: [],
      //展示的招聘信息
      showRecuritMsg: [],

      //当前招聘信息的所有工人信息
      allworkMsg: [],

      //展示的工人信息
      showworkMsg: [],

      //工人应聘弹窗
      dialogworkerMsg: false,

      //发送消息弹窗
      dialogsendMsg: false,
    };
  },
  mounted() {
    this.initSite();
    this.initWorkType();
    this.initSalaryType();
    this.$emit("initMessage");
  },
  methods: {
    //获取当前工地数据
    initSite() {
      this.getRequest("/site/" + this.id).then((resp) => {
        if (resp) {
          console.log("工地的详细数据->>>>", resp);
          this.siteDetails = resp;
          this.initAllRecuritMsg();
        }
      });
    },

    //获取工种类
    initWorkType() {
      this.getRequest("/worktype/").then((resp) => {
        if (resp) {
          this.allWorkType = resp;
        }
      });
    },
    //获取薪资种类
    initSalaryType() {
      this.getRequest("/salaryType/").then((resp) => {
        if (resp) {
          this.allSalaryType = resp;
        }
      });
    },

    //初始化招聘信息列表
    initAllRecuritMsg() {
      this.allRecuritMsg = this.siteDetails.recuritMessageList;
    },

    //更新展示数据
    initShowMsghandle() {
      this.getRequest("/recuritMessage/site/" + this.id).then((resp) => {
        if (resp) {
          console.log("更新所有显示的数据", this.allRecuritMsg);
          this.allRecuritMsg = resp;
          this.showingMsg();
        }
      });
    },

    //打开添加弹窗
    openhandler01() {
      this.dialogVisible01 = true;
    },

    //关闭弹窗
    closehandler() {
      this.addrecuritMessage = { state: 1 };
      this.dialogVisible01 = false;
      this.dialogVisible02 = false;
      this.dialogsendMsg = false;
      // this.sendMsgDate = {};
    },

    //添加招聘信息
    insertrecuritMessage(addrecuritMessage) {
      this.$refs[addrecuritMessage].validate((valid) => {
        if (valid) {
          this.postRequest(
            "/recuritMessage/" + this.id,
            this.addrecuritMessage
          ).then((resp) => {
            if (resp) {
              this.dialogVisible01 = false;
              this.addrecuritMessage = {};
              this.initShowMsghandle();
            }
          });
        } else {
          return false;
        }
      });
    },

    //查看所有信息
    showingMsg() {
      this.showRecuritMsg = [];
      // Object.assign(this.showRecuritMsg, this.allRecuritMsg);
      console.log("显示查看招聘信息的>>>>所有的招聘信息", this.allRecuritMsg);
      this.allRecuritMsg.forEach((item) => {
        this.showRecuritMsg.push(item);
      });
    },

    // 查看暂不招聘信息
    shownotMsg() {
      this.showRecuritMsg = [];
      this.allRecuritMsg.forEach((item) => {
        if (item.state == 0) {
          this.showRecuritMsg.push(item);
        }
      });
    },

    //打开应聘信息弹窗
    openworkerMsgHandler(item) {
      this.dialogworkerMsg = true;
      this.changeMsg(item);
    },

    //激活查看招聘信息的应聘工人
    changeMsg(item) {
      console.log("激活招聘信息获取的id", item.id);
      this.count = 5;

      //设置当前查看的招聘信息id（方便发送消息）
      this.worktypeId = item.workType.id;

      this.getRequest("/recuritMessage/" + item.id).then((resp) => {
        if (resp) {
          console.log("招聘信息列表", resp);
          this.allworkMsg = resp;
        }
      });
    },

    //打开发送消息弹窗
    opensendMsgDialog(item) {
      this.dialogsendMsg = true;
      this.sendMsgDate.worker = item;
      this.sendMsgDate.receiver = item.id;
      this.sendMsgDate.sender = this.company.id;
      this.sendMsgDate.siteId = this.siteDetails.id;
      this.sendMsgDate.worktypeId = this.worktypeId;
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
                  siteId: 0,
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

    //打开修改信息弹窗
    openUpdateHandle(item) {
      console.log("修改信息的id", item);
      this.updaterecuritMessage = item;
      this.dialogVisible02 = true;
    },

    //修改招聘按钮
    updatehandle(updaterecuritMessage) {
      console.log("修改信息的id", updaterecuritMessage);
      this.$refs[updaterecuritMessage].validate((valid) => {
        if (valid) {
          this.putRequest("/recuritMessage/", this.updaterecuritMessage).then(
            (resp) => {
              if (resp) {
                this.dialogVisible02 = false;
              }
            }
          );
        } else {
          return false;
        }
      });
    },

    //删除招聘
    deleteRecruit(item) {
      this.$confirm("此操作将删除该招聘信息, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.deleteRequest("/recuritMessage/" + item.id).then((resp) => {
            if (resp) {
              this.initShowMsghandle();
            }
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
  },
};
</script>

<style>
</style>