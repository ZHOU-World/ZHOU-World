<template>
  <div>
    <!--课程预览-->
    <div class="ccInfo">
      <img :src="coursePublish.cover">
      <div class="main">
        <h2>{{ coursePublish.title }}</h2>
        <p class="gray"><span>共{{ coursePublish.lessonNum }}课时</span></p>
        <p><span>所属分类：{{ coursePublish.subjectParentTitle }} — {{ coursePublish.subjectTitle }}</span></p>
        <p>课程讲师：{{ coursePublish.teacherName }}</p>
        <h3 class="red">￥{{ coursePublish.price }}</h3>
      </div>
    </div>
    <div style="text-align:center">
      <el-button :disabled="prevflag" type="primary" plain @click="prev">上一步</el-button>
      <el-button :disabled="nextflag" type="primary" plain @click="publish">发布课程</el-button>
    </div>
  </div>
</template>

<script>
import courseApi from '@/api/course'
export default {
  data() {
    return {
      prevflag: false,
      nextflag: false,
      coursePublish: {}
    }
  },
  created() {
    this.getCoursePublish()
  },
  methods: {
    getCoursePublish() {
      courseApi.getCoursePublish(this.$parent.courseId).then(r => {
        console.log(r.data.item)
        this.coursePublish = r.data.item
      })
    },
    prev() {
      this.prevflag = true
      this.$parent.active = 1
    },
    // 发布课程
    publish() {
      this.nextflag = false
      courseApi.coursePublish(this.$parent.courseId).then(r => {
        this.$message.success('课程发布成功')
        this.$parent.active = 3
      })
    }
  }
}
</script>
<style>
.ccInfo {
    background: #f5f5f5;
    padding: 20px;
    overflow: hidden;
    border: 1px dashed #DDD;
    margin-bottom: 40px;
    position: relative;
}
.ccInfo img {
    background: #d6d6d6;
    width: 500px;
    height: 278px;
    display: block;
    float: left;
    border: none;
}
.ccInfo .main {
    margin-left: 520px;
}

.ccInfo .main h2 {
    font-size: 28px;
    margin-bottom: 30px;
    line-height: 1;
    font-weight: normal;
}
.ccInfo .main p {
    margin-bottom: 10px;
    word-wrap: break-word;
    line-height: 24px;
    max-height: 48px;
    overflow: hidden;
}

.ccInfo .main p {
    margin-bottom: 10px;
    word-wrap: break-word;
    line-height: 24px;
    max-height: 48px;
    overflow: hidden;
}
.ccInfo .main h3 {
    left: 540px;
    bottom: 20px;
    line-height: 1;
    font-size: 28px;
    color: #d32f24;
    font-weight: normal;
    position: absolute;
}
</style>
