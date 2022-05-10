<template>
  <div>
    <h1>系统用户</h1>
    <div style="margin-top: 20px; margin-bottom: 10px">
      <!-- 添加系统用户 -->
      <el-button type="primary" @click="openhandler01">添加系统用户</el-button>
    </div>
    <!-- 表单 -->
    <div>
      <el-table v-loading="loading" :data="allAdmin" border style="width: 100%">
        <el-table-column prop="name" label="用户名" width="120">
        </el-table-column>
        <el-table-column prop="phone" label="账户" width="120">
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="120">
        </el-table-column>
        <el-table-column prop="roles" label="角色" width="120">
          <template slot-scope="scope">
            <span
              v-for="item of scope.row.roles"
              :key="item.id"
              :index="item.id"
            >
              <el-tag>
                {{ item.nameZh }}
              </el-tag>
            </span>
          </template>
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
              title="确定删除此用户吗？"
              @confirm="deleteHandle(scope.row)"
            >
              <el-button
                v-show="scope.row.id != 1"
                type="danger"
                size="small"
                slot="reference"
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

    <!-- 添加客服人员弹窗 -->
    <el-dialog title="添加" :visible.sync="dialogVisible01" width="50%">
      <el-form ref="addadmin" :model="addadmin" :rules="rules">
        <!-- 第一行 -->
        <el-row>
          <!-- 用户名 -->
          <el-col :span="11">
            <el-form-item label="用户名:" prop="name">
              <el-input
                v-model="addadmin.name"
                size="mini"
                style="width: 180px"
                placeholder="请输入用户名"
                prefix-icon="el-icon-edit"
              ></el-input>
            </el-form-item>
          </el-col>
          <!-- 账号 -->
          <el-col :span="11">
            <el-form-item label="账号:" prop="phone">
              <el-input
                v-model="addadmin.phone"
                size="mini"
                style="width: 180px"
                placeholder="请输入账号用于登录"
                prefix-icon="el-icon-edit"
              ></el-input>
            </el-form-item>
          </el-col>
          <!-- 密码 -->
          <el-col :span="11">
            <el-form-item label="密码:" prop="password">
              <el-input
                v-model="addadmin.password"
                size="mini"
                style="width: 180px"
                placeholder="请输入密码"
                prefix-icon="el-icon-edit"
              ></el-input>
            </el-form-item>
          </el-col>
          <!-- 备注 -->
          <el-col :span="11">
            <el-form-item label="备注:" prop="remark">
              <el-input
                v-model="addadmin.remark"
                size="mini"
                style="width: 180px"
                placeholder="请输入备注"
                prefix-icon="el-icon-edit"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row
          ><!-- 角色 -->
          <el-col :span="14">
            <el-form-item label="角色:" prop="role">
              <el-select
                size="mini"
                collapse-tags
                v-model="addadmin.role"
                placeholder="请选择角色"
              >
                <el-option
                  v-for="(r, index) in allroles"
                  :key="index"
                  :label="r.name"
                  :value="r.id"
                >
                </el-option>
              </el-select>
            </el-form-item> </el-col
        ></el-row>
        <el-row>
          <el-col :span="21">
            <el-form-item style="display: flex; justify-content: flex-end">
              <el-button @click="closehandler">取 消</el-button>
              <el-button type="primary" @click="insertAdmin('addadmin')"
                >添 加</el-button
              >
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </el-dialog>

    <!-- 修改客服人员弹窗 -->
    <el-dialog title="修改" :visible.sync="dialogVisible02" width="50%">
      <el-form ref="updateadmin" :model="updateadmin" :rules="rules">
        <!-- 第一行 -->
        <el-row>
          <!-- 用户名 -->
          <el-col :span="11">
            <el-form-item label="用户名:" prop="name">
              <el-input
                v-model="updateadmin.name"
                size="mini"
                style="width: 180px"
                placeholder="请输入用户名"
                prefix-icon="el-icon-edit"
              ></el-input>
            </el-form-item>
          </el-col>
          <!-- 账号 -->
          <el-col :span="11">
            <el-form-item label="账号:" prop="phone">
              <el-input
                disabled
                v-model="updateadmin.phone"
                size="mini"
                style="width: 180px"
                placeholder="请输入账号用于登录"
                prefix-icon="el-icon-edit"
              ></el-input>
            </el-form-item>
          </el-col>
          <!-- 密码 -->
          <el-col :span="11">
            <el-form-item label="密码:" prop="password">
              <el-input
                v-model="updateadmin.password"
                size="mini"
                style="width: 180px"
                placeholder="请输入密码"
                prefix-icon="el-icon-edit"
              ></el-input>
            </el-form-item>
          </el-col>
          <!-- 备注 -->
          <el-col :span="11">
            <el-form-item label="备注:" prop="remark">
              <el-input
                v-model="updateadmin.remark"
                size="mini"
                style="width: 180px"
                placeholder="请输入备注"
                prefix-icon="el-icon-edit"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="21">
            <el-form-item style="display: flex; justify-content: flex-end">
              <el-button @click="closehandler">取 消</el-button>
              <el-button
                type="primary"
                @click="updateadminHandle('updateadmin')"
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
  name: "AddAdmin",
  data() {
    return {
      allAdmin: [],
      addadmin: { name: null },
      updateadmin: {},
      // role: null,
      allroles: [
        { id: 1, name: "系统管理员" },
        { id: 2, name: "客服人员" },
      ],

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
        name: [{ required: true, message: "请输入工种名", trigger: "blur" }],
        phone: [{ required: true, message: "请输入账号", trigger: "blur" }],
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          {
            min: 3,
            max: 20,
            message: "长度在 3 到 20 个字符",
            trigger: "blur",
          },
        ],
        role: [{ required: true, message: "请选择角色", trigger: "blur" }],
      },
    };
  },
  mounted() {
    this.initadmin();
  },
  methods: {
    // 获取所有的系统用户
    initadmin(type) {
      this.loading = true;
      if (type == null || type != "page") {
        this.currentPage = 1;
      }
      this.getRequest("/admin/all/" + this.currentPage + "/" + this.size).then(
        (resp) => {
          if (resp) {
            console.log("获取到的系统用户", resp);

            this.allAdmin = resp.data;
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
      this.initadmin("page");
    },

    //每页条数改变时
    sizeChange(pageSize) {
      this.size = pageSize;
      // 修改展示数据
      this.initadmin("page");
    },

    //打开添加
    openhandler01() {
      this.dialogVisible01 = true;
    },

    //取消
    closehandler() {
      this.addadmin = {};
      this.dialogVisible01 = false;
      this.dialogVisible02 = false;
    },

    //添加
    insertAdmin(form) {
      this.$refs[form].validate((valid) => {
        if (valid) {
          this.postRequest("/admin/add/" + this.addadmin.role, this.addadmin)
            .then((resp) => {
              if (resp) {
                this.initadmin("");
                this.dialogVisible01 = false;
                this.addadmin = {};
              }
            })
            .catch((err) => {
              this.closehandler();
            });
        } else {
          return false;
        }
      });
    },

    //修改弹窗
    updateHandle(item) {
      this.updateadmin = item;
      this.updateadmin.password = null;
      this.dialogVisible02 = true;
    },

    // 修改用户按钮
    updateadminHandle(form) {
      this.$refs[form].validate((valid) => {
        if (valid) {
          this.putRequest("/admin/update", this.updateadmin).then((resp) => {
            if (resp) {
              this.initadmin("page");
              this.dialogVisible02 = false;
              this.updateadmin = {};
            }
          });
        } else {
          return false;
        }
      });
    },

    //删除
    deleteHandle(item) {
      console.log("触发删除", item);
      this.deleteRequest("/admin/delete", item).then((resp) => {
        if (resp) {
          this.initadmin("page");
        }
      });
    },
  },
};
</script>

<style>
</style>