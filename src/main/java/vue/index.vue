<!--------------------------------
 - @Author: Dylan Du
 - @LastEditor: Dylan Du
 - @LastEditTime: 2025-09-13
 - @Description: Product管理页面
 --------------------------------->

<template>
    <CommonPage>
        <template #action>
            <NButton type="primary" @click="handleAdd()">
                <i class="i-material-symbols:add mr-4 text-18" />
                创建Product
            </NButton>
        </template>
        <MeCrud
            ref="$table"
            v-model:query-items="queryItems"
            :scroll-x="1200"
            :columns="columns"
            :get-data="api.read"
        />
        <MeModal ref="modalRef" width="520px">
            <n-form
                ref="modalFormRef"
                label-placement="left"
                label-align="left"
                :label-width="80"
                :model="modalForm"
                :disabled="modalAction === 'view'"
            >
                <n-form-item label="产品名称" path="productName">
                    <n-input v-model:value="modalForm.productName" placeholder="请输入产品名称" />
                </n-form-item>
                <n-form-item label="产品价格（精确到分）" path="price">
                    <n-input-number
                        v-model:value="modalForm.price"
                        placeholder="请输入产品价格（精确到分）"
                        :min="0"
                        :precision="2"
                    />
                </n-form-item>
                <n-form-item label="库存数量" path="stock">
                    <n-input-number
                        v-model:value="modalForm.stock"
                        placeholder="请输入库存数量"
                        :min="0"
                        :precision="0"
                    />
                </n-form-item>
                <n-form-item label="是否上架（0-下架，1-上架）" path="isOnSale">
                    <n-switch v-model:value="modalForm.isOnSale" />
                </n-form-item>
                <n-form-item label="分类ID（关联分类表）" path="categoryId">
                    <n-input-number
                        v-model:value="modalForm.categoryId"
                        placeholder="请输入分类ID（关联分类表）"
                        :min="0"
                        :precision="0"
                    />
                </n-form-item>
                <n-form-item label="产品重量（kg）" path="weight">
                    <n-input-number
                        v-model:value="modalForm.weight"
                        placeholder="请输入产品重量（kg）"
                        :min="0"
                        :precision="2"
                    />
                </n-form-item>
                <n-form-item label="产品详细描述" path="description">
                    <n-input v-model:value="modalForm.description" placeholder="请输入产品详细描述" />
                </n-form-item>
                <n-form-item label="封面图片URL" path="coverImage">
                    <n-input v-model:value="modalForm.coverImage" placeholder="请输入封面图片URL" />
                </n-form-item>
                <n-form-item label="排序权重（值越大越靠前）" path="sort">
                    <n-input v-model:value="modalForm.sort" placeholder="请输入排序权重（值越大越靠前）" />
                </n-form-item>
            </n-form>
        </MeModal>
    </CommonPage>
</template>

<script setup>
import { NButton, NTag } from 'naive-ui'
import { MeCrud, MeModal } from '@/components'
import { useCrud } from '@/composables'
import { formatDateTime } from '@/utils'
import api from './api'

defineOptions({ name: 'ProductMgt' })

const $table = ref(null)
const queryItems = ref({})

onMounted(() => {
    $table.value?.handleSearch()
})

const {
    modalRef,
    modalFormRef,
    modalForm,
    modalAction,
    handleAdd,
    handleDelete,
    handleOpen,
    handleSave,
} = useCrud({
    name: 'Product',
    initForm: {
    },
    doCreate: api.create,
    doDelete: api.delete,
    doUpdate: api.update,
    refresh: () => $table.value?.handleSearch(),
})

const columns = [
    {
        title: '主键ID',
        key: 'id',
        width: 150,
        ellipsis: { tooltip: true }
    },
    {
        title: '产品名称',
        key: 'productName',
        width: 150,
        ellipsis: { tooltip: true }
    },
    {
        title: '产品价格（精确到分）',
        key: 'price',
        width: 150,
        ellipsis: { tooltip: true }
    },
    {
        title: '库存数量',
        key: 'stock',
        width: 150,
        ellipsis: { tooltip: true }
    },
    {
        title: '是否上架（0-下架，1-上架）',
        key: 'isOnSale',
        width: 150,
        ellipsis: { tooltip: true }
    },
    {
        title: '分类ID（关联分类表）',
        key: 'categoryId',
        width: 150,
        ellipsis: { tooltip: true }
    },
    {
        title: '产品重量（kg）',
        key: 'weight',
        width: 150,
        ellipsis: { tooltip: true }
    },
    {
        title: '创建用户',
        key: 'createUser',
        width: 150,
        ellipsis: { tooltip: true }
    },
    {
        title: '创建时间',
        key: 'createTime',
        width: 150,
        ellipsis: { tooltip: true }
    },
    {
        title: '更新用户',
        key: 'updateUser',
        width: 150,
        ellipsis: { tooltip: true }
    },
    {
        title: '更新时间',
        key: 'updateTime',
        width: 150,
        ellipsis: { tooltip: true }
    },
    {
        title: '产品详细描述',
        key: 'description',
        width: 150,
        ellipsis: { tooltip: true }
    },
    {
        title: '封面图片URL',
        key: 'coverImage',
        width: 150,
        ellipsis: { tooltip: true }
    },
    {
        title: '排序权重（值越大越靠前）',
        key: 'sort',
        width: 150,
        ellipsis: { tooltip: true }
    },
    {
        title: '创建时间',
        key: 'createTime',
        width: 180,
        render(row) {
            return h('span', formatDateTime(row.createTime))
        }
    },
    {
        title: '操作',
        key: 'actions',
        width: 200,
        align: 'right',
        fixed: 'right',
        render(row) {
            return [
                h(
                    NButton,
                    {
                        size: 'small',
                        type: 'primary',
                        onClick: () => handleOpen({ row })
                    },
                    { default: () => '编辑' }
                ),
                h(
                    NButton,
                    {
                        size: 'small',
                        type: 'error',
                        style: 'margin-left: 12px;',
                        onClick: () => handleDelete(row.id)
                    },
                    { default: () => '删除' }
                )
            ]
        }
    }
]
</script>
