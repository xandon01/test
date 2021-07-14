/**
 * 用户相关的请求模块
 */
import request from '../utils/request'

/**
 * 用户
 */
export const login = data => {
  return request({
    method: 'POST',
    url: 'loginSubmit',
    data,
  })
}

export const account = data => {
  return request({
    method: 'POST',
    url: 'account',
    data,
  })
}

export const transer = data => {
  return request({
    method: 'POST',
    url: 'transer',
    data,
  })
}

export const paybean = data => {
  return request({
    method: 'POST',
    url: 'paybean?amt=50',
    data,
  })
}