package com.example.umcenterbacked.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.umcenterbacked.common.BaseResponse;
import com.example.umcenterbacked.common.ErrorCode;
import com.example.umcenterbacked.exception.BusinessException;
import com.example.umcenterbacked.model.User;
import com.example.umcenterbacked.model.request.UserDeletedRequest;
import com.example.umcenterbacked.model.request.UserLoginRequest;
import com.example.umcenterbacked.model.request.UserRegisterRequest;
import com.example.umcenterbacked.service.UserService;
import com.example.umcenterbacked.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.umcenterbacked.constant.UserConstant.ADMIN_ROLE;
import static com.example.umcenterbacked.constant.UserConstant.USER_LOGIN_STATE;

/**
 * @author :lhy
 * @description : 用户接口
 * @date :2023/02/23 下午 01:22
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    /**
     * 注册请求
     *
     * @param userRegisterRequest 用户注册信息请求体
     * @return java.lang.Long 返回新用户ID
     */
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {

        if (userRegisterRequest == null) {
            // return ResultUtils.error(ErrorCode.PARAMS_ERROR);
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        long result = userService.userRegister(userAccount, userPassword, checkPassword);

        return ResultUtils.success(result);
    }

    /**
     * 登录请求
     *
     * @param userLoginRequest 用户登录请求体
     * @param request          springboot内置请求对象，用于存储用户session，记录用户态
     * @return com.example.umcenterbacked.model.User 返回脱敏用户信息，json格式
     */
    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.NO_USER, "用户为空");
        }

        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            // return ResultUtils.error(ErrorCode.PARAMS_ERROR);
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号密码不能为空");
        }

        User result = userService.userLogin(userAccount, userPassword, request);
        return ResultUtils.success(result);
    }


    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "HttpServletRequest没有携带当前登录用户信息");
        }
        int result = userService.userLogout(request);
        return ResultUtils.success(result);
    }


    /**
     * 管理员查询，根据用户昵称查询
     *
     * @param userName 传入的查询字符串
     * @param request  springboot内置请求对象，用于存储用户session，记录用户态
     * @return com.example.umcenterbacked.common.BaseResponse<java.util.List < com.example.umcenterbacked.model.User>>
     */
    @GetMapping("/search")
    // public List<User> searchUsers(@RequestBody UserSearchRequest userSearchRequest, HttpServletRequest request) {
    //         String username = userSearchRequest.getUsername();
    public List<User> searchUsers(String userName, HttpServletRequest request) {

        //权限校验
        if (!isAdmin(request)) {
            // return ResultUtils.error(ErrorCode.NO_AUTH);
            throw new BusinessException(ErrorCode.NO_AUTH, "当前用户无权限");
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(userName)) {
            queryWrapper.like("userName", userName);
        }

        List<User> userList = userService.list(queryWrapper);
        List<User> result =
                userList.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());
        return result;
    }

    /**
     * 管理员删除，根据用户ID进行逻辑删除账号
     *
     * @param userDeleteRequest 根据ID删除用户，实体类包含ID字段
     * @return boolean 是否成功删除
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody UserDeletedRequest userDeleteRequest,
                                            HttpServletRequest request) {
        long id = userDeleteRequest.getId();
        // public boolean deleteUser(@RequestBody long id, HttpServletRequest request) {

        //权限校验
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH, "当前用户无权限");
        }

        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "待删除信息ID有误");
        }
        boolean result = userService.removeById(id);
        return ResultUtils.success(result);
    }

    //TODO：search和delete自行修改版

    // /**
    //  * 管理员查询，根据用户昵称查询
    //  *
    //  * @param username 用户昵称
    //  * @return java.util.List<com.example.umcenterbacked.model.User> 返回匹配到的用户List
    //  */
    // @GetMapping("/search")
    // public List<User> searchUsers( HttpServletRequest request) {
    //     String username = request.getParameter("userName");
    //     //权限校验
    //     if (!isAdmin(request)) {
    //         return new ArrayList<>();
    //     }
    //
    //     QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    //     if (StringUtils.isNotBlank(username)) {
    //         queryWrapper.like("userName", username);
    //     }
    //
    //     List<User> userList = userService.list(queryWrapper);
    //     return userList.stream().map(user -> {
    //         return userService.getSafetyUser(user);
    //     }).collect(Collectors.toList());
    // }


    // /**
    //  * 管理员删除，根据用户ID进行逻辑删除账号
    //  *
    //  * @param id 用户ID
    //  * @return boolean 是否成功删除
    //  */
    // @RequestMapping("/delete")
    // public boolean deleteUser(HttpServletRequest request) {
    //     String req = request.getParameter("id");
    //     long id = Long.parseLong(req);
    //     //权限校验
    //     if (!isAdmin(request)) {
    //         return false;
    //     }
    //
    //     if (id <= 0) {
    //         return false;
    //     }
    //     return userService.removeById(id);
    // }

    @GetMapping("/current")
    public User getCurrentUser(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User userCurrent = (User) userObj;
        if (userCurrent == null) {
            return null;
        }
        Long userID = userCurrent.getId();
        // TODO: 校验用户是否合法
        User user = userService.getById(userID);
        User result = userService.getSafetyUser(user);
        // return ResultUtils.success(result);
        return result;
    }


    /**
     * 校验当前用户是否为管理员
     *
     * @param request springboot内置请求对象，用于存储用户session，记录用户态
     * @return boolean
     */
    public boolean isAdmin(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;

        return user != null && user.getUserRole() == ADMIN_ROLE;
    }
}