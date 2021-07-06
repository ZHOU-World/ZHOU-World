// 引入axios实例
import request from '@/utils/request'
// 课程管理模块api文件

export default{
  // 新增章
  genSta(day) {
    return request({
      url: `admin/statistics/daily/gen-statistics/${day}`,
      method: 'POST'
    })
  },
  // 查询章节嵌套集合
  getSta(begin, end) {
    return request({
      url: `admin/statistics/daily/get-statistics/${begin}/${end}`,
      method: 'GET'
    })
  }
}
