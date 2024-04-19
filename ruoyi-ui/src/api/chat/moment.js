import request from '@/utils/request'

// 查询聊天用户列表
export function listMoment(query) {
  return request({
    url: '/moment/moment/list',
    method: 'get',
    params: query
  })
}

// 查询聊天用户详细
export function getMoment(id) {
  return request({
    url: '/moment/moment/' + id,
    method: 'get'
  })
}

// 新增聊天用户
export function addMoment(data) {
  return request({
    url: '/moment/moment',
    method: 'post',
    data: data
  })
}


// 修改聊天用户
export function updateMoment(data) {
  return request({
    url: '/moment/moment',
    method: 'put',
    data: data
  })
}

// 删除聊天用户
export function delMoment(id) {
  return request({
    url: '/moment/moment/' + id,
    method: 'delete'
  })
}

// 导出聊天用户
export function exportMoment(query) {
  return request({
    url: '/moment/moment/export',
    method: 'get',
    params: query
  })
}
