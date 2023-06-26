import request from '@/utils/request'

// 查询组织列表
export function listDept(query) {
  return request({
    url: '/dept/list',
    method: 'get',
    params: query
  })
}

// 查询组织列表（排除节点）
export function listDeptExcludeChild(deptId) {
  return request({
    url: '/dept/list/exclude/' + deptId,
    method: 'get'
  })
}

// 查询组织详细
export function getDept(deptId) {
  return request({
    url: '/dept/' + deptId,
    method: 'get'
  })
}

// 新增组织
export function addDept(data) {
  return request({
    url: '/dept',
    method: 'post',
    data: data
  })
}

// 修改组织
export function updateDept(data) {
  return request({
    url: '/dept',
    method: 'put',
    data: data
  })
}

// 删除组织
export function delDept(deptId) {
  return request({
    url: '/dept/' + deptId,
    method: 'delete'
  })
}