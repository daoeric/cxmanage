import request from '@/utils/request'

// 查询聊天用户列表
export function listFriend(query) {
  return request({
    url: '/member/friend/list',
    method: 'get',
    params: query
  })
}

// 查询聊天用户列表
export function listApply(query) {
  return request({
    url: '/member/apply/list',
    method: 'get',
    params: query
  })
}

// 查询聊天用户详细
export function getApply(id) {
  return request({
    url: '/member/apply/' + id,
    method: 'get'
  })
}

// 新增聊天用户
export function addApply(data) {
  return request({
    url: '/member/apply',
    method: 'post',
    data: data
  })
}

// 修改聊天用户
export function updateApply(data) {
  return request({
    url: '/member/apply',
    method: 'put',
    data: data
  })
}

// 删除聊天用户
export function delApply(id) {
  return request({
    url: '/member/apply/' + id,
    method: 'delete'
  })
}

// 导出聊天用户
export function exportApply(query) {
  return request({
    url: '/member/apply/export',
    method: 'get',
    params: query
  })
}
