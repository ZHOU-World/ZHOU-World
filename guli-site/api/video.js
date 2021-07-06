// 引入发送异步请求的axios实例
import request from '~/utils/request'
// 导入后端接口的方法
export default{
  // 获取加密视频播放凭证
  getPlayAuth(vid) {
    return request({
      // baseURL: 'http://localhost:8130',
      url: `/api/vod/media/getPlayAuth/${vid}`,
      method: 'GET'
    })
  }
}
