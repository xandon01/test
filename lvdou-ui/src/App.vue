<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
export default {
    name: 'App'
}
</script>>
<style lang="less">
  .login-btn-wrap {
    padding: 26px 16px;
    .login-btn {
      // background-color: rgb(0, 196, 106);
      border: none;
      .van-button__text {
        font-size: 15px;
      }
    }
  }

</style>
