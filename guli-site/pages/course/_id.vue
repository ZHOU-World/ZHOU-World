<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- 课程详情 开始 -->
    <section class="container">

      <!-- 课程所属分类 开始 -->
      <section class="path-wrap txtOf hLh30">
        <a href="/" title class="c-999 fsize14">首页</a>
        \
        <a href="/course" title class="c-999 fsize14">课程列表</a>
        \
        <a href="#" class="c-333 fsize14">{{ course.subjectParentTitle }}</a>
        \
        <a href="#" class="c-333 fsize14">{{ course.subjectTitle }}</a>
      </section>
      <!-- /课程所属分类 结束 -->

      <!-- 课程基本信息 开始 -->
      <div>
        <article class="c-v-pic-wrap" style="height: 357px;">
          <section id="videoPlay" class="p-h-video-box">
            <img :src="course.cover" :alt="course.title" class="dis c-v-pic">
          </section>
        </article>
        <aside class="c-attr-wrap">
          <section class="ml20 mr15">
            <h2 class="hLh30 txtOf mt15">
              <span class="c-fff fsize24">{{ course.title }}</span>
            </h2>
            <section class="c-attr-jg">
              <span class="c-fff">价格：</span>
              <b class="c-yellow" style="font-size:24px;">￥{{ course.price }}</b>
            </section>
            <section class="c-attr-mt c-attr-undis">
              <span class="c-fff fsize14">主讲：{{ course.teacherName }}&nbsp;&nbsp;&nbsp;</span>
            </section>
            <section class="c-attr-mt of">
              <span class="ml10 vam">
                <em class="icon18 scIcon"/>
                <a class="c-fff vam" title="收藏" href="#" >收藏</a>
              </span>
            </section>
            <section class="c-attr-mt">
              <a v-if="isBuy" href="#" title="立即观看" class="comm-btn c-btn-3">立即观看</a>
              <a v-else :disabled="flag" href="javascript:void(0)" title="立即购买" class="comm-btn c-btn-3" @click="createOrder">立即购买</a>
            </section>
          </section>
        </aside>
        <aside class="thr-attr-box">
          <ol class="thr-attr-ol">
            <li>
              <p>&nbsp;</p>
              <aside>
                <span class="c-fff f-fM">购买数</span>
                <br>
                <h6 class="c-fff f-fM mt10">{{ course.buyCount }}</h6>
              </aside>
            </li>
            <li>
              <p>&nbsp;</p>
              <aside>
                <span class="c-fff f-fM">课时数</span>
                <br>
                <h6 class="c-fff f-fM mt10">{{ course.lessonNum }}</h6>
              </aside>
            </li>
            <li>
              <p>&nbsp;</p>
              <aside>
                <span class="c-fff f-fM">浏览数</span>
                <br>
                <h6 class="c-fff f-fM mt10">{{ course.viewCount }}</h6>
              </aside>
            </li>
          </ol>
        </aside>
        <div class="clear"/>
      </div>
      <!-- /课程基本信息 结束 -->

      <div class="mt20 c-infor-box">
        <article class="fl col-7">
          <section class="mr30">
            <div class="i-box">
              <div>
                <section id="c-i-tabTitle" class="c-infor-tabTitle c-tab-title">
                  <a name="c-i" class="current" title="课程详情">课程详情</a>
                </section>
              </div>
              <article class="ml10 mr10 pt20">

                <!-- 课程详情介绍 开始 -->
                <div>
                  <h6 class="c-i-content c-infor-title">
                    <span>课程介绍</span>
                  </h6>
                  <div class="course-txt-body-wrap">
                    <!-- 将内容中的html翻译过来，vue默认会对字符串中的html代码进行转义处理 -->
                    <!-- v-html 不对字符串的html代码进行转义处理-->
                    <section class="course-txt-body" v-html="course.description"/>
                  </div>
                </div>
                <!-- /课程详情介绍 结束 -->

                <!-- 课程大纲 开始-->
                <div class="mt50">
                  <h6 class="c-g-content c-infor-title">
                    <span>课程大纲</span>
                  </h6>
                  <section class="mt20">
                    <div class="lh-menu-wrap">
                      <menu id="lh-menu" class="lh-menu mt10 mr10">
                        <ul>
                          <!-- 课程章节目录 -->
                          <li v-for="chapter in course.chapters" :key="chapter.id" class="lh-menu-stair">
                            <a :title="chapter.title" href="javascript:void(0)" class="current-1">
                              <em class="lh-menu-i-1 icon18 mr10"/>{{ chapter.title }}
                            </a>
                            <ol class="lh-menu-ol" style="display: block;">
                              <li class="lh-menu-second ml30">
                                <a v-for="video in chapter.children" :key= "video.id" :title="video.title" href="javascript:void(0)" @click="handlePlayVideo(video)" >
                                  <span class="fr">
                                    <i >{{ video.videoOriginalName?video.videoOriginalName:'还未上传视频' }}</i>
                                  </span>
                                  <span v-if="video.free" class="fr">
                                    <i class="free-icon vam mr10">免费试听</i>
                                  </span>
                                  <em class="lh-menu-i-2 icon16 mr5">&nbsp;</em>{{ video.title }}
                                </a>

                              </li>
                            </ol>
                          </li>

                        </ul>
                      </menu>
                    </div>
                  </section>
                  <!-- /课程大纲 结束 -->
              </div></article>
            </div>
          </section>
        </article>
        <aside class="fl col-3">
          <div class="i-box">
            <!-- 主讲讲师 开始-->
            <div>
              <section class="c-infor-tabTitle c-tab-title">
                <a title href="javascript:void(0)">主讲讲师</a>
              </section>
              <section class="stud-act-list">
                <ul style="height: auto;">
                  <li>
                    <div class="u-face">
                      <a :href="`/teacher/${course.teacherId}`" target="_blank">
                        <img :src="course.avatar?course.avatar:'https://210108sh.oss-cn-shanghai.aliyuncs.com/avatar/default.jpg'" width="50" height="50" alt>
                      </a>
                    </div>
                    <section class="hLh30 txtOf">
                      <a :href="`/teacher/${course.teacherId}`" class="c-333 fsize16 fl" target="_blank">{{ course.teacherName }}</a>
                    </section>
                    <section class="hLh20 txtOf">
                      <span v-if="course.level===1" class="c-999">高级讲师</span>
                      <span v-if="course.level===2" class="c-999">首席讲师</span>
                    </section>
                  </li>
                </ul>
              </section>
            </div>
            <!-- /主讲讲师 结束 -->
          </div>
        </aside>
        <div class="clear"/>
      </div>
    </section>
    <!-- /课程详情 结束 -->
  </div>
