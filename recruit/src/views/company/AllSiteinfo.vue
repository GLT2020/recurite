<template>
  <div>
    <h1>工地列表</h1>
    <div style="margin-top: 20px; margin-bottom: 10px">
      <!-- 添加工地 -->
      <el-button type="primary" @click="openhandler01">添加工地</el-button>
      <!-- 查看未完成工地 -->
      <el-button type="primary" @click="getunSites()">查看进行中工地</el-button>
      <!-- 查看完成工地 -->
      <el-button type="primary" @click="getcomSites()"
        >查看已完成工地</el-button
      >
    </div>

    <!-- 展示工地卡片 -->
    <el-card
      class="box-card"
      v-for="(item, index) of showsites"
      :key="index"
      :index="item.id"
      style="margin-top: 5px"
    >
      <!-- 工地信息 -->
      <div slot="header" class="clearfix">
        <span>{{ item.name }}</span>
        <span style="margin-left: 10px" v-if="item.siteState == 1">
          <el-tag style="margin-right: 10px">施工中</el-tag>
          <el-tag v-if="item.recuritState == 1">招聘中</el-tag>
          <el-tag v-else type="warning">不招聘</el-tag>
        </span>
        <span style="margin-left: 10px" v-else>
          <el-tag type="info">已完工</el-tag>
        </span>
        <!-- 操作按钮 -->
        <el-button
          style="float: right; padding: 3px 0"
          type="text"
          @click="findDetails(item.id)"
          >查看</el-button
        >
        <el-button
          style="float: right; padding: 3px 0; margin: 0 10px 0 10px"
          type="text"
          @click="openhandler02(item)"
          >设置</el-button
        >

        <el-button
          v-if="item.siteState == 0"
          style="float: right; padding: 3px 0"
          type="text"
          @click="deleteDetails(item)"
          >删除</el-button
        >
        <!-- <el-button
          style="float: right; padding: 3px 0"
          type="text"
          @click="deleteDetails(item.id)"
          >删除</el-button
        > -->
      </div>
      <div>{{ item.userName }} {{ item.phone }}</div>
    </el-card>

    <!-- 添加工地弹窗 -->
    <el-dialog title="添加工地" :visible.sync="dialogVisible01" width="70%">
      <el-form ref="addSite" :model="addsite" :rules="rules">
        <!-- 第一行 -->
        <el-row>
          <!-- 工地名 -->
          <el-col :span="8">
            <el-form-item label="工地名:" prop="name">
              <el-input
                v-model="addsite.name"
                size="mini"
                style="width: 180px"
                placeholder="请输入工地名"
                prefix-icon="el-icon-edit"
              ></el-input>
            </el-form-item>
          </el-col>
          <!-- 负责人名 -->
          <el-col :span="8">
            <el-form-item label="负责人名:" prop="name">
              <el-input
                v-model="addsite.userName"
                size="mini"
                style="width: 180px"
                placeholder="请输入负责人名"
                prefix-icon="el-icon-edit"
              ></el-input>
            </el-form-item>
          </el-col>

          <!-- 电话号 -->
          <el-col :span="8">
            <el-form-item label="电话号:" prop="phone">
              <el-input
                v-model="addsite.phone"
                size="mini"
                style="width: 180px"
                placeholder="请输入电话号"
                prefix-icon="el-icon-edit"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <!-- 第二行 -->
        <el-row>
          <!-- 工地地址 -->
          <el-col :span="14">
            <el-form-item label="工地地址:" prop="address">
              <el-input
                v-model="addsite.address"
                size="mini"
                style="width: 350px"
                placeholder="请输入联系地址"
                prefix-icon="el-icon-edit"
              ></el-input>
            </el-form-item>
          </el-col>
          <!-- 开工日期 -->
          <el-col :span="8">
            <el-form-item label="开工日期:" prop="startTime">
              <el-date-picker
                v-model="addsite.startTime"
                type="date"
                size="mini"
                style="width: 150px"
                value-format="yyyy-MM-dd"
                placeholder="开工日期"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <!-- 第三行 -->
        <el-row>
          <!-- 预计工期 -->
          <el-col :span="10">
            <el-form-item label="预计工期:" prop="tTime">
              <el-input
                v-model="addsite.expectTime"
                size="mini"
                style="width: 180px"
                placeholder="请输入预计工期"
                prefix-icon="el-icon-edit"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <!-- 是否找人 -->
            <el-form-item label="是否招人">
              <el-switch
                v-model="addsite.recuritState"
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
          <!-- 工地简述 -->
          <el-col :span="21">
            <el-form-item label="工地简述:" prop="remark">
              <el-input type="textarea" v-model="addsite.remark"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="21">
            <el-form-item style="display: flex; justify-content: flex-end">
              <el-button @click="closehandler">取 消</el-button>
              <el-button type="primary" @click="insertsite('addSite')"
                >添 加</el-button
              >
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </el-dialog>

    <!-- 修改工地弹窗 -->
    <el-dialog title="修改工地" :visible.sync="dialogVisible02" width="70%">
      <el-form ref="updateSite" :model="updateSite" :rules="rules">
        <!-- 第一行 -->
        <el-row>
          <!-- 工地名 -->
          <el-col :span="8">
            <el-form-item label="工地名:" prop="name">
              <el-input
                v-model="updateSite.name"
                size="mini"
                style="width: 180px"
                placeholder="请输入工地名"
                prefix-icon="el-icon-edit"
              ></el-input>
            </el-form-item>
          </el-col>
          <!-- 负责人名 -->
          <el-col :span="8">
            <el-form-item label="负责人名:" prop="name">
              <el-input
                v-model="updateSite.userName"
                size="mini"
                style="width: 180px"
                placeholder="请输入负责人名"
                prefix-icon="el-icon-edit"
              ></el-input>
            </el-form-item>
          </el-col>

          <!-- 电话号 -->
          <el-col :span="8">
            <el-form-item label="电话号:" prop="phone">
              <el-input
                v-model="updateSite.phone"
                size="mini"
                style="width: 180px"
                placeholder="请输入电话号"
                prefix-icon="el-icon-edit"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <!-- 第二行 -->
        <el-row>
          <!-- 工地地址 -->
          <el-col :span="14">
            <el-form-item label="工地地址:" prop="address">
              <el-input
                v-model="updateSite.address"
                size="mini"
                style="width: 350px"
                placeholder="请输入联系地址"
                prefix-icon="el-icon-edit"
              ></el-input>
            </el-form-item>
          </el-col>
          <!-- 开工日期 -->
          <el-col :span="8">
            <el-form-item label="开工日期:" prop="startTime">
              <el-date-picker
                v-model="updateSite.startTime"
                type="date"
                size="mini"
                style="width: 150px"
                value-format="yyyy-MM-dd"
                placeholder="开工日期"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <!-- 第三行 -->
        <el-row>
          <!-- 预计工期 -->
          <el-col :span="10">
            <el-form-item label="预计工期:" prop="expectTime">
              <el-input
                v-model="updateSite.expectTime"
                size="mini"
                style="width: 180px"
                placeholder="请输入预计工期"
                prefix-icon="el-icon-edit"
              ></el-input>
            </el-form-item>
          </el-col>
          <!-- 是否找人 -->
          <el-col :span="6">
            <el-tooltip
              class="item"
              effect="dark"
              content="当不招人时，工人将无法查看工地"
              placement="top"
            >
              <el-form-item label="招人中">
                <el-switch
                  v-model="updateSite.recuritState"
                  active-color="#13ce66"
                  inactive-color="#ff4949"
                  :active-value="1"
                  :inactive-value="0"
                >
                </el-switch>
              </el-form-item>
            </el-tooltip>
          </el-col>
          <!-- 是否完工 -->
          <el-col :span="6">
            <el-tooltip
              class="item"
              effect="dark"
              content="选择完工后，所有的招聘信息将自动停止招聘"
              placement="top"
            >
              <el-form-item label="已完工">
                <el-switch
                  v-model="updateSite.siteState"
                  active-color="#13ce66"
                  inactive-color="#ff4949"
                  :active-value="0"
                  :inactive-value="1"
                >
                </el-switch>
              </el-form-item>
            </el-tooltip>
          </el-col>
        </el-row>
        <!-- 第四行 -->
        <el-row>
          <!-- 工地简述 -->
          <el-col :span="21">
            <el-form-item label="工地简述:" prop="remark">
              <el-input type="textarea" v-model="updateSite.remark"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="21">
            <el-form-item style="display: flex; justify-content: flex-end">
              <el-button @click="closehandler">取 消</el-button>
              <el-button
                type="primary"
                @click="updateDetailsHandle('updateSite')"
                >修 改</el-button
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
  name: "AllSiteinfo",
  data() {
    return {
      //company存进去的时候本身就是个object，所以不用再转换
      company: JSON.parse(window.sessionStorage.getItem("company")),
      allsites: [],
      //用于展示
      showsites: [],

      //当前展示的是完成还是未完成（0为完工 1未完工）
      showFlag: 1,

      //添加工地使用
      addsite: {
        name: null,
        userName: null,
        phone: null,
        address: null,
        createTime: null,
        startTime: null,
        completeTime: null,
        expectTime: null,
        userface: null,
        remark: null,
        recuritState: 1,
        siteState: 0,
      },
      dialogVisible01: false,
      dialogVisible02: false,

      //修改工地使用
      updateSite: {},

      rules: {
        name: [{ required: true, message: "请输入工地名", trigger: "blur" }],
        userName: [
          { required: true, message: "请输入负责人名", trigger: "blur" },
        ],
        phone: [
          { required: true, message: "请输入电话号", trigger: "blur" },
          { min: 11, max: 11, message: "请输入规范的电话号", trigger: "blur" },
        ],
        address: [
          { required: true, message: "请输入工地地址", trigger: "blur" },
        ],
        startTime: [
          { required: true, message: "请输入开工日期", trigger: "blur" },
        ],
        // tTime: [{ required: true, message: "请输入开工日期", trigger: "blur" }],
      },
    };
  },
  mounted() {
    this.initSites();
    this.getunSites();
    this.$emit("initMessage");
  },
  methods: {
    //获取所有工地列表
    initSites() {
      this.getRequest("/company/sites/" + this.company.id).then((resp) => {
        if (resp) {
          // console.log("获取到的所有工地：", resp);
          this.allsites = resp;
          if (this.showFlag == 1) {
            //将进行中的工地进行显示
            this.getunSites();
          } else {
            //将已完工的工地进行显示
            this.getcomSites();
          }

          this.updateSite = {};
          this.dialogVisible02 = false;
        }
      });
    },

    //关闭弹窗
    closehandler() {
      this.addsite = { recuritState: 1, siteState: 1 };
      this.dialogVisible01 = false;
      this.dialogVisible02 = false;
    },

    //添加工地弹窗
    openhandler01() {
      this.dialogVisible01 = true;
    },

    //添加工地
    insertsite(addSite) {
      console.log("输出当前添加工地信息", this.addsite);
      this.$refs[addSite].validate((valid) => {
        if (valid) {
          this.postRequest(
            "/company/createSite/" + this.company.id,
            this.addsite
          ).then((resp) => {
            if (resp) {
              this.initSites();
              this.addsite = {};
              this.dialogVisible01 = false;
            }
          });
        } else {
          return false;
        }
      });
    },

    //获取所有未完工工地
    getunSites() {
      this.showsites = [];
      this.showFlag = 1;
      this.allsites.forEach((item) => {
        // console.log("循环判断的工地信息-》》》》", item);
        if (item.siteState == 1) {
          this.showsites.push(item);
        }
      });
      // console.log("未完成工地列表", this.showsites);
    },

    //查看完成工地
    getcomSites() {
      this.showsites = [];
      this.showFlag = 0;
      this.allsites.forEach((item) => {
        // console.log("循环判断的工地信息-》》》》", item);
        if (item.siteState == 0) {
          this.showsites.push(item);
        }
      });
      // console.log("完成工地列表", this.showsites);
    },

    //跳转查看工地具体信息
    findDetails(sid) {
      this.$router.push({ name: "SiteinfoDetail", params: { id: sid } });
    },

    //打开修改弹窗
    openhandler02(item) {
      this.updateSite = item;
      this.dialogVisible02 = true;
    },

    //修改工地信息
    updateDetailsHandle(form) {
      console.log("修改工地信息：", this.updateSite);
      this.$refs[form].validate((valid) => {
        if (valid) {
          this.putRequest("/site/", this.updateSite).then((resp) => {
            if (resp) {
              this.initSites();
              // console.log("更新的工地状态", this.updateSite);
            }
          });
        } else {
          return false;
        }
      });
    },

    //删除工地信息
    deleteDetails(item) {
      this.$confirm("此操作将删除该工地信息, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.putRequest("/site/deleteSite", item).then((resp) => {
            if (resp) {
              this.initSites();
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