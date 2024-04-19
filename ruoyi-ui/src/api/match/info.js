import request from '@/utils/request'

// 查询赛事列表列表
export function listInfo(query) {
  return request({
    url: '/match/info/list',
    method: 'get',
    params: query
  })
}

// 查询赛事列表详细
export function getInfo(matchId) {
  return request({
    url: '/match/info/' + matchId,
    method: 'get'
  })
}

// 新增赛事列表
export function addInfo(data) {
  return request({
    url: '/match/info',
    method: 'post',
    data: data
  })
}

// 修改赛事列表
export function updateInfo(data) {
  return request({
    url: '/match/info',
    method: 'put',
    data: data
  })
}

// 删除赛事列表
export function delInfo(matchId) {
  return request({
    url: '/match/info/' + matchId,
    method: 'delete'
  })
}

// 导出赛事列表
export function exportInfo(query) {
  return request({
    url: '/match/info/export',
    method: 'get',
    params: query
  })
}