<template>
  <div class="main">
    <div class="title">
      <a href="/login">登录</a>
      <span>·</span>
      <a class="active" href="/register">注册</a>
    </div>

    <div class="sign-up-container">
      <form action="register">
        <div class="input-prepend restyle">
          <input
            v-model="member.nickname"
            type="text"
            placeholder="你的昵称">
          <i class="iconfont icon-user"/>
        </div>
        <div class="input-prepend restyle no-radius">
          <input
            v-model="member.mobile"
            type="text"
            placeholder="手机号">
          <i class="iconfont icon-phone"/>
        </div>
        <div class="input-prepend restyle no-radius" style="position:relative">
          <input
            v-model="member.code"
            type="text"
            placeholder="验证码">
          <i class="iconfont icon-yanzhengma"/>
          <a
            href="javascript:"
            type="button"
            style="position:absolute;right: 10px;top: 15px;"
            @click="getCodeFun()">{{ codeText }}</a>
        </div>
        <div class="input-prepend">
          <input
            v-model="member.password"
            type="password"
            placeholder="设置密码">
          <i class="iconfont icon-password"/>
        </div>
        <div class="btn">
          <input
            type="button"
            class="sign-up-button"
            value="注册"
            @click="submitRegister()">
        </div>
        <p class="sign-up-msg">
          点击 “注册” 即表示您同意并愿意遵守简书
          <br>
          <a target="_blank" href="http://www.jianshu.com/p/c44d171298ce">用户协议</a>
          和
          <a target="_blank" href="http://www.jianshu.com/p/2ov8x3">隐私政策</a> 。
        </p>
      </form>
      <!-- 更多注册方式 -->
      <div class="more-sign">
        <h6>社交帐号直接注册</h6>
        <ul>
          <li><a id="weixin" class="weixin" href="http://localhost:8160/api/ucenter/wx/login"><i class="iconfont icon-weixin"/></a></li>
          <li><a id="qq" class="qq" target="_blank" href="#"><i class="iconfont icon-qq"/></a></li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import '~/assets/css/sign.css'
import '~/assets/css/iconfont.css'
import ucenterApi from '~/api/ucenter'
export default {
  layout: 'sign',
  data() {
    return {
      member: {
        mobile: '',
        code: '',
        nickname: '',
        password: ''
      },
      sending: false, // 是否发送验证码
      second: 60, // 倒计时间
      codeText: '获取验证码',
      timer: {}
    }
  },
  methods: {
    // 获取验证码（发送验证码的请求）
    getCodeFun() {
      if (this.sending) { // 为true表示已经发送验证码
        return
      }
      this.sending = true// 表示下面的方法中不能重复点击
      // 先禁用发送验证码的按钮
      ucenterApi.sendMsg(this.member.mobile).then(r => {
        if (r.code === 20000) {
          this.$message.success('验证码已发送')
          this.timeDown()
        } else {
          // 修改sending的值，可以重新获取验证码
          this.sending = false
        }
      })
      // 修改发送验证码按钮显示倒计时，结束回复点击
    },

    // 倒计时
    timeDown() {
      this.codeText = this.second
      // js定时任务，每一秒减一,setTimeout()延时,setInterval()定时任务，没过一定时间执行一次
      this.timer = setInterval(() => {
        this.second--
        this.codeText = this.second
        if (this.codeTest === 0) { // 当秒数为零，停止定时，恢复按钮可用
          clearInterval(this.timer) // 清除定时任务
          this.codeText = '重新获取验证码'
          this.sending = false
        }
      }, 1000)
    },
    // 注册
    submitRegister() {
      ucenterApi.regist(this.member).then(r => {
        this.$message.success('注册成功')
        // 注册成功之后跳转到登录页面
        this.$router.push('/login')
      })
    }
  }
}
</script>
