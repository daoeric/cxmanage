import request from '@/utils/request'

// 查询IP白名单列表
export function listIp(query) {
  return request({
    url: '/channel/ip/list',
    method: 'get',
    params: query
  })
}

// 查询IP白名单详细
export function getIp(id) {
  return request({
    url: '/channel/ip/' + id,
    method: 'get'
  })
}

// 新增IP白名单
export function addIp(data) {
  return request({
    url: '/channel/ip',
    method: 'post',
    data: data
  })
}

// 修改IP白名单
export function updateIp(data) {
  return request({
    url: '/channel/ip',
    method: 'put',
    data: data
  })
}

// 删除IP白名单
export function delIp(id) {
  return request({
    url: '/channel/ip/' + id,
    method: 'delete'
  })
}

// 导出IP白名单
export function exportIp(query) {
  return request({
    url: '/channel/ip/export',
    method: 'get',
    params: query
  })
}
//修改通道状态
export function changeChannelStatus(code, status) {
  const data = {
    alias:code,
    status:status
  }
  return request({
    url: '/channel/ip/update/status',
    method: 'post',
    data: data
  })
}
