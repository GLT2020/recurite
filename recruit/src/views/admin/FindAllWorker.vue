<template>
  <div>
    <h1>查看所有工人</h1>
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
        :data="allWorker"
        border
        style="width: 100%"
      >
        <el-table-column fixed prop="name" label="姓名" width="120">
        </el-table-column>
        <el-table-column prop="phone" label="联系电话" width="120">
        </el-table-column>
        <el-table-column prop="sex" label="性别" width="80"> </el-table-column>
        <el-table-column prop="age" label="年龄" width="80"> </el-table-column>
        <el-table-column
          sortable
          prop="createTime"
          label="出生日期"
          width="120"
        >
          <template slot-scope="scope">
            {{ scope.row.createTime }}
          </template>
        </el-table-column>
        <el-table-column
          prop="workTypeList"
          label="工种"
          width="120"
          show-overflow-tooltip
        >
          <template slot-scope="scope">
            <el-tag
              v-for="item of scope.row.workTypeList"
              :key="item.id"
              :index="item.id"
              >{{ item.name }}</el-tag
            >
          </template>
        </el-table-column>
        <el-table-column prop="education" label="学历" width="120">
        </el-table-column>
        <el-table-column prop="state" label="状态" width="120">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.state != 1">暂不找工</el-tag>
            <el-tag type="success" v-else>找工中</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="nowAddress" label="常驻地址" width="300">
        </el-table-column>
        <el-table-column prop="address" label="意向地址" width="300">
        </el-table-column>

        <el-table-column prop="remark" label="工人简述" width="300">
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
              >查看</el-button
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
  name: "FindAllWorker",
  data() {
    return {
      allWorker: [],
      // 查看的具体信息
      workerDetails: {},

      //分页
      total: 0,
      loading: false,
      currentPage: 1,
      size: 10,
      pageFlag: true,

      loading: false,

      dialogVisible01: false,
    };
  },
  mounted() {
    this.initWorker();
  },
  methods: {
    // 获取所有的公司信息
    initWorker(type) {
      this.loading = true;
      if (type == null || type != "page") {
        this.currentPage = 1;
      }
      this.postRequest(
        "/worker/admin/search/" + this.currentPage + "/" + this.size,
        this.workerDetails
      ).then((resp) => {
        if (resp) {
          console.log("获取到的所有工人信息", resp);

          this.allWorker = resp.data;
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
      this.downloadRequest("/worker/admin/export");
    },
  },
};
</script>

<style>
</style>