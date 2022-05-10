<template>
  <div>
    <h1>个人应聘</h1>

    <!-- 应聘信息展示 -->
    <el-card
      :loading="loading"
      v-show="recuritMsgDate"
      class="box-card"
      v-for="item of recuritMsgDate"
      :key="item.id"
      :index="item.id"
      style="margin-top: 10px"
    >
      <div slot="header" class="clearfix">
        <span
          >{{ item.workType.name }}
          <el-divider
            style="margin: 0 20px 0 20px; font-weight: 800"
            direction="vertical"
          ></el-divider>
          {{ item.salaryType.name }}
          {{ item.salary }}
          <el-divider
            style="margin: 0 20px 0 20px; font-weight: 800"
            direction="vertical"
          ></el-divider>
          招聘人数 : {{ item.number }}
        </span>
        <el-button
          style="float: right; padding: 3px 0"
          type="text"
          @click="cancleApply(item.id)"
          >取消应聘</el-button
        >
        <el-button
          style="float: right; padding: 3px 0; margin-right: 20px"
          type="text"
          @click="detailsHandle(item.site.id, item.id)"
          >查看具体信息</el-button
        >
      </div>
      <div>
        <!-- 其他信息 -->
        <div>
          <span style="margin-right: 30px"
            >工地地址 : {{ item.site.address }}
          </span>
          <el-divider
            style="margin: 0 20px 0 20px; font-weight: 800"
            direction="vertical"
          ></el-divider>
          <span>
            <span style="margin-left: 30px"
              >负责人:{{ item.site.userName }}
            </span>
          </span>
        </div>
        <div>
          <span style="margin-right: 30px"
            >开工日期 : {{ item.site.startTime }}
          </span>

          <el-divider
            style="margin: 0 20px 0 20px; font-weight: 800"
            direction="vertical"
          ></el-divider>
          <span style="margin-left: 30px"
            >预计工期 : {{ item.site.expectTime }}
          </span>
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
  name: "Application",
  data() {
    return {
      worker: JSON.parse(window.sessionStorage.getItem("worker")),
      searchDate: {
        stypeId: null,
        wtypeId: null,
      },
      address: null,

      showAdvanceSearch: false,

      total: 0,
      loading: false,
      currentPage: 1,
      size: 5,
      pageFlag: true,
      loading: false,

      allWorkTypes: [],
      allSalaryTypes: [],

      recuritMsgDate: [],
    };
  },
  mounted() {
    this.$emit("initMessage");
    this.searchHandler();
  },
  methods: {
    //搜索处理
    searchHandler(type) {
      if (type == null || type != "page") {
        this.currentPage = 1;
      }
      this.loading = true;
      this.getRequest(
        "/recuritMessage/getApply/" +
          this.currentPage +
          "/" +
          this.size +
          "/" +
          this.worker.id
      ).then((resp) => {
        if (resp) {
          this.recuritMsgDate = resp.data;
          console.log("获取到的数据,", resp);
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

      this.searchHandler("page");
    },

    //显示页数改变
    sizeChange(pageSize) {
      this.size = pageSize;

      this.searchHandler("page");
    },

    //取消应聘
    cancleApply(rid) {
      console.log("取消应聘的招聘信息的id", rid);
      this.getRequest(
        "/recuritMessage/cancelApply/" + this.worker.id + "/" + rid
      ).then((resp) => {
        if (resp) {
          this.searchHandler("page");
        }
      });
    },

    //查看工地具体信息
    detailsHandle(sid, rid) {
      //根据工地id获取公司id
      this.getRequest("/site//getCompanyId/" + sid).then((resp) => {
        if (resp) {
          let cid = resp;
          this.$router.push("WorkSiteinfo/" + cid + "/" + sid);
        }
      });
    },
  },
};
</script>

<style>
</style>