// 引入axios实例
import request from '@/utils/request'
// 课程分类管理模块api文件

export default{
// 查询分类嵌套
  listNestedSubjects() {
    return request({
      url: '/admin/edu/subject/listNestedSubjects', // 拼接baseAPI(在config/dev.env.js中)
      method: 'GET'
    })
  }

}
