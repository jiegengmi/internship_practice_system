import { useCallback, useMemo, useState, useEffect } from 'react';
import { Form, Row, Col, Card, Button } from 'antd';
import { ModalContent, FormItem, Content, validateRules, useDebounceValidator } from '@ra-lib/admin';
import config from 'src/commons/config-hoc';
import RoleSelectTable from 'src/pages/role/RoleSelectTable';

export default config({
    modal: {
        title: (props) => {
            if (props?.record?.isDetail) return '查看用户';

            return props.isEdit ? '编辑用户' : '创建用户';
        },
        width: '70%',
        top: 50,
    },
})(function Edit(props) {
    const { record, isEdit, onOk, onCancel } = props;
    const [loading, setLoading] = useState(false);
    const [form] = Form.useForm();
    const isDetail = record?.isDetail;

    const params = useMemo(() => {
        return { id: record?.id };
    }, [record]);


    const open = () => {
        setLoading(true);
    };

    const close = () => {
        setLoading(false);
    };

    // 编辑时，查询详情数据
    props.ajax.useGet('/user/getById', params, [params], {
        mountFire: isEdit,
        setLoading,
        formatResult: (data) => {
            form.setFieldsValue(data)
        },
    });
    const { run: save } = props.ajax.usePost('/user/insert', null, { close, successTip: '创建成功！' });
    const { run: update } = props.ajax.usePost('/user/update', null, { close, successTip: '修改成功！' });
    const { run: fetchUserByName } = props.ajax.useGet('/user/getByName');

    const handleSubmit = useCallback(
        async (values) => {
            const roleIds = values.roleIds?.filter((id) => !`${id}`.startsWith('systemId'));
            const params = {
                ...values,
                roleIds,
            };
            if (isEdit) {
                await update(params);
            } else {
                await save(params);
            }
            onOk();
        },
        [isEdit, update, save, onOk],
    );

    const checkAccount = useDebounceValidator(async (rule, value) => {
        if (!value) return;

        const user = await fetchUserByName({ username: value });
        if (!user) return;

        const id = form.getFieldValue('id');
        if (isEdit && user.id !== id && user.name === value) throw Error('账号不能重复！');
        if (!isEdit && user.name === value) throw Error('账号不能重复！');
    });

    const disabled = isDetail;
    const layout = {
        labelCol: { flex: '100px' },
        disabled,
    };
    const colLayout = {
        xs: { span: 24 },
        sm: { span: 12 },
    };
    return (
        <Form autoComplete='new-password' form={form} name="roleEdit" onFinish={handleSubmit} initialValues={{ enabled: true }}>
            <ModalContent
                loading={loading}
                okText="保存"
                okHtmlType="submit"
                cancelText="重置"
                onCancel={() => form.resetFields()}
                footer={disabled ? <Button onClick={onCancel}>关闭</Button> : undefined}
            >
                {isEdit ? <FormItem hidden name="id" /> : null}
                <Row gutter={8}>
                    <Col {...colLayout}>
                        <Card title="基础信息">
                            <Content fitHeight otherHeight={120}>
                                <FormItem
                                    {...layout}
                                    label="账号"
                                    name="name"
                                    required
                                    noSpace
                                    rules={[{ validator: checkAccount }]}
                                />
                                <FormItem {...layout}  label="密码" name="password" required noSpace />
                                <FormItem
                                    {...layout}
                                    type={'switch'}
                                    label="启用"
                                    name="status"
                                    checkedChildren="启"
                                    unCheckedChildren="禁"
                                    required
                                />
                                <FormItem {...layout} label="姓名" name="nickName" required noSpace />
                                <FormItem 
                                    {...layout} 
                                    label="性别" 
                                    name='sex'
                                    width={20}
                                    initialValue={1}
                                    options={[
                                        {value: 1, label:'男'},
                                        {value: 0, label:'女'}
                                    ]}
                                    allowClear
                                    noSpace 
                                />
                                <FormItem
                                    {...layout}
                                    label="邮箱"
                                    name="email"
                                    rules={[validateRules.email()]}
                                    noSpace
                                />
                                <FormItem
                                    {...layout}
                                    label="手机号"
                                    name="tel"
                                    rules={[validateRules.mobile()]}
                                    noSpace
                                />
                            </Content>
                        </Card>
                    </Col>
                    <Col {...colLayout}>
                        <Card title="角色配置" bodyStyle={{ padding: 0 }}>
                            <FormItem {...layout} name="roleIds">
                                <RoleSelectTable fitHeight otherHeight={200} getCheckboxProps={() => ({ disabled })} />
                            </FormItem>
                        </Card>
                    </Col>
                </Row>
            </ModalContent>
        </Form>
    );
});
