package com.example.umcenterbacked.service.impl;

import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.umcenterbacked.common.ErrorCode;
import com.example.umcenterbacked.exception.BusinessException;
import com.example.umcenterbacked.model.User;
import com.example.umcenterbacked.service.UserService;
import com.example.umcenterbacked.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.umcenterbacked.constant.UserConstant.USER_LOGIN_STATE;

/**
 * @author lhy
 * @description 针对表【user(用户)】的数据库操作Service实现
 * @createDate 2023-02-22 16:12:12
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    //盐值混淆密码
    private static final String SALT = "wuxu";

    @Resource
    private UserMapper userMapper;

    /**
     * 用户注册逻辑
     *
     * @param userAccount   用户账号
     * @param userPassword  用户密码
     * @param checkPassword 验证密码
     * @return long 返回新注册用户ID
     */
    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {

        //校验
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            throw new BusinessException(ErrorCode.NULL_ERROE,"参数不能为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账户长度不能小于4");
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账户密码不能小于8");
        }

        //特殊字符校验
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账户包含特殊字符");
        }

        //检验密码与校验密码是否相同
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码和校验密码输入不一致");
        }

        //以上无问题，则查询数据库，进行查重操作
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账户重复");
        }

        //查重结束，进行密码加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());

        //密码加密后，插入数据库
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        boolean saveResult = this.save(user);
        if (!saveResult) {
            throw new BusinessException(ErrorCode.DATABASE_ERROR,"数据库写入错误");
        }

        return user.getId();
    }


    /**
     * 用户登录逻
     *
     * @param userAccount  用户账号
     * @param userPassword 用户密码
     * @param request      springboot内置请求对象，用于存储用户session，记录用户态
     * @return com.example.umcenterbacked.model.User 返回脱敏用户信息
     */
    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {

        //校验 非空、用户账号长度、密码长度
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.NULL_ERROE,"参数不能为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号不能小于4");
        }
        if (userPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码不能小于8");
        }

        //特殊字符校验
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账户包含特殊字符");
        }

        //查重结束，进行密码加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());

        //以上无问题，则查询数据库，进行查重操作
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPassword);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            log.info("User login failed,userAccount cannot match userPassword");
            throw new BusinessException(ErrorCode.NO_USER,"用户不存在");
        }

        //用户数据脱敏
        User safetyUser = getSafetyUser(user);

        //记录用户登录态
        request.getSession().setAttribute(USER_LOGIN_STATE, safetyUser);
        return safetyUser;
    }

    /**
     * 用户脱敏
     * @param originUser    脱敏前用户信息
     * @return com.example.umcenterbacked.model.User 脱敏后用户信息
     */
    @Override
    public User getSafetyUser(User originUser) {

        if (originUser == null) {
            throw new BusinessException(ErrorCode.NO_USER);
        }

        User safetyUser = new User();
        safetyUser.setId(originUser.getId());
        safetyUser.setUserName(originUser.getUserName());
        safetyUser.setUserAccount(originUser.getUserAccount());
        safetyUser.setAvatarUrl(originUser.getAvatarUrl());
        safetyUser.setGender(originUser.getGender());
        safetyUser.setPhone(originUser.getPhone());
        safetyUser.setEmail(originUser.getEmail());
        safetyUser.setUserStatus(originUser.getUserStatus());
        safetyUser.setCreateTime(originUser.getCreateTime());
        safetyUser.setUserRole(originUser.getUserRole());
        return safetyUser;
    }

    /**
     * 用户注销逻辑
     * @param request springboot内置请求对象，用于存储用户session，记录用户态
     * @return int 返回0
     */
    @Override
    public int userLogout(HttpServletRequest request) {
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        throw new BusinessException(ErrorCode.SUCCESS,"成功退出");
    }
}




