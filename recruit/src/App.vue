<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
// import Login from "./views/Login.vue";

export default {
  name: "App",
  data() {
    return {
      msg: "???",
    };
  },
  components: {
    // Login,
  },
};
</script>

<style>
</style>
