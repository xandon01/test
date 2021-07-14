<template>
  <div class="layout-container">
    <!-- 子路由出口 -->
    <router-view />
    <!-- /子路由出口 -->

    <!-- 底部导航栏 -->
    <van-tabbar v-model="active" route active-color="rgb(0, 196, 106)" inactive-color="#000">
      <van-tabbar-item icon="home-o" to="/account">Account</van-tabbar-item>
      <van-tabbar-item icon="comment-o" to="/transer">Transer</van-tabbar-item>
      <van-tabbar-item icon="friends" to="/merchant">Merchant</van-tabbar-item>
      <van-tabbar-item icon="manager-o" to="/order">Order</van-tabbar-item>
    </van-tabbar>
    <!-- /底部导航栏 -->
  </div>
</template>

<script>
export default {
  name: 'LayoutIndex',
  components: {},
  props: {},
  data () {
    return {
      active: 1
    }
  },
  computed: {},
  watch: {},
  created () {},
  mounted () {},
  methods: {}
}
</script>

<style scoped lang="less"></style>
