<!-- 商户页面信息 -->
<template>
  <div>
    <!-- 导航栏 -->
    <van-nav-bar class="app-nav-bar" title="Merchant" left-arrow @click-left="$router.back()" />
    <!-- /导航栏 -->


    <!-- tabs开始 -->
    <van-tabs v-model="active">
      <van-tab title="Boc">
        <van-card
          title="(Green development in China) Bank of China Fund 1"
          desc="Performance benchmark 5.99%,  1099 days "
          thumb="images/fund.jpeg"
        />
        <van-card
          title="(Green development in China) Bank of China Fund 2"
          desc="Performance benchmark 4.99%,  365 days "
          thumb="images/fund.jpeg"
        />
        <van-card
           title="Fund"
           desc="BOC current treasure"
          thumb="images/fund2.jpg"
        />

      </van-tab>
      <van-tab title="Jing dong">
        <van-card
          num="1000"
          price="5999.00"
          title="Notebook computer"
          desc="Dell laptop 4-core 8g512 solid state drive starts in 6 seconds"
          thumb="images/dell.jpeg"
          origin-price="6100.00"
        />
         <van-card
          num="1000"
          price="899.00"
          title="Play with 20 mobile phones"
          desc="4GB Memory | 128G storage | 6.5 size"
          thumb="images/honor.jpg"
          origin-price="999.00"
        />
         <van-card
          num="1000"
          price="3399.00"
          title="Mi TV E32C"
          desc="32 inch HD quad core processor 1GB + 4GB"
          thumb="images/mi.jpg"
        />
      </van-tab>
      <van-tab title="Mei tuan">
        <van-card
          title="Qingfeng steamed bun shop"
          desc="4.8 score | per capita 27.00"
          thumb="images/Qingfeng.jpg"
        />
      </van-tab>
      <van-tab title="Tian mao">
        <van-card
          num="9999"
          price="399.00"
          title="Li Ning"
          desc="New summer sportswear"
          thumb="images/lining.jpeg"
          origin-price="499.00"
        />
      </van-tab>
    </van-tabs>
    <!-- tabs结束 -->
  </div>
</template>

<script>
export default {
  data() {
    return {
      active: 1
    };
  },
  computed: {},
  watch: {},
  created() {},
  methods: {
    zhgetJifen() {
      this.$router.push({ path: "/zh-order" }); //页面跳转
    },
    jdgetJifen() {
      this.$router.push({ path: "/jd-order" }); //页面跳转
    },
    mtgetJifen() {
      this.$router.push({ path: "/mt-order" }); //页面跳转
    },
    tbgetJifen() {
      this.$router.push({ path: "/tb-order" }); //页面跳转
    }
  },
  mounted() {}
};
</script>
<style scoped>
/deep/ .van-grid-item__content--center {
  justify-content: flex-start;
}
</style>
