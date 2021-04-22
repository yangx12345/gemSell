import request from '@/utils/request'

export function getById(id) {
  return request({
    url: `/cart/getById/${id}`,
    method: 'get'
  })
}

export function getListByCondition(data, pageIndex, pageSize) {
  return request({
    url: '/cart/getListByCondition',
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
    url: '/cart/add',
    method: 'post',
    data: data
  })
}

export function update(data) {
  return request({
    url: '/cart/update',
    method: 'put',
    data: data
  })
}

export function deleteById(id) {
  return request({
    url: `/cart/delete/${id}`,
    method: 'delete'
  })
}

export function batchDelete(ids) {
  return request({
    url: '/cart/batchDelete',
    method: 'delete',
    params: {
      ids: ids
    }
  })
}
