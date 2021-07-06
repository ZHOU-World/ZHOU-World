<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- 课程列表 开始 -->
    <section class="container">
      <header class="comm-title">
        <h2 class="fl tac">
          <span class="c-333">全部课程</span>
        </h2>
      </header>
      <section class="c-sort-box">
        <section class="c-s-dl">
          <dl>
            <dt>
              <span class="c-999 fsize14">课程类别</span>
            </dt>
            <dd class="c-s-dl-li">
              <!-- 一级类别 开始-->
              <ul class="clearfix">
                <li>
                  <a
                    title="全部"
                    href="javascript:void(0);">全部</a>
                </li>
                <li class="current">
                  <a
                    title="后端开发"
                    href="javascript:void(0);">后端开发</a>
                </li>
                <li>
                  <a
                    title="前端开发"
                    href="javascript:void(0);">前端开发</a>
                </li>
                <li>
                  <a
                    title="云计算"
                    href="javascript:void(0);">云计算</a>
                </li>
                <li>
                  <a
                    title="数据库"
                    href="javascript:void(0);">数据库</a>
                </li>
              </ul>
              <!-- /一级类别 结束-->
            </dd>
          </dl>
          <dl>
            <dt>
              <span class="c-999 fsize14"/>
            </dt>
            <dd class="c-s-dl-li">
              <!-- 二级类别 开始-->
              <ul class="clearfix">
                <li class="current">
                  <a
                    title="全部"
                    href="javascript:void(0);">全部</a>
                </li>
                <li>
                  <a
                    title="Java"
                    href="javascript:void(0);">Java</a>
                </li>
                <li>
                  <a
                    title="Python"
                    href="javascript:void(0);">Python</a>
                </li>
              </ul>
              <!-- /二级类别 结束-->
            </dd>
          </dl>
          <div class="clear"/>
        </section>
        <div class="js-wrap">
          <section class="fr">
            <span class="c-ccc">
              <i class="c-master f-fM">1</i>/
              <i class="c-666 f-fM">1</i>
            </span>
          </section>
          <section class="fl">
            <!-- 排序 开始-->
            <ol class="js-tap clearfix">
              <li class="current bg-green">
                <a title="销量" href="javascript:void(0);">销量
                  <i>↓</i>
                </a>
              </li>
              <li>
                <a title="最新" href="javascript:void(0);">最新
                </a>
              </li>
              <li>
                <a title="价格" href="javascript:void(0);">价格
                </a>
              </li>
            </ol>
            <!-- /排序 结束-->
          </section>
        </div>
        <div class="mt40">
          <!-- /无数据提示 开始-->
          <section v-if="!courses" class="no-data-wrap">
            <em class="icon30 no-data-ico">&nbsp;</em>
            <span class="c-666 fsize14 ml10 vam">没有相关数据，小编正在努力整理中...</span>
          </section>
          <!-- /无数据提示 结束-->
          <!-- 数据列表 开始-->
          <article v-if="courses" class="comm-course-list">
            <ul id="bna" class="of">
              <li v-for="course in courses" :key="course.id">
                <div class="cc-l-wrap">
                  <section class="course-img">
                    <img :alt="course.title" :src="course.cover" class="img-responsive">
                    <div class="cc-mask">
                      <a :href="`/course/${course.id}`" title="开始学习" class="comm-btn c-btn-1">开始学习</a>
                    </div>
                  </section>
                  <h3 class="hLh30 txtOf mt10">
                    <a :href="`/course/${course.id}`" :title="course.title" class="course-title fsize18 c-333">{{ course.title }}</a>
                  </h3>
                  <section class="mt10 hLh20 of">
                    <span v-if="course.price===0" class="fr jgTag bg-green">
                      <i class="c-fff fsize12 f-fA">免费</i>
                    </span>
                    <span v-else class="fr jgTag ">
                      <i class="c-orange fsize12 f-fA"> ￥{{ course.price }}</i>
                    </span>
                    <span class="fl jgAttr c-ccc f-fA">
                      <i class="c-999 f-fA">{{ course.viewCount }}人学习</i>
                      |
                      <i class="c-999 f-fA">{{ course.buyCount }}人购买</i>
                    </span>
                  </section>
                </div>
              </li>
            </ul>
            <div class="clear"/>
          </article>
          <!-- /数据列表 结束-->
        </div>
      </section>
    </section>
    <!-- /课程列表 结束 -->
  </div>
</template>
<script>
import courseApi from '~/api/course'
export default {
  // 服务端渲染
  async asyncData() {
    const query = { 'orderByColumn': 'buy_count', 'type': 1 }
    const response = await courseApi.listCourses(query)
    console.log(response)
    return {
      courses: response.data.items
    }
  }
}
</script>
