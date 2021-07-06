<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- 讲师列表 开始 -->
    <section class="container">
      <header class="comm-title">
        <h2 class="fl tac">
          <span class="c-333">名师团队</span>
        </h2>
      </header>
      <section class="c-sort-box unBr">
        <div>
          <!-- /无数据提示 开始-->
          <section v-if="!teachers" class="no-data-wrap">
            <em class="icon30 no-data-ico">&nbsp;</em>
            <span class="c-666 fsize14 ml10 vam">没有相关数据，小编正在努力整理中...</span>
          </section>
          <!-- /无数据提示 结束-->
          <!-- /数据列表 开始-->
          <article v-if="teachers" class="i-teacher-list">
            <ul class="of">
              <li v-for="teacher in teachers" :key="teacher.id">
                <section class="i-teach-wrap">
                  <div class="i-teach-pic">
                    <a :title="teacher.name" :href="`/teacher/${teacher.id}`">
                      <img v-if="teacher.avatar" :alt="teacher.name" :src="teacher.avatar" width="142">
                      <img v-else :alt="teacher.name" src="https://210108sh.oss-cn-shanghai.aliyuncs.com/avatar/default.jpg" width="142">
                    </a>
                  </div>
                  <div class="mt10 hLh30 txtOf tac">
                    <a :title="teacher.name" :href="`/teacher/${teacher.id}`" class="fsize18 c-666">{{ teacher.name }}</a>
                  </div>
                  <div class="hLh30 txtOf tac">
                    <span v-if="teacher.level===1" class="fsize14 c-999" >高级讲师</span>
                    <span v-if="teacher.level===2" class="fsize14 c-999" >首席讲师</span>
                  </div>
                  <div class="mt15 i-q-txt">
                    <p class="c-999 f-fA">{{ teacher.career }}</p>
                  </div>
                </section>
              </li>
            </ul>
            <div class="clear"/>
          </article>
          <!-- /数据列表 结束-->
        </div>
      </section>
    </section>
    <!-- /讲师列表 结束 -->
  </div>
</template>
<script>
import teacherApi from '~/api/teacher'
export default {
  // 在交给浏览器之前，nuxt服务器主动执行此方法
  async asyncData() { // 执行时vue还未初始化
    const response = await teacherApi.list() // await代表阻塞，直到异步请求执行完毕
    console.log(response.data.items)
    return {
      teachers: response.data.items,
      test: 1
    }
  },
  data() {
    return {
      a: 1
    }
  },
  created() {
    this.listTeachers()
  },
  methods: {
    listTeachers() {
      teacherApi.list().then(r => {
        // console.log(r.data.items)
        this.teachers = r.data.items
      })
    }
  }
}
</script>
