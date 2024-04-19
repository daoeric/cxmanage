import request from '@/utils/request'

// 查询商户信息列表
export function listInfo(query) {
  return request({
    url: '/customer/info/list',
    method: 'get',
    params: query
  })
}

// 查询商户信息详细
export function getInfo(id) {
  return request({
    url: '/customer/info/' + id,
    method: 'get'
  })
}

// 新增商户信息
export function addInfo(data) {
  return request({
    url: '/customer/info',
    method: 'post',
    data: data
  })
}
export function updatePlan(data){
  return request({
    url: '/customer/info/updatePlan',
    method: 'put',
    data: data
  })
}

export function resetPwd(data){
  return request({
    url: '/customer/info/resetPwd',
    method: 'put',
    data: data
  })
}

export function resetGoogle(data){
  return request({
    url: '/customer/info/resetGoogle',
    method: 'put',
    data: data
  })
}

export function addBalance(data){
  return request({
    url: '/customer/info/addBalance',
    method: 'put',
    data: data
  })
}

// 用户状态修改
export function changeUserStatus(userId, status) {
  const data = {
    id:userId,
    status
  }
  return request({
    url: '/customer/info/updateStatus',
    method: 'post',
    data: data
  })
}

// export function resetGoogle(data){
//   return request({
//     url: '/customer/info/resetGoogle',
//     method: 'put',
//     data: data
//   })
// }

// 修改商户信息
export function updateInfo(data) {
  return request({
    url: '/customer/info',
    method: 'put',
    data: data
  })
}

export function updateChannel(data) {
  return request({
    url: '/customer/info/updateChanel',
    method: 'put',
    data: data
  })
}

// 删除商户信息
export function delInfo(id) {
  return request({
    url: '/customer/info/' + id,
    method: 'delete'
  })
}

// 导出商户信息
export function exportInfo(query) {
  return request({
    url: '/customer/info/export',
    method: 'get',
    params: query
  })
}

//list所有代理
export function getAgentList(){
  return request({
    url: '/customer/info/agent/list',
    method: 'get'
  })
}
export function updateAgent(data){
  return request({
    url: '/customer/info/agent/update',
    method: 'post',
    data: data
  })
}
