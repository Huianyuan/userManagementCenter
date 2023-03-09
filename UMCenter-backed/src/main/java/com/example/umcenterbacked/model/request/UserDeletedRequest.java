package com.example.umcenterbacked.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author :lhy
 * @description : 管理员删除请求实体
 * @date :2023/02/27 下午 01:08
 */
@Data
public class UserDeletedRequest implements Serializable {
    private static final long serialVersionUID = 7867442180286525369L;

    private Long id;

}
