import request from '@/utils/request'

export function getById(id) {
  return request({
    url: `/order/getById/${id}`,
    method: 'get'
  })
}

export function getListByCondition(data, pageIndex, pageSize) {
  return request({
    url: '/order/getListByCondition',
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
    url: '/order/add',
    method: 'post',
    data: data
  })
}

export function update(data) {
  return request({
    url: '/order/update',
    method: 'put',
    data: data
  })
}

export function deleteById(id) {
  return request({
    url: `/order/delete/${id}`,
    method: 'delete'
  })
}

export function batchDelete(ids) {
  return request({
    url: '/order/batchDelete',
    method: 'delete',
    params: {
      ids: ids
    }
  })
}
export function audit(data) {
  return request({
    url: '/order/audit',
    method: 'post',
    data
  })
}
