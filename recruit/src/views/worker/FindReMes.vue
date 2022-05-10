<template>
  <div>
    <div style="display: flex; justify-content: space-between">
      <!-- 搜索工具栏 -->
      <div>
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
          招聘搜索</el-button
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
            <!-- 工资种类 -->
            <el-col :span="10">
              <el-form-item label="工资种类:" prop="stypeId">
                <el-select
                  size="mini"
                  collapse-tags
                  v-model="searchDate.stypeId"
                  placeholder="选择工资种类"
                >
                  <el-option
                    v-for="(r, index) in allSalaryTypes"
                    :key="index"
                    :label="r.name"
                    :value="r.id"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>

            <!-- 工种 -->
            <el-col :span="11">
              <el-form-item label="工种:" prop="wtypeId">
                <!-- 工种选择 -->
                <el-select
                  size="mini"
                  collapse-tags
                  v-model="searchDate.wtypeId"
                  placeholder="选择工种"
                >
                  <el-option
                    v-for="(r, index) in allWorkTypes"
                    :key="index"
                    :label="r.name"
                    :value="r.id"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <!-- 第二行 -->
          <el-row style="margin-top: 10px">
            <!-- 工作地址 -->
            <el-col :span="10">
              <el-form-item label="工作地址:" prop="address">
                <el-input
                  v-model="address"
                  size="mini"
                  style="width: 280px"
                  placeholder="请输入大致的工作地址，例如广州市"
                  prefix-icon="el-icon-edit"
                ></el-input>
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
          @click="apply(item.id)"
          >应聘</el-button
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
  name: "FindReMes",
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

      //规则
      rules: {
        phone: [
          { min: 11, max: 11, message: "请输入规范的电话号", trigger: "blur" },
        ],
      },

      recuritMsgDate: [],
    };
  },
  mounted() {
    this.initWorkTypes();
    this.initSalaryTypes();
    this.$emit("initMessage");
  },
  methods: {
    //获取所有工种
    initWorkTypes() {
      this.getRequest("/worktype/").then((resp) => {
        if (resp) {
          this.allWorkTypes = resp;
        }
      });
    },

    //获取所有工资种类
    initSalaryTypes() {
      this.getRequest("/salaryType/").then((resp) => {
        if (resp) {
          this.allSalaryTypes = resp;
        }
      });
    },

    // 搜索框取消
    handleAdvanceCancel() {
      this.searchDate = {};
      this.address = null;
    },

    //搜索
    searchHandler(type) {
      this.loading = true;
      if (type == null || type != "page") {
        this.currentPage = 1;
      }
      let url =
        "/recuritMessage/search/" + this.currentPage + "/" + this.size + "?";
      if (this.searchDate.stypeId != 0 && this.searchDate.stypeId != null) {
        url = url + "stypeId=" + this.searchDate.stypeId;
      }
      if (this.searchDate.wtypeId != 0 && this.searchDate.wtypeId != null) {
        url = url + "&wtypeId=" + this.searchDate.wtypeId;
      }
      // console.log("address有问题？？？？");
      if (null != this.address && this.address.length != 0) {
        url = url + "&address=" + this.address;
      }
      console.log("分页搜索信息的url", url);
      this.getRequest(url).then((resp) => {
        if (resp) {
          console.log("分页查询的数据", resp);
          this.recuritMsgDate = resp.data;
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
      this.searchHandler("page");
    },

    //显示页数改变
    sizeChange(pageSize) {
      this.size = pageSize;
      // 修改员工展示数据
      this.searchHandler("page");
    },

    //应聘
    apply(mid) {
      console.log("应聘的招聘信息的id", mid);
      this.postRequest("/recuritMessage/apply/" + this.worker.id + "/" + mid);
    },

    //查看具体信息
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