import request from '@/utils/request'

// 查询渠道统计列表
export function listChannelReport(query) {
  return request({
    url: '/report/channelReport/list',
    method: 'post',
    data: query
  })
}

// 查询渠道统计详细
export function getChannelReport(id) {
  return request({
    url: '/report/channelReport/' + id,
    method: 'get'
  })
}

// 新增渠道统计
export function addChannelReport(data) {
  return request({
    url: '/report/channelReport',
    method: 'post',
    data: data
  })
}

// 修改渠道统计
export function updateChannelReport(data) {
  return request({
    url: '/report/channelReport',
    method: 'put',
    data: data
  })
}

// 删除渠道统计
export function delChannelReport(id) {
  return request({
    url: '/report/channelReport/' + id,
    method: 'delete'
  })
}

// 导出渠道统计
export function exportChannelReport(query) {
  return request({
    url: '/report/channelReport/export',
    method: 'get',
    params: query
  })
}
