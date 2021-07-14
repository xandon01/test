<!--  -->
<template>
  <div>
       <!-- 导航栏 -->
    <van-nav-bar
      class="app-nav-bar"
      title="Order"
      left-arrow
      @click-left="$router.back()"
    />
    <!-- /导航栏 -->

    <div>
      <van-card
        num="1"
        price="10000.00"
        title="Fund"
        desc="BOC current treasure"
        thumb="images/fund2.jpg"
      />
      <van-card
        price="50.00"
        title="Green bean"
        desc="Green bean for cash(50)"
      />

      <van-submit-bar
        :price="995000"
        label="total: "
        button-text="To settle accounts"
        @submit="showPopup"
        style="margin-bottom: 80px;"
      >
        <van-checkbox v-model="checked">Select all</van-checkbox>
      </van-submit-bar>

      <van-popup v-model="show" position="bottom" :style="{ height: '50%' }">
        <van-radio-group v-model="radio" style="font-size:16px">
          <p class="login-btn">Payment method：</p>
          <van-radio name="1">Wechat payment</van-radio>
          <br />
          <van-radio name="2">Alipay payment</van-radio>
          <br />
          <van-radio name="3">Bank card payment</van-radio>
          <br />
        </van-radio-group>
        <div style="padding: 20px 0"></div>
        <div class="login-btn-wrap">
          <van-button class="login-btn" type="primary" block @click="pay">Confirm payment</van-button>
        </div>
      </van-popup>
    </div>
  </div>
</template>

<script>
import { paybean } from "@/api/user";
export default {
  data() {
    return {
      show: false,
      showList: false,
      radio: 1,
      checked: true,
      chosenCoupon: -1,
    };
  },
  computed: {},
  watch: {},
  created() {},
  methods: {
    // 结算弹窗
    showPopup() {
      this.show = true;
    },
    async pay() {
      this.show = false;
      const res = await paybean();
      console.log("paybean sucess", res);
      this.$router.push({ path: "/account" }); //页面跳转
    },
    // 点击提交订单,跳转到支付页面
    onSubmit() {
      // this.$router.push({ path: "/pay" }); //页面跳转
    },
    onChange(index) {
      this.showList = false;
      this.chosenCoupon = index;
    },
    onExchange(code) {
      this.coupons.push(coupon);
    }
  },
  mounted() {}
};
</script>
<style scoped>
</style>
