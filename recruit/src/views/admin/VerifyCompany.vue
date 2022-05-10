<template>
  <div>
    <div>
      <h1 style="display: inline-block; margin-right: 20px">验证公司</h1>
      <el-button
        size="mini"
        type="primary"
        icon="el-icon-refresh"
        @click="initCompany"
      ></el-button>
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
        <el-table-column
          sortable
          prop="updateTime"
          label="更新时间"
          width="120"
        >
          <template slot-scope="scope">
            <el-tag
              v-if="scope.row.updateTime == null || scope.row.updateTime == ''"
              >2022-01-01</el-tag
            >
            {{ scope.row.updateTime }}
          </template>
        </el-table-column>

        <el-table-column prop="state" label="状态" width="120">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.state == 1">待验证</el-tag>
            <el-tag type="warning" v-else>未通过</el-tag>
          </template>
        </el-table-column>

        <el-table-column fixed="right" label="操作">
          <template slot-scope="scope">
            <el-button @click="handleClick(scope.row)" type="text" size="small"
              >查看</el-button
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

    <!-- 查看具体信息弹窗 -->
    <el-dialog title="具体信息" :visible.sync="dialogVisible01" width="50%">
      <el-descriptions direction="vertical" :column="4" border>
        <el-descriptions-item label="公司名">
          {{ conpanyDetails.name }}</el-descriptions-item
        >
        <el-descriptions-item label="联系电话">{{
          conpanyDetails.phone
        }}</el-descriptions-item>
        <el-descriptions-item label="法人" :span="2">{{
          conpanyDetails.person
        }}</el-descriptions-item>
        <el-descriptions-item label="公司简述">
          <span
            v-if="conpanyDetails.remark != null && conpanyDetails.remark != ''"
          >
            {{ conpanyDetails.remark }}
          </span>
          <span v-else>无</span>
        </el-descriptions-item>
        <el-descriptions-item label="联系地址">
          {{ conpanyDetails.address }}
        </el-descriptions-item>
        <el-descriptions-item label="创建时间" :span="2">
          <span
            v-if="
              conpanyDetails.createTime != null &&
              conpanyDetails.createTime != ''
            "
          >
            {{ conpanyDetails.createTime }}
          </span>
          <span v-else>无</span>
        </el-descriptions-item>
        <el-descriptions-item label="营业执照">
          <span
            v-if="
              conpanyDetails.license != null && conpanyDetails.license != ''
            "
          >
            <el-image
              style="width: 100px; height: 100px"
              :src="conpanyDetails.license"
              fit="fill"
            ></el-image>
            <!-- {{ conpanyDetails.license }} -->
          </span>
          <span v-else>无</span>
        </el-descriptions-item>
      </el-descriptions>

      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible01 = false">取 消</el-button>
        <el-button type="warning" @click="notVerifyhandle(conpanyDetails.id)"
          >不 通 过</el-button
        >
        <el-button type="primary" @click="verifyhandle(conpanyDetails.id)"
          >通 过</el-button
        >
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "VerifyCompany",
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
    };
  },
  mounted() {
    this.initCompany();
  },
  methods: {
    // 获取所有的待验证公司
    initCompany(type) {
      this.loading = true;
      if (type == null || type != "page") {
        this.currentPage = 1;
      }
      this.getRequest(
        "/company/admin/verify/" + this.currentPage + "/" + this.size
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

    //单行点击事件
    handleClick(row) {
      this.conpanyDetails = row;
      this.dialogVisible01 = true;
    },

    // 不通过
    notVerifyhandle() {
      this.postRequest(
        "/company/admin/verifyCompany/" + 0,
        this.conpanyDetails
      ).then((resp) => {
        if (resp) {
          this.initCompany("page");
          this.dialogVisible01 = false;
        }
      });
    },

    //通过
    verifyhandle() {
      this.postRequest(
        "/company/admin/verifyCompany/" + 1,
        this.conpanyDetails
      ).then((resp) => {
        if (resp) {
          this.initCompany("page");
          this.dialogVisible01 = false;
        }
      });
    },
  },
};
</script>

<style>
h1 {
}
</style>