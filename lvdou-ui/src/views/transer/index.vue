<!--  -->
<template>
<div>
     <!-- 导航栏 -->
    <van-nav-bar
      class="app-nav-bar"
      title="Transer bean"
      left-arrow
      @click-left="$router.back()"
    />
    <!-- /导航栏 -->

      <van-cell-group>
        <van-field v-model="fudouren" label="Bean payer" placeholder="" />
        <van-field v-model="accountList.rec" type="tel" label="Bean payee" placeholder="Please input payee mobile phone no" />
        <van-field v-model="accountList.amt" type="number" label="quantity" placeholder="Please input green bean quantity" />
      </van-cell-group>
      <div class="login-btn-wrap">
          <van-button class="login-btn"  type="primary" block @click="zhuandou()">Confirm</van-button>
      </div>
</div>
</template>

<script>
import qs from 'qs'
import { transer } from "@/api/user";

export default {
data() {
return {
  fudouren:'',
  shoudouren:'',
  douNumber:'',
  accountList:{
    rec: '',
    amt: ''
  }
}
},
computed: {},
watch: {},
created() {

},
methods: {
  async zhuandou() {

      const res = await transer(qs.stringify(this.accountList))
      console.log("Transer sucess", res);
      this.$router.push({ path: "/account" }); //页面跳转

    },
},
mounted() {
  this.fudouren = sessionStorage.getItem("username")
}
}
</script>
<style scoped>

</style>
