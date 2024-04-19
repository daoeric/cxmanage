import request from '@/utils/request'

// 查询通用费率配置列表
export function listCommonSet(query) {
  return request({
    url: '/channel/commonSet/list',
    method: 'get',
    params: query
  })
}

// 查询通用费率配置详细
export function getCommonSet(id) {
  return request({
    url: '/channel/commonSet/' + id,
    method: 'get'
  })
}

// 新增通用费率配置
export function addCommonSet(data) {
  return request({
    url: '/channel/commonSet',
    method: 'post',
    data: data
  })
}

// 修改通用费率配置
export function updateCommonSet(data) {
  return request({
    url: '/channel/commonSet',
    method: 'put',
    data: data
  })
}

// 删除通用费率配置
export function delCommonSet(id) {
  return request({
    url: '/channel/commonSet/' + id,
    method: 'delete'
  })
}

// 导出通用费率配置
export function exportCommonSet(query) {
  return request({
    url: '/channel/commonSet/export',
    method: 'get',
    params: query
  })
}

export function getUnBindProduct() {
  return request({
    url: '/channel/commonSet/unProduct' ,
    method: 'get'
  })
}
