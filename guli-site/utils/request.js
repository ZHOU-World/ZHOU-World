import axios from 'axios'
import { Message } from 'element-ui'
import local from '~/utils/local'
// 创建axios实例
const service = axios.create({
  baseURL: 'http://localhost:9110',
  timeout: 12000 // 请求超时时间
})

// http request 拦截器
service.interceptors.request.use(
  config => {
    return config
  },
  error => {
    return Promise.reject(error)
  }
)
// response 拦截器
service.interceptors.response.use(
  response => {
    /**
       * code为非20000是抛错 可结合自己业务进行修改
       */
    const res = response.data
    // 28006-28009的响应状态码需要放行，让api方法执行时自己处理异常
    if (res.code === 20000) {
      return response.data
    } else if (res.code === 28004) { // 当响应状态码是28004时，删除jwt,不弹出弹框
      local.remove('GULI_JWT')
      // return Promise.reject('error')
      window.location = '/login' // 跳转登录页面
    } else if (res.code === 28006 || res.code === 28007 || res.code === 28008 || res.code === 28009) {
      Message({
        message: res.message || 'error',
        type: 'error',
        duration: 5 * 1000
      })
      return response.data // 只做异常提示，不处理
    } else {
      Message({
        message: res.message || 'error',
        type: 'error',
        duration: 5 * 1000
      })
      return Promise.reject('error')
    }
  },
  error => {
    console.log('err：' + error) // for debug
    Message({
      message: error.message,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

export default service
