import type {ActionType, ProColumns} from '@ant-design/pro-components';
import {ProTable, TableDropdown} from '@ant-design/pro-components';
import {useRef} from 'react';
import {searchUsers} from "@/services/ant-design-pro/api";
import {Image} from "antd";


const columns: ProColumns<API.CurrentUser>[] = [
  {
    dataIndex: 'id',
    valueType: 'indexBorder',
    width: 48,
  },
  {
    title: '头像',
    dataIndex: 'avatarUrl',
    search: false,
    render:(_,record) => (
      <Image
        src={record.avatarUrl}
        style={{width: 48, height: 48}}
      />
    )
  },
  {
    title: '用户名',
    dataIndex: 'userName',
    copyable: true,
    // tip: '过长会自动收缩',
    // formItemProps: {
    //   rules: [
    //     {
    //       required: true,
    //       message: '此项为必填项',
    //     },
    //   ],
    // },
  },
  {
    title: '账号',
    dataIndex: 'userAccount',
    copyable: true,
    // formItemProps: {
    //   rules: [
    //     {
    //       required: true,
    //       message: '此项为必填项',
    //     },
    //   ],
    // },
  },
  {
    title: '性别',
    dataIndex: 'gender',
    copyable: true,
    search: false,
  },
  {
    title: '电话',
    dataIndex: 'phone',
    copyable: true,
    search: false,
  },
  {
    title: '邮箱',
    dataIndex: 'email',
    copyable: true,
    search: false,
  },
  {
    title: '状态',
    dataIndex: 'userStatus',
    search: false,
    valueType: 'select',
    valueEnum:{
      0:{text: '正常',status: 'Default'},
      1:{text: '已销号',status: 'Error'},
    }
  },
  {
    title: '身份',
    dataIndex: 'userRole',
    valueType: 'select',
    search: false,
    valueEnum:{
      0:{text: '普通用户',status: 'Default'},
      1:{text: '管理员',status: 'Success'},
    }

  },
  {
    title: '创建时间',
    dataIndex: 'createTime',
    valueType: 'dateTime'
  },

  // {
  //   disable: true,
  //   title: '标签',
  //   dataIndex: 'labels',
  //   search: false,
  //   renderFormItem: (_, { defaultRender }) => {
  //     return defaultRender(_);
  //   },
  //   render: (_, record) => (
  //     <Space>
  //       {record.labels.map(({ name, color }) => (
  //         <Tag color={color} key={name}>
  //           {name}
  //         </Tag>
  //       ))}
  //     </Space>
  //   ),
  // },

  {
    title: '操作',
    valueType: 'option',
    key: 'option',
    render: (text, record, _, action) => [
      <a
        key="editable"
        onClick={() => {
          action?.startEditable?.(record.id);
        }}
      >
        编辑
      </a>,
      // @ts-ignore
      <a href={record.url} target="_blank" rel="noopener noreferrer" key="view">
        查看
      </a>,
      <TableDropdown
        key="actionGroup"
        onSelect={() => action?.reload()}
        menus={[
          {key: 'copy', name: '复制'},
          {key: 'delete', name: '删除'},
        ]}
      />,
    ],
  },
];

export default () => {
  const actionRef = useRef<ActionType>();



  return (
    <ProTable<API.CurrentUser>
      columns={columns}
      actionRef={actionRef}
      cardBordered
      // @ts-ignore
      request={async (params = {}, sort, filter) => {
        console.log(sort, filter);

        const userList = await searchUsers();
        return {
          data: userList
        }
      }}
      editable={{
        type: 'multiple',
      }}
      columnsState={{
        persistenceKey: 'pro-table-singe-demos',
        persistenceType: 'localStorage',
        onChange(value) {
          console.log('value: ', value);
        },
      }}
      rowKey="id"
      search={{
        labelWidth: 'auto',
      }}
      options={{
        setting: {
          // @ts-ignore
          listsHeight: 400,
        },
      }}
      form={{
        // 由于配置了 transform，提交的参与与定义的不同这里需要转化一下
        syncToUrl: (values, type) => {
          if (type === 'get') {
            return {
              ...values,
              created_at: [values.startTime, values.endTime],
            };
          }
          return values;
        },
      }}
      pagination={{
        pageSize: 10,
        onChange: (page) => console.log(page),
      }}
      dateFormatter="string"
      headerTitle="查询到的用户信息表"
    />
  );
};
