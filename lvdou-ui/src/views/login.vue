<template>
  <div class="login-container">
    <!-- 导航栏 -->
    <van-nav-bar
      class="app-nav-bar"
      title="GreenBean-Login"
      left-arrow
      @click-left="$router.back()"
    />
    <!-- /导航栏 -->

    <!-- 登录表单 -->
    <van-cell-group>
      <van-field
        v-model="user.phone"
        icon-prefix="toutiao"
        left-icon="shouji"
        placeholder="Please input mobile phone no"
      />
      <van-field
        v-model="user.code"
        clearable
        icon-prefix="toutiao"
        left-icon="yanzhengma"
        placeholder="Please input verfication code"
      >
        <template #button>
          <van-button
            class="send-btn"
            size="mini"
            round
          >Verfication code</van-button>
        </template>
      </van-field>
    </van-cell-group>
    <div class="login-btn-wrap">
      <van-button class="login-btn"  type="primary" block  @click="login()">Login</van-button>
    </div>
    <!-- /登录表单 -->
  </div>
</template>

<script>
import qs from 'qs'
import {login} from '@/api/user'

export default {
  name: 'LoginIndex',
  components: {},
  props: {},
  data () {
    return {
      user: {
         phone: '', // 手机号
         code: '' // 验证码
      }
    }
  },
  computed: {},
  watch: {},
  created () {},
  mounted () {},
  methods: {
    async login(){
        const res = await login(qs.stringify(this.user))
          this.$toast.success({
            icon: 'like-o',
            forbidClick: true,
            message: 'Login sucess'
          });
          let username = this.user.phone
          sessionStorage.setItem("username",username)
          this.$router.push({ path: "/account" }); //页面跳转
    }
  }
}
</script>

<style scoped lang="less">
.login-container {
  .send-btn {
    width: 76px;
    height: 23px;
    background-color: #ededed;
    .van-button__text {
      font-size: 11px;
      color: #666666;
    }
  }
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
}
</style>
