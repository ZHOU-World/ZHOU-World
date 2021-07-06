<template>
  <div style="padding:15px">
    <el-button type="primary" @click="showSaveChapterDialog">添加章节</el-button>
    <!-- 章节列表 -->
    <ul class="chapterList">
      <li
        v-for="chapter in chapterList"
        :key="chapter.id"
        遍历>
        <p>
          {{ chapter.title }}
          <span class="acts">
            <el-button type="text" @click="showSaveVideoDialog(chapter.id)">添加课时</el-button>
            <el-button type="text" @click="handleEditChapter(chapter.id)">编辑</el-button>
            <el-button type="text" @click="handleDeleteChapter(chapter)">删除</el-button>
          </span>
        </p>
        <!-- 视频 -->
        <ul class="chapterList videoList">
          <li
            v-for="video in chapter.children"
            :key="video.id">
            <p>
              {{ video.title }}
              <el-tag v-if="!video.videoSourceId" size="mini" type="danger">
                {{ '尚未上传视频' }}
              </el-tag>
              <span class="acts">
                <el-tag v-if="video.free" size="mini" type="success">{{ '免费观看' }}</el-tag>
                <el-button type="text" @click="showEditVideoDialog(video)">编辑</el-button>
                <el-button type="text" @click="handleDeleteVideo(video)">删除</el-button>
              </span>
            </p>
          </li>
        </ul>
      </li>
    </ul>

    <div style="text-align:center;">
      <el-button :disabled="prevflag" type="primary" @click="prev">上一步</el-button>
      <el-button :disabled="nextflag" type="primary" @click="next">下一步</el-button>
    </div>
    <chapter-form ref="chapterDialog"/>
    <video-form ref="videoDialog"/>
  </div>
</template>
<script>
import ChapterForm from '@/views/course/components/CourseChapter/ChapterForm'
import ChapterApi from '@/api/chapter'
import VideoForm from '@/views/course/components/CourseChapter/VideoForm'
import VideoApi from '@/api/video'

export default {
  // 注册
  components: {
    ChapterForm, VideoForm
  },
  data() {
    return {
      prevflag: false,
      nextflag: false,
      chapterList: []
    }
  },
  created() {
    this.listNestedChapter()
  },
  methods: {
    // 编辑课时
    showEditVideoDialog(video) {
      // 根据id查询课时的数据,进入父组件查询.此处调用
      this.$refs.videoDialog.open(video.id)
    },
    // 删除课时
    handleDeleteVideo(video) {
      this.$confirm(`此操作将永久删除该【${video.title}】, 是否继续? `, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        VideoApi.remove(video.id).then(r => {
          this.$message.success('删除成功')
          // 刷新页面
          this.listNestedChapter()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    // 添加课时单击事件
    showSaveVideoDialog(chapterId) {
      // console.log('.......')
      this.$refs.videoDialog.video = { sort: 1, free: 0 }
      this.$refs.videoDialog.title = '添加课时'
      // 设置课程和章的id赋值给课时对象的相应属性
      // 课程的id赋值给课时对象的courseId属性
      this.$refs.videoDialog.video.courseId = this.$parent.courseId // 课时ID
      // 显示新增课时对话框之前，将章的id设置给课时
      this.$refs.videoDialog.video.chapterId = chapterId // 章的ID
      this.$refs.videoDialog.dialogFormVisible = true
    },
    // 点击删除单击事件
    handleDeleteChapter(chapter) {
      if (chapter.children && chapter.children.length > 0) { // 这里面的chapter是局部变量，参数传递过来，不需要加this.
        this.$message.warning('请先删除课时信息')
        return
      }
      this.$confirm(`此操作将永久删除该【${chapter.title}】, 是否继续? `, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        ChapterApi.remove(chapter.id).then(r => {
          this.$message.success('删除成功')
          // 刷新页面
          this.listNestedChapter()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    // 点击编辑，回显要编辑的章信息到对话框中
    handleEditChapter(id) {
      // 根据id查询章的数据,进入父组件查询.此处调用
      this.$refs.chapterDialog.open(id)
      // this.$refs.chapterDialog.dialogFormVisible = true
    },
    listNestedChapter() {
      ChapterApi.listNestedChapter(this.$parent.courseId).then(r => {
        this.chapterList = r.data.items
      })
    },
    showSaveChapterDialog() {
      // 弹出的对话框新增章数据
      this.$refs.chapterDialog.title = '添加章节'
      this.$refs.chapterDialog.chapter = { sort: 1 }
      // 弹出对话框
      this.$refs.chapterDialog.dialogFormVisible = true // 修改子组件的属性
    },
    prev() {
      this.prevflag = true
      this.$parent.active = 0
    },
    next() {
      this.nextflag = false
      this.$parent.active = 2
    }
  }
}
</script>

<style>
.chapterList{
    position: relative;
    list-style: none;
    margin: 0;
    padding: 0;
}
.chapterList li{
  position: relative;
}
.chapterList p{
  float: left;
  font-size: 20px;
  margin: 10px 0;
  padding: 10px;
  height: 70px;
  line-height: 50px;
  width: 100%;
  border: 1px solid #DDD;
}
.chapterList .acts {
    float: right;
    font-size: 14px;
}

.videoList{
  padding-left: 50px;
}
.videoList p{
  float: left;
  font-size: 14px;
  margin: 10px 0;
  padding: 10px;
  height: 50px;
  line-height: 30px;
  width: 100%;
  border: 1px dashed #DDD;
}
</style>
