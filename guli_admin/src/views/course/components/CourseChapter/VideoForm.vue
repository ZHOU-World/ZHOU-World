<template>
  <div>
    <el-dialog :visible.sync="dialogFormVisible" :title="title">
      <el-form >
        <el-form-item :label-width="formLabelWidth" label="课时标题">
          <el-input v-model="video.title" auto-complete="off"/>
        </el-form-item>
        <el-form-item :label-width="formLabelWidth" label="课时排序">
          <el-input-number v-model="video.sort" :min="1"/>
        </el-form-item>
        <el-form-item :label-width="formLabelWidth" label="是否免费">
          <el-radio v-model="video.free" :label="1">免费</el-radio>
          <el-radio v-model="video.free" :label="0">默认</el-radio>
        </el-form-item>
        <!-- 上传视频 -->
        <el-form-item label="上传视频">
          <el-upload
            ref="upload"
            :auto-upload="false"
            :on-success="handleUploadSuccess"
            :on-error="handleUploadError"
            :on-exceed="handleUploadExceed"
            :file-list="fileList"
            :limit="1"
            :action="uploadUrl">
            <el-button slot="trigger" size="small" type="primary">选择视频</el-button>
            <el-button
              :disabled="uploadBtnDisabled"
              style="margin-left: 10px;"
              size="small"
              type="success"
              @click="submitUpload()">上传</el-button>
          </el-upload>
        </el-form-item>
        <!-- 上传视频结束 -->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button :disabled="flag" type="primary" @click="saveOrUpdateVideo">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import VideoApi from '@/api/video'
export default {
  data() {
    return {
      // uploadUrl: 'http://localhost:8130/admin/vod/media/upload', // 上传视频的接口地址
      uploadUrl: process.env.BASE_API + '/admin/vod/media/upload',
      dialogFormVisible: false,
      title: '添加课时',
      video: {
        sort: 1,
        free: 0
      },
      formLabelWidth: '80px',
      fileList: [], // 上传文件列表
      uploadBtnDisabled: false,
      flag: true
    }
  },
  methods: {

    // 视频上传成功的回调
    handleUploadSuccess(response, file, fileList) {
      this.uploadBtnDisabled = false
      if (response.success) {
        this.$message.success('视频上传成功')
        this.video.videoSourceId = response.data.id
        this.video.videoOriginalName = file.name // 本地上传文件对象
        this.flag = false
      } else {
        this.$message.error('上传失败1')
      }
      this.fileList = []
    },

    // 失败回调，响应报文状态码不是200时
    handleUploadError() {
      this.uploadBtnDisabled = false
      this.$message.error('上传失败2')
      this.fileList = []
    },
    // 上传
    submitUpload() {
      this.uploadBtnDisabled = true
      this.$refs.upload.submit() // 提交上传请求
    },
    // 上传多于一个视频
    handleUploadExceed(files, fileList) {
      this.$message.warning('想要重新上传视频，请先删除列表中的视频')
    },
    open(id) {
      this.title = '更新课时'
      // 回显要更新的video内容
      VideoApi.getById(id).then(r => {
        this.video = r.data.item
        this.video.free = this.video.free ? 1 : 0
        this.dialogFormVisible = true
      })
    },
    saveOrUpdateVideo() {
      if (this.video.id) {
        // 更新
        this.update()
      } else {
        // 新增
        this.save()
      }
    },
    update() {
      VideoApi.updateVideo(this.video).then(r => {
        this.$message.success('更新课时成功')
        // 关闭对话框
        this.dialogFormVisible = false
        // 刷新页面，调用父组件中最新的章节嵌套集合
        this.$parent.listNestedChapter()
      })
    },
    save() {
      VideoApi.saveVideo(this.video).then(r => {
        this.$message.success('新增课时成功')
        // 关闭对话框
        this.dialogFormVisible = false
        // 刷新页面
        this.$parent.listNestedChapter()
      })
    }
  }
}
</script>
