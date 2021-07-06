<template>
  <div style="padding:15px" class="app-container">
    <!--表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="日期">
        <el-date-picker
          v-model="day"
          type="date"
          placeholder="选择要统计的日期"
          value-format="yyyy-MM-dd" />
      </el-form-item>

      <el-button
        :disabled="btnDisabled"
        type="primary"
        @click="genarateData()">生成</el-button>
    </el-form>
  </div>
</template>
<script>
import staApi from '@/api/statistics'
export default {
  data() {
    return {
      day: '',
      btnDisabled: false
    }
  },
  methods: {
    genarateData() {
      if (this.day.length <= 1) { // 校验日期格式，最好用正则取校验
        this.$message.warning('请选择日期')
        return
      }
      this.btnDisabled = true// 按钮禁用
      staApi.genSta(this.day).then(r => {
        this.$message.success('生成统计数据成功')
        this.btnDisabled = false// 生成之后按钮解除禁用
      })
    }
  }
}
</script>
