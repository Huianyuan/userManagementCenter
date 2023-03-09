import { GithubOutlined } from '@ant-design/icons';
import { DefaultFooter } from '@ant-design/pro-components';
import {BLOG, GITHUB} from "@/constants";
const Footer: React.FC = () => {
  const defaultMessage = '更改自 蚂蚁集团体验技术部产品';
  const currentYear = new Date().getFullYear();
  return (
    <DefaultFooter
      copyright={`${currentYear} ${defaultMessage}`}
      links={[
        {
          key: 'Blog',
          title: 'huianyuan惠安苑',
          href: BLOG,
          blankTarget: true,
        },
        {
          key: 'github',
          title: <><GithubOutlined/> huianyuan</>,
          href: GITHUB,
          blankTarget: true,
        },
        {
          key: 'Ant Design',
          title: 'Ant Design',
          href: 'https://ant.design',
          blankTarget: true,
        },
      ]}
    />
  );
};
export default Footer;
