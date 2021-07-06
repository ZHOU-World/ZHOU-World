<template>
  <div style="padding:20px;">
    <el-form ref="form" label-width="100px">
      <el-form-item label="课程标题">
        <!--  到后台course实体类中查询属性-->
        <el-input v-model="course.title"/>
      </el-form-item>
      <el-form-item label="课程讲师">
        <el-select v-model="course.teacherId" placeholder="请选择讲师">
          <el-option
            v-for="item in teachers"
            :key="item.id"
            :label="item.name"
            :value="item.id"/>
        </el-select>
      </el-form-item>
      <el-form-item label="课程类别">
        <!-- 从后台javabean中找到course的属性 -->
        <el-select
          v-model="course.subjectParentId"
          placeholder="请选择课程父级别分类"
          @change="handleLevelOneSubjectChange">
          <el-option
            v-for="item in levelOneSubjects"
            :key="item.id"
            :label="item.title"
            :value="item.id"/>
        </el-select>
        <el-select v-model="course.subjectId" placeholder="请选择课程二级分类">
          <el-option
            v-for="item in levelTwoSubjects"
            :key="item.id"
            :label="item.title"
            :value="item.id"/>
        </el-select>
      </el-form-item>
      <!-- 标签需要写上，否则不是格式化，不对其 -->
      <el-form-item label="课程总课时">
        <el-input-number v-model="course.lessonNum" :min="0" controls-position="right"/>
      </el-form-item>
      <el-form-item label="课程简介">
        <tinymce v-model="course.description" />
      </el-form-item>
      <el-form-item label="课程封面">
        <el-upload
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload"
          :action="uploadUrl"
          class="avatar-uploader"
        >
          <img
            v-if="course.cover"
            :src="course.cover"
            class="avatar">
          <i v-else class="el-icon-plus avatar-uploader-icon"/>
        </el-upload>
      </el-form-item>
      <el-form-item label="课程价格">
        <el-input-number v-model="course.price" :min="0" controls-position="right"/>元
      </el-form-item>
    </el-form>

    <div style="text-align:center">
      <el-button :disabled="flag" type="primary" @click="saveAndNext">保存并下一步</el-button>
    </div>
  </div>
</template>
<script>
import teacherApi from '@/api/teacher'
import subjectApi from '@/api/subject'
import Tinymce from '@/components/Tinymce'
import courseApi from '@/api/course'

export default {
  components: { Tinymce },
  data() {
    return {
      // uploadUrl: 'http://localhost:8120/admin/oss/file/upload?module=cover',
      uploadUrl: process.env.BASE_API + '/admin/oss/file/upload?module=cover',
      flag: false,
      course: {
        subjectId: ''
      },
      teachers: [],
      levelOneSubjects: [],
      levelTwoSubjects: []
    }
  },
  created() {
    if (!this.$parent.courseId) { // 从父组件获取courseId,判断如果courseId不存在，代表第一次进入
      if (this.$route.query.active) { // 获取路径中的active值判断一下，如果存在
        this.$parent.active = Number(this.$route.query.active) // 接收active值并赋值给父组件
      }
      this.$parent.courseId = this.$route.params.id // 再将courseId获取赋值给父组件
    }
    // 如果存在courseId，代表不是编辑第一次进入的页面，不用再获取courseId和active的值
    subjectApi.listNestedSubjects().then(r => {
      this.levelOneSubjects = r.data.items

      // 如果父组件中courseId的属性值存在，代表本次已经提交过信息，查询回显
      if (this.$parent.courseId) {
        courseApi.getCourseInfo(this.$parent.courseId).then(r => {
          this.course = r.data.item
          // 回显数据已经有一级课程，但是二级还没有
          // 获取到一级课程的children属性值，就得到二级分类集合
          this.levelOneSubjects.forEach((levelOneSubject) => {
            if (levelOneSubject.id === this.course.subjectParentId) {
              this.levelTwoSubjects = levelOneSubject.children
              return
            }
          })
        })
      }
    })
    teacherApi.list().then(r => {
      this.teachers = r.data.items
    })
  },
  methods: {
    // 提交按钮的单击事件
    handleSaveOrUpdateTeacher() {
      this.flag = true // 防止表单重复提交
      if (this.$route.params.tid) {
        this.update()
      } else {
        this.save()
      }
    },
    // 上传成功后的回调，res表示响应对象
    handleAvatarSuccess(res, file) {
      console.log(res)
      this.course.cover = res.data.path
      // 强制VUE渲染,显示图片
      this.$forceUpdate()
    },
    // 上传文件格式大小的检查
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg'
      const isLt2M = file.size / 1024 / 1024 < 2

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!')
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
      }
      return isJPG && isLt2M
    },
    // 父级课程分类，内容改变的监听函数
    handleLevelOneSubjectChange(val) { // val为选中的一级分类的id
    // 清空二级分类的ID
      this.course.subjectId = ''
      console.log(val)
      // 根据一级分类的id查找一级分类对应的二级分类集合（children）
      this.levelOneSubjects.forEach((levelOneSubject) => {
        if (levelOneSubject.id === val) {
          this.levelTwoSubjects = levelOneSubject.children
          return
        }
      })
    },
    // 提交课程基本信息
    saveAndNext() {
      this.flag = true
      // 更新判断
      if (this.$parent.courseId) {
        // 更新
        this.update()
      } else {
        // 新增
        this.save()
      }
    },
    // 更新
    update() {
      courseApi.updateCourseInfo(this.course, this.$parent.courseId).then(r => {
        this.$message.success('更新课程基本信息成功')
        // 请求成功再跳转
        this.$parent.active = 1
      })
    },
    // 新增
    save() {
      // 当前页面以后在form页面中引入使用，父组件为form页面
      courseApi.saveCourseInfo(this.course).then(r => {
        this.$message.success('保存课程基本信息成功')
        this.$parent.courseId = r.data.id // 将保存课程的id存在父组件中
        // 请求成功再跳转
        this.$parent.active = 1
      })
    }
  }
}
</script>
<style>
		.tinymce-container {
				position: relative;
				line-height: normal;
					}
</style>
<style>
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 356px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 356px;
    height: 178px;
    display: block;
  }
</style>
