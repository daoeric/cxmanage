import request from '@/utils/request'

// 查询结算方案列表
export function listPlan(query) {
  return request({
    url: '/channel/plan/list',
    method: 'get',
    params: query
  })
}

export function listPlanAll(query) {
  return request({
    url: '/channel/plan/list/all',
    method: 'get',
    params: query
  })
}

// 查询结算方案详细
export function getPlan(planId) {
  return request({
    url: '/channel/plan/' + planId,
    method: 'get'
  })
}

// 新增结算方案
export function addPlan(data) {
  return request({
    url: '/channel/plan',
    method: 'post',
    data: data
  })
}

// 修改结算方案
export function updatePlan(data) {
  return request({
    url: '/channel/plan',
    method: 'put',
    data: data
  })
}

// 删除结算方案
export function delPlan(planId) {
  return request({
    url: '/channel/plan/' + planId,
    method: 'delete'
  })
}

// 导出结算方案
export function exportPlan(query) {
  return request({
    url: '/channel/plan/export',
    method: 'get',
    params: query
  })
}

export function getPlanSet(query){
  return request({
    url: '/channel/channelSet/list',
    method: 'get',
    params: query
  })
}

export function getUnBindProduct(planId){
  return request({
    url: '/channel/channelSet/unProduct/'+planId,
    method: 'get',
  })
}

export function getBindChannel(productId){
  return request({
    url: '/channel/channel/list/'+productId,
    method: 'get',
  })
}

export function addPlanDetail(data){
  return request({
    url: '/channel/channelSet/',
    method: 'post',
    data: data
  })
}


