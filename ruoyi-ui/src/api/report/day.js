import request from '@/utils/request'

// 查询日报统计列表
export function listDay(data) {
  return request({
    url: '/report/day/list',
    method: 'post',
    data: data
  })
}

// 查询日报统计详细
export function getDay(id) {
  return request({
    url: '/report/day/' + id,
    method: 'get'
  })
}

// 新增日报统计
export function addDay(data) {
  return request({
    url: '/report/day',
    method: 'post',
    data: data
  })
}

// 修改日报统计
export function updateDay(data) {
  return request({
    url: '/report/day',
    method: 'put',
    data: data
  })
}

// 删除日报统计
export function delDay(id) {
  return request({
    url: '/report/day/' + id,
    method: 'delete'
  })
}

// 导出日报统计
export function exportDay(data) {
  return request({
    url: '/report/day/export',
    method: 'post',
    data: data
  })
}
