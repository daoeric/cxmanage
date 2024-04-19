import request from '@/utils/request'

// 查询代付提现列表
export function listWithdrawal(query) {
  return request({
    url: '/customer/withdrawal/list',
    method: 'post',
    data: query
  })
}

// 查询代付提现详细
export function getWithdrawal(withdrawId) {
  return request({
    url: '/customer/withdrawal/' + withdrawId,
    method: 'get'
  })
}

export function getNoticeCount() {
  return request({
    url: '/customer/withdrawal/notice/count',
    method: 'get'
  })
}


export function mualCallback(withdrawId){
  return request({
    url: '/customer/withdrawal/mualCallback/' + withdrawId,
    method: 'post'
  })
}

// 新增代付提现
export function addWithdrawal(data) {
  return request({
    url: '/customer/withdrawal',
    method: 'post',
    data: data
  })
}

export function mualAdd(data){
  return request({
     url: '/customer/withdrawal/mualAdd',
     method: 'post',
     data: data
   })
}




// 修改代付提现
export function updateWithdrawal(data) {
  return request({
    url: '/customer/withdrawal',
    method: 'put',
    data: data
  })
}
// 审核订单
export function approve(data) {
  return request({
    url: '/customer/withdrawal/approve',
    method: 'put',
    data: data
  })
}


// 删除代付提现
export function delWithdrawal(withdrawId) {
  return request({
    url: '/customer/withdrawal/' + withdrawId,
    method: 'delete'
  })
}

// 导出代付提现
export function exportWithdrawal(query) {
  return request({
    url: '/customer/withdrawal/export',
    method: 'post',
    data: query
  })
}
