import { useState, useMemo, useCallback, useEffect } from 'react';
import { Button, Form, Space } from 'antd';
import { PageContent, QueryBar, FormItem, Table, Pagination, Operator, ToolBar } from '@ra-lib/admin';
import config from 'src/commons/config-hoc';
import EditModal from './EditModal';

export default config({
    path: '/users',
})(function User(props) {
    const [loading, setLoading] = useState(false);
    const [pageNum, setPageNum] = useState(1);
    const [pageSize, setPageSize] = useState(10);
    const [conditions, setConditions] = useState({});
    const [visible, setVisible] = useState(false);
    const [record, setRecord] = useState(null);
    const [form] = Form.useForm();
    const {
        data: roles = [],
        run: setRole
    } = props.ajax.useGet('/role/getEnableRoles', null, {
        formatResult: (res) => {
            return (res || []).map((item) => {
                    return {value: item.id, label: item.name};
                });
        },
    });

    useEffect(() => {
        (async () => {
            await setRole();
        })();
    }, [setRole]);

    const params = useMemo(() => {
        return {
            ...conditions,
            pageNum,
            pageSize,
        };
    }, [conditions, pageNum, pageSize]);
    
    // 使用现有查询条件，重新发起请求
    const refreshSearch = useCallback(() => {
        setConditions(form.getFieldsValue());
    }, [form]);

    // 获取列表
    const { data: { dataSource, total } = {} } = props.ajax.useGet('/user/query', params, [params], {
        setLoading,
        formatResult: (data) => {
            return  {
                dataSource: data?.records || [],
                total: data?.total || 0,
            };
        },
    });
    
    // 删除
    const { run: deleteRecord } = props.ajax.useDel('/user/delete/:id', null, { setLoading, successTip: '删除成功！' });

    const columns = [
        { title: '账号', dataIndex: 'name', align: 'center'},
        { title: '姓名', dataIndex: 'nickName', align: 'center' },
        { title: '性别', dataIndex: 'sex', width: 45, align: 'center', render: (value) => {
            return value === 1 ? "男" : "女";
        }},
        { title: '手机号', dataIndex: 'tel', width:100, align: 'center' },
        { title: '邮箱', dataIndex: 'email', width:130, align: 'center' },
        { title: '创建时间', dataIndex: 'createTime', width:145, align: 'center' },
        { title: '修改时间', dataIndex: 'updateTime', width:145, align: 'center' },
        { title: '状态', dataIndex: 'status',width: 45, align: 'center', render: (value) => {
            if (value === -1) {
                return "注销";
            } else if (value === 0) {
                return "禁用";
            } else if (value === 1) {
                return "启用";
            }
        }},
        {
            title: '操作',
            key: 'operator',
            width: 150,
            align: 'center',
            render: (value, record) => {
                const { id, name } = record;
                const items = [
                    {
                        label: '查看',
                        onClick: () => setRecord({ ...record, isDetail: true }) || setVisible(true),
                    },
                    {
                        label: '修改',
                        onClick: () => setRecord(record) || setVisible(true),
                    },
                    {
                        label: '删除',
                        color: 'red',
                        confirm: {
                            title: `您确定删除「${name}」吗？`,
                            onConfirm: () => handleDelete(id),
                        },
                    },
                ];

                return <Operator items={items} />;
            },
        },
    ];

    const handleDelete = useCallback(
        async (id) => {
            await deleteRecord(id);
            // 触发列表更新
            refreshSearch();
        },
        [deleteRecord, refreshSearch],
    );

    const queryItem = {
        style: { width: 200 },
    };

    return (
        <PageContent loading={loading}>
            <QueryBar>
                <Form
                    name="user"
                    layout="inline"
                    form={form}
                    initialValues={{ position: '01' }}
                    onFinish={(values) => setPageNum(1) || setConditions(values)}
                >
                    <FormItem
                        {...queryItem}
                        label="用户角色"
                        name="roleId"
                        allowClear
                        options={roles}
                    />
                    <FormItem {...queryItem} label="账号" name="username" />
                    <FormItem {...queryItem} label="姓名" name="nickName" />
                    <FormItem {...queryItem} label="手机号" name="tel" />
                    <FormItem {...queryItem} label="邮箱" name="email" />
                    <FormItem>
                        <Space>
                            <Button type="primary" htmlType="submit">
                                查询
                            </Button>
                            <Button onClick={() => form.resetFields()}>重置</Button>
                        </Space>
                    </FormItem>
                </Form>
            </QueryBar>
            <ToolBar>
                <Button type="primary" onClick={() => setRecord(null) || setVisible(true)}>
                    添加
                </Button>
            </ToolBar>
            <Table
                className="table1"
                serialNumber
                pageNum={pageNum}
                pageSize={pageSize}
                fitHeight
                dataSource={dataSource}
                columns={columns}
                rowKey="id"
            />
            <Pagination
                total={total}
                pageNum={pageNum}
                pageSize={pageSize}
                onPageNumChange={setPageNum}
                onPageSizeChange={(pageSize) => setPageNum(1) || setPageSize(pageSize)}
            />
            <EditModal
                visible={visible}
                record={record}
                isEdit={!!record}
                onOk={() => setVisible(false) || refreshSearch()}
                onCancel={() => setVisible(false)}
            />
        </PageContent>
    );
});
