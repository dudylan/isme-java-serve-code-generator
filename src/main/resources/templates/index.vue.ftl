<!--------------------------------
 - @Author: ${author}
 - @LastEditor: ${author}
 - @LastEditTime: ${date}
 - @Description: ${entity}管理页面
 --------------------------------->

<template>
    <CommonPage>
        <template #action>
            <NButton type="primary" @click="handleAdd()">
                <i class="i-material-symbols:add mr-4 text-18" />
                创建${entity}
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
                <#if table.fields??>
                <#list table.fields as field>
                <#-- 排除系统字段 -->
                <#if !config.systemFields?seq_contains(field.propertyName)>
                <n-form-item label="${field.comment!field.propertyName}" path="${field.propertyName}">
                    <#-- 数字类型字段使用数字输入框 -->
                    <#if field.propertyType?contains("Integer") || field.propertyType?contains("Long")
                          || field.propertyType?contains("Double") || field.propertyType?contains("BigDecimal")>
                    <n-input-number
                        v-model:value="modalForm.${field.propertyName}"
                        placeholder="请输入${field.comment!field.propertyName}"
                        :min="0"
                        :precision="<#if field.propertyType?contains('Integer') || field.propertyType?contains('Long')>0<#else>2</#if>"
                    />
                    <#-- Boolean类型字段使用开关组件 -->
                    <#elseif field.propertyType == "Boolean" || field.propertyType == "boolean">
                    <n-switch v-model:value="modalForm.${field.propertyName}" />
                    <#else>
                    <n-input v-model:value="modalForm.${field.propertyName}" placeholder="请输入${field.comment!field.propertyName}" />
                    </#if>
                </n-form-item>
                </#if>
                </#list>
                <#else>
                <n-alert type="warning">未定义字段列表</n-alert>
                </#if>
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

defineOptions({ name: '${entity}Mgt' })

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
    name: '${entity}',
    initForm: {
        <#if fields??>
        <#list fields as field>
        ${field.propertyName}: '',
        </#list>
        </#if>
    },
    doCreate: api.create,
    doDelete: api.delete,
    doUpdate: api.update,
    refresh: () => $table.value?.handleSearch(),
})

const columns = [
    <#if table.fields??>
    <#list table.fields as field>
    {
        title: '${field.comment!field.propertyName}',
        key: '${field.propertyName}',
        width: 150,
        ellipsis: { tooltip: true }
    },
    </#list>
    </#if>
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
