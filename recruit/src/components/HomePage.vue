<template>
  <div>
    <h1>公告</h1>
    <!-- 按钮 -->
    <div style="margin: 10px 0 10px 0"></div>
    <!-- 表单 -->
    <!-- <el-table :data="showDate" stripe style="width: 100%">
      <el-table-column prop="topic" label="主题" width="180"> </el-table-column>
      <el-table-column prop="content" label="内容" width="180">
      </el-table-column>

      <el-table-column prop="createTime" label="日期" width="180">
      </el-table-column>
    </el-table> -->
    <div style="text-align: center">
      <div class="topic">
        <h1>{{ showDate.topic }}</h1>
      </div>
      <div class="content">
        <h3>{{ showDate.content }}</h3>
      </div>
    </div>

    <!-- 进度条 -->
    <div style="margin-top: 20px">
      <el-steps :active="active" finish-status="success">
        <el-step
          :title="'公告' + (parseInt(index) + 1)"
          v-for="(item, index) of receiveDate"
          :key="item.id"
          :index="index"
        ></el-step>
        <!-- <el-step title="步骤 2"></el-step>
        <el-step title="步骤 3"></el-step> -->
      </el-steps>

      <el-button style="margin-top: 12px" @click="nexthandle">下一条</el-button>
    </div>
  </div>
</template>

<script>
export default {
  name: "HomePage",
  data() {
    return {
      user: null,

      //接收的信息
      receiveDate: [],

      //展示的信息
      showDate: { topic: null, content: null },

      active: 0,
    };
  },
  mounted() {
    this.user = JSON.parse(window.sessionStorage.getItem("user"));

    this.receiveHandle();
  },

  methods: {
    //查看收件箱
    receiveHandle(type) {
      this.getRequest("/sysmessage/getAll").then((resp) => {
        if (resp) {
          this.receiveDate = resp;
          this.showDate = this.receiveDate[0];
        }
      });
    },

    //下一个公告
    nexthandle() {
      this.active++;
      console.log("下一个公告：", this.receiveDate);
      if (this.active > this.receiveDate.length - 1) {
        this.active = 0;
      }
      this.showDate = this.receiveDate[this.active];
    },
  },
};
</script>


<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}

.topic {
  width: 90%;
}

.content {
  height: 230px;
  width: 90%;
}
</style>
