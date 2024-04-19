import request from '@/utils/request'

// 查询通道信息列表
export function listChannel(query) {
  return request({
    url: '/channel/channel/list',
    method: 'get',
    params: query
  })
}
export function listAll() {
  return request({
    url: '/channel/channel/list/all',
    method: 'get',
  })
}



// 查询通道信息详细
export function getChannel(channelId) {
  return request({
    url: '/channel/channel/' + channelId,
    method: 'get'
  })
}

// 新增通道信息
export function addChannel(data) {
  return request({
    url: '/channel/channel',
    method: 'post',
    data: data
  })
}

// 修改通道信息
export function updateChannel(data) {
  return request({
    url: '/channel/channel',
    method: 'put',
    data: data
  })
}
// 删除通道信息
export function delChannel(channelId) {
  return request({
    url: '/channel/channel/' + channelId,
    method: 'delete'
  })
}

// 导出通道信息
export function exportChannel(query) {
  return request({
    url: '/channel/channel/export',
    method: 'get',
    params: query
  })
}

// 通道别名列表
export function aliasList() {
  return request({
    url: '/channel/channel/alias',
    method: 'get',
  })
}

// 根据产品获取通道
export function getChannelByProductId(productId) {
  return request({
    url: '/channel/channel/list/' + productId,
    method: 'get'
  })
}

export function productList() {
  return request({
    url: '/channel/product/list/all',
    method: 'get'
  })
}

export function balanceList(){
  return request({
    url: '/channel/channel/upFlow/balance',
    method: 'get'
  })
}

export function  balance(code){
  return request(
    {
      url: '/channel/channel/upFlow/balance/'+code,
      method: 'get'
    }
  )
}
