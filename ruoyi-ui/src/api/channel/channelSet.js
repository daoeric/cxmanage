import request from '@/utils/request'

// 查询通道配置列表
export function listChannelSet(query) {
  return request({
    url: '/channel/channelSet/list',
    method: 'get',
    params: query
  })
}

// 查询通道配置详细
export function getChannelSet(id) {
  return request({
    url: '/channel/channelSet/' + id,
    method: 'get'
  })
}

// 新增通道配置
export function addChannelSet(data) {
  return request({
    url: '/channel/channelSet',
    method: 'post',
    data: data
  })
}

// 修改通道配置
export function updateChannelSet(data) {
  return request({
    url: '/channel/channelSet',
    method: 'put',
    data: data
  })
}

// 删除通道配置
export function delChannelSet(id) {
  return request({
    url: '/channel/channelSet/' + id,
    method: 'delete'
  })
}

// 导出通道配置
export function exportChannelSet(query) {
  return request({
    url: '/channel/channelSet/export',
    method: 'get',
    params: query
  })
}
