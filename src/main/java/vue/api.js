/**********************************
* @Author: Dylan Du
* @LastEditor: Dylan Du
* @LastEditTime:  2025-09-13
**********************************/
import { request } from '@/utils'

export default {
    create: data => request.post('/product', data),
    read: (params = {}) => request.get('/product', { params }),
    update: data => request.patch(`/product/${data.id}`, data),
    delete: id => request.delete(`/product/${id}`),
}
