package com.example.umcenterbacked.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author :lhy
 * @description :用户注册请求体
 * @date :2023/02/23 下午 01:28
 */
@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = -7885850358999864740L;

    private String userAccount;

    private String userPassword;

    private String checkPassword;
}
