// 引入axios实例
import request from '@/utils/request'
// 课程管理模块api文件
export default{
  // 1、新增课时
  saveVideo(video) {
    return request({
      url: '/admin/edu/video/save',
      method: 'POST',
      data: video
    })
  },
  // 2、根据ID查询课时
  getById(id) {
    return request({
      url: `/admin/edu/video/get/${id}`,
      method: 'GET'
    })
  },
  // 3、更新课时
  updateVideo(video) {
    return request({
      url: `/admin/edu/video/update`,
      method: 'PUT',
      data: video
    })
  },
  // 4、删除指定课时
  remove(id) {
    return request({
      url: `/admin/edu/video/remove/${id}`,
      method: 'DELETE'
    })
  }
}
