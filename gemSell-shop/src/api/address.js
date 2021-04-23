import request from '@/utils/request'

export function getById(id) {
  return request({
    url: `/address/getById/${id}`,
    method: 'get'
  })
}

export function getAddressListByCondition(data, pageIndex, pageSize) {
  return request({
    url: '/address/getListByCondition',
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
    url: '/address/add',
    method: 'post',
    data: data
  })
}

export function update(data) {
  return request({
    url: '/address/update',
    method: 'put',
    data: data
  })
}

export function deleteById(id) {
  return request({
    url: `/address/delete/${id}`,
    method: 'delete'
  })
}

export function batchDelete(ids) {
  return request({
    url: '/address/batchDelete',
    method: 'delete',
    params: {
      ids: ids
    }
  })
}

export function getaddressList() {
  return request({
    url: '/address/getaddressList',
    method: 'get'
  })
}
