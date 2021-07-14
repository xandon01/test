/**
 * 封装axios请求模块
 */
import axios from 'axios'

const request = axios.create({
  baseURL: '/lvdou',  //基础路径位置
  // timeout: timeout
  headers:{
    'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
  },
})

/**
 * 调用接口哪里使用哪里加载
 *


 import request from '@/utils/request'

 request({
   method: 'xxx',
   url: 'xxx'
 })
 */
export default request
