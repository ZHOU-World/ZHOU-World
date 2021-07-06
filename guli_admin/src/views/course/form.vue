<template>
  <div>
    <!-- 三个页面共有的部分 -->
    <h3 style="text-align:center;">发布新课程</h3> <!--标题标签独占一行 -->
    <!-- elementUI步骤条 -->
    <el-steps :active="active" simple>
      <el-step title="填写课程基本信息" icon="el-icon-edit"/>
      <el-step title="创建课程大纲" icon="el-icon-upload"/>
      <el-step title="发布课程" icon="el-icon-picture"/>
    </el-steps>
    <course-info v-if="active===0"/>
    <course-chapter v-if="active===1"/>
    <course-publish v-if="active===2 ||active===3"/>
  </div>
</template>
<script>
// 使用子标签
// 1、引入vue组件
import CourseInfo from '@/views/course/components/CourseInfo'
import CourseChapter from '@/views/course/components/CourseChapter' // 默认找index.vue
import CoursePublish from '@/views/course/components/CoursePublish'
export default {
  components: {// 2、注册vue组件
    CourseInfo, CourseChapter, CoursePublish
  },
  data() {
    return {
      active: 0
    }
  }
}
</script>
