<template>
  <div>
    <h1>找工地</h1>
    <div style="display: flex; justify-content: space-between">
      <!-- 搜索工具栏 -->
      <div>
        <el-input
          style="width: 300px; margin-right: 10px"
          prefix-icon="el-icon-search"
          placeholder="请输入工地名字进行搜索"
          clearable
          :disabled="showAdvanceSearch"
          v-model="searchDate.name"
          @keydown.enter="searchHandler('normal')"
        ></el-input>
        <el-tooltip
          class="item"
          effect="dark"
          content="输入为空时，搜索所有工地"
          placement="top"
        >
          <el-button
            type="primary"
            icon="el-icon-search"
            @click="searchHandler('normal')"
            :disabled="showAdvanceSearch"
            >搜索</el-button
          >
        </el-tooltip>
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
            <!-- 工地负责人 -->
            <el-col :span="10">
              <el-form-item label="工地负责人:" prop="person">
                <el-input
                  v-model="searchDate.person"
                  size="mini"
                  style="width: 180px"
                  placeholder="请输入工地负责人姓名"
                  prefix-icon="el-icon-edit"
                ></el-input>
              </el-form-item>
            </el-col>

            <!-- 电话号 -->
            <el-col :span="11">
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
          </el-row>
          <!-- 第二行 -->
          <el-row style="margin-top: 10px">
            <!-- 工地名 -->
            <el-col :span="10">
              <el-form-item label="工地名:" prop="name">
                <el-input
                  v-model="searchDate.name"
                  size="mini"
                  style="width: 180px"
                  placeholder="请输入工地名"
                  prefix-icon="el-icon-edit"
                ></el-input>
              </el-form-item>
            </el-col>
            <!-- 工地地址 -->
            <el-col :span="10">
              <el-form-item label="工地地址:" prop="address">
                <el-input
                  v-model="searchDate.address"
                  size="mini"
                  style="width: 280px"
                  placeholder="请输入大致工地地址，例如广州市"
                  prefix-icon="el-icon-edit"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <!-- 第三行 -->
          <el-row style="margin-top: 10px">
            <!-- 开工日期 -->
            <el-col :span="14">
              <el-form-item label="开工日期:" prop="beginDateScope">
                <el-date-picker
                  v-model="beginDateScope"
                  size="mini"
                  type="daterange"
                  unlink-panels
                  range-separator="至"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期"
                  format="yyyy-MM-dd"
                  value-format="yyyy-MM-dd"
                >
                </el-date-picker>
              </el-form-item>
            </el-col>
            <!-- 按钮 -->
            <el-col :span="8">
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

    <!-- 工地信息展示 -->
    <el-card
      v-show="siteDate"
      class="box-card"
      v-for="item of siteDate"
      :key="item.id"
      :index="item.id"
      style="margin-top: 10px"
    >
      <div slot="header" class="clearfix">
        <span
          >工地名：{{ item.name }}
          <el-divider
            style="margin: 0 20px 0 20px; font-weight: 800"
            direction="vertical"
          ></el-divider>

          电话号：{{ item.phone }}</span
        >
        <el-button
          style="float: right; padding: 3px 0"
          type="text"
          @click="detailsHandle(item.company.id, item.id)"
          >查看具体信息</el-button
        >
      </div>
      <div>
        <!-- 地址 -->
        <div>
          <span style="margin-right: 30px">工地地址:{{ item.address }} </span>
          <el-divider
            style="margin: 0 20px 0 20px; font-weight: 800"
            direction="vertical"
          ></el-divider>
          <span>
            <span style="margin-left: 30px">负责人:{{ item.userName }} </span>
          </span>
        </div>

        <!-- 其他信息 -->
        <div>
          <span style="margin-right: 30px">开工日期:{{ item.startTime }} </span>

          <el-divider
            style="margin: 0 20px 0 20px; font-weight: 800"
            direction="vertical"
          ></el-divider>
          <span style="margin-left: 30px">预计工期:{{ item.expectTime }} </span>
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
  </div>
</template>

<script>
export default {
  name: "FindSite",
  data() {
    return {
      //搜索的数据
      searchDate: {},
      beginDateScope: [],
      showAdvanceSearch: false,

      total: 0,
      loading: false,
      currentPage: 1,
      size: 5,
      pageFlag: true,
      //规则
      rules: {
        phone: [
          { min: 11, max: 11, message: "请输入规范的电话号", trigger: "blur" },
        ],
      },

      siteDate: [],
    };
  },
  mounted() {
    this.$emit("initMessage");
  },
  methods: {
    //高级搜索取消
    handleAdvanceCancel() {
      this.searchDate = {};
      this.beginDateScope = [];
    },

    //搜索处理
    searchHandler(type) {
      if (type == null || type != "page") {
        this.currentPage = 1;
      }
      this.postRequest(
        "/site/search/" +
          this.currentPage +
          "/" +
          this.size +
          "/" +
          this.beginDateScope,
        this.searchDate
      ).then((resp) => {
        if (resp) {
          this.siteDate = resp.data;
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

    //显示页数改变
    sizeChange(pageSize) {
      this.size = pageSize;
      // 修改员工展示数据
      this.searchHandler("page");
    },

    //查看工地具体信息
    detailsHandle(cid, sid) {
      this.$router.push("WorkSiteinfo/" + cid + "/" + sid);
    },
  },
};
</script>

<style>
</style>