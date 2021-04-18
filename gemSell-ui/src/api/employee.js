import request from '@/utils/request'

export function getById(id) {
  return request({
    url: `/user/getById/${id}`,
    method: 'get'
  })
}

export function getuserByWordId(wordId) {
  return request({
    url: `/user/getuserByWordId/${wordId}`,
    method: 'get'
  })
}

export function getListByCondition(data, pageIndex, pageSize) {
  return request({
    url: '/user/getListByCondition',
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
    url: '/user/add',
    method: 'post',
    data: data
  })
}

export function update(data) {
  return request({
    url: '/user/update',
    method: 'put',
    data: data
  })
}

export function deleteById(id) {
  return request({
    url: `/user/delete/${id}`,
    method: 'delete'
  })
}

export function batchDelete(ids) {
  return request({
    url: '/user/batchDelete',
    method: 'delete',
    params: {
      ids: ids
    }
  })
}

export function login(data) {
  return request({
    url: '/user/login',
    method: 'post',
    data: data
  })
}

export function register(data) {
  return request({
    url: '/user/register',
    method: 'post',
    data: data
  })
}

export function changePassword(oldPassword, newPassword, userName) {
  return request({
    url: '/user/changePassword',
    method: 'post',
    params: { oldPassword: oldPassword, newPassword: newPassword, userName: userName }
  })
}

export function restPassword(id) {
  return request({
    url: '/user/restPassword',
    method: 'post',
    params: { userId: id }
  })
}

export function getuserByCurrentDeptId(deptId) {
  return request({
    url: '/user/getuserByCurrentDeptId/' + deptId,
    method: 'get'
  })
}

export function getWorkStateChartData() {
  return request({
    url: '/user/getWorkStateChartData',
    method: 'get'
  })
}

export function getSexChartData() {
  return request({
    url: '/user/getSexChartData',
    method: 'get'
  })
}

export function getTiptopDegreeChartData() {
  return request({
    url: '/user/getTiptopDegreeChartData',
    method: 'get'
  })
}

export function getJobLevelChartData() {
  return request({
    url: '/user/getJobLevelChartData',
    method: 'get'
  })
}

export function getPosChartData() {
  return request({
    url: '/user/getPosChartData',
    method: 'get'
  })
}
