<!--  -->
<template>
  <div text-align="center">
    <!-- 导航栏 -->
    <!-- <van-nav-bar   title  /> -->
     <van-nav-bar  class="app-nav-bar" title="Account" lleft-arrow left-arrow @click-left="$router.back()">
      <template #right>
        <span  class="nav-bar-right">User：{{username}}</span>
      </template>
    </van-nav-bar>
    <!-- /导航栏 -->

    <van-cell-group>
      <van-field
        v-model="balNum"
        label="Balance"
        placeholder="850"
        size="large"
        left-icon="smile-o"
        right-icon="warning-o"
        readonly
        error
      />
    </van-cell-group>
    <van-tabs v-model="active">
      <van-tab title="Income and expenditure details">
        <van-cell v-for="item in listTrs" :key="item" :title="item" />
      </van-tab>
    </van-tabs>
  </div>
</template>

<script>
import { account } from "@/api/user";

export default {
  data() {
    return {
      username: "",
      balNum: "",
      active: 2,
      listTrs: []
    };
  },
  computed: {},
  watch: {},
  created() {
    this.account();
  },
  methods: {
    async account() {
      const res = await account();
      this.listTrs = res.data.trs;
      this.balNum = res.data.bal;
    },

    chakan() {
      this.$router.push({ path: "/" }); //页面跳转
    },
    zhuandou() {
      this.$router.push({ path: "/transer" }); //页面跳转
    },
    shanghu() {
      this.$router.push({ path: "/merchant" }); //页面跳转
    }
  },
  mounted() {
    this.username = sessionStorage.getItem("username");
  }
};
</script>
<style scoped>
.nav-bar-right{
  color: #ffffff;
  /* font-size: 16px; */
}
</style>
