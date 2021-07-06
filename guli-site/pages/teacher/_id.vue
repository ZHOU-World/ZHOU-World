<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- 讲师介绍 开始 -->
    <section class="container">
      <header class="comm-title">
        <h2 class="fl tac">
          <span class="c-333">讲师介绍</span>
        </h2>
      </header>
      <div class="t-infor-wrap">
        <!-- 讲师基本信息 开始 -->
        <section class="fl t-infor-box c-desc-content">
          <div class="mt20 ml20">
            <section class="t-infor-pic">
              <img v-if="teacher.avatar" :src="teacher.avatar" :alt="teacher.name">
              <img v-else :alt="teacher.name" src="https://210108sh.oss-cn-shanghai.aliyuncs.com/avatar/default.jpg">
            </section>
            <h3 class="hLh30">
              <span class="fsize24 c-333">{{ teacher.name }}
                &nbsp;{{ teacher.level===1?'高级讲师':'首席讲师' }}
              </span>
            </h3>
            <section class="mt10">
              <span class="t-tag-bg">{{ teacher.intro }}</span>
            </section>
            <section class="t-infor-txt">
              <p class="mt20">{{ teacher.career }}</p>
            </section>
            <div class="clear"/>
          </div>
        </section>
        <!-- /讲师基本信息 结束 -->
        <div class="clear"/>
      </div>
      <section class="mt30">
        <div>
          <header class="comm-title all-teacher-title c-course-content">
            <h2 class="fl tac">
              <span class="c-333">主讲课程</span>
            </h2>
            <section class="c-tab-title">
              <a href="javascript: void(0)">&nbsp;</a>
            </section>
          </header>
          <!-- 无数据提示 开始-->
          <section v-if="!courses" class="no-data-wrap">
            <em class="icon30 no-data-ico">&nbsp;</em>
            <span class="c-666 fsize14 ml10 vam">没有相关数据，小编正在努力整理中...</span>
          </section>
          <!-- /无数据提示 结束-->

          <!-- 课程列表 开始-->
          <article v-if="courses" class="comm-course-list">
            <ul class="of">
              <li v-for="course in courses" :key="course.id">
                <div class="cc-l-wrap">
                  <section class="course-img">
                    <img :alt="course.title" :src="course.cover" class="img-responsive">
                    <div class="cc-mask">
                      <a :href="`/course/`+course.id" title="开始学习" class="comm-btn c-btn-1">开始学习</a>
                    </div>
                  </section>
                  <h3 class="hLh30 txtOf mt10">
                    <a :href="`/course/${course.id}`" :title="course.title" class="course-title fsize18 c-333">{{ course.title }}</a>
                  </h3>
                </div>
              </li>
            </ul>
            <div class="clear"/>
          </article>
          <!-- /课程列表 结束-->
        </div>
      </section>
    </section>
    <!-- /讲师介绍 结束 -->
  </div>
</template>
<script>
import TeacherApi from '~/api/teacher'
import CourseApi from '~/api/course'

export default {
  // 服务端渲染
  // nuxt为方便使用路由的其他信息，把所有访问当前页面的参数都封装为page/context对象
  async asyncData(context) {
    console.log(context.params.id)
    // console.log(context.route.params.id)
    const teacherRes = await TeacherApi.getTeacher(context.params.id)
    const coursesRes = await CourseApi.listByTeacherId(context.params.id)
    // console.log(teacherRes.data.item, coursesRes.data.item)
    return {
      teacher: teacherRes.data.item,
      courses: coursesRes.data.items
    }
  }
}
</script>