</template>
<script>
import courseApi from '~/api/course'
import orderApi from '~/api/order'
import local from '~/utils/local'
export default {
  // 服务器渲染
  async asyncData(ctx) {
    const response = await courseApi.getById(ctx.params.id)
    console.log(response.data)
    return {
      course: response.data.item
    }
  },
  data() {
    return {
      flag: true,
      isBuy: false
    }
  },
  mounted() {
    // 用户已登录，查询当前用户是否已购买课程
    const token = local.fetch('GULI-JWT')
    if (token) {
      orderApi.isBuy(this.$route.params.id, token).then(r => {
        this.isBuy = r.data.isBuy
        if (!this.isBuy) {
          // 已登录未购买
          this.flag = false// 立即购买按钮可用
        }
      })
    } else {
      // 用户未登录
      this.flag = false// 立即购买按钮可用
    }
  },
  methods: {
    handlePlayVideo(video) {
      console.log(video)
      // 如果有视频，跳转到播放页面，并携带视频资源id跳转
      if (video.videoSourceId) {
        // 视频免费或者当前用户已经购买课程，跳转播放
        if (video.free || this.isBuy) {
          this.$message.success('即将播放视频' + video.videoSourceId)
          // 路由方式进入视频播放页面
          this.$router.push('/video/' + video.videoSourceId)
        } else { // 未登录
          if (!local.fetch('GULI-JWT')) {
            this.$message.warning('请先登录')
          } else { // 未购买
            this.$message.warning('先购买视频')
          }
        }
      } else {
      // 如果没有视频，给出提示
        this.$message.warning('无视频')
      }
    },
    // 创建订单
    createOrder() {
      const token = local.fetch('GULI-JWT')
      orderApi.createOrder(this.$route.params.id, token).then(r => {
        console.log(r.data.id)// 后台传过来的id值
        // 跳转课程详情页，并显示课程详情页的信息
        this.$router.push('/order/' + r.data.id)
      })
    }
  }
}
</script>
