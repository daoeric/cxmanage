import request from '@/utils/request'

// 查询聊天用户列表
export function listMessage(query) {
  return request({
    url: '/message/message/list',
    method: 'get',
    params: query
  })
}

// 查询聊天用户详细
export function getMessage(id) {
  return request({
    url: '/message/message/' + id,
    method: 'get'
  })
}

// 新增聊天用户
export function addMessage(data) {
  return request({
    url: '/message/message',
    method: 'post',
    data: data
  })
}


// 修改聊天用户
export function updateMessage(data) {
  return request({
    url: '/message/message',
    method: 'put',
    data: data
  })
}

// 删除聊天用户
export function delMessage(id) {
  return request({
    url: '/message/message/' + id,
    method: 'delete'
  })
}

// 导出聊天用户
export function exportMessage(query) {
  return request({
    url: '/message/message/export',
    method: 'get',
    params: query
  })
}
