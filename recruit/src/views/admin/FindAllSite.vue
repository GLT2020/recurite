<template>
  <div>
    <h1>查看所有工地</h1>
    <!-- 导入导出工具栏 -->
    <div style="margin-bottom: 10px">
      <el-button type="success" @click="exportData" icon="el-icon-download">
        导出数据
      </el-button>
    </div>
    <!-- 表单 -->
    <div>
      <el-table v-loading="loading" :data="allSite" border style="width: 100%">
        <el-table-column fixed prop="name" label="工地名" width="120">
        </el-table-column>
        <el-table-column prop="company.name" label="公司名" width="120">
        </el-table-column>
        <el-table-column prop="userName" label="工地负责人" width="120">
        </el-table-column>
        <el-table-column prop="phone" label="联系电话" width="120">
        </el-table-column>
        <el-table-column prop="address" label="地址" width="300">
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="120">
        </el-table-column>
        <el-table-column prop="startTime" label="开工时间" width="120">
        </el-table-column>
        <el-table-column prop="completeTime" label="完工时间" width="120">
        </el-table-column>
        <el-table-column prop="expectTime" label="预计工期" width="120">
        </el-table-column>

        <!-- 状态 -->
        <el-table-column prop="recuritState" label="招聘状态" width="120">
          <template slot-scope="scope">
            <el-tag type="success" v-if="scope.row.recuritState == 1"
              >招聘中</el-tag
            >
            <el-tag type="info" v-else>不招聘</el-tag>
          </template>
        </el-table-column>
        <!-- 状态 -->
        <el-table-column prop="siteState" label="工地状态" width="120">
          <template slot-scope="scope">
            <el-tag type="success" v-if="scope.row.siteState == 1"
              >施工中</el-tag
            >
            <el-tag type="info" v-else>完工</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="siteState" label="是否移除" width="120">
          <template slot-scope="scope">
            <el-tag type="success" v-if="scope.row.dflag == 1">未删除</el-tag>
            <el-tag type="info" v-else>已删除</el-tag>
          </template>
        </el-table-column>

        <el-table-column prop="remark" label="工地简述" width="300">
          <template slot-scope="scope">
            <span v-if="scope.row.remark == null || scope.row.remark == ''"
              >无</span
            >
            <span v-else>{{ scope.row.remark }}</span>
          </template>
        </el-table-column>
        <!-- 操作 -->
        <!-- <el-table-column fixed="right" label="操作" width="100">
          <template slot-scope="scope">
            <el-button @click="handleClick(scope.row)" type="text" size="small"
              >修改状态</el-button
            >
          </template>
        </el-table-column> -->
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
  </div>
</template>

<script>
export default {
  name: "FindAllSite",
  data() {
    return {
      allSite: [],
      // 查看的具体信息
      siteDetails: {},

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
    this.initSite();
  },
  methods: {
    // 获取所有的公司信息
    initSite(type) {
      this.loading = true;
      console.log("获取所有工地信息", this.size);
      if (type == null || type != "page") {
        this.currentPage = 1;
      }
      this.getRequest(
        "/site/admin/getAllSite/" + this.currentPage + "/" + this.size
      ).then((resp) => {
        if (resp) {
          console.log("获取到的所有工地信息", resp);

          this.allSite = resp.data;
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

    //导出
    exportData() {
      console.log("开始导出....");
      this.downloadRequest("/site/admin/export");
    },
  },
};
</script>

<style>
</style>