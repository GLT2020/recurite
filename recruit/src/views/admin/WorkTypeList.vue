<template>
  <div>
    <h1>修改工种</h1>
    <div style="margin-top: 20px; margin-bottom: 10px">
      <!-- 添加工地 -->
      <el-button type="primary" @click="openhandler01">添加工种</el-button>
    </div>
    <!-- 表单 -->
    <div>
      <el-table
        v-loading="loading"
        :data="allWorkType"
        border
        style="width: 60%"
      >
        <el-table-column fixed prop="name" label="工种名" width="120">
        </el-table-column>
        <el-table-column prop="state" label="状态" width="120">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.state == 1">使用中</el-tag>
            <el-tag v-else type="warning">禁用中</el-tag>
          </template>
        </el-table-column>

        <!-- 操作 -->
        <el-table-column fixed="right" label="操作">
          <template slot-scope="scope">
            <el-button
              v-show="scope.row.state != 1"
              @click="handleClick(scope.row, 'use')"
              type="success"
              size="small"
              >启用</el-button
            >
            <el-button
              v-show="scope.row.state == 1"
              @click="handleClick(scope.row, 'notuse')"
              type="danger"
              size="small"
              >禁用</el-button
            >
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

    <!-- 添加工种弹窗 -->
    <el-dialog title="添加工种" :visible.sync="dialogVisible01" width="50%">
      <el-form ref="WorkType" :model="WorkType" :rules="rules">
        <!-- 第一行 -->
        <el-row>
          <!-- 工种名 -->
          <el-col :span="11">
            <el-form-item label="工种名:" prop="name">
              <el-input
                v-model="WorkType.name"
                size="mini"
                style="width: 180px"
                placeholder="请输入工种名"
                prefix-icon="el-icon-edit"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <!-- 是否启用 -->
            <el-form-item label="是否启用">
              <el-switch
                v-model="WorkType.state"
                active-color="#13ce66"
                inactive-color="#ff4949"
                :active-value="1"
                :inactive-value="0"
              >
              </el-switch>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="21">
            <el-form-item style="display: flex; justify-content: flex-end">
              <el-button @click="closehandler">取 消</el-button>
              <el-button type="primary" @click="insertworkType('WorkType')"
                >添 加</el-button
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
  name: "WorkTypeList",
  data() {
    return {
      allWorkType: [],
      WorkType: { name: null, state: 1 },

      //分页
      total: 0,
      loading: false,
      currentPage: 1,
      size: 10,
      pageFlag: true,

      loading: false,

      dialogVisible01: false,

      rules: {
        name: [{ required: true, message: "请输入工种名", trigger: "blur" }],
      },
    };
  },
  mounted() {
    this.initWorkType();
  },
  methods: {
    // 获取所有的工种种类
    initWorkType(type) {
      this.loading = true;
      if (type == null || type != "page") {
        this.currentPage = 1;
      }
      this.getRequest(
        "/worktype/admin/" + this.currentPage + "/" + this.size
      ).then((resp) => {
        if (resp) {
          console.log("获取到的所有工种信息", resp);

          this.allWorkType = resp.data;
          this.total = resp.total;
          this.pageFlag = false;
          this.loading = false;
        }
      });
    },

    //当前页改变
    currentChange(currentPage) {
      console.log("currentpage:" + currentPage);
      this.currentPage = currentPage;
      // 修改员工展示数据
      this.initWorkType("page");
    },

    //每页条数改变时
    sizeChange(pageSize) {
      this.size = pageSize;
      // 修改员工展示数据
      this.initWorkType("page");
    },

    //启用或者禁用
    handleClick(WorkType, flag) {
      let use = flag == "notuse" ? 0 : 1;
      this.putRequest("/worktype/admin/user/" + use, WorkType).then((resp) => {
        if (resp) {
          this.initWorkType("page");
        }
      });
    },

    //打开添加
    openhandler01() {
      this.dialogVisible01 = true;
    },

    //取消
    closehandler() {
      (this.WorkType = {}), (this.dialogVisible01 = false);
    },

    insertworkType(form) {
      this.$refs[form].validate((valid) => {
        if (valid) {
          this.postRequest("/worktype/admin/addWorkType", this.WorkType).then(
            (resp) => {
              if (resp) {
                this.initWorkType("page");
                this.dialogVisible01 = false;
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

<style>
</style>