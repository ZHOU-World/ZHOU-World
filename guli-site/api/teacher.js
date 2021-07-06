// 引入发送异步请求的axios实例
import request from '~/utils/request'
// 导入后端接口的方法
export default{
// 查询所有讲师
  list() {
    return request({
      url: '/api/edu/teacher/list', // 地址是和request.js中的baseURL拼接而成
      method: 'GET'
    })
  },
  // 查询讲师详情
  getTeacher(id) {
    return request({
      url: `/api/edu/teacher/getTeacherById/${id}`,
      method: 'GET'
    })
  }
}
