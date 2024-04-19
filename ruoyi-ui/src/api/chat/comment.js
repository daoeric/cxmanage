import request from '@/utils/request'

// 查询聊天用户列表
export function listComment(query) {
  return request({
    url: '/moment/comment/list',
    method: 'get',
    params: query
  })
}

// 查询聊天用户详细
export function getComment(id) {
  return request({
    url: '/moment/comment/' + id,
    method: 'get'
  })
}

// 新增聊天用户
export function addComment(data) {
  return request({
    url: '/moment/comment',
    method: 'post',
    data: data
  })
}


// 修改聊天用户
export function updateComment(data) {
  return request({
    url: '/moment/comment',
    method: 'put',
    data: data
  })
}

// 删除聊天用户
export function delComment(id) {
  return request({
    url: '/moment/comment/' + id,
    method: 'delete'
  })
}

// 导出聊天用户
export function exportComment(query) {
  return request({
    url: '/moment/comment/export',
    method: 'get',
    params: query
  })
}
