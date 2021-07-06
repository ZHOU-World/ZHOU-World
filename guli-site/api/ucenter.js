import request from '~/utils/request'
export default{
  // 1、获取验证码
  sendMsg(mobile) {
    return request({
      // baseURL: 'http://localhost:8150',
      url: `/api/sms/send/${mobile}`,
      method: 'GET'
    })
  },
  // 2、注册请求提交
  regist(member) {
    return request({
      // baseURL: 'http://localhost:8160',
      url: '/api/ucenter/member/regist',
      method: 'POST',
      data: member
    })
  },
  // 3、登录
  // 写法一
  // login(mobile,password){
  //   return request({
  //     baseURL: 'http://localhost:8160',
  //     url: '/api/ucenter/member/login',
  //     method: 'POST',
  //     params:{mobile,password} //相当于传递json对象
  //   })
  // }
  login(member) {
    return request({
      // baseURL: 'http://localhost:8160',
      url: '/api/ucenter/member/login',
      method: 'POST',
      params: member // member{"nickname":"xxx","age":"11"}
    })
  },
  // 4、解析jwt
  getUserInfo(token) {
    return request({
      // baseURL: 'http://localhost:8160',
      url: '/api/ucenter/member/get-userInfo',
      method: 'GET',
      headers: { token } // 设置请求头
    })
  }
}
