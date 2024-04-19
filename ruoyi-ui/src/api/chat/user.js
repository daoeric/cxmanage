import request from '@/utils/request'

// 查询聊天用户列表
export function listUser(query) {
  return request({
    url: '/chat/user/list',
    method: 'get',
    params: query
  })
}

// 查询聊天用户详细
export function getUser(id) {
  return request({
    url: '/chat/user/' + id,
    method: 'get'
  })
}

// 新增聊天用户
export function addUser(data) {
  return request({
    url: '/chat/user',
    method: 'post',
    data: data
  })
}

// 新增聊天用户
export function addRobot(data) {
  return request({
    url: '/chat/robot',
    method: 'post',
    data: data
  })
}


// 修改聊天用户
export function updateUser(data) {
  return request({
    url: '/chat/user',
    method: 'put',
    data: data
  })
}

// 修改聊天用户
export function resetUser(data) {
  return request({
    url: '/chat/user/reset',
    method: 'post',
    data: data
  })
}

// 删除聊天用户
export function delUser(id) {
  return request({
    url: '/chat/user/' + id,
    method: 'delete'
  })
}

// 导出聊天用户
export function exportUser(query) {
  return request({
    url: '/chat/user/export',
    method: 'get',
    params: query
  })
}
