<template>
  <div style="padding:15px" class="app-container">
    <!--表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-date-picker
          v-model="searchObj.begin"
          type="date"
          placeholder="选择开始日期"
          value-format="yyyy-MM-dd" />
      </el-form-item>
      <el-form-item>
        <el-date-picker
          v-model="searchObj.end"
          type="date"
          placeholder="选择截止日期"
          value-format="yyyy-MM-dd" />
      </el-form-item>
      <el-button
        :disabled="btnDisabled"
        type="primary"
        icon="el-icon-search"
        @click="showChart()">查询</el-button>
    </el-form>

    <el-row>
      <el-col :span="12">
        <div id="register_num" class="chart" style="height:500px;" />
      </el-col>
      <el-col :span="12">
        <div id="login_num" class="chart" style="height:500px;" />
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <div id="video_view_num" class="chart" style="height:500px;" />
      </el-col>
      <el-col :span="12">
        <div id="course_num" class="chart" style="height:500px;" />
      </el-col>
    </el-row>
  </div>
</template>
<script>
import staApi from '@/api/statistics/'
import echarts from 'echarts'
export default {
  data() {
    return {
      searchObj: {},
      btnDisabled: false,
      xData: [],
      loginYData: [],
      videoNumYData: [],
      courseNumYData: [],
      registNumYData: []
    }
  },
  methods: {
    initEcharts(id, xData, yData, title) {
      var chartDom = document.getElementById(id)
      var myChart = echarts.init(chartDom)
      var option

      option = {
        title: {
          text: title
        },
        xAxis: {
          type: 'category',
          data: xData
        },
        yAxis: {
          type: 'value'
        },
        series: [{
          data: yData,
          type: 'line'
        }]
      }

      option && myChart.setOption(option)
    },
    showChart() {
      staApi.getSta(this.searchObj.begin, this.searchObj.end).then(r => {
        this.xData = r.data.xData
        this.loginYData = r.data.loginNumYData
        this.videoNumYData = r.data.videoNumYData
        this.courseNumYData = r.data.courseNumYData
        this.registNumYData = r.data.registNumYData

        this.initEcharts('register_num', this.xData, this.registNumYData, '会员注册数量')
        this.initEcharts('login_num', this.xData, this.loginYData, '会员登录数量')
        this.initEcharts('video_view_num', this.xData, this.videoNumYData, '视频播放数量')
        this.initEcharts('course_num', this.xData, this.courseNumYData, '课程发布数量')
        // console.log(r.data)
      })
    }
  }
}
</script>
