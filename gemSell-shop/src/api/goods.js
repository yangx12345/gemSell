import request from '@/utils/request'

export function getById(id) {
  return request({
    url: `/goods/getById/${id}`,
    method: 'get'
  })
}

export function getGoodsListByCondition(data, pageIndex, pageSize) {
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

export function getGoodsList() {
  return request({
    url: '/goods/getGoodsList',
    method: 'get'
  })
}
