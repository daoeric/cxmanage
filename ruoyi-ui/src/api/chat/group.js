import request from '@/utils/request'

// 查询聊天用户列表
export function listGroup(query) {
  return request({
    url: '/group/group/list',
    method: 'get',
    params: query
  })
}

// 查询聊天用户列表
export function listGroupUser(query) {
  return request({
    url: '/group/group/list-user',
    method: 'get',
    params: query
  })
}

// 查询聊天用户列表
export function listGroupUsers(query) {
  return request({
    url: '/group/member/list',
    method: 'get',
    params: query
  })
}

// 查询聊天用户详细
export function getGroup(id) {
  return request({
    url: '/group/group/' + id,
    method: 'get'
  })
}

// 新增聊天用户
export function addGroup(data) {
  return request({
    url: '/group/group',
    method: 'post',
    data: data
  })
}


// 修改聊天用户
export function updateGroup(data) {
  return request({
    url: '/group/group',
    method: 'put',
    data: data
  })
}

// 删除聊天用户
export function delGroup(id) {
  return request({
    url: '/group/group/' + id,
    method: 'delete'
  })
}

// 导出聊天用户
export function exportGroup(query) {
  return request({
    url: '/group/group/export',
    method: 'get',
    params: query
  })
}
