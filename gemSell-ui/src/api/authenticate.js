import request from '@/utils/request'

export function getById(id) {
  return request({
    url: `/authenticate/getById/${id}`,
    method: 'get'
  })
}

export function getListByCondition(data, pageIndex, pageSize) {
  return request({
    url: '/authenticate/getListByCondition',
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
    url: '/authenticate/add',
    method: 'post',
    data: data
  })
}

export function update(data) {
  return request({
    url: '/authenticate/update',
    method: 'put',
    data: data
  })
}

export function deleteById(id) {
  return request({
    url: `/authenticate/delete/${id}`,
    method: 'delete'
  })
}

export function batchDelete(ids) {
  return request({
    url: '/authenticate/batchDelete',
    method: 'delete',
    params: {
      ids: ids
    }
  })
}
