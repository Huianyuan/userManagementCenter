import Footer from '@/components/Footer';
import {register} from '@/services/ant-design-pro/api';


import {LockOutlined, UserOutlined,} from '@ant-design/icons';
import {LoginForm, ProFormText,} from '@ant-design/pro-components';
import {message, Tabs} from 'antd';
import React, {useState} from 'react';
import {history} from 'umi';
import styles from './index.less';
import {SYSTEM_LOG} from "@/constants";


const Register: React.FC = () => {
  const [type, setType] = useState<string>('account');

  //表单提交2
  const handleSubmit = async (values: API.RegisterParams) => {
    const {userPassword, checkPassword} = values

    if (userPassword !== checkPassword) {
      const defaultLoginFailureMessage = '两次输入密码不一致，请重试！';
      message.error(defaultLoginFailureMessage);
      return
    }

    try {
      // 注册
      const res = await register(values);
      if (res.code === 200 && res.data > 0) {
        const defaultLoginSuccessMessage = '注册成功！';
        message.success(defaultLoginSuccessMessage);

        /** 此方法会跳转到 redirect 参数所在的位置 */
        if (!history) return;
        const {query} = history.location;
        const {redirect} = query as {
          redirect: string;
        };
        history.push('/user/login?redirect' + redirect);
        return;
      } else {
        throw new Error(res.description);
      }
    } catch (error:any) {
      const defaultLoginFailureMessage = '注册失败，请重试！';
      message.error(error.message ?? defaultLoginFailureMessage);
    }
  };
  return (
    <div className={styles.container}>
      <div className={styles.content}>
        <LoginForm

          submitter={{
            searchConfig: {
              submitText: '注册'
            }
          }}

          logo={<img alt="logo" src={SYSTEM_LOG}/>}
          title="User Management Center"
          subTitle={'UMC 简单的用户管理中心'}
          initialValues={{
            autoLogin: true,
          }}
          //表单提交1->表单提交2
          onFinish={async (values) => {
            await handleSubmit(values as API.RegisterParams);
          }}
        >
          <Tabs activeKey={type} onChange={setType}>
            <Tabs.TabPane key="account" tab={'账户注册'}/>
          </Tabs>

          {type === 'account' && (
            <>
              <ProFormText
                name="userAccount"
                fieldProps={{
                  size: 'large',
                  prefix: <UserOutlined className={styles.prefixIcon}/>,
                }}
                placeholder="请输入账户"
                rules={[
                  {
                    required: true,
                    message: '账户是必填项！',
                  },
                ]}
              />
              <ProFormText.Password
                name="userPassword"
                fieldProps={{
                  size: 'large',
                  prefix: <LockOutlined className={styles.prefixIcon}/>,
                }}
                placeholder="请输入密码"
                rules={[
                  {
                    required: true,
                    message: '密码是必填项！',
                  },
                  {
                    min: 8,
                    type: 'string',
                    message: '长度不能小于8',
                  }
                ]}
              />
              <ProFormText.Password
                name="checkPassword"
                fieldProps={{
                  size: 'large',
                  prefix: <LockOutlined className={styles.prefixIcon}/>,
                }}
                placeholder="请确认密码"
                rules={[
                  {
                    required: true,
                    message: '密码必填再次确认！',
                  },
                  {
                    min: 8,
                    type: 'string',
                    message: '长度不能小于8',
                  }
                ]}
              />
            </>
          )}

        </LoginForm>
      </div>
      <Footer/>
    </div>
  );
};
export default Register;
