import request from '@/utils/request'

export function getById(id) {
  return request({
    url: `/type/getById/${id}`,
    method: 'get'
  })
}

export function getListByCondition() {
  return request({
    url: '/type/getListByCondition',
    method: 'post'
  })
}

export function add(data) {
  return request({
    url: '/type/add',
    method: 'post',
    data: data
  })
}

export function update(data) {
  return request({
    url: '/type/update',
    method: 'put',
    data: data
  })
}

export function deleteById(id) {
  return request({
    url: `/type/delete/${id}`,
    method: 'delete'
  })
}

export function batchDelete(ids) {
  return request({
    url: '/type/batchDelete',
    method: 'delete',
    params: ids
  })
}

export function getTypeSelect() {
  return request({
    url: '/type/getTypeSelect',
    method: 'get'
  })
}
