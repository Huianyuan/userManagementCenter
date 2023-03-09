package com.example.umcenterbacked.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author :lhy
 * @description :用户登录请求体
 * @date :2023/02/23 下午 01:28
 */
@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 6972891581551333164L;

    private String userAccount;

    private String userPassword;
}
