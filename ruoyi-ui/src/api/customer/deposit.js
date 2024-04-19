import request from '@/utils/request'

// 查询商户充值订单列表
export function listDeposit(query) {
  return request({
    url: '/customer/deposit/list',
    method: 'post',
    data: query
  })
}

// 查询商户充值订单详细
export function getDeposit(requestId) {
  return request({
    url: '/customer/deposit/' + requestId,
    method: 'get'
  })
}

// 新增商户充值订单
export function addDeposit(data) {
  return request({
    url: '/customer/deposit',
    method: 'post',
    data: data
  })
}

// 修改商户充值订单
export function updateDeposit(data) {
  return request({
    url: '/customer/deposit',
    method: 'put',
    data: data
  })
}

// 删除商户充值订单
export function delDeposit(requestId) {
  return request({
    url: '/customer/deposit/' + requestId,
    method: 'delete'
  })
}

export function mualCallback(requestId){
  return request({
    url: '/customer/deposit/mualCallback/' + requestId,
    method: 'post'
  })
}

// 导出商户充值订单
export function exportDeposit(query) {
  return request({
    url: '/customer/deposit/export',
    method: 'post',
    data: query
  })
}
