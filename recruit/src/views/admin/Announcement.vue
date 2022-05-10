<template>
  <div>
    <h1>公告管理</h1>
    <div style="margin-top: 20px; margin-bottom: 10px">
      <!-- 添加公告 -->
      <el-button type="primary" @click="openhandler01">添加公告</el-button>
    </div>
    <!-- 表单 -->
    <div>
      <el-table v-loading="loading" :data="allMsg" border style="width: 100%">
        <el-table-column prop="topic" label="主题" width="220">
        </el-table-column>
        <el-table-column
          show-overflow-tooltip
          prop="content"
          label="内容"
          width="220"
        >
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180">
        </el-table-column>

        <!-- 操作 -->
        <el-table-column fixed="right" label="操作">
          <template slot-scope="scope">
            <el-button
              style="margin-right: 10px"
              @click="updateHandle(scope.row)"
              type="success"
              size="small"
              >修改</el-button
            >
            <el-popconfirm
              confirm-button-text="确 定"
              cancel-button-text="不 了"
              icon="el-icon-info"
              icon-color="red"
              title="确定删除此公告吗？"
              @confirm="deleteHandle(scope.row)"
            >
              <el-button type="danger" size="small" slot="reference"
                >删除</el-button
              >
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页控件 -->
      <div style="display: flex; justify-content: center">
        <el-pagination
          background
          layout="sizes, prev, pager, next, ->, total"
          :total="total"
          :hide-on-single-page="pageFlag"
          :page-sizes="[10, 20]"
          :page-size="size"
          @current-change="currentChange"
          @size-change="sizeChange"
        >
        </el-pagination>
      </div>
    </div>

    <!-- 添加公告弹窗 -->
    <el-dialog title="添加" :visible.sync="dialogVisible01" width="50%">
      <el-form ref="addMsg" :model="addMsg" :rules="rules">
        <!-- 第一行 -->
        <el-row>
          <!-- 用户名 -->
          <el-col :span="20">
            <el-form-item label="主题:" prop="topic">
              <el-input
                v-model="addMsg.topic"
                size="mini"
                style="width: 380px"
                placeholder="请输入主题"
                prefix-icon="el-icon-edit"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!-- 内容 -->
          <el-col :span="20">
            <el-form-item label="内容:" prop="content">
              <el-input
                type="textarea"
                v-model="addMsg.content"
                style="width: 380px"
                placeholder="请输入内容"
                prefix-icon="el-icon-edit"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="21">
            <el-form-item style="display: flex; justify-content: flex-end">
              <el-button @click="closehandler">取 消</el-button>
              <el-button type="primary" @click="insertMsg('addMsg')"
                >添 加</el-button
              >
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </el-dialog>

    <!-- 修改公告弹窗 -->
    <el-dialog title="修改" :visible.sync="dialogVisible02" width="50%">
      <el-form ref="updateMsg" :model="updateMsg" :rules="rules">
        <!-- 第一行 -->
        <el-row>
          <!-- 用户名 -->
          <el-col :span="20">
            <el-form-item label="主题:" prop="topic">
              <el-input
                v-model="updateMsg.topic"
                size="mini"
                style="width: 380px"
                placeholder="请输入主题"
                prefix-icon="el-icon-edit"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!-- 内容 -->
          <el-col :span="20">
            <el-form-item label="内容:" prop="content" show-overflow-tooltip>
              <el-input
                type="textarea"
                v-model="updateMsg.content"
                size="mini"
                style="width: 380px"
                placeholder="请输入内容"
                prefix-icon="el-icon-edit"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="21">
            <el-form-item style="display: flex; justify-content: flex-end">
              <el-button @click="closehandler">取 消</el-button>
              <el-button type="primary" @click="updateMsgHandle('updateMsg')"
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
  name: "Announcement",
  data() {
    return {
      allMsg: [],
      addMsg: { name: null },
      updateMsg: {},

      //分页
      total: 0,
      loading: false,
      currentPage: 1,
      size: 10,
      pageFlag: true,

      loading: false,

      //添加弹窗
      dialogVisible01: false,
      //修改弹窗
      dialogVisible02: false,

      rules: {
        topic: [{ required: true, message: "请输入主题", trigger: "blur" }],
        content: [{ required: true, message: "请输入内容", trigger: "blur" }],
      },
    };
  },
  mounted() {
    this.initMsg();
  },
  methods: {
    // 获取所有的系统公告
    initMsg(type) {
      this.loading = true;
      if (type == null || type != "page") {
        this.currentPage = 1;
      }
      this.getRequest("/sysmessage/" + this.currentPage + "/" + this.size).then(
        (resp) => {
          if (resp) {
            console.log("获取到的系统公告", resp);

            this.allMsg = resp.data;
            this.total = resp.total;
            this.pageFlag = false;
            this.loading = false;
          }
        }
      );
    },

    //当前页改变
    currentChange(currentPage) {
      console.log("currentpage:" + currentPage);
      this.currentPage = currentPage;
      // 修改展示数据
      this.initMsg("page");
    },

    //每页条数改变时
    sizeChange(pageSize) {
      this.size = pageSize;
      // 修改展示数据
      this.initMsg("page");
    },

    //打开添加
    openhandler01() {
      this.dialogVisible01 = true;
    },

    //取消
    closehandler() {
      this.addMsg = {};
      this.updateMsg = {};
      this.dialogVisible01 = false;
      this.dialogVisible02 = false;
    },

    insertMsg(form) {
      this.$refs[form].validate((valid) => {
        if (valid) {
          this.postRequest("/sysmessage/", this.addMsg).then((resp) => {
            if (resp) {
              this.initMsg("page");
              this.dialogVisible01 = false;
              this.addMsg = {};
            }
          });
        } else {
          return false;
        }
      });
    },

    //修改弹窗
    updateHandle(item) {
      this.updateMsg = item;

      this.dialogVisible02 = true;
    },

    // 修改用户按钮
    updateMsgHandle(form) {
      this.$refs[form].validate((valid) => {
        if (valid) {
          this.putRequest("/sysmessage/", this.updateMsg).then((resp) => {
            if (resp) {
              this.initMsg("page");
              this.dialogVisible02 = false;
              this.updateMsg = {};
            }
          });
        } else {
          return false;
        }
      });
    },

    //删除
    deleteHandle(item) {
      // console.log("触发删除", item);
      this.deleteRequest("/sysmessage/" + item.id).then((resp) => {
        if (resp) {
          this.initMsg("page");
        }
      });
    },
  },
};
</script>

<style>
</style>