package com.example.umcenterbacked.service;

import com.example.umcenterbacked.model.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lhy
 * @description 针对表【user(用户)】的数据库操作Service
 * @createDate 2023-02-22 16:12:12
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param userAccount   用户账号
     * @param userPassword  用户密码
     * @param checkPassword 校验码
     * @return long 新用户ID
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * 用户登录
     *
     * @param userAccount  用户账号
     * @param userPassword 用户密码
     * @return user 脱敏后的用户信息
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户信息脱敏
     *
     * @param originUser 脱敏前用户信息
     * @return com.example.umcenterbacked.model.User
     */
    User getSafetyUser(User originUser);

    /**
     * 用户登出
     * @param request springboot内置请求对象，用于存储用户session，记录用户态
     */
    int userLogout(HttpServletRequest request);
}
