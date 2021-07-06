// 引入axios实例
import request from '@/utils/request'
// 讲师管理模块api文件

export default{

  // 1、查询讲师列表
  list() {
    // 返回异步请求的结果
    return request({
      url: '/admin/edu/teacher/list', // http://localhost:8110
      method: 'GET'
    })
  },
  // 2、查询讲师分页列表(带条件查询)
  listPage(pageNum, pageSize, teacherQuery) {
    // 返回异步请求的结果
    return request({
      url: `/admin/edu/teacher/list/${pageNum}/${pageSize}`, // http://localhost:8110
      method: 'GET',
      params: teacherQuery
    })
  },
  // 根据ID删除讲师
  deleteById(id) {
    return request({
      url: `/admin/edu/teacher/removeById/${id}`,
      method: 'DELETE'
    })
  },
  // 根据ID查询讲师getById/{id}
  getById(id) {
    return request({
      url: `/admin/edu/teacher/getById/${id}`,
      method: 'GET'
    })
  },
  // 更新讲师
  update(teacher) {
    return request({
      url: `/admin/edu/teacher/update`,
      method: 'PUT',
      data: teacher // 请求体json的方式提交
    })
  },
  // 新增讲师
  save(teacher) {
    return request({
      url: '/admin/edu/teacher/save',
      method: 'POST',
      data: teacher // 请求体json的方式提交
    })
  },
  // 批量删除讲师
  batchDel(ids) {
    return request({
      url: '/admin/edu/teacher/batchRemove',
      method: 'DELETE',
      // params: { ids: ids }// 前一个代表属性名，后一个代表属性的值（1，2，3，4....）,当相同时可以写一个
      params: { ids } // url?ids=ids变量的值（1，2，3，4.....）
    })
  }
}

