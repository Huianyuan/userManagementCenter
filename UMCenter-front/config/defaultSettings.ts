import { Settings as LayoutSettings } from '@ant-design/pro-components';



const Settings: LayoutSettings & {
  pwa?: boolean;
  logo?: string;
} = {
  navTheme: 'light',
  // 拂晓蓝
  primaryColor: '#1890ff',
  layout: 'mix',
  contentWidth: 'Fluid',
  fixedHeader: false,
  fixSiderbar: true,
  colorWeak: false,
  title: '用户管理中心',
  pwa: false,
  logo: 'https://avatars.githubusercontent.com/u/34713275?v=4',
  iconfontUrl: '',
};

export default Settings;
