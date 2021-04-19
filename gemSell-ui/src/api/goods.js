import request from '@/utils/request'

export function getById(id) {
  return request({
    url: `/goods/getById/${id}`,
    method: 'get'
  })
}

export function getuserByWordId(wordId) {
  return request({
    url: `/goods/getuserByWordId/${wordId}`,
    method: 'get'
  })
}

export function getListByCondition(data, pageIndex, pageSize) {
  return request({
    url: '/goods/getListByCondition',
    method: 'post',
    data: data,
    params:
      {
        pageIndex: pageIndex,
        pageSize: pageSize
      }
  })
}

export function add(data) {
  return request({
    url: '/goods/add',
    method: 'post',
    data: data
  })
}

export function update(data) {
  return request({
    url: '/goods/update',
    method: 'put',
    data: data
  })
}

export function deleteById(id) {
  return request({
    url: `/goods/delete/${id}`,
    method: 'delete'
  })
}

export function batchDelete(ids) {
  return request({
    url: '/goods/batchDelete',
    method: 'delete',
    params: {
      ids: ids
    }
  })
}

export function login(data) {
  return request({
    url: '/goods/login',
    method: 'post',
    data: data
  })
}

export function register(data) {
  return request({
    url: '/goods/register',
    method: 'post',
    data: data
  })
}

export function changePassword(oldPassword, newPassword, userName) {
  return request({
    url: '/goods/changePassword',
    method: 'post',
    params: { oldPassword: oldPassword, newPassword: newPassword, userName: userName }
  })
}

export function restPassword(id) {
  return request({
    url: '/goods/restPassword',
    method: 'post',
    params: { userId: id }
  })
}

export function getuserByCurrentDeptId(deptId) {
  return request({
    url: '/goods/getuserByCurrentDeptId/' + deptId,
    method: 'get'
  })
}

export function getWorkStateChartData() {
  return request({
    url: '/goods/getWorkStateChartData',
    method: 'get'
  })
}

export function getSexChartData() {
  return request({
    url: '/goods/getSexChartData',
    method: 'get'
  })
}

export function getTiptopDegreeChartData() {
  return request({
    url: '/goods/getTiptopDegreeChartData',
    method: 'get'
  })
}

export function getJobLevelChartData() {
  return request({
    url: '/goods/getJobLevelChartData',
    method: 'get'
  })
}

export function getPosChartData() {
  return request({
    url: '/goods/getPosChartData',
    method: 'get'
  })
}
