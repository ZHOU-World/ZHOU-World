<template>
  <div>
    <el-dialog :visible.sync="dialogFormVisible" :title="title">
      <el-form >
        <el-form-item :label-width="formLabelWidth" label="章节标题">
          <el-input v-model="chapter.title" autocomplete="off"/>
        </el-form-item>
        <el-form-item :label-width="formLabelWidth" label="章节排序">
          <el-input-number v-model="chapter.sort" :min="1"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveOrUpdateChapter">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import ChapterApi from '@/api/chapter'
export default {
  data() {
    return {
      title: '添加章节',
      chapter: {
        sort: 1
      },
      dialogFormVisible: false,
      formLabelWidth: '80px'
    }
  },
  created() {

  },
  methods: {
    // 编辑章信息，回显数据，显示对话框
    open(id) {
      ChapterApi.getById(id).then(r => {
        this.chapter = r.data.item
        this.title = '更新章节'
        this.dialogFormVisible = true
      })
    },
    // 新增/更新章节信息
    saveOrUpdateChapter() {
      if (this.chapter.id) {
        // 更新
        this.update()
      } else {
        // 新增
        this.save()
      }
    },
    save() {
      // 绑定当前章属于哪个课程，当前课程的id和课程id一样
      // form.vue → CourseChapter index.vue → CourseChapter ChapterForm.vue
      this.chapter.courseId = this.$parent.$parent.courseId
      ChapterApi.saveChapter(this.chapter).then(r => {
        this.$message.success('新增成功')
        // 清空缓存
        this.chapter = {
          sort: 1
        }
        this.dialogFormVisible = false // 关闭对话框
        this.$parent.listNestedChapter()
      })
    },
    update() {
      ChapterApi.update(this.chapter).then(r => {
        this.$message.success('更新成功')
        // 清除缓存
        this.chapter = {
          sort: 1
        }
        this.dialogFormVisible = false// // 关闭对话框
        // 调用父组件中最新的章节嵌套集合
        this.$parent.listNestedChapter()
      })
    }
  }
}
</script>
