// 引入发送异步请求的axios实例
import request from '~/utils/request'
// 导入后端接口的方法
export default{
  // 1、查询讲师的课程列表
  listByTeacherId(teacherId) {
    return request({
      url: `/api/edu/course/getCourses/${teacherId}`,
      method: 'GET'
    })
  },
  // 2、带条件查询的课程列表
  listCourses(query) {
    return request({
      url: '/api/edu/course/list',
      method: 'GET',
      params: query // 条件参数：put/post/delete → data; get → params
    })
  },
  // 3、根据ID查询课程详情页面
  getById(id) {
    return request({
      url: `/api/edu/course/getById/${id}`,
      method: 'GET'
    })
  }
}
