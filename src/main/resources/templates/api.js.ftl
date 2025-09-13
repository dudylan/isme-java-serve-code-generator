/**********************************
* @Author: ${author}
* @LastEditor: ${author}
* @LastEditTime:  ${date}
**********************************/
import { request } from '@/utils'

export default {
    create: data => request.post('/${entity?lower_case}', data),
    read: (params = {}) => request.get('/${entity?lower_case}', { params }),
    update: data => request.patch(`/${entity?lower_case}/${r"${data.id}"}`, data),
    delete: id => request.delete(`/${entity?lower_case}/${r"${id}"}`),
}
