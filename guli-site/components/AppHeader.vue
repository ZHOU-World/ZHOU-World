<template>
  <!-- 公共头 -->
  <header id="header">
    <section class="container">
      <h1 id="logo">
        <a href="#" title="谷粒学院">
          <img src="~/assets/img/logo.png" width="100%" alt="谷粒学院">
        </a>
      </h1>
      <div class="h-r-nsl">
        <ul class="nav">
          <router-link to="/" tag="li" active-class="current" exact>
            <a>首页</a>
          </router-link>
          <router-link to="/course" tag="li" active-class="current">
            <a>课程</a>
          </router-link>
          <router-link to="/teacher" tag="li" active-class="current">
            <a>名师</a>
          </router-link>
          <router-link to="/article" tag="li" active-class="current">
            <a>文章</a>
          </router-link>
          <router-link to="/question" tag="li" active-class="current">
            <a>问答</a>
          </router-link>
        </ul>
        <!-- / nav -->
        <ul class="h-r-login">
          <li v-if="!member.id" id="no-login">
            <a href="/login" title="登录">
              <em class="icon18 login-icon">&nbsp;</em>
              <span class="vam ml5">登录</span>
            </a>
            |
            <a href="/register" title="注册">
              <span class="vam ml5">注册</span>
            </a>
          </li>
          <!-- 注意undis将当前节点隐藏了 -->
          <li v-if="member.id" id="is-login-one" class="mr10">
            <a id="headerMsgCountId" :href="`/ucenter/message`" title="消息">
              <em class="icon18 news-icon">&nbsp;</em>
            </a>
            <q class="red-point">&nbsp;</q>
          </li>
          <!-- 注意undis将当前节点隐藏了 -->
          <li v-if="member.id" id="is-login-two" class="h-r-user">
            <a :href="`/ucenter/${member.id}`" title>
              <img
                v-if="member.avatar"
                :src="member.avatar"
                width="30"
                height="30"
                class="vam picImg"
                alt
              >
              <img
                v-else
                src="~/assets/img/avatar-boy.gif"
                width="30"
                height="30"
                class="vam picImg"
                alt
              >
              <span id="userName" class="vam disIb">{{ member.nickname }}</span>
            </a>
            <a href="javascript:void(0)" title="退出" class="ml5" @click="handleLogout">退出</a>
          </li>
          <!-- /未登录显示第1 li；登录后显示第2，3 li -->
        </ul>
        <aside class="h-r-search">
          <form action="#" method="post">
            <label class="h-r-s-box">
              <input type="text" placeholder="搜索课程" name="queryCourse.courseName" value>
              <button type="submit" class="s-btn">
                <em class="icon18">&nbsp;</em>
              </button>
            </label>
          </form>
        </aside>
      </div>
      <aside class="mw-nav-btn">
        <div class="mw-nav-icon"/>
      </aside>
      <div class="clear"/>
    </section>
  </header>
  <!-- /公共头 -->
</template>
<script>
import local from '~/utils/local'
import ucenterApi from '~/api/ucenter'
export default {
  data() {
    return {
      member: {}
    }
  },
  mounted() { // 解析jwt
  // 登录分两种情况
    // 1、微信登录登录成功后浏览器重定向到访问首页，地址栏中携带token,token的值就是登录成功后的jwt，可以将token存到localstorage中
    if (this.$route.query.token) {
      local.save('GULI-JWT', this.$route.query.token) // 获取token的值存到localstorage中
      window.location = '/' // 刷新页面，去掉地址栏中的token参数
    }
    // 2、用户在login登录页面登录成功，登录成功返回jwt保存在localstorage，以下就是登录页面登录
    // 如果localstorage中有用户登录成功的jwttoken[GULI-JWT],代表已登录，解析jwt中的用户信息转为member对象
    const guliJwt = local.fetch('GULI-JWT')
    // 获取localstorage中的用户数据，如果存在，不用获取提交请求获取用户数据
    const userJsonStr = local.fetch('GULI-User')
    if (userJsonStr) {
    // 有用户信息的缓存，无需查询，将字符串转为json对象，赋值给member
      this.member = JSON.parse(userJsonStr)
      return // 结束，mounted后面的代码不需要执行
    }
    if (guliJwt) {
      // 已登录（需要获取jwt的数据，此处通过后端解析jwt数据，js解析不会）
      // 提交异步请求，在请求头中携带token=guliJwt,后端提供接口解析jwt返回jwt中的用户数据的json对象
      ucenterApi.getUserInfo(guliJwt).then(r => {
        this.member = r.data.item
        // 将用户信息也存到localstorage中，本地缓存对象时会直接将对象的object字符串存入到本地，以后不能使用
        // 需要将对象转为json数据存入浏览器
        local.save('GULI-User', JSON.stringify(r.data.item)) // 直接保存的是	[object Object]
        console.log(r.data)
      })
    }
  },
  methods: {
    // 注销功能
    handleLogout() { // 删除登录缓存的token即可
      this.$confirm('确定退出吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 删除 登录缓存的token即可
        local.remove('GULI-JWT')
        // 删除保存在localstorage中的token
        local.remove('GULI-User')
        this.$message({
          type: 'success',
          message: '退出成功!'
        })
        // 刷新当前页面
        // this.$router.push('/') //路由刷新只是把nuxt中的内容重新渲染
        window.location = '/' // 让浏览器重新访问首页，整个页面强制重新渲染
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消退出'
        })
      })
    }
  }
}
</script>
