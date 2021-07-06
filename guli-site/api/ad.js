import request from '~/utils/request'
export default{
// 查询所有热门数据
  list() {
    return request({
      // baseURL: 'http://localhost:8140',
      url: '/api/cms/ad/listAds',
      method: 'GET'
    })
  }
}
