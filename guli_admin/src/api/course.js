// 引入axios实例
import request from '@/utils/request'
// 课程管理模块api文件

export default{
  // 1、发布课程第一步：保存课程基本信息
  saveCourseInfo(courseInfo) { // 保存什么，传什么
    return request({
      url: '/admin/edu/course/save-course-info',
      method: 'POST',
      data: courseInfo // 请求体参数，params代表查询参数，？后面拼接
    })
  },
  // 2、保存课程基本信息之后，数据回显
  getCourseInfo(courseId) {
    return request({
      url: `/admin/edu/course/get-course-info/${courseId}`,
      method: 'GET'
    })
  },
  // 3、更新基本课程信息
  updateCourseInfo(courseInfo, courseId) {
    return request({
      url: `/admin/edu/course/update-course-info/${courseId}`,
      method: 'PUT',
      data: courseInfo
    })
  },
  // 4、查询课程列表
  listPage(pageNum, pageSize, query) {
    return request({
      url: `/admin/edu/course/course-list/${pageNum}/${pageSize}`,
      method: 'GET',
      params: query
    })
  },
  // 5、查询要发布的课程数据
  getCoursePublish(id) {
    return request({
      url: `/admin/edu/course/get-course-publish/${id}`,
      method: 'GET'
    })
  },
  // 6、发布课程
  coursePublish(courseId) {
    return request({
      url: `/admin/edu/course/coursePublish/${courseId}`,
      method: 'PUT'
    })
  }
}
