package com.example.umcenterbacked.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author :lhy
 * @description :TODO
 * @date :2023/02/27 下午 01:17
 */
@Data
public class UserSearchRequest implements Serializable {
    private static final long serialVersionUID = -7187838029895017737L;
    private String username;
}
