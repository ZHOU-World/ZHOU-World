// 引入axios实例
import request from '@/utils/request'
// 课程管理模块api文件

export default{
  // 新增章
  saveChapter(chapter) {
    return request({
      url: 'admin/edu/chapter/save',
      method: 'POST',
      data: chapter
    })
  },
  // 查询章节嵌套集合
  listNestedChapter(courseId) {
    return request({
      url: `/admin/edu/chapter/listNestedChapters/${courseId}`,
      method: 'GET',
      params: courseId
    })
  },
  // 根据id查询
  getById(id) {
    return request({
      url: `/admin/edu/chapter/get/${id}`,
      method: 'GET',
      params: id
    })
  },
  // 更新章
  update(chapter) {
    return request({
      url: `/admin/edu/chapter/update`,
      method: 'PUT',
      data: chapter
    })
  },
  // 删除章
  remove(id) {
    return request({
      url: `/admin/edu/chapter/remove/${id}`,
      method: 'DELETE',
      params: id
    })
  }
}
