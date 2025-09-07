<!--------------------------------
 - @Author: ${author}
 - @LastEditor: ${author}
 - @LastEditTime: ${date}
 --------------------------------->

<template>
    <CommonPage>
        <template #action>
            <NButton  type="primary" @click="handleAdd()">
                <i class="i-material-symbols:add mr-4 text-18" />
                创建
            </NButton>
        </template>
        <MeCrud
                ref="$table"
                v-model:query-items="queryItems"
                :scroll-x="1200"
                :columns="columns"
                :get-data="api.read"
        >

        </MeCrud>

        <MeModal ref="modalRef" width="520px">
            <n-form
                    ref="modalFormRef"
                    label-placement="left"
                    label-align="left"
                    :label-width="80"
                    :model="modalForm"
                    :disabled="modalAction === 'view'"
            >

            </n-form>
        </MeModal>
    </CommonPage>
</template>

<script setup>
import { NAvatar, NButton, NSwitch, NTag } from 'naive-ui'
import { MeCrud, MeModal, MeQueryItem } from '@/components'
import { useCrud } from '@/composables'
import { withPermission } from '@/directives'
import { formatDateTime } from '@/utils'
import api from './api'

defineOptions({ name: 'UserMgt' })

const $table = ref(null)
/** QueryBar筛选参数（可选） */
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
    name: '',
    initForm: { },
    doCreate: api.create,
    doDelete: api.delete,
    doUpdate: api.update,
    refresh: () => $table.value?.handleSearch(),
})

const columns = [
    {
        title: '头像',
        key: 'avatar',
        width: 80,
        render: ({ avatar }) =>
            h(NAvatar, {
                size: 'medium',
                src: avatar,
            }),
    },
    { title: '用户名', key: 'username', width: 150, ellipsis: { tooltip: true } },
    {
        title: '角色',
        key: 'roles',
        width: 200,
        ellipsis: { tooltip: true },
        render: ({ roles }) => {
            if (roles?.length) {
                return roles.map((item, index) =>
                    h(
                        NTag,
                        { type: 'success', style: index > 0 ? 'margin-left: 8px;' : '' },
                        { default: () => item.name },
                    ),
                )
            }
            return '暂无角色'
        },
    },
    {
        title: '性别',
        key: 'gender',
        width: 80,
        render: ({ gender }) => genders.find(item => gender === item.value)?.label ?? '',
    },
    { title: '邮箱', key: 'email', width: 150, ellipsis: { tooltip: true } },
    {
        title: '创建时间',
        key: 'createDate',
        width: 180,
        render(row) {
            return h('span', formatDateTime(row.createTime))
        },
    },
    {
        title: '状态',
        key: 'enable',
        width: 120,
        render: row =>
            h(
                NSwitch,
                {
                    size: 'small',
                    rubberBand: false,
                    value: row.enable,
                    loading: !!row.enableLoading,
                    onUpdateValue: () => handleEnable(row),
                },
                {
                    checked: () => '启用',
                    unchecked: () => '停用',
                },
            ),
    },
    {
        title: '操作',
        key: 'actions',
        width: 420,
        align: 'right',
        fixed: 'right',
        hideInExcel: true,
        render(row) {
            return [
                withPermission(
                    h(NButton, {
                        size: 'small',
                        type: 'primary',
                        secondary: true,
                    }, {
                        default: () => '超管专属',
                        icon: () => h('i', { class: 'i-carbon:user-role text-14' }),
                    }),
                    'SuperAdmin',
                ),
                h(
                    NButton,
                    {
                        size: 'small',
                        type: 'primary',
                        class: 'ml-12px',
                        secondary: true,
                        onClick: () => handleOpenRolesSet(row),
                    },
                    {
                        default: () => '分配角色',
                        icon: () => h('i', { class: 'i-carbon:user-role text-14' }),
                    },
                ),
                h(
                    NButton,
                    {
                        size: 'small',
                        type: 'primary',
                        style: 'margin-left: 12px;',
                        onClick: () => handleOpen({ action: 'reset', title: '重置密码', row, onOk: onSave }),
                    },
                    {
                        default: () => '重置密码',
                        icon: () => h('i', { class: 'i-radix-icons:reset text-14' }),
                    },
                ),

                h(
                    NButton,
                    {
                        size: 'small',
                        type: 'error',
                        style: 'margin-left: 12px;',
                        onClick: () => handleDelete(row.id),
                    },
                    {
                        default: () => '删除',
                        icon: () => h('i', { class: 'i-material-symbols:delete-outline text-14' }),
                    },
                ),
            ]
        },
    },
]

async function handleEnable(row) {
    row.enableLoading = true
    try {
        await api.update({ id: row.id, enable: !row.enable })
        row.enableLoading = false
        $message.success('操作成功')
        $table.value?.handleSearch()
    }
    catch (error) {
        console.error(error)
        row.enableLoading = false
    }
}
</script>
