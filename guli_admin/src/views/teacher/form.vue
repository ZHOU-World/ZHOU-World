<template>
  <div style="padding:50px">
    <el-form
      ref="form"
      label-width="80px">
      <el-form-item label="讲师姓名">
        <el-input v-model="teacher.name"/>
      </el-form-item>
      <el-form-item label="讲师简介">
        <el-input v-model="teacher.intro"/>
      </el-form-item>
      <el-form-item label="讲师资历">
        <el-input v-model="teacher.career"/>
      </el-form-item>
      <el-form-item label="讲师头衔">
        <el-select v-model="teacher.level">
          <el-option :value="1" label="高级讲师"/>
          <el-option :value="2" label="首席讲师"/>
        </el-select>
      </el-form-item>
      <!-- 讲师头像 -->
      <el-form-item label="讲师头像">
        <el-upload
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload"
          :action="uploadUrl"
          class="avatar-uploader"
        >
          <img
            v-if="teacher.avatar"
            :src="teacher.avatar"
            class="avatar">
          <i v-else class="el-icon-plus avatar-uploader-icon"/>
        </el-upload>
      </el-form-item>
      <el-form-item label="讲师排序">
        <el-input-number v-model="teacher.sort" :min="1"/>
      </el-form-item>
      <el-form-item label="入驻时间">
        <el-date-picker
          v-model="teacher.joinDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择日期"/>
      </el-form-item>
      <el-form-item>
        <el-button :disabled="flag" type="primary" @click="handleSaveOrUpdateTeacher">提交</el-button>
        <el-button @click="clear">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import teacherApi from '@/api/teacher' // js语法，必须写入script中
export default {
  data() {
    return {
      // uploadUrl: 'http://localhost:8120/admin/oss/file/upload?module=avatar',
      uploadUrl: process.env.BASE_API + '/admin/oss/file/upload?module=avatar',
      flag: false,
      teacher: {
        sort: 1
      }
    }
  },
  created() {
    console.log('tid:' + this.$route.params.tid)
    if (this.$route.params.tid) {
      this.getTeacher()
    }
  },
  methods: {
    // 上传成功，res表示响应对象
    handleAvatarSuccess(res, file) {
      console.log(res)
      this.teacher.avatar = res.data.path
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
    // 提交按钮的单击事件
    handleSaveOrUpdateTeacher() {
      this.flag = true // 防止表单重复提交
      if (this.$route.params.tid) {
        this.update()
      } else {
        this.save()
      }
    },
    // 更新讲师信息
    update() {
      teacherApi.update(this.teacher).then(r => {
        this.$message.success('更新讲师成功')
        this.$router.push('/teacher/list')
        this.flag = false
      })
    },
    // 根据id进行回显
    getTeacher() {
      teacherApi.getById(this.$route.params.tid).then(r => {
        this.teacher = r.data.item
      })
    },
    save() {
      teacherApi.save(this.teacher).then(r => {
        this.$message.success('新增讲师成功')
        // 跳转到讲师列表页面
        // this为当前的vue对象
        // this.$router代表设置给VUE对象的路由对象（router/index.js包含所有路由的配置）
        // this.$route代表访问当前页面的路由对象，页面在router/index.js映射当前页面的路由设置
        this.$router.push('/teacher/list')
        this.flag = false
      })
    },
    clear() {
      this.teacher = {}
    }
  }
}
</script>
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
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>
