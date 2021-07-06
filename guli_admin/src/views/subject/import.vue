<template>
  <div>
    <template>
      <div class="app-container">
        <el-form label-width="120px">
          <el-form-item label="信息描述">
            <el-tag type="info">excel模版说明</el-tag>
            <el-tag>
              <i class="el-icon-download"/>
              <a :href="defaultExcelTemplate">点击下载模版</a>
            </el-tag>
          </el-form-item>
          <el-form-item label="选择Excel">
            <el-upload
              ref="upload"
              :auto-upload="false"
              :on-exceed="fileUploadExceed"
              :on-success="fileUploadSuccess"
              :on-error="fileUploadError"
              :limit="1"
              :action="importUrl"
              name="file"
              accept="application/vnd.ms-excel">
              <el-button
                slot="trigger"
                size="small"
                type="primary">选取文件</el-button>
              <el-button
                :disabled="importBtnDisabled"
                style="margin-left: 10px;"
                size="small"
                type="success"
                @click="submitUpload()">导入</el-button>
            </el-upload>
          </el-form-item>
        </el-form>
      </div>
    </template>
  </div>
</template>
<script>
export default {
  data() {
    return {
      importUrl: 'http://localhost:8110/admin/edu/subject/import',
      defaultExcelTemplate: 'http://210108sh.oss-cn-shanghai.aliyuncs.com/excel2021/05/27/c24949f874b84f81b3441badcc74c553.xls', // 默认Excel模板
      importBtnDisabled: false // 导入按钮是否禁用
    }
  },
  methods: {
    // 上传多于一个文件时
    fileUploadExceed() {
      this.$message.warning('只能选取一个文件')
    },

    // 上传
    submitUpload() {
      this.importBtnDisabled = true// 是否禁用
      // this.$refs代表vue对象渲染的范围内定义了ref属性的标签（组件）集合
      // 根据ref属性的值可以从$ref中获取对应的标签对象，含有ref属性值为upload所在的标签
      this.$refs.upload.submit() // 提交上传请求
    },

    fileUploadSuccess(response) {
      this.$message.success('导入成功')
      this.importBtnDisabled = false
    },

    fileUploadError(response) {

    }
  }
}
</script>
