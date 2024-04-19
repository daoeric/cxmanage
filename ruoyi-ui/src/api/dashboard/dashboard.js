import request from '@/utils/request'

// 查询额度变更列表
export function info() {
  return request({
    url: '/dashboard/info',
    method: 'get',
  })
}
export function chatData() {
  return request({
    url: '/dashboard/chat/data',
    method: 'get',
  })
}
