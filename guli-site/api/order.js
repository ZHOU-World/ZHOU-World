import request from '~/utils/request'
export default{
  // 1、创建订单
  createOrder(courseId, token) {
    return request({
      // baseURL: 'http://localhost:8170',
      url: `/api/trade/order/auth/create-order/${courseId}`,
      method: 'POST',
      // 如果使用请求拦截器统一配置，所有请求都携带token访问
      // 那么所有的nuxt asynData方法都不能用，必须修改为mounted发起请求
      headers: { token }
    })
  },
  // 2、根据订单id查询订单详情信息
  getOrder(orderId, token) {
    return request({
      url: `/api/trade/order/auth/getOrder/${orderId}`,
      method: 'GET',
      headers: { token }
    })
  },
  // 3、获取支付二维码
  toPay(orderId, token) {
    return request({
      url: `/api/trade/order/auth/toPay/${orderId}`,
      method: 'GET',
      headers: { token }
    })
  },
  // 4、查询订单支付状态
  getPayStatus(orderId, token) {
    return request({
      url: `/api/trade/order/auth/get-paystatus/${orderId}`,
      method: 'GET',
      headers: { token }
    })
  },
  // 5、查询查询用户是否已购买课程
  isBuy(courseId, token) {
    return request({
      url: `/api/trade/order/auth/isBuy/${courseId}`,
      method: 'GET',
      headers: { token }
    })
  }
}
