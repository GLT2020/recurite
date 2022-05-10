<template>
  <div>
    <h1>查看所有公司</h1>
    <!-- 导入导出工具栏 -->
    <div style="margin-bottom: 10px">
      <el-button type="success" @click="exportData" icon="el-icon-download">
        导出数据
      </el-button>
    </div>
    <!-- 表单 -->
    <div>
      <el-table
        v-loading="loading"
        :data="allCompany"
        border
        style="width: 100%"
      >
        <el-table-column fixed prop="name" label="公司名" width="120">
        </el-table-column>
        <el-table-column prop="phone" label="联系电话" width="120">
        </el-table-column>
        <el-table-column prop="person" label="法人" width="120">
        </el-table-column>
        <!-- 创建时间 -->
        <el-table-column
          sortable
          prop="createTime"
          label="创建时间"
          width="120"
        >
          <template slot-scope="scope">
            <el-tag
              v-if="scope.row.createTime == null || scope.row.createTime == ''"
              >2022-01-01</el-tag
            >
            {{ scope.row.createTime }}
          </template>
        </el-table-column>
        <!-- 状态 -->
        <el-table-column prop="state" label="状态" width="120">
          <template slot-scope="scope">
            <el-tag type="warning" v-if="scope.row.state == 1">待验证</el-tag>
            <el-tag type="warning" v-else-if="scope.row.state == 0"
              >未通过</el-tag
            >
            <el-tag type="success" v-else>已验证</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="address" label="公司地址" width="300">
        </el-table-column>

        <el-table-column prop="remark" label="公司简述" width="300">
          <template slot-scope="scope">
            <span v-if="scope.row.remark == null || scope.row.remark == ''"
              >无</span
            >
            <span v-else>{{ scope.row.remark }}</span>
          </template>
        </el-table-column>
        <!-- 操作 -->
        <el-table-column fixed="right" label="操作" width="100">
          <template slot-scope="scope">
            <el-button @click="handleClick(scope.row)" type="text" size="small"
              >修改状态</el-button
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

    <!-- 修改信息弹窗 -->
    <el-dialog title="管理状态" :visible.sync="dialogVisible01" width="70%">
      <el-form ref="Form" :model="updatecompany" :rules="rules">
        <el-form-item label="公司名:" prop="name">
          <el-input
            disabled
            v-model="updatecompany.name"
            size="mini"
            style="width: 180px"
            placeholder="请输入公司名"
            prefix-icon="el-icon-edit"
          ></el-input>
        </el-form-item>

        <el-form-item label="联系地址:" prop="address">
          <el-input
            disabled
            v-model="updatecompany.address"
            size="mini"
            style="width: 180px"
            placeholder="请输入地址"
            prefix-icon="el-icon-edit"
          ></el-input>
        </el-form-item>
        <el-form-item label="状态:" prop="state">
          <el-select v-model="updatecompany.state" placeholder="请选择">
            <el-option
              v-for="item in stateType"
              :key="item.value"
              :label="item.name"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item style="display: flex; justify-content: flex-end">
          <template>
            <el-button @click="dialogVisible02 = false">取 消</el-button>
            <el-button type="primary" @click="updateCompanyhandle('Form')"
              >修 改</el-button
            >
          </template>
        </el-form-item>
      </el-form>
      <!-- <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible01 = false">取 消</el-button>
        <el-button type="primary" @click="updateMsg()">修 改</el-button>
      </span> -->
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "FindAllCompany",
  data() {
    return {
      allCompany: [],
      // 查看的具体信息
      conpanyDetails: {},

      //分页
      total: 0,
      loading: false,
      currentPage: 1,
      size: 10,
      pageFlag: true,

      loading: false,

      dialogVisible01: false,

      //修改公司状态
      updatecompany: {},

      //修改的状态
      stateType: [
        { name: "未通过", id: 0 },
        { name: "待验证", id: 1 },
        { name: "通过", id: 2 },
      ],

      rules: {
        name: [{ required: true, message: "请输入公司名", trigger: "blur" }],
        address: [{ required: true, message: "请输入地址", trigger: "blur" }],
        state: [{ required: true, message: "请选择状态", trigger: "blur" }],
      },
    };
  },
  mounted() {
    this.initCompany();
  },
  methods: {
    // 获取所有的公司信息
    initCompany(type) {
      this.loading = true;
      console.log("获取所有公司信息", this.size);
      if (type == null || type != "page") {
        this.currentPage = 1;
      }
      this.getRequest(
        "/company/admin/getAllCompany/" + this.currentPage + "/" + this.size
      ).then((resp) => {
        if (resp) {
          console.log("获取到的所有工地信息", resp);

          this.allCompany = resp.data;
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
      this.initCompany("page");
    },

    //每页条数改变时
    sizeChange(pageSize) {
      this.size = pageSize;
      // 修改员工展示数据
      this.initCompany("page");
    },

    //点击事件弹窗
    handleClick(item) {
      this.dialogVisible01 = true;
      this.updatecompany = item;
    },

    updateCompanyhandle(form) {
      this.$refs[form].validate((valid) => {
        if (valid) {
          this.putRequest(
            "/company/admin/updateState",
            this.updatecompany
          ).then((resp) => {
            if (resp) {
              this.dialogVisible01 = false;
              this.initCompany("page");
            }
          });
        } else {
          return false;
        }
      });
    },

    //导出
    exportData() {
      console.log("开始导出....");
      this.downloadRequest("/company/admin/export");
    },
  },
};
</script>

<style>
</style>